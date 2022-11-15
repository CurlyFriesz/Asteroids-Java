import java.awt.*;

public class Bullet {
    private int x, y;
    private double vx, vy;
    int life = 0;

    public Bullet(int xx, int yy, double vxx, double vyy) {
        x = xx;
        y = yy;
        vx = vxx;
        vy = vyy;
    }

    public void move() {
        x += vx*5;
        y += vy*5;
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
        life++;
    }

    public boolean hitTar(Meteor m) {
        boolean hit = false;
        if(m.drawPoly().contains(x,y)) {
            hit =  true;
        }
        else{
            hit = false;
        }
        return hit;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawOval(x, y, 6, 6);
        g.drawOval(x+800, y, 6, 6);
        g.drawOval(x-800, y, 6, 6);
        g.drawOval(x, y+700, 6, 6);
        g.drawOval(x, y-700, 6, 6);
    }
}

