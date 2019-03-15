package springboot.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导入导出工具类
 * 
 * @author sony
 *
 */
public class LoadUtils {

	// 导出
	/**
	 * 是否
	 * 
	 * @param result
	 * @return
	 */
	public static String getResult(Object result) {
		if (result == null) {
			return "否";
		}
		if (result.equals(true)) {
			return "是";
		}
		return "否";
	}

	public static void setColumnWidth(Sheet sheet, int start, int end, int width) {
		for (int i = start; i < end; i++) {
			sheet.setColumnWidth(i, (short) width);
		}
	}

	public static void setColumnWidth(HSSFSheet sheet, int start, int end, int width) {
		for (int i = start; i < end; i++) {
			sheet.setColumnWidth(i, (short) width);
		}
	}

	public static void setNullCell(XSSFRow row, int start, int end) {
		for (int i = start; i < end; i++) {
			row.createCell(i).setCellValue("");
		}
	}

	public static String getInfo(Object obj) {
		if (obj != null) {
			return obj.toString().trim();
		}
		return "";
	}

	public static String getDateString(Cell cell) {
		try {
			if (!StringUtils.isEmpty(cell)) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					return cell.getStringCellValue();
				} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					return sdf.format(cell.getDateCellValue());
				}
				if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					short format = cell.getCellStyle().getDataFormat();
					SimpleDateFormat sdf = null;
					if (format == 14 || format == 31 || format == 57 || format == 58) {
						// 日期
						sdf = new SimpleDateFormat("yyyy-MM-dd");
					} else if (format == 20 || format == 32) {
						// 时间
						sdf = new SimpleDateFormat("HH:mm");
					}
					double value = cell.getNumericCellValue();
					Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
					return sdf.format(date);
				}
			}
		} catch (Exception e) {
		}
		return getInfo(cell);
	}

	public static String getCell(Row row, int colIndex) {
		if (row != null) {
			Cell cell = row.getCell(colIndex);
			if (cell != null) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					return cell.getStringCellValue();
				} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					return NumberToTextConverter.toText(cell.getNumericCellValue());
				} else {
					return getInfo(cell);
				}
			}
		}
		return "";
	}

	public static String getCellValue(Sheet sheet, int row, int column) {
		return getCell(sheet.getRow(row), column);
	}

	public static XSSFCellStyle getCellStyleTitile(XSSFWorkbook workbook) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 居中
		XSSFFont font = workbook.createFont();// 字体样式
		font.setBold(true);// 加粗
		font.setFontHeightInPoints((short) 14);// 字体大小
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setFillBackgroundColor(new XSSFColor(java.awt.Color.GREEN));
		return cellStyle;
	}

	public static CellStyle getCellStyleTitile(Workbook workbook, int fontSize, boolean fontBold, boolean border) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 居中
		Font font = workbook.createFont();// 字体样式
		if (fontBold) {
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		}
		font.setFontHeightInPoints((short) fontSize);// 字体大小
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
		if (border) {
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		}
		return cellStyle;
	}

	/**
	 * 
	 * @param workbook
	 * @param fontSize
	 *            字体大小
	 * @param fontBold
	 *            是否粗体
	 * @param wrap
	 *            是否自动换行
	 * @param align
	 *            是否居中
	 * @param border
	 *            是否显示边框
	 * @return
	 */
	public static CellStyle getCellStyle(Workbook workbook, int fontSize, boolean fontBold, boolean wrap, boolean align, boolean border) {
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();// 字体样式
		if (fontBold) {
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		}
		font.setFontHeightInPoints((short) fontSize);// 字体大小
		cellStyle.setFont(font);
		if (align) {
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		}
		// cellStyle.setFillBackgroundColor(HSSFColor.BLACK.index);
		if (wrap) {
			cellStyle.setWrapText(true);// 自动换行
		}
		if (border) {
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		}
		return cellStyle;
	}

	/**
	 * 
	 * @param workbook
	 * @param fontSize
	 *            字体大小
	 * @param fontBold
	 *            是否粗体
	 * @param wrap
	 *            是否自动换行
	 * @param align
	 *            是否居中
	 * @param border
	 *            是否显示边框
	 * @param fontColor
	 *            字体颜色
	 * @return
	 */
	public static HSSFCellStyle getCellStyle(HSSFWorkbook workbook, int fontSize, boolean fontBold, boolean wrap, boolean align, boolean border, short fontColor) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();// 字体样式
		if (fontBold) {
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		}
		font.setFontHeightInPoints((short) fontSize);// 字体大小
		if (fontColor > 0) {
			font.setColor(fontColor);
		}
		cellStyle.setFont(font);
		if (align) {
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		}
		cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
		if (wrap) {
			cellStyle.setWrapText(true);// 自动换行
		}
		if (border) {
			cellStyle.setBorderLeft(HSSFColor.GREEN.index);
			cellStyle.setBorderRight(HSSFColor.GREEN.index);
			cellStyle.setBorderTop(HSSFColor.GREEN.index);
			cellStyle.setBorderBottom(HSSFColor.GREEN.index);
		}
		return cellStyle;
	}

	/**
	 * 
	 * @param workbook
	 * @param fontSize
	 *            字体大小
	 * @param fontBold
	 *            是否粗体
	 * @param wrap
	 *            是否自动换行
	 * @param align
	 *            是否居中
	 * @param vertial
	 *            垂直方向是否居中
	 * @param border
	 *            是否显示边框
	 * @param fontColor
	 *            字体颜色
	 * @return
	 */
	public static HSSFCellStyle getCellStyle(HSSFWorkbook workbook, int fontSize, boolean fontBold, boolean wrap, boolean align, boolean vertial, boolean border, short fontColor) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();// 字体样式
		if (fontBold) {
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		}
		font.setFontHeightInPoints((short) fontSize);// 字体大小
		if (fontColor > 0) {
			font.setColor(fontColor);
		}
		cellStyle.setFont(font);
		if (align) {
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		}
		if (vertial) {
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向居中
		} else {
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 垂直方向靠上
		}
		cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
		if (wrap) {
			cellStyle.setWrapText(true);// 自动换行
		}
		if (border) {
			cellStyle.setBorderLeft(HSSFColor.GREEN.index);
			cellStyle.setBorderRight(HSSFColor.GREEN.index);
			cellStyle.setBorderTop(HSSFColor.GREEN.index);
			cellStyle.setBorderBottom(HSSFColor.GREEN.index);
		}
		return cellStyle;
	}

	/**
	 * 
	 * @param workbook
	 * @param fontSize
	 *            字体大小
	 * @param fontBold
	 *            是否粗体
	 * @param wrap
	 *            是否自动换行
	 * @param align
	 *            是否居中
	 * @param border
	 *            是否显示边框
	 * @param fontColor
	 *            字体颜色
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static XSSFCellStyle getCellStyle(XSSFWorkbook workbook, int fontSize, boolean fontBold, boolean wrap, boolean align, boolean border, short fontColor) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();// 字体样式
		if (fontBold) {
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		}
		font.setFontHeightInPoints((short) fontSize);// 字体大小
		if (fontColor > 0) {
			font.setColor(fontColor);
		}
		cellStyle.setFont(font);
		if (align) {
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		}
		cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
		if (wrap) {
			cellStyle.setWrapText(true);// 自动换行
		}
		if (border) {
			cellStyle.setBorderRight(cellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderLeft(cellStyle.BORDER_THIN);
			cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderTop(cellStyle.BORDER_THIN);
			cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderBottom(cellStyle.BORDER_THIN);
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		}
		return cellStyle;
	}

	/**
	 * 
	 * @param workbook
	 * @param fontSize
	 *            字体大小
	 * @param fontBold
	 *            是否粗体
	 * @param wrap
	 *            是否自动换行
	 * @param align
	 *            是否居中
	 * @param border
	 *            是否显示边框
	 * @param bg
	 *            背景颜色
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static XSSFCellStyle getbgCellStyle(XSSFWorkbook workbook, int fontSize, boolean fontBold, boolean wrap, boolean align, boolean border, short bg) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();// 字体样式
		if (fontBold) {
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		}
		font.setFontHeightInPoints((short) fontSize);// 字体大小
		cellStyle.setFont(font);
		if (align) {
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		}
		cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
		if (wrap) {
			cellStyle.setWrapText(true);// 自动换行
		}
		if (border) {
			cellStyle.setBorderRight(cellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderLeft(cellStyle.BORDER_THIN);
			cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderTop(cellStyle.BORDER_THIN);
			cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderBottom(cellStyle.BORDER_THIN);
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		}
		cellStyle.setFillForegroundColor(bg);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return cellStyle;
	}

	public static HSSFCellStyle getCellStyleBorder(HSSFWorkbook workbook, boolean left, boolean right, boolean top, boolean buttom, short border) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		if (left) {
			cellStyle.setBorderLeft(border);
		}
		if (right) {
			cellStyle.setBorderRight(border);
		}
		if (top) {
			cellStyle.setBorderTop(border);
		}
		if (buttom) {
			cellStyle.setBorderBottom(border);
		}
		return cellStyle;
	}

	public static XSSFCellStyle getCellStyleBorder(XSSFWorkbook workbook, boolean left, boolean right, boolean top, boolean buttom, short border) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		if (left) {
			cellStyle.setBorderLeft(border);
		}
		if (right) {
			cellStyle.setBorderRight(border);
		}
		if (top) {
			cellStyle.setBorderTop(border);
		}
		if (buttom) {
			cellStyle.setBorderBottom(border);
		}
		return cellStyle;
	}

	public static XSSFCellStyle getCellStyleValue(XSSFWorkbook workbook) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 居中
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		XSSFFont font = workbook.createFont();// 字体样式
		font.setFontHeightInPoints((short) 11);// 字体大小
		cellStyle.setWrapText(true);// 自动换行
		cellStyle.setFont(font);
		return cellStyle;
	}

	public static HSSFCellStyle getCellStyleValue(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 居中
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = workbook.createFont();// 字体样式
		font.setFontHeightInPoints((short) 11);// 字体大小
		cellStyle.setWrapText(true);// 自动换行
		cellStyle.setFont(font);
		return cellStyle;
	}

	// public static void setCellStyle(XSSFSheet sheet, XSSFCellStyle cellStyle,
	// int start, int end, int rowIndex) {
	// for (int i = start; i < end; i++) {
	// sheet.getRow(rowIndex).getCell((short) i).setCellStyle(cellStyle);
	// }
	// }

	public static void setCellStyle(Sheet sheet, CellStyle cellStyle, int start, int end, int rowIndex) {
		Row row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		for (int i = start; i < end; i++) {
			Cell cell = row.getCell(i);
			if (cell == null) {
				cell = row.createCell(i);
			}
			cell.setCellStyle(cellStyle);
		}
	}

	public static int setCell(Row row, int cellIndex, Object cellValue) {
		cellIndex++;
		row.createCell(cellIndex).setCellValue(LoadUtils.getInfo(cellValue));
		return cellIndex;
	}

	public static Row getRow(Sheet sheet, int index) {
		if (sheet.getRow(index) != null) {
			return sheet.getRow(index);
		} else {
			return sheet.createRow(index);
		}
	}

	public static Cell getCell(Sheet sheet, int rowIdx, int colIdx) {
		Row row = getRow(sheet, rowIdx);
		if (row.getCell(colIdx) != null) {
			return row.getCell(colIdx);
		} else {
			return row.createCell(colIdx);
		}
	}

	/**
	 * 转换日期格式数据
	 * 
	 * @param map
	 *            转换MAP
	 * @param key
	 *            转换属性名
	 * @param format
	 *            被转化格式
	 */
	public static void formatDate(Map<String, Object> map, String key, String format) {
		if (map.containsKey(key)) {
			if (!StringUtils.isEmpty(map.get(key))) {
				map.put(key, DateUtils.formatDate((Date) map.get(key), format));
			}
		}
	}

	public static int setCell(HSSFRow row, int cellIndex, Map<String, Object> map, List<String> properties) {
		for (String propertie : properties) {
			cellIndex++;
			row.createCell(cellIndex).setCellValue(LoadUtils.getInfo(map.get(propertie)));
		}
		return cellIndex;
	}

	public static int setCell(Sheet sheet, int rowIndex, int columnIndex, List<String> values) {
		Row row = sheet.createRow(rowIndex);
		for (String value : values) {
			row.createCell(columnIndex).setCellValue(LoadUtils.getInfo(value));
			columnIndex++;
		}
		return columnIndex;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> objectToMap(Object object) {
		return (Map<String, Object>) object;
	}

	public static int setCell(HSSFRow row, int cellIndex, int size, Object cellValue) {
		for (int i = 1; i <= size; i++) {
			cellIndex++;
			row.createCell(cellIndex).setCellValue(LoadUtils.getInfo(cellValue));
		}
		return cellIndex;
	}

	public static Cell setCellValue(Sheet sheet, int row, int cell, Object value) {
		Row wbRow = sheet.getRow(row);
		if (wbRow == null) {
			wbRow = sheet.createRow(row);
		}
		Cell wbCell = wbRow.getCell(cell);
		if (wbCell == null) {
			wbCell = wbRow.createCell(cell);
		}
		if (value instanceof Double) {
			wbCell.setCellValue((Double) value);
			return wbCell;
		} else if (value instanceof Date) {
			wbCell.setCellValue((Date) value);
			return wbCell;
		} else if (value instanceof Boolean) {
			wbCell.setCellValue((Boolean) value);
			return wbCell;
		} else {
			wbCell.setCellValue(LoadUtils.getInfo(value));
			return wbCell;
		}
	}

	public static String getBackGroundColorRgb(CellStyle cellStyle) {
		XSSFCellStyle cs = (XSSFCellStyle) cellStyle;
		if (cs.getFillForegroundColorColor() != null && cs.getFillForegroundColorColor().getARGBHex() != null) {
			return "#" + cs.getFillForegroundColorColor().getARGBHex().substring(2);
		}
		return null;
	}

	// public static void setContent(HttpServletResponse response, Workbook
	// workbook, String name) throws IOException {
	// if (workbook != null) {
	// String fileName = new String((name +
	// DateUtils.formatDate(DateUtils.getCurrentDate(),
	// DateUtils.FORMAT_DATETIME_YYYYMMDDHHMMSS) + ".xlsx").getBytes("gb2312"),
	// "iso-8859-1");
	// response.setContentType("application/ms-excel");
	// response.setHeader("Content-Disposition", "attachment;filename=" +
	// fileName);
	// ByteArrayOutputStream os = new ByteArrayOutputStream();
	// workbook.write(os);
	// InputStream is = new ByteArrayInputStream(os.toByteArray());
	// DrmFileEncryptUtil.encryptBasicFile(is, response.getOutputStream());
	// os.flush();
	// os.close();
	// }
	// }

	public static void setContent(HttpServletRequest request, HttpServletResponse response, Workbook workbook, String name) throws IOException {
		if (workbook != null) {
			String fileName = name + DateUtils.formatDate(DateUtils.getCurrentDate(), DateUtils.FORMAT_DATETIME_YYYYMMDDHHMMSS) + ".xlsx";
			// 针对IE或者以IE为内核的浏览器：
			String userAgent = request.getHeader("User-Agent");
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				fileName = urlEncoder(fileName);
			} else {
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			}
			response.setContentType("application/ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setCharacterEncoding("UTF-8");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
		}
	}

	public static void setContent(HttpServletRequest request, HttpServletResponse response, Workbook workbook, String name, String type) throws IOException {
		if (workbook != null) {
			String fileName = name + DateUtils.formatDate(DateUtils.getCurrentDate(), DateUtils.FORMAT_DATETIME_YYYYMMDDHHMMSS) + "." + type;
			String userAgent = request.getHeader("User-Agent");
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				fileName = urlEncoder(fileName);
			} else {
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			}
			response.setContentType("application/ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setCharacterEncoding("UTF-8");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
		}
	}

	public static void setContent(HttpServletRequest request, HttpServletResponse response, File file, String name) throws IOException {
		response.setContentType("application/octet-stream");
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			name = urlEncoder(name);
		} else {
			name = new String(name.getBytes("UTF-8"), "iso-8859-1");
		}
		response.setHeader("Content-Disposition", "attachment;filename=" + name);
		response.setCharacterEncoding("UTF-8");
		FileInputStream fiptS = new FileInputStream(file);
		OutputStream ouputStream = response.getOutputStream();
		try {
			int c;
			while ((c = fiptS.read()) != -1) {
				ouputStream.write(c);
			}
			ouputStream.flush();
		} finally {
			if(null != fiptS){
				fiptS.close();
			}
			if(null != ouputStream){
				ouputStream.close();
			}
		}

	}

	public static void viewContent(HttpServletRequest request, HttpServletResponse response, File file, String name) throws IOException {
		String suffix = name.substring(name.lastIndexOf(".")).toLowerCase();
		if (!".pdf".equalsIgnoreCase(suffix)) {
			response.setContentType("application/octet-stream");
			String userAgent = request.getHeader("User-Agent");
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				name = urlEncoder(name);
			} else {
				name = new String(name.getBytes("UTF-8"), "iso-8859-1");
			}
			response.setHeader("Content-Disposition", "attachment;filename=" + name);
			response.setCharacterEncoding("UTF-8");
		}

		FileInputStream fiptS = new FileInputStream(file);
		OutputStream ouputStream = response.getOutputStream();
		try {
			int c;
			while ((c = fiptS.read()) != -1) {
				ouputStream.write(c);
			}
			ouputStream.flush();
		} finally {
			if(null != fiptS){
				fiptS.close();
			}
			if(null != ouputStream){
				ouputStream.close();
			}
		}

	}


	/**
	 * 
	 * @param sheet
	 * @param startRow
	 *            开始行
	 * @param endRow
	 *            结束行
	 * @param startCol
	 *            开始列
	 * @param endCol
	 *            结束列
	 */
	public static void addMergedRegion(Sheet sheet, int startRow, int endRow, int startCol, int endCol) {
		sheet.addMergedRegion(new CellRangeAddress(startRow, (short) endRow, startCol, (short) endCol));
	}

	public static String urlDecoder(String str) {
		try {
			if (!StringUtils.isEmpty(str)) {
				return URLDecoder.decode(str, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static Object urlDecoder(Object str) {
		try {
			if (!StringUtils.isEmpty(str)) {
				return URLDecoder.decode(str.toString(), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String urlEncoder(String str) {
		try {
			if (!StringUtils.isEmpty(str)) {
				return URLEncoder.encode(str, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static void formartArray(Object value, StringBuffer result) {
		if (!StringUtils.isEmpty(value)) {
			String[] values = value.toString().split(",");
			for (String str : values) {
				result.append("'" + str + "',");
			}
			result.deleteCharAt(result.length() - 1);
		}
	}

	public static String sortString(String str) {
		String[] strs = str.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < strs.length; i++) {
			list.add(Integer.valueOf(strs[i]));
		}
		Collections.sort(list);
		str = "";
		for (Integer value : list) {
			str += value + ",";
		}
		return str.substring(0, str.length() - 1);
	}

	public static boolean matchStr(String content, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(content);
		return match.matches();
	}

	/**
	 * 集团车型年获取
	 * 
	 * @param date
	 *            年月日
	 * @param formatResult
	 *            返回值类型
	 * @param last
	 *            减去月份
	 * @return startMY：车型开始年月份yyyy-MM,endMY:车型结束月份yyyy-MM,MY:年份
	 */
	public static Map<String, String> getBlocMY(Date date, String formatResult, Integer last) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (last != null) {
			c.add(Calendar.MONTH, -last);
		}
		Calendar myStart = Calendar.getInstance();
		Calendar myEnd = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		String startMY = null;
		String endMY = null;
		String my = null;
		if (month >= 7) {
			my = c.get(Calendar.YEAR) + 1 + "";
			myStart.set(Calendar.YEAR, c.get(Calendar.YEAR));
			myStart.set(Calendar.MONTH, 6);
			myEnd.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
			myEnd.set(Calendar.MONTH, 5);
			startMY = DateUtils.formatDate(myStart.getTime(), formatResult);
			endMY = DateUtils.formatDate(myEnd.getTime(), formatResult);
		} else if (month <= 6) {
			my = c.get(Calendar.YEAR) + "";
			myStart.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
			myStart.set(Calendar.MONTH, 6);
			myEnd.set(Calendar.YEAR, c.get(Calendar.YEAR));
			myEnd.set(Calendar.MONTH, 5);
			startMY = DateUtils.formatDate(myStart.getTime(), formatResult);
			endMY = DateUtils.formatDate(myEnd.getTime(), formatResult);
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put(START_MY, startMY);
		result.put(END_MY, endMY);
		result.put(YEAR_MY, my);

		return result;

	}

	public static final String START_MY = "startMY";
	public static final String END_MY = "endMY";
	public static final String YEAR_MY = "MY";

	public static final String REGEX_PRODUCT_NO = "^[\u4e00-\u9fa5_a-zA-Z0-9]{2} [0-9]{7}$";

	public static final String REGEX_STR = "^[\u4e00-\u9fa5_a-zA-Z0-9()（）]*$";

	public static final String REGEX_EVALUATION_RESULT = "^-?([0-9]*.)?[0-9]{0,10}$";

	public static int RGB(int r, int g, int b) {
		return r << 16 | g << 8 | b;
	}

	public static void main(String[] args) {
		int r, g, b, c;
		r = 255;
		g = 255;
		b = 255;
		c = RGB(r, g, b);
		System.out.println("0x%06x" + c);
	}

}
