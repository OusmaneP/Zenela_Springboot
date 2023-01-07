package com.podosoft.zenela.Websocket.models;

public class MessageDto {
    private String message;
    private String fromLogin;

    public MessageDto() {
    }

    public MessageDto(String message, String fromLogin) {
        this.message = message;
        this.fromLogin = fromLogin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(String fromLogin) {
        this.fromLogin = fromLogin;
    }
}
