package com.n8lm.zener.sdk;

import com.n8lm.zener.math.CurveFunction;
import com.n8lm.zener.math.EditableCurveAnchor2f;
import com.n8lm.zener.math.Vector2f;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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

        CurveFunction bezier = new CurveFunction();
        bezier.addAnchor(new EditableCurveAnchor2f(new Vector2f(0.0f, 0.0f), new Vector2f(-1.0f, 0.0f)));
        bezier.addAnchor(new EditableCurveAnchor2f(new Vector2f(2.0f, 2.0f), new Vector2f(1.5f, 2.0f)));
        bezier.addAnchor(new EditableCurveAnchor2f(new Vector2f(3.0f, 0.0f), new Vector2f(2.0f, 0.0f)));
        bezier.addAnchor(new EditableCurveAnchor2f(new Vector2f(5.0f, 1.0f), new Vector2f(4.0f, 2.0f)));

        SingleFunctionEditor editor = new SingleFunctionEditor();
        editor.setFunctionValue(bezier);
        //gc.scale(100f, 100f);
        //Pane pane = new Pane(canvas);
        //pane.setPrefSize(300, 300);
        tab.setContent(editor);
        //tab.setContextMenu();
        //tab.setContent(new Rectangle(500, 500, Color.LIGHTSTEELBLUE));
        this.getTabs().add(tab);
    }

    public void generateNewColor() {
        Tab tab = new Tab("new tab");
        tab.setContent(/*new GradientEditor()*/new ParticleSystemEditor());
        this.getTabs().add(tab);
    }

}
