package com.niccolo_zanieri_swe;

import com.niccolo_zanieri_swe.dao.ConnectionPool;
import com.niccolo_zanieri_swe.dao.CreatorDAO;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.SQLException;

public class CreatorAdminController {    // TODO: create pages to show errors
    public CreatorAdminController() {
        String url = "jdbc:postgresql://localhost:5432/swe_exam_db";
        String usr = "niccolo_zanieri";
        String psw = "basidati2022";
        try {
            this.dao = new CreatorDAO(ConnectionPool.getConnectionPool(url, usr, psw));
        } catch(SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }
    
    @FXML
    public void registerCreator(String usr, String email, String psw) throws IOException {
        try {
            this.dao.insertCreator(usr, email, psw);
        } catch(SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }

    private CreatorDAO dao;
}
