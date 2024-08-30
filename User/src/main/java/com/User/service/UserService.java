package com.User.service;

import com.User.entity.User;
import com.User.entity.StockResponse;
import com.User.entity.UserResponse;
import com.User.entity.UserWithStocksResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    User getUserByClientId(String clientId);

    User createUser(User user);

    User updateUser(String clientId, User user);

    void deleteUser(Long clientId);

    // Method to get user details along with their stocks
    UserWithStocksResponse getUserWithStocks(String clientId);

    List<StockResponse> getAllStocks();


}
