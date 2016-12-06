package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import monster.Monster;
import path.Path;
import path.PathFinder;
import tile.Coordinate;
import tile.ITile;
import tile.Tile;
import tile.TileClickHandler;

public class RpgGame extends ApplicationAdapter {
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static final int START_X = 0 - WIDTH/50;
	public static final int START_Y = 27*(HEIGHT/30);
	
	SpriteBatch batch;
	Random random;
	List<ITile> tiles;
	List<ITile> checkpoints; // The order of checkpoints from start to finish: 5, 2, 3, 4, 7, 6, 0, 1
	Stage stage;
	Map<Coordinate, ITile> tileMap;
	Coordinate[][] coordinates;
	TileClickHandler clickHandler;
	PathFinder finder;
	Monster m;
	
	String[] gemSkins = {"yellow", "red", "green", "blue", "black", "pink", "purple"};
	
	@Override
	public void create () {
		stage = new Stage();
		tiles = new ArrayList<ITile>();
		checkpoints = new ArrayList<ITile>();
		tileMap = new HashMap<Coordinate, ITile>();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		createCoordinates();
		clickHandler = new TileClickHandler(tileMap, coordinates);
		finder = new PathFinder();
		createTiles();
		random = new Random();
		m = new Monster(batch, new Texture("monster.png"), START_X, START_Y);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		renderTiles();
		stage.act();
		m.update();
		batch.end();
		checkInputs();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	public List<Path> findAllPaths() {
		Path p1 = finder.findPathBetweenTwoTiles(checkpoints.get(5), checkpoints.get(2));
		Path p2 = finder.findPathBetweenTwoTiles(checkpoints.get(2), checkpoints.get(3));
		Path p3 = finder.findPathBetweenTwoTiles(checkpoints.get(3), checkpoints.get(4));
		Path p4 = finder.findPathBetweenTwoTiles(checkpoints.get(4), checkpoints.get(7));
		Path p5 = finder.findPathBetweenTwoTiles(checkpoints.get(7), checkpoints.get(6));
		Path p6 = finder.findPathBetweenTwoTiles(checkpoints.get(6), checkpoints.get(0));
		Path p7 = finder.findPathBetweenTwoTiles(checkpoints.get(0), checkpoints.get(1));
		List<Path> paths = new ArrayList<Path>();
		paths.add(p1);
		paths.add(p2);
		paths.add(p3);
		paths.add(p4);
		paths.add(p5);
		paths.add(p6);
		paths.add(p7);
		return paths;
	}
	
	public void checkInputs() {
		boolean isEnterPressed = false;
		if(Gdx.input.isKeyPressed(66) && !isEnterPressed) {
			isEnterPressed = true;
			m.jump(START_X, START_Y);
			m.setPath(findAllPaths());
		}
		if(!Gdx.input.isKeyPressed(66)) {
			isEnterPressed = false;
		}
		if(Gdx.input.isKeyPressed(62) && clickHandler.getClickedTile() != null) {
			clickHandler.getClickedTile().occupy();
			if(finder.pathExists(checkpoints.get(5), checkpoints.get(2)) && finder.pathExists(checkpoints.get(2), checkpoints.get(3)) &&
					finder.pathExists(checkpoints.get(3), checkpoints.get(4)) && finder.pathExists(checkpoints.get(4), checkpoints.get(7)) &&
					finder.pathExists(checkpoints.get(7), checkpoints.get(6)) && finder.pathExists(checkpoints.get(6), checkpoints.get(0)) &&
					finder.pathExists(checkpoints.get(0), checkpoints.get(1))) {
				int randomNum = random.nextInt((6 - 0) + 1) + 0;
				String gemString = gemSkins[randomNum];
				clickHandler.getClickedTile().disable();
				clickHandler.getClickedTile().setTexture(new Texture(gemString + ".png"));
				clickHandler.getClickedTile().setDefaultTexture(new Texture(gemString + ".png"));
				clickHandler.unclick();
			}
			else {
				clickHandler.getClickedTile().unoccupy();
			}
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
}
