package monster;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.RpgGame;

import path.Path;
import tile.Coordinate;
import tile.ITile;

public class Monster implements IMonster {
	
	private int posX;
	private int posY;
	private int speed;
	private int value;
	private boolean isDead;
	private Texture animationSheet;
	private TextureRegion texture;
	private SpriteBatch batch;
	private ShapeRenderer renderer;
	private List<ITile> path;
	private int tileCounter;
	private ITile lastTile;
	private Rectangle collisionBox;
	private Rectangle hpFrame;
	private Rectangle hpFill;
	private int maxHp;
	private boolean canFly;
	private int currentHp;
	private boolean canDamagePlayer;
	private boolean canGiveGold;
	private int originalSpeed;
	private boolean isSlowed;
	private boolean isPoisoned;
	private int slowTimer;
	private int slowDuration;
	private int poisonTimer;
	private int poisonDamageTimer;
	private int poisonDuration;
	private int poisonDamage;
	private int poisonDelay;
	private Animation animation;
	private float animationCounter;
	
	public Monster(SpriteBatch batch, ShapeRenderer renderer, Texture texture, int posX, int posY, int speed, int maxHp, int value) {
		this.batch = batch;
		this.renderer = renderer;
		this.path = new ArrayList<ITile>();
		this.speed = speed;
		this.value = value;
		this.animationSheet = texture;
		TextureRegion[][] temp = TextureRegion.split(animationSheet, 20, 20);
		TextureRegion[] frames = new TextureRegion[6];
		for(int i = 0; i < 6; i++) {
			frames[i] = temp[0][i];
		}
		animation = new Animation(0.1f, frames);
		this.texture = new TextureRegion(texture);
		this.texture.setRegionWidth(RpgGame.WIDTH/50);
		this.texture.setRegionHeight(RpgGame.HEIGHT/30);
		this.collisionBox = new Rectangle(posX, posY, this.texture.getRegionWidth(), this.texture.getRegionHeight());
		this.hpFrame = new Rectangle(posX, (int)(posY + this.texture.getRegionHeight()*1.1), this.texture.getRegionWidth(), this.texture.getRegionHeight()*0.4f);
		this.hpFill = new Rectangle(posX, (int)(posY + this.texture.getRegionHeight()*1.1), this.texture.getRegionWidth(), this.texture.getRegionHeight()*0.4f);
		this.posX = posX;
		this.posY = posY;
		isDead = false;
		this.tileCounter = -1;
		canFly = false;
		canDamagePlayer = true;
		canGiveGold = true;
		this.maxHp = maxHp;
		this.currentHp = maxHp; 
		this.originalSpeed = speed;
		this.isSlowed = false;
		this.slowTimer = 0;
		this.slowDuration = 0;
		this.isPoisoned = false;
		this.poisonTimer = 0;
		this.poisonDamageTimer = 0;
		this.poisonDuration = 0;
		this.poisonDamage = 0;
		this.poisonDelay = 500;
		this.animationCounter = 0;
	}
	
	public void updateCollisionBox() {
		collisionBox.setPosition(posX, posY);
		hpFrame.setPosition(posX, (int)(posY + texture.getRegionHeight()*1.1));
		hpFill.setPosition(posX, (int)(posY + texture.getRegionHeight()*1.1));
	}
	
	public void slow(int amount, int duration) {
		if(!isSlowed) {
			speed -= amount;
			isSlowed = true;
		}
		slowTimer = 0;
		slowDuration = duration;
	}
	
	public int getValue() {
		return value;
	}
	
	public void poison(int amount, int duration) {
		isPoisoned = true;
		poisonDamage = amount;
		poisonDuration = duration;
	}
	
	public Rectangle getCollisionBox() {
		return collisionBox;
	}
	
	public boolean canDamagePlayer() {
		return canDamagePlayer;
	}
	
	public void giveGold() {
		canGiveGold = false;
	}
	
	public boolean canGiveGold() {
		return canGiveGold;
	}
	
	public boolean isDead() {
		return getHp() <= 0 || isDead;
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
		if(isSlowed) {
			slowTimer += Gdx.graphics.getDeltaTime()*1000;
			if((slowTimer/slowDuration) >= 1) {
				isSlowed = false;
				speed = originalSpeed;
			}
		}
		if(isPoisoned) {
			poisonTimer += Gdx.graphics.getDeltaTime()*1000;
			poisonDamageTimer += Gdx.graphics.getDeltaTime()*1000;
			if((poisonTimer/poisonDuration) >= 1) {
				isPoisoned = false;
				poisonTimer = 0;
				poisonDamageTimer = 0;
			}
			if((poisonDamageTimer/poisonDelay) >= 1) {
				poisonDamageTimer = 0;
				hit(poisonDamage);
			}
		}
		updateCollisionBox();
		render();
	}
	
	public void kill() {
		isDead = true;
		canDamagePlayer = false;
		animationSheet.dispose();
	}
	
	public void render() {
		if(!isDead) {
			animationCounter += Gdx.graphics.getDeltaTime();
			TextureRegion currentFrame;
			currentFrame = animation.getKeyFrame(animationCounter, true);
			batch.begin();
			batch.draw(currentFrame, posX, posY);
			batch.end();
			renderer.begin(ShapeType.Filled);
			renderer.setColor(getHpColor());
			renderer.rect(hpFill.getX(), hpFill.getY(), getLengthOfHpBar(), hpFill.getHeight());
			renderer.end();
			renderer.begin(ShapeType.Line);
			renderer.setColor(Color.BLACK);
			renderer.rect(hpFrame.getX(), hpFrame.getY(), hpFrame.getWidth(), hpFrame.getHeight());
			renderer.end();
		}
	}
	
	public Color getHpColor() {
		if((float)getHp()/getMaxHp() > 0.8) {
			return Color.GREEN;
		}
		else if((float)getHp()/getMaxHp() >= 0.4) {
			return Color.YELLOW;
		}
		else {
			return Color.RED;
		}
	}
	
	public int getLengthOfHpBar() {
		if(((float)getHp()/getMaxHp()) >= 0) {
			return (int)(hpFrame.getWidth()*((float)getHp()/getMaxHp()));
		}
		else {
			return 0;
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
	
	public boolean isSlowed() {
		return isSlowed;
	}
	
	// THIS METHOD MIGHT BE BUGGY - TO TEST
	public boolean hasReached(ITile tile) {
		Coordinate c = tile.getPosition();
		int x = c.getX();
		int y = c.getY();
		if((posX <= x + 2 && posX >= x - 2) && (posY <= y + 2 && posY >= y - 2)) {
			posX = x;
			posY = y;
		}
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
		Path lastPath = paths.get(paths.size() - 1);
		List<ITile> tilesForLastPath = lastPath.getPath();
		lastTile = tilesForLastPath.get(tilesForLastPath.size() - 1);
	}
	
	public boolean donePath() {
		if(this.hasReached(lastTile)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
	public int getWidth() {
		return texture.getRegionWidth();
	}
	
	public int getHeight() {
		return texture.getRegionHeight();
	}
	
	public Coordinate getCenter() {
		return new Coordinate(posX + texture.getRegionWidth()/2, posY + texture.getRegionHeight()/2);
	}
	
	public int getHp() {
		return currentHp;
	}
	
	public boolean canFly() {
		return canFly;
	}

	@Override
	public int getMaxHp() {
		return maxHp;
	}
	
	public void hit(int damage) {
		currentHp -= damage;
	}
}
