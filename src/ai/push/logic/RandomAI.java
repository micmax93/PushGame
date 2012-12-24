package ai.push.logic;

import java.util.Random;

public class RandomAI extends AbstractAI
{
	RandomAI(Logic logic)
	{
		super(logic);
	}

	@Override
	protected void algorithm()
	{
		result=list.get(new Random().nextInt(list.size())).mainMove;
	}

}
