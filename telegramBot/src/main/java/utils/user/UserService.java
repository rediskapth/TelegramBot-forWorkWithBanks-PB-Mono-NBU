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
    private ConcurrentMap<Long, UserSettings> userList;
    private static final String USERDATA_FILE_NAME = "users.json";
    private static File file = new File(USERDATA_FILE_NAME);
    private static UserService serviceInstance;

    public UserService() {
        userList = new ConcurrentHashMap<>();
        readUsersFromFile();
    }

    public static UserService getInstance() {
        return Objects.requireNonNullElseGet(serviceInstance, UserService::new);
    }

    private static final UserSettings defaultSettings = new UserSettings("NoName",
            new HashSet<>(List.of(Banks.PRIVATBANK)),
            new HashSet<>(List.of(CurrencyNames.USD)), 2, 9);

    public Boolean isUserExists(Long userId) {
        return userList.containsKey(userId);
    }

    //Записываем банк в настройки пользователя, если его нет, то добавляется/если есть то не дублируется
    public void setBank(Long userId, String bank) {
        HashSet<Banks> banks = userList.get(userId).getBankList();
        banks.add(Banks.valueOf(bank));
        userList.get(userId).setBankList(banks);
        //saveUsersToFile();
    }

    //Удаляем банк из настройки пользователя
    public void unSetBank(Long userId, String bank) {
        HashSet<Banks> banks = userList.get(userId).getBankList();
        banks.remove(Banks.valueOf(bank));
        userList.get(userId).setBankList(banks);
        //saveUsersToFile();
    }

    //Записываем валюту в настройки пользователя, если ее нет, то добавляется/если есть то не дублируется
    public void setCurrency(Long userId, String currency) {
        HashSet<CurrencyNames> currencies = userList.get(userId).getCurrencies();
        currencies.add(CurrencyNames.valueOf(currency));
        userList.get(userId).setCurrencies(currencies);
        //saveUsersToFile();
    }

    //Удаляем валюту из настроек пользователя
    public void unSetCurrency(Long userId, String currency) {
        HashSet<CurrencyNames> currencies = userList.get(userId).getCurrencies();
        currencies.remove(CurrencyNames.valueOf(currency));
        userList.get(userId).setCurrencies(currencies);
      //  System.out.println(userList.get(userId).getCurrencies().toString());
        //saveUsersToFile();
    }

    //Записываем кол-во знаков после запятой в пользователя
    public void setAccuracy(Long userId, int accuracy) {
        userList.get(userId).setRoundAccuracy(accuracy);
        //saveUsersToFile();
    }

    //Записываем время оповещения 0- не оповещать
    public void setNotify(Long userId, int notifyHour) {
        userList.get(userId).setNotifyHour(notifyHour);
        //saveUsersToFile();
    }


    //Get element of Map with user settings
    public UserSettings getUserSettings(Long userId) {
        UserSettings settings = userList.get(userId);
        if (settings == null) {
            return defaultSettings;
        }
        return settings;
    }

    //Add or change Map element with user settings
    public void setUserSettings(Long userId, UserSettings userSettings) {
        if (userList.containsKey(userId)) {
            userList.replace(userId, userSettings);
        } else {
            userList.put(userId, userSettings);
        }
        saveUsersToFile();
    }

    private void readUsersFromFile() {
        File usersFile = new File(USERDATA_FILE_NAME);
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            userList = new Gson()
                    .fromJson(reader, new TypeToken<ConcurrentHashMap<String, UserSettings>>() {
                    }.getType());
            System.out.println(userList);//for check
        } catch (IOException e) {
            System.out.println(e.getMessage());  //it may not the best way
        }
    }

    public void saveUsersToFile() {
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(userList, writer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

