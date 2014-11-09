package com.n8lm.zener.sdk;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by sunz5 on 11/7/14.
 */
public class Workbench extends TabPane {


    public Workbench() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("workbench.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void generateNewViewer() {
        Tab tab = new Tab("new tab", new Rectangle(200, 200, Color.LIGHTSTEELBLUE));
        this.getTabs().add(tab);
    }

}
