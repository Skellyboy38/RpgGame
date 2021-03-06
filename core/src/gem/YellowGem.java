package gem;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.YellowBullet;
import monster.Monster;
import tile.TileClickHandler;

public class YellowGem extends Gem {

	private String name;
	private String description;

	public YellowGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY, int level, AssetManager manager) {
		super(batch, renderer, stage, clickHandler, texture, posX, posY, "yellow", level, manager);

		this.name = "Yellow gem";
		this.description = "The yellow gem hits all enemies around it, but slower.";
	}

	@Override
	public void hit(List<Monster> monsters) {
		elapsedTime = 0;
		canHit = false;
		for(Monster m : monsters) {
			if(!m.isDead()) {
				hit(m);
			}
		}
	}
	
	@Override
	public void hit(Monster m) {
		bullets.add(new YellowBullet(posX + textureRegion.getRegionWidth()/2, posY + textureRegion.getRegionHeight()/2, m, this, manager));
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
