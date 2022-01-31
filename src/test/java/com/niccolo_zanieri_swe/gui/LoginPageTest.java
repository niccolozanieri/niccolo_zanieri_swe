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
class LoginPageTest {

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
        Parent mainNode = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("login_page.fxml")));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testLoginToSignup(FxRobot robot) {
        robot.clickOn("#signUpLabel");
        Assertions.assertThat(robot.lookup("#submitButton").queryAs(Button.class)).hasText("Submit");
    }

    @Test
    public void testCreatorSuccLogin(FxRobot robot) {
        crDao = new CreatorDAO(pool);
        try {
            if(crDao.findCreator("test_usr") == null) {
                crDao.insertCreator("test_usr", "test_email", "test_psw");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        robot.clickOn("#loginUserTF");
        robot.write("test_usr");
        robot.clickOn("#loginPswTF");
        robot.write("test_psw");
        robot.clickOn("#loginButton");
        Assertions.assertThat(robot.lookup("#succLoginLabel").queryAs(Label.class)).hasText("Welcome back test_usr!!");
    }

    @Test
    public void testCreatorFailLogin(FxRobot robot) {
        crDao = new CreatorDAO(pool);
        clDao = new ClientDAO(pool);
        try {
            if(crDao.findCreator("test_usr") != null) {
                crDao.removeCreator("test_usr");
            }
            if(clDao.findClient("test_usr") != null) {
                clDao.removeClient("test_usr");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        robot.clickOn("#loginUserTF");
        robot.write("test_usr");
        robot.clickOn("#loginPswTF");
        robot.write("test_psw");
        robot.clickOn("#loginButton");
        Assertions.assertThat(robot.lookup("#toLoginLabel").queryAs(Label.class)).hasText("Log in page");
    }

    @Test
    public void testClientSuccLogin(FxRobot robot) {
        clDao = new ClientDAO(pool);
        try {
            if(clDao.findClient("test_usr") == null) {
                clDao.insertClient("test_usr", "test_email", "test_psw");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        robot.clickOn("#loginUserTF");
        robot.write("test_usr");
        robot.clickOn("#loginPswTF");
        robot.write("test_psw");
        robot.clickOn("#loginButton");
        Assertions.assertThat(robot.lookup("#succLoginLabel").queryAs(Label.class)).hasText("Welcome back test_usr!!");
    }

    @Test
    public void testClientFailLogin(FxRobot robot) {
        clDao = new ClientDAO(pool);
        crDao = new CreatorDAO(pool);
        try {
            if(clDao.findClient("test_usr") != null) {
                clDao.removeClient("test_usr");
            }
            if(crDao.findCreator("test_usr") != null) {
                crDao.removeCreator("test_usr");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        robot.clickOn("#loginUserTF");
        robot.write("test_usr");
        robot.clickOn("#loginPswTF");
        robot.write("test_psw");
        robot.clickOn("#loginButton");
        Assertions.assertThat(robot.lookup("#toLoginLabel").queryAs(Label.class)).hasText("Log in page");
    }



}