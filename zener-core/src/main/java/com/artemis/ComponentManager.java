package com.artemis;

import java.util.BitSet;

import com.artemis.utils.ArrayBag;
import com.artemis.utils.Bag;

public class ComponentManager extends Manager {
	private ArrayBag<ArrayBag<Component>> componentsByType;
	private Bag<Entity> deleted;

	public ComponentManager() {
		componentsByType = new ArrayBag<ArrayBag<Component>>();
		deleted = new ArrayBag<Entity>();
	}
	
	@Override
	protected void initialize() {
	}

	private void removeComponentsOfEntity(Entity e) {
		BitSet componentBits = e.getComponentBits();
		for (int i = componentBits.nextSetBit(0); i >= 0; i = componentBits.nextSetBit(i+1)) {
			componentsByType.get(i).set(e.getId(), null);
		}
		componentBits.clear();
	}
	
	protected void addComponent(Entity e, ComponentType type, Component component) {
		componentsByType.ensureCapacity(type.getIndex());

        ArrayBag<Component> components = componentsByType.get(type.getIndex());
		if(components == null) {
			components = new ArrayBag<Component>();
			componentsByType.set(type.getIndex(), components);
		}
		
		components.set(e.getId(), component);

		e.getComponentBits().set(type.getIndex());
	}

	protected void removeComponent(Entity e, ComponentType type) {
		if(e.getComponentBits().get(type.getIndex())) {
			componentsByType.get(type.getIndex()).set(e.getId(), null);
			e.getComponentBits().clear(type.getIndex());
		}
	}
	
	protected ArrayBag<Component> getComponentsByType(ComponentType type) {
        ArrayBag<Component> components = componentsByType.get(type.getIndex());
		if(components == null) {
			components = new ArrayBag<Component>();
			componentsByType.set(type.getIndex(), components);
		}
		return components;
	}
	
	protected Component getComponent(Entity e, ComponentType type) {
        ArrayBag<Component> components = componentsByType.get(type.getIndex());
		if(components != null && components.isIndexWithinBounds(e.getId())) {
			return components.get(e.getId());
		}
		return null;
	}
	
	public Bag<Component> getComponentsFor(Entity e, Bag<Component> fillBag) {
		BitSet componentBits = e.getComponentBits();

		for (int i = componentBits.nextSetBit(0); i >= 0; i = componentBits.nextSetBit(i+1)) {
			fillBag.add(componentsByType.get(i).get(e.getId()));
		}
		
		return fillBag;
	}

	
	@Override
	public void deleted(Entity e) {
		deleted.add(e);
	}
	
	protected void clean() {
		if(deleted.size() > 0) {
			for(int i = 0; deleted.size() > i; i++) {
				removeComponentsOfEntity(deleted.get(i));
			}
			deleted.clear();
		}
	}

}
