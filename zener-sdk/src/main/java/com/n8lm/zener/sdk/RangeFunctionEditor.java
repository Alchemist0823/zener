package com.n8lm.zener.sdk;

import com.n8lm.zener.math.CurveFunction;
import com.n8lm.zener.math.SingleRangeFunction;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Alchemist0823 on 7/12/2015.
 */
public class RangeFunctionEditor extends FunctionEditorBase {

    protected ObjectProperty<SingleRangeFunction> rangeFunction;

    public ObjectProperty<SingleRangeFunction> rangeFunctionProperty() {
        return rangeFunction;
    }

    public final void setRangeFunction(SingleRangeFunction value) {
        rangeFunctionProperty().set(value);
    }

    public final SingleRangeFunction getRangeFunction() {
        return rangeFunctionProperty().get();
    }

    public RangeFunctionEditor() {
        super();
        this.rangeFunction = new SimpleObjectProperty<>(this, "rangeFunction", new SingleRangeFunction(new CurveFunction(), new CurveFunction()));
        rangeFunctionProperty().addListener(new ChangeListener<SingleRangeFunction>() {
            @Override
            public void changed(ObservableValue<? extends SingleRangeFunction> observable, SingleRangeFunction oldValue, SingleRangeFunction newValue) {
                canvas.replace(oldValue.getLower(), newValue.getLower());
                canvas.replace(oldValue.getUpper(), newValue.getUpper());
            }
        });
        canvas.addFunction(getRangeFunction().getLower());
        canvas.addFunction(getRangeFunction().getUpper());

    }
}
