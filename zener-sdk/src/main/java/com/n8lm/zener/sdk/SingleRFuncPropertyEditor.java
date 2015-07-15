package com.n8lm.zener.sdk;

import com.n8lm.zener.math.SingleRangeFunction;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;

/**
 * Created by Alchemist0823 on 7/11/2015.
 */
public class SingleRFuncPropertyEditor extends AbstractPropertyEditor<SingleRangeFunction, RangeFunctionEditor> {

    public SingleRFuncPropertyEditor(PropertySheet.Item property) {
        super(property, new RangeFunctionEditor());
    }

    @Override
    protected ObservableValue<SingleRangeFunction> getObservableValue() {
        return getEditor().rangeFunctionProperty();
    }

    @Override
    public void setValue(SingleRangeFunction singleRangeFunction) {
        getEditor().setRangeFunction(singleRangeFunction);
    }
}
