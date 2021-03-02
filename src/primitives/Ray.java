package primitives;

import java.util.Objects;

/**
 * This class represents a ray in the space = a vector that doesn't start in (0,0,0).
 * has a starting point p0 and a vector from it to the edge of ray.
 */
public class Ray {
    Point3D p0;
    Vector dir;

    /**
     * constructor for ray. receives starting point and vector and sets them. sets the vector normalized
     * @param p0 - starting point
     * @param dir - direction vector
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalized();
    }

    /**
     * getter
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * getter
     */
    public Vector getDir() {
        return dir;
    }

    @Override
    public String toString() {
        return "Ray : (" + p0 + ") ,(" + dir._head + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

}
