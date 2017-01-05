package gem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.PinkBullet;
import monster.Monster;
import tile.TileClickHandler;

public class PinkGem extends Gem {

	private String name;
	private String description;
	private float increaseSpeedAmount;

	public PinkGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY, int level, AssetManager manager) {
		super(batch, renderer, stage, clickHandler, texture, posX, posY, "pink", level, manager);
		
		this.name = "Pink gem";
		this.description = "The pink gem only attacks air units.";
	}
	
	public float getSpeedUpAmount() {
		return increaseSpeedAmount;
	}
	
	@Override
	public void hit(Monster m) {
		if(!m.isDead() && m.isFlying()) {
			elapsedTime = 0;
			canHit = false;
			bullets.add(new PinkBullet(posX + textureRegion.getRegionWidth()/2, posY + textureRegion.getRegionHeight()/2, m, this, manager));
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
