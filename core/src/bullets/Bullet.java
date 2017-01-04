package bullets;

import com.badlogic.gdx.graphics.Texture;

import gem.IGem;
import monster.IMonster;

public abstract class Bullet implements IBullet {
	
	protected Texture bulletTexture;
	protected int speed;
	protected int posX;
	protected int posY;
	protected boolean hasArrived;
	protected boolean alreadyHit;
	protected IMonster toHit;
	protected IGem gem;
	protected boolean isCrit;
	
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

	public void dispose() {
		bulletTexture.dispose();
	}
	
	public boolean isCrit() {
		return isCrit;
	}
	
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
			}
			dispose();
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
