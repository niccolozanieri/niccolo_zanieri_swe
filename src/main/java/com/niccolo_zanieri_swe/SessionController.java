package com.niccolo_zanieri_swe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SessionController implements Initializable {

    @FXML private ChoiceBox<String> creatorClientChB = new ChoiceBox<>();
    private String[] userTypes = {"Creator", "Client"};

    @FXML private TextField userTF;
    @FXML private TextField emailTF;
    @FXML private TextField pswTF;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(creatorClientChB.getItems().isEmpty()) {
            creatorClientChB.getItems().addAll(userTypes);
        }
    }

    @FXML
    public void switchToSignUp(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signup_page.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("signup_page.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToLogin(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login_page.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("login_page.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void submitForm(MouseEvent event) throws IOException {
        if(creatorClientChB.getValue().equals("Creator")) {
            CreatorAdminController creatorCtrl = new CreatorAdminController();
            creatorCtrl.registerCreator(userTF.getText(), emailTF.getText(), pswTF.getText());
        } else {
            ClientAdminController clientCtrl = new ClientAdminController();
            clientCtrl.registerClient(userTF.getText(), emailTF.getText(), pswTF.getText());
        }
    }
}
