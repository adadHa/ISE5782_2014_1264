package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Polygons
 *
 * @author Harel Addai
 *
 */
class TriangleTest {
    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle pl = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 1, 0)), "Bad normal to triangle");
    }

    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        /*ray always intersects the plane of the triangle, we check it, before of this function*/

        // TC01: There is a simple single test, ray intersect the triangle
        Triangle _triangle1 = new Triangle(new Point(0,0,1), new Point(0,4,1), new Point(4,0,1));
        Ray _simpleIntersectRay = new Ray(new Point(1,1,0), new Vector(0,0,1));
        assertEquals(new Point(1,1,1), _triangle1.findIntersections(_simpleIntersectRay).get(0), "Triangle.findIntersection is not working for simple intersect ray");

        Triangle triangle1 = new Triangle(new Point(1,1,1), new Point(-1,-1,1), new Point(-2,2,0));
        Ray simpleIntersectRay = new Ray(new Point(5,0,0), new Vector(-8,0,1));
        assertEquals(new Point(-1,0,0.75), triangle1.findIntersections(simpleIntersectRay).get(0), "Triangle.findIntersection is not working for simple intersect ray");

        // TC02: There is a simple test, ray do not intersect the triangle, opposite to triangles ribs
        Ray oppositeToRibRay = new Ray(new Point(5,0,0), new Vector(-4,0,1));
        assertNull(triangle1.findIntersections(oppositeToRibRay), "Triangle.findIntersections is not returns null for a ray that not intersect the triangle, but intersect on the opposite of one of the triangle ribs");

        // TC03: There is a simple test, ray do not intersect the triangle, opposite to triangle vertexes
        Ray oppositeToVertexRay = new Ray(new Point(4,2,0), new Vector(-4,-4,1));
        assertNull(triangle1.findIntersections(oppositeToVertexRay), "Triangle.findIntersections is not returns null for a point that intersect the triangle, but intersect on the opposite of one of the triangles vertexes");

        // =============== Boundary Values Tests ==================

        // TC04: There is test, of a ray that intersect the rib of the triangle
        Ray intersectRibRay = new Ray(new Point(-1,1,2), new Vector(1,-1,-1));
        //assertEquals(new Point(0,0,1), triangle1.findIntersections(intersectRibRay), "triangle.findIntersections not working for intersection in the rib");
        assertNull(triangle1.findIntersections(intersectRibRay), "triangle.findIntersections not returns null for intersection in the rib");

        // TC05: There is test, of a ray that intersect the vertex of the triangle
        Ray intersectVertexRay = new Ray(new Point(3,3,5), new Vector(-5,-1,-5));
        //assertEquals(new Point(-2,2,0), triangle1.findIntersections(intersectVertexRay), "triangle.findIntersections not working for intersection in the vertex");
        assertNull(triangle1.findIntersections(intersectVertexRay), "triangle.findIntersections not returns null for intersection in the vertex");

        // TC06: There is test, of a ray that intersect the continuing of the rib of the triangle
        Ray intersectRibsContinueRay = new Ray(new Point(0,4,4), new Vector(-5,-1,-5));
        assertNull(triangle1.findIntersections(intersectRibsContinueRay), "triangle1.findIntersections not returns null for intersection in the continuing of the rib");//the intersection in the point (-5,3,-1)
    }
}