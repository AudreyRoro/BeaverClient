package com.example.admin.model;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by Marianne on 22/01/15.
 */
public class Concerned {

    private int cId;
    private Participant participant;
    private Buyer buyer;

    private boolean isChecked;

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

    @JsonIgnore
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
