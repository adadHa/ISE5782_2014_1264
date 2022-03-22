package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    private Point position;
    private Vector vTo;
    private Vector vRight;
    private Vector vUp;
    private double distanceCameraToViewPlane;
    private double width;
    private double height;

    /**
     *
     * @param position
     * @param vTo
     * @param vUp
     */
    public Camera(Point position, Vector vTo, Vector vUp) {
        if(!isZero(vTo.dotProduct(vUp))) //Vto is not orthogonal to vUp
            throw new IllegalArgumentException("Vto is not orthogonal to vUp");
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = vUp.crossProduct(vTo).normalize();

        this.position = position;
        this.vTo = vTo;
        this.vUp = vUp;
    }

    /**
     *
     * @param width
     * @param height
     * @return this
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     *
     * @param distancet
     * @return this
     */
    public Camera setVPDistance(double distance) {
        this.distanceCameraToViewPlane = distance;
        return this;
    }

    /**
     *
     * @return Point position
     */
    public Point getPosition() {
        return position;
    }

    /**
     *
     * @return Vector vTo
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     *
     * @return Vector vRight
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     *
     * @return Vector vUp
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     *
     * @return distance from camera to view plane
     */
    public double getDistanceCameraToViewPlane() {
        return distanceCameraToViewPlane;
    }

    /**
     *
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     *
     * @param nX width of view plane
     * @param nY height of view plane
     * @param j col
     * @param i row
     * @return the constructed ray.
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        return null;
    }

}
