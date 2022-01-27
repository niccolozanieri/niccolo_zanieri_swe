package com.niccolo_zanieri_swe;

import com.niccolo_zanieri_swe.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class SessionController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(creatorClientChB.getItems().isEmpty()) {
            creatorClientChB.getItems().addAll(userTypes);
        }
    }

    @FXML
    public void switchToSignup(MouseEvent event) throws IOException {
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
    public void switchToSuccessSignupCr() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("successful_signup_cr.fxml")));
        stage = (Stage)(signupUserTF.getScene().getWindow());
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("successful_signup.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToSuccessSignupCl() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("successful_signup_cl.fxml")));
        stage = (Stage)(signupUserTF.getScene().getWindow());
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("successful_signup.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToSignupError() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signup_error.fxml")));
        stage = (Stage) (signupUserTF.getScene().getWindow());
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("session_error.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToLoginError() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login_error.fxml")));
        stage = (Stage) (loginUserTF.getScene().getWindow());
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("session_error.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToSuccLogin(String usr) throws IOException {  // FIXME: method not working, can't load label
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("successful_login.fxml")));
        root = loader.load();
        SessionController sessionCtrl = loader.getController();
        sessionCtrl.succLoginLabel.setText("Welcome back " + usr + "!!");
        stage = (Stage) (loginUserTF.getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void submitForm(MouseEvent event) throws IOException {
        if(creatorClientChB.getValue().equals("Creator")) {
            try {
                CreatorAdminController creatorCtrl = new CreatorAdminController();
                creatorCtrl.registerCreator(signupUserTF.getText(), signupEmailTF.getText(), sigupPswTF.getText());
                switchToSuccessSignupCr();
            } catch(SQLException e) {
                switchToSignupError();
            }
        } else {
            try {
                ClientAdminController clientCtrl = new ClientAdminController();
                clientCtrl.registerClient(signupUserTF.getText(), signupEmailTF.getText(), sigupPswTF.getText());
                switchToSuccessSignupCl();
            } catch(SQLException e) {
                switchToSignupError();
            }
        }
    }

    @FXML
    public void login(MouseEvent event) throws IOException {
        User user = null;
        try {
            CreatorAdminController creatorCtrl = new CreatorAdminController();
            user = creatorCtrl.findCreator(loginUserTF.getText(), loginPswTF.getText());
            switchToSuccLogin(loginUserTF.getText());
        } catch(SQLException e) {
            switchToLoginError();
        } catch(IllegalArgumentException e) {
            try {
                ClientAdminController clientCtrl = new ClientAdminController();
                user = clientCtrl.findClient(loginUserTF.getText(), loginPswTF.getText());
                switchToSuccLogin(loginUserTF.getText());
            } catch(SQLException | IllegalArgumentException e1) {
                switchToLoginError();
            }
        }
    }



    @FXML private ChoiceBox<String> creatorClientChB = new ChoiceBox<>();
    private String[] userTypes = {"Creator", "Client"};

    @FXML private TextField signupUserTF;
    @FXML private TextField signupEmailTF;
    @FXML private TextField sigupPswTF;

    @FXML private TextField loginUserTF;
    @FXML private TextField loginPswTF;

    @FXML private Label succLoginLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;
}
