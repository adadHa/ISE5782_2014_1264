package lighting;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
import static java.awt.Color.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */

public class LightsTests {
	private Scene scene1 = new Scene("Test scene");
	private Scene scene2 = new Scene("Test scene") //
			.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
	private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(150, 150) //
			.setVPDistance(1000);
	private Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(200, 200) //
			.setVPDistance(1000);

	private Point[] p = { // The Triangles' vertices:
			new Point(-110, -110, -150), // the shared left-bottom
			new Point(80, 100, -150), // the shared right-top
			new Point(110, -110, -150), // the right-bottom
			new Point(-75, 85, 0) }; // the left-top
	private Point trPL = new Point(50, 30, -100); // Triangles test Position of Light
	private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
	private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
	private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
	private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
	private Material material = new Material().setKd(0.5).setKs(0.5).setShininess(300);
	private Geometry triangle1 = new Triangle(p[0], p[1], p[2]).setMaterial(material);
	private Geometry triangle2 = new Triangle(p[0], p[1], p[3]).setMaterial(material);
	private Geometry sphere = new Sphere(new Point(0, 0, -50), 50d) //
			.setEmission(new Color(BLUE).reduce(2)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
	private Geometry tube1 = new Tube(new Ray(new Point(-20,1,0), new Vector(2,1,1)), 20d);
	private Geometry tube2 = new Tube(new Ray(new Point(40,5,-5), new Vector(-2,12,-5)), 20d);
	//private Geometry tube3 = new Tube(new Ray(new Point(11,22,-3), new Vector(12,13,14)), 20d);
	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	@Test
	public void sphereDirectional() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));

		ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by a point light
	 */
	@Test
	public void spherePoint() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new PointLight(spCL, spPL).setKl(0.001).setKq(0.0002));

		ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by a spotlight
	 */
	@Test
	public void sphereSpot() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setKl(0.001).setKq(0.0001));

		ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a two triangles lighted by a directional light
	 */
	@Test
	public void trianglesDirectional() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new DirectionalLight(trCL, trDL));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a two triangles lighted by a point light
	 */
	@Test
	public void trianglesPoint() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new PointLight(trCL, trPL).setKl(0.001).setKq(0.0002));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of two triangles lighted by a spotlight
	 */
	@Test
	public void trianglesSpot() {
		scene2.geometries.add(triangle1.setEmission(new Color(GREEN)), triangle2.setEmission(new Color(RED)),new Sphere(new Point(0,0,1100),50));
		// the new sphere was added inorder to create the problem of
		// "shading by a body behind the camera", the Exercise claims that the problem
		// is achieved also without this addition but I think that it doesn't.
		scene2.lights.add(new SpotLight(trCL, trPL, trDL).setKl(0.001).setKq(0.0001));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a tube lighted by a spotlight and pointLight
	 */
	@Test
	public void tubeSpot() {
		Material material = new Material().setKd(0.7).setKs(0.3);
		scene2.geometries.add(tube1.setEmission(new Color(CYAN)).setMaterial(material));
		scene2.geometries.add(tube2.setEmission(new Color(GREEN)).setMaterial(material));
		scene2.geometries.add(new Tube(new Ray(new Point(50,0,0), new Vector(2,2,-5)), 15) .setEmission(new Color(BLUE)).setMaterial(material.setKd(0.3).setKs(0.7)));


		scene2.lights.add(new PointLight(new Color(125,0,125),new Point(20,100,100)));
		ImageWriter imageWriter = new ImageWriter("lightTubeSpot", 500, 500);
		camera2.setVPDistance(600).setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}



	@Test
	public void specularEffect() {
		Scene scene = new Scene("scene").setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
		scene.geometries.add(new Sphere(new Point(0,0,0), 5).setEmission(spCL).setMaterial(material));
		scene.lights.add(new PointLight(spCL,new Point(10,0,0)).setKl(0.001).setKq(0.0001));
		Camera camera = new Camera(new Point(300,0,0),new Vector(-1,0,0),new Vector(0,0,1));
		camera.setVPSize(3,3).setVPDistance(295).setRayTracer(new RayTracerBasic(scene))
				.setImageWriter(new ImageWriter("specuralEffect",3,3))
				.renderImage()
				.writeToImage();

	}

	@Test
	public void triangleMultiplyLightSources(){
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new SpotLight(trCL, trPL, trDL).setKl(0.001).setKq(0.0001));
		scene2.lights.add(new PointLight(new Color(0,255,255),new Point(50,60,-100)));
		scene2.lights.add(new DirectionalLight(new Color(0,123,0),new Vector(-10,1,2)));
		scene2.lights.add(new PointLight(new Color(125,0,125),new Point(50,30,1000)));

		ImageWriter imageWriter = new ImageWriter("triangleMultiplyLightSources", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	@Test
	public void sphereMultiplyLightSources(){
		scene1.geometries.add(sphere);
		scene1.lights.add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setKl(0.001).setKq(0.0001));
		scene1.lights.add(new PointLight(new Color(0,120,120), new Point(-50,-50, 100)));
		scene1.lights.add(new DirectionalLight(new Color(123,255,0), new Vector(-50,-50, -50)));

		ImageWriter imageWriter = new ImageWriter("sphereMultiplyLightSources", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}



}
