package gem;

import java.util.List;

import com.badlogic.gdx.math.Circle;

import bullets.IBullet;
import monster.IMonster;
import tile.Coordinate;

public interface IGem {
	
	public void render();
	
	public Coordinate getCoordinates();
	
	public void setPermanent();
	
	public void drawCollisionBox();
	
	public boolean isTemporary();
	
	public Circle getCollisionBox();
	
	public void hit(IMonster m);
	
	public int getDamage();
	
	public boolean canHit();
	
	public void update();
	
	public List<IBullet> getBullets();
}
