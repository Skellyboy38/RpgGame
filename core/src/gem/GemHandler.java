package gem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import settings.Settings;
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
	private Texture enhanceSheet;
	private Animation animation;
	private float animationCounter;
	
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
		enhanceSheet = new Texture("enhance.png");
		TextureRegion[][] temp = TextureRegion.split(enhanceSheet, 20, 20);
		TextureRegion[] frames = new TextureRegion[10];
		for(int i = 0; i < 10; i++) {
			frames[i] = temp[0][i];
		}
		animation = new Animation(0.1f, frames);
		animationCounter = 0;
	}
	
	public List<IGem> getFinalizedGems() {
		return gems;
	}
	
	public List<IGem> checkCombinations() {
		List<IGem> toRet = new ArrayList<IGem>();
		for(int i = 0; i < currentGems.size(); i++) {
			for(int j = 0; j < currentGems.size(); j++) {
				if(i == j) {
					continue;
				}
				if(currentGems.get(i).getLevel() < 6 && currentGems.get(i).getType().equals(currentGems.get(j).getType()) &&
						currentGems.get(i).getLevel() == currentGems.get(j).getLevel()) {
					toRet.add(currentGems.get(i));
				}
			}
		}
		return toRet;
	}

	public void render() {
		animationCounter += Gdx.graphics.getDeltaTime();
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
		for(IGem gem : checkCombinations()) {
			batch.begin();
			TextureRegion currentFrame = animation.getKeyFrame(animationCounter, true);
			batch.draw(currentFrame, gem.getCoordinates().getX(), gem.getCoordinates().getY());
			batch.end();
		}
	}
	
	public boolean isCombination(IGem gem) {
		return checkCombinations().contains(gem);
	}
	
	public void commitGem(IGem gem, boolean upgrade) {
		if(upgrade) {
			String type = gem.getType();
			IGem newGem;
			if(type.equals("green")) {
				newGem = new GreenGem(batch, renderer, stage, clickHandler, 
						new Texture("green_"+(gem.getLevel()+1)+".png"), 
						gem.getCoordinates().getX(), gem.getCoordinates().getY(), 
						gem.getLevel()+1);
			}
			else if(type.equals("blue")) {
				newGem = new BlueGem(batch, renderer, stage, clickHandler, 
						new Texture("blue_"+(gem.getLevel()+1)+".png"), 
						gem.getCoordinates().getX(), gem.getCoordinates().getY(), 
						gem.getLevel()+1);
			}
			else if(type.equals("yellow")) {
				newGem = new YellowGem(batch, renderer, stage, clickHandler, 
						new Texture("yellow_"+(gem.getLevel()+1)+".png"), 
						gem.getCoordinates().getX(), gem.getCoordinates().getY(), 
						gem.getLevel()+1);
			}
			else if(type.equals("white")) {
				newGem = new WhiteGem(batch, renderer, stage, clickHandler, 
						new Texture("white_"+(gem.getLevel()+1)+".png"), 
						gem.getCoordinates().getX(), gem.getCoordinates().getY(), 
						gem.getLevel()+1);
			}
			else if(type.equals("pink")) {
				newGem = new PinkGem(batch, renderer, stage, clickHandler, 
						new Texture("pink_"+(gem.getLevel()+1)+".png"), 
						gem.getCoordinates().getX(), gem.getCoordinates().getY(), 
						gem.getLevel()+1);
			}
			else {
				newGem = new BlackGem(batch, renderer, stage, clickHandler, 
						new Texture("black_"+(gem.getLevel()+1)+".png"), 
						gem.getCoordinates().getX(), gem.getCoordinates().getY(), 
						gem.getLevel()+1);
			}
			newGem.setPermanent();
			gems.add(newGem);
		}
		else {
			gem.setPermanent();
			gems.add(gem);
		}
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
	
	public boolean canIncreaseGemChances() {
		return creator.canIncreaseGemChances();
	}
	
	public int getGemChancesLevel() {
		return creator.getGemChancesLevel();
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
		creator.reset();
	}
	
	public void increaseGemChances() {
		creator.increaseChances();
	}
	
	public Float[] getGemChances() {
		return creator.getGemChances();
	}
	
	public void nextStage() {
		currentGems.clear();
	}
	
	private class GemCreator {
		private Random random;
		private Float[] gemLevelChances;
		private int chancesLevel;
		
		public GemCreator() {
			this.random = new Random();
			this.chancesLevel = 1;
			this.gemLevelChances = Settings.gemChances.get(chancesLevel);
		}
		
		public Float[] getGemChances() {
			return gemLevelChances;
		}
		
		public int getGemChancesLevel() {
			return chancesLevel;
		}
		
		public void reset() {
			chancesLevel= 1;
			gemLevelChances = Settings.gemChances.get(chancesLevel);
		}
		
		public void increaseChances() {
			if(chancesLevel != 10) {
				chancesLevel++;
			}
			gemLevelChances = Settings.gemChances.get(chancesLevel);
		}
		
		public boolean canIncreaseGemChances() {
			return chancesLevel < 10;
		}
		
		public IGem createGem(int posX, int posY) {
			int type = random.nextInt(6);
			float gemLevelChance = random.nextFloat();
			int gemLevel;
			if(gemLevelChance <= gemLevelChances[0]) {
				gemLevel = 1;
			}
			else if(gemLevelChance <= gemLevelChances[0] + gemLevelChances[1]) {
				gemLevel = 2;
			}
			else if(gemLevelChance <= gemLevelChances[0] + gemLevelChances[1] + gemLevelChances[2]) {
				gemLevel = 3;
			}
			else if(gemLevelChance <= gemLevelChances[0] + gemLevelChances[1] + gemLevelChances[2] + gemLevelChances[3]) {
				gemLevel = 4;
			}
			else {
				gemLevel = 5;
			}
			
			if(type == 0) {
				return new GreenGem(batch, renderer, stage, clickHandler, new Texture("green_"+gemLevel+".png"), posX, posY, gemLevel);
			}
			else if(type == 1) {
				return new YellowGem(batch, renderer, stage, clickHandler, new Texture("yellow_"+gemLevel+".png"), posX, posY, gemLevel);
			}
			else if(type == 2) {
				return new BlueGem(batch, renderer, stage, clickHandler, new Texture("blue_"+gemLevel+".png"), posX, posY, gemLevel);
			}
			else if(type == 3) {
				return new BlackGem(batch, renderer, stage, clickHandler, new Texture("black_"+gemLevel+".png"), posX, posY, gemLevel);
			}
			else if(type == 4) {
				return new WhiteGem(batch, renderer, stage, clickHandler, new Texture("white_"+gemLevel+".png"), posX, posY, gemLevel);
			}
			else if(type == 5) {
				return new PinkGem(batch, renderer, stage, clickHandler, new Texture("pink_"+gemLevel+".png"), posX, posY, gemLevel);
			}
			else {
				return null;
			}
			
		}
	}
}
