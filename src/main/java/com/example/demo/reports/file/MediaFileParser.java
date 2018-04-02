package com.example.demo.reports.file;

import com.example.demo.reports.entity.MediaFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javafunk.excelparser.SheetParser;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MediaFileParser {
    public List<MediaFile> parse(String fileName) throws IOException {
        log.info("parse for media started");
        List<MediaFile> mediaFileList;
        try {
            FileInputStream fis = new FileInputStream(fileName);

            Workbook workbook = null;
            if (fileName.toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileName.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            }

            Sheet sheet = workbook != null ? workbook.getSheetAt(0) : null;
            SheetParser parser = new SheetParser();
            List<MediaFile> sectionList = parser.createEntity(sheet, MediaFile.class, error -> {
                throw error;
            });
            mediaFileList = new ArrayList<>(sectionList);

        } catch (IOException e) {
            throw new IOException("Error during parsing file");
        }
        return mediaFileList;
    }

}
