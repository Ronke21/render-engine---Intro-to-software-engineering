package primitives;

/**
 * this class represents the different materials of the surfaces
 */
public class Material {

    /**
     * @member Kd - diffuse component
     * @member Ks - specular component
     * @member Shininess - how shiny the material is
     * @member Kt - transparency component
     * @member Kr - reflection component
     */
    public double Kd = 0.0;
    public double Ks = 0.0;
    public int Shininess = 0;
    public double Kt = 0.0; //opaque
    public double Kr = 0.0; //matte

    /**
     * setter - chaining method
     *
     * @param Kd - diffuse component
     * @return the material after setting the diffuse component
     */
    public Material setKd(double Kd) {
        this.Kd = Kd;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param Ks - specular component
     * @return - the material after setting the
     */
    public Material setKs(double Ks) {
        this.Ks = Ks;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param shininess - how shiny the material is
     * @return the material after setting the
     */
    public Material setShininess(int shininess) {
        this.Shininess = shininess;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param kt - transparency component
     * @return the material after setting the transparency component
     */
    public Material setKt(double kt) {
        this.Kt = kt;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param kr -  reflection component
     * @return the material after setting the reflection component
     */
    public Material setKr(double kr) {
        this.Kr = kr;
        return this;
    }
}
