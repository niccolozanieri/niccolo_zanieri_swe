module com.example.niccolo_zanieri_swe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.niccolo_zanieri_swe to javafx.fxml;
    exports com.example.niccolo_zanieri_swe;
    exports com.example.niccolo_zanieri_swe.controller;
    opens com.example.niccolo_zanieri_swe.controller to javafx.fxml;
}