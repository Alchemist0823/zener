package com.n8lm.zener.sdk;

import com.n8lm.zener.math.BezierFunction;
import com.n8lm.zener.math.CurveAnchor2f;
import com.n8lm.zener.math.Vector2f;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
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
        Tab tab = new Tab("new tab");

        BezierFunction bezier = new BezierFunction();
        bezier.addAnchor(new CurveAnchor2f(new Vector2f(0.0f, 0.0f), new Vector2f(-1.0f, 0.0f)));
        bezier.addAnchor(new CurveAnchor2f(new Vector2f(2.0f, 2.0f), new Vector2f(1.5f, 2.0f)));
        bezier.addAnchor(new CurveAnchor2f(new Vector2f(3.0f, 0.0f), new Vector2f(2.0f, 0.0f)));
        bezier.addAnchor(new CurveAnchor2f(new Vector2f(5.0f, 1.0f), new Vector2f(4.0f, 2.0f)));

        FunctionCanvas canvas = new FunctionCanvas(bezier, 100);
        //gc.scale(100f, 100f);
        //Pane pane = new Pane(canvas);
        //pane.setPrefSize(300, 300);
        tab.setContent(canvas);
        //tab.setContent(new Rectangle(500, 500, Color.LIGHTSTEELBLUE));
        this.getTabs().add(tab);
    }

}
