package com.niccolo_zanieri_swe.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
    public UserDAO(ConnectionPool pool) {
        this.pool = pool;
    }



    private ConnectionPool pool;
}
