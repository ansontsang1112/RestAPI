package com.ericlam.mc.jetty.api;

import org.bukkit.scheduler.BukkitRunnable;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

class JettyServerRunnable extends BukkitRunnable {
    private int port;
    private ContextHolder[] holders;

    public JettyServerRunnable(int port, ContextHolder[] holders) {
        this.port = port;
        this.holders = holders;
    }

    @Override
    public void run() {
        try{
            Server server = new Server(port);
            ServletContextHandler handler = new ServletContextHandler();
            for (ContextHolder holder : holders) {
                handler.addServlet(new ServletHolder(holder),holder.getPath());
            }
            handler.setMaxFormContentSize(50);
            server.setHandler(handler);
            server.start();
            server.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
