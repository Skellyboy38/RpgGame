package monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MonsterLevel1 extends Monster {
	
	private int maxHp;
	private boolean canFly;
	private int currentHp;

	public MonsterLevel1(SpriteBatch batch, ShapeRenderer renderer, Texture texture, int posX, int posY) {
		super(batch, renderer, texture, posX, posY);
		canFly = false;
		maxHp = 5;
		currentHp = maxHp; 
	}
	
	public int getHp() {
		return currentHp;
	}
	
	public boolean canFly() {
		return canFly;
	}

	@Override
	public int getMaxHp() {
		return maxHp;
	}
	
	public void hit(int damage) {
		currentHp -= damage;
	}

}
