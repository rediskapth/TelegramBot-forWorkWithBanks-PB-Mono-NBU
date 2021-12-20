package api.config;

import org.springframework.beans.factory.annotation.Value;

public class TelegramConfig {
    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;
}
