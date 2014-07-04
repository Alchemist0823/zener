/*
 * This file is part of Zener.
 *
 * Zener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * Zener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Zener.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.n8lm.zener.graphics;

import com.artemis.Component;
import com.n8lm.zener.math.Rectangle2D;

public class ViewComponent extends Component {

	private Rectangle2D viewport;
	private FrameBuffer framebuffer;
	
	private int priority;
	private boolean active;
	private Projection projection;
	
	/*
	public ViewComponent() {
		this(new PerspectiveProjection());
	}*/

	public ViewComponent(Projection projection) {
		this(projection, 0);
	}
	
	public ViewComponent(Projection projection, int priority) {
		this(projection, priority, new Rectangle2D(0, 0, 0, 0), null, true);
	}
	
	public ViewComponent(Projection projection, int priority, Rectangle2D viewport, FrameBuffer framebuffer, boolean active) {
		super();
		
		this.setProjection(projection);
		this.viewport = viewport;
		this.priority = priority;
		this.framebuffer = framebuffer;
		this.active = active;
	}
	

	public Rectangle2D getViewport() {
		return viewport;
	}

	public void setViewport(Rectangle2D viewport) {
		this.viewport = viewport;
	}

	public FrameBuffer getFramebuffer() {
		return framebuffer;
	}

	public void setFramebuffer(FrameBuffer framebuffer) {
		this.framebuffer = framebuffer;
	}
	
	public void setRenderToScreen(){
		this.framebuffer = null;
	}

	public boolean isRenderToScreen() {
		if (framebuffer == null)
			return true;
		else
			return false;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

}
