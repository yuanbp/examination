package com.chieftain.examination;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * POI 操作 Excel
 *
 * @author niejy  niejingyu@gmail.com
 * @since: 1.0
 * @version:1.0
 * @createTime:2011 年 05 月 21 日 16：12：15
 * @updateTime:2011 年 07 月 21 日 10：47：20
 */
public class PoiExcelSheetCopy {

    /**
     * 根据源 Sheet 样式 copy 新 Sheet
     *
     * @param fromsheetname
     * @param newsheetname
     * @param targetFile
     */
    public void copySheet(String fromsheetname, String newsheetname, String targetFile) {
        XSSFWorkbook wb = null;
        try {
            FileInputStream fis = new FileInputStream(targetFile);
            wb = new XSSFWorkbook(fis);
            XSSFSheet fromsheet = wb.getSheet(fromsheetname);
            if (fromsheet != null && wb.getSheet(newsheetname) == null) {
                XSSFSheet newsheet = wb.createSheet(newsheetname);
                // 设置打印参数    
                newsheet.setMargin(XSSFSheet.TopMargin, fromsheet.getMargin(XSSFSheet.TopMargin));// 页边距（上）
                newsheet.setMargin(XSSFSheet.BottomMargin, fromsheet.getMargin(XSSFSheet.BottomMargin));// 页边距（下）
                newsheet.setMargin(XSSFSheet.LeftMargin, fromsheet.getMargin(XSSFSheet.LeftMargin));// 页边距（左）
                newsheet.setMargin(XSSFSheet.RightMargin, fromsheet.getMargin(XSSFSheet.RightMargin));// 页边距（右

                XSSFPrintSetup ps = newsheet.getPrintSetup();
                ps.setLandscape(false); // 打印方向，true：横向，false：纵向 (默认)    
                ps.setVResolution((short) 600);
                ps.setPaperSize(XSSFPrintSetup.A4_PAPERSIZE); // 纸张类型    

                File file = new File(targetFile);
                if (file.exists() && (file.renameTo(file))) {
                    copyRows(wb, fromsheet, newsheet, fromsheet.getFirstRowNum(), fromsheet.getLastRowNum());
                    FileOutputStream fileOut = new FileOutputStream(targetFile);
                    wb.write(fileOut);
                    fileOut.flush();
                    fileOut.close();
                } else {
                    System.out.println("文件不存在或者正在使用, 请确认…");
                }
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拷贝 Excel 行
     *
     * @param wb
     * @param fromsheet
     * @param newsheet
     * @param firstrow
     * @param lastrow
     */
    public void copyRows(XSSFWorkbook wb, XSSFSheet fromsheet, XSSFSheet newsheet, int firstrow, int lastrow) {
        if ((firstrow == -1) || (lastrow == -1) || lastrow < firstrow) {
            return;
        }
        // 拷贝合并的单元格    
        CellRangeAddress region = null;
        for (int i = 0; i < fromsheet.getNumMergedRegions(); i++) {
            region = fromsheet.getMergedRegion(i);
            if ((region.getFirstColumn() >= firstrow) && (region.getLastRow() <= lastrow)) {
                newsheet.addMergedRegion(region);
            }
        }

        XSSFRow fromRow = null;
        XSSFRow newRow = null;
        XSSFCell newCell = null;
        XSSFCell fromCell = null;
        // 设置列宽    
        for (int i = firstrow; i <= lastrow; i++) {
            fromRow = fromsheet.getRow(i);
            if (fromRow != null) {
                for (int j = fromRow.getLastCellNum(); j >= fromRow.getFirstCellNum(); j--) {
                    int colnum = fromsheet.getColumnWidth((short) j);
                    if (colnum > 100) {
                        newsheet.setColumnWidth((short) j, (short) colnum);
                    }
                    if (colnum == 0) {
                        newsheet.setColumnHidden((short) j, true);
                    } else {
                        newsheet.setColumnHidden((short) j, false);
                    }
                }
                break;
            }
        }
        // 拷贝行并填充数据    
        for (int i = 0; i <= lastrow; i++) {
            fromRow = fromsheet.getRow(i);
            if (fromRow == null) {
                continue;
            }
            newRow = newsheet.createRow(i - firstrow);
            newRow.setHeight(fromRow.getHeight());
            for (int j = fromRow.getFirstCellNum(); j < fromRow.getPhysicalNumberOfCells(); j++) {
                fromCell = fromRow.getCell((short) j);
                if (fromCell == null) {
                    continue;
                }
                newCell = newRow.createCell((short) j);
                newCell.setCellStyle(fromCell.getCellStyle());
                int cType = fromCell.getCellType();
                newCell.setCellType(cType);
                switch (cType) {
                    case XSSFCell.CELL_TYPE_STRING:
                        newCell.setCellValue(fromCell.getRichStringCellValue());
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        newCell.setCellValue(fromCell.getNumericCellValue());
                        break;
                    case XSSFCell.CELL_TYPE_FORMULA:
                        newCell.setCellFormula(fromCell.getCellFormula());
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        newCell.setCellValue(fromCell.getBooleanCellValue());
                        break;
                    case XSSFCell.CELL_TYPE_ERROR:
                        newCell.setCellValue(fromCell.getErrorCellValue());
                        break;
                    default:
                        newCell.setCellValue(fromCell.getRichStringCellValue());
                        break;
                }
            }
        }
    }

    public void copyRowsDesc(XSSFWorkbook wb, XSSFSheet fromsheet, XSSFSheet newsheet, int firstrow, int lastrow) {
        if ((firstrow == -1) || (lastrow == -1) || lastrow < firstrow) {
            return;
        }
        // 拷贝合并的单元格
        CellRangeAddress region = null;
        for (int i = 0; i < fromsheet.getNumMergedRegions(); i++) {
            region = fromsheet.getMergedRegion(i);
            if ((region.getFirstColumn() >= firstrow) && (region.getLastRow() <= lastrow)) {
                newsheet.addMergedRegion(region);
            }
        }

        XSSFRow fromRow = null;
        XSSFRow newRow = null;
        XSSFCell newCell = null;
        XSSFCell fromCell = null;
        // 设置列宽
        for (int i = firstrow; i <= lastrow; i++) {
            fromRow = fromsheet.getRow(i);
            if (fromRow != null) {
                for (int j = fromRow.getLastCellNum(); j >= fromRow.getFirstCellNum(); j--) {
                    int colnum = fromsheet.getColumnWidth((short) j);
                    if (colnum > 100) {
                        newsheet.setColumnWidth((short) j, (short) colnum);
                    }
                    if (colnum == 0) {
                        newsheet.setColumnHidden((short) j, true);
                    } else {
                        newsheet.setColumnHidden((short) j, false);
                    }
                }
                break;
            }
        }
        // 拷贝行并填充数据
        for (int i = lastrow; i > firstrow; i--) {
            fromRow = fromsheet.getRow(i);
            if (fromRow == null) {
                continue;
            }
            newRow = newsheet.createRow(i);
            newRow.setHeight(fromRow.getHeight());
            for (int j = fromRow.getFirstCellNum(); j < fromRow.getPhysicalNumberOfCells(); j++) {
                fromCell = fromRow.getCell((short) j);
                if (fromCell == null) {
                    continue;
                }
                newCell = newRow.createCell((short) j);
                newCell.setCellStyle(fromCell.getCellStyle());
                int cType = fromCell.getCellType();
                newCell.setCellType(cType);
                switch (cType) {
                    case XSSFCell.CELL_TYPE_STRING:
                        newCell.setCellValue(fromCell.getRichStringCellValue());
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        newCell.setCellValue(fromCell.getNumericCellValue());
                        break;
                    case XSSFCell.CELL_TYPE_FORMULA:
                        newCell.setCellFormula(fromCell.getCellFormula());
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        newCell.setCellValue(fromCell.getBooleanCellValue());
                        break;
                    case XSSFCell.CELL_TYPE_ERROR:
                        newCell.setCellValue(fromCell.getErrorCellValue());
                        break;
                    default:
                        newCell.setCellValue(fromCell.getRichStringCellValue());
                        break;
                }
            }
        }
    }

    public void revertShiftRow(XSSFSheet sheetAt, int startRow, int endRow, int n, boolean copyRowHeight, boolean resetOriginalRowHeight) {
        System.out.println(sheetAt.getLastRowNum());
        //先获取原始的合并单元格address集合
        List<CellRangeAddress> originMerged = sheetAt.getMergedRegions();
        //移动
        sheetAt.shiftRows(startRow, endRow, n, copyRowHeight, resetOriginalRowHeight);
        //关键逻辑再这个for循环
        for (CellRangeAddress cellRangeAddress : originMerged) {
            //这里的8是插入行的index，表示这行之后才重新合并
            if (cellRangeAddress.getFirstRow() > startRow) {
                //你插入了几行就加几，我这里插入了一行，加1
                int firstRow = cellRangeAddress.getFirstRow() + n;
                if (firstRow >= (startRow + n + 1) || firstRow <= (startRow + n + (n - 1))) {
                    continue;
                }
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(firstRow, (firstRow + (cellRangeAddress
                        .getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                sheetAt.addMergedRegion(newCellRangeAddress);
            }
        }
    }

    public void shiftRowReal(XSSFSheet sheetAt, int startRow, int endRow, int n, boolean copyRowHeight, boolean resetOriginalRowHeight) {
        System.out.println(sheetAt.getLastRowNum());
        //先获取原始的合并单元格address集合
        List<CellRangeAddress> originMerged = sheetAt.getMergedRegions();
        //移动
        sheetAt.shiftRows(startRow, endRow, n, copyRowHeight, resetOriginalRowHeight);
        //关键逻辑再这个for循环
        for (CellRangeAddress cellRangeAddress : originMerged) {
            //这里的8是插入行的index，表示这行之后才重新合并
            if (cellRangeAddress.getFirstRow() > startRow) {
                //你插入了几行就加几，我这里插入了一行，加1
                int firstRow = cellRangeAddress.getFirstRow() + n;
                if (firstRow >= (startRow + n + 1) || firstRow <= (startRow + n + (n - 1))) {
                    continue;
                }
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(firstRow, (firstRow + (cellRangeAddress
                        .getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                sheetAt.addMergedRegion(newCellRangeAddress);
            }
        }
    }


    public void copyTrgetRows(XSSFWorkbook wb, XSSFSheet fromsheet, XSSFSheet newsheet, int fromRowIdx, int targetRowIdx) {
        if (fromRowIdx == -1 || targetRowIdx == -1) {
            return;
        }
        // 拷贝合并的单元格
        CellRangeAddress region = null;
        for (int i = 0; i < fromsheet.getNumMergedRegions(); i++) {
            region = fromsheet.getMergedRegion(i);
            newsheet.addMergedRegion(region);
        }

        XSSFRow fromRow = null;
        XSSFRow newRow = null;
        XSSFCell newCell = null;
        XSSFCell fromCell = null;
        // 设置列宽
        fromRow = fromsheet.getRow(fromRowIdx);
        for (int j = fromRow.getLastCellNum(); j >= fromRow.getFirstCellNum(); j--) {
            int colnum = fromsheet.getColumnWidth((short) j);
            if (colnum > 100) {
                newsheet.setColumnWidth((short) j, (short) colnum);
            }
            if (colnum == 0) {
                newsheet.setColumnHidden((short) j, true);
            } else {
                newsheet.setColumnHidden((short) j, false);
            }
        }
        // 拷贝行并填充数据
        fromRow = fromsheet.getRow(fromRowIdx);
        if (fromRow == null) {
            return;
        }
        newRow = newsheet.createRow(targetRowIdx);
        newRow.setHeight(fromRow.getHeight());
        for (int j = fromRow.getFirstCellNum(); j < fromRow.getPhysicalNumberOfCells(); j++) {
            fromCell = fromRow.getCell((short) j);
            if (fromCell == null) {
                continue;
            }
            newCell = newRow.createCell((short) j);
            newCell.setCellStyle(fromCell.getCellStyle());
            int cType = fromCell.getCellType();
            newCell.setCellType(cType);
            switch (cType) {
                case XSSFCell.CELL_TYPE_STRING:
                    newCell.setCellValue(fromCell.getRichStringCellValue());
                    break;
                case XSSFCell.CELL_TYPE_NUMERIC:
                    newCell.setCellValue(fromCell.getNumericCellValue());
                    break;
                case XSSFCell.CELL_TYPE_FORMULA:
                    newCell.setCellFormula(fromCell.getCellFormula());
                    break;
                case XSSFCell.CELL_TYPE_BOOLEAN:
                    newCell.setCellValue(fromCell.getBooleanCellValue());
                    break;
                case XSSFCell.CELL_TYPE_ERROR:
                    newCell.setCellValue(fromCell.getErrorCellValue());
                    break;
                default:
                    newCell.setCellValue(fromCell.getRichStringCellValue());
                    break;
            }
        }
    }

    public XSSFSheet insertRow(XSSFSheet sheetAt, int start, int end, int n, boolean copyRowHeight, boolean resetOriginalRowHeight) {
        List<CellRangeAddress> originMerged = sheetAt.getMergedRegions();
        sheetAt.shiftRows(start, end, n, true, true);
        for (int i = 0; i < n; i++) {
            sheetAt.createRow(start + i);
        }
        for (CellRangeAddress cellRangeAddress : originMerged) {
            if (cellRangeAddress.getFirstRow() > start) {
                int firstRow = cellRangeAddress.getFirstRow() + n;
                if (firstRow >= (start + n + 1) || firstRow <= (start + n + (n - 1))) {
                    continue;
                }
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(firstRow, (firstRow + (cellRangeAddress
                        .getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                sheetAt.addMergedRegion(newCellRangeAddress);
            }
        }
        return sheetAt;
    }

    public XSSFSheet insertRowWith(XSSFSheet sheetAt, XSSFRow sampleRow, int start, int end, int n, boolean copyRowHeight, boolean resetOriginalRowHeight) {
        List<CellRangeAddress> originMerged = sheetAt.getMergedRegions();
        int rowRange = n - 1;
        if (n > 1) {
            sheetAt.shiftRows(start, end, n - 1, true, true);
        }
        for (int i = 0; i < n; i++) {
            XSSFRow dataRow = sheetAt.createRow(start + i);
            for (int j = 0; j < sampleRow.getPhysicalNumberOfCells() - 4; j++) {
                XSSFCell cell = dataRow.createCell(j);
                cell.setCellStyle(sampleRow.getCell(j).getCellStyle());
                if (sampleRow.getCell(j).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                    cell.setCellValue(sampleRow.getCell(j).getStringCellValue());
                } else {
                    cell.setCellValue(sampleRow.getCell(j).getNumericCellValue());
                }
                if (j == 0) {
                    cell.setCellValue(i);
                }

            }
        }
        if (n > 1) {
            int rangeStart = 10;
            int rangeEnd = 10;
            if (rowRange > 2) {
                for (int i = 2; i < (n - 1); i++) {
                    rangeEnd = rangeEnd + 2;
                }
                System.out.println("start:" + start + "  end:" + end);
            }

            for (CellRangeAddress cellRangeAddress : originMerged) {
                if (cellRangeAddress.getFirstRow() > start) {
                    int firstRow = cellRangeAddress.getFirstRow() + rowRange;
                    if (n > 2) {
                        if (rowRange == 2 && firstRow == 10) {
                            continue;
                        } else if (firstRow >= rangeStart || firstRow <= rangeEnd) {
                            continue;
                        }
                    }

//                    if (firstRow == 10 || firstRow == 11 || firstRow == 12 ||  firstRow == 13 || firstRow == 14 || firstRow == 15 || firstRow == 16 || firstRow == 17 || firstRow == 18 || firstRow == 19 || firstRow == 20 || firstRow == 21 || firstRow == 22) {
//                        continue;
//                    }
                    CellRangeAddress newCellRangeAddress = new CellRangeAddress(firstRow, (firstRow + (cellRangeAddress
                            .getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress.getFirstColumn(),
                            cellRangeAddress.getLastColumn());
                    sheetAt.addMergedRegion(newCellRangeAddress);
                }
            }
        }
        return sheetAt;
    }

    public static void main(String[] args) throws Exception {
//        PoiExcelSheetCopy ew = new PoiExcelSheetCopy();
//        ew.copySheet("template", "test2", "template.xls");
        test();
//        File file = new File("/Users/chieftain/retainfiles/settlement.xlsx");
//        InputStream template = new FileInputStream(file);
//        XSSFWorkbook book = new XSSFWorkbook(template);
//        XSSFSheet sheetAt = book.getSheetAt(0);
//        new PoiExcelSheetCopy().insertRow(sheetAt,7,sheetAt.getLastRowNum(),6,true,false);
//        OutputStream outStream = new FileOutputStream("/Users/chieftain/retainfiles/settlementmodel.xlsx");
//        book.write(outStream);
//        outStream.close();
//        template.close();
    }

    public static void test() throws Exception {
        int start = 7;
        int n = 7;
        File file = new File("/Users/chieftain/retainfiles/settlement.xlsx");
        InputStream template = new FileInputStream(file);
        Workbook book = new XSSFWorkbook(template);
        Sheet sheetAt = book.getSheetAt(0);
        System.out.println(sheetAt.getLastRowNum());
        Row row = sheetAt.getRow(7);
        //先获取原始的合并单元格address集合
        List<CellRangeAddress> originMerged = sheetAt.getMergedRegions();
        //移动
        sheetAt.shiftRows(start, sheetAt.getLastRowNum(), n, true, true);
        Row create = sheetAt.createRow(start);
        Row create1 = sheetAt.createRow(start + 1);
        Row create2 = sheetAt.createRow(start + 2);
        Row create3 = sheetAt.createRow(start + 3);
        Row create4 = sheetAt.createRow(start + 4);
        Row create5 = sheetAt.createRow(start + 5);
        //关键逻辑再这个for循环
        for (CellRangeAddress cellRangeAddress : originMerged) {
            //这里的8是插入行的index，表示这行之后才重新合并
            if (cellRangeAddress.getFirstRow() > start) {
                //你插入了几行就加几，我这里插入了一行，加1
                int firstRow = cellRangeAddress.getFirstRow() + n;
//                if(firstRow == (start+n+1) || firstRow == (start+n+2) || firstRow == (start+n+3) || firstRow == (start+n+4)){
//                    continue;
//                }
                if (firstRow >= (start + n + 1) || firstRow <= (start + n + (n - 1))) {
                    continue;
                }
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(firstRow, (firstRow + (cellRangeAddress
                        .getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                sheetAt.addMergedRegion(newCellRangeAddress);
            }
        }
        book.write(new FileOutputStream("/Users/chieftain/retainfiles/settlementmodel.xlsx"));
        template.close();
    }
}