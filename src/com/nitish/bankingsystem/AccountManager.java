package com.nitish.bankingsystem;

import java.nio.channels.ScatteringByteChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner scanner;

    public AccountManager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    public void credit_money(long account_number) throws SQLException{
        scanner.nextLine();
        System.out.println("Enter your amount");
        Double amount=scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter your security_pin");
        String security_pin=scanner.nextLine();
        try {
            connection.setAutoCommit(false);
          if(account_number != 0){
              PreparedStatement pstm=connection.prepareStatement("SELECT * FROM accounts WHERE" +
                      " account_number=? AND security_pin=?");
              pstm.setLong(1,account_number);
              pstm.setString(2,security_pin);
              ResultSet result=pstm.executeQuery();
              if(result.next()){
                  String credit_query="UPDATE accounts SET balance = balance + ? WHERE account_number=?";
                  PreparedStatement pstm1=connection.prepareStatement(credit_query);
                  pstm1.setDouble(1,amount);
                  pstm1.setLong(2,account_number);
                  int affectedRow=pstm1.executeUpdate();
                  if(affectedRow !=0 ){
                      System.out.println("Rs."+amount+"credited successfully");
                      connection.commit();
                      connection.setAutoCommit(true);
                      return;
                  }else{
                      System.out.println("failed");
                      connection.rollback();
                      connection.setAutoCommit(true);

                  }
              }else{
                  System.out.println("invalid security_pin");
              }
          }
        }catch (SQLException e){

            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }
    public void debit_money(long account_number) throws SQLException{
        scanner.nextLine();
        System.out.println("Enter your amount");
       double amount=scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter your security pin");
        String security_pin=scanner.nextLine();
        try{
            connection.setAutoCommit(false);
            PreparedStatement pstm=connection.prepareStatement
                    ("SELECT * FROM accounts WHERE account_number= ? AND security_pin= ?");
            pstm.setLong(1,account_number);
           pstm.setString(2,security_pin);
           ResultSet result =pstm.executeQuery();
           if(result.next()){
            double current_balance=result.getDouble("balance");
            if(amount <=current_balance){
                String debit_query="UPDATE accounts set balance = balance - ? WHERE account_number = ? ";
                PreparedStatement pstm1=connection.prepareStatement(debit_query);
                pstm1.setDouble(1,amount);
                pstm1.setLong(2,account_number);
                int rowAffected=pstm1.executeUpdate();
                if(rowAffected != 0){
                    System.out.println("Rs."+amount+"debited successfull");
                    connection.commit();
                    connection.setAutoCommit(true);
                    return;
                }else{
                    System.out.println("transaction failed");
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
             }else{
                System.out.println("Insufficient balance");
            }
           }else{
               System.out.println("invalid pin");
           }

        }catch (SQLException e){
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }
    public void transfer_money(long sender_account_number) throws SQLException{
        scanner.nextLine();
        System.out.println("enter receiver account_number:");
        long receiver_account_number=scanner.nextLong();
        System.out.println("Enter Amount:");
        double amount=scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter Security_pin: ");
        String security_pin=scanner.nextLine();
        try{
            connection.setAutoCommit(false);
            if(sender_account_number !=0 && receiver_account_number !=0){
                PreparedStatement pstm=connection.prepareStatement("SELECT * FROM accounts" +
                        " WHERE account_number= ? AND security_pin= ?");
                pstm.setLong(1,sender_account_number);
                pstm.setString(2,security_pin);
                ResultSet result= pstm.executeQuery();
                if(result.next()){
                    double current_balance=result.getDouble("balance");
                    if(amount<=current_balance){
                        String debit_query="UPDATE  accounts SET balance= balance - ? WHERE account_number= ?";
                        String credit_query="UPDATE accounts SET balance= balance + ? WHERE account_number= ?";
                        PreparedStatement debit_pstm=connection.prepareStatement(debit_query);
                        PreparedStatement credit_pstm=connection.prepareStatement(credit_query);
                        debit_pstm.setDouble(1,amount);
                        debit_pstm.setLong(2,sender_account_number);
                        credit_pstm.setDouble(1,amount);
                        credit_pstm.setLong(2,receiver_account_number);
                       int result2= debit_pstm.executeUpdate();
                       int result3=credit_pstm.executeUpdate();
                       if(result2 !=0 && result3 !=0){
                           System.out.println("Transaction successfully");
                           System.out.println("Rs."+amount+"Transferred successfully");
                           connection.commit();
                           connection.setAutoCommit(true);
                           return;
                       }else{
                           System.out.println("Transaction failed");
                           connection.rollback();
                           connection.setAutoCommit(true);
                       }
                    }else{
                        System.out.println("Insufficient amount");
                    }
                }else{
                    System.out.println("Invalid security pin");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }
    public void get_balance(long account_number){
        scanner.nextLine();
        System.out.println("Enter your security_pin: ");
        String security_pin=scanner.nextLine();
        try{
            PreparedStatement pstm=connection.prepareStatement("SELECT balance FROM" +
                    " accounts WHERE account_number=? AND security_pin=?");
            pstm.setLong(1,account_number);
            pstm.setString(2,security_pin);
            ResultSet result= pstm.executeQuery();
            if(result.next()){
                double balance=result.getDouble("balance");
//                System.out.println("current balance:"+balance);
                System.out.printf("Current balance: %.2f%n", balance);
            }else{
                System.out.println("invalid pin");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
