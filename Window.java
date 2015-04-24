import com.sun.javafx.geom.Vec2d;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Johan, romis_000
 */
public class Window extends JFrame implements Runnable {

    int xDirection, yDirection;
    private Image dbImage;
    private Graphics dbg;
    private ArrayList<ConvexPolygon> polygons = new ArrayList<>();
    private ArrayList<Point> tempPoints = new ArrayList<>();
    private ArrayList<Point> rdmPolyPoints = new ArrayList<>();
    private ConvexPolygon rdmPoly;
    private ConvexPolygon pol1;
    private ConvexPolygon pol2;
    private ConvexPolygon pol3;
    private ConvexPolygon pol4;
    private ConvexPolygon selectedp;
    private String errorMsg = "";

    public void run() {
        try {
            while (true) {
                move();
                Thread.sleep(5);
            }
        } catch (Exception e) {
            System.out.println("Erreur");
        }
    }

    public void move() {
        if(selectedp != null){
            selectedp.translate(xDirection, yDirection);
        }
    }

    public void setXDirection(int xdir) {
        xDirection = xdir;
    }

    public void setYDirection(int ydir) {
        yDirection = ydir;
    }
    
    public class ML extends MouseInputAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int button = e.getButton();
            // Left click allow the user to select a polygon
            if(button == e.BUTTON1){
                for(ConvexPolygon p : polygons){
                    if(p.contain(new Point(e.getX(), e.getY())))
                        selectedp = p;
                }
            }
            // Right click adds a vertex at the click position
            // for the selected polygon, if any and if it doesn't
            // make it concave
            if(button == e.BUTTON3){
                if(selectedp != null){
                    if(!(selectedp.addVertex(new Point(e.getX(), e.getY())))){
                        errorMsg = "Can't add this point or the polygon will be concave !";
                    }else{
                        errorMsg = "";
                    }
                }
                else{
                    tempPoints.add(new Point(e.getX(), e.getY()));
                    if(tempPoints.size() >= 3){
                        ConvexPolygon p = new ConvexPolygon(tempPoints);
                        polygons.add(p);
                        tempPoints = new ArrayList<>();
                    }
                }
            }
        }
    }

    public class KL extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            // Direction arrow sets the translation value
            // used to move the selected polygon in the window
            if (keyCode == e.VK_LEFT) {
                setXDirection(-1);
            }
            if (keyCode == e.VK_RIGHT) {
                setXDirection(+1);
            }
            if (keyCode == e.VK_UP) {
                setYDirection(-1);
            }
            if (keyCode == e.VK_DOWN) {
                setYDirection(+1);
            }
            // Unselect the currently selected polygon
            if(keyCode == e.VK_ESCAPE){
                selectedp = null;
                errorMsg = "";
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            // Resets the x and/or y translation
            if (keyCode == e.VK_LEFT) {
                setXDirection(0);
            }
            if (keyCode == e.VK_RIGHT) {
                setXDirection(0);
            }
            if (keyCode == e.VK_UP) {
                setYDirection(0);
            }
            if (keyCode == e.VK_DOWN) {
                setYDirection(0);
            }
            // delete a random vertex from the selected polygon
            if (keyCode == e.VK_D) {
                if(selectedp != null){
                    Random rdm = new Random();
                    ArrayList<Point> pts = selectedp.getPoints();
                    selectedp.deleteVertex(pts.get(rdm.nextInt(pts.size())));
                }
            }
            // Sorts the bottom left polygon
            if (keyCode == e.VK_S) {
                pol4.sortVertices(new AntiClockSort(pol4.getVertices()));
            }
            // Generate a new ConvexPolygon from the convex hull of
            // randomly generated points
            if (keyCode == e.VK_R) {
                polygons.remove(rdmPoly);
                rdmPolyPoints.clear();
                Random rdm = new Random();
                for (int i = 0; i < 20; i++) {
                    Point p = new Point(rdm.nextInt(300) + 50, rdm.nextInt(300) + 50);
                    rdmPolyPoints.add(p);
                }
                rdmPoly = new ConvexPolygon(rdmPolyPoints).convexHull(rdmPolyPoints);
                polygons.add(rdmPoly);
            }
        }
    }

    public Window() {
        addKeyListener(new KL());
        addMouseListener(new ML());
        setTitle("Dev");
        setSize(1000, 1000);
        setResizable(false);
        setVisible(true);
        setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ArrayList<Point> points1 = new ArrayList<Point>();
        points1.add(new Point(400, 500));
        points1.add(new Point(600, 400));
        points1.add(new Point(450, 350));
        pol1 = new ConvexPolygon(points1);
        ArrayList<Point> points2 = new ArrayList<Point>();
        points2.add(new Point(600, 600));
        points2.add(new Point(800, 500));
        points2.add(new Point(650, 450));
        points2.add(new Point(550, 550));
        pol2 = new ConvexPolygon(points2);
        polygons.add(pol2);
        ArrayList<Point> points4 = new ArrayList<Point>();
        points4.add(new Point(200, 600));
        points4.add(new Point(250, 800));
        points4.add(new Point(400, 900));
        points4.add(new Point(100, 750));
        pol3 = new ConvexPolygon(points4);
        polygons.add(pol3);
        ArrayList<Point> points5 = new ArrayList<Point>();
        points5.add(new Point(800, 900));
        points5.add(new Point(850, 800));
        points5.add(new Point(900, 950));
        points5.add(new Point(950, 850));
        pol4 = new ConvexPolygon(points5);
        polygons.add(pol4);

        // convex hull randomized test
        ArrayList<Point> points3 = new ArrayList<Point>();
        Random rdm = new Random();
        for (int i = 0; i < 15; i++) {
            Point p = new Point(rdm.nextInt(300) + 50, rdm.nextInt(300) + 50);
            rdmPolyPoints.add(p);
            points3.add(p);
        }
        rdmPoly = new ConvexPolygon(points3).convexHull(points3);
        polygons.add(rdmPoly);
    }

    public void paint(Graphics g) {
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }

    public void paintComponent(Graphics g) {
        //g.fillPolygon(pol1.GetXPoints(), pol1.GetYPoints(), pol1.GetXPoints().length);

        // Draw everything
        for (ConvexPolygon p : polygons) {
            // Specific tests for the selected polygon
            if(selectedp != null){
                //Draw the polygons intersecting with the selected one in red
                if (selectedp.intersects(p)) {
                    g.setColor(Color.red);
                }
                g.drawPolygon(p.GetXPoints(), p.GetYPoints(), p.GetXPoints().length);
                g.setColor(Color.BLACK);
            
                // Draw the selected polygon in blue
                if(p.equals(selectedp)){
                    g.setColor(Color.blue);
                    g.drawPolygon(p.GetXPoints(), p.GetYPoints(), p.GetXPoints().length);
                    g.setColor(Color.black);
                }
            }else{
                // Else draw the polygon in black
                g.drawPolygon(p.GetXPoints(), p.GetYPoints(), p.GetXPoints().length);
            }
            // Draw all the points of the polygon with a filled oval
            for(Point pt : p.getPoints()){
                g.setColor(Color.BLUE);
                int x = (int)pt.getX();
                int y = (int)pt.getY();
                g.fillOval(x - 2, y - 2, 4, 4);
                g.setColor(Color.black);
            }
        }
        
        // Draw all the points generated randomly for the convex polygon
        // issued from the convex hull of theses points
        for(Point p : rdmPolyPoints){
            g.setColor(Color.BLUE);
            int x = (int)p.getX();
            int y = (int)p.getY();
            g.fillOval(x - 2, y - 2, 4, 4);
            g.setColor(Color.black);
        }
        
        g.setColor(Color.red);
        g.drawChars(errorMsg.toCharArray(), 0, errorMsg.length(), 450, 50);
        
        repaint();
    }
}
