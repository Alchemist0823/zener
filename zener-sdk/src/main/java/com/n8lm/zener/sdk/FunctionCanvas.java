package com.n8lm.zener.sdk;

import com.n8lm.zener.math.CurveFunction;
import com.n8lm.zener.math.Vector2f;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2014/11/10.
 *
 * @author Alchemist
 */
public class FunctionCanvas extends Canvas {

    final static double CP_RAD = 6;
    final static double P_RAD = 8;
    final static float HEIGHT = 400;

    protected double scale;
    protected List<CurveFunction> beziers;

    protected int selectedIndex = -1;
    protected int selectedPoint = -1;

    Vector2f v = new Vector2f();

    public FunctionCanvas() {
        super(600, 600);
        this.beziers = new ArrayList<>();

        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Vector2f v = new Vector2f();
                v.set((float) mapX(event.getX()), (float) mapY(event.getY()));

                if (selectedPoint != -1 && selectedIndex != -1) {
                    int selectp = selectedPoint % beziers.get(selectedIndex).getAnchorCount();
                    int selectObj = selectedPoint / beziers.get(selectedIndex).getAnchorCount();
                    //Vector2f t = new Vector2f();
                    if (selectObj == 0) {
                        beziers.get(selectedIndex).setAnchorPoint(selectp, v);
                    } else if (selectObj == 1) {
                        beziers.get(selectedIndex).setAnchorControl1(selectp, v);
                    } else if (selectObj == 2) {
                        beziers.get(selectedIndex).setAnchorControl2(selectp, v);
                    }
                    draw();
                }
            }
        });

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    for (int k = 0; k < beziers.size(); k++) {
                        CurveFunction bezier = beziers.get(k);
                        for (int i = 0; i < bezier.getAnchorCount(); i++) {
                            bezier.getAnchor(i).getControl1().mult((float) scale, v);
                            v.y = HEIGHT - v.y;
                            if (v.x - CP_RAD <= event.getX() && event.getX() <= v.x + CP_RAD
                                    && v.y - CP_RAD <= event.getY() && event.getY() <= v.y + CP_RAD) {
                                selectedPoint = i + bezier.getAnchorCount();
                                selectedIndex = k;
                                bezier.getAnchor(i).setSelected1(true);
                                return;
                            }

                            bezier.getAnchor(i).getControl2().mult((float) scale, v);
                            v.y = HEIGHT - v.y;
                            if (v.x - CP_RAD <= event.getX() && event.getX() <= v.x + CP_RAD
                                    && v.y - CP_RAD <= event.getY() && event.getY() <= v.y + CP_RAD) {
                                selectedPoint = i + bezier.getAnchorCount() * 2;
                                selectedIndex = k;
                                bezier.getAnchor(i).setSelected2(true);
                                return;
                            }

                            bezier.getAnchor(i).getPoint().mult((float) scale, v);
                            v.y = HEIGHT - v.y;
                            if (v.x - P_RAD <= event.getX() && event.getX() <= v.x + P_RAD
                                    && v.y - P_RAD <= event.getY() && event.getY() <= v.y + P_RAD) {
                                selectedPoint = i;
                                selectedIndex = k;
                                bezier.getAnchor(i).setSelectedP(true);
                                return;
                            }
                        }
                    }
                }
                //System.out.println(selected);
            }
        });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (selectedIndex >= 0) {
                        int selectp = selectedPoint % beziers.get(selectedIndex).getAnchorCount();
                        int selectObj = selectedPoint / beziers.get(selectedIndex).getAnchorCount();
                        if (selectObj == 0) {
                            beziers.get(selectedIndex).getAnchor(selectp).setSelectedP(false);
                        } else if (selectObj == 1) {
                            beziers.get(selectedIndex).getAnchor(selectp).setSelected1(false);
                        } else if (selectObj == 2) {
                            beziers.get(selectedIndex).getAnchor(selectp).setSelected2(false);
                        }
                    }
                    selectedPoint = -1;
                    selectedIndex = -1;
                    draw();
                }
            }
        });
    }

    public void updateScale() {
        if (beziers.isEmpty())
            return;
        float endX = beziers.get(0).getEndX(), startX = beziers.get(0).getStartX();
        for (CurveFunction bezier : beziers) {
            if (bezier.getEndX() > endX)
                endX = bezier.getEndX();
            if (bezier.getStartX() > startX)
                startX = bezier.getStartX();
        }
        if (endX > startX)
            this.scale = 600 / (endX - startX);
        else
            this.scale = 1;
        draw();
    }

    public void draw() {

        GraphicsContext gc = this.getGraphicsContext2D();
        //gc.getTransform().setToIdentity();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        for (CurveFunction bezier : beziers) {
            if (bezier.getAnchorCount() == 0)
                continue;
            gc.beginPath();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(5);

            float startX = bezier.getStartX();
            gc.moveTo(startX * scale, HEIGHT - bezier.getYfromX(startX) * scale);
            for (float x = (float) (startX + 1 / scale); x < bezier.getEndX(); x += 1 / scale) {
                gc.lineTo(x * scale, HEIGHT - bezier.getYfromX(x) * scale);
            }
            //gc.closePath();
            gc.stroke();

            gc.setStroke(Color.STEELBLUE);

            for (int i = 0; i < bezier.getAnchorCount(); i++) {
                double x1 = bezier.getAnchor(i).getControl1().x * scale;
                double y1 = HEIGHT - bezier.getAnchor(i).getControl1().y * scale;
                double x2 = bezier.getAnchor(i).getControl2().x * scale;
                double y2 = HEIGHT - bezier.getAnchor(i).getControl2().y * scale;
                gc.strokeLine(x1, y1, x2, y2);
            }

            gc.setFill(Color.BLUE);

            for (int i = 0; i < bezier.getAnchorCount(); i++) {
                double x = bezier.getAnchor(i).getPoint().x * scale;
                double y = HEIGHT - bezier.getAnchor(i).getPoint().y * scale;
                gc.fillOval(x - P_RAD, y - P_RAD, P_RAD * 2, P_RAD * 2);
                if (bezier.getAnchor(i).isSelectedP())
                    gc.strokeOval(x - P_RAD, y - P_RAD, P_RAD * 2, P_RAD * 2);
            }

            gc.setFill(Color.INDIANRED);

            for (int i = 0; i < bezier.getAnchorCount(); i++) {
                double x = bezier.getAnchor(i).getControl1().x * scale;
                double y = HEIGHT - bezier.getAnchor(i).getControl1().y * scale;
                gc.fillOval(x - CP_RAD, y - CP_RAD, CP_RAD * 2, CP_RAD * 2);
                if (bezier.getAnchor(i).isSelected1())
                    gc.strokeOval(x - CP_RAD, y - CP_RAD, CP_RAD * 2, CP_RAD * 2);

                x = bezier.getAnchor(i).getControl2().x * scale;
                y = HEIGHT - bezier.getAnchor(i).getControl2().y * scale;
                gc.fillOval(x - CP_RAD, y - CP_RAD, CP_RAD * 2, CP_RAD * 2);
                if (bezier.getAnchor(i).isSelected2())
                    gc.strokeOval(x - CP_RAD, y - CP_RAD, CP_RAD * 2, CP_RAD * 2);
            }
        }
    }

    private double mapX(double x) {
        return x / scale;
    }

    private double mapY(double y) {
        return (HEIGHT - y) / scale;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
        this.draw();
    }

    public void replace(CurveFunction oldValue, CurveFunction newValue) {
        for (int i = 0; i < beziers.size(); i++)
            if (beziers.get(i) == oldValue) {
                beziers.set(i, newValue);
                break;
            }
        updateScale();
    }

    public void addFunction(CurveFunction functionValue) {
        beziers.add(functionValue);
        updateScale();
    }
}
