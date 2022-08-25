

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
		private AtomicBoolean done ; //REMOVE
		private AtomicBoolean started ; //REMOVE
		private AtomicBoolean won ; //REMOVE

		private FallingWord[] words;
		private HungryWord[] hungryWords;
		private int noWords, noHungryWords;
		public int width, height;
		private final static int borderWidth=25; //appearance - border

		GamePanel () {

		}
		GamePanel(FallingWord[] words, HungryWord[] hungryWords, int maxY,	
				 AtomicBoolean d, AtomicBoolean s, AtomicBoolean w) {
			this.words=words; //shared word list
			this.hungryWords = hungryWords;
			noWords = words.length; //only need to do this once
			noHungryWords = hungryWords.length;
			done=d; //REMOVE
			started=s; //REMOVE
			won=w; //REMOVE
		}
		
		public void paintComponent(Graphics g) {
		    width = getWidth()-borderWidth*2;
		    height = getHeight()-borderWidth*2;
		    g.clearRect(borderWidth,borderWidth,width,height);//the active space
		    g.setColor(Color.pink); //change colour of pen
		    g.fillRect(borderWidth,height,width,borderWidth); //draw danger zone

		    g.setColor(Color.black);
		    g.setFont(new Font("Arial", Font.PLAIN, 26));
		   //draw the words
		    if (!started.get()) {
		    	g.setFont(new Font("Arial", Font.BOLD, 26));
				g.drawString("Type all the words before they hit the red zone,press enter after each one.",borderWidth*2,height/2);	
		    	
		    }
		    else if (!done.get()) {
				for (int i = 0; i < noHungryWords; i++) {
					g.setColor(Color.green);
					// the -length*17 is to account for the entire length of the hungry word so that it doesn't appear the whole word first
					g.drawString(hungryWords[i].getWord(),hungryWords[i].getX()-hungryWords[i].getWord().length()*17,hungryWords[i].getY()+borderWidth);
					// g.setColor(Color.lightGray); //change colour of pen
					// g.fillRect(borderWidth,0,width,borderWidth);
				}
				// System.out.println("hungry word drawn");
		    	for (int i=0;i<noWords-1;i++){	    	
					g.setColor(Color.black);
		    		g.drawString(words[i].getWord(),words[i].getX()+borderWidth,words[i].getY());	
				}
				g.setColor(Color.lightGray); //change colour of pen
				g.fillRect(0,0,width+borderWidth*2,borderWidth);
				g.fillRect(0, 0, borderWidth, height+borderWidth);
				g.fillRect(width+borderWidth, 0, borderWidth, height+borderWidth);
		   }
		   else { if (won.get()) {
			   g.setFont(new Font("Arial", Font.BOLD, 36));
			   g.drawString("Well done!",width/3,height/2);
			   g.setColor(Color.lightGray);
			   g.fillRect(0,0,width+borderWidth*2,borderWidth);
			   g.fillRect(0, 0, borderWidth, height+borderWidth);
			   g.fillRect(width+borderWidth, 0, borderWidth, height+borderWidth);
		   } else {
			   g.setFont(new Font("Arial", Font.BOLD, 36));
			   g.drawString("Game over!",width/2,height/2);
			   g.fillRect(0,0,width+borderWidth*2,borderWidth);
			   g.fillRect(0, 0, borderWidth, height+borderWidth);
			   g.fillRect(width+borderWidth, 0, borderWidth, height+borderWidth);
		   }
		   }
		}
		
		public int getValidXpos() {
			int width = getWidth()-borderWidth*4;
			int x= (int)(Math.random() * width);
			return x;
		}

		public int getValidYpos() {
			int height = getHeight()-borderWidth*4;
			int y= (int)(Math.random() * height);
			return y;
		}
		
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(10); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
			}
		}

	}


