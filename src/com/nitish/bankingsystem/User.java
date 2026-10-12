package com.nitish.bankingsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    public void register(){
        scanner.nextInt();
        System.out.print("FullName: ");
        String full_name=scanner.nextLine();
        scanner.nextLine();
        System.out.println("Email: ");
        String email=scanner.nextLine();
        System.out.println("password: ");
        String password=scanner.nextLine();

        if(user_exist(email)){
            System.out.println("user already exist for this email address ");
            return;
        }
        String register_query="INSERT INTO user(full_name,email,password) VALUES (?,?,?)";
        try{
            PreparedStatement pstm=connection.prepareStatement(register_query);
            pstm.setString(1,full_name);
            pstm.setString(2,email);
            pstm.setString(3,password);
           int affectedRow= pstm.executeUpdate();
           if(affectedRow != 0){
               System.out.println("Register successfull");
           }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public String login(){
        scanner.nextLine();
        System.out.println("Enter your email");
        String email=scanner.nextLine();
//        scanner.nextLine();
        System.out.println("Enter your password");
        String password=scanner.nextLine();
        String login_query="SELECT * FROM user WHERE email=? AND password=?";
        try{
            PreparedStatement pstm=connection.prepareStatement(login_query);
            pstm.setString(1,email);
            pstm.setString(2,password);
            ResultSet result= pstm.executeQuery();
            if(result.next()){
                return email;
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean user_exist(String email){
        String query="SELECT * FROM user WHERE email=? ";
       try{
           PreparedStatement pstm=connection.prepareStatement(query);
           pstm.setString(1,email);
           ResultSet result= pstm.executeQuery();
           if(result.next()){
               return true;
           }else {
               return false;
           }
       }catch (SQLException e){
           e.printStackTrace();
       }
       return false;
    }

}
