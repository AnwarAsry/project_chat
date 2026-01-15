package se.sprinto.hakan.chatapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatServerMain implements CommandLineRunner {
    private final ChatServer chatServer;

    public ChatServerMain(ChatServer chatServer) {
        this.chatServer = chatServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatServerMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {}
}
