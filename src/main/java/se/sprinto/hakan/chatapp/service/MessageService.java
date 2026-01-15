package se.sprinto.hakan.chatapp.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import se.sprinto.hakan.chatapp.model.Message;
import se.sprinto.hakan.chatapp.model.User;
import se.sprinto.hakan.chatapp.repository.MessageRepository;
import se.sprinto.hakan.chatapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository repo;
    private final UserRepository userRepo;

    public MessageService(MessageRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @Transactional
    public void save(User user, String message) {
        User u = userRepo.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Message m = new Message();
        m.setUser(user);
        m.setText(message);
        repo.save(m);
    }

    @Transactional
    public List<Message> getMessages(Long userId) { return repo.findByUserId(userId); }
}

