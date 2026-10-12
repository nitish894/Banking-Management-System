package com.nitish.bankingsystem;

import java.sql.*;
import java.util.Scanner;

public class Account {
    private final Connection connection;
    private final Scanner scanner;

    public Account(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    public long open_account(String email){
        if(email== null || email.isBlank()){
            throw new IllegalArgumentException("email cannot be null");
        }
        if(!account_exist(email)){
            String query="INSERT INTO accounts(account_number,full_name," +
                    "email,balance,security_pin) VALUES (?,?,?,?,?)";
            scanner.nextInt();
            System.out.println("Enter your full_name");
            String full_name=scanner.nextLine();
            scanner.nextLine();
            System.out.println("Enter your initial Amount ");
            double balance=scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter your security_pin");
            String security_pin=scanner.nextLine();
            try{
                long account_number=generateAccountNumber();
                PreparedStatement pstm=connection.prepareStatement(query);
                pstm.setLong(1,account_number);
                pstm.setString(2,full_name);
                pstm.setString(3,email);
                pstm.setDouble(4,balance);
                pstm.setString(5,security_pin);
                int result= pstm.executeUpdate();
                if(result != 0){
                return account_number;
                }else {
                    throw new RuntimeException("Account creation failed");
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Account already exist");
    }
    public long getAccount_number(String email){
        String query = "SELECT account_number FROM accounts WHERE email = ?";
       try{
           PreparedStatement pstm=connection.prepareStatement(query);
           pstm.setString(1,email);
          ResultSet result= pstm.executeQuery();
          if(result.next()){
              return result.getLong("account_number");
          }
       }catch (SQLException e){
           e.printStackTrace();
       }
       throw new RuntimeException("Account_number doesn't exists!!");
    }
    private long generateAccountNumber(){
        try (Statement stm = connection.createStatement()) {
            try (ResultSet result = stm.executeQuery("SELECT account_number FROM " +
                    "Accounts ORDER BY account_number DESC LIMIT 1")) {
                if(result.next()){
                    long last_account_number=result.getLong("account_number");
                    return last_account_number+1;
                }else {
                    return 10000100;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 10000100;
    }
    public boolean account_exist(String email) {
        String query = "SELECT account_number FROM accounts WHERE email=?";
        try{
            PreparedStatement pstm=connection.prepareStatement(query);
            pstm.setString(1,email);
            ResultSet result=pstm.executeQuery();
            return result.next();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
