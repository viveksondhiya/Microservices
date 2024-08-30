package com.User.serviceImpl;

import com.User.entity.*;
import com.User.feign.BseClient;
import com.User.feign.NseClient;
import com.User.repository.UserRepository;
import com.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BseClient bseClient; // Inject Feign Client
    @Autowired
    private NseClient nseClient; // Inject Feign Client

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Map User entities to UserResponse objects
        List<UserResponse> userResponses = users.stream()
                .map(user -> new UserResponse(
                        user.getClientId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getCity(),
                        user.getLastTradeDate()
                ))
                .collect(Collectors.toList());

        return userResponses;

    }

    @Override
    public User getUserByClientId(String clientId) {
        return userRepository.findByClientId(clientId);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String clientId, User user) {
        User existingUser = userRepository.findByClientId(clientId);
        existingUser.setName(user.getName());
        existingUser.setPhone(user.getPhone());
        existingUser.setEmail(user.getEmail());
        existingUser.setCity(user.getCity());
        existingUser.setLastTradeDate(user.getLastTradeDate());
        existingUser.setStockIdsBse(user.getStockIdsBse());
        existingUser.setStockIdsNse(user.getStockIdsNse());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long clientId) {
        userRepository.deleteById(clientId);
    }
    @Override
    public UserWithStocksResponse getUserWithStocks(String clientId) {
        User user = getUserByClientId(clientId);
        UserResponse userSend=new UserResponse();
        userSend.setClientId(user.getClientId());
        userSend.setName(user.getName());
        userSend.setEmail(user.getEmail());
        userSend.setPhone(user.getPhone());
        userSend.setCity(user.getCity());
        userSend.setLastTradeDate(user.getLastTradeDate());

        List<Long> stockIdsBse = user.getStockIdsBse(); // Get stock IDs from user
        List<Long> stockIdsNse = user.getStockIdsNse(); // Get stock IDs from user

        // Fetch stock details from BSE microservice
        List<UserStockResponse> bseStocks = bseClient.getStocksByIds(stockIdsBse);
        // Fetch stock details from NSE microservice
        List<UserStockResponse> nseStocks = nseClient.getStocksByIds(stockIdsNse);

        // Combine the results from BSE and NSE
        List<UserStockResponse> allStocks = new ArrayList<>();
        allStocks.addAll(bseStocks);
        allStocks.addAll(nseStocks);

        return new UserWithStocksResponse(userSend, allStocks);
    }

    @Override
    public List<StockResponse> getAllStocks(){
        List<StockResponse> allStocks=new ArrayList<>();
        // Fetch stock details from BSE microservice
        List<StockResponse> bseStocks = bseClient.getAllStocks();
        // Fetch stock details from NSE microservice
        List<StockResponse> nseStocks = nseClient.getAllStocks();
        // Combine and map the stocks to only include name and price
        allStocks.addAll(bseStocks.stream()
                .map(stock -> new StockResponse(stock.getName(), stock.getPrice()))
                .collect(Collectors.toList()));

        allStocks.addAll(nseStocks.stream()
                .map(stock -> new StockResponse(stock.getName(), stock.getPrice()))
                .collect(Collectors.toList()));
        return allStocks;
    }
}
