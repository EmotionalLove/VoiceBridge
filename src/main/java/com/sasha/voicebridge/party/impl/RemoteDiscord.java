package com.sasha.voicebridge.party.impl;

import com.sasha.voicebridge.party.IDiscord;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class RemoteDiscord implements IDiscord {

    private JDA discord;

    @Override
    public void login(String token, boolean bot) {
        JDABuilder builder = new JDABuilder(bot ? AccountType.BOT : AccountType.CLIENT);
        builder.setToken(token);
        try {
            discord = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
            System.out.println("Couldn't log into Discord! (Remote Discord)!");
            System.exit(2);
        }
    }

    @Override
    public JDA getDiscord() {
        return discord;
    }
}
