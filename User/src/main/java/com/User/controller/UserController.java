package com.User.controller;

import com.User.entity.StockResponse;
import com.User.entity.User;
import com.User.entity.UserResponse;
import com.User.entity.UserWithStocksResponse;
import com.User.service.UserService;
import com.User.service.UserStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserStockService userStockService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String clientId) {
        return userService.getUserByClientId(clientId);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") String clientId, @RequestBody User user) {
        return userService.updateUser(clientId, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long clientId) {
        userService.deleteUser(clientId);
    }

    @GetMapping("/{id}/withStocks")
    public UserWithStocksResponse getUserWithStocks(@PathVariable("id") String clientId) {
        return userStockService.getUserWithStocks(clientId);
    }

    @GetMapping("/allStocks")
    public ResponseEntity<?> getAllStocks() {
        try {
            List<StockResponse> stocks = userService.getAllStocks();
            return ResponseEntity.ok(stocks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Both NSE and BSE services are not available.");
        }
    }

}
