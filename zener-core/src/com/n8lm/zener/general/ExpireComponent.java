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

package com.n8lm.zener.general;

import com.artemis.Component;
import com.n8lm.zener.utils.EndEventListener;

public class ExpireComponent extends Component {
	private float remain;
	private EndEventListener expireListener;
	
	public ExpireComponent(float time) {
		this(time, null);
	}
	
	public ExpireComponent(float time, EndEventListener expireListener) {
		this.remain = time;
		this.expireListener = expireListener;
	}
	
	public float getRemain() {
		return remain;
	}
	
	public void substract(float time) {
		this.remain -= time;
	}

	public EndEventListener getExpireListener() {
		return expireListener;
	}
}
