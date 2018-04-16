package net.kalman98.freecoins;

public class CoinboHandler
{
	private static CoinboHandler instance = new CoinboHandler();
	private int multiplier = 0;
	private long score = 0;
	private long overallScore = 0;
	private long highScore = 0;
	private long mostRecentHitTime;
	
	public long getScore()
	{
		return this.score;
	}
	
	public void setScore(long scoreIn)
	{
		this.score = scoreIn;
	}
	
	public void setOverallScore(long overallScoreIn)
	{
		this.overallScore = overallScoreIn;
	}
	
	public long getOverallScore()
	{
		return this.overallScore;
	}
	
	public void addScore(long amount)
	{
		this.setScore(this.getScore() + amount);
	}
	
	public void addOverallScore(long amount)
	{
		this.setOverallScore(this.getOverallScore() + amount);
	}
	
	public long getHighScore()
	{
		return this.highScore;
	}
	
	public void setHighScore(long scoreIn)
	{
		this.highScore = scoreIn;
	}
	public int getMultiplier()
	{
		return this.multiplier;
	}
	
	public long getMostRecentHitTime()
	{
		return this.mostRecentHitTime;
	}
	
	public long getTimeSinceLastHit(long worldTime)
	{
		return worldTime - this.getMostRecentHitTime();
	}
	
	public void setMultiplier(int multiplierIn)
	{
		this.multiplier = multiplierIn;
	}
	
	/**
	 * Adds one to the handler's multiplie	coinboHandler.setHighScore(coinboHandler.);r.
	 */
	public void incrementMultiplier()
	{
		this.setMultiplier(multiplier + 1);
	}
	
	public void resetMultiplier()
	{
		this.setMultiplier(1);
	}
	
	public void resetScore()
	{
		this.setScore(0);
	}
	
	public void setMostRecentHitTime(long timeIn)
	{
		this.mostRecentHitTime = timeIn;
	}
	
	public boolean isComboValid(long worldTime)
	{
		return this.getTimeSinceLastHit(worldTime) < 1.75; // a combo can be continued if the latest hit is within 1.75 seconds of the one before it
	}
	
	public void worldChange()
	{
		if (this.getScore() > this.getHighScore())
			this.setHighScore(this.getScore());
		this.resetMultiplier();
		this.resetScore();
	}

	public static CoinboHandler getInstance()
	{
		return instance;
	}
}
