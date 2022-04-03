package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

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
        GeoPoint closestPoint;
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections != null){
            closestPoint = ray.findClosestGeoPoint(intersections);
            pixelColor = calcColor(closestPoint);
        }
        else
            pixelColor = scene.background;
        return pixelColor;
    }

    private Color calcColor(GeoPoint geoPoint){
        return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission());
    }
}
