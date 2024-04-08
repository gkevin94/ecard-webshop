package com.ecard.ecardwebshop.user;

import com.ecard.ecardwebshop.product.ResultStatus;
import com.ecard.ecardwebshop.product.ResultStatusEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    private UserValidator userValidator;

    public UserController(UserService userService) {
        this.userService = userService;
        this.userValidator = new UserValidator(userService);
    }

    @GetMapping("/users")
    public List<User> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/user")
    public User getUser(Authentication authentication) {
        if (authentication == null)
            return new User(1, "VISITOR");

        User user = userService.getUserByName(authentication.getName());
        if (user == null){
            return new User(1, "VISITOR");
        } else if (user.getRole().equals("ROLE_ADMIN")) {
            return new User(user.getId(), user.getName(), authentication.getName(), 1, "ROLE_ADMIN");
        } else if (user.getRole().equals("ROLE_USER")) {
            return new User(user.getId(), user.getName(), authentication.getName(), 1, "ROLE_USER");
        } else {
            return new User(1, "VISITOR");
        }
    }

    @GetMapping("/role")
    public User determineRole(Authentication authentication) {
        if (authentication == null)
            return new User(1, "VISITOR");

        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                return new User(authentication.getName(), 1, "ROLE_USER");
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                return new User(authentication.getName(), 1, "ROLE_ADMIN");
            }
        }
        return new User(1, "VISITOR");
    }

    @DeleteMapping("/users/{id}")
    public ResultStatus deleteUserById(@PathVariable long id) {
        if (userValidator.deletionWasSuccessFul(id)) {
            return new ResultStatus(ResultStatusEnum.OK, "A felhasználó törlése sikeres volt.");
        }
        return new ResultStatus(ResultStatusEnum.NOT_OK, "A felhasználó törlése sikertelen volt.");
    }

    @PostMapping("/users/{id}")
    public ResultStatus updateUser(@PathVariable long id, @RequestBody User user) {
        if (userValidator.userCanBeSaved(user)) {
            userService.updateUser(id, user);
            return new ResultStatus(ResultStatusEnum.OK, "A felhasználó sikeresen módosításra került");
        }
        return new ResultStatus(ResultStatusEnum.NOT_OK, "A módosítás sikertelen volt");
    }

    @PostMapping("/users")
    public ResultStatus createUser(@RequestBody User user) {
        if (!userValidator.userCanBeSaved(user)) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Üres név vagy jelszó lett megadva");
        }
        if (!userValidator.userNameIsUnique(user)) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, String.format("\"%s\" már regisztrált felhasználó!", user.getUserName()));
        }
        long id = userService.insertUserAndGetId(user);
        return new ResultStatus(ResultStatusEnum.OK, String.format("\"%s\" sikeresen mentésre került. ( id: %d )", user.getUserName(), id));
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }
}
