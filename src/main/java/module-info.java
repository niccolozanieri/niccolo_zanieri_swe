module com.example.niccolo_zanieri_swe {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;


    opens com.niccolo_zanieri_swe to javafx.fxml;
    exports com.niccolo_zanieri_swe;
    exports com.niccolo_zanieri_swe.dao;
}