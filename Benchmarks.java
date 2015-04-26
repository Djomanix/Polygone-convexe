
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Johan
 */
/**
 * Runs the benchmarks for the most important operations and generates a file
 * with time execution results
 */
public class Benchmarks {

    static Date date = new Date();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
    static File file = new File("Benchmarks_" + dateFormat.format(date) + ".txt");
    static BufferedWriter out;

    public static void main(String[] args) throws IOException {
        out = new BufferedWriter(new FileWriter(file, true));
        out.write("--- Benchmarks pour la structure de données ConvexPolyogon ---");
        out.newLine();
        out.close();

        addVertexBench();
        sortVerticesBench();
        intersectsBench();
        convexHullBench();
        isConvexBench();
        containsBench();
    }

    /**
     * Runs addVertex operation series of benchs
     */
    public static void addVertexBench() throws IOException {
        out = new BufferedWriter(new FileWriter(file, true));
        out.newLine();
        out.write("-> Opération addVertex:");
        out.newLine();
        out.close();

        testAddVertex(1000);
        testAddVertex(10000);
        testAddVertex(20000);
        testAddVertex(50000);
        testAddVertex(100000);
        //testAddVertex(1000000);
    }

    /**
     * Runs sortVertices operation series of benchs
     */
    public static void sortVerticesBench() throws IOException {
        out = new BufferedWriter(new FileWriter(file, true));
        out.newLine();
        out.write("-> Opération sortVertices:");
        out.newLine();
        out.close();

        testSortVertices(1000);
        testSortVertices(10000);
        testSortVertices(20000);
        testSortVertices(50000);
        testSortVertices(100000);
        testSortVertices(1000000);
        testSortVertices(10000000);
    }

    /**
     * Runs intersects operation series of benchs
     */
    public static void intersectsBench() throws IOException {
        out = new BufferedWriter(new FileWriter(file, true));
        out.newLine();
        out.write("-> Opération intersects:");
        out.newLine();
        out.close();

        testIntersects(10);
        testIntersects(100);
        testIntersects(1000);
        testIntersects(10000);
        testIntersects(20000);
        testIntersects(50000);
        //testIntersects(100000);
    }

    /**
     * Runs convexHull operation series of benchs
     */
    public static void convexHullBench() throws IOException {
        out = new BufferedWriter(new FileWriter(file, true));
        out.newLine();
        out.write("-> Opération convexHull:");
        out.newLine();
        out.close();

        testConvexHull(10);
        testConvexHull(100);
        testConvexHull(1000);
        testConvexHull(10000);
        testConvexHull(20000);
        testConvexHull(50000);
        testConvexHull(100000);
        //testConvexHull(1000000);
    }

    /**
     * Runs isConvex operation series of benchs
     */
    public static void isConvexBench() throws IOException {
        out = new BufferedWriter(new FileWriter(file, true));
        out.newLine();
        out.write("-> Opération isConvex:");
        out.newLine();
        out.close();

        testIsConvex(1000);
        testIsConvex(10000);
        testIsConvex(100000);
        testIsConvex(1000000);
        testIsConvex(10000000);
        //testIsConvex(100000000);
    }

    /**
     * Runs contain operation series of benchs
     */
    public static void containsBench() throws IOException {
        out = new BufferedWriter(new FileWriter(file, true));
        out.newLine();
        out.write("-> Opération contain:");
        out.newLine();
        out.close();

        testContain(1000);
        testContain(10000);
        testContain(100000);
        testContain(1000000);
        testContain(10000000);
        //testContain(100000000);
    }

    /**
     * addVertex bench: adds a vertex to a polygon with the given number of
     * vertices
     */
    public static void testAddVertex(int nb) throws IOException {
        float totalTime = 0;
        ArrayList<PointDouble> points = new ArrayList<>();
        ArrayList<PointDouble> randomPoints = new ArrayList<>();
        randomPoints = generatePoints(nb + 1);

        for (int i = 0; i < nb; ++i) {
            points.add(randomPoints.remove(0));
        }
        ConvexPolygon test = new ConvexPolygon(points);
        test.sortVertices(new AntiClockSort(test.getVertices()));

        long startTime = System.nanoTime();
        test.addVertex(randomPoints.remove(0));
        long endTime = System.nanoTime();
        totalTime = (endTime - startTime);

        System.out.println("addVertex: vertex added in a polygon of " + points.size() + " vertices in " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out = new BufferedWriter(new FileWriter(file, true));
        out.write("addVertex: On a ajouté un sommet à un polygone de " + points.size() + " sommets en " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out.newLine();
        out.close();
    }

    /**
     * sortVertices bench: sorts the given number of vertices in the ArrayList
     * of a polygon
     */
    public static void testSortVertices(int nb) throws IOException {
        float totalTime = 0;
        ArrayList<PointDouble> randomPoints = new ArrayList<>();
        randomPoints = generatePoints(nb);
        Collections.shuffle(randomPoints);

        ConvexPolygon test = new ConvexPolygon(randomPoints);
        long startTime = System.nanoTime();
        test.sortVertices(new AntiClockSort(test.getVertices()));
        long endTime = System.nanoTime();
        totalTime = (endTime - startTime);

        System.out.println("sortVertices: " + randomPoints.size() + " vertices sorted in " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out = new BufferedWriter(new FileWriter(file, true));
        out.write("sortVertices: On a trié " + randomPoints.size() + " sommets d'un polygone en " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out.newLine();
        out.close();
    }

    /**
     * intersects bench: tests the intersection between two polygons with the
     * given number of vertices
     */
    public static void testIntersects(int nb) throws IOException {
        float totalTime = 0;
        ArrayList<PointDouble> points1 = new ArrayList<>();
        ArrayList<PointDouble> points2 = new ArrayList<>();
        points1 = generatePoints(nb);
        points2 = generatePoints(nb);
        ConvexPolygon test1 = new ConvexPolygon(points1);
        ConvexPolygon test2 = new ConvexPolygon(points2);
        test1.sortVertices(new AntiClockSort(test1.getVertices()));
        test2.sortVertices(new AntiClockSort(test2.getVertices()));

        long startTime = System.nanoTime();
        Boolean rez = test1.intersects(test2);
        long endTime = System.nanoTime();
        totalTime = (endTime - startTime);

        System.out.println("intersects: intersection test between two polygons of " + points1.size() + " vertices achieved in " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out = new BufferedWriter(new FileWriter(file, true));
        out.write("intersects: On a testé l'intersection de deux polygones de " + points1.size() + " sommets en " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out.newLine();
        out.close();
    }

    /**
     * convexHull bench: gets the convex hull of the given number of points
     */
    public static void testConvexHull(int nb) throws IOException {
        float totalTime = 0;
        ArrayList<PointDouble> randomPoints = new ArrayList<>();
        randomPoints = generatePoints(nb);
        Collections.shuffle(randomPoints);
        ConvexPolygon test = new ConvexPolygon(randomPoints);

        long startTime = System.nanoTime();
        ConvexPolygon rez = test.convexHull(randomPoints);
        long endTime = System.nanoTime();
        totalTime = (endTime - startTime);

        System.out.println("convexHull: convex hull of " + randomPoints.size() + " points achieved in " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out = new BufferedWriter(new FileWriter(file, true));
        out.write("convexHull: On a généré l'enveloppe convexe de " + randomPoints.size() + " points en " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out.newLine();
        out.close();
    }

    /**
     * isConvex bench: checks if the polygon with the given number of vertices
     * is convex (we test the worst case -> polygon vertices are ordered)
     */
    public static void testIsConvex(int nb) throws IOException {
        float totalTime = 0;
        ArrayList<PointDouble> randomPoints = new ArrayList<>();
        randomPoints = generatePoints(nb);
        ConvexPolygon test = new ConvexPolygon(randomPoints);
        test.sortVertices(new AntiClockSort(test.getVertices()));

        long startTime = System.nanoTime();
        Boolean rez = test.isConvex();
        long endTime = System.nanoTime();
        totalTime = (endTime - startTime);

        System.out.println("isConvex: convexity test on polygon of " + randomPoints.size() + " vertices achieved in " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out = new BufferedWriter(new FileWriter(file, true));
        out.write("isConvex: On a testé la convexité d'un polygone de " + randomPoints.size() + " points en " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out.newLine();
        out.close();
    }

    /**
     * contain bench: checks if the polygon with the given number of vertices
     * contains a randomly generated point
     */
    public static void testContain(int nb) throws IOException {
        float totalTime = 0;
        ArrayList<PointDouble> randomPoints = new ArrayList<>();
        randomPoints = generatePoints(nb);
        ConvexPolygon test = new ConvexPolygon(randomPoints);
        test.sortVertices(new AntiClockSort(test.getVertices()));

        Random rnd = new Random();
        long startTime = System.nanoTime();
        Boolean rez = test.contain(new PointDouble(rnd.nextInt(500), rnd.nextInt(500)));
        long endTime = System.nanoTime();
        totalTime = (endTime - startTime);

        System.out.println("contain: check if a polygon of " + randomPoints.size() + " vertices contains a point in " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out = new BufferedWriter(new FileWriter(file, true));
        out.write("contain: On a testé si un polygone de " + randomPoints.size() + " sommets contient un point en " + totalTime / 1000000 + "ms <-> " + (totalTime / 1000000) / 1000 + "s");
        out.newLine();
        out.close();
    }

    /**
     * generates some points making it easier to use convex polygons
     */
    public static ArrayList<PointDouble> generatePoints(int nb) {
        ArrayList<PointDouble> pts = new ArrayList<>();
        pts = PointDouble.getNPointsOnCircle(new PointDouble(300.0, 300.0), 100.0, nb);
        return pts;
    }
}
