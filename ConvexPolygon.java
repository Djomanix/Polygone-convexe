import com.sun.javafx.geom.Vec2d;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author romis_000, Johan
 */
public class ConvexPolygon {

    private ArrayList<Point> points = new ArrayList<Point>();

    public ConvexPolygon(ArrayList<Point> points) {
        this.points = points;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
    
    public int[] GetXPoints() {
        int xpoints[] = new int[this.points.size()];
        int c = 0;
        for (Point p : this.points) {
            xpoints[c] = p.x;
            ++c;
        }
        return xpoints;
    }

    public int[] GetYPoints() {
        int ypoints[] = new int[this.points.size()];
        int c = 0;
        for (Point p : this.points) {
            ypoints[c] = p.y;
            ++c;
        }
        return ypoints;
    }

    public int[][] getVertices() {
        int[][] vertices = new int[points.size()][2];
        for (int i = 0; i < points.size(); ++i) {
            vertices[i][0] = points.get(i).x;
            vertices[i][1] = points.get(i).y;
        }
        return vertices;
    }

    public Vec2d[] Getaxes() {
        Vec2d[] axes = new Vec2d[this.points.size()];
        for (int i = 0; i < this.points.size(); i++) {
            // get the current vertex
            Vec2d v1 = new Vec2d(this.points.get(i).getX(), this.points.get(i).getY());
            // get the next vertex
            Vec2d v2 = new Vec2d(this.points.get(i + 1 == this.points.size() ? 0 : i + 1).getX(), this.points.get(i + 1 == this.points.size() ? 0 : i + 1).getY());
            // subtract the two to get the edge vector
            Vec2d edge = new Vec2d((v1.x + (-v2.x)), (v1.y + (-v2.y)));
            // get either perpendicular vector
            Vec2d normal = new Vec2d((-edge.y), edge.x);
            // the perp method is just (x, y) => (-y, x) or (y, -x)
            axes[i] = normal;
        }
        return axes;
    }

    public boolean isConvex() {
        Point prev = points.get(points.size() - 2);
        Point curr = points.get(points.size() - 1);
        Point next = points.get(0);
        boolean isCCW = turnLeft(prev, curr, next);
        for (int i = 1; i < points.size(); i++) {
            prev = curr;
            curr = next;
            next = points.get(i);
            if (turnLeft(prev, curr, next) != isCCW) {
                return false;
            }
        }
        return true;
    }

    public boolean contain(Point p) {
        Point prev = points.get(points.size() - 1);
        Point curr = p;
        Point next = points.get(0);
        boolean isCCW = turnLeft(prev, curr, next);
        for (int i = 1; i < points.size(); i++) {
            prev = next;
            next = points.get(i);
            if (turnLeft(prev, curr, next) != isCCW) {
                return false;
            }
        }
        return true;
    }

    public boolean turnLeft(Point p1, Point p2, Point p3) {
        return ((p2.getX() - p1.getX()) * (p3.getY()
                - p2.getY()) - (p3.getX() - p2.getX()) * (p2.getY() - p1.getY())) > 0;
    }

    public boolean isCCW() {
        double min = points.get(0).getY();
        int minIndex = 0;
        for (int i = 1; i < points.size() - 1; i++) {
            if (points.get(i).getY() < min) {
                min = points.get(i).getY();
                minIndex = i;
            }
        }
        Point prev = points.get((minIndex - 1 + points.size()) % points.size());
        Point next = points.get((minIndex + 1) % points.size());
        return turnLeft(prev, points.get(minIndex), next);
    }

    public ConvexPolygon convexHull(ArrayList<Point> points) {
        Collections.sort(points, new PointXCompare());
        int n = points.size();
        
        ArrayList<Point> pl1 = new ArrayList<Point>();
        ArrayList<Point> pl2 = new ArrayList<Point>();
        for (int i = 0; i < n; i++) {
            while (pl2.size() >= 2 && !(turnLeft(pl2.get(pl2.size() - 2), pl2.get(pl2.size() - 1), points.get(i)))) {
                    pl2.remove(pl2.get(pl2.size() - 1));
            }
            pl2.add(points.get(i));
        }
        for (int i = n - 1; i >= 0; i--) {
            while (pl1.size() >= 2 && !(turnLeft(pl1.get(pl1.size() - 2), pl1.get(pl1.size() - 1), points.get(i)))) {
                    pl1.remove(pl1.get(pl1.size() - 1));
            }
            pl1.add(points.get(i));
        }
        pl1.remove(pl1.size() - 1);
        pl2.remove(pl2.size() - 1);
        pl2.addAll(pl1);
        return new ConvexPolygon(pl2);
    }

    public void translate(int x, int y) {
        for (Point p : this.points) {
            p.x += x;
            p.y += y;
        }
    }

    public Boolean intersects(ConvexPolygon pol2) {
        Vec2d[] axes1 = this.Getaxes();
        Vec2d[] axes2 = pol2.Getaxes();
        for (int i = 0; i < axes1.length; i++) {
            Vec2d axis = axes1[i];
            // project both shapes onto the axis
            Projection p1 = this.project(axis);
            Projection p2 = pol2.project(axis);
            // do the projections overlap?
            if (!p1.overlap(p2)) {
                // then we can guarantee that the shapes do not overlap
                return false;
            }
        }
        for (int i = 0; i < axes2.length; i++) {
            Vec2d axis = axes2[i];
            Projection p1 = this.project(axis);
            Projection p2 = pol2.project(axis);
            if (!p1.overlap(p2)) {
                return false;
            }
        }
        return true;
    }

    public Projection project(Vec2d axis) {
        double min = ((axis.x * this.points.get(0).x) + (axis.y * this.points.get(0).y));
        double max = min;
        for (int i = 1; i < this.points.size(); i++) {
            double p = ((axis.x * this.points.get(i).x) + (axis.y * this.points.get(i).y));
            if (p < min) {
                min = p;
            } else if (p > max) {
                max = p;
            }
        }
        Projection proj = new Projection(min, max);
        return proj;
    }

    public Boolean hasVertex(Point pt) {
        for (int i = 0; i < this.points.size(); i++) {
            //for (int i = 0; i < this.points.size() - 1; i++) {
            if (this.points.get(i).equals(pt)) {
                return true;
            }
        }
        return false;
    }

    public Boolean deleteVertex(Point pt) {
        if(this.points.size() > 3)
            for (int i = 0; i < this.points.size(); i++) {
                if (this.points.get(i).equals(pt)) {
                    this.points.remove(i);
                    return true;
                }
            }
        return false;
    }

    public void sortVertices(PolygonSorter sorter) {
        Point[] arrPoints = points.toArray(new Point[points.size()]);
        Arrays.sort(arrPoints, sorter);
        this.points.clear();
        points.addAll(Arrays.asList(arrPoints));
    }

    public Boolean addVertex(Point pt) {
        ArrayList<Point> tempList = new ArrayList<>(Arrays.asList(this.points.toArray(new Point[this.points.size()])));
        tempList.add(pt);
        ConvexPolygon tempPolygon = new ConvexPolygon(tempList);
        tempPolygon.sortVertices(new AntiClockSort(tempPolygon.getVertices()));
        if (tempPolygon.isConvex()) {
            this.points = tempPolygon.points;
            return true;
        } else {
            return false;
        }
    }
}


