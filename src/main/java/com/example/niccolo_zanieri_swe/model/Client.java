package com.example.niccolo_zanieri_swe.model;

import java.util.Collections;

public class Client extends User {
    public Client(String usr, String email, String psw) {
        super(usr, email, psw);
    }

    public Client(String usr, String email, String psw, Tags[] tags) {
        super(usr, email, psw, tags);
    }
}
