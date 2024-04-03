package com.example.currency.repository;

import com.example.currency.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface defines desired methods for database communication relative to Rate entity.

 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    public List<Rate> findAllByOrderByIdAsc();
}