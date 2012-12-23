package ai.push.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import ai.push.logic.Counter;
import ai.push.logic.Settings;
import ai.push.logic.Transition;


@SuppressWarnings("serial")
public class GameWindow extends JFrame implements MouseListener, ActionListener
{
	static public GameWindow curr;
	Counter counter = new Counter(this);
	Checker checker;
	Point start;
	JButton endTurn = new JButton("Koniec tury");
	JButton pass = new JButton("Generuj");
	JButton exit = new JButton("Wyjdü");
	public JProgressBar time1 = new JProgressBar(SwingConstants.VERTICAL, 0, 0);
	public JProgressBar time2 = new JProgressBar(SwingConstants.VERTICAL, 0, 0);
	public JLabel timer[] = { new JLabel("Czas"), new JLabel("0:00"),
			new JLabel("Czas"), new JLabel("0:00") };

	public GameWindow()
	{
		super("Gra");
		// setMinimumSize(minimumSize)
		setBounds(10, 10, 700, 600);
		setBackground(Color.GRAY);
		setResizable(false);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		setEnabled(true);
		setVisible(true);
		addMouseListener(this);
		setLayout(null);
		start = new Point(20, 50);
		checker = new Checker(createImage(500, 500), Settings.size);

		endTurn.setBounds(570, 180, 100, 40);
		pass.setBounds(570, 250, 100, 40);
		exit.setBounds(570, 320, 100, 40);
		endTurn.addActionListener(this);
		exit.addActionListener(this);
		pass.addActionListener(this);
		add(endTurn);
		add(exit);
		add(pass);

		time1.setBounds(570, 30, 20, 120);
		time1.setMaximum(counter.getTime(0));
		time1.setValue(counter.getTime(1));
		time1.setForeground(Settings.C1);
		add(time1);
		timer[0].setBounds(600, 10, 80, 100);
		timer[0].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		add(timer[0]);
		timer[1].setText(counter.getTimeString(1));
		timer[1].setBounds(600, 50, 80, 100);
		timer[1].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		add(timer[1]);

		time2.setBounds(570, 400, 20, 120);
		time2.setMaximum(counter.getTime(0));
		time2.setValue(counter.getTime(2));
		time2.setForeground(Settings.C2);
		add(time2);
		timer[2].setBounds(600, 380, 80, 100);
		timer[2].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		add(timer[2]);
		timer[3].setText(counter.getTimeString(2));
		timer[3].setBounds(600, 420, 80, 100);
		timer[3].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		add(timer[3]);

		counter.start();
		endTurn.setEnabled(false);
		//pass.setEnabled(false);
		// new Finisher(this).start();
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		g.clearRect(0, 0, 560, getHeight());
		g.drawImage(checker.getImg(), start.x, start.y, null);
		g.setColor(Color.BLACK);
		g.drawRect(start.x, start.y, checker.size * checker.scale, checker.size
				* checker.scale);
		/*
		 * if(stl.Gra.turn==1) { g.setColor(OknoPionka.kolorPionka[0]); } else
		 * if(stl.Gra.turn==2) { g.setColor(OknoPionka.kolorPionka[1]); }
		 * g.fillOval(585, 80, 70, 70); int
		 * c=g.getColor().getBlue()+g.getColor()
		 * .getGreen()+g.getColor().getRed(); if(c<(1.5*255)) {g.setColor(new
		 * Color(255,255,255));} else {g.setColor(new Color(0,0,0));}
		 * g.drawOval(585, 80, 70, 70);
		 */
		counter.setTurn(checker.game.turn);
		g.setColor(Color.BLACK);
		if (checker.game.turn == 1)
		{
			g.drawRect(570, 49, 120, 130);
		}
		else if (checker.game.turn == 2)
		{
			g.drawRect(570, 419, 120, 130);
		}
	}

	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX() - start.x;
		int y = e.getY() - start.y;
		int size = checker.size * checker.scale;
		int button = e.getButton();

		if ((x >= 0) && (x < size) && (y >= 0) && (y < size))
		{
			if (button == MouseEvent.BUTTON1)
			{
				checker.select(x, y);
			}
			else if (button == MouseEvent.BUTTON3)
			{
				if (checker.perform(x, y))
				{
					checker.clearSelection();
					int winner = checker.game.endTurn();
					if (winner != 0)
					{
						JOptionPane.showMessageDialog(null,
								"Gracz " + Integer.toString(winner)
										+ " wygra≥!!!");
					}
				}
			}
			repaint();
		}
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}
	Vector<Transition> list;
	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();
		if (src == endTurn)
		{
			//checker.game.endTurn();
		}
		else if (src == exit)
		{
			counter.finish();
			this.dispose();
		}
		else if (src == pass)
		{
			list=checker.game.generateTransitions();
			System.out.println(list.size());
		}
		repaint();
	}
}
