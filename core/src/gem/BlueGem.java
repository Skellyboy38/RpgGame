package gem;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.IBullet;
import bullets.BlueBullet;
import monster.IMonster;
import settings.Settings;
import tile.TileClickHandler;

public class BlueGem extends Gem {
	private Circle collisionBox;
	private boolean canHit;
	private int damage;
	private int delay; // delay between hits in milliseconds
	private ShapeRenderer renderer;
	private int elapsedTime;
	private int range;
	private String name;
	private String type;
	private String description;
	private List<IBullet> bullets;

	public BlueGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY, int level) {
		super(batch, stage, clickHandler, texture, posX, posY);
		this.renderer = renderer;
		bullets = new ArrayList<IBullet>();
		collisionBox = new Circle();
		range = Settings.gemSettings.get("blue").get(level).range;
		collisionBox.set(posX, posY, range);
		canHit = true;
		damage = Settings.gemSettings.get("blue").get(level).damage;
		elapsedTime = 0;
		delay = Settings.gemSettings.get("blue").get(level).delay;
		name = "Blue gem";
		type = "blue";
		description = "The blue gem slows enemies hit.";
	}
	
	public List<IBullet> getBullets() {
		return bullets;
	}
	
	public void hit(IMonster m) {
		if(!m.isDead()) {
			elapsedTime = 0;
			canHit = false;
			bullets.add(new BlueBullet(posX + texture.getRegionWidth()/2, posY + texture.getRegionHeight()/2, m));
			m.slow(1, 1000);
		}
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
	
	public void drawCollisionBox() {
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.RED);
		renderer.circle(collisionBox.x + GEM_WIDTH/2, collisionBox.y + GEM_HEIGHT/2, collisionBox.radius);
		renderer.end();
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
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public int getRange() {
		return range;
	}
	
	public String getDescription() {
		return description;
	}
}
