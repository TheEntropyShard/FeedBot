package me.theentropyshard.feedbot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public enum FeedManager {
    ;

    public static final int FLAG_LUNCH_MINUS = 1;
    public static final int FLAG_AFTER_MINUS = 2;
    public static final int FLAG_LUNCH_PLUS = 4;
    public static final int FLAG_AFTER_PLUS = 8;
    public static final int FLAG_LUNCH_SOUP = 16;

    public static final Map<Integer, Integer> DATABASE = new HashMap<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            File file = new File("database.bin");
            if(file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            synchronized (DATABASE) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.bin"));
                    oos.writeObject(DATABASE);
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public static void addUser(int userId, int flags) {
        DATABASE.put(userId, flags);
    }

    public static Map<Integer, Integer> getUserDB() {
        return DATABASE;
    }

    //TODO thats some sheet code, please, dont look on it
    public static String getOptionFromFlags(int flags) {
        String result = "";
        if((flags & FLAG_LUNCH_MINUS) != 0) {
            result += "Обед: -";
        } else if((flags & FLAG_LUNCH_PLUS) != 0) {
            result += "Обед: +";
        } else if((flags & FLAG_LUNCH_SOUP) != 0) {
            result += "Обед: суп";
        }

        if((flags & FLAG_AFTER_MINUS) != 0) {
            result += ", Полдник: -";
        } else if((flags & FLAG_AFTER_PLUS) != 0) {
            result += ", Полдник: +";
        }
        return result;
    }
}
