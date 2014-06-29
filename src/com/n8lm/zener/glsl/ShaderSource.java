package com.n8lm.zener.glsl;

/**
 * Created by Alchemist on 2014/6/18.
 */
public class ShaderSource {
    private String name;
    private String source;
    private String[] independence;

    public ShaderSource(String name, String source, String[] independence) {
        this.name = name;
        this.source = source;
        this.independence = independence;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String[] getIndependence() {
        return independence;
    }
}
