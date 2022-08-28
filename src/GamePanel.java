

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
			
			// setting the borders for the interface
			g.setColor(Color.lightGray); //change colour of pen
			g.fillRect(0,0,width+borderWidth*2,borderWidth);
			g.fillRect(0, 0, borderWidth, height+borderWidth);
			g.fillRect(width+borderWidth, 0, borderWidth, height+borderWidth);
		    g.setColor(Color.pink); //change colour of pen
		    g.fillRect(borderWidth,height,width,borderWidth); //draw danger zone

		    g.setColor(Color.black);
		    g.setFont(new Font("Monospaced", Font.PLAIN, 26));
			//draw the words
		    if (!started.get()) {
				g.setFont(new Font("Monospaced", Font.BOLD, 17));
				g.drawString("Type all the words before they hit the red zone,press enter after each one.",borderWidth*2,height/2);	
		    	
		    }
		    else if (!done.get()) {
				// types the hungry words first then the falling words
				g.setFont(new Font("Monospaced", Font.BOLD, 26));
				for (int i = 0; i < noHungryWords; i++) {
					// g.setColor(Color.BLUE);
					// g.fillRect(hungryWords[i].getX()-hungryWords[i].getWord().length()*15, hungryWords[i].getY()+8, hungryWords[i].getWord().length()*15, 16);
					g.setColor(Color.green);
					// the -length*17 is to account for the entire length of the hungry word so that it doesn't appear the whole word first
					g.drawString(hungryWords[i].getWord(),hungryWords[i].getX()-hungryWords[i].getWord().length()*16,hungryWords[i].getY()+borderWidth);
					
				}
				
				// for the falling words
		    	for (int i=0;i<noWords-1;i++){	   
					// g.setColor(Color.RED);
					// g.fillRect(words[i].getX()+borderWidth, words[i].getY()-16, words[i].getWord().length()*15, 16); 	
					g.setColor(Color.black);
		    		g.drawString(words[i].getWord(),words[i].getX()+borderWidth,words[i].getY());	
				}

				// putting the borders on top of the word
				g.setColor(Color.lightGray); //change colour of pen
				g.fillRect(0,0,width+borderWidth*2,borderWidth);
				g.fillRect(0, 0, borderWidth, height+borderWidth);
				g.fillRect(width+borderWidth, 0, borderWidth, height+borderWidth);
		   }
		   else { if (won.get()) {
			   g.setColor(new Color(204,226,163));
			   g.fillRect(0,0,width+borderWidth*2,height+borderWidth);//the active space
			   g.setColor(Color.black);
			   g.setFont(new Font("Monospaced", Font.BOLD, 26));
			   g.drawString("Well done!",width/3+70,height/2+50);
			   g.setColor(Color.lightGray);
			   g.fillRect(0,0,width+borderWidth*2,borderWidth);
			   g.fillRect(0, 0, borderWidth, height+borderWidth);
			   g.fillRect(width+borderWidth, 0, borderWidth, height+borderWidth);
		   } else {
			   g.setColor(new Color(197,40,61));
			   g.fillRect(0,0,width+borderWidth*2,height+borderWidth);//the active space
			   g.setColor(Color.lightGray);
			   g.setFont(new Font("Monospaced", Font.BOLD, 26));
			   g.drawString("Game over!",width/3+70,height/2);
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

		/**
		 * Gets a valid Y position for the hungry word
		 * @return a valid y position
		 */
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


