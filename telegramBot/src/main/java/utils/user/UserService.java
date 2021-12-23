package utils.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {
    private static Map<Long, UserSettings> userList;
    private static final String USERDATA_FILE_NAME = "users.json";

    private UserService() {
        userList = new ConcurrentHashMap<>();
    }

    //Get element of Map with user settings
    public UserSettings getUserSettings(Long userId){
        return userList.get(userId);
    }

    //Add or change Map element with user settings
    public void setUserSettings(Long userId, UserSettings userSettings){
        if (userList.containsKey(userId)){
            userList.replace(userId, userSettings);
        } else {
            userList.put(userId, userSettings);
        }
    }

    public static void readUsersFromFile() {
        File usersFile = new File(USERDATA_FILE_NAME);
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            userList = new Gson()
                    .fromJson(reader, new TypeToken<HashMap<String, UserSettings>>() {
                    }.getType());
            System.out.println(userList);//for check
        } catch (IOException e) {
            System.out.println(e.getMessage());  //it may not the best way
        }
    }

    public static void saveUsersToFile() {
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(USERDATA_FILE_NAME);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(userList, writer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

