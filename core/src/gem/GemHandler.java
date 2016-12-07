package gem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GemHandler {
	public static final int NUMBER_GEMS = 5;
	
	private List<IGem> gems;
	private List<Rock> rocks;
	private List<IGem> currentGems;
	private Random random;
	private Stage stage;
	
	private static final String[] GEM_SKINS = {"yellow", "red", "green", "blue", "black", "pink", "purple"};
	
	private SpriteBatch batch;
	private boolean isReady;
	
	public GemHandler(SpriteBatch batch, Stage stage) {
		this.batch = batch;
		this.stage = stage;
		this.random = new Random();
		gems = new ArrayList<IGem>();
		currentGems = new ArrayList<IGem>();
		rocks = new ArrayList<Rock>();
		isReady = false;
	}

	public void render() {
		for(Rock r : rocks) {
			r.render();
		}
		for(IGem gem : gems) {
			gem.render();
		}
		for(IGem gem : currentGems) {
			gem.render();
		}
	}
	
	public void createRock() {
		Rock r = new Rock();
		rocks.add(r);
	}
	
	public void commitGem(IGem gem) {
		gems.add(gem);
		currentGems.clear();
		isReady = false;
	}
	
	public void addTemporaryGem(int posX, int posY) {
		if(currentGems.size() == 5) {
			return;
		}
		else {
			IGem temporaryGem = new Gem(batch, stage, getGemTexture(), posX, posY);
			currentGems.add(temporaryGem);
			if(currentGems.size() == 5) {
				isReady = true;
			}
		}
	}
	
	public Texture getGemTexture() {
		int randomNumber = random.nextInt(7);
		return new Texture(GEM_SKINS[randomNumber] + ".png");
	}
	
	public boolean isReady() {
		return isReady;
	}
}
