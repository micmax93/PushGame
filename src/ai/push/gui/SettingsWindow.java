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
	JLabel label3 = new JLabel("czas oczekiwania");
	SizeSelection combo1 = new SizeSelection();
	RowsSelection combo2 = new RowsSelection();
	DelaySelection combo3 = new DelaySelection();
	JButton exit = new JButton("zamknij");

	public SettingsWindow()
	{
		super("Opcje");
		// setMinimumSize(minimumSize)
		setBounds(160, 160, 190, 200);
		setBackground(Color.GRAY);
		setResizable(false);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		setEnabled(true);
		setVisible(true);

		label1.setBounds(10, 10, 100, 30);
		add(label1);
		combo1.setBounds(120, 10, 50, 30);
		combo1.addActionListener(this);
		add(combo1);

		label2.setBounds(10, 50, 130, 30);
		add(label2);
		combo2.setBounds(120, 50, 50, 30);
		combo2.addActionListener(this);
		add(combo2);

		label3.setBounds(10, 90, 130, 30);
		add(label3);
		combo3.setBounds(120, 90, 50, 30);
		combo3.addActionListener(this);
		add(combo3);

		exit.setBounds(10, 130, 160, 30);
		exit.addActionListener(this);
		add(exit);

		load();
		setLayout(null);
	}

	void load()
	{
		combo1.setSelectedItem(Settings.size);
		combo2.setSelectedItem(Settings.rowCount);
		combo3.setSelectedItem((double)(Settings.delay)/1000);
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
			Settings.delay= (int) ((double)combo3.getSelectedItem()*1000);
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
class DelaySelection extends JComboBox<Double>
{
	DelaySelection()
	{
		super();
		addItem(0.0);
		addItem(0.10);
		addItem(0.25);
		addItem(0.5);
		addItem(0.75);
		addItem(1.0);
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