package com.example.jpa;

import com.example.jpa.dao.StudentRepository;
import com.example.jpa.dao.TeacherRepository;
import com.example.jpa.pojo.Teacher;
import com.microsoft.schemas.office.visio.x2012.main.CellType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class JpaApplicationTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void getAllByExcel() throws Exception {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new File("C:\\Users\\DataEE\\Downloads\\老师表.xls"));
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

        String name =null;
        int age = 0;
        String studentName =null;

        for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
            XSSFRow row = xssfSheet.getRow(rowNum);
            name = getValue(row.getCell(0));
            age = new Double(getValue(row.getCell(1))).intValue();
            studentName = getValue(row.getCell(2));

            System.out.println(name + " " + age + " " + studentName);
        }

    }

    private static String getValue(XSSFCell xssfCell) {
        if (xssfCell == null) {
            return String.valueOf("0");
        }
        if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfCell.getNumericCellValue());
        } else {
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }

}
