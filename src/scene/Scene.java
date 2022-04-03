package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight(Color.BLACK, new Double3(1,1,1)); //Double3 constractor should be public or stay protected?
    public Geometries geometries = new Geometries();
    public List<LightSource> lights = new LinkedList<LightSource>();

    public Scene(String name) {
        this.name = name;
    }

    /**
     * A setter method for name field.
     * The method returns the scene as it following the NVI design pattern
     * @param name
     * @return
     */
    public Scene setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * A setter method for background field.
     * The method returns the scene as it following the NVI design pattern
     * @param background
     * @return
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * A setter method for ambientLight field.
     * The method returns the scene as it following the NVI design pattern
     * @param ambientLight
     * @return
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * A setter method for geometries field.
     * The method returns the scene as it following the NVI design pattern
     * @param geometries
     * @return
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * A setter method for lights field.
     * The method returns the scene as it following the NVI design pattern
     * @param lights
     * @return
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
