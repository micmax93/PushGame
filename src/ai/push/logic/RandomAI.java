package ai.push.logic;

import java.util.Random;

/**
 * Przyk³adowa implementacja AI dla modelu losowego.
 * @author micmax93
 *
 */
public class RandomAI extends AbstractAI
{
	RandomAI(Logic logic)
	{
		super(logic);
	}

	@Override
	/**
	 * Algorym wybiera losowy ruch.
	 */
	protected void algorithm()
	{
		result=list.get(new Random().nextInt(list.size())).mainMove;
	}

}
