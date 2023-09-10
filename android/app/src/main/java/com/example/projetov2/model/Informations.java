package com.example.projetov2.model;

public class Informations {
    private static final String CHANNEL_FLUTTER = "samples.flutter.dev/battery";
    private static final String Channel_Id = "my_engine_id";
    private String Message_From_Native = "";

    private String route = "";

    private static Informations instance;

    private Informations() {

    }

    public static synchronized Informations getInstance() {
        if (instance == null) {
            instance = new Informations();
        }
        return instance;
    }

    public static String getChannelFlutter() {
        return CHANNEL_FLUTTER;
    }

    public static String getChannel_Id() {
        return Channel_Id;
    }

    public String getMessage_From_Native() {
        return Message_From_Native;
    }

    public void setMessage_From_Native(String message_From_Native) {
        Message_From_Native = message_From_Native;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}