package com.example.task.service;

import com.example.task.Repository.DetailsRepository;
import com.example.task.entity.Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DetailsService {

    private DetailsRepository repository;
    @Autowired
    public DetailsService(DetailsRepository repository) {
        this.repository = repository;
    }

    public List<Details> getAllDetails(String supplier){
        System.out.println("getting supploer info");
        return repository.findBySupplier(supplier);

    }

    public List<Details> getDetailsBySupplierAndExp(String supplier, Date exp) {
        return repository.findBySupplierAndExp(supplier,exp);
    }
}
