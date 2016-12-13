package bullets;

import com.badlogic.gdx.graphics.Texture;

import gem.IGem;
import monster.IMonster;

public abstract class Bullet implements IBullet {
	
	private Texture bulletTexture;
	private int speed;
	private int posX;
	private int posY;
	private boolean hasArrived;
	private boolean alreadyHit;
	private IMonster toHit;
	private IGem gem;
	private boolean isCrit;
	
	public Bullet(int posX, int posY, IMonster m, IGem gem, Texture texture) {
		this.bulletTexture = texture;
		this.posX = posX;
		this.posY = posY;
		this.toHit = m;
		this.gem = gem;
		hasArrived = false;
		alreadyHit = false;
		this.isCrit = gem.isCrit();
		speed = 10;
	}
	
	public boolean isCrit() {
		return isCrit;
	}
	
	public void update() {
		if(posX > toHit.getCenter().getX() - 10 && posX < toHit.getCenter().getX() + 10 && posY > toHit.getCenter().getY() - 10 && posY < toHit.getCenter().getY() + 10) {
			hasArrived = true;
			if(!alreadyHit) {
				if(isCrit) {
					toHit.hit((int)(gem.getDamage()*gem.getCrit()), gem.getType());
				}
				else {
					toHit.hit(gem.getDamage(), gem.getType());
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
	
	public boolean hasArrived() {
		return hasArrived;
	}

	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
	public Texture getTexture() {
		return bulletTexture;
	}

}
