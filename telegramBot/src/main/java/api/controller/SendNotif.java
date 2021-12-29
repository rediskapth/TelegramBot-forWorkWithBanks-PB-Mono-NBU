package api.controller;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.user.UserService;
import utils.user.UserSettings;

import java.io.IOException;
import java.util.Map;

public class SendNotif {
    private TelegramImplementations bot;
    private UserService serviceInstance = UserService.getInstance();

    public SendNotif(TelegramImplementations bot) {
        this.bot = bot;
    }

    private void sendOneNotification(Long userId, UserSettings userSettings) throws IOException, InterruptedException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userId.toString());
        String output = bot.sendInfo(userSettings);
        sendMessage.setText(output);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendNotifications(int hour) throws IOException, InterruptedException {
        Map<Long, UserSettings> settingsMap = serviceInstance.getAllUserSettings();
        for (Long userId : settingsMap.keySet()) {
            UserSettings value = settingsMap.get(userId);
            if (value.getNotifyHour() == hour) {
                sendOneNotification(userId, value);
                System.out.println("Notif send");
            }
        }
    }
}
