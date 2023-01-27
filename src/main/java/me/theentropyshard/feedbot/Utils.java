package me.theentropyshard.feedbot;

import org.jsoup.Jsoup;

public enum Utils {
    ;

    public static String getLinkFromID(int id) {
        return String.format("https://vk.com/id%d", id);
    }

    public static String getUsernameFromID(int id) {
        return Utils.getUsernameFromLink(Utils.getLinkFromID(id));
    }

    public static String getUsernameFromLink(String link) {
        try {
            return Jsoup.connect(link)
                    .get()
                    .title()
                    .split("\\|")[0]
                    .trim();
        } catch (Exception e) {
            return "User";
        }
    }
}
