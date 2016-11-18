
package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import clui.ConsoleController;
import model.Board.State;
import model.Game;
import model.GameListener;
import model.Location;
//import complete.SquareDemo.Square;
import model.NotImplementedException;
import model.Player;
import controller.*;
public class Main extends JFrame {
	
	private static final String[] playerstrings = {"Human", "DumbAI", 
										"RandomAI", "SmartAI"};
	JPanel center, option;
	Box player1, player2;
	JButton start;
	JLabel text;
	Game game;
	public Controller c1, c2;
	boolean started;
	
	public class Square extends JPanel implements MouseListener,GameListener {
		private final int i, j;
		private boolean selected;
		
		public Square(int i, int j) {
			this.i = i; this.j = j;
			selected = false;
			setPreferredSize(new Dimension(30,30));
			addMouseListener(this);
		}
		
		public @Override void paint(Graphics g) {
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth()-1, getHeight()-1);
			
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, getWidth()-1, getHeight()-1);
			
			g.setColor(Color.BLACK);
			if (game.getBoard().get(i, j) == Player.O) {
				g.drawArc(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2, 0, 360);	
			}
			if (game.getBoard().get(i, j) == Player.X) {
				g.drawLine(10 ,10 ,40 , 40);
				g.drawLine(10 ,40 ,40 , 10);	
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (started) {
				game.submitMove(game.nextTurn(), new Location(i, j));
				text.setText("It is " + game.nextTurn() + "'s turn");
				if (game.nextTurn() == Player.O && c1==null || game.nextTurn() == Player.X && c2==null)
					paintImmediately(0 , 0 , getWidth() , getHeight());
				if (game.getBoard().getState() == State.HAS_WINNER) {
					text.setText("The winner is " + game.getBoard().getWinner().winner);
				}
				else if (game.getBoard().getState() == State.DRAW) {
					text.setText("It is draw");
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void gameChanged(Game g) {
			//for(int i = 0 ; i < Integer.MAX_VALUE ; i++);
			
			if (game.getBoard().getState() == State.HAS_WINNER) {
				text.setText("The winner is " + game.getBoard().getWinner().winner);
			}
			else if (game.getBoard().getState() == State.DRAW) {
				text.setText("It is draw");
			}
			paintImmediately(0 , 0 , getWidth() , getHeight());
		}
	}

	public Main() {
		super("Five in a row");	
		started = false;
		game = new Game(Player.O); 
		center = new JPanel();
		center.setLayout(new GridLayout(9,9));
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				Square s = new Square(i,j);
				center.add(s);
				game.addListener(s);
			}
				
		setPreferredSize(new Dimension(600,500));
		add(center, BorderLayout.CENTER);
		
		option = new JPanel();
		option.setLayout(new GridLayout(8,1));
		
		player1 = new Box(BoxLayout.LINE_AXIS);
			player1.add(new JLabel("Player1 (O):  "));
			JComboBox p1 = new JComboBox(playerstrings);
			p1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					c1 = createController(p1.getSelectedIndex(), Player.O);
				}
			});
			player1.add(p1);
		
		option.add(player1);	
			
		player2 = new Box(BoxLayout.LINE_AXIS);
			player2.add(new JLabel("Player2 (X):  "));
			JComboBox p2 = new JComboBox(playerstrings);
			p2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					c2 = createController(p2.getSelectedIndex(), Player.X);
				}
			});
			player2.add(p2);		
		option.add(player2);
			
		JButton start = new JButton("start");
		start.addActionListener(new ActionListener() {
			public @Override void actionPerformed(ActionEvent arg0) {
				start.setEnabled(false);
				p1.setEnabled(false);
				p2.setEnabled(false);
				started = true;
				text.setText("It is " + Player.O + "'s turn");
				if (c1 != null) game.addListener(c1);
				if (c2 != null) game.addListener(c2);
				
			}
		});
		option.add(start);
		
		text = new JLabel("Press start to begin");
		option.add(text);
		
		add(option, BorderLayout.AFTER_LINE_ENDS);
		
		pack();
	}
			
	public static Controller createController(int k, Player p) {
		switch(k) {
		//"Human", "DumbAI",  "RandomAI", "SmartAI"
		case 1: return new DumbAI(p);
		case 2: return new RandomAI(p);
		case 3: return new SmartAI(p);
		}
		return null;
	}

	public static void main(String[] args) {
		new Main().setVisible(true);	
	}
}
