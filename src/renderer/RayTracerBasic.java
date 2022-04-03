package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
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
            pixelColor = calcColor(closestPoint, ray);
        }
        else
            pixelColor = scene.background;
        return pixelColor;
    }

    private Color calcColor(GeoPoint intersection, Ray ray){
        return scene.ambientLight.getIntensity()
                .add(intersection.geometry.getEmission(),
                        calcColorLocalEffects(intersection, ray));
    }

    private Color calcColorLocalEffects(GeoPoint geoPoint, Ray ray) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;
        Material material = geoPoint.geometry.getMaterial();

        for (LightSource lightSource :
                scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl*nv>0){ //check whether nl and nv have the same sign
                Color iL = lightSource.getIntensity(geoPoint.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    /**
     * This method calc the specular effect of the light source
     * @param material the material of the geometry
     * @param n dot product of the vectors n,l
     * @param l the vector position of the light to p
     * @param nl dot product of the vectors n,l
     * @param v  vector of camera
     * @return
     */
    private double calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        //first, we calc r, the specular vector
        double ln = l.dotProduct(n);
        Vector r = l.subtract(n.scale(-2*ln)).normalize();
        double vr = v.dotProduct(r);

        //now we calc specular effect
        if(-vr > 0){
            double x = material.kS*Math.pow((-1*vr),material.nShininess);
            return x;
        }
        else return 0;
    }

    /**
     * This method calculate the diffusive effect of the light source
     * @param material the material of the geometry
     * @param nl dot product of the vectors n,l
     * @return
     */
    private double calcDiffusive(Material material, double nl) {
        if(nl < 0) //calc |nl|  --> abs(nl)
            nl = nl*-1;
        return material.kD*nl;
    }
}
