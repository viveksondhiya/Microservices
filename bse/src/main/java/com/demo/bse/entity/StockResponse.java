package com.demo.bse.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockResponse {
    private String name;
    private Double price;
    private int quantity;

    public StockResponse(Long id, String name,Double price,int quantity) {
         this.name=name;
         this.price=price;
         this.quantity=quantity;
    }
}
