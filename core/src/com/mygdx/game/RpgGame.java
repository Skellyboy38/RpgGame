package com.mygdx.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;

import collision.CollisionDetector;
import gem.GemHandler;
import gem.Rock;
import monster.Summoner;
import overlay.Overlay;
import player.Player;
import settings.Settings;
import tile.Coordinate;
import tile.ITile;
import tile.Tile;
import tile.TileClickHandler;

public class RpgGame extends ApplicationAdapter {

	public static int WIDTH = 1000;
	public static int HEIGHT = 600;
	public static int PLAYER_HP = 20;
	public static int NUMBER_ASSETS = 89;
	public static final float ZOOM_FACTOR = 0.05f;
	public static final int TILE_WIDTH = 20;
	public static TextButtonStyle BUTTON_STYLE = new TextButtonStyle();

	SpriteBatch batch;
	AssetManager manager;
	BitmapFont font;
	NumberFormat format;
	Rectangle loadFrame;
	Rectangle loadFill;
	ShapeRenderer renderer;
	Summoner summoner;
	CollisionDetector collisionDetector;
	List<ITile> tiles;
	List<ITile> checkpoints; // The order of checkpoints from start to finish: 5, 6, 2, 3, 4, 8, 7, 0, 1
	Stage stage;
	Map<Coordinate, ITile> tileMap;
	Coordinate[][] coordinates;
	TileClickHandler clickHandler;
	Overlay overlay;
	GemHandler gemHandler;
	OrthographicCamera cam;
	FitViewport viewport;
	InputProcessor zoom;
	InputMultiplexer inputMultiplexer;
	Player player;
	Settings gameSettings;
	float effectiveViewportWidth;
	float effectiveViewportHeight;
	Texture background;
	Texture loading;
	Texture pausedOverlay;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int screenWidth;
	int screenHeight;
	boolean createHandlers;
	boolean isPaused;
	boolean isPPressed;
	boolean isEnterPressed;
	boolean isCPressed;
	boolean isDPressed;

	@Override
	public void create () {
		BUTTON_STYLE.font = new BitmapFont();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(2f);
		format = new DecimalFormat("#0");
		screenWidth = (int)screenSize.getWidth();
		screenHeight = (int)screenSize.getHeight();
		effectiveViewportWidth = WIDTH;
		effectiveViewportHeight = HEIGHT;
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		cam.position.set(cam.viewportWidth/2f, cam.viewportHeight/2f, 0);
		cam.update();
		viewport = new FitViewport(WIDTH, HEIGHT, cam);
		batch = new SpriteBatch();
		manager = new AssetManager();
		renderer = new ShapeRenderer();
		loading = new Texture("loading.png");
		createHandlers = true;
		
		stage = new Stage(viewport, batch);
		zoom = new InputCore();
		inputMultiplexer = new InputMultiplexer();
		tiles = new ArrayList<ITile>();
		checkpoints = new ArrayList<ITile>();
		tileMap = new HashMap<Coordinate, ITile>();
		isPaused = false;
		isPPressed = false;
		isEnterPressed = false;
		isCPressed = false;
		isDPressed = false;
		
		loadFill = new Rectangle(270, 230, 300, 20);
		loadFrame = new Rectangle(270, 230, 300, 20);
		loadAssets();
	}

	@Override
	public void render () {
		if(manager.update()) {
			//Do only once when all textures are loaded
			if(createHandlers) {
				background = manager.get("background.png", Texture.class);
				pausedOverlay = manager.get("paused.png", Texture.class);
				gameSettings = new Settings(manager);
				player = new Player(PLAYER_HP);
				summoner = new Summoner(batch, renderer, checkpoints, player, manager);
				createCoordinates();
				clickHandler = new TileClickHandler(tileMap, coordinates, batch, summoner, manager);
				createTiles();
				gemHandler = new GemHandler(batch, renderer, stage, clickHandler, manager, summoner);
				collisionDetector = new CollisionDetector(gemHandler.getFinalizedGems(), summoner.getMonsters());
				overlay = new Overlay(clickHandler, summoner, player, gemHandler, manager);
				inputMultiplexer.addProcessor(overlay.getStage());
				inputMultiplexer.addProcessor(stage);
				inputMultiplexer.addProcessor(zoom);
				Gdx.input.setInputProcessor(inputMultiplexer);
				summoner.getFlyingPaths();
				
				createHandlers = false;
			}
			//The main game loop
			if(!player.isDead()) {
				if(!isPaused) {
					updateEffectiveViewport();
					Gdx.gl.glClearColor(1, 1, 1, 1);
					Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
					cam.update();
					batch.setProjectionMatrix(cam.combined);
					renderer.setProjectionMatrix(cam.combined);
					batch.begin();
					batch.draw(background, -500, -300);
					batch.end();
					stage.act();
					renderTiles();
					gemHandler.render();
					clickHandler.render();
					summoner.render();
					overlay.render();
					collisionDetector.detectCollisions();
					checkInputs();
				}
				else {
					batch.begin();
					batch.draw(pausedOverlay, 0 ,0);
					batch.end();
				}
				checkPause();
			}
			else {
				restart();
			}
		}
		else {
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			batch.draw(loading, 0, 0);
			font.draw(batch, format.format(manager.getProgress()*100) + "%", 600, 250);
			batch.end();
			renderer.begin(ShapeType.Filled);
			renderer.setColor(Color.GREEN);
			renderer.rect(loadFill.getX(), loadFill.getY(), 300*(manager.getProgress()), loadFill.getHeight());
			renderer.end();
			renderer.begin(ShapeType.Line);
			renderer.setColor(Color.WHITE);
			renderer.rect(loadFrame.getX(), loadFrame.getY(), loadFrame.getWidth(), loadFrame.getHeight());
			renderer.setColor(Color.BLACK);
			renderer.end();
		}
	}

	public void restart() {
		resetTiles();
		player.reset();
		clickHandler.reset();
		gemHandler.reset();
		summoner.reset();
		overlay.reset();
	}

	public void resetTiles() {
		for(int y = 0; y < 30; y++) {
			for(int x = 0; x < 50; x++) {
				tileMap.get(coordinates[x][y]).restart();
			}
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public void updateEffectiveViewport() {
		effectiveViewportWidth = WIDTH*cam.zoom;
		effectiveViewportHeight = HEIGHT*cam.zoom;
	}
	
	public void checkPause() {
		if(Gdx.input.isKeyPressed(44) && !isPPressed) {
			isPaused = !isPaused;
			Gdx.graphics.setContinuousRendering(!isPaused);
			isPPressed = true;
		}
		if(!Gdx.input.isKeyPressed(44)) {
			isPPressed = false;
		}
	}

	public void checkInputs() {
		if(Gdx.input.isKeyPressed(66) && gemHandler.isReady() && !isEnterPressed) {
			gemHandler.commitGem(clickHandler.getClickedGem(), false);
			clickHandler.unclickGem();
			gemHandler.nextStage();
			summoner.nextStage();
			summoner.start();
			isEnterPressed = true;
		}
		if(Gdx.input.isKeyPressed(32) && gemHandler.isReady() && gemHandler.isGemAboveLevel1() && !isDPressed) {
			gemHandler.commitDowngradedGem(clickHandler.getClickedGem());
			clickHandler.unclickGem();
			gemHandler.nextStage();
			summoner.nextStage();
			summoner.start();
			isDPressed = true;
		}
		if(Gdx.input.isKeyPressed(31) && gemHandler.isReady() && gemHandler.isCombination(clickHandler.getClickedGem()) && !isCPressed) {
			gemHandler.commitGem(clickHandler.getClickedGem(), true);
			clickHandler.unclickGem();
			gemHandler.nextStage();
			summoner.nextStage();
			summoner.start();
			isCPressed = true;
		}
		if(!Gdx.input.isKeyPressed(66)) {
			isEnterPressed = false;
		}
		if(!Gdx.input.isKeyPressed(31)) {
			isCPressed = false;
		}
		if(!Gdx.input.isKeyPressed(32)) {
			isDPressed = false;
		}
		if(Gdx.input.isKeyPressed(62) && (clickHandler.getClickedTile() != null || clickHandler.getClickedRock() != null) && !gemHandler.hasFiveGems() && !summoner.isSummoning()) {
			if(clickHandler.getClickedTile() != null) {
				clickHandler.getClickedTile().occupy();
				if(summoner.doesPathExist()) {
					ITile tile = clickHandler.getClickedTile();
					tile.disable();
					clickHandler.buryTile();
					gemHandler.addTemporaryGem(tile.getPosition().getX(), tile.getPosition().getY());
				}
				else {
					clickHandler.getClickedTile().unoccupy();
				}
			}
			else {
				Rock rock = clickHandler.getClickedRock();
				clickHandler.unclickRock();
				gemHandler.replaceRock(rock, rock.getCoordinates().getX(), rock.getCoordinates().getY());
			}
		}

		if(Gdx.input.getX() <= 40 && (cam.position.x - effectiveViewportWidth/2) > -50) {
			cam.position.x -= 5;
		}
		if(Gdx.input.getX() >= 960 && (cam.position.x + effectiveViewportWidth/2) < WIDTH + 50) {
			cam.position.x += 5;
		}
		if(Gdx.input.getY() <= 40 && (cam.position.y + effectiveViewportHeight/2) < HEIGHT + 50) {
			cam.position.y += 5;
		}
		if(Gdx.input.getY() >= 560 && (cam.position.y - effectiveViewportHeight/2) > -50) {
			cam.position.y -= 5;
		}
	}

	public void createTiles() {
		int posX = 0;
		int posY = 0;
		int id = 0;

		for(int y = 0; y < 30; y++) {
			for(int x = 0; x < 50; x++) {
				boolean isCheckpoint = isCheckpoint(x, y);
				Tile tile = new Tile(id, batch, stage, clickHandler, manager.get(getTextureBasedOnPosition(x, y), Texture.class), posX, posY, isCheckpoint, manager);
				if(isCheckpoint) {
					checkpoints.add(tile);
				}
				tileMap.put(coordinates[x][y], tile);
				tiles.add(tile);
				posX += WIDTH/50;
				id++;
			}
			posY += HEIGHT/30;
			posX = 0;
		}
		fetchAllNeighbors();
	}

	public void fetchAllNeighbors() {
		for(int y = 0; y < 30; y++) {
			for(int x = 0; x < 50; x++) {
				ITile tile = tileMap.get(coordinates[x][y]);
				if(y == 0) {
					if(x == 0) {
						tile.addNeighbor(tileMap.get(coordinates[x][y+1]));
						tile.addNeighbor(tileMap.get(coordinates[x+1][y]));
					}
					else if(x > 0 && x < 49) {
						tile.addNeighbor(tileMap.get(coordinates[x][y+1]));
						tile.addNeighbor(tileMap.get(coordinates[x-1][y]));
						tile.addNeighbor(tileMap.get(coordinates[x+1][y]));
					}
					else {
						tile.addNeighbor(tileMap.get(coordinates[x][y+1]));
						tile.addNeighbor(tileMap.get(coordinates[x-1][y]));
					}
				}
				else if(y > 0 && y < 29) {
					if(x == 0) {
						tile.addNeighbor(tileMap.get(coordinates[x][y+1]));
						tile.addNeighbor(tileMap.get(coordinates[x][y-1]));
						tile.addNeighbor(tileMap.get(coordinates[x+1][y]));
					}
					else if(x > 0 && x < 49) {
						tile.addNeighbor(tileMap.get(coordinates[x][y+1]));
						tile.addNeighbor(tileMap.get(coordinates[x][y-1]));
						tile.addNeighbor(tileMap.get(coordinates[x-1][y]));
						tile.addNeighbor(tileMap.get(coordinates[x+1][y]));
					}
					else {
						tile.addNeighbor(tileMap.get(coordinates[x][y+1]));
						tile.addNeighbor(tileMap.get(coordinates[x][y-1]));
						tile.addNeighbor(tileMap.get(coordinates[x-1][y]));
					}
				}
				else {
					if(x == 0) {
						tile.addNeighbor(tileMap.get(coordinates[x][y-1]));
						tile.addNeighbor(tileMap.get(coordinates[x+1][y]));
					}
					else if(x > 0 && x < 49) {
						tile.addNeighbor(tileMap.get(coordinates[x][y-1]));
						tile.addNeighbor(tileMap.get(coordinates[x-1][y]));
						tile.addNeighbor(tileMap.get(coordinates[x+1][y]));
					}
					else {
						tile.addNeighbor(tileMap.get(coordinates[x][y-1]));
						tile.addNeighbor(tileMap.get(coordinates[x-1][y]));
					}
				}
			}
		}
	}

	public void renderTiles() {
		for(ITile tile : tiles) {
			tile.render();
		}
	}

	public boolean isCheckpoint(int x, int y) {
		if(y == 27 && (x == 0 || x == 10 || x == 25 || x == 45)) {
			return true;
		}
		else if(y == 9 && (x == 10 || x == 25 || x == 45)) {
			return true;
		}
		else if(y == 1 && (x == 25 || x == 49)){
			return true;
		}
		else {
			return false;
		}
	}

	public String getTextureBasedOnPosition(int x, int y) {
		if(y == 27) {
			if((x > 0 && x <= 9) || (x > 25 && x < 45)) {
				return "path.png";
			}
			else if(x == 0 || x == 10 || x == 25 || x == 45) {
				return "checkpoint.png";
			}
			else {
				return "tile.png";
			}
		}
		else if(y < 27 && y > 9) {
			if(x == 10 || x == 25 || x == 45) {
				return "path.png";
			}
			else {
				return "tile.png";
			}
		}
		else if(y == 9) {
			if((x > 10 && x < 25) || (x > 25 && x < 45)) {
				return "path.png";
			}
			else if(x == 10 || x == 25 || x == 45) {
				return "checkpoint.png";
			}
			else {
				return "tile.png";
			}
		}
		else if(y > 1 && y < 9) {
			if(x == 25) {
				return "path.png";
			}
			else {
				return "tile.png";
			}
		}
		else if(y == 1) {
			if(x == 25 || x == 49) {
				return "checkpoint.png";
			}
			else if(x > 25 && x <= 49) {
				return "path.png";
			}
			else {
				return "tile.png";
			}
		}
		else {
			return "tile.png";
		}
	}

	public void createCoordinates() {
		coordinates = new Coordinate[50][30];
		for(int y = 0; y < 30; y++) {
			for(int x = 0; x < 50; x++) {
				coordinates[x][y] = new Coordinate(x, y);
			}
		}
	}

	private class InputCore implements InputProcessor {

		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			if(!isPaused) {
				if(amount == 1 && cam.zoom < 1.1){
					cam.zoom += ZOOM_FACTOR;
				}
				else if(amount == -1 && cam.zoom > 0.4){
					cam.zoom -= ZOOM_FACTOR;
				}
			}
			return true;
		}
	}

	public void resize(int width, int height) {
		viewport.update(width, height);
		if(overlay != null) {
			overlay.getStage().getViewport().update(width, height);
		}
		cam.update();
	}

	public void loadAssets() {
		manager.load("background.png", Texture.class);

		manager.load("bluePower.png", Texture.class);
		manager.load("blackPower.png", Texture.class);
		manager.load("greenPower.png", Texture.class);
		manager.load("yellowPower.png", Texture.class);
		manager.load("redPower.png", Texture.class);
		manager.load("whitePower.png", Texture.class);
		manager.load("purplePower.png", Texture.class);

		manager.load("monsterSheet1.png", Texture.class);
		manager.load("monsterSheet2.png", Texture.class);
		manager.load("flyingMonsterSheet1.png", Texture.class);
		manager.load("slowAnimation.png", Texture.class);
		manager.load("poisonAnimation.png", Texture.class);

		manager.load("temporaryPath.png", Texture.class);

		//Bullets
		manager.load("blackBullet.png", Texture.class);
		manager.load("blueBullet.png", Texture.class);
		manager.load("greenBullet.png", Texture.class);
		manager.load("pinkBullet.png", Texture.class);
		manager.load("purpleBullet.png", Texture.class);
		manager.load("redBullet.png", Texture.class);
		manager.load("whiteBullet.png", Texture.class);
		manager.load("yellowBullet.png", Texture.class);

		//For the overlay
		manager.load("overlay.png", Texture.class);
		manager.load("infoArea.png", Texture.class);
		manager.load("shopButton.png", Texture.class);
		manager.load("gemChances.png", Texture.class);
		manager.load("shop.png", Texture.class);
		manager.load("minimizeButton.png", Texture.class);
		manager.load("maximizeButton.png", Texture.class);

		//Gem handler
		manager.load("temporaryGem.png", Texture.class);
		manager.load("enhance.png", Texture.class);
		manager.load("specialCombinationAnimationSheet.png", Texture.class);

		//Green gem
		manager.load("green_1.png", Texture.class);
		manager.load("green_2.png", Texture.class);
		manager.load("green_3.png", Texture.class);
		manager.load("green_4.png", Texture.class);
		manager.load("green_5.png", Texture.class);
		manager.load("green_6.png", Texture.class);

		//Blue gem
		manager.load("blue_1.png", Texture.class);
		manager.load("blue_2.png", Texture.class);
		manager.load("blue_3.png", Texture.class);
		manager.load("blue_4.png", Texture.class);
		manager.load("blue_5.png", Texture.class);
		manager.load("blue_6.png", Texture.class);

		//Yellow gem
		manager.load("yellow_1.png", Texture.class);
		manager.load("yellow_2.png", Texture.class);
		manager.load("yellow_3.png", Texture.class);
		manager.load("yellow_4.png", Texture.class);
		manager.load("yellow_5.png", Texture.class);
		manager.load("yellow_6.png", Texture.class);

		//White gem
		manager.load("white_1.png", Texture.class);
		manager.load("white_2.png", Texture.class);
		manager.load("white_3.png", Texture.class);
		manager.load("white_4.png", Texture.class);
		manager.load("white_5.png", Texture.class);
		manager.load("white_6.png", Texture.class);

		//Pink gem
		manager.load("pink_1.png", Texture.class);
		manager.load("pink_2.png", Texture.class);
		manager.load("pink_3.png", Texture.class);
		manager.load("pink_4.png", Texture.class);
		manager.load("pink_5.png", Texture.class);
		manager.load("pink_6.png", Texture.class);

		//Red gem
		manager.load("red_1.png", Texture.class);
		manager.load("red_2.png", Texture.class);
		manager.load("red_3.png", Texture.class);
		manager.load("red_4.png", Texture.class);
		manager.load("red_5.png", Texture.class);
		manager.load("red_6.png", Texture.class);

		//Purple gem
		manager.load("purple_1.png", Texture.class);
		manager.load("purple_2.png", Texture.class);
		manager.load("purple_3.png", Texture.class);
		manager.load("purple_4.png", Texture.class);
		manager.load("purple_5.png", Texture.class);
		manager.load("purple_6.png", Texture.class);

		//Black gem
		manager.load("black_1.png", Texture.class);
		manager.load("black_2.png", Texture.class);
		manager.load("black_3.png", Texture.class);
		manager.load("black_4.png", Texture.class);
		manager.load("black_5.png", Texture.class);
		manager.load("black_6.png", Texture.class);
		
		manager.load("rock.png", Texture.class);
		manager.load("tilePressed.png", Texture.class);
		manager.load("tileHover.png", Texture.class);
		manager.load("clickedGem.png", Texture.class);
		manager.load("tile.png", Texture.class);
		manager.load("path.png", Texture.class);
		manager.load("checkpoint.png", Texture.class);
		manager.load("crit.png", Texture.class);
		manager.load("paused.png", Texture.class);
	}
}
