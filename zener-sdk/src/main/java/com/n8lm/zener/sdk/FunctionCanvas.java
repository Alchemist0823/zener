package com.n8lm.zener.sdk;

import com.n8lm.zener.math.BezierFunction;
import com.n8lm.zener.math.BezierObject2D;
import com.n8lm.zener.math.CurveAnchor2f;
import com.n8lm.zener.math.Vector2f;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import net.java.games.input.Component;

/**
 * Created on 2014/11/10.
 *
 * @author Alchemist
 */
public class FunctionCanvas extends Canvas {

    final static double CP_RAD = 6;
    final static double P_RAD = 8;
    protected double scale;
    protected BezierFunction bezier;

    protected int selected = -1;

    Vector2f v = new Vector2f();

    public FunctionCanvas(final BezierFunction bezier) {
        super(600, 600);

        this.scale =  600 / (bezier.getEndX() - bezier.getStartX());
        this.bezier = bezier;

        draw();
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Vector2f v = new Vector2f();
                v.set((float) mapX(event.getX()), (float) mapY(event.getY()));

                if (selected != -1) {
                    int selectp = selected % bezier.getAnchorCount();
                    int selectObj = selected / bezier.getAnchorCount();
                    //Vector2f t = new Vector2f();
                    if (selectObj == 0) {
                        bezier.setAnchorPoint(selectp, v);
                    } else if (selectObj == 1) {
                        bezier.setAnchorControl1(selectp, v);
                    } else if (selectObj == 2) {
                        bezier.setAnchorControl2(selectp, v);
                    }
                    draw();
                }
            }
        });

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    for (int i = 0; i < bezier.getAnchorCount(); i++) {
                        bezier.getAnchor(i).getControl1().mult((float) scale, v);
                        v.y = 400 - v.y;
                        if (v.x - CP_RAD <= event.getX() && event.getX() <= v.x + CP_RAD
                                && v.y - CP_RAD <= event.getY() && event.getY() <= v.y + CP_RAD) {
                            selected = i + bezier.getAnchorCount();
                            break;
                        }

                        bezier.getAnchor(i).getControl2().mult((float) scale, v);
                        v.y = 400 - v.y;
                        if (v.x - CP_RAD <= event.getX() && event.getX() <= v.x + CP_RAD
                                && v.y - CP_RAD <= event.getY() && event.getY() <= v.y + CP_RAD) {
                            selected = i + bezier.getAnchorCount() * 2;
                            break;
                        }

                        bezier.getAnchor(i).getPoint().mult((float) scale, v);
                        v.y = 400 - v.y;
                        if (v.x - P_RAD <= event.getX() && event.getX() <= v.x + P_RAD
                                && v.y - P_RAD <= event.getY() && event.getY() <= v.y + P_RAD) {
                            selected = i;
                            break;
                        }
                    }
                }
                //System.out.println(selected);
            }
        });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY)
                    selected = -1;
            }
        });
    }

    public void draw() {

        GraphicsContext gc = this.getGraphicsContext2D();
        //gc.getTransform().setToIdentity();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        gc.beginPath();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        float startX = bezier.getStartX();
        gc.moveTo(startX * scale, 400 - bezier.getYfromX(startX) * scale);
        for(float x = (float) (startX + 1 / scale); x < bezier.getEndX(); x += 1 / scale) {
            gc.lineTo(x * scale, 400 - bezier.getYfromX(x) * scale);
        }
        //gc.closePath();
        gc.stroke();

        gc.setStroke(Color.STEELBLUE);

        for (int i = 0; i < bezier.getAnchorCount(); i ++) {
            double x1 =bezier.getAnchor(i).getControl1().x * scale;
            double y1 = 400 - bezier.getAnchor(i).getControl1().y * scale;
            double x2 =bezier.getAnchor(i).getControl2().x * scale;
            double y2 = 400 - bezier.getAnchor(i).getControl2().y * scale;
            gc.strokeLine(x1, y1, x2, y2);
        }

        gc.setFill(Color.BLUE);

        for (int i = 0; i < bezier.getAnchorCount(); i ++) {
            double x =bezier.getAnchor(i).getPoint().x * scale;
            double y = 400 - bezier.getAnchor(i).getPoint().y * scale;
            gc.fillOval(x - P_RAD, y - P_RAD, P_RAD * 2, P_RAD * 2);
        }

        gc.setFill(Color.INDIANRED);

        for (int i = 0; i < bezier.getAnchorCount(); i ++) {
            double x =bezier.getAnchor(i).getControl1().x * scale;
            double y = 400 - bezier.getAnchor(i).getControl1().y * scale;
            gc.fillOval(x - CP_RAD, y - CP_RAD, CP_RAD * 2, CP_RAD * 2);

            x = bezier.getAnchor(i).getControl2().x * scale;
            y = 400 - bezier.getAnchor(i).getControl2().y * scale;
            gc.fillOval(x - CP_RAD, y - CP_RAD, CP_RAD * 2, CP_RAD * 2);
        }
    }

    private double mapX(double x) {
        return x / scale;
    }

    private double mapY(double y) {
        return (400 - y) / scale;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
        this.draw();
    }
}
