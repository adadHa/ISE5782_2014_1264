package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase{


    /**
     * a constant for breaking condition of transparency/reflection recursion
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * a constant for breaking condition of transparency/reflection recursion
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    private static final Double3 INITIAL_K = new Double3(1.0,1.0,1.0);

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
     * This function check whther a geopoint is being shaded by
     * @param gp
     * @param lightSource
     * @param l
     * @param n
     * @return
     */
    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n){
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(gp.point,lightDirection, n);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point));
        if(intersections == null)
            return true;
        return false;
    }

    /**
     *
     * @param ray
     * @return
     */
    @Override
    public Color traceRay(Ray ray) {
        Color pixelColor;
        GeoPoint closestPoint = findClosestIntersection(ray);
        if(closestPoint != null){
            int a = 1;
        }
        return closestPoint == null ? scene.background :
                calcColor(closestPoint, ray);
    }

    private Color calcColor(GeoPoint gp, Ray ray){
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k){
        Color color = intersection.geometry.getEmission()
                .add(calcColorLocalEffects(intersection, ray));
        Color globalEffects = color.add(calcGlobalEffects(intersection,ray,level,k));
        return 1 == level ? color : globalEffects;
    }

    /**
     * This function calculates the shadows effects.
     * @param geoPoint
     * @param ray
     * @return
     */
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
                if(unshaded(geoPoint,lightSource,l,n)){
                    Color iL = lightSource.getIntensity(geoPoint.point);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * This function calculates the transparency/reflection effects
     * @param gp
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Double3 kr = gp.geometry.getMaterial().kR, kkr = kr.product(k);
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDir();
        if (!kr.equals(Double3.ZERO) && !kkr.lowerThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = constructReflectedRay(gp, v, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            color = (reflectedPoint != null) ?
                    color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr)) : color;
        }

        Double3 kt = gp.geometry.getMaterial().kT, kkt = kt.product(k);
        if (!kt.equals(Double3.ZERO) && !kkt.lowerThan(MIN_CALC_COLOR_K)) {
            Ray refractedRay = constructRefractedRay(gp, v, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            color = (refractedPoint != null) ?
                    color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt))
                    : color;
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
        Vector r = l.add(n.scale(-2*ln)).normalize();
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

    /**
     * This function calculates the reflected ray according to
     * given ray, normal and intersection GeoPoint
     * @param geoPoint
     * @param v vector of the original ray.
     * @param n normal to the geometry at geoPoint.point
     * @return
     */
    private Ray constructReflectedRay(GeoPoint geoPoint,Vector v,Vector n){
        // r = v-2(vn)*n
        double vn = v.dotProduct(n);
        Vector r = v.add(n.scale(-2*vn));
        return new Ray(geoPoint.point,r,n);
    }

    /**
     * This function calculates the refracted ray according to
     * given ray, normal and intersection GeoPoint
     * @param geoPoint
     * @param v vector of the original ray.
     * @param n normal to the geometry at geoPoint.point
     * @return
     */
    private Ray constructRefractedRay(GeoPoint geoPoint,Vector v,Vector n) {
        // r = v #TODO are they normalized?
        // Snell's law: 1*sin(alfaR) = 1*sin(alfaI)
        Vector r = v.scale(1);
        return new Ray(geoPoint.point,r,n);
    }

    private GeoPoint findClosestIntersection(Ray ray){
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections != null)
        {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return closestPoint;
        }
        else return null;
    }
}
