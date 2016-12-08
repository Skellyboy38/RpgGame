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
import player.Player;
import settings.Settings;
import tile.ITile;

public class Summoner {
	public static final int START_X = 0 - RpgGame.WIDTH/50;
	public static final int START_Y = 27*(RpgGame.HEIGHT/30);
	
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
	private int level;
	private boolean canIncrementLevel;
	private Player player;

	public Summoner(SpriteBatch batch, ShapeRenderer renderer, List<ITile> checkpoints, Player player) {
		this.level = 1;
		this.batch = batch;
		this.renderer = renderer;
		monsters = new ArrayList<IMonster>();
		finder = new PathFinder();
		canStart = false;
		this.checkpoints = checkpoints;
		this.timeElapsed = 0;
		isSummoning = false;
		canIncrementLevel = false;
		this.player = player;
	}
	
	public List<IMonster> getMonsters() {
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
	
	public void reset() {
		level = 1;
		monsters.clear();
		canStart = false;
		isSummoning = false;
		canIncrementLevel = false;
	}
	
	public void nextStage() {
		setNumberOfMonsters(Settings.levels.get(level).number);
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
		Monster m;
		switch(level) {
		case 1:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(1).speed, Settings.levels.get(1).hp);
			break;
		case 2:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(2).speed, Settings.levels.get(2).hp);
			break;
		case 3:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(3).speed, Settings.levels.get(3).hp);
			break;
		case 4:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(4).speed, Settings.levels.get(4).hp);
			break;
		case 5:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(5).speed, Settings.levels.get(5).hp);
			break;
		case 6:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(6).speed, Settings.levels.get(6).hp);
			break;
		case 7:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(7).speed, Settings.levels.get(7).hp);
			break;
		case 8:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(8).speed, Settings.levels.get(8).hp);
			break;
		case 9:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(9).speed, Settings.levels.get(9).hp);
			break;
		case 10:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(10).speed, Settings.levels.get(10).hp);
			break;
		case 11:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(11).speed, Settings.levels.get(11).hp);
			break;
		case 12:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(12).speed, Settings.levels.get(12).hp);
			break;
		case 13:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(13).speed, Settings.levels.get(13).hp);
			break;
		case 14:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(14).speed, Settings.levels.get(14).hp);
			break;
		case 15:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(15).speed, Settings.levels.get(15).hp);
			break;
		case 16:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(16).speed, Settings.levels.get(16).hp);
			break;
		case 17:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(17).speed, Settings.levels.get(17).hp);
			break;
		case 18:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(18).speed, Settings.levels.get(18).hp);
			break;
		case 19:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(19).speed, Settings.levels.get(19).hp);
			break;
		case 20:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(20).speed, Settings.levels.get(20).hp);
			break;
		case 21:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(21).speed, Settings.levels.get(21).hp);
			break;
		case 22:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(22).speed, Settings.levels.get(22).hp);
			break;
		case 23:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(23).speed, Settings.levels.get(23).hp);
			break;
		case 24:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(24).speed, Settings.levels.get(24).hp);
			break;
		case 25:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(25).speed, Settings.levels.get(25).hp);
			break;
		case 26:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(26).speed, Settings.levels.get(26).hp);
			break;
		case 27:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(27).speed, Settings.levels.get(27).hp);
			break;
		case 28:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(28).speed, Settings.levels.get(28).hp);
			break;
		case 29:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(29).speed, Settings.levels.get(29).hp);
			break;
		case 30:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(30).speed, Settings.levels.get(30).hp);
			break;
		case 31:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(31).speed, Settings.levels.get(31).hp);
			break;
		case 32:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(32).speed, Settings.levels.get(32).hp);
			break;
		case 33:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(33).speed, Settings.levels.get(33).hp);
			break;
		case 34:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(34).speed, Settings.levels.get(34).hp);
			break;
		case 35:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(35).speed, Settings.levels.get(35).hp);
			break;
		case 36:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(36).speed, Settings.levels.get(36).hp);
			break;
		case 37:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(37).speed, Settings.levels.get(37).hp);
			break;
		case 38:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(38).speed, Settings.levels.get(38).hp);
			break;
		case 39:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(39).speed, Settings.levels.get(39).hp);
			break;
		case 40:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(40).speed, Settings.levels.get(40).hp);
			break;
		case 41:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(41).speed, Settings.levels.get(41).hp);
			break;
		case 42:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(42).speed, Settings.levels.get(42).hp);
			break;
		case 43:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(43).speed, Settings.levels.get(43).hp);
			break;
		case 44:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(44).speed, Settings.levels.get(44).hp);
			break;
		case 45:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(45).speed, Settings.levels.get(45).hp);
			break;
		case 46:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(46).speed, Settings.levels.get(46).hp);
			break;
		case 47:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(47).speed, Settings.levels.get(47).hp);
			break;
		case 48:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(48).speed, Settings.levels.get(48).hp);
			break;
		case 49:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(49).speed, Settings.levels.get(49).hp);
			break;
		case 50:
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(50).speed, Settings.levels.get(50).hp);
			break;
		default: 
			m = new Monster(batch, renderer, new Texture("monster.png"), START_X, START_Y, Settings.levels.get(1).speed, Settings.levels.get(1).hp);
			break;
		}
		m.jump(START_X, START_Y);
		m.setPath(paths);
		monsters.add(m);
	}
	
	public void checkStatus() {
		for(int i = 0; i < monsters.size(); i++) {
			if(monsters.get(i).donePath()) {
				if(monsters.get(i).canDamagePlayer()) {
					player.damage(1);
				}
				killMonster(monsters.get(i));
				startTimes[i] = false;
			}
			else if(monsters.get(i).isDead()) {
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
		if(isDoneSummoning) {
			if(canIncrementLevel) {
				level++;
				canIncrementLevel = false;
			}
			isSummoning = false;
		}
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
			if((timeElapsed/Settings.levels.get(level).delay)%1 == 0 && timeElapsed < Settings.levels.get(level).delay*numberOfMonsters) {
				startTimes[timeElapsed/Settings.levels.get(level).delay] = true;
			}
		}
	}
	
	public void start() {
		canIncrementLevel = true;
		canStart = true;
		isSummoning = true;
	}
	
	public List<Path> findAllPaths() {
		Path p1 = finder.findPathBetweenTwoTiles(checkpoints.get(5), checkpoints.get(6));
		Path p2 = finder.findPathBetweenTwoTiles(checkpoints.get(6), checkpoints.get(2));
		Path p3 = finder.findPathBetweenTwoTiles(checkpoints.get(2), checkpoints.get(3));
		Path p4 = finder.findPathBetweenTwoTiles(checkpoints.get(3), checkpoints.get(4));
		Path p5 = finder.findPathBetweenTwoTiles(checkpoints.get(4), checkpoints.get(8));
		Path p6 = finder.findPathBetweenTwoTiles(checkpoints.get(8), checkpoints.get(7));
		Path p7 = finder.findPathBetweenTwoTiles(checkpoints.get(7), checkpoints.get(3));
		Path p8 = finder.findPathBetweenTwoTiles(checkpoints.get(3), checkpoints.get(0));
		Path p9 = finder.findPathBetweenTwoTiles(checkpoints.get(0), checkpoints.get(1));
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
	
	public boolean doesPathExist() {
		if(finder.pathExists(checkpoints.get(5), checkpoints.get(6)) && finder.pathExists(checkpoints.get(6), checkpoints.get(2)) && finder.pathExists(checkpoints.get(2), checkpoints.get(3)) &&
				finder.pathExists(checkpoints.get(3), checkpoints.get(4)) && finder.pathExists(checkpoints.get(4), checkpoints.get(8)) &&
				finder.pathExists(checkpoints.get(8), checkpoints.get(7)) && finder.pathExists(checkpoints.get(7), checkpoints.get(0)) &&
				finder.pathExists(checkpoints.get(0), checkpoints.get(1))) {
			return true;
		}
		else {
			return false;
		}
	}
}
