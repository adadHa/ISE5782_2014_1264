package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Constractor for Ray Tracer
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * This method traces a ray and returns the color of its first intersection point.
     * @param ray
     * @return
     */
    public abstract Color traceRay(Ray ray);
}

