package com.sasha.voicebridge.party;

import net.dv8tion.jda.core.JDA;

public interface IDiscord {

    void login(String token, boolean bot);

    JDA getDiscord();


}
