package geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Polygons
 *
 * @author Dan
 *
 */
public class PolygonTest{

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void Constructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void GetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)), "Bad normal to trinagle");
    }

    /**
     * Test method for {@link Polygon#findIntersections(Ray)}.
     */
    @Test
    public void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple case, point is inside polygon
        Polygon pl = new Polygon(new Point(0, 0, 0), new Point(1, 0, 0), new Point(1, 1, 0), new Point(0, 1, 0));
        Ray r = new Ray(new Point(0.5, 0.5, -1), new Vector(0,0,1));
        assertEquals(new Point(0.5, 0.5, 0), pl.findIntersections(r).get(0), "TC01: simple case. wrong return vale");

        // TC02: There is a simple test, ray do not intersect the polygon, opposite to triangles ribs
        pl = new Polygon(new Point(1,1,1), new Point(-1,-1,1), new Point(-2,2,0),new Point(1,3,0.5));
        Ray oppositeToRibRay = new Ray(new Point(5,0,0), new Vector(-4,0,1));
        assertNull(pl.findIntersections(oppositeToRibRay), "Polygon.findIntersections is not returns null for a ray that not intersect the triangle, but intersect on the opposite of one of the triangle ribs");

        // TC03: There is a simple test, ray do not intersect the triangle, opposite to triangle vertexes
        Ray oppositeToVertexRay = new Ray(new Point(4,2,0), new Vector(-4,-4,1));
        assertNull(pl.findIntersections(oppositeToVertexRay), "Polygon.findIntersections is not returns null for a point that intersect the triangle, but intersect on the opposite of one of the triangles vertexes");

        // =============== Boundary Values Tests ==================

        // TC04: There is test, of a ray that intersect the rib of the polygon
        Ray intersectRibRay = new Ray(new Point(-1,1,2), new Vector(1,-1,-1));
        //assertEquals(new Point(0,0,1), triangle1.findIntersections(intersectRibRay), "triangle.findIntersections not working for intersection in the rib");
        assertNull(pl.findIntersections(intersectRibRay), "Polygon.findIntersections not returns null for intersection in the rib");

        // TC05: There is test, of a ray that intersect the vertex of the polygon
        Ray intersectVertexRay = new Ray(new Point(3,3,5), new Vector(-5,-1,-5));
        assertNull(pl.findIntersections(intersectVertexRay), "Polygon.findIntersections not returns null for intersection in the vertex");

        // TC06: There is test, of a ray that intersect the continuing of the rib of the polygon
        Ray intersectRibsContinueRay = new Ray(new Point(0,4,4), new Vector(-5,-1,-5));
        assertNull(pl.findIntersections(intersectRibsContinueRay), "Polygon.findIntersections not returns null for intersection in the continuing of the rib");//the intersection in the point (-5,3,-1)


    }



}