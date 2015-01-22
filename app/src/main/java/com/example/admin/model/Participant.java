package com.example.admin.model;

import java.util.Set;

/**
 * Created by Marianne on 22/01/15.
 */
public class Participant {
    private int pId;
    private Event event;
    private User user;
    private Set<Buyer> buyers;
    private Set<Concerned> concernedSet;
    private Set<Debt> givers;
    private Set<Debt> receivers;

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Buyer> getBuyers() {
        return buyers;
    }

    public void setBuyers(Set<Buyer> buyers) {
        this.buyers = buyers;
    }

    public Set<Concerned> getConcernedSet() {
        return concernedSet;
    }

    public void setConcernedSet(Set<Concerned> concernedSet) {
        this.concernedSet = concernedSet;
    }

    public Set<Debt> getGivers() {
        return givers;
    }

    public void setGivers(Set<Debt> givers) {
        this.givers = givers;
    }

    public Set<Debt> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<Debt> receivers) {
        this.receivers = receivers;
    }
}
