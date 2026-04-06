package ru.ezhak.users.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ezhak.users.model.User;
import ru.ezhak.users.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindUsers() {
        User user = new User();
        user.setFirstName("Test");
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.findUsers();
        assertEquals(1, users.size());
        assertEquals("Test", users.getFirst().getFirstName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUserById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findUserById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        User user = new User();
        userService.save(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDelete() {
        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdate() {
        User user = new User();
        userService.update(1L, user);
        assertEquals(1L, user.getId());
        verify(userRepository, times(1)).save(user);
    }
}
