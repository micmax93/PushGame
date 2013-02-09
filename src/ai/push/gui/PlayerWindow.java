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
import ai.push.logic.ai.AIFactory;


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

	JComboBox<Integer> md1 = new JComboBox<Integer>();
	JComboBox<Integer> md2 = new JComboBox<Integer>();

	public PlayerWindow()
	{
		super("Pionek");
		setBounds(100, 100, 620, 380);
		setBackground(Color.GRAY);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setEnabled(true);
		setVisible(true);

		rb1.setBounds(10, 5, 112, 30);
		rb1.setSelected(true);
		rb2.setBounds(300, 5, 90, 30);
		bg.add(rb1);
		bg.add(rb2);
		add(rb1);
		add(rb2);

		cc1.setBounds(0, 40, 620, 400);
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
		AIFactory.addAlgos(cb1);
		cb1.setBounds(154, 10, 100, 20);
		if (Settings.AI1)
		{
			cb1.setSelectedItem(AIFactory.algoAI1);
		}
		cb1.addActionListener(this);
		add(cb1);
		
		md1.setBounds(254, 10, 35, 20);
		AIFactory.loadDepths(1,(String) cb1.getSelectedItem(), md1);
		md1.setVisible(md1.getItemCount()>0);
		md1.addActionListener(this);
		add(md1);

		prev2.setBounds(391, 10, 30, 20);
		prev2.setVisible(true);
		prev2.setBackground(Settings.C2);
		prev2.setEnabled(false);
		add(prev2);

		cb2.addItem("Gracz");
		AIFactory.addAlgos(cb2);
		cb2.setBounds(422, 10, 100, 20);
		if (Settings.AI2)
		{
			cb2.setSelectedItem(AIFactory.algoAI2);
		}
		cb2.addActionListener(this);
		add(cb2);
		
		md2.setBounds(522, 10, 35, 20);
		AIFactory.loadDepths(2,(String) cb2.getSelectedItem(), md2);
		md2.setVisible(md2.getItemCount()>0);
		md2.addActionListener(this);
		add(md2);

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
			AIFactory.setAlgo(1, cb1);
			AIFactory.loadDepths(1,(String) cb1.getSelectedItem(), md1);
			md1.setVisible(md1.getItemCount()>0);
		}
		if (src == cb2)
		{
			Settings.AI2 = (cb2.getSelectedIndex() > 0);
			AIFactory.setAlgo(2, cb2);
			AIFactory.loadDepths(2,(String) cb2.getSelectedItem(), md2);
			md2.setVisible(md2.getItemCount()>0);
		}
		if (src == md1)
		{
			AIFactory.setDepth(1, md1);
		}
		if (src == md2)
		{
			AIFactory.setDepth(2, md2);
		}
	}
}
