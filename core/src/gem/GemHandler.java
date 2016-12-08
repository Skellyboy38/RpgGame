package gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import tile.TileClickHandler;

public class GemHandler {
	public static final int NUMBER_GEMS = 5;
	
	private List<IGem> gems;
	private List<Rock> rocks;
	private List<IGem> currentGems;
	private Stage stage;
	private TileClickHandler clickHandler;
	private GemCreator creator;
	private ShapeRenderer renderer;
	private TextureRegion newGem;
	
	private SpriteBatch batch;
	
	public GemHandler(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler) {
		this.clickHandler = clickHandler;
		this.renderer = renderer;
		this.batch = batch;
		this.stage = stage;
		this.creator = new GemCreator();
		gems = new ArrayList<IGem>();
		currentGems = new ArrayList<IGem>();
		rocks = new ArrayList<Rock>();
		newGem = new TextureRegion(new Texture("temporaryGem.png"));
	}
	
	public List<IGem> getFinalizedGems() {
		return gems;
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
			batch.begin();
			batch.draw(newGem, gem.getCoordinates().getX(), gem.getCoordinates().getY());
			batch.end();
		}
	}
	
	public void commitGem(IGem gem) {
		gem.setPermanent();
		gems.add(gem);
		currentGems.remove(gem);
		for(IGem g : currentGems) {
			rocks.add(new Rock(batch, stage, clickHandler, new Texture("rock.png"), g.getCoordinates().getX(), g.getCoordinates().getY()));
		}
		for(IGem g : currentGems) {
			g.removeListeners();
		}
		currentGems.clear();
	}
	
	public void addTemporaryGem(int posX, int posY) {
		IGem temporaryGem = creator.createGem(posX, posY);
		currentGems.add(temporaryGem);
	}
	
	public void replaceRock(Rock rock, int posX, int posY) {
		rock.removeListeners();
		rocks.remove(rock);
		addTemporaryGem(posX, posY);
	}
	
	public boolean isReady() {
		return currentGems.size() == 5 && clickHandler.getClickedGem() != null && clickHandler.getClickedGem().isTemporary();
	}
	
	public boolean hasFiveGems() {
		return currentGems.size() == 5;
	}
	
	public void reset() {
		for(IGem gem : gems) {
			gem.removeListeners();
		}
		gems.clear();
		for(Rock r : rocks) {
			r.removeListeners();
		}
		rocks.clear();
	}
	
	public void nextStage() {
		currentGems.clear();
	}
	
	private class GemCreator {
		private Random random;
		private Map<Integer, Float> gemLevelChances;
		
		public GemCreator() {
			this.random = new Random();
			createGemChances();
		}
		
		public void createGemChances() {
			
			gemLevelChances = new HashMap<Integer, Float>();
			gemLevelChances.put(1, 1f);
			gemLevelChances.put(2, 0f);
			gemLevelChances.put(3, 0f);
			gemLevelChances.put(4, 0f);
			gemLevelChances.put(5, 0f);
		}
		
		public IGem createGem(int posX, int posY) {
			int type = random.nextInt(3);
			int gemLevel = 1;
			for(Integer i : gemLevelChances.keySet()) {
				if(gemLevelChances.get(i) == 1) {
					break;
				}
			}
			if(type == 0) {
				return new GreenGem(batch, renderer, stage, clickHandler, new Texture("green.png"), posX, posY, gemLevel);
			}
			else if(type == 1) {
				return new YellowGem(batch, renderer, stage, clickHandler, new Texture("yellow.png"), posX, posY, gemLevel);
			}
			else if(type == 2) {
				return new BlueGem(batch, renderer, stage, clickHandler, new Texture("blue.png"), posX, posY, gemLevel);
			}
			else {
				return null;
			}
			
		}
	}
}
