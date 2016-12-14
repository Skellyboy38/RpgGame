package gem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.IBullet;
import tile.TileClickHandler;

public class SpecialGem extends Gem {
	
	private Animation animation;
	private int animationCounter;

	public SpecialGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler,
			Texture texture, int posX, int posY, String type, int level) {
		super(batch, renderer, stage, clickHandler, texture, posX, posY, type, level);
		
		TextureRegion[][] temp = TextureRegion.split(texture, 20, texture.getHeight());
		TextureRegion[] frames = new TextureRegion[texture.getWidth()/20];
		for(int i = 0; i < texture.getWidth()/20; i++) {
			frames[i] = temp[0][i];
		}
		this.animation =  new Animation(0.05f, frames);
		this.animationCounter = 0;
	}
	
	@Override
	public void render() {
		update();
		animationCounter += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = animation.getKeyFrame(animationCounter, true);
		batch.begin();
		batch.draw(currentFrame, posX, posY);
		for(IBullet b : getBullets()) {
			if(!b.hasArrived()) {
				batch.draw(b.getTexture(), b.getX(), b.getY());
			}
			else {
				if(b.isCrit() && critTime <= CRIT_TEXTURE_DURATION) {
					batch.draw(critTexture, b.getX(), b.getY());
					critTime += Gdx.graphics.getDeltaTime();
				}
				toRemove = b;
			}
		}
		if(toRemove != null) {
			bullets.remove(toRemove);
			toRemove = null;
		}
		batch.end();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
