package monster;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

import tile.Coordinate;

public interface IMonster extends Poolable {
	
	public void update();
	
	public boolean isDead();
	
	public void kill();
	
	public boolean donePath();
	
	public int getHp();
	
	public int getMaxHp();
	
	public Rectangle getCollisionBox();
	
	public void hit(int damage, String type);
	
	public int getX();
	
	public int getY();
	
	public int getWidth();
	
	public int getHeight();
	
	public Coordinate getCenter();
	
	public boolean canDamagePlayer();
	
	public void slow(int amount, int duration);
	
	public boolean isSlowed();
	
	public void poison(int damage, int duration);
	
	public int getValue();
	
	public boolean canGiveGold();
	
	public void giveGold();
	
	public boolean isFlying();

	public void dispose();
}
