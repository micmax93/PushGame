package ai.push.logic.ai;

import javax.swing.JComboBox;

import ai.push.logic.Logic;
import ai.push.logic.oracle.Oracle;

public class AIFactory
{
	static final public String GREEDY_AI="AI zach³anny";
	static final public String ALPHA_BETA_AI="AI alfa-beta";
	static final public String RANDOM_AI="AI losowy";
	static final public String FS_ALPHA_BETA = "AI alfa-beta FS";
	
	static public String algoAI1=GREEDY_AI;
	static public Integer depthAI1=4;
	static public String algoAI2=ALPHA_BETA_AI;
	static public Integer depthAI2=4;
	
	
	static public void addAlgos(JComboBox<String> cb)
	{
		cb.addItem(RANDOM_AI);
		cb.addItem(GREEDY_AI);
		cb.addItem(ALPHA_BETA_AI);
		cb.addItem(FS_ALPHA_BETA);
	}
	
	static public void loadDepths(int aiID,String algo,JComboBox<Integer> cb)
	{
		if(algo==ALPHA_BETA_AI || algo == FS_ALPHA_BETA)
		{
			cb.removeAllItems();
			cb.addItem(2);
			cb.addItem(4);
			cb.addItem(6);
			if(aiID==1)
			{
				cb.setSelectedItem(depthAI1);
			}
			else if(aiID==2)
			{
				cb.setSelectedItem(depthAI2);
			}
			
		}
		else
		{
			cb.removeAllItems();
		}
	}
	
	static public void setDepth(int aiID,JComboBox<Integer> cb)
	{
		if(cb.getItemCount()>0)
		{
			if(aiID==1)
			{
				depthAI1=(Integer) cb.getSelectedItem();
			}
			else if(aiID==2)
			{
				depthAI2=(Integer) cb.getSelectedItem();
			}
		}
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
		Integer md=0;
		if(player==Oracle.PLAYER.PLAYER1)
		{
			ai=algoAI1;
			md=depthAI1;
		}
		else if(player==Oracle.PLAYER.PLAYER2)
		{
			ai=algoAI2;
			md=depthAI2;
		}
		
		AbstractAI result=null;
		if(ai==RANDOM_AI)
		{
			result=new RandomAI(logic, player);
		}
		else if(ai==GREEDY_AI)
		{
			result=new GreedyAI(logic, player);
		}
		else if(ai==ALPHA_BETA_AI)
		{
			result=new AlphaBetaAI(logic, player);
			result.setMaxDepth(md);
		}
		else if(ai==FS_ALPHA_BETA)
		{
			result=new FSAlphaBetaAI(logic, player);
			result.setMaxDepth(md);
		}
		return result;
	}
}
