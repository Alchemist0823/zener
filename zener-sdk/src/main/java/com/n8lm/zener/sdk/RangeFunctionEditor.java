package com.n8lm.zener.sdk;

import com.n8lm.zener.math.EditableCurveFunction;
import com.n8lm.zener.math.SingleRangeFunction;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by Alchemist0823 on 7/12/2015.
 */
public class RangeFunctionEditor extends FunctionView {

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
        this.rangeFunction = new SimpleObjectProperty<>(this, "rangeFunction", new SingleRangeFunction(new EditableCurveFunction(), new EditableCurveFunction()));
        rangeFunctionProperty().addListener((observable, oldValue, newValue) -> {
            replace(oldValue.getLower(), newValue.getLower());
            replace(oldValue.getUpper(), newValue.getUpper());
        });
        addFunction(getRangeFunction().getLower());
        addFunction(getRangeFunction().getUpper());

    }
}
