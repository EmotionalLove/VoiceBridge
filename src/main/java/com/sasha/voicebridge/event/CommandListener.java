package com.sasha.voicebridge.event;

import com.sasha.voicebridge.Startup;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

public class CommandListener {

    public static Message LAST_MSG;
    public static User YOU;

    @SubscribeEvent
    public void onGuildMessageRx(GuildMessageReceivedEvent e) {
        if (e.getMessage().getContentDisplay().startsWith("*")) {
            LAST_MSG = e.getMessage();
            YOU = e.getJDA().getSelfUser();
            Startup.processor.processCommand(e.getMessage().getContentDisplay());
        }
    }

}
