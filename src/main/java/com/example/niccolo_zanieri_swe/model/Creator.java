package com.example.niccolo_zanieri_swe.model;

import java.util.ArrayList;
import java.util.Collections;

enum Specializations {
    FILMMAKER, PHOTOGRAPHER, GRAPHIC_DESIGNER
}

public class Creator extends User {
    public Creator(String usr, String email, String psw) {
        super(usr, email, psw);
    }

    public Creator(String usr, String email, String psw, Tags[] tags, Specializations[] specs) {
        super(usr, email, psw, tags);

        Collections.addAll(specializations, specs);
    }

    public ArrayList<Specializations> getSpecializations() {
        return specializations;
    }

    private ArrayList<Specializations> specializations = new ArrayList<>();
}