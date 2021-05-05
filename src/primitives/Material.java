package primitives;

/**
 * this class represents the different materials of the surfaces
 */
public class Material {

    /**
     * @member Kd - diffuse component
     * @member Ks - specular component
     * @member Shininess - how shiny the material is
     */
    public double Kd = 0;
    public double Ks = 0;
    public int Shininess = 0;

    /**
     * setter - chaining method
     *
     * @param Kd
     * @return
     */
    public Material setKd(double Kd) {
        this.Kd = Kd;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param Ks
     * @return
     */
    public Material setKs(double Ks) {
        this.Ks = Ks;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param shininess
     * @return
     */
    public Material setShininess(int shininess) {
        this.Shininess = shininess;
        return this;
    }
}
