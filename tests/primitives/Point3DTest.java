package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    @Test
    void distance() {

        Point3D a = new Point3D(1,2,3);
        Point3D b = new Point3D(1,2,3);

        double distance = a.distance(b);

        assertEquals(distance, 0, "wrong distance");

        Point3D c = new Point3D(4,5,6);
        distance = a.distance(c);

        assertEquals(distance, 5.196152422706632, "wrong distance");
    }
}