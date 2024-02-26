package com.example.task.controller;

import CsvParser.java.CsvParser;
import com.example.task.Repository.DetailsRepository;
import com.example.task.entity.Details;
import com.example.task.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.xml.xpath.XPathVariableResolver;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.internal.CoreLogging.logger;

@RestController

@RequestMapping("/api/csv")
public class Controller {
    @Autowired
    private DetailsService service;
    @Autowired
    private DetailsRepository repository;
    List<Details> filterData;

    @GetMapping("/get/{supplier}")
    @CrossOrigin(origins = "http://localhost:3000")


    public List<Details> getting(@PathVariable String supplier, @RequestParam(name="exp",required=false)
        @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) Date exp){
        if (exp != null) {
            // If date is provided, filter based on both supplier and date
            filterData = service.getDetailsBySupplierAndExp(supplier, exp);
        }
        else{
            filterData= service.getAllDetails(supplier);
        }
//        filterData= service.getAllDetails(supplier);
        return filterData;
    }

    @GetMapping("/get1/{name}")
    public List<Details> filter(@PathVariable String name){
        System.out.println("yrert"+filterData);
        List<Details> arr = filterData.stream().filter(Details -> Details.getName().equals(name) ).collect(Collectors.toList());
        return arr;
    }


    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file){

        try {

            List<Details> entities = CsvParser.parse(file.getInputStream());
            repository.saveAll(entities);
            return "CSV data uploaded successfully!";
        } catch (IOException e) {
            return "Error uploading CSV: " + e.getMessage();
        }
    }
}
