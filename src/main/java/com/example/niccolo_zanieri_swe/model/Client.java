package com.example.niccolo_zanieri_swe.model;

import java.util.ArrayList;
import java.util.Collections;

public class Client extends User {
    public Client(String usr, String email, String psw) {
        super(usr, email, psw);
    }

    public Client(String usr, String email, String psw, Tags[] tags) {
        super(usr, email, psw, tags);
    }

    public void sendEmploymentProposal(String desc, int timeLim, float budget, Creator c) {
        EmploymentProposal e = new EmploymentProposal(desc, timeLim, budget);

        if(c.receiveProposal(e)) {
            this.proposals.add(e);
        } 
    }

    private ArrayList<EmploymentProposal> proposals = new ArrayList<>();
}
