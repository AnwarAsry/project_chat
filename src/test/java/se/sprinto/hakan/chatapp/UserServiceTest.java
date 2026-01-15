package se.sprinto.hakan.chatapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.sprinto.hakan.chatapp.model.User;
import se.sprinto.hakan.chatapp.repository.UserRepository;
import se.sprinto.hakan.chatapp.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository repo;
    @InjectMocks
    UserService userService;

    @Test
    void loginTest() {
        Mockito.when(repo.login("ska", "hämtaHAHA")).thenReturn(new User(1L, "ska", "hämtaHAHA"));
        User u = userService.login("ska", "hämtaHAHA");

        Mockito.verify(repo).login("ska", "hämtaHAHA");
        Assertions.assertEquals("ska", u.getUsername());
        Assertions.assertEquals("hämtaHAHA", u.getPassword());
    }

    @Test
    void loginThrowsWhenUserNotFound() {
        Mockito.when(repo.login("ska", "fel"))
                .thenReturn(null);
        User u = userService.login("ska", "fel");
        Mockito.verify(repo).login("ska", "fel");
        Assertions.assertNull(u);
    }

    @Test
    void registerTest() {
        User u = new User(1L, "ska", "hämtaHAHA");

        Mockito.when(repo.save(Mockito.any(User.class)))
                .thenReturn(u);
        User saved = userService.register(u);

        Assertions.assertEquals("ska", saved.getUsername());
        Assertions.assertEquals("hämtaHAHA", saved.getPassword());
    }
}
