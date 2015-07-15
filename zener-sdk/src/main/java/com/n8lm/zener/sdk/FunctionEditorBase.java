package com.n8lm.zener.sdk;

import com.n8lm.zener.math.CurveFunction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Created on 2014/11/11.
 *
 * @author Alchemist
 */
public class FunctionEditorBase extends BorderPane {

    protected FunctionCanvas canvas;
    protected TextField scaleText;
    protected Canvas rulerh;
    protected Canvas rulerv;

    protected double scale;

    private void setScale(double scale) {
        if (scale == 0)
            scale = 1;
        this.scale = scale;

        GraphicsContext gc = rulerh.getGraphicsContext2D();

        gc.clearRect(0, 0, rulerh.getWidth(), rulerh.getHeight());
        double y = (400 / scale / 8);
        double x = 10000;
        while (x > y)
            x /= 10;
        x = x * 10 / 4;
        for (double i = 0 ; i < 400 / scale; i += x)
            gc.strokeText(String.format("%.2f", i), 0, 400 - i * scale);


        gc = rulerv.getGraphicsContext2D();

        gc.clearRect(0, 0, rulerv.getWidth(), rulerv.getHeight());
        y = (600 / scale / 8);
        x = 10000;
        while (x > y)
            x /= 10;
        x = x * 10 / 4;
        for (double i = 0 ; i < rulerv.getWidth() / scale; i += x)
            gc.strokeText(String.format("%.2f", i), 30 + i * scale, 10);

        canvas.setScale(scale);
    }


    public FunctionEditorBase() {
        super();

        ArrayList<CurveFunction> beziers = new ArrayList<>();

        this.canvas = new FunctionCanvas();
        this.canvas.setWidth(600);
        this.canvas.setHeight(450);

        this.rulerh = new Canvas(30, 450);
        this.rulerv = new Canvas(600, 20);

        scaleText = new TextField("" + canvas.getScale());
        scaleText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setScale(Double.parseDouble(scaleText.getText()));

            }
        });

        HBox hbox = new HBox(rulerh, canvas);
        VBox vbox = new VBox(hbox, rulerv);

        this.setTop(scaleText);
        this.setCenter(vbox);

        setScale(canvas.getScale());
        //this.setLeft(rulerh);
        //this.canvas.setWidth();
        //this.canvas.setHeight();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        rulerh.setHeight(this.getHeight() - 100);
        canvas.setWidth(this.getWidth() - 50);
        canvas.setHeight(this.getHeight() - 100);
        rulerv.setWidth(this.getWidth() - 50);
    }

}
