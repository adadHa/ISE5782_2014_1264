package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.random;

public class MultiSampling {
    /**
     * This function creates a list of random cast rays on a target area at a given size.
     * @param ray the ray on which the target area sit.
     * @param raysAmount
     * @param distanceFromTargetArea
     * @param targetAreaSize
     * @return
     */
    public static List<Ray> constructMultiSamplingRaysRandom(Ray ray, double raysAmount, double distanceFromTargetArea, double targetAreaSize) {
        ArrayList<Ray> resultList = new ArrayList<Ray>();
        Ray rayThroughPixel;

        Vector rotatedVector, v1 = ray.getDir().findPrependicular(),
                v2 = v1.crossProduct(ray.getDir());
        Point point, targetAreaCenter = ray.getPoint(distanceFromTargetArea);
        resultList.add(ray);
        if (raysAmount == 0)
            return resultList;
        double randomRadius, randomAngle; //-----------------------random points on circle
        for (int i = 1; i < raysAmount; i++) {
            randomRadius = random(0.01, targetAreaSize);
            randomAngle = random(0, 360);
            rotatedVector = v1.rotate(v2, randomAngle);
            point = targetAreaCenter.add(rotatedVector.scale(randomRadius));
            resultList.add(new Ray(ray.getP0(), point.subtract(ray.getP0())));
        }
        return resultList;
    }
}