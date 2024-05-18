package com.sv.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sv.springboot.entity.UserAccount;
import com.sv.springboot.service.UserAccountService;

@RestController
@RequestMapping("/api")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<UserAccount> registerUser(@RequestParam String name,
                                                    @RequestParam String email,
                                                    @RequestParam String mobileNumber) {
        UserAccount newUser = userAccountService.registerUser(name, email, mobileNumber);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Endpoint for deposit
    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestParam String accountNumber,
                                        @RequestParam double amount) {
        userAccountService.deposit(accountNumber, amount);
        return ResponseEntity.ok().build();
    }

    // Endpoint for withdrawal
    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestParam String accountNumber,
                                         @RequestParam double amount) {
        userAccountService.withdraw(accountNumber, amount);
        return ResponseEntity.ok().build();
    }

    // Endpoint for checking balance
    @GetMapping("/balance")
    public ResponseEntity<Double> checkBalance(@RequestParam String accountNumber) {
        double balance = userAccountService.checkBalance(accountNumber);
        return ResponseEntity.ok(balance);
    }
}

