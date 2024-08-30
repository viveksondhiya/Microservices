    package com.demo.bse.controller;

    import com.demo.bse.entity.Stock;
    import com.demo.bse.entity.StockResponse;
    import com.demo.bse.service.StockService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/stocks")
    public class StockController {

        @Autowired
        private StockService stockService;

        @GetMapping("/allStocks")
        public List<StockResponse> getAllStocks(){
            return stockService.getAllStocks();
        }

        @PostMapping("/listStock")
        public Stock createStock(@RequestBody Stock stock) {
            return stockService.createStock(stock);
        }

        @GetMapping("/{id}")
        public Stock getStock(@PathVariable Long id) {
            return stockService.getStock(id);
        }

        @PutMapping("/{id}")
        public Stock updateStock(@PathVariable Long id, @RequestBody Stock stock) {
            return stockService.updateStock(id, stock);
        }

        @DeleteMapping("/{id}")
        public void deleteStock(@PathVariable Long id) {
            stockService.deleteStock(id);
        }

        @GetMapping("/batch")
        public List<StockResponse> getStocksByIds(@RequestParam List<Long> ids) {
            return stockService.getStocksByIds(ids);
        }


    }
