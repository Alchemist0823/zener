package com.n8lm.zener.sdk;

import com.n8lm.zener.math.EditableCurveFunction;
import com.n8lm.zener.math.Vector3fFunction;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Created by Alchemist0823 on 7/19/2015.
 */
public class Vector3fFunctionEditor extends HBox {

    private FunctionView functionViewX;
    private FunctionView functionViewY;
    private FunctionView functionViewZ;

    protected ObjectProperty<Vector3fFunction> vector3fFunction;

    public ObjectProperty<Vector3fFunction> vector3fFunctionProperty() {
        return vector3fFunction;
    }

    public final void setVector3fFunction(Vector3fFunction value) {
        vector3fFunctionProperty().set(value);
    }

    public final Vector3fFunction getVector3fFunction() {
        return vector3fFunctionProperty().get();
    }

    public Vector3fFunctionEditor() {
        super();
        this.vector3fFunction = new SimpleObjectProperty<>(this, "vector3fFunction", new Vector3fFunction(new EditableCurveFunction(), new EditableCurveFunction(), new EditableCurveFunction()));

        functionViewX = new FunctionView();
        functionViewY = new FunctionView();
        functionViewZ = new FunctionView();
        functionViewX.addFunction(getVector3fFunction().getxFunction());
        functionViewY.addFunction(getVector3fFunction().getyFunction());
        functionViewZ.addFunction(getVector3fFunction().getzFunction());

        vector3fFunctionProperty().addListener((observable, oldValue, newValue) -> {
            functionViewX.replace(oldValue.getxFunction(), newValue.getxFunction());
            functionViewY.replace(oldValue.getyFunction(), newValue.getyFunction());
            functionViewZ.replace(oldValue.getzFunction(), newValue.getzFunction());
        });

        this.getChildren().addAll(functionViewX, functionViewY, functionViewZ);

        this.setSpacing(8.0);
        HBox.setHgrow(functionViewX, Priority.ALWAYS);
        HBox.setHgrow(functionViewY, Priority.ALWAYS);
        HBox.setHgrow(functionViewZ, Priority.ALWAYS);
    }
}
