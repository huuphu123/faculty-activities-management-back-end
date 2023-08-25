package com.example.faculty.utils;

import com.example.faculty.entity.EventAttendance;
import com.example.faculty.error.CustomException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<EventAttendance> excelToAttendance(InputStream in, String ID) throws IOException {
        List<EventAttendance> attendances = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(in);

        XSSFSheet sheet = workbook.getSheetAt(0);

        if (sheet.getPhysicalNumberOfRows() <= 1) {
            throw new CustomException("Excel file is empty!");
        }

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i ++) {
            XSSFRow row = sheet.getRow(i);
            EventAttendance attendance = new EventAttendance();
            attendance.setId(ID);

            if (row.getPhysicalNumberOfCells() != 5) {
                throw new CustomException("Excel file is wrong format!");
            }
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                switch (j) {
                    case 0:
                        attendance.setSId(String.valueOf(row.getCell(j)));
                        break;
                    case 1:
                        attendance.setFname(String.valueOf(row.getCell(j)));
                        break;
                    case 2:
                        attendance.setLname(String.valueOf(row.getCell(j)));
                        break;
                    case 3:
                        attendance.setCheckIn((int)row.getCell(j).getNumericCellValue());
                        break;
                    case 4:
                        attendance.setCheckOut((int)row.getCell(j).getNumericCellValue());
                        break;
                    default:
                        throw new CustomException("Excel file is wrong format!");
                }
                attendances.add(attendance);
            }
        }
        return attendances;
    }
}
