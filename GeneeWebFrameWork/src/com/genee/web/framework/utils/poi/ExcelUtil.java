package com.genee.web.framework.utils.poi;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.genee.web.module.constants.ExcelType;

public class ExcelUtil {
	private ExcelUtil() {
	}

	/**
	 * 生成Excel
	 * 
	 * @param type
	 *            文件类型
	 *
	 * @return
	 */
	public static Workbook buildExcel(ExcelType type,
			List<Map<String, Object>> topHeaders,
			List<Map<String, Object>> midHeaders,
			List<Map<String, Object>> contents) {

		Workbook workbook = null; // 创建新的Excel工作薄
		Sheet sheet = null;

		if (type.compareTo(ExcelType.XLS) == 0) {// 2003版
			workbook = new HSSFWorkbook();
		} else {
			workbook = new XSSFWorkbook();// 默认2007及以上版
		}

		// 判断workbook中是否有sheet
		if (workbook.getNumberOfSheets() > 0) {
			// 获取sheet
			sheet = workbook.getSheetAt(0);
		} else {
			// 创建新sheet
			sheet = workbook.createSheet();
		}

		// 冻结窗格(从列A到行2)
		sheet.createFreezePane(0, 2);
		sheet.setDefaultColumnWidth(15); // 设置默认宽度

		Row topRow = null;
		topRow = sheet.createRow(0); // 创建首行
		topRow.setHeightInPoints(18); // 设置行高

		CellStyle headerStyle = workbook.createCellStyle(); // 样式

		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);// 设置居中

		Font font = workbook.createFont();// 设置字体
		font.setColor(IndexedColors.AQUA.getIndex()); // 字体颜色
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 字重
		font.setFontHeightInPoints((short) 12); // 字体大小
		headerStyle.setFont(font);

		// 插入一级标题
		int topHeaderIndex = 0;
		for (Map<String, Object> header : topHeaders) {
			Cell cell = topRow.createCell(topHeaderIndex);

			// 计算需要合并的表头的开始列和结束列
			int headerStart = topHeaderIndex;
			int headerEnd = topHeaderIndex
					+ Integer.parseInt(header.get("ccount").toString()) - 1;

			// 合并单元格
			sheet.addMergedRegion(new CellRangeAddress(0, 0, headerStart,
					headerEnd));

			// 单元格计数
			topHeaderIndex += Integer.parseInt(header.get("ccount").toString());

			// 应用样式
			cell.setCellStyle(headerStyle);

			// 插入值
			cell.setCellValue(header.get("name").toString());
		}

		Row midRow = null;
		midRow = sheet.createRow(1); // 创建二级标题行
		midRow.setHeightInPoints(18); // 设置行高

		// 插入二级标题
		int midHeaderIndex = 0;
		for (Map<String, Object> header : midHeaders) {
			Cell cell = midRow.createCell(midHeaderIndex);

			// 应用样式
			cell.setCellStyle(headerStyle);

			// 插入值
			cell.setCellValue(header.get("name").toString());
			
			midHeaderIndex ++;
		}

		// 插入数据
		int rowIndex = 2;
		for (Map<String, Object> contentMap : contents) {
			Row contentRow = sheet.createRow(rowIndex);

			int cellIndex = 0;
			// 循环判断字段名称是否对应
			for (Map<String, Object> headerMap : midHeaders) {
				for (Map.Entry<String, Object> contentEntry : contentMap.entrySet()) {
					if (contentEntry.getKey().equals(
							headerMap.get("code").toString())) {

						Cell cell = contentRow.createCell(cellIndex);
						cell.setCellValue(contentEntry.getValue().toString());
						cellIndex++;
						break;
					}
				}
			}
			rowIndex++;
		}

		return workbook;
	}
}