package com.n8lm.zener.sdk;

import com.n8lm.zener.math.EditableCurveFunction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Created on 2014/11/11.
 *
 * @author Alchemist
 */
public class FunctionEditorBase extends BorderPane {

    protected EditableFunctionView functionView;
    protected TextField scaleText;
    protected Canvas rulerh;
    protected Canvas rulerv;

    protected float scale;

    private void setScale(float functionScaleX, float functionScaleY) {
        if (functionScaleX == 0)
            functionScaleX = 1;
        this.scale = functionScaleX;

        GraphicsContext gc = rulerh.getGraphicsContext2D();

        gc.clearRect(0, 0, rulerh.getWidth(), rulerh.getHeight());
        double y = (rulerh.getHeight() / functionScaleY / 8);
        double x = 10000;
        while (x > y)
            x /= 10;
        x = x * 10 / 4;
        for (double i = 0; i < rulerh.getHeight() / functionScaleY; i += x)
            gc.strokeText(String.format("%.2f", i), 0, rulerh.getHeight() - i * functionScaleY);


        gc = rulerv.getGraphicsContext2D();

        gc.clearRect(0, 0, rulerv.getWidth(), rulerv.getHeight());
        y = (rulerh.getWidth() / functionScaleX * 2);
        x = 10000;
        while (x > y)
            x /= 10;
        x = x * 10 / 2;
        for (double i = 0; i < rulerv.getWidth() / functionScaleX; i += x)
            gc.strokeText(String.format("%.2f", i), 30 + i * functionScaleX, 10);

        functionView.setFunctionScaleX(functionScaleX);
    }


    public FunctionEditorBase() {
        super();

        ArrayList<EditableCurveFunction> beziers = new ArrayList<>();

        this.functionView = new EditableFunctionView();

        this.rulerh = new Canvas(30, 100);
        rulerh.heightProperty().bind(functionView.heightProperty());
        this.rulerv = new Canvas(200, 20);
        rulerv.widthProperty().bind(functionView.widthProperty());

        scaleText = new TextField("" + functionView.getFunctionScaleX());
        scaleText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setScale(Float.parseFloat(scaleText.getText()), functionView.getFunctionScaleY());

            }
        });

        HBox hbox = new HBox(rulerh, functionView);
        hbox.setFillHeight(true);
        HBox.setHgrow(functionView, Priority.ALWAYS);

        VBox vbox = new VBox(hbox, rulerv);
        vbox.setFillWidth(true);
        VBox.setVgrow(hbox, Priority.ALWAYS);

        this.setTop(scaleText);
        this.setCenter(vbox);

        //this.setLeft(rulerh);
        //this.functionView.setWidth();
        //this.functionView.setHeight();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        setScale(functionView.getFunctionScaleX(), functionView.getFunctionScaleY());

//        System.out.println(functionView.getHeight());
//        System.out.println(functionView.getWidth());
        /*
        rulerh.setHeight(this.getHeight() - 100);
        functionView.setWidth(this.getWidth() - 50);
        functionView.setHeight(this.getHeight() - 100);
        rulerv.setWidth(this.getWidth() - 50);*/
    }

}
