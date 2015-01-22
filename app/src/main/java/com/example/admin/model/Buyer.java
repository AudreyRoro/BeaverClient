package com.example.admin.model;

import java.util.Set;

/**
 * Created by Marianne on 22/01/15.
 */
public class Buyer {

    private int bId;
    private int bValue;
    private String bTitle;
    private String bDescription;
    private Participant participant;
    private Set<Concerned> concernedSet;

    public int getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }

    public int getbValue() {
        return bValue;
    }

    public void setbValue(int bValue) {
        this.bValue = bValue;
    }

    public String getbTitle() {
        return bTitle;
    }

    public void setbTitle(String bTitle) {
        this.bTitle = bTitle;
    }

    public String getbDescription() {
        return bDescription;
    }

    public void setbDescription(String bDescription) {
        this.bDescription = bDescription;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Set<Concerned> getConcernedSet() {
        return concernedSet;
    }

    public void setConcernedSet(Set<Concerned> concernedSet) {
        this.concernedSet = concernedSet;
    }
}
