package main.java.com.library.server.handler;

import main.java.com.library.server.handler.requestHandler.*;
import main.java.com.library.server.model.Book;
import main.java.com.library.server.model.BorrowRecord;
import main.java.com.library.server.model.User;
import main.java.com.library.server.service.BookService;
import main.java.com.library.server.service.BorrowService;
import main.java.com.library.server.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private BookService bookService;
    private UserService userService;
    private BorrowService borrowService;
    private RequestHandler requestHandler;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.bookService = new BookService();
        this.userService = new UserService();
        this.borrowService = new BorrowService();
        this.requestHandler = new RequestHandler();

        registerHandlers();
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            // 读取客户端请求
            Object request = in.readObject();
            System.out.println("Received request: " + request);

            // 处理请求并生成响应
            Object response = requestHandler.handleRequest(request);

            // 发送响应给客户端
            out.writeObject(response);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerHandlers() {
        requestHandler.registerHandler(AddBookRequest.class, this::handleAddBookRequest);
        requestHandler.registerHandler(DeleteBookRequest.class, this::handleDeleteBookRequest);
        requestHandler.registerHandler(UpdateBookRequest.class, this::handleUpdateBookRequest);
        requestHandler.registerHandler(GetBookRequest.class, this::handleGetBookRequest);
        requestHandler.registerHandler(GetAllBooksRequest.class, request -> handleGetAllBooksRequest());
        requestHandler.registerHandler(RegisterUserRequest.class, this::handleRegisterUserRequest);
        requestHandler.registerHandler(LoginRequest.class, this::handleLoginRequest);
        requestHandler.registerHandler(BorrowBookRequest.class, this::handleBorrowBookRequest);
        requestHandler.registerHandler(ReturnBookRequest.class, this::handleReturnBookRequest);
        requestHandler.registerHandler(DeleteBorrowRecordRequest.class, this::handleDeleteBorrowRecordRequest);
        requestHandler.registerHandler(GetBorrowRecordRequest.class, this::handleGetBorrowRecordRequest);
        requestHandler.registerHandler(GetAllUsersRequest.class, request -> handleGetAllUsersRequest());
        requestHandler.registerHandler(GetAllBorrowRecordsRequest.class, request -> handleGetAllBorrowRecordsRequest());
        requestHandler.registerHandler(UpdateUserRequest.class, this::handleUpdateUserRequest);
        requestHandler.registerHandler(GetUserRequest.class, this::handleGetUserRequest);
        requestHandler.registerHandler(UpdatePasswordRequest.class, this::handleUpdatePasswordRequest);
        requestHandler.registerHandler(UpdateRoleRequest.class, this::handleUpdateRoleRequest);
    }

    private Object handleAddBookRequest(AddBookRequest request) {
        boolean result = bookService.addBook(request.getBook());
        return result ? "Book added successfully" : "Failed to add book";
    }

    private Object handleDeleteBookRequest(DeleteBookRequest request) {
        boolean result = bookService.deleteBook(request.getBookId());
        return result ? "Book deleted successfully" : "Failed to delete book";
    }

    private Object handleUpdateBookRequest(UpdateBookRequest request) {
        boolean result = bookService.updateBook(request.getBook());
        return result ? "Book updated successfully" : "Failed to update book";
    }

    private Object handleGetBookRequest(GetBookRequest request) {
        Book book = bookService.getBook(request.getIdentifier());
        return book != null ? book : "Book not found";
    }

    private Object handleGetAllBooksRequest() {
        List<Book> books = bookService.getAllBooks();
        return books;
    }

    private Object handleRegisterUserRequest(RegisterUserRequest request) {
        boolean result = userService.registerUser(request.getUser());
        return result ? "User registered successfully" : "Failed to register user";
    }

    private Object handleLoginRequest(LoginRequest request) {
        boolean result = userService.login(request.getUsername(), request.getPassword());
        return result ? "Login successful" : "Invalid username or password";
    }

    private Object handleBorrowBookRequest(BorrowBookRequest request) {
        BorrowRecord record = new BorrowRecord(
                request.getRecordID(),
                request.getUserID(),
                request.getBookID(),
                new java.util.Date(),
                null
        );
        boolean result = borrowService.addBorrowRecord(record);
        return result ? "Book borrowed successfully" : "Failed to borrow book";
    }

    private Object handleReturnBookRequest(ReturnBookRequest request) {
        boolean result = borrowService.returnBook(request.getRecordID());
        return result ? "Book returned successfully" : "Failed to return book";
    }

    private Object handleDeleteBorrowRecordRequest(DeleteBorrowRecordRequest request) {
        boolean result = borrowService.deleteBorrowRecord(request.getRecordID());
        return result ? "Borrow record deleted successfully" : "Failed to delete borrow record";
    }

    private Object handleGetBorrowRecordRequest(GetBorrowRecordRequest request) {
        BorrowRecord record = borrowService.getBorrowRecord(request.getRecordID());
        return record != null ? record : "Borrow record not found";
    }

    private Object handleGetAllUsersRequest() {
        List<User> users = userService.getAllUsers();
        return users;
    }

    private Object handleGetAllBorrowRecordsRequest() {
        List<BorrowRecord> records = borrowService.getAllBorrowRecords();
        return records;
    }

    private Object handleUpdateUserRequest(UpdateUserRequest request) {
        boolean result = userService.updateUser(request.getUser());
        return result ? "User updated successfully" : "Failed to update user";
    }

    private Object handleGetUserRequest(GetUserRequest request) {
        User user = userService.getUser(request.getIdentifier());
        return user != null ? user : "User not found";
    }

    private Object handleUpdatePasswordRequest(UpdatePasswordRequest request) {
        boolean result = userService.updatePassword(request.getUserID(), request.getNewPassword());
        return result ? "Password updated successfully" : "Failed to update password";
    }

    private Object handleUpdateRoleRequest(UpdateRoleRequest request) {
        boolean result = userService.updateRole(request.getUserID(), request.getNewRole());
        return result ? "Role updated successfully" : "Failed to update role";
    }
}