module no.njoter.metrodronev2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;


    opens no.njoter.metrodronev2 to javafx.fxml;
    exports no.njoter.metrodronev2;
}