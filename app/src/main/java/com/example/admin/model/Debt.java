package com.example.admin.model;

/**
 * Created by Marianne on 22/01/15.
 */
public class Debt {

    private int dId;
    private int dAmount;
    private boolean dState;

    private Participant giver;
    private Participant Receiver;

    public int getdId() {
        return dId;
    }

    public void setdId(int dId) {
        this.dId = dId;
    }

    public int getdAmount() {
        return dAmount;
    }

    public void setdAmount(int dAmount) {
        this.dAmount = dAmount;
    }

    public boolean isdState() {
        return dState;
    }

    public void setdState(boolean dState) {
        this.dState = dState;
    }

    public Participant getGiver() {
        return giver;
    }

    public void setGiver(Participant giver) {
        this.giver = giver;
    }

    public Participant getReceiver() {
        return Receiver;
    }

    public void setReceiver(Participant receiver) {
        Receiver = receiver;
    }
}
