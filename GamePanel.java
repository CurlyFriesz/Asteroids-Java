import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener{
    private boolean keys[];
    private Timer timer;
	private int screen = 1;
	private int INTRO=1;
	private int GAME=2;
	private int GAMEOVER=3;
	private Player p;
	private int gunheat = 0;
	private int lifes = 3;
	private int highscore = 0;
	private int score = 0;
	private int iFrame = 0;
	private ArrayList <Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList <Meteor> meteors = new ArrayList<Meteor>();
	private Meteor[] titlemeteors = {new Meteor(1,3),new Meteor(2,3),new Meteor(4,3),
									 new Meteor(1,2),new Meteor(2,2),new Meteor(3,2),
									 new Meteor(1,1),new Meteor(3,1),new Meteor(4,1)};

    public GamePanel() {
        keys = new boolean[KeyEvent.KEY_LAST+1];
		setPreferredSize(new Dimension(800, 700));
		
		p = new Player(400, 350);

        setFocusable(true);
		requestFocus();
		addKeyListener(this);
		addMouseListener(this);
		timer = new Timer(20, this);
		timer.start();
    }
	
	public void move(){
		p.move(keys);
		gunheat--;
		iFrame--;

		if(keys[KeyEvent.VK_SPACE] && gunheat <= 0) {
			bullets.add(p.shoot());
			gunheat = 10;
		}

		for(int i = 0; i<bullets.size(); i++) {
			bullets.get(i).move();
			if(bullets.get(i).life == 30){
				bullets.remove(i);
			}
		}

		if(screen == GAME && meteors.size()==0) {
			meteors.add(new Meteor(1,3));
			meteors.add(new Meteor(2,3));
			meteors.add(new Meteor(3,3));
			meteors.add(new Meteor(4,3));
			iFrame = 30;
		}
	
		for(Meteor m: meteors) {
			m.move();
			if(screen == GAME && p.collMeteor(m) == true && iFrame <= 0) {
				lifes-=1;
				iFrame = 30;
				p = new Player(400, 350);
			}
		}

		for(Meteor m: titlemeteors) {
			m.move();
		}

		for(int i = 0; i < meteors.size(); i++) {
			for(int j = 0; j<bullets.size(); j++) {
				if(bullets.get(j).hitTar(meteors.get(i)) == true) {
					int shape = meteors.get(i).getshape();
					int phase = meteors.get(i).getphase();
					double tx = meteors.get(i).getx();
					double ty = meteors.get(i).gety();
					bullets.remove(j);
					meteors.remove(i);
					if(phase > 1) {
						meteors.add(new Meteor(shape, phase-1, tx, ty));
						meteors.add(new Meteor(shape, phase-1, tx, ty));
					}
					score+=20;
				}
			}
		}
		if(lifes == 0) {
			if(score > highscore) {
				highscore = score;
			}
			screen = GAMEOVER;
		}
		repaint();
	}
	
    @Override
	public void actionPerformed(ActionEvent e){
		move();
	}
	
	@Override
	public void keyReleased(KeyEvent ke){
		int key = ke.getKeyCode();
		keys[key] = false;
	}	
	
	@Override
	public void keyPressed(KeyEvent ke){
		int key = ke.getKeyCode();
		keys[key] = true;
	}
	
	@Override
	public void keyTyped(KeyEvent ke){}
	
	@Override
	public void	mouseClicked(MouseEvent e){
		if(screen == INTRO || screen == GAMEOVER) {
			screen = GAME;
			score = 0;
			lifes = 3;
			p = new Player(400, 350);
			meteors = new ArrayList<Meteor>();
		}
	}
	
	@Override
	public void	mouseEntered(MouseEvent e){}
	
	@Override
	public void	mouseExited(MouseEvent e){}
	
	@Override
	public void	mousePressed(MouseEvent e){}
	
	@Override
	public void	mouseReleased(MouseEvent e){}
	
    public void paint(Graphics g){
		if(screen == GAME) {
			g.setColor(Color.BLACK);
			g.fillRect(0,0,800,700);
			p.draw(g);
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).draw(g);
			}
			for(Meteor m: meteors) {
				m.draw(g);
			}
			g.setColor(Color.WHITE);

			for(int i = 0; i < lifes; i++) {
				g.drawPolygon(new int[]{15+i*40,10+i*40,25+i*40,40+i*40,35+i*40}, new int[]{120,125,95,125,120}, 5);
			}
			
			g.setFont(new Font("Century Gothic", Font.PLAIN, 36));
			g.drawString("SCORE: " + Integer.toString(score), 10, 40);
			g.drawString("HIGHSCORE: " + Integer.toString(highscore), 10, 80);
		} else if(screen == INTRO) {
			g.setColor(Color.BLACK);
			g.fillRect(0,0,800,700);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Century Gothic", Font.BOLD, 80));
			g.drawString("Asteroids", 230, 250);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 30));
			g.drawString("click anywhere to start", 250, 500);
			for(Meteor m: titlemeteors) {
				m.draw(g);
			}
		}  else if(screen == GAMEOVER) {
			g.setColor(Color.BLACK);
			g.fillRect(0,0,800,700);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Century Gothic", Font.BOLD, 100));
			g.drawString("game over", 160, 200);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 40));
			g.drawString("HIGHSCORE: "+score, 200, 350);
			g.drawString("click anywhere to restart", 170, 500);
		}
    }
}
