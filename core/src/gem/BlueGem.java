package gem;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.BlueBullet;
import monster.IMonster;
import settings.Settings;
import tile.TileClickHandler;

public class BlueGem extends Gem {
	
	private String name;
	private String description;
	private int slowAmount;
	private int slowDuration;

	public BlueGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY, int level) {
		super(batch, renderer, stage, clickHandler, texture, posX, posY, "blue", level);
		
		this.slowAmount = Settings.gemSettings.get("blue").get(level).slowAmount;
		this.slowDuration = Settings.gemSettings.get("blue").get(level).slowDuration;
		this.name = "Blue gem";
		this.description = "The blue gem slows enemies hit.";
	}
	
	public void hit(IMonster m) {
		if(!m.isDead()) {
			elapsedTime = 0;
			canHit = false;
			bullets.add(new BlueBullet(posX + textureRegion.getRegionWidth()/2, posY + textureRegion.getRegionHeight()/2, m, this));
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
	public void hit(List<IMonster> monsters) {
		// TODO Auto-generated method stub
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
