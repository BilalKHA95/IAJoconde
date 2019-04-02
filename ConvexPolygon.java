package iajoconde_final;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javafx.collections.ObservableList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class ConvexPolygon extends Polygon implements Comparable {

    static final int maxNumPoints = 3;
    static Random gen = new Random(); 
    static int max_X, max_Y;
    NumberFormat nf = new DecimalFormat("##.00");


    // randomly generates a polygon
    public ConvexPolygon(int numPoints) {
        super();
        genRandomConvexPolygone(numPoints);
        int r = gen.nextInt(256);
        int g = gen.nextInt(256);
        int b = gen.nextInt(256);
        this.setFill(Color.rgb(0,0,0,0));
        this.setOpacity(gen.nextDouble());
    }

    public ConvexPolygon() {
        super();
    }

    public String toString() {
        String res = super.toString();
        res += " " + this.getFill() + " opacity " + this.getOpacity();
        return res;
    }

    public void addPoint(double x, double y) {
        getPoints().add(x);
        getPoints().add(y);
    }
    
    
    public double dist(double x , double y){
        
        return Math.sqrt(Math.pow(x,2) + Math.pow(y, 2)) ; 
    
    
    }

    // http://cglab.ca/~sander/misc/ConvexGeneration/convex.html
    public void genRandomConvexPolygone(int n) {
        List<Point> points = new LinkedList<Point>();
        List<Integer> abs = new ArrayList<>();
        List<Integer> ord = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            abs.add(gen.nextInt(max_X));
            ord.add(gen.nextInt(max_Y));
        }
        Collections.sort(abs);
        Collections.sort(ord);
        //System.out.println(abs + "\n" + ord);
        int minX = abs.get(0);
        int maxX = abs.get(n - 1);
        int minY = ord.get(0);
        int maxY = ord.get(n - 1);

        List<Integer> xVec = new ArrayList<>();
        List<Integer> yVec = new ArrayList<>();

        int top = minX, bot = minX;
        for (int i = 1; i < n - 1; i++) {
            int x = abs.get(i);

            if (gen.nextBoolean()) {
                xVec.add(x - top);
                top = x;
            } else {
                xVec.add(bot - x);
                bot = x;
            }
        }
        xVec.add(maxX - top);
        xVec.add(bot - maxX);

        int left = minY, right = minY;
        for (int i = 1; i < n - 1; i++) {
            int y = ord.get(i);

            if (gen.nextBoolean()) {
                yVec.add(y - left);
                left = y;
            } else {
                yVec.add(right - y);
                right = y;
            }
        }
        yVec.add(maxY - left);
        yVec.add(right - maxY);

        Collections.shuffle(yVec);

        List<Point> lpAux = new ArrayList<>();
        for (int i = 0; i < n; i++)
            lpAux.add(new Point(xVec.get(i), yVec.get(i)));

        // sort in order by angle
        Collections.sort(lpAux, (x, y) -> Math.atan2(x.getY(), x.getX()) < Math.atan2(y.getY(), y.getX()) ? -1 :
                Math.atan2(x.getY(), x.getX()) == Math.atan2(y.getY(), y.getX()) ? 0 : 1);

        int x = 0, y = 0;
        int minPolX = 0, minPolY = 0;

        for (int i = 0; i < n; i++) {
            points.add(new Point(x, y));
            x += lpAux.get(i).getX();
            y += lpAux.get(i).getY();

            if (x < minPolX)
                minPolX = x;
            if (y < minPolY)
                minPolY = y;
        }

        int xshift = gen.nextInt(max_X - (maxX - minX));
        int yshift = gen.nextInt(max_Y - (maxY - minY));
        xshift -= minPolX;
        yshift -= minPolY;
        for (int i = 0; i < n; i++) {
            Point p = points.get(i);
            p.translate(xshift, yshift);
        }
        for (Point p : points)
            addPoint(p.getX(), p.getY());
    }

    @Override
    public int compareTo(Object o) {
        
        
        
        if(o instanceof ConvexPolygon){
            ObservableList<Double> pts = this.getPoints();
            int resA = 0 , resB = 0; 
            ObservableList<Double> ptsO = this.getPoints();
            for(int i=0 ; i < pts.size() ; i+=2){
                resA+= this.dist(pts.get(i), pts.get(i + 1)) ;
                resB+=this.dist(ptsO.get(i), ptsO.get(i + 1)) ;
            }
            
            if(resA == resB){
                return 0 ; 
            
            }else if(resA > resB){
                return 1 ; 
            
            }else{
                return -1 ; 
            
            }

        
        }else{
            return -1 ; 
        
        }
        
    }

    public class Point {

        int x, y;

        // generate a random point
        public Point() {
            x = gen.nextInt(max_X);
            y = gen.nextInt(max_Y);
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void translate(int vx, int vy) {
            x += vx;
            y += vy;
        }

        public boolean equals(Object o) {
            if (o == null)
                return false;
            else if (o == this)
                return true;
            else if (o instanceof Point)
                return ((Point) o).x == this.x && ((Point) o).y == this.y;
            else
                return false;
        }

        public String toString() {
            NumberFormat nf = new DecimalFormat("#.00");
            return "(" + x + "," + y + ")"; // + nf.format(Math.atan2(y, x))+")";
        }

    }
}
