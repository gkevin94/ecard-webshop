package com.ecard.ecardwebshop.user;

import com.ecard.ecardwebshop.product.ResultStatusEnum;

public class UserValidator {

    UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean userCanBeSaved(User user) {
        return usernameIsNotEmptyOrNull(user.getUserName()) && passwordIsValid(user.getPassword());
    }

    public boolean userNameIsUnique(User user) {
        return userService.getUserByName(user.getUserName()) == null;
    }

    private boolean usernameIsNotEmptyOrNull(String name) {
        return name != null && !name.trim().equals("");
    }

    private boolean passwordIsValid(String pass) {
        return pass == null || pass.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,50}$");
    }

    public boolean deletionWasSuccessFul(long id) {
        int sizeOfUserListBeforeDeletion = userService.listUsers().size();
        userService.deleteUserById(id);
        int sizeOfUserListAfterDeletingAUser = userService.listUsers().size();
        return sizeOfUserListBeforeDeletion > sizeOfUserListAfterDeletingAUser;
    }
}