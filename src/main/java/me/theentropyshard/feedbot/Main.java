package me.theentropyshard.feedbot;

import api.longpoll.bots.exceptions.VkApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        checkDB();

        try {
            new FeedBot().startPolling();
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }

    private static void checkDB() {
        try {
            File file = new File("database.bin");
            if(!file.exists())  {
                file.createNewFile();
                return;
            }
            if(file.length() == 0L) {
                return;
            }
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            FeedManager.DATABASE.putAll((HashMap) o);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
