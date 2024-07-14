package com.loandao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.databaseconnector.DatabaseConnector;
import com.loan.Loan;

public class LoanDAO {
    private Connection connection;

    public LoanDAO() throws SQLException {
        connection = DatabaseConnector.getConnection();
    }

    public void loanBook(int memberId, int bookId) throws SQLException {
        String sql = "INSERT INTO loans(member_id, book_id, loan_date) VALUES (?, ?, ?)";
        LocalDate loanDate = LocalDate.now();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, memberId);
            preparedStatement.setInt(2, bookId);
            preparedStatement.setDate(3, Date.valueOf(loanDate));
            preparedStatement.executeUpdate();
        }
    }

    public void returnBook(int loanId) throws SQLException {
        String sql = "UPDATE loans SET return_date = ? WHERE id = ?";
        LocalDate returnDate = LocalDate.now();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, Date.valueOf(returnDate));
            preparedStatement.setInt(2, loanId);
            preparedStatement.executeUpdate();
        }
    }

    public List<Loan> getAllLoans() throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Loan loan = new Loan();
                loan.setId(rs.getInt("id"));
                loan.setMemberId(rs.getInt("member_id"));
                loan.setBookId(rs.getInt("book_id"));
                loan.setLoanDate(rs.getDate("loan_date").toLocalDate());
                if (rs.getDate("return_date") != null) {
                    loan.setReturnDate(rs.getDate("return_date").toLocalDate());
                }
                loans.add(loan);
            }
        }
        return loans;
    }
}
