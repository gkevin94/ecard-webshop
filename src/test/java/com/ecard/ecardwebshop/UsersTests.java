package com.ecard.ecardwebshop;

import com.ecard.ecardwebshop.product.ResultStatus;
import com.ecard.ecardwebshop.user.User;
import com.ecard.ecardwebshop.user.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class UsersTests {


    @Autowired
    private UserController userController;

    @Test
    public void testListUsers() {
        // Given: init.sql
        // When
        List<User> users = userController.listUsers();

        //Then (size of user is increased by one)
        assertEquals(3, userController.listUsers().size());
    }

    @Test
    public void testCreateUserAndListUsers() {

        // Given (having a user list)
        List<User> users = userController.listUsers();

        // When (adding a user)
        userController.createUser(new User(5L, "Ciara Doe", "a@eee.com", "cccdoe", "abcdef1A", 1,
                "ROLE_USER", "ACTIVE"));

        //Then (size of user is increased by one)
        assertEquals(users.size() + 1, userController.listUsers().size());
    }

    @Test
    public void testCreateUserWithExistingUserName() {

        // Given (having a user list)
        List<User> users = userController.listUsers();

        // When (adding a user)
        ResultStatus status = userController.createUser(new User(5L, "Ciara Doe", "a@eee.com", "user", "abcdef1A", 1,
                "ROLE_USER", "ACTIVE"));

        //Then (size of user is increased by one)
        assertEquals(users.size(), userController.listUsers().size());
        assertEquals("\"user\" már regisztrált felhasználó!", status.getMessage());
    }

    @Test
    public void testDeleteUserById() {

        // Given (having a user list)
        List<User> users = userController.listUsers();


        // When (deleting a user)
        long id = users.stream().filter(u -> u.getName().equals("admin")).findFirst().get().getId();
        userController.deleteUserById(id);
        List<User> users2 = userController.listUsers();

        //Then (size of user list is decreased by one)
        assertEquals(users.size() - 1, users2.size());

    }

    @Test
    public void testUpdateUserById() {

        // Given (having a user list)
        List<User> users = userController.listUsers();


        User exampleUser = users.get(0);
        long exampleUserId = users.get(0).getId();

        assertThat(users.contains(exampleUser), equalTo(true));

        // When (modifying a user by ID)
        userController.updateUser(exampleUserId, (new User(5L, "Ciara Doe", "a@eee.com", "cccdoe", "abcdef1A", 1,
                "ROLE_USER", "ACTIVE")));

        //Then (example user can not be found any more in the list)
        users = userController.listUsers();
        assertThat(users.contains(exampleUser), equalTo(false));

        // Ciara Doe can be found in the list and it's userName is cccdoe

        User updatedUser = users.stream().filter((user) -> user.getName().equals("Ciara Doe")).findFirst().get();
        String updatedUsersUserName = "cccdoe";
        assertEquals(updatedUsersUserName, updatedUser.getUserName());
    }

    @Test
    public void testGetUserById() {

        // Given (an ID)
        long id = 3;
        // When (getting a user by id)
        User user = userController.getUserById(id);
        //Then (It's name is admin)
        assertEquals("admin2", user.getUserName());
    }
}
