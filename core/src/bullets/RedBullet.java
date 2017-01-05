package bullets;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import gem.IGem;
import monster.IMonster;
import monster.Monster;

public class RedBullet extends Bullet {
	
	public static final int BURST_RADIUS = 60;
	
	private Circle burstCircle;
	private List<Monster> monsters;

	public RedBullet(int posX, int posY, IMonster m, IGem gem, List<Monster> monsters, AssetManager manager) {
		super(posX, posY, m, gem, manager.get("redBullet.png", Texture.class));
		
		this.burstCircle = new Circle(posX, posY, BURST_RADIUS);
		this.monsters = monsters;
	}
	
	@Override
	public void update() {
		burstCircle.x = posX;
		burstCircle.y = posY;
		if(posX > toHit.getCenter().getX() - 10 && posX < toHit.getCenter().getX() + 10 && posY > toHit.getCenter().getY() - 10 && posY < toHit.getCenter().getY() + 10) {
			hasArrived = true;
			if(!alreadyHit) {
				if(isCrit) {
					for(IMonster m : monsters) {
						if(Intersector.overlaps(burstCircle, m.getCollisionBox())) {
							m.hit((int)(gem.getDamage()*gem.getCritDamage()), gem.getType());
						}
					}
				}
				else {
					for(IMonster m : monsters) {
						if(Intersector.overlaps(burstCircle, m.getCollisionBox())) {
							m.hit(gem.getDamage(), gem.getType());
						}
					}
				}
				alreadyHit = true;
			}
			return;
		}
		if(posX < toHit.getCenter().getX()) {
			posX += speed;
		}
		else if(posX > toHit.getCenter().getX()) {
			posX -= speed;
		}
		
		if(posY < toHit.getCenter().getY()) {
			posY += speed;
		}
		else if(posY > toHit.getCenter().getY()) {
			posY -= speed;
		}
	}
}
