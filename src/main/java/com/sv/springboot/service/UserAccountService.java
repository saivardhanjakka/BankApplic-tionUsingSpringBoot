package com.sv.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sv.springboot.entity.UserAccount;
import com.sv.springboot.repository.UserAccountRepository;

import java.util.Optional;
import java.util.Random;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    // Method for user registration
    @Transactional
    public UserAccount registerUser(String name, String email, String mobileNumber) {
        // Check if the email or mobile number is already registered
        if (userAccountRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email is already registered");
        }
        if (userAccountRepository.findByMobileNumber(mobileNumber) != null) {
            throw new RuntimeException("Mobile number is already registered");
        }

        // Generate a random password
        String password = generateRandomPassword(10);

        // Generate a random account number
        String accountNumber = generateRandomAccountNumber(10);

        // Create a new user account
        UserAccount userAccount = new UserAccount(name, email, mobileNumber, password, accountNumber, 0.0);
        return userAccountRepository.save(userAccount);
    }

    // Method for deposit
    @Transactional
    public void deposit(String accountNumber, double amount) {
        UserAccount userAccount = findUserAccountByAccountNumber(accountNumber);
        userAccount.deposit(amount);
        userAccountRepository.save(userAccount);
    }

    // Method for withdrawal
    @Transactional
    public void withdraw(String accountNumber, double amount) {
        UserAccount userAccount = findUserAccountByAccountNumber(accountNumber);
        userAccount.withdraw(amount);
        userAccountRepository.save(userAccount);
    }

    // Method to check balance
    @Transactional(readOnly = true)
    public double checkBalance(String accountNumber) {
        UserAccount userAccount = findUserAccountByAccountNumber(accountNumber);
        return userAccount.getBalance();
    }

    // Helper method to find UserAccount by account number
    private UserAccount findUserAccountByAccountNumber(String accountNumber) {
        return Optional.ofNullable(userAccountRepository.findByAccountNumber(accountNumber))
                .orElseThrow(() -> new RuntimeException("User account not found"));
    }

    // Helper method to generate a random password
 // Helper method to generate a random password
    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    // Helper method to generate a random account number
    private String generateRandomAccountNumber(int length) {
        String digits = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(digits.length());
            sb.append(digits.charAt(index));
        }
        return sb.toString();
    }

}
