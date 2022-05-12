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
	private Scene scene = new Scene("Test scene").setBackground(new Color(120,140,255));

	@Test
	void PR07Test() {
			//Camera camera = new Camera(new Point(-2500, -2500, 800), new Vector(1, 1, 0), new Vector(0, 0, 1)) //
			//	.setVPSize(2500, 2500).setVPDistance(3000); //
		//first option: we see the bird from the back:
		  Camera camera = new Camera(new Point(35,50,0), new Vector(-1,-1,0), new Vector(0,0,1)) //
				.setVPSize(60,60).setVPDistance(40);
		/* //more option, need to fix, we see the bird from the front
		Camera camera = new Camera(new Point(-50,-50,0), new Vector(1,1,0), new Vector(0,0,1)) //
				.setVPSize(60,60).setVPDistance(40);*/

		scene.setAmbientLight(new AmbientLight(new Color(20, 150, 240), new Double3(0.1)));//color of sky, less or more

		scene.geometries.add(
				//body of the bird
				new Sphere(new Point(2,2,-3), 14)
						.setEmission(new Color(230,120,20)).setMaterial(new Material().setKt(0.2).setShininess(30)),
				//head of the bird
				new Sphere(new Point(-14,0,2),10)
						.setEmission(new Color(230,120,20)).setMaterial(new Material().setKt(0.05)),
				//eyes of the bird
				new Sphere(new Point(-18,-9,4.5), 1)
						.setEmission(new Color(0,0,0)),
				new Sphere(new Point(-19, -7, 4.5),2)
						.setEmission(new Color(255,255,220)).setMaterial(new Material().setKt(1)),
				new Sphere(new Point(-18,9,4.5),1)
						.setEmission(new Color(0,0,0)),
				new Sphere(new Point(-19,7,4.5),2)
						.setEmission(new Color(255,255,220)).setMaterial(new Material().setKt(1)),
				//beak of the bird (pyramid, other did not succeed to me)

				//new Triangle(new Point(-22,0,0), new Point(-19,0,-6), new Point(-35,0,-12))
				//		.setEmission(new Color(130,50,10)),
				new Triangle(new Point(-20,-4,0), new Point(-17,-2,-5.5), new Point(-35,0,-12))
						.setEmission(new Color(130,50,10)),
				new Triangle(new Point(-20,4,0), new Point(-17,2,-5.5), new Point(-35,0,-12))
						.setEmission(new Color(130,50,10)),
				new Triangle(new Point(-20,-4,0), new Point(-20,4,0), new Point(-35,0,-12))
						.setEmission(new Color(130,50,10)),
				new Triangle(new Point(-17,-2,-5.5), new Point(-17,2,-5.5), new Point(-35,0,-12))
						.setEmission(new Color(130,50,10)),
				//legs of the bird
				new Triangle(new Point(5,-7,-13), new Point(6,-6,-16), new Point(-8,-13,-27))
						.setEmission(new Color(100,40,10)),
				new Triangle(new Point(5,9,-13), new Point(6,8,-16), new Point(-8,13,-27))
						.setEmission(new Color(100,40,10)),
				//wings of the bird
				new Triangle(new Point(-7,-8,5), new Point(3.5,-9,6), new Point(18,-34,-5))
						.setEmission(new Color(255,200,30)),
				new Triangle(new Point(-5,12,5),new Point(3.5,13,6),new Point(18,30,-5))
						.setEmission(new Color(255,200,30)),
				//tail of the bird
				new Polygon(new Point(12,-2,0), new Point(12,2,0), new Point(20,6,-4), new Point(20,-2,-4))
						.setEmission(new Color(255,200,30)),
				//the land (replace to tube, like a branch?) the water source, and the shore
				new Plane(new Point(-8,-13,-25), new Point(-8,13,-25), new Point(1,1,-25))
						.setEmission(new Color(140,50,40)),
				new Plane(new Point(-15,10,-25), new Point(-15,-10,-25), new Point(-35,0,-12))
						.setEmission(new Color(0, 20, 130)).setMaterial(new Material().setKr(0.5)),
				new Tube(new Ray(new Point(-15,10,-25), new Vector(0,-250,0)), 2)
						.setEmission(new Color(77,0,0)));

				/*new Sphere(new Point(0,0,100),100).setEmission(new Color(BLUE)),
				new Polygon(new Point(500,-500,0), new Point(-500,-500,0),new Point(-500,500,0),new Point(500,500,0))
						.setEmission(new Color(GRAY)).setMaterial(new Material().setKr(0.9)),
				new Triangle(new Point(1500,0,250),new Point(0,1500,250),new Point(1500,1500,1000))
						.setEmission(new Color(WHITE)),
				new Sphere(new Point(300,300,300),100).setEmission(new Color(RED))
						.setMaterial(new Material().setKt(0.7)),
				//new Sphere(new Point(300,0,300),50).setEmission(new Color(RED)),
				new Triangle(new Point(1500,0,250), new Point(500,500,0), new Point(500,-500,0))
						.setEmission(new Color(LIGHT_GRAY)).setMaterial(new Material().setKr(1)),
				new Triangle(new Point(0,1500,250),new Point(500,500,0), new Point(-500,500,0))
						.setEmission(new Color(LIGHT_GRAY)).setMaterial(new Material().setKr(1)));*/

		/*scene.lights.add(new SpotLight(new Color(0, 400, 400), new Point(-500, -500, 2000), new Vector(-1,-1,-1)) //
				.setKl(0.00001).setKq(0.000005));
		scene.lights.add(new SpotLight(new Color(0, 400, 400), new Point(-500, -500, -100), new Vector(1,1,10)) //
				.setKl(0.00001).setKq(0.000005));
		scene.lights.add(new PointLight(new Color(50,150,150), new Point(-500,-500,-500)).setKl(0.000005)); //*/
		scene.lights.add(new PointLight(new Color(800,500,250), new Point(-19,9,4.5)).setKq(0.001));
		scene.lights.add(new PointLight(new Color(800,500,250), new Point(-19,-9,4.5)).setKq(0.001));
		scene.lights.add(new SpotLight(new Color(800,500,250), new Point(-20,20,30), new Vector(22,-18,-35)).setKl(0.001).setKq(0.0001));
		scene.lights.add(new DirectionalLight(new Color(200, 200, 0), new Vector(22, -18, -35)));
		ImageWriter imageWriter = new ImageWriter("PR07", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}


}
