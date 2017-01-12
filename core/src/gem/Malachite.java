package gem;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.YellowBullet;
import monster.Monster;
import tile.TileClickHandler;

public class Malachite extends SpecialGem {
	private String name;
	private String description;

	public Malachite(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, String animationName,
			int posX, int posY, String type, int level, AssetManager manager) {
		super(batch, renderer, stage, clickHandler, animationName, posX, posY, type, level, manager);
		
		this.name = "Malachite";
		this.description = "Malachite does AoE damage.";
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
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public void hit(Monster m) {
		bullets.add(new YellowBullet(posX + textureRegion.getRegionWidth()/2, 
				posY + textureRegion.getRegionHeight()/2, m, this, manager));
	}
}
