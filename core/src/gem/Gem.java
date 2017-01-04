package gem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.RpgGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bullets.IBullet;
import monster.Monster;
import settings.Settings;
import tile.Coordinate;
import tile.TileClickHandler;

public abstract class Gem implements IGem {
	
	public static final float CRIT_CHANCE = 0.1f;
	public static final int CRIT_TEXTURE_DURATION = 500;
	
	protected SpriteBatch batch;
	private ShapeRenderer renderer;
	private Circle collisionBox;
	private Rectangle body;
	private TileClickHandler clickHandler;
	private TextButton button;
	private IGem instance;
	protected IBullet toRemove;
	private Coordinate coordinates;
	protected List<IBullet> bullets;
	protected TextureRegion textureRegion;
	protected Texture critTexture;
	private Random random;
	
	protected int elapsedTime;
	protected int critTime;
	protected int range;
	protected int delay;
	protected int originalDelay;
	protected int damage;
	protected int posX;
	protected int posY;
	protected int level;
	private float critMultiplier;
	protected float currentCritChance;
	
	private boolean isSpedUp;
	private boolean isCritUp;
	private boolean isTemporary;
	protected boolean canHit;
	
	private String type;

	public Gem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY, String type, int level) {
		this.range = Settings.gemSettings.get(type).get(level).range;
		this.damage = Settings.gemSettings.get(type).get(level).damage;
		this.originalDelay = Settings.gemSettings.get(type).get(level).delay;
		this.delay = originalDelay;
		this.batch = batch;
		this.clickHandler = clickHandler;
		this.textureRegion = new TextureRegion(texture);
		this.critTexture = new Texture("crit.png");
		this.bullets = new ArrayList<IBullet>();
		this.renderer = renderer;
		this.body = new Rectangle();
		this.random = new Random();
		body.setWidth(20);
		body.setHeight(20);
		body.setX(posX);
		body.setY(posY);
		this.collisionBox = new Circle();
		collisionBox.set(posX, posY, range);
		textureRegion.setRegionWidth(RpgGame.TILE_WIDTH);
		textureRegion.setRegionHeight(RpgGame.TILE_WIDTH);
		this.type = type;
		this.posX = posX;
		this.posY = posY;
		this.critMultiplier = 3f;
		this.currentCritChance = CRIT_CHANCE;
		createButton();
		this.coordinates = new Coordinate(posX, posY);
		this.isTemporary = true;
		this.instance = this;
		this.elapsedTime = 0;
		this.critTime = 0;
		this.canHit = true;
		this.isSpedUp = false;
		this.isCritUp = false;
		this.level = level;
		
		stage.addActor(button);
	}

	public void dispose() {
		critTexture.dispose();
		for(IBullet b : bullets) {
			b.dispose();
		}
	}
	
	public float getCritDamage() {
		return critMultiplier;
	}
	
	public float getCritChance() {
		return currentCritChance;
	}
	
	public void update() {
		elapsedTime += Gdx.graphics.getDeltaTime()*1000;
		if((elapsedTime/delay) >= 1) {
			canHit = true;
		}
		for(IBullet b : bullets) {
			b.update();
		}
	}
	
	public int getPoisonDamage() {
		return 0;
	}
	
	public int getPoisonDuration() {
		return 0;
	}
	
	public int getSlowAmount() {
		return 0;
	}
	
	public int getSlowDuration() {
		return 0;
	}
	
	public void drawCollisionBox() {
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.RED);
		renderer.circle(collisionBox.x + RpgGame.TILE_WIDTH/2, collisionBox.y + RpgGame.TILE_WIDTH/2, collisionBox.radius);
		renderer.end();
	}
	
	public boolean isCrit() {
		return random.nextFloat() <= CRIT_CHANCE;
	}
	
	public float getSpeedUpAmount() {
		return 0f;
	}
	
	public float getCritAmount() {
		return 0f;
	}
	
	public List<IBullet> getBullets() {
		return bullets;
	}
	
	public void speedUp(float amount) {
		if(!isSpedUp) {
			isSpedUp = true;
			delay = (int)(originalDelay/amount);
		}
		else if((int)(originalDelay/amount) < delay) {
			delay = (int)(originalDelay/amount);
		}
	}
	
	public void increaseCrit(float amount) {
		if(!isCritUp) {
			isCritUp = true;
			currentCritChance = CRIT_CHANCE + amount;
		}
		else if(CRIT_CHANCE + amount > currentCritChance) {
			currentCritChance = CRIT_CHANCE + amount;
		}
	}
	
	public Rectangle getBody() {
		return body;
	}
	
	public TextureRegion getTexture() {
		return textureRegion;
	}
	
	public void removeListeners() {
		button.remove();
	}

	public void createButton() {
		button = new TextButton("", RpgGame.BUTTON_STYLE);
		button.setHeight(textureRegion.getRegionHeight());
		button.setWidth(textureRegion.getRegionWidth());
		button.setPosition(posX, posY);

		button.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				clickHandler.clickGem(instance);
				return false;
			}
		});
	}
	
	public boolean isTemporary() {
		return isTemporary;
	}

	public Coordinate getCoordinates() {
		return coordinates;
	}
	
	public boolean allBulletsArrived() {
		boolean toRet = true;
		for(IBullet b : bullets) {
			if(!b.hasArrived()) {
				toRet = false;
			}
		}
		return toRet;
	}

	@Override
	public void render() {
		update();
		batch.begin();
		batch.draw(textureRegion, posX, posY);
		for(IBullet b : getBullets()) {
			if(!b.hasArrived()) {
				batch.draw(b.getTexture(), b.getX(), b.getY());
			}
			else {
				if(b.isCrit() && critTime <= CRIT_TEXTURE_DURATION) {
					batch.draw(critTexture, b.getX(), b.getY());
					critTime += Gdx.graphics.getDeltaTime();
				}
				toRemove = b;
			}
		}
		if(toRemove != null) {
			bullets.remove(toRemove);
			toRemove = null;
		}
		batch.end();
	}
	
	public void setPermanent() {
		isTemporary = false;
	}

	public Circle getCollisionBox() {
		return collisionBox;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public boolean canHit() {
		return canHit;
	}
	
	public int getSpeed() {
		return delay;
	}
	
	public String getType() {
		return type;
	}
	
	public int getRange() {
		return range;
	}
	
	public int getLevel() {
		return level;
	}
	
	@Override
	public void hit(Monster m) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void hit(List<Monster> monsters) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void hit(Monster m, List<Monster> monsters) {
		// TODO Auto-generated method stub
	}
}
