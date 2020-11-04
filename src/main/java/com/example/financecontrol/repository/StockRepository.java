package com.example.financecontrol.repository;

import com.example.financecontrol.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock,Integer> {

    Stock getStockById(Integer stockId);

    List<Stock> getStocksByPaymentId(Integer paymentId);

    List<Stock> getStocksBySharerId(Integer sharerId);

}
