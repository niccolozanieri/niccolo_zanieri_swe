package com.niccolo_zanieri_swe;

import com.niccolo_zanieri_swe.dao.ConnectionPool;
import com.niccolo_zanieri_swe.dao.CreatorDAO;
import com.niccolo_zanieri_swe.model.Creator;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class CreatorAdminController {
    public CreatorAdminController() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/swe_exam_db";
        String usr = "niccolo_zanieri";
        String psw = "basidati2022";
        this.dao = new CreatorDAO(ConnectionPool.getConnectionPool(url, usr, psw));
    }
    
    @FXML
    public void registerCreator(String usr, String email, String psw) throws SQLException {
        this.dao.insertCreator(usr, email, psw);
    }

    @FXML
    public Creator findCreator(String usr, String psw) throws SQLException, IllegalArgumentException {
        Creator creator = this.dao.findCreator(usr);
        if(creator != null && !creator.getPassword().equals(psw)) {
            throw new IllegalArgumentException("Entered password is incorrect.");
        } else if(creator == null) {
            throw new IllegalArgumentException("Entered username doesn't exist.");
        } else {
            return creator;
        }
    }

    private CreatorDAO dao;
}
