package com.ericlam.mc.jetty.api;

import com.google.gson.Gson;

import javax.annotation.Nullable;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

@WebServlet
public abstract class ContextHolder extends HttpServlet {

    private String path;
    public ContextHolder(String description, String name, String path){
        this.path = path;
        try {
            Field field = this.getClass().getDeclaredField("annotations");
            field.setAccessible(true);
            WebServlet web = new WebServlet() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return null;
                }

                @Override
                public String name() {
                    return this.getClass().getName();
                }

                @Override
                public String[] value() {
                    return new String[0];
                }

                @Override
                public String[] urlPatterns() {
                    return new String[]{path};
                }

                @Override
                public int loadOnStartup() {
                    return 0;
                }

                @Override
                public WebInitParam[] initParams() {
                    return new WebInitParam[0];
                }

                @Override
                public boolean asyncSupported() {
                    return false;
                }

                @Override
                public String smallIcon() {
                    return "";
                }

                @Override
                public String largeIcon() {
                    return "";
                }

                @Override
                public String description() {
                    return description;
                }

                @Override
                public String displayName() {
                    return name;
                }
            };
            field.set(this, web);
        }catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    String getPath() {
        return path;
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Content-Type", "application/json, charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doHead(req,resp);
        Map<String,String[]> parameters = req.getParameterMap();
        Method method = Method.GET;
        Map<String,Object> map = this.onRequest(req.getPathInfo(),parameters,method);
        resp.getWriter().println(new Gson().toJson(map));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doHead(req,resp);
        Map<String,String[]> parameters = req.getParameterMap();
        Method method = Method.POST;
        Map<String,Object> map = this.onRequest(req.getPathInfo(),parameters,method);
        resp.getWriter().println(new Gson().toJson(map));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doHead(req,resp);
        Map<String,String[]> parameters = req.getParameterMap();
        Method method = Method.PUT;
        Map<String,Object> map = this.onRequest(req.getPathInfo(),parameters,method);
        resp.getWriter().println(new Gson().toJson(map));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doHead(req,resp);
        Map<String,String[]> parameters = req.getParameterMap();
        Method method = Method.DELETE;
        Map<String,Object> map = this.onRequest(req.getPathInfo(),parameters,method);
        resp.getWriter().println(new Gson().toJson(map));
    }

    public enum Method{
        GET, POST, DELETE, PUT
    }

    public abstract Map<String,Object> onRequest(@Nullable  String url, Map<String,String[]> parameters, Method method);

}
