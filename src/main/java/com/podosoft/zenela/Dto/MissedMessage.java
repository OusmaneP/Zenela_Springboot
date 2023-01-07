package com.podosoft.zenela.Dto;

public class MissedMessage {
    Long fromLogin;
    int missedCount;

    public MissedMessage() {
    }

    public MissedMessage(Long fromLogin, int missedCount) {
        this.fromLogin = fromLogin;
        this.missedCount = missedCount;
    }

    public Long getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(Long fromLogin) {
        this.fromLogin = fromLogin;
    }

    public int getMissedCount() {
        return missedCount;
    }

    public void setMissedCount(int missedCount) {
        this.missedCount = missedCount;
    }
}
