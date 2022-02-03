package com.niccolo_zanieri_swe.model;

import java.util.ArrayList;

public class Client extends User {
    public Client(String usr, String email, String psw) {
        super(usr, email, psw);
    }

    public Client(String usr, String email, String psw, Tags[] tags) {
        super(usr, email, psw, tags);
    }

    public boolean sendEmploymentProposal(String desc, int timeLim, float budget, Creator c) {
        EmploymentProposal e = new EmploymentProposal(desc, timeLim, budget);
        boolean result = false;

        if(c.receiveProposal(e)) {
            this.proposals.add(e);
            result = true;
        }

        return result;
    }

    private ArrayList<EmploymentProposal> proposals = new ArrayList<>();
}
