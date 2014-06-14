package com.n8lm.zener.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.graphics.ViewRenderSystem.RenderMode;
import com.n8lm.zener.math.Matrix4f;

public class GLRenderSystem extends EntitySystem {

	protected @Mapper ComponentMapper<ViewComponent> vm;
	protected @Mapper ComponentMapper<TransformComponent> tm;
	
	protected ViewRenderSystem srs;
	protected Matrix4f depthPVMat;
	protected Texture depthMap;
	//private Matrix4f depthP;
	//private Matrix4f depthV;
	
	public GLRenderSystem(World world) {
		super(Aspect.getAspectForAll(ViewComponent.class, TransformComponent.class));
		
		srs = new ViewRenderSystem(this);
		world.setSystem(srs, true);
	}
	
	class RenderEntry implements Comparable<RenderEntry> {
		ViewComponent vc;
		Entity entity;
		
		@Override
		public int compareTo(RenderEntry other) {
			return vc.getPriority() - other.vc.getPriority();
		}
		
	}

	@Override
	protected final void processEntities(ImmutableBag<Entity> entities) {
		
		List<RenderEntry> renderEntries = new ArrayList<RenderEntry>();
		
		for (int i = 0; i < entities.size(); i++) {
			RenderEntry re = new RenderEntry();
			re.vc = vm.get(entities.get(i));
			re.entity = entities.get(i); 
			renderEntries.add(re);
		}
		
		Collections.sort(renderEntries);
		
		for (int i = 0, s = renderEntries.size(); s > i; i++) {
			//System.out.print(renderEntries.get(i).vc.getPriority());
			process(renderEntries.get(i).entity);
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
	protected void process(Entity e) {
		
		if (vm.get(e).isActive()) {
			if (!vm.get(e).isRenderToScreen()) {

				//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
				//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
				
				//glTexParameteri(GL_TEXTURE_2D, GL_DEPTH_TEXTURE_MODE, GL_INTENSITY);
				//glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FAIL_VALUE_ARB, 0.5f);
				
				//glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
				//glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
				//glTexGeni(GL_R, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
				//glTexGeni(GL_Q, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
				//TODO
				
				FrameBuffer framebuffer = vm.get(e).getFramebuffer();
				glBindFramebuffer(GL_FRAMEBUFFER, framebuffer.getID());
				
				if (framebuffer.getColorTexture() == null) {

					srs.setRenderMode(RenderMode.DepthRender);
					glDrawBuffer(GL_NONE);
					glReadBuffer(GL_NONE);
					//glColorMask(false, false, false, false);
				} else {

					glDrawBuffer(GL_BACK);
					glReadBuffer(GL_BACK);
				}
				
				int status = glCheckFramebufferStatus(GL_FRAMEBUFFER) ;
				if (status != GL_FRAMEBUFFER_COMPLETE) {
					throw new RuntimeException("Framebuffer error: " + glGetError());
				}
				
			} else {
				srs.setRenderMode(RenderMode.NormalRender);
				glBindFramebuffer(GL_FRAMEBUFFER, 0);
				glDrawBuffer(GL_BACK);
				glReadBuffer(GL_BACK);
			}

			glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
			
			srs.setView(e);
			srs.process();

			glBindFramebuffer(GL_FRAMEBUFFER, 0);
			glDrawBuffer(GL_BACK);
			glReadBuffer(GL_BACK);
			

			if (srs.getRenderMode() == RenderMode.DepthRender) {
				depthPVMat = vm.get(e).getProjection().getProjectionMatrix(null);
				//depthP = vm.get(e).getProjection().getProjectionMatrix(null);
				//depthV = tm.get(e).getWorldTransform().getViewMatrix(null);
				depthPVMat.multLocal(tm.get(e).getWorldTransform().getViewMatrix(null));
				//System.out.println(depthPVMat);
				depthMap = vm.get(e).getFramebuffer().getDepthTexture();
			}
		}
	}
	
	@Override
	protected void initialize() {
		glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_CLAMP);
		glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);

		//glEnable(GL_MULTISAMPLE);
		//int buffer = glGetInteger(GL_SAMPLE_BUFFERS);
		//int samples = glGetInteger(GL_SAMPLES);

		//System.out.println(buffer);
		//System.out.println(samples);
	}

	public Matrix4f getDepthPV() {
		return depthPVMat;
	}

	public Texture getDepthTexture() {
		return depthMap;
	}

}
