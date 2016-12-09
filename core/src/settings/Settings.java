package settings;

import java.util.HashMap;
import java.util.Map;

public class Settings {
	
	public static Map<Integer, Entity> levels;
	public static Map<String, Map<Integer, GemSettings>> gemSettings;
	public static Map<Integer, Float[]> gemChances;

	public Settings() {
		levels = new HashMap<Integer, Entity>();
		gemSettings = new HashMap<String, Map<Integer, GemSettings>>();
		gemChances = new HashMap<Integer, Float[]>();
		
		populateLevels();
		populateGemSettings();
		populateGemChances();
	}
	
	public void populateLevels() {
		levels.put(1, new Entity(2, 5, 10, 300, 2));
		levels.put(2, new Entity(2, 10, 10, 300, 2));
		levels.put(3, new Entity(2, 20, 10, 300, 2));
		levels.put(4, new Entity(2, 40, 10, 300, 2));
		levels.put(5, new Entity(2, 60, 10, 300, 2));
		levels.put(6, new Entity(2, 80, 10, 300, 3));
		levels.put(7, new Entity(2, 100, 10, 300, 3));
		levels.put(8, new Entity(2, 120, 10, 300, 3));
		levels.put(9, new Entity(2, 140, 10, 300, 3));
		levels.put(10, new Entity(4, 160, 10, 270, 3));
		levels.put(11, new Entity(4, 200, 10, 270, 4));
		levels.put(12, new Entity(4, 240, 10, 270, 4));
		levels.put(13, new Entity(4, 280, 10, 250, 4));
		levels.put(14, new Entity(4, 320, 10, 250, 4));
		levels.put(15, new Entity(4, 400, 10, 200, 4));
		levels.put(16, new Entity(4, 480, 10, 200, 4));
		levels.put(17, new Entity(4, 560, 10, 200, 4));
		levels.put(18, new Entity(4, 640, 10, 200, 4));
		levels.put(19, new Entity(4, 720, 10, 200, 4));
		levels.put(20, new Entity(4, 900, 10, 200, 5));
		levels.put(21, new Entity(4, 1050, 10, 200, 5));
		levels.put(22, new Entity(4, 1200, 10, 200, 5));
		levels.put(23, new Entity(4, 1350, 10, 200, 5));
		levels.put(24, new Entity(4, 1500, 10, 200, 5));
		levels.put(25, new Entity(4, 2000, 15, 200, 5));
		levels.put(26, new Entity(4, 2500, 15, 200, 5));
		levels.put(27, new Entity(4, 3000, 15, 200, 5));
		levels.put(28, new Entity(4, 3500, 15, 200, 5));
		levels.put(29, new Entity(4, 4000, 15, 200, 5));
		levels.put(30, new Entity(4, 5000, 20, 200, 6));
		levels.put(31, new Entity(4, 6000, 20, 200, 6));
		levels.put(32, new Entity(4, 7000, 20, 200, 6));
		levels.put(33, new Entity(4, 8000, 20, 200, 6));
		levels.put(34, new Entity(4, 9000, 20, 200, 6));
		levels.put(35, new Entity(4, 10000, 20, 200, 6));
		levels.put(36, new Entity(4, 11000, 20, 200, 6));
		levels.put(37, new Entity(4, 12000, 20, 200, 6));
		levels.put(38, new Entity(4, 13000, 20, 200, 6));
		levels.put(39, new Entity(4, 14000, 20, 200, 6));
		levels.put(40, new Entity(4, 17000, 30, 200, 7));
		levels.put(41, new Entity(4, 20000, 30, 200, 7));
		levels.put(42, new Entity(4, 23000, 30, 200, 7));
		levels.put(43, new Entity(4, 26000, 30, 200, 7));
		levels.put(44, new Entity(4, 29000, 30, 200, 7));
		levels.put(45, new Entity(4, 35000, 30, 200, 8));
		levels.put(46, new Entity(4, 41000, 30, 200, 8));
		levels.put(47, new Entity(4, 47000, 30, 200, 8));
		levels.put(48, new Entity(4, 60000, 30, 200, 8));
		levels.put(49, new Entity(4, 80000, 30, 200, 8));
		levels.put(50, new Entity(4, 100000, 30, 200, 8));
	}
	
	public void populateGemSettings() {
		Map<Integer, GemSettings> Green = new HashMap<Integer, GemSettings>();
		Green.put(1, new GemSettings(100, 3, 200));
		Green.put(2, new GemSettings(150, 4, 200));
		Green.put(3, new GemSettings(200, 5, 200));
		Green.put(4, new GemSettings(250, 8, 200));
		Green.put(5, new GemSettings(300, 10, 200));
		gemSettings.put("green", Green);
		
		Map<Integer, GemSettings> Yellow = new HashMap<Integer, GemSettings>();
		Yellow.put(1, new GemSettings(100, 1, 300));
		Yellow.put(2, new GemSettings(100, 2, 300));
		Yellow.put(3, new GemSettings(100, 3, 300));
		Yellow.put(4, new GemSettings(100, 4, 300));
		Yellow.put(5, new GemSettings(100, 5, 300));
		gemSettings.put("yellow", Yellow);
		
		Map<Integer, GemSettings> Blue = new HashMap<Integer, GemSettings>();
		Blue.put(1, new GemSettings(150, 2, 400));
		Blue.put(2, new GemSettings(150, 3, 400));
		Blue.put(3, new GemSettings(150, 4, 400));
		Blue.put(4, new GemSettings(150, 6, 400));
		Blue.put(5, new GemSettings(150, 8, 400));
		gemSettings.put("blue", Blue);
	}
	
	public void populateGemChances() {
		Float[] chancesLevel1 = {1f, 0f, 0f, 0f, 0f};
		Float[] chancesLevel2 = {0.8f, 0.2f, 0f, 0f, 0f};
		Float[] chancesLevel3 = {0.6f, 0.3f, 0.1f, 0f, 0f};
		Float[] chancesLevel4 = {0.4f, 0.4f, 0.2f, 0f, 0f};
		Float[] chancesLevel5 = {0.2f, 0.5f, 0.2f, 0.1f, 0f};
		Float[] chancesLevel6 = {0.1f, 0.5f, 0.3f, 0.1f, 0f};
		Float[] chancesLevel7 = {0f, 0.5f, 0.4f, 0.2f, 0f};
		Float[] chancesLevel8 = {0f, 0.3f, 0.4f, 0.3f, 0f};
		Float[] chancesLevel9 = {0f, 0.1f, 0.3f, 0.5f, 0.1f};
		Float[] chancesLevel10 = {0f, 0f, 0.2f, 0.5f, 0.3f};
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
		
		public Entity(int speed, int hp, int number, int delay, int value) {
			this.speed = speed;
			this.hp = hp;
			this.number = number;
			this.delay = delay;
			this.value = value;
		}
	}
	
	public class GemSettings {
		public int range;
		public int damage;
		public int delay;
		
		public GemSettings(int range, int damage, int delay) {
			this.range = range;
			this.damage = damage;
			this.delay = delay;
		}
	}
}
