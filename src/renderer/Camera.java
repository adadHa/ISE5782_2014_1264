package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import static primitives.Util.random;

public class Camera {
    private Point position;
    private Vector vTo;
    private Vector vRight;
    private Vector vUp;
    private double distanceCameraToViewPlane;
    private double width;
    private double height;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    private boolean AntiAliasingOn;
    private int eyeRaysAmount = 9;

    // Depth Of Field
    private boolean isDepthOfFieldOn = false;
    private double apertureRadius = 0;
    private double focalDistance;
    private  double focalRaysAmount = 20;

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
        this.vRight = vTo.crossProduct(vUp).normalize();
        this.position = position;
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
     * @param distance
     * @return this
     */
    public Camera setVPDistance(double distance) {
        this.distanceCameraToViewPlane = distance;
        return this;
    }

    /**
     *
     * @param imageWriter
     * @return
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     *
     * @param rayTracerBasic
     * @return
     */
    public Camera setRayTracer(RayTracerBase rayTracerBasic) {
        this.rayTracer = rayTracerBasic;
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
        //Ray[][] rayThroughPixel = new Ray[nX][nY];
        //rayThroughPixel[i][j] = new Ray(position, )

        Point pIJ = position.add(vTo.scale(distanceCameraToViewPlane)); // = Pc
        double rY = this.height / nY; //rY is the size of the vertical rib of the pixel (without the horizontal rib)
        double rX = this.width / nX; //rX is the size of the horizontal rib of the pixel (without teh vertical rib)

        double xJ = (j - (double)(nX - 1)/2) * rX; //xJ is the horizontal distance of our pixel from the central pixel (in pixels)
        double yI = -(i - (double)(nY - 1)/2) * rY; //yI is the vertical distance of our pixel from the central pixel (in pixels)
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));
        Vector vectorToThePixel = pIJ.subtract(this.position);
        Ray rayThroughPixel = new Ray(this.position, vectorToThePixel);
        return rayThroughPixel;
    }

    /**
     * this method render a 3d scene to an image.
     */
    public Camera renderImage() {
        if(imageWriter == null ||
                rayTracer == null)
            throw new MissingResourceException("Not all fields of camera were initiallized", "", "");
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        Ray ray;
        Color pixelColor;
        for (int i = 0; i < nX; i++){
            for (int j = 0; j < nY; j++){
                System.out.println(i + "," + j);
                if(AntiAliasingOn)
                    pixelColor = antiAliasing(nX,nY,j,i);
                else{
                    ray = constructRay(nX, nY, j, i);
                    pixelColor = rayTracer.traceRay(ray);
                }
                imageWriter.writePixel(j, i, pixelColor);
            }
        }
        return this;
    }

    /**
     * This function implements Anti Aliasing algorithm
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    private Color antiAliasing(int nX, int nY, int j, int i) {
        Color sumColors = new Color(0,0,0);
        Vector vectorToThePixel;
        Ray rayThroughPixel;
        Point pIJCenter = position.add(vTo.scale(distanceCameraToViewPlane)); // = Pc
        double rY = this.height / nY; //rY is the size of the vertical rib of the pixel (without the horizontal rib)
        double rX = this.width / nX; //rX is the size of the horizontal rib of the pixel (without teh vertical rib)
        double xJ = (j - (double)(nX - 1)/2) * rX; //xJ is the horizontal distance of our pixel from the central pixel (in pixels)
        double yI = -(i - (double)(nY - 1)/2) * rY; //yI is the vertical distance of our pixel from the central pixel (in pixels)
        if (xJ != 0) pIJCenter = pIJCenter.add(vRight.scale(xJ));
        if (yI != 0) pIJCenter = pIJCenter.add(vUp.scale(yI));

        // make the top-left corner of the pixel
        Point pIJ;
        double interval = rX/eyeRaysAmount;

        for (double z = 0; z < eyeRaysAmount; z++)
        {
            // move pIJ each iteration to the left of the current row
            // (the pixel is divided to grid of rows and columns
            // [interval times rows and interval times columns])
            if(!isZero(rX/2 - (interval)*z))
                pIJ = pIJCenter.add(vUp.scale(rX/2 - (interval)*z));
            else pIJ = pIJCenter;
            pIJ = pIJ.add(vRight.scale(-rX/2));
            for (int q = 0; q < eyeRaysAmount; q++){
                pIJ = pIJ.add(vRight.scale(interval*rX));
                vectorToThePixel = pIJ.subtract(this.position);
                rayThroughPixel = new Ray(this.position, vectorToThePixel);
                sumColors = sumColors.add(rayTracer.traceRay(rayThroughPixel));
            }
        }
        // calculate the average color (there are eyeRaysAmount^2 sample rays);
        Color avarageColor = sumColors.scale((double) 1/(eyeRaysAmount*eyeRaysAmount));
        return avarageColor;
    }

    /**
     * Turn on Anti Aliasing
     * @param eyeRaysAmount - set the amount of eye rays to produce (81 means 81X81)
     * @return
     */
    public Camera setAntiAliasingOn(int eyeRaysAmount) {
        this.eyeRaysAmount = eyeRaysAmount;
        AntiAliasingOn = true;
        return this;
    }

    /**
     * this method add a grid to the rendered image.
     * @param interval the width/height of each square in the grid.
     * @param color
     */
    public void printGrid(int interval, Color color) {
        if(imageWriter == null)
            throw new MissingResourceException("ImageWriter is null","","");
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nX; i+= interval){
            for (int j = 0; j < nY; j++){

                    imageWriter.writePixel(j, i, color);
            }
        }
        for (int i = 0; i < nX; i++){
            for (int j = 0; j < nY; j+= interval){

                imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * Function writeToImage produces unoptimized png file of the image according to pixel color matrix in the directory of the project
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }


    /**
     * spin the camera up and down around vRight vector
     * @param angle in degrees (how many we need move)
     * @return
     */
    public Camera spinAroundVRight(double angle){
        if(angle >= 360){
            angle = angle % 360;
        }
        if(angle > 180){
            angle = angle - 180; //now all the angles between -180 and 180
        }
        //we are move only the vUp and the vTo vectors
        double angleRad = Math.toRadians(angle);
        double sinA = Math.sin(angleRad);
        double cosA = Math.cos(angleRad);
        if(isZero(cosA)){ //if angle is 90 or -90 degrees, we move the camera 90 degrees upper
            vTo = vUp.scale(sinA); //sin (90) = 1, sin (-90) = -1, vTo = vUp or vTo = -vUp
        }
        else if(isZero(sinA)){ //if angle is 0 or 180 degrees
            vTo = vTo.scale(cosA); //cos(0) = 1, cos(180) = -1, vTo = vTo or vTo = -vTo
        }
        else{ //spin around the vTo vector according to the Rodrigues' rotation formula (rotation V = v*cosA + (K x v)*sinA + K*(K*V)(1-cosA), k is vRight in our situation
            vTo = vTo.scale(cosA).add(vRight.crossProduct(vTo).scale(sinA));
        }
        vUp = vRight.crossProduct(vTo).normalize(); //change the vUp vector according to the new vTo (vUp is perpendicular to vTo and vRight)

        return this;
    }

    /**
     * spin the camera left and right around vUp vector
     * @param angle in degrees (how many we need move)
     * @return
     */
    public Camera spinAroundVUp(double angle){
        if(angle >= 360){
            angle = angle % 360;
        }
        if(angle > 180){
            angle = angle - 180; //now all the angles between -180 and 180
        }
        //we are move only the vRight and the vTo vectors
        double angleRad = Math.toRadians(angle);
        double sinA = Math.sin(angleRad);
        double cosA = Math.cos(angleRad);
        if(isZero(cosA)){ //if angle is 90 or -90 degrees, we move the camera 90 degrees
            vRight = vTo.scale(sinA); //sin (90) = 1, sin (-90) = -1, vRight = vTo or vRight = -vTo
        }
        else if(isZero(sinA)){ //if angle is 0 or 180 degrees
            vRight = vRight.scale(cosA); //cos(0) = 1, cos(180) = -1, vRight = vRight or vRight = -vRight
        }
        else{ //spin around the vTo vector according to the Rodrigues' rotation formula (rotation V = v*cosA + (K x v)*sinA + K*(K*V)(1-cosA), k is vUp in our situation
            vRight = vRight.scale(cosA).add(vUp.crossProduct(vRight).scale(sinA));
        }
        vTo = vUp.crossProduct(vRight).normalize(); //change the vTo vector according to the new vRight (vTo is perpendicular to vUp and vRight)

        return this;
    }

    /**
     * spin the camera around vTo vector
     * @param angle in degrees (how many we need move)
     * @return
     */
    public Camera spinAroundVTo(double angle){
        if(angle >= 360){
            angle = angle % 360;
        }
        if(angle > 180){
            angle = angle - 180; //now all the angles between -180 and 180
        }
        //we are moving only the vUp and the vRight vectors
        double angleRad = Math.toRadians(angle);
        double sinA = Math.sin(angleRad);
        double cosA = Math.cos(angleRad);
        if(isZero(cosA)){ //if angle is 90 or -90 degrees, we move the camera 90 degrees
            vUp = vRight.scale(sinA); //sin (90) = 1, sin (-90) = -1, vUp = vRight or vUp = -vRight
        }
        else if(isZero(sinA)){ //if angle is 0 or 180 degrees
            vUp = vUp.scale(cosA); //cos(0) = 1, cos(180) = -1, vUp = vUp or vUp = -vUp
        }
        else{ //spin around the vTo vector according to the Rodrigues' rotation formula (rotation V = v*cosA + (K x v)*sinA + K*(K*V)(1-cosA), k is vTo in our situation
            vUp = vUp.scale(cosA).add(vTo.crossProduct(vUp).scale(sinA));
        }
        vRight = vTo.crossProduct(vUp).normalize(); //change the vTo vector according to the new vRight (vTo is perpendicular to vUp and vRight)

        return this;
    }


    /**
     * move the camera up or down
     * @param distance how many the camera goes up (it can be minus and go down)
     * @return
     */
    public Camera moveUpDown(double distance){
        if(!isZero(distance)) {
            position = position.add(vUp.scale(distance));
        }
        return this;
    }

    /**
     * move the camera right or left
     * @param distance how many the camera goes right (can be minus and go left)
     * @return
     */
    public Camera moveRightLeft(double distance){
        if(!isZero(distance)) {
            position = position.add(vRight.scale(distance));
        }
        return this;
    }


    /**
     * move the camera near or away (from the objects)
     * @param distance how many the camera goes forward (can be minus and go backward)
     * @return
     */
    public Camera moveNearAway(double distance){
        if(!isZero(distance)) {
            position = position.add(vTo.scale(distance));
        }
        return this;
    }

    /**
     * Turn on Depth Of Field
     * @param apertureRadius - set the radius of the aperture.
     * @param focalDistance
     * @param focalRaysAmount
     * @return
     */
    public void setDepthOfFieldOn(double apertureRadius, double focalDistance, double focalRaysAmount) {
        isDepthOfFieldOn = true;
        this.apertureRadius = apertureRadius;
        this.focalDistance = focalDistance;
        this.focalRaysAmount = focalRaysAmount;
    }


    private void depthOfField(Ray ray){
        Point focalPoint = ray.getPoint(focalDistance);
        double randomRadius,randomAngle;
        Point pointOnFocalPlane;
        Vector rotatedVector;
        for(int i = 0; i < focalRaysAmount; i++){
            randomRadius = random(0,focalDistance);
            randomAngle = random(0,360);
            rotatedVector = vUp.rotate(vRight,randomAngle);
            pointOnFocalPlane = focalPoint.add(rotatedVector);

        }
    }

    private Point getCenterPixel(int nX, int nY, int j, int i){
        Point pIJCenter = position.add(vTo.scale(distanceCameraToViewPlane)); // = Pc
        double rY = this.height / nY; //rY is the size of the vertical rib of the pixel (without the horizontal rib)
        double rX = this.width / nX; //rX is the size of the horizontal rib of the pixel (without teh vertical rib)
        double xJ = (j - (double)(nX - 1)/2) * rX; //xJ is the horizontal distance of our pixel from the central pixel (in pixels)
        double yI = -(i - (double)(nY - 1)/2) * rY; //yI is the vertical distance of our pixel from the central pixel (in pixels)
        if (xJ != 0) pIJCenter = pIJCenter.add(vRight.scale(xJ));
        if (yI != 0) pIJCenter = pIJCenter.add(vUp.scale(yI));
        return pIJCenter;
    }
}
