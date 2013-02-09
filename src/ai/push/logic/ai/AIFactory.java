package ai.push.logic.ai;

import javax.swing.JComboBox;

import ai.push.logic.Logic;
import ai.push.logic.oracle.Oracle;

public class AIFactory
{
	static final public String GREEDY_AI="AI zach³anny";
	static final public String ALPHA_BETA_AI="AI alfa-beta";
	static final public String RANDOM_AI="AI losowy";
	
	static public String algoAI1=ALPHA_BETA_AI;
	static public String algoAI2=GREEDY_AI;
	
	static public void addAlgos(JComboBox<String> cb)
	{
		cb.addItem(RANDOM_AI);
		cb.addItem(GREEDY_AI);
		cb.addItem(ALPHA_BETA_AI);
	}
	
	static public void setAlgo(int aiID,JComboBox<String> cb)
	{
		if(aiID==1)
		{
			algoAI1=(String) cb.getSelectedItem();
		}
		else if(aiID==2)
		{
			algoAI2=(String) cb.getSelectedItem();
		}
	}
	
	static public AbstractAI getAI(Logic logic, Oracle.PLAYER player)
	{
		String ai="";
		if(player==Oracle.PLAYER.PLAYER1)
		{
			ai=algoAI1;
		}
		else if(player==Oracle.PLAYER.PLAYER2)
		{
			ai=algoAI2;
		}
		
		
		if(ai==RANDOM_AI)
		{
			return new RandomAI(logic, player);
		}
		else if(ai==GREEDY_AI)
		{
			return new GreedyAI(logic, player);
		}
		else if(ai==ALPHA_BETA_AI)
		{
			return new AlphaBetaAI(logic, player);
		}
		return null;
	}
}
