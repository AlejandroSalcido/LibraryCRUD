package com.main;

import java.sql.SQLException;

import java.util.List;
import java.util.Scanner;


import com.book.Book;
import com.bookdao.BookDAO;
import com.loandao.LoanDAO;
import com.member.Member;
import com.memberdao.MemberDAO;


public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final MemberDAO memberDAO = new MemberDAO();
    private static final BookDAO bookDAO = new BookDAO();
    private static final LoanDAO loanDAO = new LoanDAO();

    public static void main(String[] args) {
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Member");
            System.out.println("2. Update Member");
            System.out.println("3. Delete Member");
            System.out.println("4. List All Members");
            System.out.println("5. Add Book");
            System.out.println("6. Update Book");
            System.out.println("7. Delete Book");
            System.out.println("8. List All Books");
            System.out.println("9. Loan Book");
            System.out.println("10. Return Book");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        addMember();
                        break;
                    case 2:
                        updateMember();
                        break;
                    case 3:
                        deleteMember();
                        break;
                    case 4:
                        listAllMembers();
                        break;
                    case 5:
                        addBook();
                        break;
                    case 6:
                        updateBook();
                        break;
                    case 7:
                        deleteBook();
                        break;
                    case 8:
                        listAllBooks();
                        break;
                    case 9:
                        loanBook();
                        break;
                    case 10:
                        returnBook();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 10.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void addMember() throws SQLException {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();
        System.out.print("Enter member phone: ");
        String phone = scanner.nextLine();

        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPhone(phone);

        memberDAO.addMember(member);
        System.out.println("Member added successfully.");
    }

    private static void updateMember() throws SQLException {
        System.out.print("Enter member id to update: ");
        int memberId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Member member = memberDAO.getMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        System.out.print("Enter new name (leave blank to keep unchanged): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            member.setName(name);
        }

        System.out.print("Enter new email (leave blank to keep unchanged): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            member.setEmail(email);
        }

        System.out.print("Enter new phone (leave blank to keep unchanged): ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) {
            member.setPhone(phone);
        }

        memberDAO.updateMember(member);
        System.out.println("Member updated successfully.");
    }

    private static void deleteMember() throws SQLException {
        System.out.print("Enter member id to delete: ");
        int memberId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        memberDAO.deleteMember(memberId);
        System.out.println("Member deleted successfully.");
    }

    private static void listAllMembers() throws SQLException {
        List<Member> members = memberDAO.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members found.");
        } else {
            System.out.println("Members:");
            for (Member member : members) {
                System.out.println("ID: " + member.getId() + ", Name: " + member.getName() + ", Email: " + member.getEmail() + ", Phone: " + member.getPhone());
            }
        }
    }

    private static void addBook() throws SQLException {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book publisher: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setIsbn(isbn);

        bookDAO.addBook(book);
        System.out.println("Book added successfully.");
    }

    private static void updateBook() throws SQLException {
        System.out.print("Enter book id to update: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.print("Enter new title (leave blank to keep unchanged): ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) {
            book.setTitle(title);
        }

        System.out.print("Enter new author (leave blank to keep unchanged): ");
        String author = scanner.nextLine();
        if (!author.isEmpty()) {
            book.setAuthor(author);
        }

        System.out.print("Enter new publisher (leave blank to keep unchanged): ");
        String publisher = scanner.nextLine();
        if (!publisher.isEmpty()) {
            book.setPublisher(publisher);
        }

        System.out.print("Enter new ISBN (leave blank to keep unchanged): ");
        String isbn = scanner.nextLine();
        if (!isbn.isEmpty()) {
            book.setIsbn(isbn);
        }

        bookDAO.updateBook(book);
        System.out.println("Book updated successfully.");
    }

    private static void deleteBook() throws SQLException {
        System.out.print("Enter book id to delete: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        bookDAO.deleteBook(bookId);
        System.out.println("Book deleted successfully.");
    }

    private static void listAllBooks() throws SQLException {
        List<Book> books = bookDAO.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Books:");
            for (Book book : books) {
                System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor() +
                        ", Publisher: " + book.getPublisher() + ", ISBN: " + book.getIsbn() + ", Available: " + (book.isAvailable() ? "Yes" : "No"));
            }
        }
    }

    private static void loanBook() throws SQLException {
        System.out.print("Enter member id: ");
        int memberId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter book id: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        loanDAO.loanBook(memberId, bookId);
        System.out.println("Book loaned successfully.");
    }

    private static void returnBook() throws SQLException {
        System.out.print("Enter loan id to return: ");
        int loanId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        loanDAO.returnBook(loanId);
        System.out.println("Book returned successfully.");
    }
}
