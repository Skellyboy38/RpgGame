package gem;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

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
	
	public void hit(List<IMonster> monsters);
	
	public int getDamage();
	
	public boolean canHit();
	
	public void update();
	
	public List<IBullet> getBullets();
	
	public int getSpeed();
	
	public String getName();
	
	public String getType();
	
	public int getRange();
	
	public String getDescription();
	
	public TextureRegion getTexture();
	
	public void removeListeners();
	
	public int getLevel();
	
	public void speedUp(float speed);
	
	public Rectangle getBody();
	
	public float getSpeedUpAmount();
}
