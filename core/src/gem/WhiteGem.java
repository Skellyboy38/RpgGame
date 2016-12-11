package gem;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.WhiteBullet;
import monster.IMonster;
import tile.TileClickHandler;

public class WhiteGem extends Gem {
	
	private String name;
	private String description;
	private float increaseSpeedAmount;

	public WhiteGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY, int level) {
		super(batch, renderer, stage, clickHandler, texture, posX, posY, "white", level);
		
		this.name = "White gem";
		this.description = "The white gem only attacks ground units.";
	}
	
	public float getSpeedUpAmount() {
		return increaseSpeedAmount;
	}
	
	public void hit(IMonster m) {
		if(!m.isDead() && !m.isFlying()) {
			elapsedTime = 0;
			canHit = false;
			bullets.add(new WhiteBullet(posX + textureRegion.getRegionWidth()/2, posY + textureRegion.getRegionHeight()/2, m, this));
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
