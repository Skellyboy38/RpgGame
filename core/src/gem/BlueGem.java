package gem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.BlueBullet;
import monster.Monster;
import settings.Settings;
import tile.TileClickHandler;

public class BlueGem extends Gem {
	
	private String name;
	private String description;
	private int slowAmount;
	private int slowDuration;

	public BlueGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY, int level, AssetManager manager) {
		super(batch, renderer, stage, clickHandler, texture, posX, posY, "blue", level, manager);
		
		this.slowAmount = Settings.gemSettings.get("blue").get(level).slowAmount;
		this.slowDuration = Settings.gemSettings.get("blue").get(level).slowDuration;
		this.name = "Blue gem";
		this.description = "The blue gem slows enemies hit.";
	}
	
	@Override
	public void hit(Monster m) {
		if(!m.isDead()) {
			elapsedTime = 0;
			canHit = false;
			bullets.add(new BlueBullet(posX + textureRegion.getRegionWidth()/2, posY + textureRegion.getRegionHeight()/2, m, this, manager));
			m.slow(slowAmount, slowDuration);
		}
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int getSlowAmount() {
		return slowAmount;
	}
	
	@Override
	public int getSlowDuration() {
		return slowDuration;
	}
}
