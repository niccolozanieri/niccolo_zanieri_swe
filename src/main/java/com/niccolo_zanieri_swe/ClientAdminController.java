package com.niccolo_zanieri_swe;

import com.niccolo_zanieri_swe.dao.ClientDAO;
import com.niccolo_zanieri_swe.dao.ConnectionPool;
import com.niccolo_zanieri_swe.dao.CreatorDAO;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ClientAdminController {

    public ClientAdminController() {
        String url = "jdbc:postgresql://localhost:5432/swe_exam_db";
        String usr = "niccolo_zanieri";
        String psw = "basidati2022";
        try {
            this.dao = new ClientDAO(ConnectionPool.getConnectionPool(url, usr, psw));
        } catch(SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }

    @FXML
    public void registerClient(String usr, String email, String psw) throws IOException {
        try {
            this.dao.insertClient(usr, email, psw);
        } catch(SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }

    private ClientDAO dao;
    private Stage stage;
    private Scene scene;
    private Parent root;
}
