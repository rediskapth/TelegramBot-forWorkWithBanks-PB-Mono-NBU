import api.controller.TelegramImplementations;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import utils.user.UserService;
import utils.user.UserSettings;

import java.util.Optional;

public class TestAPI extends TelegramLongPollingBot {
    private UserService userList = new UserService();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Long userId = update.getMessage().getChatId();
            if (!userList.isUserExists(userId)) {
                UserSettings user = userList.getUserSettings(userId);
                user.setName(update.getMessage().getFrom().getFirstName());
                userList.setUserSettings(userId, user);
            }
            try {
                messageHandler(update.getMessage());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            System.out.println(userList.getUserSettings(userId).getName());
        }

    }

    private void messageHandler(Message message) throws TelegramApiException {
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                switch (command) {
                    case "/set_currency":
                        execute(SendMessage.builder().chatId(message.getChatId().toString()).text("Please select currencies").build());
                }
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    public static void main(String[] args) throws TelegramApiException {
        TestAPI bot = new TestAPI();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }
}
