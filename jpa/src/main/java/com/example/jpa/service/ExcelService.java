package com.example.jpa.service;

import org.springframework.http.ResponseEntity;

public interface ExcelService {
    ResponseEntity<byte[]> exportExcel();
}
