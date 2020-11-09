package com.example.financecontrol.service.implementations;

import com.example.financecontrol.domain.Payment;
import com.example.financecontrol.domain.Stock;
import com.example.financecontrol.domain.User;
import com.example.financecontrol.repository.StockRepository;
import com.example.financecontrol.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public boolean createStock(Payment paymentId, User sharerId) {
        Stock stock = new Stock(paymentId,sharerId);
        stockRepository.save(stock);
        return true;
    }

    @Override
    public Stock getStockById(Integer stockId) {
        Stock stock = stockRepository.getStockById(stockId);
        if(stock == null)
            return null;
        return stock;
    }

    @Override
    public boolean updateStock(Integer stockId, Payment payment, User sharer, boolean confirmation) {
        Stock stock = stockRepository.getStockById(stockId);
        if(stock == null)
            return false;
        stock.setPayment(payment);
        stock.setSharer(sharer);
        stock.setConfirmation(confirmation);
        stockRepository.save(stock);
        return true;
    }

    @Override
    public Stock deleteStockById(Integer stockId) {
        Stock stock = stockRepository.getStockById(stockId);
        if(stock == null)
            return null;
        stockRepository.delete(stock);
        return stock;
    }

    @Override
    public List<Stock> getPaymentById(Payment payment) {
        try {
            return stockRepository.getStocksByPaymentId(payment.getId());
        }
        catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public List<Stock> getUserDebs(User sharer) {
        try {
            return stockRepository.getStocksBySharerId(sharer.getId());
        }catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public String getUserSpendMoney(User authenticatedUser){
        double spendMoney = 0.0;
        try {
            List<Stock> userDebsList = getUserDebs(authenticatedUser);
            for (Stock stock: userDebsList){
                List<Stock> paymentSharersList = getPaymentById(stock.getPayment());
                spendMoney += stock.getPayment().getAmount() / paymentSharersList.size();
            }
            DecimalFormat decimalFormat = new DecimalFormat("###.##");
            return decimalFormat.format(spendMoney);
        }catch (NullPointerException e){
            return null;
        }

    }
}
