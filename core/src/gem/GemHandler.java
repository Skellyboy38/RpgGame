package gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
		}
	}
	
	public void commitGem(IGem gem) {
		gem.setPermanent();
		gems.add(gem);
		currentGems.remove(gem);
		for(IGem g : currentGems) {
			rocks.add(new Rock(batch, stage, clickHandler, new Texture("rock.png"), g.getCoordinates().getX(), g.getCoordinates().getY()));
		}
		currentGems.clear();
	}
	
	public void addTemporaryGem(int posX, int posY) {
		if(currentGems.size() == 5) {
			return;
		}
		else {
			IGem temporaryGem = creator.createGem(posX, posY);
			currentGems.add(temporaryGem);
		}
	}
	
	public boolean isReady() {
		return currentGems.size() == 5 && clickHandler.getClickedGem() != null && clickHandler.getClickedGem().isTemporary();
	}
	
	public void reset() {
		currentGems.clear();
	}
	
	private class GemCreator {
		private Random random;
		private Map<String, Float> gemTypeChances;
		private Map<Integer, Float> gemLevelChances;
		
		public GemCreator() {
			this.random = new Random();
			createGemChances();
		}
		
		public void createGemChances() {
			gemTypeChances = new HashMap<String, Float>();
			gemTypeChances.put("green", 1f);
			gemTypeChances.put("red", 0f);
			gemTypeChances.put("blue", 0f);
			gemTypeChances.put("black", 0f);
			gemTypeChances.put("yellow", 0f);
			gemTypeChances.put("pink", 0f);
			gemTypeChances.put("purple", 0f);
			
			gemLevelChances = new HashMap<Integer, Float>();
			gemLevelChances.put(1, 1f);
			gemLevelChances.put(2, 0f);
			gemLevelChances.put(3, 0f);
			gemLevelChances.put(4, 0f);
			gemLevelChances.put(5, 0f);
		}
		
		public IGem createGem(int posX, int posY) {
			String gemType = "green"; //To be changed later
			int gemLevel = 1;
			for(Integer i : gemLevelChances.keySet()) {
				if(gemLevelChances.get(i) == 1) {
					break;
				}
			}
			if(gemType.equals("green")) {
				return new GreenGem(batch, renderer, stage, clickHandler, new Texture(gemType + ".png"), posX, posY);
			}
			else {
				return null;
			}
			
		}
	}
}
