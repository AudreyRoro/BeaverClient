package com.example.admin.model;

/**
 * Created by Marianne on 22/01/15.
 */
public class Concerned {

    private int cId;
    private Participant participant;
    private Buyer buyer;

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }
}
