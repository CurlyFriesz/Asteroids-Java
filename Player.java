import java.awt.*;
import java.awt.event.*;

public class Player{
    private int x, y;
    private int up = KeyEvent.VK_W;
    private int left = KeyEvent.VK_A;
    private int right = KeyEvent.VK_D;
    private double rot;
    private double vx = 0,vy = 0;

    public Player(int xx, int yy) {
        x = xx;
        y = yy;
    }

    public void move(boolean[] keys) {
        x += vx;
        y += vy;
        vx *= 0.95;
        vy *= 0.95;
        if(keys[up]) {
            double []thrust = vectToXY(2, rot);
            vx += thrust[0];
            vy += thrust[1];
        }
        if(keys[left]) {
            rot -= Math.cos(5);
        }
        if(keys[right]) {
            rot += Math.cos(5);
        }

        if(x>800) {
            x = 0;
        }
        if(y>700) {
            y = 0;
        }
        if(x<0) {
            x += 800;
        }
        if(y<0) {
            y += 700;
        }
    }

    public Bullet shoot() {
        int tipx = (int)rotate(30,0,rot)[0]+x;
        int tipy = (int)rotate(30,0,rot)[1]+y;
        return new Bullet(tipx, tipy, vectToXY(4, rot)[0], vectToXY(4, rot)[1]);
    }

    public double[] vectToXY(double mag, double ang) {
        double x = mag*Math.cos(ang);
        double y = mag*Math.sin(ang);
        return new double[]{x,y};
    }

    public double[] XYToVect(double x, double y) {
        double mag = Math.sqrt(Math.pow(y, 2) + Math.pow(x, 2));
        double ang = Math.atan2(y, x);
        return new double[]{mag, ang};
    }

    public double[] rotate(double x, double y, double rot) {
        double[] v2 = XYToVect(x, y);
        double[] p2 = vectToXY(v2[0], v2[1]+rot);
        return p2;
    }

    public Polygon drawpoly(int[] px, int[] py, int orx, int ory, double ang) {
        int[] xv = new int[px.length];
        int[] yv = new int[py.length];
        for(int i = 0; i < xv.length; i++) {
            double[] r = rotate(px[i], py[i], ang);
            xv[i] = (int)r[0]+orx;
            yv[i] = (int)r[1]+ory;
        }

        return new Polygon(xv, yv, 5);
    }

    public boolean collMeteor(Meteor m) {
        boolean hit = false;
        int[] px = drawpoly(new int[]{-20,-10,-10,-20,30}, new int[]{-20,0,0,20,0}, x, y, rot).xpoints;
        int[] py = drawpoly(new int[]{-20,-10,-10,-20,30}, new int[]{-20,0,0,20,0}, x, y, rot).ypoints;
        for(int j = 0; j<px.length; j++) {
            if(m.drawPoly().contains(px[j],py[j])) {
                hit = true;
            }
        }
        return hit;
    }
		
	public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g.setColor(Color.WHITE);
        g2d.draw(drawpoly(new int[]{-20,-10,-10,-20,30}, new int[]{-20,-10,10,20,0}, x, y, rot));
        g2d.draw(drawpoly(new int[]{-20,-10,-10,-20,30}, new int[]{-20,-10,10,20,0}, x-800, y, rot));
        g2d.draw(drawpoly(new int[]{-20,-10,-10,-20,30}, new int[]{-20,-10,10,20,0}, x+800, y, rot));
        g2d.draw(drawpoly(new int[]{-20,-10,-10,-20,30}, new int[]{-20,-10,10,20,0}, x, y-700, rot));
        g2d.draw(drawpoly(new int[]{-20,-10,-10,-20,30}, new int[]{-20,-10,10,20,0}, x, y+700, rot));
	}  	
}
