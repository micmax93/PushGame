package ai.push.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ai.push.logic.Logic;

@SuppressWarnings("serial")
public class MainMenu extends JFrame implements ActionListener
{
	JButton startGame = new JButton("Nowa gra");
	JButton loadGame = new JButton("Za³aduj grê");
	JButton editBoard = new JButton("Ustaw planszê");
	JButton players = new JButton("Wybór graczy");
	JButton settings = new JButton("Ustawienia");
	JButton exit = new JButton("WyjdŸ");
	JLabel title = new JLabel("PUSH");

	public MainMenu()
	{
		super("Menu");
		setBounds(200, 200, 300, 280);
		setBackground(new Color(51, 179, 255));
		setForeground(new Color(51, 179, 255));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setEnabled(true);
		setVisible(true);
		
		MenuLayout m1=new MenuLayout();
		MenuLayout m2=new MenuLayout();
		m2.sx=m1.sx+m1.w+20;
		m2.lx=m2.sx;

		//startGame.setBounds(15, 70, 250, 30);
		m1.setBounds(startGame);
		startGame.addActionListener(this);
		add(startGame);
		
		m2.setBounds(loadGame);
		loadGame.addActionListener(this);
		add(loadGame);
		
		m1.setBounds(editBoard);
		editBoard.addActionListener(this);
		add(editBoard);

		//players.setBounds(15, 115, 250, 30);
		m2.setBounds(players);
		players.addActionListener(this);
		add(players);

		//settings.setBounds(15, 160, 250, 30);
		m1.setBounds(settings);
		settings.addActionListener(this);
		add(settings);

		//exit.setBounds(15, 205, 250, 30);
		m2.setBounds(exit);
		exit.addActionListener(this);
		add(exit);

		setLayout(null);
		m2.setSize(this);
		// new Audio().start();
		
		title.setBounds(getWidth()/2-75, 10, getWidth(), 50);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		add(title);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();
		if (src == startGame)
		{
			new GameWindow();
		}
		else if (src == editBoard)
		{
			new GameWindow(true);
		}
		else if (src == loadGame)
		{
			JFileChooser fs=new JFileChooser();
			int returnVal = fs.showOpenDialog(this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fs.getSelectedFile();
	            Logic l=new Logic();
	            try
				{
					l.loadFromFile(file.getPath());
					new GameWindow(l);
				}
				catch (ClassNotFoundException | IOException e1)
				{
					JOptionPane.showMessageDialog(null,"Nie uda³o siê otworzyæ gry.");
				}
	        }
		}
		else if (src == settings)
		{
			new SettingsWindow();
		}
		else if (src == exit)
		{
			this.dispose();
		}
		else if (src == players)
		{
			new PlayerWindow();
		}
	}

}

class MenuLayout
{
	public int sx=15,sy=70;
	public int w=120,h=30,d=15;
	public int lx=15,ly=70;
	public void setBounds(JButton b)
	{
		b.setBounds(lx, ly, w, h);
		ly+=h+d;
	}
	public void setSize(JFrame f)
	{
		f.setSize(sx+w+30, ly+h);
	}
}
