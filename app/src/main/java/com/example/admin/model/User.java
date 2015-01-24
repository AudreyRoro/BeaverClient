package com.example.admin.model;

import java.util.Set;

/**
 * Created by admin on 07/01/15.
 */
public class User {
    private int uId;
    private String uPseudo;
    private String uPassword;
    private String uMail;
    private int uState;
    private Set<Participant> participants;

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getuPseudo() {
        return uPseudo;
    }

    public void setuPseudo(String uPseudo) {
        this.uPseudo = uPseudo;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuMail() {
        return uMail;
    }

    public void setuMail(String uMail) {
        this.uMail = uMail;
    }

    public int getuState() {
        return uState;
    }

    public void setuState(int uState) {
        this.uState = uState;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }


}
