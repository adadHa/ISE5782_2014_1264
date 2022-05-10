/**
 *
 */
package lighting;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class PR07Test {
	private Scene scene = new Scene("Test scene");

	@Test
	void PR07Test() {
		Camera camera = new Camera(new Point(-2500, -2500, 800), new Vector(1, 1, 0), new Vector(0, 0, 1)) //
				.setVPSize(2500, 2500).setVPDistance(3000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

		scene.geometries.add(
				new Sphere(new Point(0,0,100),100).setEmission(new Color(BLUE)),
				new Polygon(new Point(500,-500,0), new Point(-500,-500,0),new Point(-500,500,0),new Point(500,500,0))
						.setEmission(new Color(GRAY)).setMaterial(new Material().setKr(0.9)),
				new Triangle(new Point(1500,0,250),new Point(0,1500,250),new Point(1500,1500,1000))
						.setEmission(new Color(WHITE)),
				new Sphere(new Point(300,300,300),100).setEmission(new Color(RED))
						.setMaterial(new Material().setKt(0.7)),
				new Sphere(new Point(300,0,300),50).setEmission(new Color(RED)));


		scene.lights.add(new SpotLight(new Color(0, 400, 400), new Point(-500, -500, 2000), new Vector(-1,-1,-1)) //
				.setKl(0.00001).setKq(0.000005));
		scene.lights.add(new SpotLight(new Color(0, 400, 400), new Point(-500, -500, -100), new Vector(1,1,10)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("PR07", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}


}
