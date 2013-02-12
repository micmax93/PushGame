package ai.push.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;

import ai.push.logic.DummyLogic;
import ai.push.logic.Field;
import ai.push.logic.FieldsStaticStorage;
import ai.push.logic.Logic;
import ai.push.logic.Movement;
import ai.push.logic.Settings;

public class Checker
{
	Point[][] startPoint;
	Logic game;
	public final int size, scale;
	public Image img;
	Graphics g;
	int special[][];
	Field selected;

	public Checker(Image img, int size)
	{
		this.size = size;
		special = new int[size][size];
		this.img = img;
		g = img.getGraphics();
		scale = img.getWidth(null) / size;

		game = new Logic();
		startPoint = new Point[size][size];
		for (int k = 0; k < size; k++)
		{
			for (int w = 0; w < size; w++)
			{
				startPoint[k][w] = new Point((k * scale), (w * scale));
			}
		}
		selected = null;
	}
	
	public Checker(Image img, int size,boolean editable)
	{
		this(img,size);
		if(editable)
		{
			game=new DummyLogic();
		}
	}

	public void clearSelection()
	{
		selected = null;
		special = new int[size][size];
	}

	void drawPawn(int row, int column, int type)
	{
		if (type == 0)
		{
			return;
		}
		else if (type == 1)
		{
			g.setColor(Settings.C1);
		}
		else if (type == 2)
		{
			g.setColor(Settings.C2);
		}
		else
		{
			return;
		}

		float scale = this.scale;
		g.fillOval(startPoint[column][row].x + (int) (0.1 * scale),
				startPoint[column][row].y + (int) (int) (0.1 * scale),
				(int) (0.8 * scale), (int) (0.8 * scale));

		if ((column + row) % 2 == 0)
		{
			g.setColor(Color.WHITE);
		}
		else
		{
			g.setColor(Color.BLACK);
		}

		g.drawOval(startPoint[column][row].x + (int) (0.1 * scale),
				startPoint[column][row].y + (int) (int) (0.1 * scale),
				(int) (0.8 * scale), (int) (0.8 * scale));
	}

	void drawField(int row, int column, double size)
	{
		int a = (int) (size * scale);
		if ((a % 2) != (scale % 2))
		{
			a++;
		}
		int b = (int) ((scale - a) / 2);
		g.fillRect(startPoint[column][row].x + b,
				startPoint[column][row].y + b, a, a);
	}

	public Image getImg()
	{
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, size * scale, size * scale);
		byte board[][] = game.getTab();

		for (int row = 0; row < size; row++)
		{
			for (int column = 0; column < size; column++)
			{
				if ((column + row) % 2 == 0)
				{
					g.setColor(Color.BLACK);
				}
				else
				{
					g.setColor(Color.WHITE);
				}
				if (special[row][column] == 1)
				{
					g.setColor(Color.GRAY);
				}
				
				drawField(row, column, 1);

				if (special[row][column] == 2)
				{
					Color c = g.getColor();
					g.setColor(Color.ORANGE);
					drawField(row, column, 0.9);
					g.setColor(c);
					drawField(row, column, 0.85);
				}
				
				drawPawn(row, column, board[row][column]);
			}
		}
		return img;
	}

	public boolean select(int x, int y)
	{
		int column = x / scale;
		int row = y / scale;
		special = new int[size][size];
		selected = null;
		if (game.isSelectable(row, column))
		{
			special[row][column] = 1;
//			selected = new Field(row, column);
			selected = FieldsStaticStorage.getField(row, column);
//			List<Field> dest = game.possibleDestinations(new Field(row,column));
			List<Field> dest = game.possibleDestinations(FieldsStaticStorage.getField(row,column));
			for (int i = 0; i < dest.size(); i++)
			{
				special[dest.get(i).row][dest.get(i).column] = 2;
			}
			return true;
		}
		else
		{
			return false;
		}

	}

	public boolean perform(int x, int y)
	{
		int column = x / scale;
		int row = y / scale;
		if (special[row][column] == 2)
		{
//			Movement mov = new Movement(selected, new Field(row, column));
			Movement mov = new Movement(selected, FieldsStaticStorage.getField(row, column));
			return game.executeMove(mov);
		}
		else
		{
			return false;
		}
	}

}
