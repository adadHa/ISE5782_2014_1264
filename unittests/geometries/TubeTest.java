package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class

TubeTest {
    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01 - simple case
        Point p = new Point(0.5,0,1);
        Tube x = new Tube(
                new Ray(new Point(0,0,0), new Vector(1,0,0)),
                1
                );
        assertEquals( new Vector(0,0,1), x.getNormal(p));

        // =============== Boundary Values Tests ==================
        // TC10: vector pp0 is orthogonal to v
        p = new Point(0,1,0);
        x = new Tube(
                new Ray(new Point(0,0,0), new Vector(1,0,0)),
                1
        );
        assertEquals( new Vector(0,1,0), x.getNormal(p));
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}.
     */
    @Test
    void getIntersections() {

        // ============ Equivalence Partitions Tests ==============

        Ray tubesRay = new Ray(new Point(-20,1,0), new Vector(30,0,0));
        Tube tube = new Tube(tubesRay, 4);

        // EP01: ray is outside from the tube (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-10, 8, 2), new Vector(19, -5, 8))), "Wrong in tube.findIntersections EP01 (in a Ray that outside the tube)");

        // EP02: ray starts before and intersect the tube (2 points)
        Point p1 = new Point(-18, 1, -4);
        Point p2 = new Point(-10, 5, 0);
        List<Point> EP02Intersections = tube.findIntersections(new Ray(new Point(-2,9,4), new Vector (-24,-12,-12)));
        assertEquals(2, EP02Intersections.size(), "Wrong number of points in EP02 (start before and intersect)");
        if (EP02Intersections.get(0).getX() > EP02Intersections.get(1).getX())
            EP02Intersections = List.of(EP02Intersections.get(1), EP02Intersections.get(0));
        assertEquals(List.of(p1, p2), EP02Intersections, "In EP02, points are incorrect (ray starts before and intersect the Tube)");

        // EP03:  ray starts in the tube and intersect it (1 point)
        List<Point> EP03Intersections = tube.findIntersections(new Ray(new Point(-5,0,2), new Vector(6,2,4)));
        assertEquals(EP03Intersections.size(), 1, "Wrong number of points in EP03 (start in and intersect)");
        assertEquals(new Point(-2, 1, 4).distanceSquared(new Point(0,0,0)),
                EP03Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In EP03, the point is incorrect (ray starts in and intersect the Tube)");

        // EP04: ray starts after the tube and not intersect the tube (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-22,-1,-6), new Vector(-4,-2,-2))), "Wrong in tube.findIntersections EP04 (in a Ray that starts after and do not crosses the tube)");



        // =============== Boundary Values Tests ==================
        // BV01: ray intersect the tube through the central ray (2 points)
        Point BV01p1 = new Point(4, 5, 0);
        Point BV01p2 = new Point(12, -3, 0);
        List<Point> BV01Intersections = tube.findIntersections(new Ray(new Point(14,-5,0), new Vector (-2,2,0)));
        assertEquals(2, BV01Intersections.size(), "Wrong number of points in BV01 (ray intersect the tube through the central ray)");
        if (BV01Intersections.get(0).getX() > BV01Intersections.get(1).getX())
            BV01Intersections = List.of(BV01Intersections.get(1), BV01Intersections.get(0));
        assertEquals(List.of(BV01p1, BV01p2), BV01Intersections, "In BV01, points are incorrect (ray intersect the tube through the central ray)");


        //**** Group: ray starts on the tube, we not count the start of the ray like an intersection
        // BV02: ray starts on the tube and geos in (1 point)
        List<Point> BV02Intersections = tube.findIntersections(new Ray(new Point(-18,1,-4), new Vector(40,-8,8)));
        assertEquals(BV02Intersections.size(), 1, "Wrong number of points in BV02 (start on and goes inside the tube (1 point should be))");
        assertEquals(new Point(2, -3, 0).distanceSquared(new Point(0,0,0)),
                BV02Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV02, the point is incorrect (ray starts on and goes inside the tube)");

        // BV03: ray starts on the tube and goes out (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(2,-3,0), new Vector(18,-2,4))), "Wrong in tube.findIntersections BV03 (in a Ray that starts on and goes outside the tube)");

        // BV04: ray starts on the tube goes in and touch the central ray (1 point)
        List<Point> BV04Intersections = tube.findIntersections(new Ray(new Point(2,-3,0), new Vector(3,8,0)));
        assertEquals(BV04Intersections.size(), 1, "Wrong number of points in BV04 (start on and intersect, go through the central ray)");
        assertEquals(new Point(5, 5, 0).distanceSquared(new Point(0,0,0)),
                BV04Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV04, the point is incorrect (ray starts on and intersect the Tube through the central ray)");

        // BV05: ray starts on the tube goes out and only due to this do not touch the central ray (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(2,-3,0), new Vector(-3,-8,0))), "Wrong in tube.findIntersections BV05 (in a Ray that starts on and goes outside the tube, if the ray started earlier, it should go through the center)");

        // BV06: ray starts on the tube and perpendicular to it, goes inside (1 point)
        List<Point> BV06Intersections = tube.findIntersections(new Ray(new Point(2,-3,0), new Vector(0,8,0)));
        assertEquals(BV06Intersections.size(), 1, "Wrong number of points in BV06 (ray start on and intersect the tube (goes inside), perpendicular to the tube)");
        assertEquals(new Point(2, 5, 0).distanceSquared(new Point(0,0,0)),
                BV06Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV06, the point is incorrect (ray start on and intersect the tube (goes inside), perpendicular to the tube)");

        // BV07: ray starts on the tube and perpendicular to it, goes outside (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(2,-3,0), new Vector(0,-8,0))), "Wrong in tube.findIntersections BV07 (ray start on and not intersect the tube (goes outside), perpendicular to the tube)");


        //**** Group: ray starts in the tube
        // BV08: ray starts in the tube and intersect it, through the central ray (1 point)
        List<Point> BV08Intersections = tube.findIntersections(new Ray(new Point(-6,-1,0), new Vector(-4,6,0)));
        assertEquals(BV08Intersections.size(), 1, "Wrong number of points in BV08 (ray start in and intersect the tube (goes inside) through the central ray)");
        assertEquals(new Point(-10, 5, 0).distanceSquared(new Point(0,0,0)),
                BV08Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV08, the point is incorrect (ray start in and intersect the tube (goes inside) through the central ray)");

        // BV09: ray starts in the tube goes out and only due to this do not touch the central ray (1 points)
        List<Point> BV09Intersections = tube.findIntersections(new Ray(new Point(-6,-1,0), new Vector(4,-6,0)));
        assertEquals(BV09Intersections.size(), 1, "Wrong number of points in BV09 (ray start in and intersect the tube, goes outside and only due to this do not across through the central ray)");
        assertEquals(new Point(-4.67, -3, 0).distanceSquared(new Point(0,0,0)),
                BV09Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.1,
                "In BV09, the point is incorrect (ray start in and intersect the tube, goes outside and only due to this do not across through the central ray)");

        // BV10: ray starts in the tube and perpendicular to it, goes inside, through the central ray (1 point)
        List<Point> BV10Intersections = tube.findIntersections(new Ray(new Point(-10,-2,0), new Vector(0,7,0)));
        assertEquals(BV10Intersections.size(), 1, "Wrong number of points in BV10 (ray start in and intersect the tube, perpendicular to it (goes inside) through the central ray)");
        assertEquals(new Point(-10, 5, 0).distanceSquared(new Point(0,0,0)),
                BV10Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV10, the point is incorrect (ray start in and intersect the tube, perpendicular to it (goes inside) through the central ray)");

        // BV11: ray starts in the tube and perpendicular to it, goes outside, and only due to this not goes through the central ray (1 point)
        List<Point> BV11Intersections = tube.findIntersections(new Ray(new Point(-10,-2,0), new Vector(0,-1,0)));
        assertEquals(BV11Intersections.size(), 1, "Wrong number of points in BV11 (ray start in and intersect the tube, perpendicular to it ,goes outside and only doe to this do not across the central ray)");
        assertEquals(new Point(-10, -3, 0).distanceSquared(new Point(0,0,0)),
                BV11Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV11, the point is incorrect (ray start in and intersect the tube, perpendicular to it ,goes outside and only doe to this do not across the central ray)");



        //**** Group: the ray starts in the tube`s ray.
        //BV12: ray starts in the tube`s central ray (1 point)
        //List<Point> BV12Intersections = tube.findIntersections(new Ray(new Point(12,1,0), new Vector(-13,-4,0)));
        //assertEquals(BV12Intersections.size(), 1, "Wrong number of points in BV12 (ray start on the tube`s central ray and intersect the tube)");
        //assertEquals(new Point(-1, -3, 0).distanceSquared(new Point(0,0,0)),
        //        BV12Intersections.get(0).distanceSquared(new Point(0,0,0)),
        //        0.0001,
        //        "In BV12, the point is incorrect (ray start on the tube`s central ray and intersect the tube)");

        // BV13: ray starts in the tube`s central ray and perpendicular to the ray (1 point)
        //List<Point> BV13Intersections = tube.findIntersections(new Ray(new Point(12,1,0), new Vector(0,4,0)));
        //assertEquals(BV13Intersections.size(), 1, "Wrong number of points in BV13 (ray start on the tube`s central ray and perpendicular to the ray, intersect the tube)");
        //assertEquals(new Point(12, 5, 0).distanceSquared(new Point(0,0,0)),
        //        BV13Intersections.get(0).distanceSquared(new Point(0,0,0)),
        //        0.0001,
        //        "In BV13, the point is incorrect (ray start on the tube`s central ray and perpendicular to the ray, intersect the tube)");



        //**** Group: ray parallel to the tube`s central ray in various options.
        // BV14: ray starts outside the tube and parallel to the tube`s ray (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-10,5,5), new Vector(20,0,0))), "Wrong in tube.findIntersections BV14 (ray start outside and parallel to the tube)");

        // BV15: ray starts on the tube and parallel to the tube`s ray (infinite points, we will call it null)
        assertNull(tube.findIntersections(new Ray(new Point(-10,5,0), new Vector(20,0,0))), "Wrong in tube.findIntersections BV15 (ray start on and parallel to the tube)");

        // BV16: ray starts in the tube and parallel to the tube`s ray (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-10,3,1), new Vector(20,0,0))), "Wrong in tube.findIntersections BV16 (ray start in and parallel to the tube)");

        // BV17: ray starts in the tube`s ray and contained in this ray (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-10,1,0), new Vector(20,0,0))), "Wrong in tube.findIntersections BV17 (ray start in the tube`s ray and parallel to the tube)");



        //**** Group: ray vector has 90 graduates with the tube central vector, but the rays do not across one other
        // BV18: ray starts and continue outside the tube and has 90 graduates with the tube`s ray (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-10,-8,6), new Vector(0,12,0))), "Wrong in tube.findIntersections BV18 (ray start and continue outside the tube, has 90 graduates with the tube ray)");

        // BV19: ray starts before the tube and tangent to the tube, has 90 graduates with the tube`s ray (1 points)
        List<Point> BV19Intersections = tube.findIntersections(new Ray(new Point(-10,8,4), new Vector(0,-1,0)));
        assertEquals(BV19Intersections.size(), 1, "Wrong number of points in BV19 (ray start before the tube, ray tangent to the tube and has 90 graduates with the tube ray, intersect the tube)");
        assertEquals(new Point(-10, 1, 4).distanceSquared(new Point(0,0,0)),
                BV19Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV19, the point is incorrect (ray start before the tube, ray tangent to the tube and has 90 graduates with the tube ray, intersect the tube)");

        // BV20: ray starts on the tube (ray starts on the tangent point) and has 90 graduates with the tube`s ray (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-10,1,4), new Vector(0,1,0))), "Wrong in tube.findIntersections BV20 (ray start on the tube in the tangent point, has 90 graduates with the tube ray)");

        // BV21: ray starts after the tube (ray starts on the tangent point) and has 90 graduates with the tube`s ray (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-10,-8,4), new Vector(0,-1,0))), "Wrong in tube.findIntersections BV21 (ray start after the tangent point, not in the tube, has 90 graduates with the tube ray)");

        // BV22: ray starts before the tube, intersect the tube and has 90 graduates with the tube`s ray, intersect the tube (2 points)
        Point BV22p1 = new Point(-10, 1, 4);
        Point BV22p2 = new Point(-10, -3, 0);
        List<Point> BV22Intersections = tube.findIntersections(new Ray(new Point(-10,-5,-2), new Vector (0,6,6)));
        assertEquals(2, BV22Intersections.size(), "Wrong number of points in BV22 (ray intersect the tube, has 90 graduates with the tubes ray)");
        if (BV22Intersections.get(0).getX() > BV22Intersections.get(1).getX())
            BV22Intersections = List.of(BV22Intersections.get(1), BV22Intersections.get(0));
        assertEquals(List.of(BV22p1, BV22p2), BV22Intersections, "In BV22, points are incorrect (ray intersect the tube, has 90 graduates with the tubes ray)");

        // BV23: ray starts on the tube, goes inside and has 90 graduates with the tube`s ray (1 point)
        List<Point> BV23Intersections = tube.findIntersections(new Ray(new Point(-10,-3,0), new Vector(0,6,6)));
        assertEquals(BV23Intersections.size(), 1, "Wrong number of points in BV23 (ray start on the tube, goes inside and has 90 graduates with the tube ray, intersect the tube)");
        assertEquals(new Point(-10, 1, 4).distanceSquared(new Point(0,0,0)),
                BV23Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV23, the point is incorrect (ray start on the tube, goes inside and has 90 graduates with the tube ray, intersect the tube)");

        // BV24: ray starts in the tube and has 90 graduates with the tube`s ray (1 point)
        List<Point> BV24Intersections = tube.findIntersections(new Ray(new Point(-10,-1,2), new Vector(0,6,6)));
        assertEquals(BV24Intersections.size(), 1, "Wrong number of points in BV24 (ray start in the tube, and has 90 graduates with the tube ray, intersect the tube)");
        assertEquals(new Point(-10, 1, 4).distanceSquared(new Point(0,0,0)),
                BV24Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV24, the point is incorrect (ray start in the tube, and has 90 graduates with the tube ray, intersect the tube)");

        // BV25: ray starts on the tube, goes outside and has 90 graduates with the tube`s ray (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-10,1,4), new Vector(0,6,6))), "Wrong in tube.findIntersections BV25 (ray start on the tube, goes outside and has 90 graduates with the tube ray)");

        /* we need it? */
        // BV26: ray starts in double tangent point and the ray tangent to the tube, has 90 graduates with the tube ray (1 point)
        List<Point> BV26Intersections = tube.findIntersections(new Ray(new Point(-10,8,4), new Vector(0,-7,0)));
        assertEquals(BV26Intersections.size(), 1, "Wrong number of points in BV26 (ray start in double tangent point, and has 90 graduates with the tube ray, intersect the tube)");
        assertEquals(new Point(-10, 1, 4).distanceSquared(new Point(0,0,0)),
                BV26Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV26, the point is incorrect (ray start in double tangent point, and has 90 graduates with the tube ray, intersect the tub)");

        /* we need it? */
        // BV27: ray starts in double tangent point, tangents are perpendicular, and the ray tangent to the tube, has 90 graduates with the tube ray (1 point)
        List<Point> BV27Intersections = tube.findIntersections(new Ray(new Point(-10,5,4), new Vector(0,-4,0)));
        assertEquals(BV27Intersections.size(), 1, "Wrong number of points in BV27 (ray start in double tangent point, tangent are perpendicular one other, and has 90 graduates with the tube ray, intersect the tube)");
        assertEquals(new Point(-10, 1, 4).distanceSquared(new Point(0,0,0)),
                BV27Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV27, the point is incorrect (ray start in double tangent point, tangent are perpendicular one other, and has 90 graduates with the tube ray, intersect the tub)");



        //**** Group: ray tangent to the tube not has 90 graduates with the tube ray
        // BV28: ray starts before the tube and tangent to it (1 point)
        List<Point> BV28Intersections = tube.findIntersections(new Ray(new Point(-20.5,-9.5,-4), new Vector(10.5,10.5,0)));
        assertEquals(BV28Intersections.size(), 1, "Wrong number of points in BV28 (ray start before the tube and tangent to the tube)");
        assertEquals(new Point(-10, 1, -4).distanceSquared(new Point(0,0,0)),
                BV28Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV28, the point is incorrect (ray start before the tube and tangent to the tube)");

        // BV29: ray starts in the tangent point (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-10,1,-4), new Vector(2.83,2.83,0))), "Wrong in tube.findIntersections BV29 (ray start in the tangent point on the tube)");

        // BV30: ray starts after the tangent point to tube (0 point)
        assertNull(tube.findIntersections(new Ray(new Point(-20.5,-9.5,-4), new Vector(-2.83,-2.83,0))), "Wrong in tube.findIntersections BV30 (ray start after the tangent point on the tube)");

        /* we need it? */
        // BV31: ray starts in double tangent point and the ray tangent to the tube (1 point)
        List<Point> BV31Intersections = tube.findIntersections(new Ray(new Point(-20.5,-9.5,-4), new Vector(10.5,10.5,0)));
        assertEquals(BV31Intersections.size(), 1, "Wrong number of points in BV28 (ray start before the tube, in double tangent point, and tangent to the tube)");
        assertEquals(new Point(-10, 1, -4).distanceSquared(new Point(0,0,0)),
                BV31Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.0001,
                "In BV28, the point is incorrect (ray start before the tube, in double tangent point, and tangent to the tube)");

        /* we need it? */
        // BV32: ray starts in double tangent point, tangents are perpendicular, and the ray tangent to the tube (1 point)

        // ****Group: others
        // BV33: ray intersect the tube through the central ray, perpendicular to the central ray (2 points)
        Point BV33p1 = new Point(-10, 1, 4);
        Point BV33p2 = new Point(-10, 1, -4);
        List<Point> BV33Intersections = tube.findIntersections(new Ray(new Point(-10,1,-8), new Vector (0,0,6)));
        assertEquals(2, BV33Intersections.size(), "Wrong number of points in BV33 (ray intersect the tube through the central ray, perpendicular to it)");
        if (BV33Intersections.get(0).getX() > BV33Intersections.get(1).getX())
            BV33Intersections = List.of(BV33Intersections.get(1), BV33Intersections.get(0));
        assertEquals(List.of(BV33p1, BV33p2), BV33Intersections, "In BV33, points are incorrect (ray intersect the tube through the central ray, perpendicular to it)");

        /*
       to do: this cases only in cylinder, because of that tube not has an edge (starting or ending)

        //**** Group: ray starts on the first circle (of ray`s point), and in it`s KTZAVOT

        //**** Group: the intersection in the first circle (of ray`s point)

        //**** Group: ray not cross the tube because the tube started too late (if the tube point starts earlier they would intersect)

        */



        /* // ============ Equivalence Partitions Tests ==============

        Ray tubesRay = new Ray(new Point(-20,1,1), new Vector(40,-1,-1));
        Tube tube = new Tube(tubesRay, 4);

        // EP01: ray is outside from the tube (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))), "Ray's line out of sphere");

        // EP02: ray starts before and intersect the tube (2 points)
        Point p1 = new Point(7.12, 2.96, 2);
        Point p2 = new Point(3, -4.25, 0);
        List<Point> EP02Intersections = tube.findIntersections(new Ray(new Point(10,8,2), new Vector (-8,-14,0)));
        assertEquals(2, EP02Intersections.size(), "Wrong number of points in EP02 (start before and intersect)");
        if (EP02Intersections.get(0).getX() > EP02Intersections.get(1).getX())
            EP02Intersections = List.of(EP02Intersections.get(1), EP02Intersections.get(0));
        assertEquals(List.of(p1, p2), EP02Intersections, "In EP01, points are incorrect (ray starts before and intersect the Tube)");

        // EP03:  ray starts in the tube and intersect them (1 point)
        List<Point> EP03Intersections = tube.findIntersections(new Ray(new Point(5,-1,2), new Vector(0,8,0)));
        assertEquals(EP03Intersections.size(), 1, "Wrong number of points in EP03 (start in and intersect)");
        assertEquals(new Point(0.708876, 0, 1.527415).distanceSquared(new Point(0,0,0)),
                EP03Intersections.get(0).distanceSquared(new Point(0,0,0)),
                0.001,
                "In EP03, the point is incorrect (ray starts in and intersect the Tube)");

        // EP04: ray starts after the tube and not intersect the tube (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(10,8,2), new Vector(-8,2,0))), "Wrong in tube.findIntersections in a Ray that do not crosses the tube EP03");



        // =============== Boundary Values Tests ==================
        //**** Group: ray starts on the tube
        // BV01: ray starts in the tube and geos in (1 point), we not count the start of the ray*/

    }
}