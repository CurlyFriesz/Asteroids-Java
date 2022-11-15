import java.util.*;
import java.awt.*;

public class Meteor {
    Random r = new Random();
    private double x, y;
    private double vx = r.nextDouble(-1,1);
    private double vy = r.nextDouble(-1,1);
    private double speed = 1;
    private int phase;
    private int size;
    private int xc = r.nextInt(1, 3);
    private int yc = r.nextInt(1, 3);
    private int shape;

    public Meteor(int s, int p) {
        shape = s;
        phase = p;

        if(xc == 1){
            x = r.nextInt(0, 350);
        } else if(xc == 2) {
            x = r.nextInt(450, 800);
        }
        if(yc == 1) {
            y = r.nextInt(0, 250);
        } else if(yc == 2) {
            y = r.nextInt(450, 700);
        }
    }

    public Meteor(int s, int p, double xx, double yy) {
        shape = s;
        phase = p;
        x = xx;
        y = yy;
    }

    public void move() {
        x += vx*speed;
        y += vy*speed;

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

        if(phase == 3) {
            size = 3;
        } else if(phase == 2) {
            size = 2;
        } else if(phase == 1) {
            size = 1;
        }
    }

    public double getx() {return x;}
    public double gety() {return y;}
    public int getshape() {return shape;}
    public int getphase() {return phase;}


    public Polygon drawPoly() {
        if(shape == 1) {
            int[] px = {(int)x-10*size,(int)x-30*size,(int)x-15*size,(int)x+25*size,(int)x+30*size,(int)x+5*size,(int)x-25*size};
            int[] py = {(int)y-5*size,(int)y,(int)y+30*size,(int)y+25*size,(int)y-5*size,(int)y-30*size,(int)y-10*size};
            return new Polygon(px, py, 5);
        } else if(shape == 2) {
            int[] px = {(int)x-30*size,(int)x-20*size,(int)x+10*size,(int)x+15*size,(int)x+30*size,(int)x+35*size,(int)x+15*size,(int)x+20*size,(int)x,(int)x-27*size};
            int[] py = {(int)y+5*size,(int)y+20*size,(int)y+30*size,(int)y+20*size,(int)y+20*size,(int)y,(int)y-5*size,(int)y-20*size,(int)y-25*size,(int)y-10*size};
            return new Polygon(px, py, 10);
        } else if(shape == 3) {
            int[] px = {(int)x-30*size,(int)x-15*size,(int)x-30*size,(int)x-25*size,(int)x-5*size,(int)x-5*size,(int)x+5*size,(int)x+15*size,(int)x+15*size,(int)x-5*size};
            int[] py = {(int)y,(int)y+5*size,(int)y+10*size,(int)y+30*size,(int)y+10*size,(int)y+30*size,(int)y+30*size,(int)y+10*size,(int)y-15*size,(int)y-20*size};
            return new Polygon(px, py, 10);
        } else if(shape == 4) {
            int[] px = {(int)x-10*size,(int)x-20*size,(int)x-5*size,(int)x,(int)x+15*size,(int)x+30*size,(int)x+15*size,(int)x+28*size,(int)x+15*size,(int)x+5*size,(int)x-5*size,(int)x-20*size};
            int[] py = {(int)y,(int)y+20*size,(int)y+30*size,(int)y+25*size,(int)y+30*size,(int)y+10*size,(int)y-5*size,(int)y-10*size,(int)y-20*size,(int)y-20*size,(int)y-25*size,(int)y-20*size};
            return new Polygon(px, py, 12);
        } else {
            return null;
        }
    }


    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g.setColor(Color.WHITE);
        g2d.draw(drawPoly());
    }
}
