package com.niccolo_zanieri_swe.model;

import java.util.ArrayList;
import java.util.Collections;

enum Specializations {
    FILMMAKER, PHOTOGRAPHER, GRAPHIC_DESIGNER
}

public class Creator extends User {
    public Creator(String usr, String email, String psw) {
        super(usr, email, psw);
    }

    public Creator(Creator creator) {
        super(creator.getUsername(), creator.getEmail(), creator.getPassword());
        this.specializations.addAll(creator.specializations);
        this.proposals.addAll(creator.proposals);
    }

    public Creator(String usr, String email, String psw, int max_p) {
        super(usr, email, psw);
        this.maxProposals = max_p;
    }

    public Creator(String usr, String email, String psw, Tags[] tags, Specializations[] specs) {
        super(usr, email, psw, tags);

        Collections.addAll(specializations, specs);
    }

    public Creator(String usr, String email, String psw, Tags[] tags, Specializations[] specs, int max_p) {
        this(usr, email, psw, tags, specs);

        this.maxProposals = max_p;
    }

    public ArrayList<Specializations> getSpecializations() {
        ArrayList<Specializations> clone = new ArrayList<>(this.specializations.size());
        clone.addAll(this.specializations);
        return clone;
    }

    public boolean receiveProposal(EmploymentProposal proposal) {
        if(this.proposals.size() == this.maxProposals) {
            return false;
        }

        this.proposals.add(proposal);
        return true;
    }


    private ArrayList<Specializations> specializations = new ArrayList<>();
    private ArrayList<EmploymentProposal> proposals = new ArrayList<>();
    private int maxProposals = 10;
}