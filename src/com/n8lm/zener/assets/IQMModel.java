package com.n8lm.zener.assets;

import java.io.IOException;
import java.io.InputStream;

import com.n8lm.zener.math.Quaternion;
import com.n8lm.zener.math.Vector3f;


public class IQMModel {

	public IQMModel() {
		// TODO Auto-generated constructor stub
	}
	
	
	class Header {
	    static final int size = 16 + 27 * 4;

	    char magic[];//16
	    int version;
	    int filesize;
	    int flags;
	    int num_text, ofs_text;
	    int num_meshes, ofs_meshes;
	    int num_vertexarrays, num_vertexes, ofs_vertexarrays;
	    int num_triangles, ofs_triangles, ofs_adjacency;
	    int num_joints, ofs_joints;
	    int num_poses, ofs_poses;
	    int num_anims, ofs_anims;
	    int num_frames, num_framechannels, ofs_frames, ofs_bounds;
	    int num_comment, ofs_comment;
	    int num_extensions, ofs_extensions;
	    
	}
	
	class Mesh
	{
	    int name;
	    int material;
	    int first_vertex, num_vertexes;
	    int first_triangle, num_triangles;
	}
	

	class Triangle
	{
	    int vertex1;
	    int vertex2;
	    int vertex3;
	}
	
	class Adjacency
	{
	    int triangle1;
	    int triangle2;
	    int triangle3;
	}
	
	class Joint
	{
	    int name;
	    int parent;
	    Vector3f translate;
	    Quaternion rotate;
	    Vector3f scale;
	}
	
	class Pose
	{
	    int parent;
	    int mask;
	    float channeloffset[]; // 10
	    float channelscale[];
	}
	
	class Anim
	{
	    int name;
	    int first_frame, num_frames;
	    float framerate;
	    int flags;
	}
	
	class VertexArray
	{
	    int type;
	    int flags;
	    int format;
	    int size;
	    int offset;
	}
	
	class Bounds
	{
	    Vector3f bbmin;
	    Vector3f bbmax;
	    float xyradius, radius;
	}
}
