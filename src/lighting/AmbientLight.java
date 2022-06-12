package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * this class represents an environmental light, that is everywhere in the scene in the same intensity
 */
public class AmbientLight extends Light{

    /**
     * constructor for AmbientLight, gets the color of the light, and a constant of the intensity
     * @param iA color of the ambientLight
     * @param kA intensity of the color ( scaled with the color, it same everywhere in the scene)
     */
    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }

    /**
     * default constructor for ambientLight, the color of the light is black
     */
    public  AmbientLight(){
        super(Color.BLACK);
    }
}
