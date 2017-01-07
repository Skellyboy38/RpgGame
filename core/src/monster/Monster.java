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
import settings.Settings;
import tile.Coordinate;
import tile.ITile;

public class Monster implements IMonster {
	
	public static final float DAMAGE_MULTIPLIER = 2f/3f;
	public static final Coordinate TYPE_LOCATION = new Coordinate(-15, 0);
	
	private Animation animation;
	private Animation poisonAnimation;
	private Animation slowAnimation;
	private Texture typeTexture;
	private Texture animationSheet;
	private TextureRegion texture;
	private SpriteBatch batch;
	private ShapeRenderer renderer;
	
	private ITile lastTile;
	private Rectangle collisionBox;
	private Rectangle hpFrame;
	private Rectangle hpFill;
	
	private int posX;
	private int posY;
	private int speed;
	private int value;
	private int tileCounter;
	private int maxHp;
	private int currentHp;
	private int slowTimer;
	private int slowDuration;
	private int poisonTimer;
	private int poisonDamageTimer;
	private int poisonDuration;
	private int poisonDamage;
	private int poisonDelay;
	private int originalSpeed;
	
	private String type;
	
	private List<ITile> path;
	
	private boolean canFly;
	private boolean canDamagePlayer;
	private boolean canGiveGold;
	private boolean isSlowed;
	private boolean isPoisoned;
	private boolean isFlying;
	
	private float animationCounter;
	
	public Monster(SpriteBatch batch, ShapeRenderer renderer) {
		this.batch = batch;
		this.renderer = renderer;
		this.path = new ArrayList<ITile>();
		
		this.poisonAnimation = createAnimation(Settings.ailmentAnimationSheets.get("poison"));
		this.slowAnimation = createAnimation(Settings.ailmentAnimationSheets.get("slow"));
		
		this.canFly = false;
		this.canDamagePlayer = true;
		this.canGiveGold = true;
		this.isSlowed = false;
		this.isPoisoned = false;
		
		this.slowTimer = 0;
		this.slowDuration = 0;
		this.poisonTimer = 0;
		this.poisonDamageTimer = 0;
		this.poisonDuration = 0;
		this.poisonDamage = 0;
		this.poisonDelay = 500;
		this.animationCounter = 0;
		this.tileCounter = -1;
	}
	
	public void setInformation(Texture texture, int posX, int posY, int speed, int maxHp, int value, String type, boolean isFlying) {
		this.animationSheet = texture;
		this.speed = speed;
		this.value = value;
		this.type = type;
		this.isFlying = isFlying;
		this.maxHp = maxHp;
		this.posX = posX;
		this.posY = posY;
		
		this.texture = new TextureRegion(texture);
		this.texture.setRegionWidth(RpgGame.TILE_WIDTH);
		this.animation = createAnimation(animationSheet);
		this.collisionBox = new Rectangle(posX, posY, this.texture.getRegionWidth(), this.texture.getRegionHeight());
		this.hpFrame = new Rectangle(posX, (int)(posY + this.texture.getRegionHeight()*1.1), this.texture.getRegionWidth(), 5);
		this.hpFill = new Rectangle(posX, (int)(posY + this.texture.getRegionHeight()*1.1), this.texture.getRegionWidth(), 5);
		this.typeTexture = Settings.elementTypes.get(type);
		this.currentHp = maxHp; 
		this.originalSpeed = speed;
	}

	public void dispose() {

	}
	
	public Animation createAnimation(Texture animationSheet) {
		TextureRegion[][] temp = TextureRegion.split(animationSheet, 20, animationSheet.getHeight());
		TextureRegion[] frames = new TextureRegion[animationSheet.getWidth()/20];
		for(int i = 0; i < animationSheet.getWidth()/20; i++) {
			frames[i] = temp[0][i];
		}
		return new Animation(0.1f, frames);
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
		return getHp() <= 0;
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
				hit(poisonDamage, type);
			}
		}
		updateCollisionBox();
		render();
	}
	
	public void kill() {
		currentHp = 0;
		canDamagePlayer = false;
	}
	
	public void render() {
		if(!isDead()) {
			animationCounter += Gdx.graphics.getDeltaTime();
			TextureRegion currentFrame;
			currentFrame = animation.getKeyFrame(animationCounter, true);
			batch.begin();
			Color c = batch.getColor();
			batch.draw(currentFrame, posX, posY);
			batch.draw(typeTexture, posX + TYPE_LOCATION.getX(), posY + texture.getRegionHeight() + TYPE_LOCATION.getY());
			if(isSlowed) {
				TextureRegion currentSlowFrame = slowAnimation.getKeyFrame(animationCounter, true);
				batch.setColor(c.r, c.g, c.b, 0.6f);
				batch.draw(currentSlowFrame, posX, posY);
			}
			if(isPoisoned) {
				TextureRegion currentSlowFrame = poisonAnimation.getKeyFrame(animationCounter, true);
				batch.setColor(c.r, c.g, c.b, 0.5f);
				batch.draw(currentSlowFrame, posX, posY);
			}
			batch.setColor(c.r, c.g, c.b, 1f);
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
	
	public boolean isFlying() {
		return isFlying;
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
	
	public void hit(int damage, String type) {
		if(type.equals(Settings.elementWeaknesses.get(this.type))) {
			currentHp -= damage*(1/DAMAGE_MULTIPLIER) == 0 ? damage : damage*(1/DAMAGE_MULTIPLIER);
		}
		else if(type.equals(Settings.elementStrengths.get(this.type))) {
			currentHp -= damage*DAMAGE_MULTIPLIER == 0 ? damage : damage*DAMAGE_MULTIPLIER;
		}
		else {
			currentHp -= damage;
		}
	}

	@Override
	public void reset() {
		this.canDamagePlayer = true;
		this.canGiveGold = true;
		this.isSlowed = false;
		this.isPoisoned = false;
		
		this.slowTimer = 0;
		this.poisonTimer = 0;
		this.poisonDamageTimer = 0;
		this.animationCounter = 0;
		this.tileCounter = -1;
	}
}
