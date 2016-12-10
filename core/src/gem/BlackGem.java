package gem;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.BlackBullet;
import monster.IMonster;
import settings.Settings;
import tile.TileClickHandler;

public class BlackGem extends Gem {

	private String name;
	private String description;
	private float increaseSpeedAmount;

	public BlackGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY, int level) {
		super(batch, renderer, stage, clickHandler, texture, posX, posY, "black", level);
		
		this.increaseSpeedAmount = Settings.gemSettings.get("black").get(level).increaseSpeedAmount + 1;
		this.name = "Black gem";
		this.description = "The black gem speeds up gems.";
	}
	
	public float getSpeedUpAmount() {
		return increaseSpeedAmount;
	}
	
	public void hit(IMonster m) {
		if(!m.isDead()) {
			elapsedTime = 0;
			canHit = false;
			bullets.add(new BlackBullet(posX + textureRegion.getRegionWidth()/2, posY + textureRegion.getRegionHeight()/2, m));
		}
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
