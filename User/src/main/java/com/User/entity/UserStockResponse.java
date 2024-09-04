package com.User.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStockResponse {
    private String symbol;
    private String name;
    private Double price;
    private Integer quantity;
    private String exchange;
}
