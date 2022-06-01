package renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */

public class GlossyBlurrySuperSampling {
	private Scene scene = new Scene("Test scene");
	@Test
	void BirdWithReflectionSuperSampling(){
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //
		double g = 1;
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

		scene.geometries.add( //
				new Polygon(new Point(1000,0,0),new Point(1000,1000,0), new Point(0,1000,0), new Point(0,0,0))
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.3).setBlurriness(20)),
				new Sphere(new Point(200, 200, -1000), 400d).setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)));


		scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, 0, 300)) //
				.setKl(0.00000001).setKq(0.000000000005));

		ImageWriter imageWriter = new ImageWriter("BirdWithReflectionSuperSampling", 500, 500);
		camera.setImageWriter(imageWriter)
				.setAntiAliasingOn(15)//
				.setRayTracer(new RayTracerBasic(scene).setRefractionSuperSamplingOn(15)) //
				.renderImage() //
				.writeToImage();
	}


}
