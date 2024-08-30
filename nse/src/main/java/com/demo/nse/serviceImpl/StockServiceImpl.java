package com.demo.nse.serviceImpl;


import com.demo.nse.entity.Stock;
import com.demo.nse.entity.StockResponse;
import com.demo.nse.repository.StockRepository;
import com.demo.nse.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

//    @Autowired
//    private NseClient nseClient;

    //    @Override
//    public List<Stock> getAllStocks(){
//        return stockRepository.findAll();
//    }
    @Override
    public List<StockResponse> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        // Convert Stock entities to StockResponse objects
        return stocks.stream()
                .map(stock -> new StockResponse(stock.getName(), stock.getPrice(),stock.getQuantity()))
                .collect(Collectors.toList());
    }
    @Override
    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock getStock(Long id) {
        // Try to get stock from BSE, if not found, fetch from NSE
        return stockRepository.findById(id).get();
//                .orElseGet(() -> nseClient.getStockFromNse(id));
    }

    @Override
    public Stock updateStock(Long id, Stock stock) {
        stock.setId(id);
        return stockRepository.save(stock);
    }

    @Override
    public void deleteStock(long id) {
        stockRepository.deleteById(id);
    }

    @Override
    public List<StockResponse> getStocksByIds(List<Long> ids) {
        return stockRepository.findAllById(ids).stream()
                .map(this::convertToStockResponse)
                .collect(Collectors.toList());
    }

    // Helper method to convert Stock to StockResponse
    private StockResponse convertToStockResponse(Stock stock) {
        return new StockResponse(stock.getId(), stock.getName(), stock.getPrice(), stock.getQuantity());
    }


//    @Transactional
//    public Stock buyStock(Long id, int quantity) {
//        Stock stock = stockRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Stock not found"));
//
//        if (stock.getQuantity() < quantity) {
//            throw new RuntimeException("Not enough stock available");
//        }
//
//        stock.setQuantity(stock.getQuantity() - quantity);
//        return stockRepository.save(stock);
//    }
//
//    @Transactional
//    public Stock sellStock(Long id, int quantity) {
//        Stock stock = stockRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Stock not found"));
//
//        stock.setQuantity(stock.getQuantity() + quantity);
//        return stockRepository.save(stock);
//    }
}
