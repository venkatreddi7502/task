package com.example.task.Repository;



import com.example.task.entity.Details;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DetailsRepository extends JpaRepository<Details, Long> {
    List<Details> findBySupplier(String supplier);

    List<Details> findBySupplierAndExp(String supplier, Date exp);
}

