package me.theentropyshard.feedbot;

import api.longpoll.bots.LongPollBot;
import api.longpoll.bots.model.events.messages.MessageNew;
import api.longpoll.bots.model.objects.basic.Message;
import me.theentropyshard.feedbot.msgprocessors.CommandMessageProcessor;
import me.theentropyshard.feedbot.msgprocessors.MessageProcessor;
import me.theentropyshard.feedbot.msgprocessors.TextMessageProcessor;

public class FeedBot extends LongPollBot {
    private static final String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");

    private final MessageProcessor textMessageProcessor;
    private final MessageProcessor commandMessageProcessor;

    public FeedBot() {
        this.textMessageProcessor = new TextMessageProcessor(this.vk);
        this.commandMessageProcessor = new CommandMessageProcessor(this.vk);
    }

    @Override
    public void onMessageNew(MessageNew messageNew) {
        Message message = messageNew.getMessage();
        if(message.getText().startsWith("/")) {
            this.commandMessageProcessor.process(message);
        } else {
            this.textMessageProcessor.process(message);
        }
    }

    @Override
    public String getAccessToken() {
        return FeedBot.ACCESS_TOKEN;
    }
}
