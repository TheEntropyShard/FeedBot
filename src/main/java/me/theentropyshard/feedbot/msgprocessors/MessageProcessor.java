package me.theentropyshard.feedbot.msgprocessors;

import api.longpoll.bots.exceptions.VkApiException;
import api.longpoll.bots.methods.VkBotsMethods;
import api.longpoll.bots.model.objects.additional.Keyboard;
import api.longpoll.bots.model.objects.additional.buttons.Button;
import api.longpoll.bots.model.objects.additional.buttons.TextButton;
import api.longpoll.bots.model.objects.basic.Message;
import me.theentropyshard.feedbot.Utils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class MessageProcessor {
    protected VkBotsMethods vk;

    public MessageProcessor(VkBotsMethods vk) {
        this.vk = vk;
    }

    public abstract void process(Message message);

    protected void sendMessage(Message message, String text) {
        try {
            this.vk.messages.send()
                    .setPeerId(message.getPeerId())
                    .setMessage(text)
                    .execute();
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }

    protected void sendKeyboard(Message message, String text) {
        try {
            List<List<Button>> buttons = new ArrayList<>();

            ArrayList<Button> buttons1 = new ArrayList<>();
            buttons.add(buttons1);

            buttons1.add(new TextButton(Button.Color.POSITIVE, new TextButton.Action("Hello")));

            Keyboard keyboard = new Keyboard(buttons);
            keyboard.setInline(true);
            this.vk.messages.send()
                    .setPeerId(message.getPeerId())
                    .setMessage(text)
                    .setKeyboard(keyboard)
                    .execute();
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }

    protected void sendPhoto(Message message, File file) {
        try {
            this.vk.messages.send()
                    .setPeerId(message.getPeerId())
                    .addPhoto(file)
                    .execute();
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }

    protected void sendPhoto(Message message, File file, String text) {
        try {
            this.vk.messages.send()
                    .setPeerId(message.getPeerId())
                    .setMessage(text)
                    .addPhoto(file)
                    .execute();
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }

    protected void deleteMessage(Message message, boolean forAll) {
        try {
            this.vk.messages.delete()
                    .setPeerId(message.getPeerId())
                    .setConversationMessageIds(message.getConversationMessageId())
                    .setDeleteForAll(forAll)
                    .execute();
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }

    protected String getFormattedUser(int fromId) {
        return "[id" + fromId + "|" + Utils.getUsernameFromID(fromId) + "]";
    }
}
