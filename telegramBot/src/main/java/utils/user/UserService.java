package utils.user;

import api.bank.Banks;
import api.bank.CurrencyNames;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserService {
    private ConcurrentMap<Long, UserSettings> userMap;
    private static final String USERDATA_FILE_NAME = "users.json";
    private static File file = new File(USERDATA_FILE_NAME);
    private static UserService serviceInstance;

    public UserService() {
        userMap = new ConcurrentHashMap<>();
        readUsersFromFile();
    }

    public static UserService getInstance() {
        return Objects.requireNonNullElseGet(serviceInstance, UserService::new);
    }

    private static UserSettings defaultSettings = new UserSettings("DefUser",
            new HashSet<>(List.of(Banks.PRIVATBANK)),
            new HashSet<>(List.of(CurrencyNames.USD)), 2, -1);

    public Boolean isUserExists(Long userId) {
        return userMap.containsKey(userId);
    }

    //Записываем банк в настройки пользователя, если его нет, то добавляется/если есть то не дублируется
    public void setBank(Long userId, String bank) {
        UserSettings userSettings = userMap.get(userId);
        HashSet<Banks> banks = userSettings.getBankList();
        banks.add(Banks.valueOf(bank));
        userMap.replace(userId, userSettings);
        saveUsersToFile();
    }

    //Удаляем банк из настройки пользователя
    public void unSetBank(Long userId, String bank) {
        UserSettings userSettings = userMap.get(userId);
        HashSet<Banks> banks = userSettings.getBankList();
        banks.remove(Banks.valueOf(bank));
        userMap.replace(userId, userSettings);
        saveUsersToFile();
    }

    //Записываем валюту в настройки пользователя, если ее нет, то добавляется/если есть то не дублируется
    public void setCurrency(Long userId, String currency) {
        UserSettings userSettings = userMap.get(userId);
        HashSet<CurrencyNames> currencies = userSettings.getCurrencies();
        currencies.add(CurrencyNames.valueOf(currency));
        userSettings.setCurrencies(currencies);
        userMap.replace(userId, userSettings);
        saveUsersToFile();
    }

    //Удаляем валюту из настроек пользователя
    public void unSetCurrency(Long userId, String currency) {
        UserSettings userSettings = userMap.get(userId);
        HashSet<CurrencyNames> currencies = userSettings.getCurrencies();
        currencies.remove(CurrencyNames.valueOf(currency));
        userSettings.setCurrencies(currencies);
        userMap.replace(userId, userSettings);
        saveUsersToFile();
    }

    //Записываем кол-во знаков после запятой в пользователя
    public void setAccuracy(Long userId, int accuracy) {
        UserSettings userSettings = userMap.get(userId);
        userSettings.setRoundAccuracy(accuracy);
        userMap.put(userId, userSettings);
        saveUsersToFile();
    }

    //Записываем время оповещения 0- не оповещать
    public void setNotify(Long userId, int notifyHour) {
        userMap.get(userId).setNotifyHour(notifyHour);
        saveUsersToFile();
    }


    //Get element of Map with user settings
    public UserSettings getUserSettings(Long userId) {
        UserSettings settings = userMap.get(userId);
        if (settings == null) {
            return defaultSettings;
        }
        return settings;
    }

    //Add or change Map element with user settings
    public void setUserSettings(Long userId, UserSettings userSettings) {
        UserSettings userSettings1 = new UserSettings(userSettings.getName(),userSettings.getBankList(),userSettings.getCurrencies(),userSettings.getRoundAccuracy(),userSettings.getNotifyHour());
        userMap.put(userId, userSettings1);
        saveUsersToFile();
    }

    private void readUsersFromFile() {
        File usersFile = new File(USERDATA_FILE_NAME);
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            userMap = new Gson()
                    .fromJson(reader, new TypeToken<ConcurrentHashMap<String, UserSettings>>() {
                    }.getType());
            System.out.println(userMap);//for check
        } catch (IOException e) {
            System.out.println("ReaderError: "+e.getMessage());  //it may not the best way
        }
    }

    public void saveUsersToFile() {
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(userMap, writer);
        } catch (IOException e) {
            System.out.println("SaverError: "+e.getMessage());
        }
    }
}

