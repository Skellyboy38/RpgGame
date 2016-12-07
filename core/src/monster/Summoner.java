package monster;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.RpgGame;

import path.Path;
import path.PathFinder;
import tile.ITile;

public class Summoner {
	public static final int START_X = 0 - RpgGame.WIDTH/50;
	public static final int START_Y = 27*(RpgGame.HEIGHT/30);
	public static final int SPAWN_DELAY = 200;
	
	private List<IMonster> monsters;
	private SpriteBatch batch;
	private ShapeRenderer renderer;
	private boolean canStart;
	private PathFinder finder;
	private List<ITile> checkpoints;
	private Boolean[] startTimes;
	private int timeElapsed;
	private int numberOfMonsters;
	private List<Path> paths;
	private boolean isSummoning;

	public Summoner(SpriteBatch batch, ShapeRenderer renderer, List<ITile> checkpoints) {
		this.batch = batch;
		this.renderer = renderer;
		monsters = new ArrayList<IMonster>();
		finder = new PathFinder();
		canStart = false;
		this.checkpoints = checkpoints;
		this.timeElapsed = 0;
		isSummoning = false;
	}
	
	public List<IMonster> getMonsters() {
		return monsters;
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
		Monster m = new MonsterLevel1(batch, renderer, new Texture("monster.png"), START_X, START_Y);
		m.jump(START_X, START_Y);
		m.setPath(paths);
		monsters.add(m);
	}
	
	public void checkStatus() {
		for(int i = 0; i < monsters.size(); i++) {
			if(monsters.get(i).isDead() || monsters.get(i).donePath()) {
				killMonster(monsters.get(i));
				startTimes[i] = false;
			}
		}
		boolean isDoneSummoning = true;
		for(IMonster m : monsters) {
			if(!m.isDead()) {
				isDoneSummoning = false;
			}
		}
		isSummoning = !isDoneSummoning;
	}
	
	public void killMonster(IMonster m) {
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
		isSummoning = true;
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
