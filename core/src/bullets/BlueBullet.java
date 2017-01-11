package bullets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import gem.IGem;
import monster.IMonster;

public class BlueBullet extends Bullet {
	
	public BlueBullet(int posX, int posY, IMonster m, IGem gem, AssetManager manager) {
		super(posX, posY, m, gem, manager.get("blueBullet.png", Texture.class));
	}
	
	@Override
	public void update() {
		if(posX > toHit.getCenter().getX() - 10 && posX < toHit.getCenter().getX() + 10 && posY > toHit.getCenter().getY() - 10 && posY < toHit.getCenter().getY() + 10) {
			hasArrived = true;
			if(!alreadyHit) {
				if(isCrit) {
					toHit.hit((int)(gem.getDamage()*gem.getCritDamage()), gem.getType());
				}
				else {
					toHit.hit(gem.getDamage(), gem.getType());
				}
				alreadyHit = true;
				toHit.slow(gem.getSlowAmount(), gem.getSlowDuration());
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
