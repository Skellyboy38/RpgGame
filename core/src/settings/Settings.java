package settings;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.RpgGame;

public class Settings {
	
	public static Map<Integer, Entity> levels;
	public static Map<String, Map<Integer, GemSettings>> gemSettings;
	public static Map<Integer, Float[]> gemChances;
	public static Map<Integer, Texture> animationSheets;
	public static Map<Integer, Integer> upgradePrices;
	public static Map<String, Texture> ailmentAnimationSheets;
	public static Map<Integer, String> elementIntToType;
	public static Map<String, Texture> elementTypes;
	public static Map<String, String> elementWeaknesses;
	public static Map<String, String> elementStrengths;

	public Settings() {
		levels = new HashMap<Integer, Entity>();
		gemSettings = new HashMap<String, Map<Integer, GemSettings>>();
		gemChances = new HashMap<Integer, Float[]>();
		animationSheets = new HashMap<Integer, Texture>();
		upgradePrices = new HashMap<Integer, Integer>();
		ailmentAnimationSheets = new HashMap<String, Texture>();
		elementTypes = new HashMap<String, Texture>();
		elementWeaknesses = new HashMap<String, String>();
		elementStrengths = new HashMap<String, String>();
		elementIntToType = new HashMap<Integer, String>();
		
		addAnimationSheets();
		addUpgradePrices();
		populateLevels();
		populateGemSettings();
		populateGemChances();
		populateElements();
	}
	
	public void populateElements() {
		elementTypes.put("blue", new Texture("bluePower.png"));
		elementTypes.put("black", new Texture("blackPower.png"));
		elementTypes.put("green", new Texture("greenPower.png"));
		elementTypes.put("yellow", new Texture("yellowPower.png"));
		elementTypes.put("red", new Texture("redPower.png"));
		
		elementWeaknesses.put("blue", "green");
		elementWeaknesses.put("red", "blue");
		elementWeaknesses.put("yellow", "black");
		elementWeaknesses.put("black", "yellow");
		elementWeaknesses.put("green", "red");
		
		elementStrengths.put("blue", "red");
		elementStrengths.put("red", "green");
		elementStrengths.put("green", "blue");
		elementStrengths.put("black", "yellow");
		elementStrengths.put("yellow", "black");
		
		elementIntToType.put(0, "blue");
		elementIntToType.put(1, "black");
		elementIntToType.put(2, "yellow");
		elementIntToType.put(3, "green");
	}
	
	public void addUpgradePrices() {
		upgradePrices.put(1, 50);
		upgradePrices.put(2, 80);
		upgradePrices.put(3, 100);
		upgradePrices.put(4, 100);
		upgradePrices.put(5, 120);
		upgradePrices.put(6, 120);
		upgradePrices.put(7, 150);
		upgradePrices.put(8, 150);
		upgradePrices.put(9, 180);
	}
	
	public void addAnimationSheets() {
		animationSheets.put(1, new Texture("monsterSheet1.png"));
		animationSheets.put(2, new Texture("monsterSheet2.png"));
		animationSheets.put(5, new Texture("flyingMonsterSheet1.png"));
		
		ailmentAnimationSheets.put("slow", new Texture("slowAnimation.png"));
		ailmentAnimationSheets.put("poison", new Texture("poisonAnimation.png"));
	}
	
	public void populateLevels() {
		levels.put(1, new Entity(2, 50, 10, 300, 2, animationSheets.get(1), false));
		levels.put(2, new Entity(2, 100, 10, 300, 2, animationSheets.get(2), false));
		levels.put(3, new Entity(2, 200, 10, 300, 2, animationSheets.get(1), false));
		levels.put(4, new Entity(2, 400, 10, 300, 2, animationSheets.get(2), false));
		levels.put(6, new Entity(2, 800, 10, 300, 3, animationSheets.get(2), false));
		levels.put(7, new Entity(2, 1000, 10, 300, 3, animationSheets.get(1), false));
		levels.put(8, new Entity(2, 1200, 10, 300, 3, animationSheets.get(2), false));
		levels.put(9, new Entity(2, 1400, 10, 300, 3, animationSheets.get(1), false));
		levels.put(11, new Entity(4, 2000, 10, 270, 4, animationSheets.get(1), false));
		levels.put(12, new Entity(4, 2400, 10, 270, 4, animationSheets.get(2), false));
		levels.put(13, new Entity(4, 2800, 10, 250, 4, animationSheets.get(1), false));
		levels.put(14, new Entity(4, 3200, 10, 250, 4, animationSheets.get(2), false));
		levels.put(16, new Entity(4, 4800, 10, 200, 4, animationSheets.get(2), false));
		levels.put(17, new Entity(4, 5600, 10, 200, 4, animationSheets.get(1), false));
		levels.put(18, new Entity(4, 6400, 10, 200, 4, animationSheets.get(2), false));
		levels.put(19, new Entity(4, 7200, 10, 200, 4, animationSheets.get(1), false));
		levels.put(21, new Entity(4, 10500, 10, 200, 5, animationSheets.get(1), false));
		levels.put(22, new Entity(4, 12000, 10, 200, 5, animationSheets.get(2), false));
		levels.put(23, new Entity(4, 13500, 10, 200, 5, animationSheets.get(1), false));
		levels.put(24, new Entity(4, 15000, 10, 200, 5, animationSheets.get(2), false));
		levels.put(26, new Entity(4, 25000, 15, 200, 5, animationSheets.get(2), false));
		levels.put(27, new Entity(4, 30000, 15, 200, 5, animationSheets.get(1), false));
		levels.put(28, new Entity(4, 35000, 15, 200, 5, animationSheets.get(2), false));
		levels.put(29, new Entity(4, 40000, 15, 200, 5, animationSheets.get(1), false));
		levels.put(31, new Entity(4, 60000, 20, 200, 6, animationSheets.get(1), false));
		levels.put(32, new Entity(4, 70000, 20, 200, 6, animationSheets.get(2), false));
		levels.put(33, new Entity(4, 80000, 20, 200, 6, animationSheets.get(1), false));
		levels.put(34, new Entity(4, 90000, 20, 200, 6, animationSheets.get(2), false));
		levels.put(36, new Entity(4, 110000, 20, 200, 6, animationSheets.get(2), false));
		levels.put(37, new Entity(4, 120000, 20, 200, 6, animationSheets.get(1), false));
		levels.put(38, new Entity(4, 130000, 20, 200, 6, animationSheets.get(2), false));
		levels.put(39, new Entity(4, 140000, 20, 200, 6, animationSheets.get(1), false));
		levels.put(41, new Entity(4, 200000, 30, 200, 7, animationSheets.get(1), false));
		levels.put(42, new Entity(4, 230000, 30, 200, 7, animationSheets.get(2), false));
		levels.put(43, new Entity(4, 260000, 30, 200, 7, animationSheets.get(1), false));
		levels.put(44, new Entity(4, 290000, 30, 200, 7, animationSheets.get(2), false));
		levels.put(46, new Entity(4, 410000, 30, 200, 8, animationSheets.get(2), false));
		levels.put(47, new Entity(4, 470000, 30, 200, 8, animationSheets.get(1), false));
		levels.put(48, new Entity(4, 600000, 30, 200, 8, animationSheets.get(2), false));
		levels.put(49, new Entity(4, 800000, 30, 200, 8, animationSheets.get(1), false));
		
		levels.put(5, new Entity(2, 300, 10, 300, 2, animationSheets.get(5), true));
		levels.put(10, new Entity(2, 800, 10, 270, 3, animationSheets.get(5), true));
		levels.put(15, new Entity(2, 2000, 10, 200, 4, animationSheets.get(5), true));
		levels.put(20, new Entity(2, 4500, 10, 200, 5, animationSheets.get(5), true));
		levels.put(25, new Entity(2, 10000, 15, 200, 5, animationSheets.get(5), true));
		levels.put(30, new Entity(2, 25000, 20, 200, 6, animationSheets.get(5), true));
		levels.put(35, new Entity(2, 50000, 20, 200, 6, animationSheets.get(5), true));
		levels.put(40, new Entity(2, 90000, 30, 200, 7, animationSheets.get(5), true));
		levels.put(45, new Entity(2, 170000, 30, 200, 8, animationSheets.get(5), true));
		levels.put(50, new Entity(2, 500000, 30, 200, 8, animationSheets.get(5), true));
	}
	
	public void populateGemSettings() {
		Map<Integer, GemSettings> Green = new HashMap<Integer, GemSettings>();
		Green.put(1, new GemSettings(100, 30, 200, 0, 0, 10, 3000, 0));
		Green.put(2, new GemSettings(120, 40, 200, 0, 0, 30, 3000, 0));
		Green.put(3, new GemSettings(150, 50, 200, 0, 0, 80, 4000, 0));
		Green.put(4, new GemSettings(170, 80, 200, 0, 0, 200, 6000, 0));
		Green.put(5, new GemSettings(200, 100, 200, 0, 0, 400, 8000, 0));
		Green.put(6, new GemSettings(250, 150, 200, 0, 0, 600, 10000, 0));
		gemSettings.put("green", Green);
		
		Map<Integer, GemSettings> Yellow = new HashMap<Integer, GemSettings>();
		Yellow.put(1, new GemSettings(100, 10, 300, 0, 0, 0, 0, 0));
		Yellow.put(2, new GemSettings(100, 20, 300, 0, 0, 0, 0, 0));
		Yellow.put(3, new GemSettings(200, 40, 300, 0, 0, 0, 0, 0));
		Yellow.put(4, new GemSettings(200, 60, 300, 0, 0, 0, 0, 0));
		Yellow.put(5, new GemSettings(200, 80, 300, 0, 0, 0, 0, 0));
		Yellow.put(5, new GemSettings(250, 200, 300, 0, 0, 0, 0, 0));
		gemSettings.put("yellow", Yellow);
		
		Map<Integer, GemSettings> Blue = new HashMap<Integer, GemSettings>();
		Blue.put(1, new GemSettings(150, 20, 400, 1, 1000, 0, 0, 0));
		Blue.put(2, new GemSettings(150, 30, 400, 1, 1500, 0, 0, 0));
		Blue.put(3, new GemSettings(150, 40, 400, 2, 1500, 0, 0, 0));
		Blue.put(4, new GemSettings(150, 60, 400, 2, 2000, 0, 0, 0));
		Blue.put(5, new GemSettings(150, 80, 400, 2, 3000, 0, 0, 0));
		Blue.put(5, new GemSettings(200, 150, 400, 3, 4000, 0, 0, 0));
		gemSettings.put("blue", Blue);
		
		Map<Integer, GemSettings> Black = new HashMap<Integer, GemSettings>();
		Black.put(1, new GemSettings(150, 20, 200, 0, 0, 0, 0, 0.1f));
		Black.put(2, new GemSettings(150, 30, 200, 0, 0, 0, 0, 0.2f));
		Black.put(3, new GemSettings(150, 40, 200, 0, 0, 0, 0, 0.3f));
		Black.put(4, new GemSettings(150, 60, 150, 0, 0, 0, 0, 0.4f));
		Black.put(5, new GemSettings(150, 80, 150, 0, 0, 0, 0, 0.5f));
		Black.put(5, new GemSettings(200, 200, 150, 0, 0, 0, 0, 1f));
		gemSettings.put("black", Black);
		
		Map<Integer, GemSettings> White = new HashMap<Integer, GemSettings>();
		White.put(1, new GemSettings(120, 40, 200, 0, 0, 0, 0, 0f));
		White.put(2, new GemSettings(120, 60, 200, 0, 0, 0, 0, 0f));
		White.put(3, new GemSettings(120, 100, 200, 0, 0, 0, 0, 0f));
		White.put(4, new GemSettings(150, 140, 200, 0, 0, 0, 0, 0f));
		White.put(5, new GemSettings(150, 200, 200, 0, 0, 0, 0, 0f));
		White.put(5, new GemSettings(150, 500, 100, 0, 0, 0, 0, 0f));
		gemSettings.put("white", White);
		
		Map<Integer, GemSettings> Pink = new HashMap<Integer, GemSettings>();
		Pink.put(1, new GemSettings(150, 50, 200, 0, 0, 0, 0, 0f));
		Pink.put(2, new GemSettings(170, 70, 200, 0, 0, 0, 0, 0f));
		Pink.put(3, new GemSettings(190, 120, 200, 0, 0, 0, 0, 0f));
		Pink.put(4, new GemSettings(210, 150, 200, 0, 0, 0, 0, 0f));
		Pink.put(5, new GemSettings(250, 250, 200, 0, 0, 0, 0, 0f));
		Pink.put(5, new GemSettings(300, 1000, 200, 0, 0, 0, 0, 0f));
		gemSettings.put("pink", Pink);
	}
	
	public void populateGemChances() {
		Float[] chancesLevel1 = {1f, 0f, 0f, 0f, 0f};
		Float[] chancesLevel2 = {0.8f, 0.2f, 0f, 0f, 0f};
		Float[] chancesLevel3 = {0.6f, 0.3f, 0.1f, 0f, 0f};
		Float[] chancesLevel4 = {0.4f, 0.4f, 0.2f, 0f, 0f};
		Float[] chancesLevel5 = {0.2f, 0.5f, 0.2f, 0.1f, 0f};
		Float[] chancesLevel6 = {0.1f, 0.5f, 0.3f, 0.1f, 0f};
		Float[] chancesLevel7 = {0.1f, 0.3f, 0.4f, 0.2f, 0f};
		Float[] chancesLevel8 = {0.1f, 0.2f, 0.4f, 0.3f, 0f};
		Float[] chancesLevel9 = {0.1f, 0.1f, 0.3f, 0.4f, 0.1f};
		Float[] chancesLevel10 = {0.1f, 0.1f, 0.1f, 0.5f, 0.2f};
		gemChances.put(1, chancesLevel1);
		gemChances.put(2, chancesLevel2);
		gemChances.put(3, chancesLevel3);
		gemChances.put(4, chancesLevel4);
		gemChances.put(5, chancesLevel5);
		gemChances.put(6, chancesLevel6);
		gemChances.put(7, chancesLevel7);
		gemChances.put(8, chancesLevel8);
		gemChances.put(9, chancesLevel9);
		gemChances.put(10, chancesLevel10);
	}
	
	public class Entity {
		public int speed;
		public int hp;
		public int number;
		public int delay;
		public int value;
		public int numberFrames;
		public Texture animationSheet;
		public boolean isFlying;
		
		public Entity(int speed, int hp, int number, int delay, int value, Texture animationSheet, boolean isFlying) {
			this.animationSheet = animationSheet;
			this.numberFrames = animationSheet.getWidth()/RpgGame.TILE_WIDTH;
			this.speed = speed;
			this.hp = hp;
			this.number = number;
			this.delay = delay;
			this.value = value;
			this.isFlying = isFlying;
		}
	}
	
	public class GemSettings {
		public int range;
		public int damage;
		public int delay;
		public int slowAmount;
		public int slowDuration;
		public float increaseSpeedAmount;
		public int poisonAmount;
		public int poisonDuration;
		
		public GemSettings(int range, int damage, int delay, int slowAmount, int slowDuration, int poisonAmount, int poisonDuration, float increaseSpeedAmount) {
			this.range = range;
			this.damage = damage;
			this.delay = delay;
			this.slowAmount = slowAmount;
			this.increaseSpeedAmount = increaseSpeedAmount;
			this.poisonAmount = poisonAmount;
			this.poisonDuration = poisonDuration;
			this.slowDuration = slowDuration;
		}
	}
}
