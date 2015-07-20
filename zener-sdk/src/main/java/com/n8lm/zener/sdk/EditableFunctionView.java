package com.n8lm.zener.sdk;

import com.n8lm.zener.math.EditableCurveFunction;
import com.n8lm.zener.math.Vector2f;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Alchemist0823 on 7/16/2015.
 */
public class EditableFunctionView extends FunctionView {

    public EditableFunctionView() {

        this.setMinHeight(200);
        this.setMinWidth(400);

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
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
                    updateScale();
                    draw();
                }
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    for (int k = 0; k < beziers.size(); k++) {
                        EditableCurveFunction bezier = beziers.get(k);
                        for (int i = 0; i < bezier.getAnchorCount(); i++) {
                            mapToScreen(bezier.getAnchor(i).getControl1(), v);
                            if (v.x - CP_RAD <= event.getX() && event.getX() <= v.x + CP_RAD
                                    && v.y - CP_RAD <= event.getY() && event.getY() <= v.y + CP_RAD) {
                                selectedPoint = i + bezier.getAnchorCount();
                                selectedIndex = k;
                                bezier.getEditData(i).setSelected1(true);
                                return;
                            }

                            mapToScreen(bezier.getAnchor(i).getControl2(), v);
                            if (v.x - CP_RAD <= event.getX() && event.getX() <= v.x + CP_RAD
                                    && v.y - CP_RAD <= event.getY() && event.getY() <= v.y + CP_RAD) {
                                selectedPoint = i + bezier.getAnchorCount() * 2;
                                selectedIndex = k;
                                bezier.getEditData(i).setSelected2(true);
                                return;
                            }

                            mapToScreen(bezier.getAnchor(i).getPoint(), v);
                            if (v.x - P_RAD <= event.getX() && event.getX() <= v.x + P_RAD
                                    && v.y - P_RAD <= event.getY() && event.getY() <= v.y + P_RAD) {
                                selectedPoint = i;
                                selectedIndex = k;
                                bezier.getEditData(i).setSelectedP(true);
                                return;
                            }
                        }
                    }
                }
                //System.out.println(selected);
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (selectedIndex >= 0) {
                        int selectp = selectedPoint % beziers.get(selectedIndex).getAnchorCount();
                        int selectObj = selectedPoint / beziers.get(selectedIndex).getAnchorCount();
                        if (selectObj == 0) {
                            beziers.get(selectedIndex).getEditData(selectp).setSelectedP(false);
                        } else if (selectObj == 1) {
                            beziers.get(selectedIndex).getEditData(selectp).setSelected1(false);
                        } else if (selectObj == 2) {
                            beziers.get(selectedIndex).getEditData(selectp).setSelected2(false);
                        }
                    }
                    selectedPoint = -1;
                    selectedIndex = -1;
                    draw();
                }
            }
        });
    }
}
