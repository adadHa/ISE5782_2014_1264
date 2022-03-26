package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class RayTracerBasic extends RayTracerBase{

    /**
     * Constractor for Ray Tracer
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     *
     * @param ray
     * @return
     */
    @Override
    public Color traceRay(Ray ray) {
        Color pixelColor;
        Point closestPoint;
        ArrayList<Point> intersections = (ArrayList<Point>) scene.geometries.findIntersections(ray);
        if (intersections != null){
            closestPoint = ray.findClosestPoint(intersections);
            pixelColor = calcColor(closestPoint);
        }
        else
            pixelColor = scene.background;
        return pixelColor;
    }

    private Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }
}
