package PerformanceImprovments;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

public class FinalImageMT {
    private final Scene scene = new Scene("Final Project Scene").setBackground(new Color(120,140,255));

    @Test
    void FinalProjectMT() {

            //We see the bird from the front
            Camera camera = new Camera(new Point(-50,-50,0), new Vector(1,1,0), new Vector(0,0,1)) //
                    .setVPSize(60,60).setVPDistance(40);
        Material mateBirdSkin = new Material().setKt(0.2).setShininess(30).setKs(0.1).setKd(0.7);
        Material mateBirdOrgans = new Material().setKd(0.7).setShininess(10);
        Material mateBirdBeak = new Material().setKr(0.001).setKs(0.2).setKd(0.5);
        Material mateEye = new Material().setKt(1).setKs(0.5).setKd(0.01);
        Material mateCloud = new Material().setKt(0.8);//todo add bluriness
        scene.setAmbientLight(new AmbientLight(new Color(20, 150, 240), new Double3(0.1)));//color of sky, less or more

        scene.geometries.add(
                //body of the bird
                new Sphere(new Point(2,2,-3), 14)
                        .setEmission(new Color(230,120,20)).setMaterial(mateBirdSkin),
                //head of the bird
                new Sphere(new Point(-14,0,2),10)
                        .setEmission(new Color(230,120,20)).setMaterial(mateBirdSkin.setKt(0.005)),
                //eyes of the bird
                new Sphere(new Point(-19,-9,4.5), 1)
                        .setEmission(new Color(0,0,0)),
                new Sphere(new Point(-19, -7, 4.5),2)
                        .setEmission(new Color(255,255,220)).setMaterial(mateEye),
                new Sphere(new Point(-18,9,4.5),1)
                        .setEmission(new Color(0,0,0)),
                new Sphere(new Point(-19,7,4.5),2)
                        .setEmission(new Color(255,255,220)).setMaterial(mateEye),
                //beak of the bird
                new Triangle(new Point(-20,-4,0), new Point(-17,-2,-5.5), new Point(-35,0,-12))
                        .setEmission(new Color(130,50,10)).setMaterial(mateBirdBeak),
                new Triangle(new Point(-20,4,0), new Point(-17,2,-5.5), new Point(-35,0,-12))
                        .setEmission(new Color(130,50,10)).setMaterial(mateBirdBeak),
                new Triangle(new Point(-20,-4,0), new Point(-20,4,0), new Point(-35,0,-12))
                        .setEmission(new Color(130,50,10)).setMaterial(mateBirdBeak),
                new Triangle(new Point(-17,-2,-5.5), new Point(-17,2,-5.5), new Point(-32,0,-13))
                        .setEmission(new Color(130,50,10)).setMaterial(mateBirdBeak),
                //legs of the bird
                new Triangle(new Point(5,-7,-13), new Point(6,-6,-16), new Point(-8,-13,-27))
                        .setEmission(new Color(100,40,10)).setMaterial(mateBirdOrgans),
                new Triangle(new Point(5,9,-13), new Point(6,8,-16), new Point(-8,13,-27))
                        .setEmission(new Color(100,40,10)).setMaterial(mateBirdOrgans),
                //wings of the bird
                new Triangle(new Point(-7,-8,5), new Point(3.5,-9,6), new Point(18,-34,-5))
                        .setEmission(new Color(255,200,30)).setMaterial(mateBirdOrgans),
                new Triangle(new Point(-5,12,5),new Point(3.5,13,6),new Point(18,30,-5))
                        .setEmission(new Color(255,200,30)).setMaterial(mateBirdOrgans),
                //tail of the bird
                new Polygon(new Point(12,-2,0), new Point(12,2,0), new Point(20,6,-4), new Point(20,-2,-4))
                        .setEmission(new Color(255,200,30)).setMaterial(mateBirdOrgans),
                //the land (replace to tube, like a branch?) the water source, and the shore
                new Plane(new Point(-8,-13,-25), new Point(-8,13,-25), new Point(1,1,-25))
                        .setEmission(new Color(9,67,19)).setMaterial(new Material().setKd(0.6).setShininess(10)),
                new Plane(new Point(-15,10,-25), new Point(-15,-10,-25), new Point(-35,0,-12))//todo add glossiness
                        .setEmission(new Color(0, 20, 255)).setMaterial(new Material().setKr(0.5).setGlossiness(15)),//.setKd(0.7).setKs(0.4).setShininess(20)),
                new Tube(new Ray(new Point(-15,10,-25), new Vector(0,-250,0)), 2)
                        .setEmission(new Color(3,22,6)).setMaterial(new Material().setKd(0.6).setShininess(10)),
                new Sphere(new Point(-35,0,-11), 2)
                        .setEmission(new Color(0,0,100)).setMaterial(new Material().setKt(0.5).setKs(0.5)),
                new Cylinder(new Ray(new Point(2,-7,-13), new Vector(0,0,1)), 5, 10).setMaterial(new Material().setKr(0.8).setKd(0.7).setKs(0.5)),
                
                
                //clouds and a bird behind them
                new Sphere(new Point(100,80,80), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(120,80,85), 10).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(110,75,80), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(100,75,75), 8).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(105,80,80), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(110,80,75), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(130,80,90), 10).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(125,75,75), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(138,75,80), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(140,80,85), 8).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(150,100,80), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(145,110,85), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(135,95,80), 10).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(120,90,85), 8).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(140,105,75), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(130,85,80), 12).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(120,90,80), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(155,100,85), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(130,90,80), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(125,85,75), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(145,110,80), 8).setEmission(new Color(204,255,255)).setMaterial(mateCloud),
                new Sphere(new Point(128,90,85), 5).setEmission(new Color(204,255,255)).setMaterial(mateCloud)
        );

        scene.lights.add(new PointLight(new Color(255,255,255), new Point(0,0,10)).setKl(0.00001).setKq(0.0001));
        scene.lights.add(new SpotLight(new Color(800,500,250), new Point(-80,20,20), new Vector(82,-18,-25)).setKl(0.1).setKq(0.0001));
        scene.lights.add(new DirectionalLight(new Color(100, 150, 150), new Vector(22, -18, -35)));


        ImageWriter imageWriter = new ImageWriter("Final Image - MultiThreading **", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene).setReflectionSuperSamplingOn(15)) //
                .setAdaptiveSuperSamplingOn(4)
                .setMultithreadingOn()//
                .renderImage()
                .writeToImage();
    }


}
