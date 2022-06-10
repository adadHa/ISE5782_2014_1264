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

    /**
     *
     * @param startPoint
     * @param center
     * @param v1
     * @param v2
     * @param raysAmount
     * @param targetAreaSize
     * @return
     */
    public static List<Ray> constructMultiSamplingRaysGrid(GeoPoint startPoint, GeoPoint center, Vector v1, Vector v2, double raysAmount, double targetAreaSize) {
        ArrayList<Ray> resultList = new ArrayList<Ray>();
        Ray rayThroughPixel;
        Vector vectorToThePixel;
        Point point;
        double targetSquareLength = targetAreaSize * 2;
        double interval = targetSquareLength / raysAmount;
        vectorToThePixel = center.point.subtract(startPoint.point);
        resultList.add(new Ray(startPoint.point,vectorToThePixel));
        if (raysAmount == 0 || isZero(targetAreaSize))
            return resultList;
        //grid
        for (double z = 0; z < raysAmount; z++) {
            // move point each iteration to the left of the current row
            // (the pixel is divided to grid of rows and columns
            // [interval times rows and interval times columns])
            if (!isZero(targetSquareLength / 2 - (interval) * z))
                point = center.add(v1.scale(targetSquareLength / 2 - (interval) * z));
            else point = center;
            point = point.add(v2.scale(-targetSquareLength / 2));
            for (int q = 0; q < raysAmount; q++) {
                point = point.add(v2.scale(interval * targetSquareLength));
                vectorToThePixel = point.subtract(ray.getP0());
                rayThroughPixel = new Ray(ray.getP0(), vectorToThePixel);
                resultList.add(rayThroughPixel);
            }
        }
        return resultList;
    }
}