package com.chieftain.examination;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * com.chieftain.jtestcodes [JTestCodes]
 * Created by chieftain on 2018/8/15
 *
 * @author chieftain on 2018/8/15
 */
public class ExcelTest {

    public static void main(String[] args) throws Exception {
//        copyHandle();
        testInsertRow();
//        sort();
    }

    public static void sort() {
//        System.out.println(11%3);
        int n = 10;
        int end = 10;
        int start = 10;
        for(int i=2;i<n;i++){
            end = end + 2;
        }
        System.out.println("start:" + start + "  end:" + end);
    }

    public static void testInsertRow() throws Exception {
        delFileByLikeName("/Users/chieftain/retainfiles","randomfile");
        PoiExcelSheetCopy ew = new PoiExcelSheetCopy();
        File file = new File("/Users/chieftain/retainfiles/settlement.xlsx");
        InputStream template = new FileInputStream(file);
        XSSFWorkbook book = new XSSFWorkbook(template);
        XSSFSheet sheetAt = book.getSheetAt(0);
        XSSFRow row = sheetAt.getRow(7);
        XSSFSheet newSheet = book.cloneSheet(0);
        ew.insertRowWith(newSheet, row, 7, sheetAt.getLastRowNum(), 3, true, false);
//        XSSFRow xjRow = newSheet.getRow(13);
//        xjRow.getCell(5).setCellValue(1800000);
//        xjRow.getCell(6).setCellValue(180000);
//        xjRow.getCell(7).setCellValue(18000);
//        xjRow.getCell(8).setCellValue(1800);
//        xjRow.getCell(9).setCellValue(180);
//        XSSFRow jmRow = newSheet.getRow(14);
//        jmRow.getCell(1).setCellValue("￥1800000");
//        jmRow.getCell(5).setCellValue("￥5800000");
//        jmRow.getCell(8).setCellValue("￥8800000");
//        XSSFRow ysRow = newSheet.getRow(15);
//        ysRow.getCell(1).setCellValue("￥180");
//        ysRow.getCell(5).setCellValue("￥58");
//        ysRow.getCell(8).setCellValue("￥8");
//        XSSFRow crRow = newSheet.getRow(16);
//        crRow.getCell(1).setCellValue(jmRow.getCell(1).getStringCellValue()+"绝不按时给钱");
        book.removeSheetAt(0);
        OutputStream outStream = new FileOutputStream("/Users/chieftain/retainfiles/randomfile"+System.currentTimeMillis()+".xlsx");
        book.write(outStream);
        outStream.close();
        template.close();
    }

    public static void delFileByLikeName(String path, String name){
        File topFolder = new File(path);
        if(topFolder.isDirectory()){
            File[] files = topFolder.listFiles();
            for (File file : files) {
                if(file.isFile()){
                    if(file.getName().contains(name)){
                        file.deleteOnExit();
                    }
                }else {

                }
            }
        }else {

        }
    }

    public static void copyHandle() {
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            PoiExcelSheetCopy ew = new PoiExcelSheetCopy();
            File file = new File("/Users/chieftain/retainfiles/settlement.xlsx");
            inStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(inStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFSheet newSheet = workbook.createSheet("settlement");
            newSheet.setZoom(115);
            ew.copyRows(workbook, sheet, newSheet, 0, 7);
            int fromRowIndx = 7;
            int rowIdx = 7;
            int datasize = 3;
            for (int i = 0; i < datasize; i++) {
                XSSFRow row = sheet.getRow(fromRowIndx);
                rowIdx++;
                XSSFRow dataRow = newSheet.createRow(rowIdx);
                for (int j = 0; j < row.getPhysicalNumberOfCells() - 4; j++) {
                    XSSFCell cell = dataRow.createCell(j);
                    cell.setCellStyle(row.getCell(j).getCellStyle());
                    if (row.getCell(j).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        cell.setCellValue(row.getCell(j).getStringCellValue());
                    } else {
                        cell.setCellValue(row.getCell(j).getNumericCellValue());
                    }

                }
            }
            ew.insertRow(sheet, 7, sheet.getLastRowNum(), datasize, true, false);
            ew.copyRowsDesc(workbook, sheet, newSheet, fromRowIndx + datasize, sheet.getLastRowNum());
            workbook.removeSheetAt(0);
            outStream = new FileOutputStream("/Users/chieftain/retainfiles/settlementtest.xlsx");
            workbook.write(outStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadData() {
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            File file = new File("/Users/chieftain/retainfiles/settlement.xlsx");
            inStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(inStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFSheet newSheet = workbook.cloneSheet(1);
            XSSFRow row0 = newSheet.createRow(0);
            outStream = new FileOutputStream("/Users/chieftain/retainfiles/settlementtest.xlsx");
            workbook.write(outStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void moveRow() {
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            File file = new File("/Users/chieftain/retainfiles/settlement.xlsx");
            inStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(inStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<XSSFRow> rowList = new ArrayList<>();
            int rowIdx = 7;
            for (int i = 0; i < 3; i++) {
                sheet.shiftRows(7 + i, sheet.getPhysicalNumberOfRows(), 1, true, false);
                XSSFRow row = sheet.getRow(7 + i + 1);
                rowIdx = rowIdx + i;
                XSSFRow dataRow = sheet.createRow(rowIdx);
                for (int j = 0; j < row.getPhysicalNumberOfCells() - 4; j++) {
                    XSSFCell cell = dataRow.createCell(j);
                    cell.setCellStyle(row.getCell(j).getCellStyle());
                    if (row.getCell(j).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        cell.setCellValue(row.getCell(j).getStringCellValue());
                    } else {
                        cell.setCellValue(row.getCell(j).getNumericCellValue());
                    }

                }
            }
            outStream = new FileOutputStream("/Users/chieftain/retainfiles/settlementtest.xlsx");
            workbook.write(outStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 行复制功能
     *
     * @param fromRow
     * @param toRow
     */
    public static void copyRow(Workbook wb, Row fromRow, Row toRow, boolean copyValueFlag) {
        toRow.setHeight(fromRow.getHeight());
        for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext(); ) {
            Cell tmpCell = (Cell) cellIt.next();
            Cell newCell = toRow.createCell(tmpCell.getColumnIndex());
            copyCell(wb, tmpCell, newCell, copyValueFlag);
        }
        Sheet worksheet = fromRow.getSheet();
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == fromRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(toRow.getRowNum(), (toRow.getRowNum() +
                        (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress
                        .getFirstColumn(), cellRangeAddress.getLastColumn());
                worksheet.addMergedRegionUnsafe(newCellRangeAddress);
            }
        }
    }

    /**
     * 复制单元格
     *
     * @param srcCell
     * @param distCell
     * @param copyValueFlag true则连同cell的内容一起复制
     */
    public static void copyCell(Workbook wb, Cell srcCell, Cell distCell, boolean copyValueFlag) {
        CellStyle newStyle = wb.createCellStyle();
        CellStyle srcStyle = srcCell.getCellStyle();
        newStyle.cloneStyleFrom(srcStyle);
        newStyle.setFont(wb.getFontAt(srcStyle.getFontIndex()));
        //样式
        distCell.setCellStyle(newStyle);
        //评论
        if (srcCell.getCellComment() != null) {
            distCell.setCellComment(srcCell.getCellComment());
        }
        // 不同数据类型处理
        CellType srcCellType = srcCell.getCellTypeEnum();
        distCell.setCellType(srcCellType);
        if (copyValueFlag) {
            if (srcCellType == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(srcCell)) {
                    distCell.setCellValue(srcCell.getDateCellValue());
                } else {
                    distCell.setCellValue(srcCell.getNumericCellValue());
                }
            } else if (srcCellType == CellType.STRING) {
                distCell.setCellValue(srcCell.getRichStringCellValue());
            } else if (srcCellType == CellType.BLANK) {

            } else if (srcCellType == CellType.BOOLEAN) {
                distCell.setCellValue(srcCell.getBooleanCellValue());
            } else if (srcCellType == CellType.ERROR) {
                distCell.setCellErrorValue(srcCell.getErrorCellValue());
            } else if (srcCellType == CellType.FORMULA) {
                distCell.setCellFormula(srcCell.getCellFormula());
            } else {
            }
        }
    }
}
