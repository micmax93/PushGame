package ai.push.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ai.push.logic.Settings;


@SuppressWarnings("serial")
public class PlayerWindow extends JFrame implements ChangeListener,
		ActionListener
{
	JColorChooser cc1 = new JColorChooser();
	JRadioButton rb1 = new JRadioButton("Gracz pierwszy");
	JRadioButton rb2 = new JRadioButton("Gracz drugi");
	ButtonGroup bg = new ButtonGroup();
	JButton prev1 = new JButton();
	JButton prev2 = new JButton();
	JComboBox<String> cb1 = new JComboBox<String>();
	JComboBox<String> cb2 = new JComboBox<String>();

	public PlayerWindow()
	{
		super("Pionek");
		setBounds(100, 100, 500, 380);
		setBackground(Color.GRAY);
		setResizable(false);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		setEnabled(true);
		setVisible(true);

		rb1.setBounds(10, 5, 112, 30);
		rb1.setSelected(true);
		rb2.setBounds(250, 5, 90, 30);
		bg.add(rb1);
		bg.add(rb2);
		add(rb1);
		add(rb2);

		cc1.setBounds(0, 40, 500, 400);
		add(cc1);
		setLayout(null);
		rb1.addActionListener(this);
		rb2.addActionListener(this);
		cc1.getSelectionModel().addChangeListener(this);
		cc1.setColor(Settings.C1);

		prev1.setBounds(123, 10, 30, 20);
		prev1.setVisible(true);
		prev1.setBackground(Settings.C1);
		prev1.setEnabled(false);
		add(prev1);

		cb1.addItem("Gracz");
		cb1.addItem("AI");
		cb1.setBounds(154, 10, 60, 20);
		if (Settings.AI1)
		{
			cb1.setSelectedIndex(1);
		}
		cb1.addActionListener(this);
		add(cb1);

		prev2.setBounds(341, 10, 30, 20);
		prev2.setVisible(true);
		prev2.setBackground(Settings.C2);
		prev2.setEnabled(false);
		add(prev2);

		cb2.addItem("Gracz");
		cb2.addItem("AI");
		cb2.setBounds(372, 10, 60, 20);
		if (Settings.AI2)
		{
			cb2.setSelectedIndex(1);
		}
		cb2.addActionListener(this);
		add(cb2);

	}

	public void stateChanged(ChangeEvent e)
	{
		if (rb1.isSelected())
		{
			Settings.C1 = cc1.getColor();
		}
		if (rb2.isSelected())
		{
			Settings.C2 = cc1.getColor();
		}

		prev1.setBackground(Settings.C1);
		prev2.setBackground(Settings.C2);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (rb1.isSelected())
		{
			cc1.setColor(Settings.C1);
		}
		if (rb2.isSelected())
		{
			cc1.setColor(Settings.C2);
		}
		Object src = e.getSource();
		if (src == cb1)
		{
			Settings.AI1 = (cb1.getSelectedIndex() > 0);
		}
		if (src == cb2)
		{
			Settings.AI2 = (cb2.getSelectedIndex() > 0);
		}
	}
}
