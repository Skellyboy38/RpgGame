package gem;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.GreenBullet;
import monster.IMonster;
import settings.Settings;
import tile.TileClickHandler;

public class GreenGem extends Gem {
	
	private String name;
	private String description;
	private int poisonDamage;
	private int poisonDuration;

	public GreenGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY, int level) {
		super(batch, renderer, stage, clickHandler, texture, posX, posY, "green", level);

		this.poisonDamage = Settings.gemSettings.get("green").get(level).poisonAmount;
		this.poisonDuration = Settings.gemSettings.get("green").get(level).poisonDuration;
		this.name = "Green gem";
		this.description = "The green gem poisons enemies.";
	}

	public void hit(IMonster m) {
		if(!m.isDead()) {
			elapsedTime = 0;
			canHit = false;
			bullets.add(new GreenBullet(posX + textureRegion.getRegionWidth()/2, posY + textureRegion.getRegionHeight()/2, m, this));
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

	@Override
	public void hit(List<IMonster> monsters) {
		// TODO Auto-generated method stub
	}

}
