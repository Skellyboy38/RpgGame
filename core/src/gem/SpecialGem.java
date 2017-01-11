package gem;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.IBullet;
import settings.Settings;
import tile.TileClickHandler;

public class SpecialGem extends Gem {
	
	private Animation animation;
	private float animationCounter;
	private List<Integer> upgradePrices;

	public SpecialGem(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler,
			Texture texture, int posX, int posY, String type, int level, AssetManager manager) {
		super(batch, renderer, stage, clickHandler, texture, posX, posY, type, level, manager);
		
		this.animation =  createAnimation(texture);
		this.animationCounter = 0;
		this.upgradePrices = Settings.specialGemUpgradePrices.get(type);
	}
	
	public Animation createAnimation(Texture animationSheet) {
		TextureRegion[][] temp = TextureRegion.split(animationSheet, 20, animationSheet.getHeight());
		TextureRegion[] frames = new TextureRegion[animationSheet.getWidth()/20];
		for(int i = 0; i < animationSheet.getWidth()/20; i++) {
			frames[i] = temp[0][i];
		}
		return new Animation(0.1f, frames);
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
	
	public int getUpgradePrice() {
		return level < 3 ? upgradePrices.get(level - 1) : 9999;
	}
	
	public void upgrade() {
		if(level < 3) {
			level++;
			this.range = Settings.gemSettings.get(type).get(level).range;
			this.damage = Settings.gemSettings.get(type).get(level).damage;
			this.originalDelay = Settings.gemSettings.get(type).get(level).delay;
		}
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
