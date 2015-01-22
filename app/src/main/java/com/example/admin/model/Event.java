package com.example.admin.model;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by admin on 07/01/15.
 */
public class Event {
    private int eId;
    private String eTitle;
    private String eDescription;
    private Timestamp eCreationDate;
    private Timestamp eBeginDate;
    private Timestamp eEndDate;
    private Set<Participant> participants;

    public int geteId() {
        return eId;
    }

    public void seteId(int eId) {
        this.eId = eId;
    }

    public String geteTitle() {
        return eTitle;
    }

    public void seteTitle(String eTitle) {
        this.eTitle = eTitle;
    }

    public String geteDescription() {
        return eDescription;
    }

    public void seteDescription(String eDescription) {
        this.eDescription = eDescription;
    }

    public Timestamp geteCreationDate() {
        return eCreationDate;
    }

    public void seteCreationDate(Timestamp eCreationDate) {
        this.eCreationDate = eCreationDate;
    }

    public Timestamp geteBeginDate() {
        return eBeginDate;
    }

    public void seteBeginDate(Timestamp eBeginDate) {
        this.eBeginDate = eBeginDate;
    }

    public Timestamp geteEndDate() {
        return eEndDate;
    }

    public void seteEndDate(Timestamp eEndDate) {
        this.eEndDate = eEndDate;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

    public Event (int id, String title){
        id = this.eId;
        title=this.eTitle;
    }

    @Override
    public String toString(){
        return this.eTitle;
    }

}
