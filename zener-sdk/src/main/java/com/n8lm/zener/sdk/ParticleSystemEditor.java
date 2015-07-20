package com.n8lm.zener.sdk;

import com.n8lm.zener.math.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.DefaultPropertyEditorFactory;
import org.controlsfx.property.editor.PropertyEditor;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Alchemist0823 on 6/7/2015.
 */
public class ParticleSystemEditor extends VBox {

    private static Map<String, Object> customDataMap = new LinkedHashMap<>();

    static {


        EditableCurveFunction bezier1 = new EditableCurveFunction();
        bezier1.addAnchor(new CurveAnchor2f(new Vector2f(0.0f, 0.0f), new Vector2f(-1.0f, 0.0f)));
        bezier1.addAnchor(new CurveAnchor2f(new Vector2f(2.0f, 2.0f), new Vector2f(1.5f, 2.0f)));
        bezier1.addAnchor(new CurveAnchor2f(new Vector2f(3.0f, 0.0f), new Vector2f(2.0f, 0.0f)));
        bezier1.addAnchor(new CurveAnchor2f(new Vector2f(5.0f, 1.0f), new Vector2f(4.0f, 2.0f)));

        EditableCurveFunction bezier2 = new EditableCurveFunction();
        bezier2.addAnchor(new CurveAnchor2f(new Vector2f(0.0f, 1.0f)), new EditableAnchorData(EditableAnchorData.ControlType.AUTO_ANIM, EditableAnchorData.ControlType.AUTO_ANIM));
        bezier2.addAnchor(new CurveAnchor2f(new Vector2f(2.0f, 3.0f)), new EditableAnchorData(EditableAnchorData.ControlType.AUTO_ANIM, EditableAnchorData.ControlType.AUTO_ANIM));
        bezier2.addAnchor(new CurveAnchor2f(new Vector2f(3.0f, 1.0f)), new EditableAnchorData(EditableAnchorData.ControlType.AUTO_ANIM, EditableAnchorData.ControlType.AUTO_ANIM));
        bezier2.addAnchor(new CurveAnchor2f(new Vector2f(5.0f, 2.0f)), new EditableAnchorData(EditableAnchorData.ControlType.AUTO_ANIM, EditableAnchorData.ControlType.AUTO_ANIM));

        customDataMap.put("Emission#initialPositionOverTime", new SingleRangeFunction(new EditableCurveFunction(), new EditableCurveFunction()));
        customDataMap.put("Emission#initialRotationOverTime", new SingleRangeFunction(new EditableCurveFunction(), new EditableCurveFunction()));
        customDataMap.put("Emission#initialSizeOverTime", new SingleRangeFunction(bezier1, bezier2));
        customDataMap.put("Emission#duration", 5f);
        customDataMap.put("Emission#EmitSpeedOverTime", new EditableCurveFunction());
        customDataMap.put("Emission#EmitLifeOverTme", new SingleRangeFunction(new EditableCurveFunction(), new EditableCurveFunction()));

        customDataMap.put("Field#ColorOverLife", new ColorGradient()); // Creates a TextField in property sheet
        customDataMap.put("Field#VelocityOverLife", LocalDate.of(2000, Month.JANUARY, 1)); // Creates a DatePicker
        customDataMap.put("Field#Force", new Vector3f()); // Creates a ChoiceBox
        customDataMap.put("Field#My Boolean", false); // Creates , a CheckBox
        customDataMap.put("Field#My Number", 500); // Creates a NumericField
        customDataMap.put("Field#My Font", new Font(20f)); // Creates a NumericField
    }

    class CustomPropertyItem implements PropertySheet.Item {
        private String key;
        private String category, name;

        public CustomPropertyItem(String key) {
            this.key = key;
            String[] skey = key.split("#");
            category = skey[0];
            name = skey[1];
        }

        @Override
        public Class<?> getType() {
            return customDataMap.get(key).getClass();
        }

        @Override
        public String getCategory() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public Object getValue() {
            return customDataMap.get(key);
        }

        @Override
        public void setValue(Object value) {
            customDataMap.put(key, value);
        }
    }

    public ParticleSystemEditor() {
        ObservableList<PropertySheet.Item> list = FXCollections.observableArrayList();
        for (String key : customDataMap.keySet())
            list.add(new CustomPropertyItem(key));

        final PropertySheet propertySheet = new PropertySheet(list);
        VBox.setVgrow(propertySheet, Priority.ALWAYS);
        getChildren().add(propertySheet);

        final SimpleObjectProperty<Callback<PropertySheet.Item, PropertyEditor<?>>> propertyEditorFactory = new SimpleObjectProperty<Callback<PropertySheet.Item, PropertyEditor<?>>>(this, "propertyEditor", new DefaultPropertyEditorFactory());

        propertySheet.setPropertyEditorFactory(new Callback<PropertySheet.Item, PropertyEditor<?>>() {
            @Override
            public PropertyEditor<?> call(PropertySheet.Item param) {
                    /*if (param.getValue() instanceof List) {
                        return Editors.createChoiceEditor(param, (List) param.getValue());
                    }*/
                if (param.getValue() instanceof SingleRangeFunction) {
                    return new SingleRFuncPropertyEditor(param);
                }

                return propertyEditorFactory.get().call(param);
            }
        });
    }
}
