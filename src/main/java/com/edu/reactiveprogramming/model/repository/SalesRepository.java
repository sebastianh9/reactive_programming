package com.edu.reactiveprogramming.model.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.edu.reactiveprogramming.model.Sale;

@Repository
public interface SalesRepository extends R2dbcRepository<Sale, Integer> {

}
