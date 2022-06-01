
package renderer;

import primitives.*;

import scene.Scene;

import java.util.ArrayList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import static primitives.Util.*;


public class RayTracerBasic extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = new Double3(1.0);

    // super sampling reflection
    private boolean isReflectionSuperSamplingOn = false;
    private boolean isRefractionSuperSamplingOn = false;
    private final double distanceFromTargetArea = 100;
    private double glossyRaysAmount = 1;
    private double blurryRaysAmount = 1;
    /**
     * Construct RayTracerBasic base with scene and uses RayTracerBase constructor
     *
     * @param scene is the scene we want to trace
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
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
    double glossiness = gp.geometry.getMaterial().glossiness;
    double blurriness = gp.geometry.getMaterial().blurriness;
    Vector n = gp.geometry.getNormal(gp.point);
    Vector v = ray.getDir();
    if (!kr.equals(Double3.ZERO) && !kkr.lowerThan(MIN_CALC_COLOR_K)) {
        Ray reflectedRay = constructReflectedRay(gp, v, n);
        List<Ray> reflectedRays = constructMultiSamplingRays(reflectedRay, glossyRaysAmount,
                                                            distanceFromTargetArea, glossiness);
        color = color.add(calcAverageColor(reflectedRays,level-1,kkr).scale(kr));
        /*GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
        color = (reflectedPoint != null) ?
                color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr))
                : color;*/
    }

    Double3 kt = gp.geometry.getMaterial().kT, kkt = kt.product(k);
    if (!kt.equals(Double3.ZERO) && !kkt.lowerThan(MIN_CALC_COLOR_K)) {
        Ray refractedRay = constructRefractedRay(gp, v, n);
        List<Ray> refractedRays = constructMultiSamplingRays(refractedRay, blurryRaysAmount,
                                                            distanceFromTargetArea, blurriness);
        color = color.add(calcAverageColor(refractedRays,level-1,kkt).scale(kt));
        /*GeoPoint refractedPoint = findClosestIntersection(refractedRay);
        color = (refractedPoint != null) ?
                color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt))
                : color;*/
    }
    return color;
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
        Vector r = v.add(n.scale(-2*vn)).normalize();
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
        return new Ray(geoPoint.point,v,n);
    }

    /**
     *
     * This function check whther a geopoint is being shaded by
     * @param geoPoint
     * @param lightSource
     * @param l
     * @param n
     * @return
     */
    private boolean unshaded(GeoPoint geoPoint, LightSource lightSource, Vector l, Vector n){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point,lightDirection, n);
        double lightDistance = lightSource.getDistance(geoPoint.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(geoPoint.point));
        if(intersections == null)
            return true;
        return false;
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * The method finds the color of the point
     *
     * @param gP is the point
     * @return the color
     */
    private Color calcColor(GeoPoint gP, Ray ray) {
        return calcColor(gP, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k){
        Color color = intersection.geometry.getEmission()
                .add(calcColorLocalEffects(intersection, ray, k));
        return 1 == level ? color :  color.add(calcGlobalEffects(intersection,ray,level,k));
    }


    /**
     * This function calculates the shadows effects.
     * @param geoPoint
     * @param ray
     * @return
     */
    private Color calcColorLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
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
                Double3 ktr = transparency(l, n,geoPoint, lightSource);
                if (!k.product(ktr).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
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
        Vector r = (l.add(n.scale(-2*ln))).normalize();
        double vr = v.dotProduct(r);

        //now we calc specular effect
        if(-vr > 0){
            double x = material.kS*Math.pow((-1*vr),material.nShininess);
            return x;
        }
        else return 0;
    }



    /**
     * The function finds the closes point among the intersection points to the
     * ray's source
     *
     * @param ray is the ray
     * @return the closes point to the ray's source
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }

    /**
     * The function calculates the transpareced light to the point
     *
     * @param geoPoint is the point
     * @param lS       is the light source
     * @param l        is the vector from the light source to the point
     * @param n        is the normal vector the point
     * @return the transpareced light.
     */
    private Double3 transparency(Vector l, Vector n,GeoPoint geoPoint, LightSource lS) {
        Ray lightRay = new Ray(geoPoint.point, l.scale(-1), n);
        double lightDistance = lS.getDistance(geoPoint.point);
        Double3 ktr = new Double3(1.0);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightDistance);
        if (intersections == null)
            return ktr;

        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                ktr = ktr.product(gp.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO;
            }
        }
        return ktr;
    }

    public RayTracerBasic setReflectionSuperSamplingOn(double glossyRaysAmount){
        this.isReflectionSuperSamplingOn = true;
        this.glossyRaysAmount = glossyRaysAmount;
        return this;
    }

    public RayTracerBase setRefractionSuperSamplingOn(double blurryRaysAmount){
        this.isRefractionSuperSamplingOn = true;
        this.blurryRaysAmount = blurryRaysAmount;
        return this;
    }

    private List<Ray> constructMultiSamplingRays(Ray ray, double raysAmount, double distanceFromTargetArea, double targetAreaSize) {
        ArrayList<Ray> resultList = new ArrayList<Ray>();
        Ray rayThroughPixel;
        /*Vector vectorToThePixel, v1 = ray.getDir().findPrependicular(),
                v2 = v1.crossProduct(ray.getDir());
        Point targetAreaCenter = ray.getPoint(distanceFromTargetArea);
        Point point;
        double targetSquareLength = targetAreaSize*2;
        double interval = targetSquareLength/raysAmount;
        resultList.add(ray);
        if(raysAmount == 0 || isZero(targetAreaSize))
            return resultList;
        //grid
        for (double z = 0; z < raysAmount; z++)
        {
            // move point each iteration to the left of the current row
            // (the pixel is divided to grid of rows and columns
            // [interval times rows and interval times columns])
            if(!isZero(targetSquareLength/2 - (interval)*z))
                point = targetAreaCenter.add(v1.scale(targetSquareLength/2 - (interval)*z));
            else point = targetAreaCenter;
            point = point.add(v2.scale(-targetSquareLength/2));
            for (int q = 0; q < raysAmount; q++){
                point = point.add(v2.scale(interval*targetSquareLength));
                vectorToThePixel = point.subtract(ray.getP0());
                rayThroughPixel = new Ray(ray.getP0(), vectorToThePixel);
                resultList.add(rayThroughPixel);
            }
        }
        return resultList;*/

        Vector rotatedVector, v1 = ray.getDir().findPrependicular(),
        v2 = v1.crossProduct(ray.getDir());
        Point point, targetAreaCenter = ray.getPoint(distanceFromTargetArea);
        resultList.add(ray);
        if(raysAmount == 0)
            return resultList;
        double randomRadius,randomAngle; //-----------------------random points on circle
        for(int i = 1; i < raysAmount; i++){
            randomRadius = random(0.01,targetAreaSize);
            randomAngle = random(0,360);
            rotatedVector = v1.rotate(v2,randomAngle);
            point = targetAreaCenter.add(rotatedVector.scale(randomRadius));
            resultList.add(new Ray(ray.getP0(), point.subtract(ray.getP0())));
        }
        return resultList;
    }

    /**
     * This method get a list of rays and return the average color of the intersection points of the rays
     * with the scene
     * @param reflectedRays
     * @param level
     * @param k
     * @return
     */
    private Color calcAverageColor(List<Ray> reflectedRays, int level, Double3 k) {
        Color sumColor = new Color(0,0,0);
        Color color;
        GeoPoint intersection;
        double counter = 0;
        for(Ray ray : reflectedRays){
            intersection = findClosestIntersection(ray);
            if(intersection != null)
            {
                color = calcColor(intersection,ray,level,k);
                sumColor = sumColor.add(color);
                counter++;
            }
        }
        if(counter != 0)
            sumColor = sumColor.scale((double) (1/counter));
        return sumColor;
    }
}
