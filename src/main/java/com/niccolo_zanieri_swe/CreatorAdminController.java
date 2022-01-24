package com.niccolo_zanieri_swe;

import com.niccolo_zanieri_swe.dao.ConnectionPool;
import com.niccolo_zanieri_swe.dao.CreatorDAO;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.SQLException;

public class CreatorAdminController {    // TODO: create pages to show errors
    public CreatorAdminController() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/swe_exam_db";
        String usr = "niccolo_zanieri";
        String psw = "basidati2022";
        this.dao = new CreatorDAO(ConnectionPool.getConnectionPool(url, usr, psw));


    }
    
    @FXML
    public void registerCreator(String usr, String email, String psw) throws IOException, SQLException {
        this.dao.insertCreator(usr, email, psw);
    }

    private CreatorDAO dao;
}
