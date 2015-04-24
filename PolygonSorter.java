import java.awt.Point;
import java.util.Comparator;

/**
 *
 * @author Johan
 */
public abstract class PolygonSorter implements Comparator<Point> {

    Point center;

    public PolygonSorter(int[][] points) {
        this.center = calcCenter(points);
    }

    final Point calcCenter(int[][] points) {
        int sumx = 0;
        int sumy = 0;
        for (int[] point : points) {
            sumx += point[0];
            sumy += point[1];
        }
        return new Point(sumx / points.length, sumy / points.length);
    }

    @Override
    public abstract int compare(Point a, Point b);
}
