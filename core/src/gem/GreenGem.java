package gem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.GreenBullet;
import monster.Monster;
import settings.Settings;
import tile.TileClickHandler;

public class GreenGem extends Gem {
	
	private String name;
	private String description;
	private int poisonDamage;
	private int poisonDuration;

	public GreenGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY, int level, AssetManager manager) {
		super(batch, renderer, stage, clickHandler, texture, posX, posY, "green", level, manager);

		this.poisonDamage = Settings.gemSettings.get("green").get(level).poisonAmount;
		this.poisonDuration = Settings.gemSettings.get("green").get(level).poisonDuration;
		this.name = "Green gem";
		this.description = "The green gem poisons enemies.";
	}

	@Override
	public void hit(Monster m) {
		if(!m.isDead()) {
			elapsedTime = 0;
			canHit = false;
			bullets.add(new GreenBullet(posX + textureRegion.getRegionWidth()/2, posY + textureRegion.getRegionHeight()/2, m, this, manager));
		}
	}
	
	@Override
	public int getPoisonDamage() {
		return poisonDamage;
	}
	
	@Override
	public int getPoisonDuration() {
		return poisonDuration;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
