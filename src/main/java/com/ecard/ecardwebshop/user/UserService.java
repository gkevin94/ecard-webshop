package com.ecard.ecardwebshop.user;

import com.ecard.ecardwebshop.product.ResultStatus;
import com.ecard.ecardwebshop.product.ResultStatusEnum;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserDao userDao;
    private PasswordEncoder encoder;

    public UserService(UserDao userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    public List<User> listUsers() {
        return userDao.listUsers();
    }

    public void deleteUserById(long id) {
        userDao.deleteUserById(id);
    }

    public void updateUser(long id, User user) {
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            userDao.updateUserWithoutPassword(id, user);
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            userDao.updateUser(id, user);
        }
    }

    public long insertUserAndGetId(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userDao.insertUserAndGetId(user);
    }

    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    public User getUserByName(String userName) {
        try {
            return userDao.getUserByName(userName).get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
