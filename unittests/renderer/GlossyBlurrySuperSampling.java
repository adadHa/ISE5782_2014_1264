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
	/**
	 * test of checking refraction super sampling with sphere and polygon
	 */
	@Test
	void BirdWithReflectionSuperSampling(){
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000).moveRightLeft(100); //
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

	/**
	 * test of checking reflection super sampling with pyramid and polygon
	 */
	@Test
	void glossyReflectionSuperSampling() {
		Camera camera = new Camera(new Point(0, 1600, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000).moveNearAway(300).moveRightLeft(-100).spinAroundVUp(0).spinAroundVRight(-8); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

		scene.geometries.add( //
				new Polygon(new Point(1000, -700, -4000), new Point(1000, 1000, -4000), new Point(-700, 1000, -4000), new Point(-700, -700, -4000))
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKr(0.9).setGlossiness(15)),
				new Triangle(new Point(-930,-630,-500), new Point(500,-500,250), new Point(320,280,-1000)).setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(-930,-630,-500), new Point(500,-500,250), new Point(600,-1300,-1000)).setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(-930,-630,-500), new Point(600,-1300,-1000), new Point(320,280,-1000)).setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(500,-500,250), new Point(600,-1300,-1000), new Point(320,280,-1000)).setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)));
		//new Sphere(new Point(200, 200, -1000), 400d).setEmission(new Color(0, 0, 100)) //
		//		.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)));


		scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, 1000, 300)) //
				.setKl(0.00000001).setKq(0.000000000005));

		ImageWriter imageWriter = new ImageWriter("BirdGlossyReflectionSuperSampling", 500, 500);
		camera.setImageWriter(imageWriter)
				.setAntiAliasingOn(15)//
				.setRayTracer(new RayTracerBasic(scene).setReflectionSuperSamplingOn(15)) //
				.renderImage() //
				.writeToImage();
	}



	/**
	 * test of checking refraction super sampling with pyramid and polygon
	 */
	@Test
	void blurryRefractionSuperSampling(){
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

		scene.geometries.add( //
				new Polygon(new Point(800, -400, 0), new Point(800, 800, 0), new Point(-400, 800, 0), new Point(-400, -400, 0))
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.3).setBlurriness(20)),
				new Triangle(new Point(-930,-630,-1500), new Point(500,-500,-750), new Point(320,280,-2000)).setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(-930,-630,-1500), new Point(500,-500,-750), new Point(600,-1300,-2000)).setEmission(new Color(0, 0, 100)) //
					.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(-930,-630,-1500), new Point(600,-1300,-2000), new Point(320,280,-2000)).setEmission(new Color(0, 0, 100)) //
					.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(500,-500,-750), new Point(600,-1300,-2000), new Point(320,280,-2000)).setEmission(new Color(0, 0, 100)) //
					.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)));
				//new Sphere(new Point(200, 200, -1000), 400d).setEmission(new Color(0, 0, 100)) //
				//		.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)));


		scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, 0, 300)) //
				.setKl(0.00000001).setKq(0.000000000005));

		ImageWriter imageWriter = new ImageWriter("BirdBlurryRefractionSuperSampling", 500, 500);
		camera.setImageWriter(imageWriter)
				.setAntiAliasingOn(15)//
				.setRayTracer(new RayTracerBasic(scene).setRefractionSuperSamplingOn(15)) //
				.renderImage() //
				.writeToImage();
	}


	/**
	 * test of a small illustration
	 */
	@Test
	void PalmTest(){
		Scene scene= new Scene("TestScene").setBackground(new Color(0,102,255));
		Camera camera=new Camera(new Point(0,-80,0),
				new Vector(0,1,0),
				new Vector(0,0,1))
				.setVPDistance(40).setVPSize(50,50)
				/*.moveRightLeft(80)
				.moveNearAway(100)
				.moveUpDown(90)
				.spinAroundVUp(90)
				.spinAroundVRight(-40);*/
		//.moveNearAway(150)
		.spinAroundVUp(180);
		//the body
		scene.geometries.add(new Sphere(new Point(0,10,0), 20)
						.setEmission(new Color(255,51,0))
						.setMaterial(new Material().setShininess(10).setKd(0.5).setKs(0.5)),
				//first eye
				new Sphere(new Point(-11,-2,10), 2)
						.setEmission(new Color(0,0,0)),
                        /*new Sphere(new Point(-11,-12,10), 5)
                                .setEmission(new Color(255,255,255))
                                .setMaterial(new Material().setKt(1).setKs(0.5)),*/
				//second eye
				new Sphere(new Point(-15,13,12), 2)
						.setEmission(new Color(0,0,0)),
                        /*new Sphere(new Point(-15,3,12), 5)
                                .setEmission(new Color(255,255,255))
                                .setMaterial(new Material().setKt(1).setKs(0.5)),*/
				new Triangle(new Point(19,0,0),new Point(28,0,-10), new Point(26,0,0))
						.setEmission(new Color(204,51,0))
						.setMaterial(new Material().setKs(1).setKd(1)),
				new Triangle(new Point(19,0,0),new Point(28,0,10), new Point(26,0,0))
						.setEmission(new Color(204,51,0))
						.setMaterial(new Material().setKs(1).setKd(1)),
				new Plane(new Point(1,2,-40), new Point(-2,3,-40), new Point(3,-4,-40))
						.setEmission(new Color(102,204,255))
						.setMaterial(new Material().setKr(0.8).setKd(0.7).setShininess(30)),
				//mirrow
				new Triangle(new Point(-30,25,-20), new Point(-30,-25,-20), new Point(-45,0,30)).
						setMaterial(new Material().setKr(0.5).setKd(0.5).setKs(0.3)),
				new Tube(new Ray(new Point(-30,25,-20), new Vector(1,1,0)), 1.5)
						.setEmission(new Color(102,255,102)).setMaterial(new Material().setKt(0.5).setKd(0.2).setKs(0.6)),
				new Tube(new Ray(new Point(-30,-25,-20), new Vector(1,1,0)), 1.5)
						.setEmission(new Color(255,255,0)).setMaterial(new Material().setKt(0.5).setKd(0.2).setKs(0.6)),
				new Tube(new Ray(new Point(-45,0,30), new Vector(1,1,0)), 1.5)
						.setEmission(new Color(204,0,204)).setMaterial(new Material().setKt(0.5).setKd(0.2).setKs(0.6)),
				new Tube(new Ray(new Point(-50,-15,-10), new Vector(1,1,0)), 1.5)
						.setEmission(new Color(255,108,255)).setMaterial(new Material().setKt(0.5).setKd(0.2).setKs(0.6)),
				new Tube(new Ray(new Point(-5,10,30), new Vector(1,1,0)), 1.5)
						.setEmission(new Color(255,0,0)).setMaterial(new Material().setKt(0.5).setKd(0.2).setKs(0.6)),
				new Sphere(new Point(20,-5,1),10).setMaterial(new Material().setKt(0.5).setKd(0.2).setKs(0.6)));




		scene.lights.add(new PointLight(new Color(255,255,255), new Point(-30,0,30)).setKq(0.0001).setKl(0.0001));
		scene.lights.add(new DirectionalLight(new Color(255,255,255), new Vector(1,1,0)));


		ImageWriter imageWriter = new ImageWriter("Palm Test4", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();


	}
}
