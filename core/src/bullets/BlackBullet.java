package bullets;

import com.badlogic.gdx.graphics.Texture;

import monster.IMonster;

public class BlackBullet implements IBullet {
	private Texture bullet;
	private int speed;
	private int posX;
	private int posY;
	private boolean hasArrived;
	private IMonster toHit;
	
	public BlackBullet(int posX, int posY, IMonster m) {
		bullet = new Texture("blackBullet.png");
		this.posX = posX;
		this.posY = posY;
		this.toHit = m;
		hasArrived = false;
		speed = 10;
	}
	
	public void update() {
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
