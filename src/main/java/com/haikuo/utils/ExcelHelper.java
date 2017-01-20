package com.haikuo.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelHelper {

	/**
	 * 创建excel并返回字节数组
	 * @param rowDatas
	 * @param titles
	 * @param rowMapper
	 * @return
	 * @throws IOException
	 */
	public static <T> byte[] getExcelBytes(List<T> rowDatas,String[] titles,RowMapper<T> rowMapper) throws IOException{
		return getExcelBytes(rowDatas, titles, rowMapper, "sheet1");
	}
	
	/**
	 * 创建excel并返回字节数组
	 * @param rowDatas 数据
	 * @param titles　标题
	 * @param rowMapper　行数据映射器
	 * @param sheetName　工作薄名称
	 * @return　excel表格的字节数组
	 * @throws IOException
	 */
	public static <T> byte[] getExcelBytes(List<T> rowDatas,String[] titles,RowMapper<T> rowMapper,String sheetName) 
			throws IOException{
		Workbook book = createExcel(rowDatas,titles,rowMapper,sheetName);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		book.write(out);
		return out.toByteArray();
	}
	
	/**
	 * 创建excel，并存储到本地路径
	 * @param filePath 文件存储路径
	 * @param rowDatas　数据
	 * @param titles　标题
	 * @param rowMapper　行数据对应关系处理类
	 * @throws Exception
	 */
	public <T> void createExcelSheet(String filePath,List<T> rowDatas,String[] titles,RowMapper<T> rowMapper) throws Exception{
		Workbook wb = createExcel(rowDatas,titles,rowMapper);
		FileOutputStream fos = new FileOutputStream(filePath);
		wb.write(fos);
		fos.close();
	}
	
	/**
	 * 创建excel
	 * @param rowDatas
	 * @param titles
	 * @param rowMapper
	 * @return
	 */
	public static <T> Workbook createExcel(List<T> rowDatas,String[] titles,RowMapper<T> rowMapper){
		return createExcel(rowDatas,titles,rowMapper,"sheet1");
	}
	
	/**
	 * 创建excel
	 * @param rowDatas
	 * @param titles
	 * @param rowMapper
	 * @param sheetName
	 * @return
	 */
	public static <T> Workbook createExcel(List<T> rowDatas,String[] titles,RowMapper<T> rowMapper,String sheetName){
		Workbook book = new HSSFWorkbook();
		Sheet sheet = book.createSheet(sheetName);
		createTitle(book,sheet,titles);
		createRows(sheet,rowDatas,rowMapper);
		return book;
	}
	
	/**
	 * 创建Excel
	 * @param rowDatas 字符数组形式行数据
	 * @param titles　
	 * @param sheetName
	 * @return
	 */
	public static Workbook createExcel(List<String[]> rowDatas,String[] titles,String sheetName){
		Workbook book = new HSSFWorkbook();
		Sheet sheet = book.createSheet(sheetName);
		createTitle(book,sheet,titles);
		createRows(sheet,rowDatas);
		return book;
	}
	
	/**
	 * 创建Excel
	 * @param rowDatas 字符数组形式行数据
	 * @param titles　
	 * @param sheetName
	 * @return
	 */
	public static Workbook createExcelSheet(Workbook workBook,List<String[]> rowDatas,String[] titles,String sheetName){
		Workbook wk = null;
		if(workBook != null){
			wk = workBook;
		}else{
			wk = new HSSFWorkbook();
		}
		Sheet sheet = wk.createSheet(sheetName);
		createTitle(wk,sheet,titles);
		createRows(sheet,rowDatas);
		return wk;
	}
	
	/**
	 * 创建行数据
	 * */
	private static <T> void createRows(Sheet sheet,List<T> rowDatas,RowMapper<T> rowMapper){
		int i = 1;
		for (T rowData : rowDatas) {
			String[] data = rowMapper.mapperRow(rowData);
			if(data != null){
				Row row = sheet.createRow(i++);
				for (int j = 0; j < data.length; j++) {
					Cell cell = row.createCell(j);
					cell.setCellValue(data[j]);
				}
			}
		}
	}

	/**
	 * 创建行数据
	 * */
	private static void createRows(Sheet sheet,List<String[]> rowDatas){
		int i = 1;
		for (String[] rowData : rowDatas) {
			if(rowData != null){
				Row row = sheet.createRow(i++);
				for (int j = 0; j < rowData.length; j++) {
					Cell cell = row.createCell(j);
					cell.setCellValue(rowData[j]);
				}
			}
		}
	}
	
	/**
	 * 创建表头
	 * **/
	private static void createTitle(Workbook book,Sheet sheet,String[] titles){
		Row titleRow = sheet.createRow(0);// 标题行
		CellStyle titleCellStyle = book.createCellStyle();
		Font font = book.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		for(int i = 0; i < titles.length;i++){
			Cell cell = titleRow.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(titleCellStyle);
		}
	}
	

	public static interface RowMapper<T> {
		/**
		 * 根据查询的数据获取Excel映射行数据
		 * **/
		public String[] mapperRow(T rowData);
	}
	
    public static String parseExcel(Cell cell) {  
        String result = new String();  
        switch (cell.getCellType()) {  
        case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型  
            if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
                SimpleDateFormat sdf = null;  
                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
                        .getBuiltinFormat("h:mm")) {  
                    sdf = new SimpleDateFormat("HH:mm");  
                } else {// 日期  
                    sdf = new SimpleDateFormat("yyyy-MM-dd");  
                }  
                Date date = cell.getDateCellValue();  
                result = sdf.format(date);  
            } else if (cell.getCellStyle().getDataFormat() == 58) {  
                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                double value = cell.getNumericCellValue();  
                Date date = org.apache.poi.ss.usermodel.DateUtil  
                        .getJavaDate(value);  
                result = sdf.format(date);  
            } else {  
//                double value = cell.getNumericCellValue();  
//                CellStyle style = cell.getCellStyle();  
//                DecimalFormat format = new DecimalFormat();  
//                String temp = style.getDataFormatString();  
//                // 单元格设置成常规  
//                if (temp.equals("General")) {  
//                    format.applyPattern("#");  
//                }  
                result = cell.getStringCellValue();  
            }  
            break;  
        case HSSFCell.CELL_TYPE_STRING:// String类型  
            result = cell.getRichStringCellValue().toString();  
            break;  
        case HSSFCell.CELL_TYPE_BLANK:  
            result = "";  
        default:  
            result = "";  
            break;  
        }  
        return result;  
    }  
}
