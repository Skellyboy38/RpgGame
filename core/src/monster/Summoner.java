package monster;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.RpgGame;

import path.Path;
import path.PathFinder;
import tile.ITile;

public class Summoner {
	public static final int START_X = 0 - RpgGame.WIDTH/50;
	public static final int START_Y = 27*(RpgGame.HEIGHT/30);
	public static final int SPAWN_DELAY = 200;
	
	private List<Monster> monsters;
	private SpriteBatch batch;
	private boolean canStart;
	private PathFinder finder;
	private List<ITile> checkpoints;
	private Boolean[] startTimes;
	private int timeElapsed;
	private int numberOfMonsters;
	private List<Path> paths;

	public Summoner(SpriteBatch batch, List<ITile> checkpoints) {
		this.batch = batch;
		monsters = new ArrayList<Monster>();
		finder = new PathFinder();
		canStart = false;
		this.checkpoints = checkpoints;
		this.timeElapsed = 0;
	}
	
	public void setNumberOfMonsters(int numMonsters) {
		numberOfMonsters = numMonsters;
		startTimes = new Boolean[numMonsters];
		for(int i = 0; i < startTimes.length; i++) {
			startTimes[i] = false;
		}
	}
	
	public void reset() {
		paths = findAllPaths();
		for(int i = 0; i < startTimes.length; i++) {
			startTimes[i] = false;
		}
		timeElapsed = 0;
		clearMonsters();
		for(int i = 0; i < numberOfMonsters; i++) {
			createMonster();
		}
	}
	
	public void clearMonsters() {
		monsters.clear();
	}
	
	public void createMonster() {
		Monster m = new Monster(batch, new Texture("monster.png"), START_X, START_Y);
		m.jump(START_X, START_Y);
		m.setPath(paths);
		monsters.add(m);
	}
	
	public void checkStatus() {
		for(int i = 0; i < monsters.size(); i++) {
			if(!monsters.get(i).isDead() && monsters.get(i).donePath()) {
				killMonster(monsters.get(i));
				startTimes[i] = false;
			}
		}
	}
	
	public void killMonster(Monster m) {
		m.kill();
	}
	
	public void render() {
		checkStatus();
		if(canStart) {
			timeElapsed += (int)(Gdx.graphics.getDeltaTime()*1000);
			for(int i = 0; i < monsters.size(); i++) {
				if(startTimes[i]) {
					monsters.get(i).update();
				}
			}
			if((timeElapsed/SPAWN_DELAY)%1 == 0 && timeElapsed < SPAWN_DELAY*numberOfMonsters) {
				startTimes[timeElapsed/SPAWN_DELAY] = true;
			}
		}
	}
	
	public void start() {
		canStart = true;
	}
	
	public List<Path> findAllPaths() {
		Path p1 = finder.findPathBetweenTwoTiles(checkpoints.get(5), checkpoints.get(2));
		Path p2 = finder.findPathBetweenTwoTiles(checkpoints.get(2), checkpoints.get(3));
		Path p3 = finder.findPathBetweenTwoTiles(checkpoints.get(3), checkpoints.get(4));
		Path p4 = finder.findPathBetweenTwoTiles(checkpoints.get(4), checkpoints.get(7));
		Path p5 = finder.findPathBetweenTwoTiles(checkpoints.get(7), checkpoints.get(6));
		Path p6 = finder.findPathBetweenTwoTiles(checkpoints.get(6), checkpoints.get(3));
		Path p7 = finder.findPathBetweenTwoTiles(checkpoints.get(3), checkpoints.get(0));
		Path p8 = finder.findPathBetweenTwoTiles(checkpoints.get(0), checkpoints.get(1));
		List<Path> paths = new ArrayList<Path>();
		paths.add(p1);
		paths.add(p2);
		paths.add(p3);
		paths.add(p4);
		paths.add(p5);
		paths.add(p6);
		paths.add(p7);
		paths.add(p8);
		return paths;
	}
	
	public boolean doesPathExist() {
		if(finder.pathExists(checkpoints.get(5), checkpoints.get(2)) && finder.pathExists(checkpoints.get(2), checkpoints.get(3)) &&
				finder.pathExists(checkpoints.get(3), checkpoints.get(4)) && finder.pathExists(checkpoints.get(4), checkpoints.get(7)) &&
				finder.pathExists(checkpoints.get(7), checkpoints.get(6)) && finder.pathExists(checkpoints.get(6), checkpoints.get(0)) &&
				finder.pathExists(checkpoints.get(0), checkpoints.get(1))) {
			return true;
		}
		else {
			return false;
		}
	}
}
