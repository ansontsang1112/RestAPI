package com.ericlam.mc.jetty.api;

import org.bukkit.plugin.Plugin;

public class JettyBuilder {
    private int port = 8080;
    private Plugin plugin;
    private ContextHolder[] holders = new ContextHolder[0];

    public JettyBuilder(Plugin plugin){
        this.plugin = plugin;
    }

    public JettyBuilder setPort(int port){
        this.port = port;
        return this;
    }

    public JettyBuilder addHolder(ContextHolder... holders){
        this.holders = holders;
        return this;
    }

    public void launch(){
        new JettyServerRunnable(port,holders).runTaskAsynchronously(plugin);
    }

}
