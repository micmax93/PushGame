package ai.push.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FieldColection
{
	List<Field> fields;
	byte ang,curr_field;
	Movement curr;
	public FieldColection()
	{
		fields=new ArrayList<Field>();
		ang=-1;
		curr_field=0;
	}
	public void addField(Field f)
	{
		fields.add(f);
	}
	public void randomize()
	{
		Collections.shuffle(fields);
	}
	public Movement getNextInitialMovement()
	{
		ang++;
		if(ang>=8)
		{
			ang=0;
			curr_field++;
		}
		if(curr_field>=fields.size())
		{
			return null;
		}
		return new Movement(fields.get(curr_field), ang);
	}
}
