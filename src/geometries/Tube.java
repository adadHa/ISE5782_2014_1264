package geometries;

import primitives.Point;
import primitives.Util.*;
import primitives.Vector;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Tube extends Geometry {
    private final Ray axisRay;
    private final double radius;

    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point p) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();
        double t = v.dotProduct(p.subtract(p0));
        Point o = null;
        Vector n = null;
        if(t != 0)
        {
             o = p0.add(v.scale(t));
             n = p.subtract(o).normalize();
        }
        else
            n = p.subtract(p0);
        return n;
    }

    /**
     * Function for finding intersections geoPoints with an infinite tube.
     * @param ray The ray that we check if it intersects the tube.
     * @return A list of intersection geoPoints, if any.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        List<GeoPoint> toReturn = new ArrayList<>();

        Point rayP0 = ray.getP0();

        Vector rayDir = ray.getDir(),
                tubeDir = this.axisRay.getDir(),
                DeltaPoints = rayP0.subtract(this.axisRay.getP0()),
                temp_for_use1 = null, temp_for_use2 = null;

        double rDir_dot_tDir = rayDir.dotProduct(tubeDir),
                DeltaP_dot_tDir = DeltaPoints.dotProduct(tubeDir),
                A = 0,B = 0,C = 0;

        boolean isAandBzero = false, isBandC = false;

        if (!isZero(rDir_dot_tDir) && rayDir.equals(tubeDir.scale(rDir_dot_tDir)))
            isAandBzero = true;
        else if(!isZero(rDir_dot_tDir))
            temp_for_use1 = rayDir.subtract(tubeDir.scale(rDir_dot_tDir));
        else temp_for_use1 = rayDir;

        if(!isZero(DeltaP_dot_tDir) && DeltaPoints.equals(tubeDir.scale(DeltaP_dot_tDir)))
            isBandC = true;
        else if(!isZero(DeltaP_dot_tDir))
            temp_for_use2 = DeltaPoints.subtract(tubeDir.scale(DeltaP_dot_tDir));
        else temp_for_use2 = DeltaPoints;

        A = !isAandBzero ? temp_for_use1.dotProduct(temp_for_use1) : 0;
        B = !(isAandBzero || isBandC) ? 2 * temp_for_use1.dotProduct(temp_for_use2): 0;
        C = !isBandC ? temp_for_use2.dotProduct(temp_for_use2) - this.radius * this.radius: 0;
        //double desc = calcs.subtract(B*B, 4*A*C);
        double desc = alignZero(B*B - 4*A*C);

        if (desc < 0) {//No solution
            return null;
            //return toReturn;
        }

        double t1 = (-B+Math.sqrt(desc))/(2*A),
                t2 = (-B-Math.sqrt(desc))/(2*A);

        if (desc == 0.0) {//One solution
            if (A==0 || -B/(2*A) <= 0)
                return null; //return toReturn;

            //toReturn.add(new Vector(rayP0.add(rayDir.scale(-B/(2*A)/*it is t1*/).getHead())).getHead());
            Point intersection = ray.getPoint(t1);
            if(intersection != rayP0 && alignZero(t1 - maxDistance) <= 0) {
                toReturn.add(new GeoPoint(this,intersection));
                return toReturn;
            }
            return null;
        }
        else if (t1 < 0 && t2 < 0){
            //return toReturn;
            return null;
        }
        else if (t1 < 0 && t2 > 0) {
            //toReturn.add(new Vector(rayP0.add(rayDir.scale(t2).getHead())).getHead());
            Point intersection = ray.getPoint(t2);
            if(intersection != rayP0 && alignZero(t2 - maxDistance) <= 0) {
                toReturn.add(new GeoPoint(this,intersection));
                return toReturn;
            }
            return null;
        }
        else if (t1 > 0 && t2 < 0) {
            //toReturn.add(new Vector(rayP0.add(rayDir.scale(t1).getHead())).getHead());
            Point intersection = ray.getPoint(t1);
            if(intersection != rayP0 && alignZero(t1 - maxDistance) <= 0) {
                toReturn.add(new GeoPoint(this,intersection));
                return toReturn;
            }
            return null;
        }
        else {
            //toReturn.add(new Vector(rayP0.add(rayDir.scale(t1).getHead())).getHead());
            //toReturn.add(new Vector(rayP0.add(rayDir.scale(t2).getHead())).getHead());
            if(!isZero(t1) && t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
                Point intersectionT1 = ray.getPoint(t1);
                toReturn.add(new GeoPoint(this,intersectionT1));
            }
            if(!isZero(t2) && t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
                Point intersectionT2 = ray.getPoint(t2);
                toReturn.add(new GeoPoint(this,intersectionT2));
            }
            if(toReturn.size() > 0)
                return toReturn;
            return null;
        }
    }
}
