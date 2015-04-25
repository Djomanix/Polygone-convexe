import java.util.Comparator;

/**
 *
 * @author Johan
 */
public abstract class PolygonSorter implements Comparator<PointDouble> {

    PointDouble center;

    public PolygonSorter(double[][] podoubles) {
        this.center = calcCenter(podoubles);
    }

    final PointDouble calcCenter(double[][] podoubles) {
        double sumx = 0;
        double sumy = 0;
        for (double[] podouble : podoubles) {
            sumx += podouble[0];
            sumy += podouble[1];
        }
        return new PointDouble(sumx / podoubles.length, sumy / podoubles.length);
    }

    @Override
    public abstract int compare(PointDouble a, PointDouble b);
}
