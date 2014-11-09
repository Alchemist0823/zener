package com.n8lm.zener.sdk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by sunz5 on 11/7/14.
 */
public class MainWindow extends VBox {

    @FXML private AnchorPane mainArea;

    private Workbench workbench;

    public MainWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-window.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initializeComponents();
    }

    private void initializeComponents() {
        workbench = new Workbench();
        mainArea.getChildren().add(workbench);
        mainArea.setBottomAnchor(workbench, 0.0);
        mainArea.setTopAnchor(workbench, 0.0);
        mainArea.setLeftAnchor(workbench, 0.0);
        mainArea.setRightAnchor(workbench, 0.0);
    }

    @FXML
    private void onNewFile() {
        workbench.generateNewViewer();
    }
}
