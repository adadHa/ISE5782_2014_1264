package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight extends Light{

    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }

    public  AmbientLight(){
        super(Color.BLACK);
    }
}
