package com.example.jpa.controller;

import com.example.jpa.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @RequestMapping("exportExcel")
    public ResponseEntity<byte[]> exportExcel() {
        return excelService.exportExcel();
    }

}
