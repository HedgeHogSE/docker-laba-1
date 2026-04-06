package ru.ezhak.users.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ezhak.users.model.User;
import ru.ezhak.users.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetUsers() {
        User user = new User();
        when(userService.findUsers()).thenReturn(List.of(user));
        
        List<User> result = userController.getUsers();
        assertEquals(1, result.size());
        verify(userService, times(1)).findUsers();
    }

    @Test
    void testGetUser() {
        User user = new User();
        user.setId(1L);
        when(userService.findUserById(1L)).thenReturn(user);

        User result = userController.getUser(1L);
        assertEquals(1L, result.getId());
        verify(userService, times(1)).findUserById(1L);
    }

    @Test
    void testAddUser() {
        User user = new User();
        userController.addUser(user);
        verify(userService, times(1)).save(user);
    }

    @Test
    void testRemoveUser() {
        userController.removeUser(1L);
        verify(userService, times(1)).delete(1L);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        userController.updateUser(1L, user);
        verify(userService, times(1)).update(1L, user);
    }
}
