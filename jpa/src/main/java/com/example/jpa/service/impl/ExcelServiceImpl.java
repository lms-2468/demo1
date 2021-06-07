package com.example.jpa.service.impl;

import com.example.jpa.controller.BaseFrontController;
import com.example.jpa.dao.TeacherRepository;
import com.example.jpa.pojo.Student;
import com.example.jpa.pojo.Teacher;
import com.example.jpa.service.ExcelService;
import com.example.jpa.util.ExcelFormatUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private TeacherRepository teacherRepository;


    Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);


    @Override
    public ResponseEntity<byte[]> exportExcel() {
        try {
            logger.info(">>>>>>>>>>开始导出excel>>>>>>>>>>");

            List<Teacher> list = teacherRepository.findAll();

            BaseFrontController baseFrontController = new BaseFrontController();

            return baseFrontController.buildResponseEntity(export((List<Teacher>) list), "老师表.xls");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(">>>>>>>>>>导出excel 异常，原因为：" + e.getMessage());
        }
        return null;
    }

    private InputStream export(List<Teacher> list) {
        logger.info(">>>>>>>>>>>>>>>>>>>>开始进入导出方法>>>>>>>>>>");
        ByteArrayOutputStream output = null;
        InputStream inputStream1 = null;
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);// 保留1000条数据在内存中
        SXSSFSheet sheet = wb.createSheet();
        // 设置报表头样式
        CellStyle header = ExcelFormatUtil.headSytle(wb);// cell样式
        CellStyle content = ExcelFormatUtil.contentStyle(wb);// 报表体样式

        // 每一列字段名
        String[] strs = new String[]{"姓名", "性别", "所带学生"};

        // 字段名所在表格的宽度
        int[] ints = new int[]{5000, 5000, 5000};

        // 设置表头样式
        ExcelFormatUtil.initTitleEX(sheet, header, strs, ints);
        logger.info(">>>>>>>>>>>>>>>>>>>>表头样式设置完成>>>>>>>>>>");

        if (list != null && list.size() > 0) {
            logger.info(">>>>>>>>>>>>>>>>>>>>开始遍历数据组装单元格内容>>>>>>>>>>");
            for (int i = 0; i < list.size(); i++) {
                Teacher teacher = list.get(i);
                SXSSFRow row = sheet.createRow(i + 1);
                int j = 0;

                SXSSFCell cell = row.createCell(j++);
                cell.setCellValue(teacher.getName()); // 姓名
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(teacher.getAge()); // 年龄
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                List<Student> students = teacher.getStudents();
                String studentName = "";
                for (int s = 0; s < students.size(); s++) {
                    if (s == students.size() - 1) {
                        studentName += students.get(s).getName();
                        break;
                    }

                    studentName += students.get(s).getName() + ",";
                }
                cell.setCellValue(studentName); // 该老师所带学生
                cell.setCellStyle(content);
            }
            logger.info(">>>>>>>>>>>>>>>>>>>>结束遍历数据组装单元格内容>>>>>>>>>>");
        }
        try {
            output = new ByteArrayOutputStream();
            wb.write(output);
            inputStream1 = new ByteArrayInputStream(output.toByteArray());
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                    if (inputStream1 != null)
                        inputStream1.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream1;
    }
}
