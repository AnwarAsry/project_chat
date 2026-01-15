package se.sprinto.hakan.chatapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.sprinto.hakan.chatapp.model.Message;
import se.sprinto.hakan.chatapp.model.User;
import se.sprinto.hakan.chatapp.repository.MessageRepository;
import se.sprinto.hakan.chatapp.repository.UserRepository;
import se.sprinto.hakan.chatapp.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    @Mock
    UserRepository userRepo;
    @Mock
    MessageRepository messageRepo;
    @InjectMocks
    MessageService messageService;

    User userOne = new User(1L, "ska", "hämtaHAHA");
    User userTwo = new User(2L, "inte", "lämnaHAHA");
    List<Message> messages = new ArrayList<>(List.of(
            new Message(userOne, "Hejdå Lov"),
            new Message(userTwo, "Ge mig 40000 i csn")
    ));

    @Test
    void getMessagesTest() {
        Mockito.when(messageRepo.findByUserId(1L)).thenReturn(List.of(messages.get(0)));

        List<Message> userOnesMessages = messageService.getMessages(1L);

        Mockito.verify(messageRepo).findByUserId(1L);
        userOnesMessages.forEach(message ->
                Assertions.assertEquals(1L, message.getUser().getId())
        );
    }

    @Test
    void saveMessageTest() {
        Mockito.when(userRepo.findById(userTwo.getId())).thenReturn(Optional.of(userTwo));
        String text = "Jag ska hämnas";
        messageService.save(userTwo, text);
        Mockito.verify(userRepo).findById(userTwo.getId());

        Mockito.verify(messageRepo).save(Mockito.argThat(
                m -> m.getUser().equals(userTwo) && m.getText().equals(text)
        ));
    }

    @Test
    void saveMessageThrowsWhenUserNotFound() {
        User missingUser = new User(99L, "ghost", "none");

        Mockito.when(userRepo.findById(99L))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> messageService.save(missingUser, "hej")
        );

        Assertions.assertEquals("User not found", ex.getMessage());
        Mockito.verify(messageRepo, Mockito.never()).save(Mockito.any());
    }
}
