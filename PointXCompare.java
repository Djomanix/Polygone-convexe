import java.util.Comparator;

/**
 *
 * @author romis_000
 */
class PointXCompare
    implements Comparator<PointDouble> {

    @Override
    public int compare(final PointDouble a, final PointDouble b) {
        if (a.x == b.x)
            return (int)(a.y - b.y);
        else
            return (int)(a.x - b.x);
    }
}