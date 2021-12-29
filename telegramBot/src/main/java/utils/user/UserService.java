package utils.user;

import api.bank.Banks;
import api.bank.CurrencyNames;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserService {
    private ConcurrentMap<Long, UserSettings> userMap;
    private static final String USERDATA_FILE_NAME = "users.json";
    private static File file = new File(USERDATA_FILE_NAME);
    private static UserService serviceInstance;

    private UserService() {
        userMap = new ConcurrentHashMap<>();
        readUsersFromFile();
    }

    public static UserService getInstance() {
        if (serviceInstance == null) {
            serviceInstance = new UserService();
        }
        return serviceInstance;
    }

    private static UserSettings defaultSettings = new UserSettings("DefUser",
            new HashSet<>(List.of(Banks.PRIVATBANK)),
            new HashSet<>(List.of(CurrencyNames.USD)), 2, -1);

   public Boolean isUserExists(Long userId) {
        try {
            return userMap.containsKey(userId);
        } catch (NullPointerException e) {
            return false;
        }
    }

    //Записываем банк в настройки пользователя, если его нет, то добавляется/если есть то не дублируется
    public void setBank(Long userId, String bank) {
        UserSettings userSettings = userMap.get(userId);
        HashSet<Banks> banksHashSet = userSettings.getBanksHashSet();
        banksHashSet.add(Banks.valueOf(bank));
        userSettings.setBanksHashSet(banksHashSet);
        userMap.replace(userId, userSettings);
        saveUsersToFile();
    }

    //Удаляем банк из настройки пользователя
    public void unSetBank(Long userId, String bank) {
        UserSettings userSettings = userMap.get(userId);
        HashSet<Banks> banks = userSettings.getBanksHashSet();
        banks.remove(Banks.valueOf(bank));
        userSettings.setBanksHashSet(banks);
        userMap.replace(userId, userSettings);
        saveUsersToFile();
    }

    //Записываем валюту в настройки пользователя, если ее нет, то добавляется/если есть то не дублируется
    public void setCurrency(Long userId, String currency) {
        UserSettings userSettings = userMap.get(userId);
        HashSet<CurrencyNames> currencies = userSettings.getCurrenciesHashSet();
        currencies.add(CurrencyNames.valueOf(currency));
        userSettings.setCurrenciesHashSet(currencies);
        userMap.replace(userId, userSettings);
        saveUsersToFile();
    }

    //Удаляем валюту из настроек пользователя
    public void unSetCurrency(Long userId, String currency) {
        UserSettings userSettings = userMap.get(userId);
        HashSet<CurrencyNames> currencies = userSettings.getCurrenciesHashSet();
        currencies.remove(CurrencyNames.valueOf(currency));
        userSettings.setCurrenciesHashSet(currencies);
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
        UserSettings userSettings = userMap.get(userId);
        userSettings.setNotifyHour(notifyHour);
        userMap.put(userId, userSettings);
        saveUsersToFile();
    }


    //Get element of Map with user settings
    public UserSettings getUserSettings(Long userId) {
        UserSettings settings = userMap.get(userId);
        if (settings == null) {
            return new UserSettings("DefUser",
                    new HashSet<>(List.of(Banks.PRIVATBANK)),
                    new HashSet<>(List.of(CurrencyNames.USD)), 2, -1);
        }
        return settings;
    }

    //Add or change Map element with user settings
    public void setUserSettings(Long userId, UserSettings userSettings) {
        userMap.put(userId, userSettings);
        saveUsersToFile();
    }

    private void readUsersFromFile() {
        File usersFile = new File(USERDATA_FILE_NAME);
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            userMap = new Gson()
                    .fromJson(reader, new TypeToken<ConcurrentHashMap<Long, UserSettings>>() {
                    }.getType());
            System.out.println(userMap);//for check
            if (userMap == null) {
                userMap = new ConcurrentHashMap<>();
            }
        } catch (IOException e) {
            System.out.println("ReaderError: " + e.getMessage());  //it may not the best way
        }
    }

    public void saveUsersToFile() {
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(userMap, writer);
        } catch (IOException e) {
            System.out.println("SaverError: " + e.getMessage());
        }
    }


//    public void saveUsersToFile() {
//        Gson json = new GsonBuilder().setPrettyPrinting().create();
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            gson.toJson(userMap, writer);
//        } catch (IOException e) {
//            System.out.println("SaverError: "+e.getMessage());
//        }
//    }

    public Map<Long, UserSettings> getAllUserSettings() {
        return new HashMap<>(userMap);
    }
}

