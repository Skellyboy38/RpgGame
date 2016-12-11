package bullets;

import com.badlogic.gdx.graphics.Texture;

import gem.IGem;
import monster.IMonster;

public class YellowBullet implements IBullet {

	private Texture bullet;
	private int speed;
	private int posX;
	private int posY;
	private boolean hasArrived;
	private boolean alreadyHit;
	private IMonster toHit;
	private IGem gem;
	
	public YellowBullet(int posX, int posY, IMonster m, IGem gem) {
		bullet = new Texture("yellowBullet.png");
		this.posX = posX;
		this.posY = posY;
		this.toHit = m;
		this.gem = gem;
		hasArrived = false;
		alreadyHit = false;
		speed = 10;
	}
	
	public void update() {
		if(hasArrived && !alreadyHit) {
			toHit.hit(gem.getDamage(), gem.getType());
			alreadyHit = true;
		}
		if(posX > toHit.getCenter().getX() - 10 && posX < toHit.getCenter().getX() + 10 && posY > toHit.getCenter().getY() - 10 && posY < toHit.getCenter().getY() + 10) {
			hasArrived = true;
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
		return bullet;
	}
}
