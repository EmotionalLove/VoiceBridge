package com.sasha.voicebridge;

import com.sasha.simplesettings.annotation.Setting;

public class Configuration {

    @Setting
    public String hostAccount = null;
    @Setting
    public String remoteAccount = null;
    @Setting
    public boolean hostAccountIsBot = true;
    @Setting
    public boolean remoteAccountIsBot = false;
    @Setting
    public boolean manageCallsOnHost = true;
    @Setting
    public boolean manageCallsOnRemote = false;

}
