package me.theentropyshard.feedbot.msgprocessors;

import api.longpoll.bots.methods.VkBotsMethods;
import api.longpoll.bots.model.objects.basic.Message;

public class TextMessageProcessor extends MessageProcessor {
    public TextMessageProcessor(VkBotsMethods vk) {
        super(vk);
    }

    @Override
    public void process(Message msgObj) {

    }
}
