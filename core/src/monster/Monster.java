package monster;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.RpgGame;

import path.Path;
import tile.Coordinate;
import tile.ITile;

public class Monster implements IMonster {
	
	private int posX;
	private int posY;
	private int speed;
	private int hp;
	private boolean isDead;
	private Texture originalTexture;
	private TextureRegion texture;
	private SpriteBatch batch;
	private List<ITile> path;
	private int tileCounter;
	
	public Monster(SpriteBatch batch, Texture texture, int posX, int posY) {
		this.batch = batch;
		this.path = new ArrayList<ITile>();
		this.speed = 4;
		this.originalTexture = texture;
		this.texture = new TextureRegion(texture);
		this.texture.setRegionWidth(RpgGame.WIDTH/50);
		this.texture.setRegionHeight(RpgGame.HEIGHT/30);
		this.posX = posX;
		this.posY = posY;
		isDead = false;
		hp = 10;
		this.tileCounter = -1;
	}
	
	public void update() {
		if(tileCounter >= 0 && tileCounter < path.size()) {
			ITile next = path.get(tileCounter);
			if(!hasReached(next)) {
				moveTo(next.getPosition(), moveHorizontally(next.getPosition()));
			}
			else {
				tileCounter++;
			}
		}
		render();
	}
	
	public void render() {
		if(!isDead) {
			batch.draw(texture, posX, posY);
		}
	}
	
	public boolean moveHorizontally(Coordinate c) {
		return (posX > c.getX() || posX < c.getX()) && (posY == c.getY());
	}
	
	public void jump(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public void moveTo(Coordinate c, boolean moveHorizontally) {
		if(moveHorizontally) {
			if(c.getX() >= posX) {
				posX += speed;
			}
			else {
				posX -= speed;
			}
		}
		else {
			if(c.getY() >= posY) {
				posY += speed;
			}
			else {
				posY -= speed;
			}
		}
	}
	
	public boolean hasReached(ITile tile) {
		Coordinate c = tile.getPosition();
		int x = c.getX();
		int y = c.getY();
		return posX == x && posY == y;
	}
	
	public void setPath(List<Path> paths) {
		path.clear();
		for(Path p : paths) {
			for(ITile tile : p.getPath()) {
				path.add(tile);
			}
		}
		tileCounter = 0;
	}
}
