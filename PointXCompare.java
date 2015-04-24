import java.awt.Point;
import java.util.Comparator;

/**
 *
 * @author romis_000
 */
class PointXCompare
    implements Comparator<Point> {

    @Override
    public int compare(final Point a, final Point b) {
        if (a.x == b.x)
            return a.y - b.y;
        else
            return a.x - b.x;
    }
}