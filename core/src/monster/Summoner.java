package monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.RpgGame;

import path.Path;
import path.PathFinder;
import player.Player;
import settings.Settings;
import tile.ITile;

public class Summoner {
	public static final int START_X = 0 - RpgGame.WIDTH/50;
	public static final int START_Y = 27*(RpgGame.HEIGHT/30);
	
	private List<Monster> monsters;
	private List<Path> paths;
	private List<Path> flyingPaths;
	private List<ITile> checkpoints;
	private List<Path> temporaryPath;
	private final Pool<Monster> monsterPool;

	private SpriteBatch batch;
	private boolean canStart;
	private Boolean[] startTimes;
	private int timeElapsed;
	private int numberOfMonsters;
	private boolean isSummoning;
	private int level;
	private boolean canIncrementLevel;
	private Random random;
	private boolean showPath;
	private Player player;
	private Texture temporaryPathTexture;

	public Summoner(final SpriteBatch batch, final ShapeRenderer renderer, List<ITile> checkpoints, Player player, AssetManager manager) {
		this.level = 1;
		this.batch = batch;
		this.temporaryPathTexture = manager.get("temporaryPath.png", Texture.class);
		monsters = new ArrayList<Monster>();
		canStart = false;
		this.checkpoints = checkpoints;
		this.timeElapsed = 0;
		isSummoning = false;
		canIncrementLevel = false;
		showPath = false;
		this.player = player;
		this.temporaryPath = new ArrayList<Path>();
		random = new Random();

		monsterPool = new Pool<Monster>() {
			@Override
			protected Monster newObject() {
				return new Monster(batch, renderer);
			}
		};
	}
	
	public void getFlyingPaths() {
		flyingPaths = findAllPaths();
	}
	
	public void toggleDisplayPath() {
		showPath = !showPath;
		if(showPath) {
			drawPath();
		}
	}
	
	public List<Monster> getMonsters() {
		return monsters;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean isSummoning() {
		return isSummoning;
	}
	
	public void setNumberOfMonsters(int numMonsters) {
		numberOfMonsters = numMonsters;
		startTimes = new Boolean[numMonsters];
		for(int i = 0; i < startTimes.length; i++) {
			startTimes[i] = false;
		}
	}

	public void dispose() {
		temporaryPathTexture.dispose();
		disposePaths();
		for(Path p : flyingPaths) {
			for(ITile tile : p.getPath()) {
				tile.dispose();
			}
		}
		for(Path p : temporaryPath) {
			for(ITile tile : p.getPath()) {
				tile.dispose();
			}
		}
		for(IMonster m : monsters) {
			m.dispose();
		}
	}

	public void disposePaths() {
		for(Path p : paths) {
			for(ITile tile : p.getPath()) {
				tile.dispose();
			}
		}
	}
	
	public void reset() {
		int numberOfMonstersInPool = monsterPool.getFree();
		level = 1;
		clearMonsters();
		for(int i = 0; i < numberOfMonstersInPool; i++) {
			Monster m = monsterPool.obtain();
			m.dispose();
			System.out.println("Disposed of monster: " + i);
		}
		canStart = false;
		isSummoning = false;
		canIncrementLevel = false;
		showPath = false;
	}
	
	public void nextStage() {
		String monsterType = Settings.elementIntToType.get(random.nextInt(7));
		setNumberOfMonsters(Settings.levels.get(level).number);
		paths = findAllPaths();
		for(int i = 0; i < startTimes.length; i++) {
			startTimes[i] = false;
		}
		timeElapsed = 0;
		for(Monster m : monsters) {
			monsterPool.free(m);
		}
		clearMonsters();
		for(int i = 0; i < numberOfMonsters; i++) {
			createMonster(monsterType);
		}
	}
	
	public void clearMonsters() {
		monsters.clear();
	}
	
	public void createMonster(String type) {
		Monster m = monsterPool.obtain();
		m.setInformation(Settings.levels.get(level).animationSheet, START_X, START_Y, Settings.levels.get(level).speed, Settings.levels.get(level).hp, Settings.levels.get(level).value, type, Settings.levels.get(level).isFlying);
		if(Settings.levels.get(level).isFlying) {
			m.setPath(flyingPaths);
		}
		else {
			m.setPath(paths);
		}
		monsters.add(m);
	}
	
	public void checkStatus() {
		for(Monster m : monsters) {
			if(m.donePath()) {
				if(m.canDamagePlayer()) {
					player.damage(1);
					killMonster(m);
				}
			}
			else if(m.isDead() && m.canGiveGold()) {
				player.addMoney(m.getValue());
				m.giveGold();
				killMonster(m);
			}
		}
		boolean isDoneSummoning = true;
		for(Monster m : monsters) {
			if(!m.isDead()) {
				isDoneSummoning = false;
			}
		}
		if(isDoneSummoning) {
			if(canIncrementLevel) {
				level++;
				canIncrementLevel = false;
			}
			isSummoning = false;
			canStart = false;
		}
	}
	
	public void killMonster(Monster m) {
		m.kill();
	}
	
	public void render() {
		if(canStart) {
			checkStatus();
			timeElapsed += (int)(Gdx.graphics.getDeltaTime()*1000);
			for(int i = 0; i < monsters.size(); i++) {
				if(startTimes[i]) {
					monsters.get(i).update();
				}
			}
			if((timeElapsed/Settings.levels.get(level).delay)%1 == 0 && timeElapsed < Settings.levels.get(level).delay*numberOfMonsters) {
				startTimes[timeElapsed/Settings.levels.get(level).delay] = true;
			}
		}
		if(showPath) {
			for(Path p : temporaryPath) {
				for(ITile tile : p.getPath()) {
					batch.begin();
					Color c = batch.getColor();
					batch.setColor(c.r, c.g, c.b, 0.1f);
					batch.draw(temporaryPathTexture, tile.getPosition().getX(), tile.getPosition().getY());
					batch.setColor(c.r, c.g, c.b, 1f);
					batch.end();
				}
			}
		}
	}
	
	public void start() {
		canIncrementLevel = true;
		canStart = true;
		isSummoning = true;
	}
	
	public List<Path> findAllPaths() {
		Path p1 = PathFinder.findPathBetweenTwoTiles(checkpoints.get(5), checkpoints.get(6));
		Path p2 = PathFinder.findPathBetweenTwoTiles(checkpoints.get(6), checkpoints.get(2));
		Path p3 = PathFinder.findPathBetweenTwoTiles(checkpoints.get(2), checkpoints.get(3));
		Path p4 = PathFinder.findPathBetweenTwoTiles(checkpoints.get(3), checkpoints.get(4));
		Path p5 = PathFinder.findPathBetweenTwoTiles(checkpoints.get(4), checkpoints.get(8));
		Path p6 = PathFinder.findPathBetweenTwoTiles(checkpoints.get(8), checkpoints.get(7));
		Path p7 = PathFinder.findPathBetweenTwoTiles(checkpoints.get(7), checkpoints.get(3));
		Path p8 = PathFinder.findPathBetweenTwoTiles(checkpoints.get(3), checkpoints.get(0));
		Path p9 = PathFinder.findPathBetweenTwoTiles(checkpoints.get(0), checkpoints.get(1));
		List<Path> paths = new ArrayList<Path>();
		paths.add(p1);
		paths.add(p2);
		paths.add(p3);
		paths.add(p4);
		paths.add(p5);
		paths.add(p6);
		paths.add(p7);
		paths.add(p8);
		paths.add(p9);
		return paths;
	}
	
	public void drawPath() {
		if(showPath) {
			this.temporaryPath = findAllPaths();
		}
	}
	
	public boolean doesPathExist() {
		if(PathFinder.pathExists(checkpoints.get(5), checkpoints.get(6)) && PathFinder.pathExists(checkpoints.get(6), checkpoints.get(2)) && PathFinder.pathExists(checkpoints.get(2), checkpoints.get(3)) &&
				PathFinder.pathExists(checkpoints.get(3), checkpoints.get(4)) && PathFinder.pathExists(checkpoints.get(4), checkpoints.get(8)) &&
				PathFinder.pathExists(checkpoints.get(8), checkpoints.get(7)) && PathFinder.pathExists(checkpoints.get(7), checkpoints.get(0)) &&
				PathFinder.pathExists(checkpoints.get(0), checkpoints.get(1))) {
			return true;
		}
		else {
			return false;
		}
	}
}
