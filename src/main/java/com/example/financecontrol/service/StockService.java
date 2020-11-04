package com.example.financecontrol.service;

import com.example.financecontrol.domain.Payment;
import com.example.financecontrol.domain.Stock;
import com.example.financecontrol.domain.User;

import java.util.List;

public interface StockService {

    boolean createStock(Payment paymentId, User sharerId);

    Stock getStockById(Integer stockId);

    boolean updateStock(Integer stockId, Payment paymentId, User sharerId, boolean confirmation);

    Stock deleteStockById(Integer stockId);

    List<Stock> getPaymentSharers(Payment paymentId);

    List<Stock> getUserDebs(User userId);

}
