package main.java.com.library.server.handler;

import main.java.com.library.server.handler.requestHandler.*;
import main.java.com.library.server.model.Book;
import main.java.com.library.server.model.BorrowRecord;
import main.java.com.library.server.model.User;
import main.java.com.library.server.service.BookService;
import main.java.com.library.server.service.BorrowService;
import main.java.com.library.server.service.UserService;
import org.reflections.Reflections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;
import java.util.Set;


public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final BookService bookService;
    private final UserService userService;
    private final BorrowService borrowService;
    private final RequestHandler requestHandler;

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
        Reflections reflections = new Reflections("main.java.com.library.server.handler.requestHandler");
        Set<Class<? extends Serializable>> requestClasses = reflections.getSubTypesOf(Serializable.class);

        for (Class<? extends Serializable> requestClass : requestClasses) {
            try {
                String methodName = "handle" + requestClass.getSimpleName().replace("Request", "") + "Request";
                Method method = this.getClass().getDeclaredMethod(methodName, requestClass);
                requestHandler.registerHandler(requestClass, req -> {
                    try {
                        return method.invoke(this, req);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "Error handling request";
                    }
                });
            } catch (NoSuchMethodException e) {
                System.out.println("No handler method found for: " + requestClass.getSimpleName());
            }
        }
    }


    private Object handleAddBookRequest(AddBookRequest request) {
        boolean result = bookService.addBook(request.book());
        return result ? "Book added successfully" : "Failed to add book";
    }

    private Object handleDeleteBookRequest(DeleteBookRequest request) {
        boolean result = bookService.deleteBook(request.bookId());
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

    private String handleRegisterUserRequest(RegisterUserRequest request) {
        boolean result = userService.registerUser(request.getUser());
        return result ? "User registered successfully" : "Failed to register user";
    }

    private Object handleLoginRequest(LoginRequest request) {
        boolean result = userService.login(request.username(), request.password());
        return result ? "Login successful" : "Invalid username or password";
    }

    private Object handleBorrowBookRequest(BorrowBookRequest request) {
        BorrowRecord record = request.getBorrowRecord();
        boolean result = borrowService.addBorrowRecord(record);
        return result ? "Book borrowed successfully" : "Failed to borrow book";
    }

    private Object handleReturnBookRequest(ReturnBookRequest request) {
        boolean result = borrowService.returnBook(request.recordID());
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
        return userService.getAllUsers();
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


    private List<BorrowRecord> handleGetAllBorrowRecordsRequest(GetAllBorrowRecordsRequest request) {
        return borrowService.getAllBorrowRecords();
    }

    private List<Book> handleGetAllBookRequest(GetAllBorrowRecordsRequest request) {
        return bookService.getAllBooks();
    }


}