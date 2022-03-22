package integrationtests;

import geometries.Sphere;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import org.junit.jupiter.api.Test;
import renderer.Camera;

public class CameraIntegrationTests {

    private double width = 3;
    private double height = 3;
    private double nX = 3;
    private double nY = 3;
    private double distance = 1;
    Point position = new Point(0,0,0);
    Vector vUp = new Vector(0,1,0);
    Vector vTo = new Vector(0,0,-1);
    Camera camera = new Camera(position, vTo, vUp);

    /**
     * Integrated test method for {@link renderer.Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Sphere#findIntersections(Ray)}.
     */
    @Test
    void sphere() {
        Sphere sp = new Sphere(new Point(0,0,-3), 1);
        for(int i = 0; i < nX; i++){
            //Ray rayThroughPixel =
        }
    }
}
