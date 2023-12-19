package no.njoter.metrodronev2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.njoter.metrodronev2.global.GlobalSynth;
import no.njoter.metrodronev2.view.MainView;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        GlobalSynth.setSynths();

        Scene scene = new Scene(new MainView());
        scene.getStylesheets().add("stylesheet.css");

        stage.setScene(scene);
        stage.setWidth(1024);
        stage.setHeight(860);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        GlobalSynth.closeSynth();
    }
}