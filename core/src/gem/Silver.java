package gem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.BlueBullet;
import monster.Monster;
import settings.Settings;
import tile.TileClickHandler;

public class Silver extends SpecialGem {
	private String name;
	private String description;
	private int slowAmount;
	private int slowDuration;

	public Silver(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler,
			int posX, int posY, String type, int level, AssetManager manager) {
		super(batch, renderer, stage, clickHandler, "silver_animation", posX, posY, type, level, manager);
		
		this.name = "Silver";
		this.description = "Silver severely slows enemies.";
		this.slowAmount = Settings.gemSettings.get("silver").get(level).slowAmount;
		this.slowDuration = Settings.gemSettings.get("silver").get(level).slowDuration;
	}
	
	@Override
	public int getSlowAmount() {
		return slowAmount;
	}
	
	@Override
	public int getSlowDuration() {
		return slowDuration;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public void hit(Monster m) {
		if(!m.isDead()) {
			elapsedTime = 0;
			canHit = false;
			bullets.add(new BlueBullet(posX + textureRegion.getRegionWidth()/2, 
					posY + textureRegion.getRegionHeight()/2, m, this, manager));
		}
	}
}
