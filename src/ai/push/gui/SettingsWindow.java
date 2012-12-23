package ai.push.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ai.push.logic.Settings;


@SuppressWarnings("serial")
public class SettingsWindow extends JFrame implements ActionListener
{
	JLabel label1 = new JLabel("rozmiar");
	JLabel label2 = new JLabel("iloœæ wierszy");
	SizeSelection combo1 = new SizeSelection();
	RowsSelection combo2 = new RowsSelection();
	JButton exit = new JButton("zamknij");

	public SettingsWindow()
	{
		super("Opcje");
		// setMinimumSize(minimumSize)
		setBounds(110, 110, 345, 380);
		setBackground(Color.GRAY);
		setResizable(false);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		setEnabled(true);
		setVisible(true);

		label1.setBounds(50, 130, 100, 30);
		add(label1);
		combo1.setBounds(100, 130, 50, 30);
		combo1.addActionListener(this);
		add(combo1);

		label2.setBounds(50, 180, 130, 30);
		add(label2);
		combo2.setBounds(130, 180, 50, 30);
		combo2.addActionListener(this);
		add(combo2);

		exit.setBounds(190, 300, 100, 30);
		exit.addActionListener(this);
		add(exit);

		load();
		setLayout(null);
	}

	void load()
	{
		combo1.setSelectedItem(Settings.size);
		combo2.setSelectedItem(Settings.rowCount);
	}

	public void paint(Graphics g)
	{
		super.paint(g);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();
		if (src == exit)
		{
			Settings.size = (Integer) combo1.getSelectedItem();
			Settings.rowCount = (Integer) combo2.getSelectedItem();
			this.dispose();
		}
		repaint();
	}
}

@SuppressWarnings("serial")
class SizeSelection extends JComboBox<Integer>
{
	SizeSelection()
	{
		super();
		addItem(6);
		addItem(8);
		addItem(10);
		addItem(12);
	}
}

@SuppressWarnings("serial")
class RowsSelection extends JComboBox<Integer>
{
	RowsSelection()
	{
		super();
		addItem(1);
		addItem(2);
		addItem(3);
		addItem(4);
	}
}