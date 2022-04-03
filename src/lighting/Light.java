package lighting;

import primitives.Color;

/**
 * This class represent a basic light.
 */
public abstract class Light {
    /**
     * the intensity of the light source
     */
    protected Color intensity;

    /**
     * Constructor for constructing the light source
     * @param intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Getter method for intensity field.
     * @return intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
