package integrationtests;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import org.junit.jupiter.api.Test;
import renderer.Camera;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CameraIntegrationTests {

    private double width = 3;
    private double height = 3;

    private double distance = 1;
    Point position = new Point(0,0,0);
    Vector vUp = new Vector(0,1,0);
    Vector vTo = new Vector(0,0,-1);
    Camera camera = new Camera(position, vTo, vUp);


    /**
     * help function, call to the function construct ray from our camera, and send these rays to the shape,
     * @param shape
     * @param nX
     * @param nY
     * @return the amount of the intersections
     */
    private int returnSumIntersections(Intersectable shape, int nX, int nY) {

        int sumIntersections = 0;
        for(int i = 0; i < nX; i++){
            for(int j = 0; j < nY; j++){
                List<Point> intersection = shape.findIntersections(camera.constructRay(nX, nY,j,i));
                if(intersection != null) sumIntersections += intersection.size();
            }
        }
        return sumIntersections;
    }

    /**
     * Integrated test method for {@link renderer.Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Sphere#findIntersections(Ray)}.
     */
    @Test
    void sphere() {
        /* test cases of a sphere in the front of the view plane and the camera, we check if the number of intersections between the sphere and camera rays that function camera.constructRay returns right rays */

        // TC01: test of sending intersection rays from the camera to a sphere that not touching the view plane, in front of the central pixel
        camera.setVPDistance(1);
        camera.setVPSize(3,3);
        Sphere sphereRIs1 = new Sphere(new Point(0,0,-3), 1);

        assertEquals(2, returnSumIntersections(sphereRIs1, 3, 3), "camera.constructRay not returns the right rays that it should return, not right number of intersections to sphere that not touching the view plane");

       //TC02: test of sending intersection rays from the camera to a sphere that swallow the view plane but not the camera

        position = new Point(0,0,0.5);
        camera = new Camera(position, vTo, vUp);
        camera.setVPDistance(1);
        camera.setVPSize(3,3);
        Sphere sphereSwallowVP = new Sphere(new Point(0,0,-2.5), 2.5);

        assertEquals(18, returnSumIntersections(sphereSwallowVP, 3, 3), "camera.constructRay not returns the right rays that it should return, not right number of intersections to sphere that swallow the view plane, but not the camera");

        camera.setVPDistance(2);
        assertEquals(32, returnSumIntersections(sphereSwallowVP,4,4) );
        camera.setVPDistance(1);

        //TC03: test of sending intersection rays from the camera to a sphere that goes into the view plane (not swallowing)
        Sphere sphereIntoVp = new Sphere(new Point(0,0,-2), 2);

        assertEquals(10, returnSumIntersections(sphereIntoVp, 3, 3), "camera.constructRay not returns the right rays that it should return, not right number of intersections to sphere that goes into the view plane");

        //TC04: test of sending intersection rays from the camera to a sphere that swallow thw view plane and the camera
        position = new Point(0,0,0);
        camera = new Camera(position, vTo, vUp);
        camera.setVPDistance(1);
        camera.setVPSize(3,3);
        Sphere sphereEatsAll = new Sphere(new Point(0,0,-2), 4);

        assertEquals(9, returnSumIntersections(sphereEatsAll, 3, 3), "camera.constructRay not returns the right rays that it should return, not right number of intersections to sphere that swallow the camera and the view plane both");

        //TC05: test of sending intersection rays from the camera, the sphere is behind the camera (0 intersections)
        Sphere sphereBehind = new Sphere(new Point(0,0,1), 0.5);

        assertEquals(0,returnSumIntersections(sphereBehind, 3, 3), "camera.constructRay not returns the right rays that it should return, not right number of intersections to sphere that behind the camera");
    }

    /**
     * Integrated test method for {@link renderer.Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Plane#findIntersections(Ray)}.
     */
    @Test
    void plane() {
        /* test cases of a plane in the front of the view plane and the camera, we check if the number of intersections between the plane and camera rays that function camera.constructRay returns right rays */

        //TC01: test of sending intersection rays from the camera to a parallel plane to the view plane, not touch the view plane (all the rays should pass the same distance until the intersection with the plane)
        camera.setVPDistance(1);
        camera.setVPSize(3,3);
        Plane planeParallelToVP = new Plane(new Point(1,0,-3), new Point(4,6,-3), new Point(0,2,-3)); //the plane z=-3 is parallel to the view plane in z=-1
        assertEquals(9,returnSumIntersections(planeParallelToVP, 3, 3), "camera.constructRay not returns the right rays that it should return, not right number of intersections to a plane that parallel the camera and view plane");

        //TC02: test of sending intersection rays from the camera to a plane that slant relative to the view plane (the bottom rays should pass more distance than upper rays, but in the end, all rays intersect the plane)
        Plane planeDiagonalToVP = new Plane(new Point(1,0,-3), new Point(4,3,-3.5), new Point(1,6,-4));
        assertEquals(9, returnSumIntersections(planeDiagonalToVP, 3, 3), "camera.constructRay not returns the right rays that it should return, not right number of intersections to a slant to the view plane plane");

        //TC03: test of sending intersections rays from the camera to a plane that vey slant relative to the plane (the bottom rays not intersect the plane doe to the big turn aside of the plane)
        Plane planeVeryDiagonalToVP = new Plane(new Point(1,0,-5), new Point(4,3,-10), new Point(1,6,-15));
        assertEquals(6, returnSumIntersections(planeVeryDiagonalToVP, 3, 3), "camera.constructRay not returns the right rays that it should return, not right number of intersections to a very slant to the view plane plane (not all the rays should intersect)");
    }

    /**
     * Integrated test method for {@link renderer.Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    void triangle(){
        /* test cases of a triangle in the front of the view plane and the camera, we check if the number of intersections between the triangle and camera rays that function camera.constructRay returns right rays */

        //TC01: test of sending intersection rays from the camera to a small triangle in the front of the view plane (1 intersection)
        camera.setVPDistance(1);
        camera.setVPSize(3,3);
        Triangle triangleSmall = new Triangle(new Point(0,1,-2), new Point(1,-1,-2), new Point(-1,-1,-2));
        assertEquals(1, returnSumIntersections(triangleSmall, 3, 3), "camera.constructRay not returns the right rays that it should return, not right number of intersections to a small triangle in the front of the center of the view plane (only the central ray should intersect)");

        //TC02: test of sending intersection rays from the camera to a high triangle in the front of the view plane (2 intersection, center and upper center)
        Triangle triangleHigh = new Triangle(new Point(0,20,-2), new Point(1,-1,-2), new Point(-1,-1,-2));
        assertEquals(2, returnSumIntersections(triangleHigh, 3, 3), "camera.constructRay not returns the right rays that it should return, not right number of intersections to a high triangle in the front of the center of the view plane (only the central and the up central should intersect");
    }
}
