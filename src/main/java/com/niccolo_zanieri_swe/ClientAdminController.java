package com.niccolo_zanieri_swe;

import com.niccolo_zanieri_swe.dao.ClientDAO;
import com.niccolo_zanieri_swe.dao.ConnectionPool;
import com.niccolo_zanieri_swe.dao.CreatorDAO;
import com.niccolo_zanieri_swe.model.Client;
import com.niccolo_zanieri_swe.model.Creator;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ClientAdminController {

    public ClientAdminController() throws SQLException{
        String url = "jdbc:postgresql://localhost:5432/swe_exam_db";
        String usr = "niccolo_zanieri";
        String psw = "basidati2022";
        this.dao = new ClientDAO(ConnectionPool.getConnectionPool(url, usr, psw));

    }

    @FXML
    public void registerClient(String usr, String email, String psw) throws IOException, SQLException {
        this.dao.insertClient(usr, email, psw);
    }

    @FXML
    public Client findClient(String usr, String psw) throws SQLException, IllegalArgumentException {
        Client client = this.dao.findClient(usr);
        if(client != null && !client.getPassword().equals(psw)) {
            throw new IllegalArgumentException("Entered password is incorrect.");
        } else if(client == null) {
            throw new IllegalArgumentException("Entered username doesn't exist.");
        } else {
            return client;
        }
    }

    private ClientDAO dao;
    private Stage stage;
    private Scene scene;
    private Parent root;
}
