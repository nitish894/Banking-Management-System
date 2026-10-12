package com.nitish.bankingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class MainB {
     public static final String url="jdbc:mysql://localhost:3306/banking_system";
     public static final String userName="root";
     public static final String password="password";

    public static void main(String[] args) {
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            Connection con= DriverManager.getConnection(url,userName,password);
            Scanner scanner=new Scanner(System.in);
            User user=new User(con,scanner);
            Account account=new Account(con,scanner);
            AccountManager accountManager=new AccountManager(con,scanner);

            String email;
            Long account_number;

            while (true){
                System.out.println("WELCOME TO OUR BANKING SYSTEM");
                System.out.println();
                System.out.println("1.Register");
                System.out.println("2.login");
                System.out.println("3.Exit");
                System.out.println("Enter your choice");
                int choice=scanner.nextInt();
                switch (choice){
                    case 1:user.register();
                       break;
                    case 2:
                        email=user.login();
                        if(email != null){
                            System.out.println();
                            System.out.println("user loggedIN");
                            if(!account.account_exist(email)){
                                System.out.println();
                                System.out.println("Choose one options");
                                System.out.println();
                                System.out.println("1.open new bank account");
//                                scanner.nextLine();
                                System.out.println("2.Exit");
                                if(scanner.nextInt()==1){
//                                    System.out.println("Email receive by Main:"+email);
                                    account_number=account.open_account(email);
                                    System.out.println();
                                    System.out.println("Account created successfully");
                                    System.out.println("Your account number:"+account_number);
                                }else{
                                    break;
                                }
                            }
                            account_number=account.getAccount_number(email);
                            int choice2=0;
                            while (choice2 !=5){
                                System.out.println("1.Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3.Transfer Money");
                                System.out.println("4.Check Balance");
                                System.out.println("5. Log Out");
                                System.out.println("Enter your choice:");
                                choice2=scanner.nextInt();
                                switch (choice2){
                                    case 1:
                                        accountManager.debit_money(account_number);
                                        break;
                                    case 2:
                                        accountManager.credit_money(account_number);
                                        break;
                                    case 3:
                                        accountManager.transfer_money(account_number);
                                        break;
                                    case 4:
                                        accountManager.get_balance(account_number);
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Enter valid choice");
                                        break;
                                }
                            }
                        }else{
                            System.out.println("Incorrect email or password");
                        }
                        break;
                    case 3:
                        System.out.println("Thank-you for using my banking System");
                        System.out.println("Exit System!!");
                        break;
                    default:
                        System.out.println("Enter valid choice");
                        break;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
