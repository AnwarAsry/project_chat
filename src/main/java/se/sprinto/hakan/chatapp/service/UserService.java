package se.sprinto.hakan.chatapp.service;

import org.springframework.stereotype.Service;
import se.sprinto.hakan.chatapp.model.User;
import se.sprinto.hakan.chatapp.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User login(String username, String password) {
        return repo.login(username, password);
    }

    public User register(User user) {
        return repo.save(user);
    }
}
