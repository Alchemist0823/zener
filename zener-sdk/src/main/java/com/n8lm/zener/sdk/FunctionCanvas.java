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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Created on 2014/11/10.
 *
 * @author Alchemist
 */
public class FunctionCanvas extends Canvas {

    protected double scale;
    protected BezierFunction bezier;

    protected int selected = -1;

    private double mapX(double x) {
        return x / scale;
    }

    private double mapY(double y) {
        return (400 - y) / scale;
    }

    Vector2f v = new Vector2f();

    public FunctionCanvas(final BezierFunction bezier, final float scale) {
        super((bezier.getEndX() - bezier.getStartX()) * scale, 500);

        this.scale = scale;
        this.bezier = bezier;
        draw();
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selected != -1) {
                    Vector2f v = new Vector2f();
                    Vector2f t = new Vector2f();

                    v.set((float) mapX(event.getX()), (float) mapY(event.getY()));

                    v.subtract(bezier.getAnchor(selected).getPoint(), v);
                    bezier.getAnchor(selected).getPoint().addLocal(v);
                    bezier.getAnchor(selected).getControl1().addLocal(v);
                    bezier.getAnchor(selected).getControl2().addLocal(v);

                    bezier.verticalLineCheck();
                }
                draw();
            }
        });

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (int i = 0; i < bezier.getAnchorCount(); i ++) {
                    bezier.getAnchor(i).getPoint().mult(scale, v);
                    v.y = 400 - v.y;
                    if (v.x - 10 <= event.getX() && event.getX() <= v.x + 10
                            && v.y - 10 <= event.getY() && event.getY() <= v.y + 10) {
                        selected = i;
                        break;
                    }
                }
            }
        });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selected = -1;
            }
        });
    }

    public void draw() {

        GraphicsContext gc = this.getGraphicsContext2D();
        //gc.getTransform().setToIdentity();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        gc.setFill(Color.RED);

        for (int i = 0; i < bezier.getAnchorCount(); i ++) {
            double x =bezier.getAnchor(i).getPoint().x * scale;
            double y = 400 - bezier.getAnchor(i).getPoint().y * scale;
            gc.fillOval(x - 10, y - 10, 20, 20);
        }

        gc.setFill(Color.YELLOW);

        for (int i = 0; i < bezier.getAnchorCount(); i ++) {
            double x =bezier.getAnchor(i).getControl1().x * scale;
            double y = 400 - bezier.getAnchor(i).getControl1().y * scale;
            gc.fillOval(x - 10, y - 10, 20, 20);

            x =bezier.getAnchor(i).getControl2().x * scale;
            y = 400 - bezier.getAnchor(i).getControl2().y * scale;
            gc.fillOval(x - 10, y - 10, 20, 20);
        }

        gc.beginPath();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);

        float startX = bezier.getStartX();
        gc.moveTo(startX * scale, 400 - bezier.getYfromX(startX) * scale);
        for(float x = (float) (startX + 1 / scale); x < bezier.getEndX(); x += 1 / scale) {
            gc.lineTo(x * scale, 400 - bezier.getYfromX(x) * scale);
        }
        //gc.closePath();
        gc.stroke();

    }
}
