package com.chieftain.examination;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class CreatRowTest {
	//当前文件已经存在
	private String excelPath = "/Users/chieftain/retainfiles/settlement.xlsx";
	private String outExcelPath = "/Users/chieftain/retainfiles/settlementtest.xlsx";
	//从第几行插入进去
	private int insertStartPointer = 7;
	//在当前工作薄的那个工作表单中插入这行数据 
	private String sheetName = "Sheet1";
	private Integer sheetIdx = 0;
 
	/**
	 * 总的入口方法
	 */
	public static void main(String[] args) {
		CreatRowTest crt = new CreatRowTest();
		crt.insertRows();
	}
	/**
	 * 在已有的Excel文件中插入一行新的数据的入口方法
	 */
	public void insertRows() {
		XSSFWorkbook wb = returnWorkBookGivenFileHandle();
		XSSFSheet sheet1 = wb.getSheetAt(sheetIdx);
		XSSFRow row = createRow(sheet1, insertStartPointer);
		createCell(row);
		saveExcel(wb);
 
	}
    /**
     * 保存工作薄
     * @param wb
     */
	private void saveExcel(XSSFWorkbook wb) {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(outExcelPath);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	}
    /**
     * 创建要出入的行中单元格
     * @param row
     * @return
     */
	private XSSFCell createCell(XSSFRow row) {
		XSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(999999);
		row.createCell(1).setCellValue(1.2);
		row.createCell(2).setCellValue("This is a string cell");
		return cell;
	}
   /**
    * 得到一个已有的工作薄的POI对象
    * @return
    */
	private XSSFWorkbook returnWorkBookGivenFileHandle() {
		XSSFWorkbook wb = null;
		FileInputStream fis = null;
		File f = new File(excelPath);
		try {
			if (f != null) {
				fis = new FileInputStream(f);
				wb = new XSSFWorkbook(fis);
			}
		} catch (Exception e) {
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return wb;
	}
   /**
    * 找到需要插入的行数，并新建一个POI的row对象
    * @param sheet
    * @param rowIndex
    * @return
    */
	private XSSFRow createRow(XSSFSheet sheet, Integer rowIndex) {
		XSSFRow row = null;
		if (sheet.getRow(rowIndex) != null) {
			int lastRowNo = sheet.getLastRowNum();
			sheet.shiftRows(rowIndex, lastRowNo, 1,true,false);
		}
		row = sheet.createRow(rowIndex);
		return row;
	}
 
	
 
}
