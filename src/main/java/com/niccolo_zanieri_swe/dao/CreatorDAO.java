package com.niccolo_zanieri_swe.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatorDAO {
    public CreatorDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public boolean insertUser(String usr, String email, String psw) {
        boolean result = false;
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new RuntimeException("There are no available connections.");
            }

            Statement stmt = c.createStatement();
            String sql = "insert into creator values('" + usr + "', '" + email + "', '" + psw + "');";
            stmt.executeUpdate(sql);
            stmt.close();
            result = true;
        } catch(Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }

        return result;
    }


    private ConnectionPool pool;
}
