package gem;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.RedBullet;
import monster.Monster;
import tile.TileClickHandler;

public class RedGem extends Gem {

	private String name;
	private String description;

	public RedGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY, int level, AssetManager manager) {
		super(batch, renderer, stage, clickHandler, texture, posX, posY, "red", level, manager);
		
		this.name = "Red gem";
		this.description = "The red gem does splash damage.";
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public void hit(Monster m, List<Monster> monsters) {
		if(!m.isDead()) {
			elapsedTime = 0;
			canHit = false;
			bullets.add(new RedBullet(posX + textureRegion.getRegionWidth()/2, posY + textureRegion.getRegionHeight()/2, m, this, monsters, manager));
		}
	}
}
