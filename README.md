# Banking Management System

A console-based Banking Management System built using Java and MySQL. The project demonstrates JDBC connectivity, database operations, account management, and transaction handling.

## Features

- User registration and login
- Account authentication using a security PIN
- Debit money
- Credit money
- Transfer money between accounts
- Check account balance
- MySQL database integration
- SQL queries using JDBC PreparedStatement

## Technologies Used

- **Language:** Java
- **Database:** MySQL
- **Database Connectivity:** JDBC
- **IDE:** IntelliJ IDEA
- **Database Driver:** MySQL Connector/J

## Project Structure

```text
Banking_System/
├── src/
│   └── com/
│       └── nitish/
│           └── bankingsystem/
│               └── MainB.java
├── README.md
└── .gitignore
```

*Note: Update this structure to match your actual project files and classes.*

## Requirements

- JDK 23 or a compatible JDK
- MySQL Server
- IntelliJ IDEA
- MySQL Connector/J

## Installation and Setup

1. Clone this repository:

   ```bash
   git clone https://github.com/nitish894/Banking-Management-System.git
   ```

2. Open the project in IntelliJ IDEA.

3. Create the required MySQL database and tables using your project's SQL schema.

4. Configure the database connection in your Java project using your local MySQL username, password, and database URL.

5. Add MySQL Connector/J to your project dependencies.

6. Run the `MainB` class to start the application.

## Database Configuration

Configure the JDBC connection using your local MySQL settings.

Example JDBC URL:

```text
jdbc:mysql://localhost:3306/banking_system
```

Replace `YOUR_DATABASE_NAME` with your actual database name.

**Security:** Never commit database passwords, account credentials, or security PINs to the repository.

## Screenshots

Screenshots of the application can be added here to demonstrate registration, login, balance checking, and successful transactions.

Example Markdown syntax:

```markdown
![Banking System Console](screenshots/banking-system.png)
```

## Learning Outcomes

- Object-oriented programming in Java
- JDBC and PreparedStatement
- MySQL CRUD operations
- SQL queries and database integration
- Transaction management and error handling

## Future Enhancements

- Password and security PIN hashing
- Improved input validation
- Automated unit testing
- Graphical user interface
- Transaction history

## Author

**Nitish Kumar Mahato**

## License

This project is intended for educational purposes. Add an open-source license if you want to permit others to reuse or modify the code.