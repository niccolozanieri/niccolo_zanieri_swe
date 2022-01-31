package com.niccolo_zanieri_swe.gui;

import com.niccolo_zanieri_swe.MainApp;
import com.niccolo_zanieri_swe.dao.ClientDAO;
import com.niccolo_zanieri_swe.dao.ConnectionPool;
import com.niccolo_zanieri_swe.dao.CreatorDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;
import java.util.Objects;

@ExtendWith(ApplicationExtension.class)
public class SignupPageTest {

    private ConnectionPool pool;
    private CreatorDAO crDao;
    private ClientDAO clDao;

    @Start
    public void start(Stage stage) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/swe_exam_db";
        String usr = "niccolo_zanieri";
        String psw = "basidati2022";

        try {
            pool = ConnectionPool.getConnectionPool(url, usr, psw);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        Parent mainNode = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("signup_page.fxml")));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testSignupToLogin(FxRobot robot) {
        robot.clickOn("#loginLabel");
        Assertions.assertThat(robot.lookup("#loginButton").queryAs(Button.class)).hasText("Login");
    }

    @Test
    public void testCreatorSuccSignupSubmission(FxRobot robot) {
        crDao = new CreatorDAO(pool);
        try {
            if(crDao.findCreator("test_usr") != null) {
                crDao.removeCreator("test_usr");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        robot.clickOn("#signupUserTF");
        robot.write("test_usr");
        robot.clickOn("#signupEmailTF");
        robot.write("test_email");
        robot.clickOn("#signupPswTF");
        robot.write("test_psw");
        robot.clickOn("#submitButton");
        Assertions.assertThat(robot.lookup("#succCrSignupLabel").queryAs(Label.class)).hasText("Go and start creating:");
        try {
            Assertions.assertThat(crDao.findCreator("test_usr").getUsername()).isEqualTo("test_usr");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        robot.clickOn("#toLoginLabel");
        Assertions.assertThat(robot.lookup("#loginButton").queryAs(Button.class)).hasText("Login");
    }

    @Test
    public void testCreatorFailSignupSubmission(FxRobot robot) {
        crDao = new CreatorDAO(pool);
        try {
            if(crDao.findCreator("test_usr") == null) {
                crDao.insertCreator("test_usr", "test_email", "test_psw");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        robot.clickOn("#signupUserTF");
        robot.write("test_usr");
        robot.clickOn("#signupEmailTF");
        robot.write("test_email");
        robot.clickOn("#signupPswTF");
        robot.write("test_psw");
        robot.clickOn("#submitButton");
        Assertions.assertThat(robot.lookup("#signupTryAgainLabel").queryAs(Label.class)).hasText("You can go back and try again:");
        robot.clickOn("#toSignUpLabel");
        Assertions.assertThat(robot.lookup("#submitButton").queryAs(Button.class)).hasText("Submit");
    }

    @Test
    public void testClientSuccSignupSubmission(FxRobot robot) {
        clDao = new ClientDAO(pool);
        try {
            if(clDao.findClient("test_usr") != null) {
                clDao.removeClient("test_usr");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        robot.clickOn("#creatorClientChB");
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#signupUserTF");
        robot.write("test_usr");
        robot.clickOn("#signupEmailTF");
        robot.write("test_email");
        robot.clickOn("#signupPswTF");
        robot.write("test_psw");
        robot.clickOn("#submitButton");
        Assertions.assertThat(robot.lookup("#succClSignupLabel").queryAs(Label.class)).hasText("Let's chase the creator you need:");
        try {
            Assertions.assertThat(clDao.findClient("test_usr").getUsername()).isEqualTo("test_usr");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        robot.clickOn("#toLoginLabel");
        Assertions.assertThat(robot.lookup("#loginButton").queryAs(Button.class)).hasText("Login");
    }

    @Test
    public void testClientFailSignupSubmission(FxRobot robot) {
        clDao = new ClientDAO(pool);
        try {
            if(clDao.findClient("test_usr") == null) {
                clDao.insertClient("test_usr", "test_email", "test_psw");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        robot.clickOn("#creatorClientChB");
        robot.press(KeyCode.DOWN).release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#signupUserTF");
        robot.write("test_usr");
        robot.clickOn("#signupEmailTF");
        robot.write("test_email");
        robot.clickOn("#signupPswTF");
        robot.write("test_psw");
        robot.clickOn("#submitButton");
        Assertions.assertThat(robot.lookup("#signupTryAgainLabel").queryAs(Label.class)).hasText("You can go back and try again:");
        robot.clickOn("#toSignUpLabel");
        Assertions.assertThat(robot.lookup("#submitButton").queryAs(Button.class)).hasText("Submit");
    }


}
