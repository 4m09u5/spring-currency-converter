package com.example.currency.repository;

import com.example.currency.model.Currency;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface defines desired methods for database communication relative to Currency entity.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
  List<Currency> findAllByOrderByIdAsc();
  List<Currency> findCurrencyByAbbreviation(String abbreviation);
}
