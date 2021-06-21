package primitives;

/**
 * this class represents the different materials of the surfaces
 * and the reflection of a light component on it,
 * in three known values: diffusion, specular, and shininess.
 */
public class Material {

    /**
     *  Kd - diffuse component, represents the scattering of light rays to all directions from the surface
     */
    public double Kd = 0.0;
    /**
     *  Ks - specular component, represents the reflectance of the light source over the surface
     */
    public double Ks = 0.0;
    /**
     *  Shininess - how shiny the material is
     */
    public int Shininess = 0;
    /**
     *  Kt - transparency component
     * 0.0 is opaque (=atum)
     * 1.0 is clear(=shakuf)
     */
    public double Kt = 0.0;
    /**
     *  Kr - reflection component
     * 0.0 is matte
     * 1.0 is very reflexive
     */
    public double Kr = 0.0;

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
