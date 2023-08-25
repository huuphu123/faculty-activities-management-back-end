package com.example.faculty.utils;

import com.example.faculty.entity.EventAttendance;
import com.example.faculty.entity.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<EventAttendance> eventAttendanceList;

    public ExcelExporter(List<EventAttendance> eventAttendanceList) {
        this.eventAttendanceList = eventAttendanceList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Event Attendance");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(13);
        style.setFont(font);

        createCell(row, 0, "NO", style);
        createCell(row, 1, "Student ID", style);
        createCell(row, 2, "First Name", style);
        createCell(row, 3, "Last Name", style);
        createCell(row, 4, "Check In", style);
        createCell(row, 5, "Check Out", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;
        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        cellStyle.setFont(font);

        for (EventAttendance attendance : this.eventAttendanceList) {
            Row row = sheet.createRow(rowCount);
            int columnCount = 0;

            createCell(row, columnCount++, rowCount, cellStyle);
            createCell(row, columnCount++, attendance.getSId(), cellStyle);
            createCell(row, columnCount++, attendance.getFname(), cellStyle);
            createCell(row, columnCount++, attendance.getLname(), cellStyle);
            createCell(row, columnCount++, attendance.getCheckIn(), cellStyle);
            createCell(row, columnCount++, attendance.getCheckOut(), cellStyle);

            rowCount ++;

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
