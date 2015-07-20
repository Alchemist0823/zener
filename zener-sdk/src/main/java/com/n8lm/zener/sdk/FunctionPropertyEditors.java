package com.n8lm.zener.sdk;

import com.n8lm.zener.math.SingleRangeFunction;
import com.n8lm.zener.math.Vector3fFunction;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.PropertyEditor;

/**
 * Created by Alchemist0823 on 7/19/2015.
 */
public class FunctionPropertyEditors {

    static PropertyEditor<SingleRangeFunction> createSingleRangeFunctionEditor(PropertySheet.Item property) {
        return new AbstractPropertyEditor<SingleRangeFunction, RangeFunctionEditor>(property, new RangeFunctionEditor()) {

            @Override
            protected ObservableValue<SingleRangeFunction> getObservableValue() {
                return getEditor().rangeFunctionProperty();
            }

            @Override
            public void setValue(SingleRangeFunction singleRangeFunction) {
                getEditor().setRangeFunction(singleRangeFunction);
            }
        };
    }

    public static PropertyEditor<Vector3fFunction> createVector3fFunctionEditor(PropertySheet.Item property) {
        return new AbstractPropertyEditor<Vector3fFunction, Vector3fFunctionEditor>(property, new Vector3fFunctionEditor()) {

            @Override
            protected ObservableValue<Vector3fFunction> getObservableValue() {
                return getEditor().vector3fFunctionProperty();
            }

            @Override
            public void setValue(Vector3fFunction vector3fFunction) {
                getEditor().setVector3fFunction(vector3fFunction);
            }
        };
    }
}
