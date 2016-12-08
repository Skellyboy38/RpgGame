package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import collision.CollisionDetector;
import gem.GemHandler;
import gem.Rock;
import monster.Summoner;
import overlay.Overlay;
import settings.Settings;
import tile.Coordinate;
import tile.ITile;
import tile.Tile;
import tile.TileClickHandler;

public class RpgGame extends ApplicationAdapter {
	
	public static int WIDTH = 1000;
	public static int HEIGHT = 600;
	public static final float ZOOM_FACTOR = 0.05f;
	public int numMonsters = 10;
	
	SpriteBatch batch;
	ShapeRenderer renderer;
	Summoner summoner;
	CollisionDetector collisionDetector;
	List<ITile> tiles;
	List<ITile> checkpoints; // The order of checkpoints from start to finish: 5, 2, 3, 4, 7, 6, 0, 1
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
	Settings gameSettings;
	float effectiveViewportWidth;
	float effectiveViewportHeight;
	
	@Override
	public void create () {
		gameSettings = new Settings();
		batch = new SpriteBatch();
		renderer = new ShapeRenderer();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		effectiveViewportWidth = WIDTH;
		effectiveViewportHeight = HEIGHT;
		cam.position.set(cam.viewportWidth/2f, cam.viewportHeight/2f, 0);
		cam.update();
		viewport = new FitViewport(WIDTH, HEIGHT, cam);
		stage = new Stage(viewport, batch);
		zoom = new InputCore();
		inputMultiplexer = new InputMultiplexer();
		tiles = new ArrayList<ITile>();
		checkpoints = new ArrayList<ITile>();
		tileMap = new HashMap<Coordinate, ITile>();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(zoom);
		Gdx.input.setInputProcessor(inputMultiplexer);
		createCoordinates();
		clickHandler = new TileClickHandler(tileMap, coordinates, batch);
		gemHandler = new GemHandler(batch, renderer, stage, clickHandler);
		createTiles();
		summoner = new Summoner(batch, renderer, checkpoints);
		collisionDetector = new CollisionDetector(gemHandler.getFinalizedGems(), summoner.getMonsters());
		overlay = new Overlay(clickHandler, summoner);
	}

	@Override
	public void render () {
		updateEffectiveViewport();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		renderer.setProjectionMatrix(cam.combined);
		stage.act();
		renderTiles();
		gemHandler.render();
		clickHandler.render();
		summoner.render();
		overlay.render();
		collisionDetector.detectCollisions();
		checkInputs();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	public void updateEffectiveViewport() {
		effectiveViewportWidth = WIDTH*cam.zoom;
		effectiveViewportHeight = HEIGHT*cam.zoom;
	}
	
	public void checkInputs() {
		boolean isEnterPressed = false;
		if(Gdx.input.isKeyPressed(66) && gemHandler.isReady() && !isEnterPressed) {
			gemHandler.commitGem(clickHandler.getClickedGem());
			clickHandler.unclickGem();
			gemHandler.reset();
			summoner.reset();
			summoner.start();
			isEnterPressed = true;
		}
		if(!Gdx.input.isKeyPressed(66)) {
			isEnterPressed = false;
		}
		if(Gdx.input.isKeyPressed(62) && (clickHandler.getClickedTile() != null || clickHandler.getClickedRock() != null) && !gemHandler.hasFiveGems() && !summoner.isSummoning()) {
			if(clickHandler.getClickedTile() != null) {
				clickHandler.getClickedTile().occupy();
				if(summoner.doesPathExist()) {
					ITile tile = clickHandler.getClickedTile();
					tile.disable();
					clickHandler.unclickTile();
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
		
		if(Gdx.input.getX() <= 20 && (cam.position.x - effectiveViewportWidth/2) > -50) {
			cam.position.x -= 10;
		}
		if(Gdx.input.getX() >= 980 && (cam.position.x + effectiveViewportWidth/2) < WIDTH + 50) {
			cam.position.x += 10;
		}
		if(Gdx.input.getY() <= 20 && (cam.position.y + effectiveViewportHeight/2) < HEIGHT + 50) {
			cam.position.y += 10;
		}
		if(Gdx.input.getY() >= 580 && (cam.position.y - effectiveViewportHeight/2) > -50) {
			cam.position.y -= 10;
		}
	}
	
	public void createTiles() {
		int posX = 0;
		int posY = 0;
		int id = 0;
		
		for(int y = 0; y < 30; y++) {
			for(int x = 0; x < 50; x++) {
				boolean isCheckpoint = isCheckpoint(x, y);
				Tile tile = new Tile(id, batch, stage, clickHandler, getTextureBasedOnPosition(x, y), posX, posY, isCheckpoint);
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
		if(y == 27 && (x == 0 || x == 25 || x == 45)) {
			return true;
		}
		else if(y == 9 && (x == 5 || x == 25 || x == 45)) {
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
			if((x > 0 && x <= 5) || (x > 25 && x < 45)) {
				return "path.png";
			}
			else if(x == 0 || x == 25 || x == 45) {
				return "checkpoint.png";
			}
			else {
				return "tile.png";
			}
		}
		else if(y < 27 && y > 9) {
			if(x == 5 || x == 25 || x == 45) {
				return "path.png";
			}
			else {
				return "tile.png";
			}
		}
		else if(y == 9) {
			if((x > 5 && x < 25) || (x > 25 && x < 45)) {
				return "path.png";
			}
			else if(x == 5 || x == 25 || x == 45) {
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
			if(amount == 1 && cam.zoom < 1.1){
				cam.zoom += ZOOM_FACTOR;
			}
			else if(amount == -1 && cam.zoom > 0.4){
				cam.zoom -= ZOOM_FACTOR;
			}
			return true;
		}
	}
	
	public void resize(int width, int height) {
        viewport.update(width, height);
        cam.update();
    }
}
