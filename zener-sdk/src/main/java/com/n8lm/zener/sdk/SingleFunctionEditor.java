package com.n8lm.zener.sdk;

import com.n8lm.zener.math.CurveFunction;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Alchemist0823 on 7/12/2015.
 */
public class SingleFunctionEditor extends FunctionEditorBase {

    protected ObjectProperty<CurveFunction> value;

    public ObjectProperty<CurveFunction> functionValueProperty() {
        return value;
    }

    public final void setFunctionValue(CurveFunction value) {
        functionValueProperty().set(value);
    }

    public final CurveFunction getFunctionValue() {
        return functionValueProperty().get();
    }

    public SingleFunctionEditor() {
        super();

        this.value = new SimpleObjectProperty<>(this, "value", new CurveFunction());
        functionValueProperty().addListener(new ChangeListener<CurveFunction>() {
            @Override
            public void changed(ObservableValue<? extends CurveFunction> observable, CurveFunction oldValue, CurveFunction newValue) {
                functionView.replace(oldValue, newValue);
            }
        });
        functionView.addFunction(getFunctionValue());
    }
}
