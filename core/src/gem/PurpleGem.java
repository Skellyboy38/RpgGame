package gem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.PurpleBullet;
import monster.Monster;
import settings.Settings;
import tile.TileClickHandler;

public class PurpleGem extends Gem {

	private String name;
	private String description;
	private float critIncreaseAmount;

	public PurpleGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY, int level, AssetManager manager) {
		super(batch, renderer, stage, clickHandler, texture, posX, posY, "purple", level, manager);
		
		this.name = "Purple gem";
		this.description = "The purple gem increases crit chance.";
		this.critIncreaseAmount = Settings.gemSettings.get("purple").get(level).critChanceIncrease;
	}
	
	@Override
	public void hit(Monster m) {
		if(!m.isDead()) {
			elapsedTime = 0;
			canHit = false;
			bullets.add(new PurpleBullet(posX + textureRegion.getRegionWidth()/2, posY + textureRegion.getRegionHeight()/2, m, this, manager));
		}
	}
	
	public float getCritAmount() {
		return critIncreaseAmount;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
