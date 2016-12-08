package monster;

import com.badlogic.gdx.math.Rectangle;

import tile.Coordinate;

public interface IMonster {
	
	public void update();
	
	public boolean isDead();
	
	public void kill();
	
	public boolean donePath();
	
	public int getHp();
	
	public int getMaxHp();
	
	public Rectangle getCollisionBox();
	
	public void hit(int damage);
	
	public int getX();
	
	public int getY();
	
	public int getWidth();
	
	public int getHeight();
	
	public Coordinate getCenter();
	
	public boolean canDamagePlayer();
	
	public void slow(int amount, int duration);
	
	public boolean isSlowed();
}
