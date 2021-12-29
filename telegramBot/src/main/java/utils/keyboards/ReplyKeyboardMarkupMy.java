package utils.keyboards;

import api.controller.TelegramImplementations;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.user.UserSettings;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardMarkupMy extends TelegramImplementations {
    public void getKeyboardMarkup(String chatUserId, UserSettings userSettings){
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setSelective(true);
    replyKeyboardMarkup.setOneTimeKeyboard(true);
    List<KeyboardRow> keyboard = new ArrayList<>();



    int startHour = 9;
    int shiftHour = 0;
    for (int i = 0; i < 3; i++) {
        KeyboardRow keyboardRow = new KeyboardRow();
        for (int j = startHour + shiftHour; j < startHour + shiftHour + 3; j++) {

            keyboardRow.add(KeyboardButton.builder()
                    .text( j + ":00")
                    .build());
        }
        shiftHour += 3;
        keyboard.add(keyboardRow);
    }  KeyboardRow keyboardFourthRow = new KeyboardRow();

    keyboardFourthRow.add(KeyboardButton.builder()
            .text( "18:00")
            .build());

    keyboardFourthRow.add(KeyboardButton.builder()
            .text( "Выключить уведомления")
            .build());

    keyboard.add(keyboardFourthRow);
    replyKeyboardMarkup.setKeyboard(keyboard);

    try {
        executeAsync(
                SendMessage.builder()
                        .chatId(chatUserId)
                        .text("Выберите время уведомлений")
                        .replyMarkup(replyKeyboardMarkup)
                        .build());
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }
}
}
