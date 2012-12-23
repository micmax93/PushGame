package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MainMenu extends JFrame implements ActionListener
{
	JButton startGame = new JButton("Rozpocznij now¹ grê");
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

		title.setBounds(13, 10, getWidth(), 50);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		add(title);

		startGame.setBounds(15, 70, 250, 30);
		startGame.addActionListener(this);
		add(startGame);

		players.setBounds(15, 115, 250, 30);
		players.addActionListener(this);
		add(players);

		settings.setBounds(15, 160, 250, 30);
		settings.addActionListener(this);
		add(settings);

		exit.setBounds(15, 205, 250, 30);
		exit.addActionListener(this);
		add(exit);

		setLayout(null);
		// new Audio().start();
	}

	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();
		if (src == startGame)
		{
			new GameWindow();
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
