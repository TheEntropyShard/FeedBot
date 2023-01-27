package me.theentropyshard.feedbot.msgprocessors;

import api.longpoll.bots.exceptions.VkApiException;
import api.longpoll.bots.methods.VkBotsMethods;
import api.longpoll.bots.model.objects.basic.Message;
import me.theentropyshard.feedbot.FeedManager;
import me.theentropyshard.feedbot.Utils;

import java.util.*;

public class CommandMessageProcessor extends MessageProcessor {
    public CommandMessageProcessor(VkBotsMethods vk) {
        super(vk);
    }

    @Override
    public void process(Message message) {
        String text = message.getText();

        String[] splits = text.split("\\s");
        Deque<String> args = new ArrayDeque<>(Arrays.asList(splits));
        switch(args.pop()) {
            case "/":
                break;

            case "/питание":
                String lunch = args.pop();
                String afternoonTea = args.pop();

                String lunchOption = lunch.split(":")[1].toLowerCase();
                String afterOption = afternoonTea.split(":")[1].toLowerCase();

                int flags = 0;

                switch(lunchOption) {
                    case "-":
                        flags |= FeedManager.FLAG_LUNCH_MINUS;
                        break;
                    case "+":
                        flags |= FeedManager.FLAG_LUNCH_PLUS;
                        break;
                    case "суп":
                        flags |= FeedManager.FLAG_LUNCH_SOUP;
                        break;
                }

                switch(afterOption) {
                    case "-":
                        flags |= FeedManager.FLAG_AFTER_MINUS;
                        break;
                    case "+":
                        flags |= FeedManager.FLAG_AFTER_PLUS;
                        break;
                }

                FeedManager.addUser(message.getFromId(), flags);

                this.sendMessage(message, "Добавил тебя в список!");

                break;

            case "/печать":
                if(FeedManager.DATABASE.isEmpty()) {
                    this.sendMessage(message, "База данных пуста!");
                    return;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("Кушают:\n");
                for(Map.Entry<Integer, Integer> entry : FeedManager.getUserDB().entrySet()) {
                    sb.append("- ").append(Utils.getUsernameFromID(entry.getKey()));
                    sb.append(": ").append(FeedManager.getOptionFromFlags(entry.getValue()));
                    sb.append("\n");
                }

                this.sendMessage(message, sb.toString());
                break;

            case "/очистить":
                FeedManager.DATABASE.clear();
                this.sendMessage(message, "База данных очищена!");
                break;

            case "/закрепить":
                try {
                    this.vk.messages.pin()
                            .setPeerId(message.getPeerId())
                            .setConversationMessageId(message.getReplyMessage().getConversationMessageId())
                            .execute();
                    this.sendMessage(message, "Закрепил!");
                } catch (VkApiException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
