package main.java.com.library.server.service.impl;

import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.common.entity.impl.User;
import main.java.com.library.server.database.impl.UserDao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author PC
 */
public class UserService extends BaseService<User> {
    public UserService() {
        super(new UserDao(User.class));
    }

    public User getUserByUsername(String username) {
        return super.getByField("username", username);
    }

    public User getUserByEmail(String email) {
        return super.getByField("email", email);
    }

    public User getUserByPhone(String phone) {
        return super.getByField("phone", phone);
    }

    public User getUser(String identifier) {
        User user = getUserByUsername(identifier);
        if (user == null) {
            user = getUserByEmail(identifier);
            if (user == null) {
                user = getUserByPhone(identifier);
            }
        }

        return user;
    }

    public boolean validateUser(String identifier, String password) {
        User user = getUser(identifier);
        return user != null && user.getPassword().equals(password);
    }

    public boolean isUserExists(String identifier) {
        return getUser(identifier) != null;
    }

    public boolean isAdmin(String identifier) {
        User user = getUser(identifier);
        return user != null && "admin".equals(user.getRole());
    }

    public String registerUser(User user) throws SQLException {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return "Failed to register user: Username is required";
        } else if (isUserExists(user.getUsername())) {
            User existingUser = getUserByUsername(user.getUsername());
            if (existingUser.isDeleted()) {
                return "Failed to register user: User has already been deleted,try to register after 7 days";
            }
            return "Failed to register user: Username already exists";
        } else if ((user.getEmail() != null && !user.getEmail().isEmpty()) && isUserExists(user.getEmail())) {
            return "Failed to register user: Email already exists";
        } else if ((user.getPhone() != null && !user.getPhone().isEmpty()) && isUserExists(user.getPhone())) {
            return "Failed to register user: Phone number already exists";
        } else if (user.getPassword().length() < 8) {
            return "Failed to register user: Password must be at least 8 characters";
        } else {
            user.setRole("user");
            if (super.add(user).equals("Success")) {
                return "Success: User registered successfully";
            } else {
                return "Failed to register user: Internal server error";
            }
        }
    }

    public String update(User user) {
        if (user.getId() == null) {
            return "Failed to update user: User ID is required";
        }

        User existingUser = super.get(user.getId());
        if (existingUser == null || existingUser.isDeleted()) {
            return "Failed to update user: User does not exist";
        }

        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            if (!user.getUsername().equals(existingUser.getUsername()) && isUserExists(user.getUsername())) {
                return "Failed to update user: Username already exists";
            }
            existingUser.setUsername(user.getUsername());
        }

        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            if (!user.getEmail().equals(existingUser.getEmail()) && isUserExists(user.getEmail())) {
                return "Failed to update user: Email already exists";
            }
            existingUser.setEmail(user.getEmail());
        }

        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            if (!user.getPhone().equals(existingUser.getPhone()) && isUserExists(user.getPhone())) {
                return "Failed to update user: Phone number already exists";
            }
            existingUser.setPhone(user.getPhone());
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            if (user.getPassword().length() < 8) {
                return "Failed to update user: Password must be at least 8 characters";
            }
            existingUser.setPassword(user.getPassword());
        }

        if (user.getRole() != null && !user.getRole().isEmpty()) {
            existingUser.setRole(user.getRole());
        }

        if (super.update(existingUser).equals("Success")) {
            return "Success: User updated successfully";
        } else {
            return "Failed to update user: Internal server error";
        }
    }


    @Override
    public String delete(String userID) {
        User existingUser = super.get(userID);
        if (existingUser == null || existingUser.isDeleted()) {
            return "Failed to delete user: User does not exist";
        }

        BorrowRecordService borrowRecordService = new BorrowRecordService();
        List<BorrowRecord> borrowRecordList = borrowRecordService.search("userId", userID, "=", 0);

        for (BorrowRecord borrowRecord : borrowRecordList) {
            if (!borrowRecord.isReturned()) {
                return "Failed to delete user: User has borrowed books, cannot delete user";
            }
        }

        existingUser.setDeleted(true);
        if (super.update(existingUser).equals("Success")) {
            return "Success: User deleted successfully";
        } else {
            return "Failed to delete user: Internal server error";
        }

    }

}