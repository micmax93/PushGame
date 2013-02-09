package ai.push.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import ai.push.logic.Counter;
import ai.push.logic.Logic;
import ai.push.logic.Settings;
import ai.push.logic.Transition;


@SuppressWarnings("serial")
public class GameWindow extends JFrame implements MouseListener, ActionListener, WindowListener
{
	static public GameWindow curr;
	boolean editMode=false;
	Counter counter;
	Checker checker;
	Refresher refresher;
	Point start;
	JButton pause = new JButton("Wstrzymaj");
	JButton savegame = new JButton("Zapisz");
	JButton exit = new JButton("Wyjd�");
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
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setEnabled(true);
		setVisible(true);
		addMouseListener(this);
		addWindowListener(this);
		setLayout(null);
		start = new Point(20, 50);
		counter = new Counter(this);
		checker = new Checker(createImage(500, 500), Settings.size);

		pause.setBounds(570, 180, 100, 40);
		savegame.setBounds(570, 250, 100, 40);
		exit.setBounds(570, 320, 100, 40);
		pause.addActionListener(this);
		exit.addActionListener(this);
		savegame.addActionListener(this);
		add(pause);
		add(exit);
		add(savegame);

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
		//pass.setEnabled(false);
		// new Finisher(this).start();
		//checker.game.setupRefreshTarget(this);
		refresher=new Refresher(this);
		refresher.start();
		checker.game.endTurn();
	}
	
	public GameWindow(Logic inL)
	{
		super("Gra");
		// setMinimumSize(minimumSize)
		setBounds(10, 10, 700, 600);
		setBackground(Color.GRAY);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setEnabled(true);
		setVisible(true);
		addMouseListener(this);
		addWindowListener(this);
		setLayout(null);
		start = new Point(20, 50);

		checker = new Checker(createImage(500, 500), Settings.size);
		checker.game=inL;
		counter = new Counter(this);

		pause.setBounds(570, 180, 100, 40);
		savegame.setBounds(570, 250, 100, 40);
		exit.setBounds(570, 320, 100, 40);
		pause.addActionListener(this);
		exit.addActionListener(this);
		savegame.addActionListener(this);
		add(pause);
		add(exit);
		add(savegame);

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
		//pass.setEnabled(false);
		// new Finisher(this).start();
		//checker.game.setupRefreshTarget(this);
		refresher=new Refresher(this);
		refresher.start();
		checker.game.endTurn();
	}
	
	public GameWindow(boolean editable)
	{
		super("Tryb Edycji");
		editMode=editable;
		// setMinimumSize(minimumSize)
		setBounds(10, 10, 700, 600);
		setBackground(Color.GRAY);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setEnabled(true);
		setVisible(true);
		addMouseListener(this);
		addWindowListener(this);
		setLayout(null);
		start = new Point(20, 50);
		checker = new Checker(createImage(500, 500), Settings.size,editable);

		pause.setBounds(570, 180, 100, 40);
		savegame.setBounds(570, 250, 100, 40);
		exit.setBounds(570, 320, 100, 40);
		pause.addActionListener(this);
		exit.addActionListener(this);
		savegame.addActionListener(this);
		add(pause);
		add(exit);
		add(savegame);

		time1.setBounds(570, 30, 20, 120);
		time1.setMaximum(1);
		time1.setValue(1);
		time1.setForeground(Settings.C1);
		add(time1);

		time2.setBounds(570, 400, 20, 120);
		time2.setMaximum(1);
		time2.setValue(1);
		time2.setForeground(Settings.C2);
		add(time2);

		pause.setEnabled(false);
		//pass.setEnabled(false);
		// new Finisher(this).start();
		//checker.game.setupRefreshTarget(this);
		checker.game.locked=false;
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		checker.game.edited=false;
		
		g.clearRect(0, 0, 560, getHeight());
		g.drawImage(checker.getImg(), start.x, start.y, null);
		g.setColor(Color.BLACK);
		g.drawRect(start.x, start.y, checker.size * checker.scale, checker.size
				* checker.scale);
		
		if(editMode) {return;}
		
		g.setColor(Color.BLACK);
		if (checker.game.turn == 1)
		{
			g.drawRect(570, 49, 120, 130);
			pause.setEnabled(!Settings.AI1); 
		}
		else if (checker.game.turn == 2)
		{
			g.drawRect(570, 419, 120, 130);
			pause.setEnabled(!Settings.AI2);
		}
		
		if(checker.game.winner==0)
		{
			counter.setTurn(checker.game.turn);
		}
		else
		{
			counter.pause();
		}
		
	}

	public void mouseClicked(MouseEvent e)
	{
		if(checker.game.locked) {return;}
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
					repaint();
					checker.game.endTurn();
				}
				
			}
			repaint();
		}
	}
	
	void onExit()
	{
		if(editMode) {return;}
		counter.finish();
		refresher.finish();
		if(checker.game.winner==0)
		{
			checker.game.winner=-1;
		}
		
	}
	
	List<Transition> list;
	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();
		if (src == pause)
		{
			if(pause.getText()=="Wstrzymaj") {
				checker.game.holdGame();
				pause.setText("Wzn�w");
				counter.pause();
			}
			else if(pause.getText()=="Wzn�w") {
				checker.game.resumeGame();
				pause.setText("Wstrzymaj");
				counter.unpause();
			}
		}
		else if (src == exit)
		{
			onExit();
			this.dispose();
		}
		else if (src == savegame)
		{
			if(counter!=null) {
				checker.game.holdGame();
				counter.pause();
			}
			
			JFileChooser fs=new JFileChooser();
			int returnVal = fs.showOpenDialog(this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fs.getSelectedFile();
	            System.out.println(file.getPath());
	            try
				{
					checker.game.saveToFile(file.getPath());
				}
				catch (IOException e1)
				{
					JOptionPane.showMessageDialog(null,"Nie uda�o si� zapisa� gry.");
				}
	        }
	        
	        if(counter!=null) {
				checker.game.resumeGame();
				counter.unpause();
	        }
		}
		repaint();
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		onExit();
	}

	public void windowClosed(WindowEvent e){}
	public void windowActivated(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}
