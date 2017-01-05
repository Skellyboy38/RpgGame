package settings;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
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
	public static Map<String, SpecialCombination> specialGemRecipes;

	public Settings(AssetManager manager) {
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
		specialGemRecipes = new HashMap<String, SpecialCombination>();
		
		addAnimationSheets(manager);
		addUpgradePrices();
		populateLevels();
		populateGemSettings();
		populateGemChances();
		populateElements(manager);
		createSpecialGemRecipes();
	}
	
	public void createSpecialGemRecipes() {
		specialGemRecipes.put("jade", new SpecialCombination(
				"green", 4,
				"blue", 2,
				"black", 3));
		specialGemRecipes.put("black_opal", new SpecialCombination(
				"black", 5,
				"purple", 3,
				"white", 4));
		specialGemRecipes.put("blood_stone", new SpecialCombination(
				"red", 5,
				"purple", 4,
				"pink", 3));
		specialGemRecipes.put("dark_emerald", new SpecialCombination(
				"green", 5,
				"blue", 4,
				"yellow", 2));
		specialGemRecipes.put("gold", new SpecialCombination(
				"pink", 5,
				"pink", 4,
				"white", 2));
		specialGemRecipes.put("malachite", new SpecialCombination(
				"black", 1,
				"green", 1,
				"purple", 1));
		specialGemRecipes.put("pink_diamond", new SpecialCombination(
				"white", 5,
				"yellow", 3,
				"white", 3));
		specialGemRecipes.put("red_crystal", new SpecialCombination(
				"red", 3,
				"green", 2,
				"pink", 1));
		specialGemRecipes.put("silver", new SpecialCombination(
				"yellow", 1,
				"white", 1,
				"blue", 1));
		specialGemRecipes.put("star_ruby", new SpecialCombination(
				"red", 2,
				"red", 1,
				"pink", 1));
		specialGemRecipes.put("uranium", new SpecialCombination(
				"yellow", 5,
				"blue", 3,
				"black", 2));
		specialGemRecipes.put("yellow_sapphire", new SpecialCombination(
				"blue", 5,
				"yellow", 4,
				"red", 4));
		specialGemRecipes.put("paraiba_tourmaline", new SpecialCombination(
				"purple", 5,
				"black", 4,
				"green", 2));
	}
	
	public void populateElements(AssetManager manager) {
		elementTypes.put("blue", manager.get("bluePower.png", Texture.class));
		elementTypes.put("black", manager.get("blackPower.png", Texture.class));
		elementTypes.put("green", manager.get("greenPower.png", Texture.class));
		elementTypes.put("yellow", manager.get("yellowPower.png", Texture.class));
		elementTypes.put("red", manager.get("redPower.png", Texture.class));
		elementTypes.put("white", manager.get("whitePower.png", Texture.class));
		elementTypes.put("purple", manager.get("purplePower.png", Texture.class));
		
		elementWeaknesses.put("blue", "green");
		elementWeaknesses.put("red", "blue");
		elementWeaknesses.put("green", "red");
		elementWeaknesses.put("yellow", "purple");
		elementWeaknesses.put("purple", "yellow");
		elementWeaknesses.put("black", "white");
		elementWeaknesses.put("white", "black");
		
		elementStrengths.put("blue", "red");
		elementStrengths.put("red", "green");
		elementStrengths.put("green", "blue");
		elementStrengths.put("black", "white");
		elementStrengths.put("white", "black");
		elementStrengths.put("yellow", "purple");
		elementStrengths.put("purple", "yellow");
		
		elementIntToType.put(0, "blue");
		elementIntToType.put(1, "black");
		elementIntToType.put(2, "yellow");
		elementIntToType.put(3, "green");
		elementIntToType.put(4, "white");
		elementIntToType.put(5, "red");
		elementIntToType.put(6, "purple");
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
		upgradePrices.put(10, 200);
	}
	
	public void addAnimationSheets(AssetManager manager) {
		animationSheets.put(1, manager.get("monsterSheet1.png", Texture.class));
		animationSheets.put(2, manager.get("monsterSheet2.png", Texture.class));
		animationSheets.put(5, manager.get("flyingMonsterSheet1.png", Texture.class));
		
		ailmentAnimationSheets.put("slow", manager.get("slowAnimation.png", Texture.class));
		ailmentAnimationSheets.put("poison", manager.get("poisonAnimation.png", Texture.class));
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
		Green.put(1, new GemSettings(100, 90, 600, 0, 0, 10, 3000, 0f, 0f));
		Green.put(2, new GemSettings(120, 120, 600, 0, 0, 30, 3000, 0f, 0f));
		Green.put(3, new GemSettings(150, 150, 600, 0, 0, 80, 4000, 0f, 0f));
		Green.put(4, new GemSettings(170, 240, 600, 0, 0, 200, 6000, 0f, 0f));
		Green.put(5, new GemSettings(200, 300, 600, 0, 0, 400, 8000, 0f, 0f));
		Green.put(6, new GemSettings(250, 450, 600, 0, 0, 600, 10000, 0f, 0f));
		gemSettings.put("green", Green);
		
		Map<Integer, GemSettings> Yellow = new HashMap<Integer, GemSettings>();
		Yellow.put(1, new GemSettings(100, 30, 900, 0, 0, 0, 0, 0, 0f));
		Yellow.put(2, new GemSettings(100, 60, 900, 0, 0, 0, 0, 0, 0f));
		Yellow.put(3, new GemSettings(200, 120, 900, 0, 0, 0, 0, 0, 0f));
		Yellow.put(4, new GemSettings(200, 180, 900, 0, 0, 0, 0, 0, 0f));
		Yellow.put(5, new GemSettings(200, 240, 900, 0, 0, 0, 0, 0, 0f));
		Yellow.put(6, new GemSettings(250, 600, 900, 0, 0, 0, 0, 0, 0f));
		gemSettings.put("yellow", Yellow);
		
		Map<Integer, GemSettings> Blue = new HashMap<Integer, GemSettings>();
		Blue.put(1, new GemSettings(150, 60, 1200, 1, 1000, 0, 0, 0, 0f));
		Blue.put(2, new GemSettings(150, 90, 1200, 1, 1500, 0, 0, 0, 0f));
		Blue.put(3, new GemSettings(150, 120, 1200, 2, 1500, 0, 0, 0, 0f));
		Blue.put(4, new GemSettings(150, 180, 1200, 2, 2000, 0, 0, 0, 0f));
		Blue.put(5, new GemSettings(150, 240, 1200, 2, 3000, 0, 0, 0, 0f));
		Blue.put(6, new GemSettings(200, 450, 1200, 3, 4000, 0, 0, 0, 0f));
		gemSettings.put("blue", Blue);
		
		Map<Integer, GemSettings> Black = new HashMap<Integer, GemSettings>();
		Black.put(1, new GemSettings(150, 60, 600, 0, 0, 0, 0, 0.1f, 0f));
		Black.put(2, new GemSettings(150, 90, 600, 0, 0, 0, 0, 0.2f, 0f));
		Black.put(3, new GemSettings(150, 120, 600, 0, 0, 0, 0, 0.3f, 0f));
		Black.put(4, new GemSettings(150, 180, 450, 0, 0, 0, 0, 0.4f, 0f));
		Black.put(5, new GemSettings(150, 240, 450, 0, 0, 0, 0, 0.5f, 0f));
		Black.put(6, new GemSettings(200, 600, 450, 0, 0, 0, 0, 1f, 0f));
		gemSettings.put("black", Black);
		
		Map<Integer, GemSettings> White = new HashMap<Integer, GemSettings>();
		White.put(1, new GemSettings(120, 120, 600, 0, 0, 0, 0, 0f, 0f));
		White.put(2, new GemSettings(120, 180, 600, 0, 0, 0, 0, 0f, 0f));
		White.put(3, new GemSettings(120, 300, 600, 0, 0, 0, 0, 0f, 0f));
		White.put(4, new GemSettings(150, 420, 600, 0, 0, 0, 0, 0f, 0f));
		White.put(5, new GemSettings(150, 600, 600, 0, 0, 0, 0, 0f, 0f));
		White.put(6, new GemSettings(150, 1500, 300, 0, 0, 0, 0, 0f, 0f));
		gemSettings.put("white", White);
		
		Map<Integer, GemSettings> Pink = new HashMap<Integer, GemSettings>();
		Pink.put(1, new GemSettings(150, 150, 600, 0, 0, 0, 0, 0f, 0f));
		Pink.put(2, new GemSettings(170, 210, 600, 0, 0, 0, 0, 0f, 0f));
		Pink.put(3, new GemSettings(190, 360, 600, 0, 0, 0, 0, 0f, 0f));
		Pink.put(4, new GemSettings(210, 450, 600, 0, 0, 0, 0, 0f, 0f));
		Pink.put(5, new GemSettings(250, 750, 600, 0, 0, 0, 0, 0f, 0f));
		Pink.put(6, new GemSettings(300, 3000, 600, 0, 0, 0, 0, 0f, 0f));
		gemSettings.put("pink", Pink);
		
		Map<Integer, GemSettings> Red = new HashMap<Integer, GemSettings>();
		Red.put(1, new GemSettings(150, 60, 600, 0, 0, 0, 0, 0f, 0f));
		Red.put(2, new GemSettings(150, 120, 600, 0, 0, 0, 0, 0f, 0f));
		Red.put(3, new GemSettings(150, 180, 600, 0, 0, 0, 0, 0f, 0f));
		Red.put(4, new GemSettings(150, 240, 600, 0, 0, 0, 0, 0f, 0f));
		Red.put(5, new GemSettings(150, 300, 600, 0, 0, 0, 0, 0f, 0f));
		Red.put(6, new GemSettings(150, 1000, 600, 0, 0, 0, 0, 0f, 0f));
		gemSettings.put("red", Red);
		
		Map<Integer, GemSettings> Purple = new HashMap<Integer, GemSettings>();
		Purple.put(1, new GemSettings(150, 60, 600, 0, 0, 0, 0, 0f, 0.03f));
		Purple.put(2, new GemSettings(150, 120, 600, 0, 0, 0, 0, 0f, 0.06f));
		Purple.put(3, new GemSettings(150, 180, 600, 0, 0, 0, 0, 0f, 0.09f));
		Purple.put(4, new GemSettings(150, 240, 450, 0, 0, 0, 0, 0f, 0.12f));
		Purple.put(5, new GemSettings(150, 300, 450, 0, 0, 0, 0, 0f, 0.15f));
		Purple.put(6, new GemSettings(200, 600, 450, 0, 0, 0, 0, 0f, 0.3f));
		gemSettings.put("purple", Purple);
		
		Map<Integer, GemSettings> Jade = new HashMap<Integer, GemSettings>();
		Jade.put(1, new GemSettings(150, 300, 600, 0, 0, 20, 3000, 0f, 0f));
		Jade.put(2, new GemSettings(180, 600, 600, 0, 0, 100, 5000, 0f, 0f));
		Jade.put(3, new GemSettings(180, 1200, 600, 0, 0, 200, 7000, 0f, 0f));
		gemSettings.put("jade", Jade);
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
		Float[] chancesLevel11 = {0.1f, 0.1f, 0.1f, 0.3f, 0.4f};
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
		gemChances.put(11,  chancesLevel11);
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
		public float critChanceIncrease;
		
		public GemSettings(int range, int damage, int delay, 
				int slowAmount, int slowDuration, int poisonAmount, 
				int poisonDuration, float increaseSpeedAmount, 
				float critChanceIncrease) {
			this.range = range;
			this.damage = damage;
			this.delay = delay;
			this.slowAmount = slowAmount;
			this.increaseSpeedAmount = increaseSpeedAmount;
			this.poisonAmount = poisonAmount;
			this.poisonDuration = poisonDuration;
			this.slowDuration = slowDuration;
			this.critChanceIncrease = critChanceIncrease;
		}
	}
	
	public class SpecialCombination {
		public int level1;
		public int level2;
		public int level3;
		public String type1;
		public String type2;
		public String type3;
		
		public SpecialCombination(String type1, int level1,
				String type2, int level2,
				String type3, int level3) {
			this.level1 = level1;
			this.level2 = level2;
			this.level3 = level3;
			this.type1 = type1;
			this.type2 = type2;
			this.type3 = type3;
		}
	}
}
