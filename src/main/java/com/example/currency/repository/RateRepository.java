package com.example.currency.repository;

import com.example.currency.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    public List<Rate> findAllByOrderByIdAsc();
}