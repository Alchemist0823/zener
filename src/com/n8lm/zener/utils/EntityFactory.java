package com.n8lm.zener.utils;

import java.util.logging.Logger;







import com.artemis.Entity;
import com.artemis.World;
import com.n8lm.zener.animation.SkeletonComponent;
import com.n8lm.zener.assets.Model;
import com.n8lm.zener.components.TransformComponent;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.graphics.GeometryComponent;
import com.n8lm.zener.graphics.MaterialComponent;
import com.n8lm.zener.graphics.NormalMaterialUniforms;
import com.n8lm.zener.graphics.UniformGroup;
import com.n8lm.zener.graphics.geom.Geometry;
import com.n8lm.zener.graphics.geom.ModelGeometry;
import com.n8lm.zener.math.Quaternion;
import com.n8lm.zener.math.Vector3f;

public class EntityFactory {

	  private final static Logger LOGGER = Logger.getLogger(EntityFactory.class
	      .getName());

	protected static World world;
	
	public static void setWorld(World wor) {
		world = wor;
	}
	
	public static Entity createAttachableObject(Vector3f pos, Quaternion rot, Entity attached, String bone) {
		Entity e = world.createEntity();
		e.addComponent(new TransformComponent(attached, bone, pos, rot, Vector3f.UNIT_XYZ));
		//e.addComponent(new VelocityComponent());
		LOGGER.info("ID: " + e.getId());
		LOGGER.info("Position: " + pos.toString());
		return e;
	}
	
	public static Entity createModelObject(Vector3f pos, Quaternion rot, Entity attached, String bone, String modelName, boolean shadowCaster) {
		Entity e = createAttachableObject(pos, rot, attached, bone);
		return addDisplayObjectComponents(e, modelName, shadowCaster);
	}

	public static Entity createDisplayObject(Vector3f pos, Quaternion rot, String geometryName, Geometry geometry, UniformGroup material, boolean shadowCaster, Entity attached, String bone) {
		Entity e = createAttachableObject(pos, rot, attached, bone);
		return addDisplayObjectComponents(e, geometryName, geometry, material, shadowCaster);
	}
	
	public static void changeEntityModel(Entity e, String modelName) {

		ResourceManager rm = ResourceManager.getInstance();
		Model model = rm.getModel(modelName);
		Geometry geometry = null;
		if (rm.getGeometryManager().hasGeometry(modelName))
			geometry = rm.getGeometryManager().getGeometry(modelName);
		else {
			geometry = new ModelGeometry(model.getMesh());
			rm.getGeometryManager().registerGeometry(modelName, geometry);
		}
		e.getComponent(GeometryComponent.class).setGeometry(geometry);
		e.getComponent(MaterialComponent.class).setMaterial(new NormalMaterialUniforms(model.getMaterial()));
		e.removeComponent(SkeletonComponent.class);
		if (model.getSkeleton().getJoints().size() > 0)
			e.addComponent(new SkeletonComponent(model.getSkeleton()));
		world.changedEntity(e);
	}

	public static void deleteEntityDisplay(Entity e) {
		e.removeComponent(GeometryComponent.class);
		e.removeComponent(MaterialComponent.class);
		e.removeComponent(SkeletonComponent.class);
	}

	public static Entity addDisplayObjectComponents(Entity e, String geometryName, Geometry geometry, UniformGroup material, boolean shadowCaster) {
		
		ResourceManager rm = ResourceManager.getInstance();
		
		if (rm.getGeometryManager().hasGeometry(geometryName))
			geometry = rm.getGeometryManager().getGeometry(geometryName);
		else
			rm.getGeometryManager().registerGeometry(geometryName, geometry);
		
		e.addComponent(new GeometryComponent(geometry, shadowCaster));
		e.addComponent(new MaterialComponent(material));
		//e.addComponent(new VelocityComponent());
		LOGGER.info("Geometry: " + geometry.toString());
		return e;
	}

	
	public static Entity addDisplayObjectComponents(Entity e, String modelName, boolean shadowCaster) {
		ResourceManager rm = ResourceManager.getInstance();
		Model model = rm.getModel(modelName);
		Geometry geometry = null;
		if (rm.getGeometryManager().hasGeometry(modelName))
			geometry = rm.getGeometryManager().getGeometry(modelName);
		else {
			geometry = new ModelGeometry(model.getMesh());
			rm.getGeometryManager().registerGeometry(modelName, geometry);
		}
		//TODO: Geometry memory
		e.addComponent(new GeometryComponent(geometry, shadowCaster));
		e.addComponent(new MaterialComponent(new NormalMaterialUniforms(model.getMaterial())));
		if (model.getSkeleton().getJoints().size() > 0)
			e.addComponent(new SkeletonComponent(model.getSkeleton()));
		LOGGER.info("Model: " + model.toString());
		return e;
	}
	
}
