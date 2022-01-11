package com.niccolo_zanieri_swe.model;

public class EmploymentProposal {
    public EmploymentProposal(String description, int timeLimit, float budget) {
        this.description = description;
        this.timeLimit = timeLimit;
        this.budget = budget;
    }

    private String description;
    private int timeLimit;
    private float budget;
}
