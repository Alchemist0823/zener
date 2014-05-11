package com.n8lm.zener.components;

import com.artemis.Component;
import com.n8lm.zener.graphics.geom.Geometry;

public class GeometryComponent extends Component{
	
	private boolean visible;
	private boolean shadowCaster;
	private Geometry ds;
	
	public GeometryComponent(Geometry ds) {
		this(ds, true);
	}

	public GeometryComponent(Geometry ds, boolean shadowCaster) {
		this.ds = ds;
		this.shadowCaster = shadowCaster;
		visible = true;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Geometry getGeometry() {
		return ds;
	}

	public void setGeometry(Geometry ds) {
		this.ds = ds;
	}

	public boolean isShadowCaster() {
		return shadowCaster;
	}

	public void setShadowCaster(boolean shadowCaster) {
		this.shadowCaster = shadowCaster;
	}
}
