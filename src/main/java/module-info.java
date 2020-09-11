module CS_Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    // Unit Testing
//    requires org.junit.jupiter;
//    requires org.junit.jupiter.api;

    opens com.darkzek.goldenratio to javafx.fxml;
    opens com.darkzek.goldenratio.ui to javafx.fxml;
    exports com.darkzek.goldenratio;
    exports com.darkzek.goldenratio.ui;
}