import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 *
 * @author Johan
 */
public class Benchmarks {

    static Date date = new Date();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
    static File file = new File("Benchmarks_" + dateFormat.format(date) + ".txt");
    static BufferedWriter out;

    /**
     * Runs the benchmarks for the most important operations
     */
    public static void main(String[] args) throws IOException {
        out = new BufferedWriter(new FileWriter(file, true));
        out.write("--- Benchmarks pour la structure de données ConvexPolyogon ---");
        out.newLine();
        out.close();

        addVertexBench();
        //sortVerticesBench();
        //intersectsBench(50, 50000);
        //convexHullBench();
        //isConvexBench();
        //containsBench();
    }

    /**
     * Run addVertex operation series of benchs
     */
    public static void addVertexBench() throws IOException {
        out = new BufferedWriter(new FileWriter(file, true));
        out.newLine();
        out.write("-> Opération addVertex:");
        out.newLine();
        out.close();
        testAddVertex(10);
        testAddVertex(100);
        testAddVertex(1000);
        testAddVertex(10000);
        testAddVertex(20000);
        //testAddVertex(50000);
    }

    /*public static void sortVerticesBench() {
     //testSortVertices(1000);
     testSortVertices(10000);
     //testSortVertices(20000);
     //testSortVertices(25000);
     //testSortVertices(100000);
     //testAddVertex(1000000);
     }

     public static void convexHullBench() {
     testConvexHull(1000);
     testConvexHull(10000);
     testConvexHull(100000);
     testConvexHull(1000000);
     }

     public static void isConvexBench() {
     testIsConvex(1000);
     testIsConvex(10000);
     testIsConvex(100000);
     testIsConvex(1000000);
     testIsConvex(10000000);
     }

     public static void containsBench() {
     testContain(1000);
     testContain(10000);
     testContain(100000);
     }

     public static void intersectsBench(int n, int nb) {
     for (int i = 0; i < n; ++i) {
     testIntersect(nb);
     }
     }*/
    /**
     * addVertex bench: tries to add the given number of vertices to a polygon
     */
    public static void testAddVertex(int nb) throws IOException {
        float totalTime = 0;
        ArrayList<PointDouble> points = new ArrayList<>();
        ArrayList<PointDouble> randomPoints = new ArrayList<>();
        randomPoints = generatePoints(nb + 3);
        Collections.shuffle(randomPoints);

        for (int i = 0; i <= 2; ++i) {
            points.add(randomPoints.remove(0));
        }
        ConvexPolygon test = new ConvexPolygon(points);

        int count = 1;
        for (int i = 0; i < nb; ++i) {
            long startTime = System.nanoTime();
            test.addVertex(randomPoints.remove(0));
            long endTime = System.nanoTime();
            totalTime += ((endTime - startTime));
            //System.out.println("addVertex: " + count + "th added in " + (endTime - startTime) + "ns -> number of vertices in polygon: " + test.getVertices().length + " -> total time: " + totalTime / 1000000 + "ms");
            ++count;
        }
        System.out.println("addVertex: " + (count - 1) + " vertices added in " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out = new BufferedWriter(new FileWriter(file, true));
        out.write("addVertex: On a ajouté " + (count - 1) + " sommets à un polygone en " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out.newLine();
        out.close();
    }

    /*public static void testSortVertices(int nb) {
     ArrayList<Point> pts = new ArrayList<>();
     Random rn = new Random();
     for (int i = 0; i < nb; ++i) {
     int x = rn.nextInt(nb + 1);
     int y = rn.nextInt(nb + 1);
     pts.add(new Point(x, y));
     Collections.shuffle(pts);
     ConvexPolygon test = new ConvexPolygon(pts);

     long startTime = System.nanoTime();
     test.sortVertices(new AntiClockSort(test.getVertices()));
     long endTime = System.nanoTime();
     System.out.println("sortVertices: " + (i + 1) + " vertices sorted in " + (endTime - startTime) + "ns -> " + x + "," + y + " -- nombre de points: " + test.getVertices().length);
     }
     }

     public static void testIntersect(int nb) {
     Random rn = new Random();
     ArrayList<Point> pts = new ArrayList<>();
     pts.add(new Point(rn.nextInt(nb + 1), rn.nextInt(nb + 1)));
     pts.add(new Point(rn.nextInt(nb + 1), rn.nextInt(nb + 1)));
     pts.add(new Point(rn.nextInt(nb + 1), rn.nextInt(nb + 1)));
     ConvexPolygon test = new ConvexPolygon(pts);

     for (int i = 3; i < nb; ++i) {
     int x = rn.nextInt(nb + 1);
     int y = rn.nextInt(nb + 1);
     test.addVertex(new Point(x, y));
     }

     Random rnd = new Random();
     ArrayList<Point> pts2 = new ArrayList<>();
     pts2.add(new Point(rnd.nextInt(nb + 1), rnd.nextInt(nb + 1)));
     pts2.add(new Point(rnd.nextInt(nb + 1), rnd.nextInt(nb + 1)));
     pts2.add(new Point(rnd.nextInt(nb + 1), rnd.nextInt(nb + 1)));
     ConvexPolygon test2 = new ConvexPolygon(pts2);

     for (int i = 3; i < nb; ++i) {
     int x = rn.nextInt(nb + 1);
     int y = rn.nextInt(nb + 1);
     test2.addVertex(new Point(x, y));
     }

     long startTime = System.nanoTime();
     Boolean rez = test.intersects(test2);
     long endTime = System.nanoTime();
     System.out.println("intersects: p1: " + (test.GetXPoints().length) + " vertices, p2: " + test2.GetXPoints().length + " vertices, time: " + (endTime - startTime) + "ns -> " + rez);
     }

     public static void testConvexHull(int nb) {
     ArrayList<Point> pts = new ArrayList<>();
     Random rn = new Random();
     for (int i = 0; i < nb; ++i) {
     int x = rn.nextInt(nb + 1);
     int y = rn.nextInt(nb + 1);
     pts.add(new Point(x, y));
     }
     ConvexPolygon test = new ConvexPolygon(pts);

     long startTime = System.nanoTime();
     ConvexPolygon rez = test.convexHull(pts);
     long endTime = System.nanoTime();
     System.out.println("convexHull: points: " + (test.GetXPoints().length) + " vertices, convexHull: " + rez.GetXPoints().length + " vertices, time: " + (endTime - startTime) + "ns");
     }

     public static void testIsConvex(int nb) {
     ArrayList<Point> pts = new ArrayList<>();
     Random rn = new Random();
     for (int i = 0; i < nb; ++i) {
     int x = rn.nextInt(nb + 1);
     int y = rn.nextInt(nb + 1);
     pts.add(new Point(x, y));
     }
     ConvexPolygon test = new ConvexPolygon(pts);

     long startTime = System.nanoTime();
     Boolean rez = test.isConvex();
     long endTime = System.nanoTime();
     System.out.println("isConvex: points: " + (test.GetXPoints().length) + " vertices, time: " + (endTime - startTime) + "ns - > " + rez);
     }

     public static void testContain(int nb) {
     Random rn = new Random();
     ArrayList<Point> pts = new ArrayList<>();
     pts.add(new Point(rn.nextInt(nb + 1), rn.nextInt(nb + 1)));
     pts.add(new Point(rn.nextInt(nb + 1), rn.nextInt(nb + 1)));
     pts.add(new Point(rn.nextInt(nb + 1), rn.nextInt(nb + 1)));
     ConvexPolygon test = new ConvexPolygon(pts);
     for (int i = 3; i < nb; ++i) {
     int x = rn.nextInt(nb + 1);
     int y = rn.nextInt(nb + 1);
     test.addVertex(new Point(x, y));
     }

     Random rnd = new Random();
     long startTime = System.nanoTime();
     Boolean rez = test.contain(new Point(rnd.nextInt(nb + 1), rnd.nextInt(nb + 1)));
     long endTime = System.nanoTime();
     System.out.println("contain: points: " + (test.GetXPoints().length) + " vertices, time: " + (endTime - startTime) + "ns - > " + rez);
     }*/
    public static ArrayList<PointDouble> generatePoints(int nb) {
        ArrayList<PointDouble> pts = new ArrayList<>();
        pts = PointDouble.getNPointsOnCircle(new PointDouble(300.0, 300.0), 100.0, nb);
        return pts;
    }
}
