package com.n8lm.zener.sdk;

import com.n8lm.zener.math.CurveFunction;
import com.n8lm.zener.math.Range;
import com.n8lm.zener.math.Vector2f;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2014/11/10.
 *
 * @author Alchemist
 */
public class FunctionView extends Pane {

    final static double CP_RAD = 5;
    final static double P_RAD = 6;

    protected float functionScaleX;
    protected float functionScaleY;
    protected List<CurveFunction> beziers;

    protected int selectedIndex = -1;
    protected int selectedPoint = -1;

    protected Canvas canvas;

    Vector2f v = new Vector2f();

    public FunctionView() {
        this.canvas = new Canvas();
        this.setMinHeight(50);
        this.setMinWidth(200);
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
        canvas.widthProperty().addListener(observable -> {
            updateScale();
            draw();
        });
        canvas.heightProperty().addListener(observable -> {
            updateScale();
            draw();
        });
        getChildren().add(canvas);
        this.beziers = new ArrayList<>();

    }

    public void updateScale() {
        if (beziers.isEmpty())
            return;

        Range rangeX = new Range(Float.MAX_VALUE, Float.MIN_VALUE);
        Range rangeY = new Range(Float.MAX_VALUE, Float.MIN_VALUE);
        Range r = new Range();
        for (CurveFunction bezier : beziers) {
            rangeX.combine(bezier.getXBound(r));
            rangeY.combine(bezier.getYBound(r));
        }
        if (rangeX.u > rangeX.l)
            this.functionScaleX = (float) (canvas.getWidth() / (rangeX.u - rangeX.l));
        else
            this.functionScaleX = 1;
        if (rangeY.u > rangeY.l)
            this.functionScaleY = (float) (canvas.getHeight() / (rangeY.u - rangeY.l));
        else
            this.functionScaleY = 1;
        draw();
    }

    protected void draw() {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        //gc.getTransform().setToIdentity();
        gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

        gc.setFill(Color.LIGHTYELLOW);
        gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

        for (CurveFunction bezier : beziers) {
            if (bezier.getAnchorCount() == 0)
                continue;
            gc.beginPath();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);

            Range range = bezier.getXBound(null);
            gc.moveTo(range.l * functionScaleX, canvas.getHeight() - bezier.getYFromX(range.l) * functionScaleY);
            for (float x = range.l + 2 / functionScaleX; x < range.u; x += 2 / functionScaleX) {
                gc.lineTo(x * functionScaleX, canvas.getHeight() - bezier.getYFromX(x) * functionScaleY);
            }
            gc.stroke();

            gc.setStroke(Color.STEELBLUE);
            for (int i = 0; i < bezier.getAnchorCount(); i++) {
                double x1 = bezier.getAnchor(i).getControl1().x * functionScaleX;
                double y1 = canvas.getHeight() - bezier.getAnchor(i).getControl1().y * functionScaleY;
                double x2 = bezier.getAnchor(i).getControl2().x * functionScaleX;
                double y2 = canvas.getHeight() - bezier.getAnchor(i).getControl2().y * functionScaleY;
                gc.strokeLine(x1, y1, x2, y2);
            }

            gc.setFill(Color.BLUE);
            for (int i = 0; i < bezier.getAnchorCount(); i++) {
                double x = bezier.getAnchor(i).getPoint().x * functionScaleX;
                double y = canvas.getHeight() - bezier.getAnchor(i).getPoint().y * functionScaleY;
                gc.fillOval(x - P_RAD, y - P_RAD, P_RAD * 2, P_RAD * 2);
                //if (bezier.getEditData(i).isSelectedP())
                //    gc.strokeOval(x - P_RAD, y - P_RAD, P_RAD * 2, P_RAD * 2);
            }

            gc.setFill(Color.INDIANRED);
            for (int i = 0; i < bezier.getAnchorCount(); i++) {
                double x = bezier.getAnchor(i).getControl1().x * functionScaleX;
                double y = canvas.getHeight() - bezier.getAnchor(i).getControl1().y * functionScaleY;
                gc.fillOval(x - CP_RAD, y - CP_RAD, CP_RAD * 2, CP_RAD * 2);
                //if (bezier.getEditData(i).isSelected1())
                //    gc.strokeOval(x - CP_RAD, y - CP_RAD, CP_RAD * 2, CP_RAD * 2);

                x = bezier.getAnchor(i).getControl2().x * functionScaleX;
                y = canvas.getHeight() - bezier.getAnchor(i).getControl2().y * functionScaleY;
                gc.fillOval(x - CP_RAD, y - CP_RAD, CP_RAD * 2, CP_RAD * 2);
                //if (bezier.getEditData(i).isSelected2())
                //    gc.strokeOval(x - CP_RAD, y - CP_RAD, CP_RAD * 2, CP_RAD * 2);
            }
        }
    }

    protected double mapX(double x) {
        return x / functionScaleX;
    }

    protected double mapY(double y) {
        return (canvas.getHeight() - y) / functionScaleY;
    }

    protected void mapToScreen(Vector2f point, Vector2f screen) {
        screen.x = point.x * functionScaleX;
        screen.y = (float) (canvas.getHeight()) - point.y * functionScaleY;
    }

    public float getFunctionScaleX() {
        return functionScaleX;
    }

    public float getFunctionScaleY() {
        return functionScaleY;
    }

    public void setFunctionScaleX(float functionScaleX) {
        this.functionScaleX = functionScaleX;
        this.draw();
    }

    public void setFunctionScaleY(float functionScaleY) {
        this.functionScaleY = functionScaleY;
        this.draw();
    }

    public void replace(CurveFunction oldValue, CurveFunction newValue) {
        for (int i = 0; i < beziers.size(); i++)
            if (beziers.get(i) == oldValue) {
                beziers.set(i, newValue);
                updateScale();
                break;
            }
    }

    public void addFunction(CurveFunction functionValue) {
        beziers.add(functionValue);
        updateScale();
    }

    public void removeFunction(CurveFunction functionValue) {
        beziers.remove(functionValue);
        updateScale();
    }
}
