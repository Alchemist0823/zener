package com.n8lm.zener.sdk;

import com.n8lm.zener.math.ColorGradient;
import com.n8lm.zener.math.ColorRGBA;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

/**
 * Created by Alchemist0823 on 6/6/2015.
 */
public class GradientEditor extends VBox {
    /**
     * The list of control points
     */
    private ColorGradient gradient = new ColorGradient();
    /**
     * The current selcted control point
     */
    private int selected = -1;
    ///** The polygon used for the markers */
    private final double[] polyY;
    private final double[] polyX;
    //private Polygon poly;

    private HBox hBox = new HBox();
    private Canvas canvas;
    /**
     * A button to add a control point
     */
    private Button add = new Button("Add");
    /**
     * A button to edit a control point
     */
    private Button edit = new Button("Edit");
    /**
     * A button to delete a control point
     */
    private Button del = new Button("Del");

    /**
     * The x position of the gradient bar
     */
    private int x = 10;
    /**
     * The y position of the gradient bar
     */
    private int y = 10;
    /**
     * The width of the gradient bar
     */
    private double width = 200;
    /**
     * The height of the gradient bar
     */
    private double barHeight = 25;

    /**
     * Create a new editor for gradients
     */
    public GradientEditor() {
        hBox.getChildren().add(add);
        hBox.getChildren().add(edit);
        hBox.getChildren().add(del);

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addPoint();
            }
        });
        del.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                delPoint();
            }
        });
        polyX = new double[]{
                0,
                5,
                -5
        };

        polyY = new double[]{
                0,
                10,
                10
        };

        canvas = new Canvas(x + width + x, y + barHeight + y);
        getChildren().addAll(canvas, hBox);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    editPoint(event.getScreenX(), event.getScreenY());
                } else {
                    selectPoint(event.getX(), event.getY());
                }
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                movePoint(event.getX(), event.getY());
            }
        });
        fireUpdate();
    }

    /**
     * Fire an update to all listeners
     */
    private void fireUpdate() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        paintComponent(canvas.getGraphicsContext2D());
    }

    /**
     * Check if there is a control point at the specified mouse location
     *
     * @param mx  The mouse x coordinate
     * @param my  The mouse y coordinate
     * @param pos The point to check agianst
     * @return True if the mouse point conincides with the control point
     */
    private boolean checkPoint(double mx, double my, double pos) {
        int dx = (int) Math.abs((x + (width * pos)) - mx);
        int dy = (int) Math.abs((y + barHeight + 7) - my);

        if ((dx < 5) && (dy < 7)) {
            return true;
        }

        return false;
    }

    /**
     * Add a new control point
     */
    private void addPoint() {
        gradient.addPoint(0.5f, ColorRGBA.White);

        //selected = point;
        //gradient.sortPoints();

        fireUpdate();
    }

    /**
     * Edit the currently selected control point
     */
    private void editPoint(double mx, double my) {
        if (selected == -1) {
            return;
        }

        final ColorRGBA color = gradient.getColor(selected);
        final ColorPicker colorPicker = new ColorPicker(new Color(color.r, color.g, color.b, color.a));

        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                Color newColor = colorPicker.getValue();
                color.set((float) newColor.getRed(), (float) newColor.getGreen(), (float) newColor.getBlue(), (float) newColor.getOpacity());
                fireUpdate();
            }
        });

        final ContextMenu contextMenu = new ContextMenu();
        MenuItem editLabel = new MenuItem();
        contextMenu.getItems().add(editLabel);
        editLabel.setGraphic(colorPicker);
        contextMenu.show(this, mx, my);
    }

    /**
     * Select the control point at the specified mouse coordinate
     *
     * @param mx The mouse x coordinate
     * @param my The mouse y coordinate
     */
    private void selectPoint(double mx, double my) {
        if (isDisabled()) {
            return;
        }

        for (int i = 1; i < gradient.getControlPointCount() - 1; i++) {
            if (checkPoint(mx, my, gradient.getPointPos(i))) {
                selected = i;
                return;
            }
        }
        if (checkPoint(mx, my, gradient.getPointPos(0))) {
            selected = 0;
            return;
        }

        if (checkPoint(mx, my, gradient.getPointPos(gradient.getControlPointCount() - 1))) {
            selected = gradient.getControlPointCount() - 1;
            return;
        }

        selected = -1;
    }

    /**
     * Delete the currently selected point
     */
    private void delPoint() {
        if (isDisabled()) {
            return;
        }

        if (selected == -1) {
            return;
        }
        if (selected == 0) {
            return;
        }
        if (selected == gradient.getControlPointCount() - 1) {
            return;
        }

        gradient.remove(selected);
        //sortPoints();
        //repaint(0);
        fireUpdate();
    }

    /**
     * Move the current point to the specified mouse location
     *
     * @param mx The x coordinate of the mouse
     * @param my The y coordinate of teh mouse
     */
    private void movePoint(double mx, double my) {
        if (isDisabled()) {
            return;
        }

        if (selected == -1) {
            return;
        }
        if (selected == 0) {
            return;
        }
        if (selected == gradient.getControlPointCount() - 1) {
            return;
        }

        double newPos = (mx - 10) / (double) width;
        newPos = Math.min(1, newPos);
        newPos = Math.max(0, newPos);

        gradient.setPointPos(selected, (double) newPos);
        fireUpdate();
        //sortPoints();
        //fireUpdate();
    }

    public void paintComponent(GraphicsContext gc) {

        Stop[] stops = new Stop[gradient.getControlPointCount()];

        for (int i = 0; i < gradient.getControlPointCount(); i++) {
            ColorRGBA colorRGBA = gradient.getColor(i);
            Color color = new Color(colorRGBA.r, colorRGBA.g, colorRGBA.b, colorRGBA.a);
            stops[i] = new Stop(gradient.getPointPos(i), color);
        }

        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, stops);
        gc.setFill(linearGradient);
        gc.fillRect(x, y, width, barHeight);

        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, width, barHeight - 1);

        for (int i = 0; i < gradient.getControlPointCount(); i++) {
            gc.translate(10 + (width * gradient.getPointPos(i)), y + barHeight);
            ColorRGBA colorRGBA = gradient.getColor(i);
            Color color = new Color(colorRGBA.r, colorRGBA.g, colorRGBA.b, colorRGBA.a);
            gc.setFill(color);
            gc.fillPolygon(polyX, polyY, 3);
            gc.setStroke(Color.BLACK);
            gc.strokePolygon(polyX, polyY, 3);

            if (i == selected) {
                gc.strokeLine(-5, 12, 5, 12);
            }
            gc.translate(-10 - (width * gradient.getPointPos(i)), -y - barHeight);
        }
    }
}
