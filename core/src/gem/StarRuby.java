package gem;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import monster.Monster;
import tile.TileClickHandler;

public class StarRuby extends SpecialGem {
	private String name;
	private String description;

	public StarRuby(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, String animationName,
			int posX, int posY, String type, int level, AssetManager manager) {
		super(batch, renderer, stage, clickHandler, animationName, posX, posY, type, level, manager);
		
		this.name = "Star Ruby";
		this.description = "Star Ruby burns all enemies around it.";
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
		m.blaze();
		m.hit(damage, getType());
	}
}
