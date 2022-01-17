package com.niccolo_zanieri_swe.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatorDAO {
    public CreatorDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public boolean insertCreator(String usr, String email, String psw) {
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
            result = true;
            stmt.close();
        } catch(SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }

        return result;
    }

    public boolean removeCreator(String usr) {
        boolean result = false;
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new RuntimeException("There are no available connections.");
            }

            Statement stmt = c.createStatement();
            String sql = "delete from creator where username = '" + usr + "';";
            stmt.executeUpdate(sql);
            result = true;
            stmt.close();
        } catch(SQLException e) {
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
