package settings;

import java.util.HashMap;
import java.util.Map;

public class Settings {
	
	public static Map<Integer, Entity> levels;
	public static Map<String, Map<Integer, GemSettings>> gemSettings;

	public Settings() {
		levels = new HashMap<Integer, Entity>();
		gemSettings = new HashMap<String, Map<Integer, GemSettings>>();
		
		populateLevels();
		populateGemSettings();
	}
	
	public void populateLevels() {
		levels.put(1, new Entity(2, 5, 10, 300));
		levels.put(2, new Entity(2, 10, 10, 300));
		levels.put(3, new Entity(2, 15, 10, 300));
		levels.put(4, new Entity(2, 20, 10, 300));
		levels.put(5, new Entity(2, 30, 10, 300));
		levels.put(6, new Entity(2, 40, 10, 300));
		levels.put(7, new Entity(2, 50, 10, 300));
		levels.put(8, new Entity(2, 60, 10, 300));
		levels.put(9, new Entity(2, 70, 10, 300));
		levels.put(10, new Entity(4, 80, 10, 270));
		levels.put(11, new Entity(4, 90, 10, 270));
		levels.put(12, new Entity(4, 100, 10, 270));
		levels.put(13, new Entity(4, 110, 10, 250));
		levels.put(14, new Entity(4, 120, 10, 250));
		levels.put(15, new Entity(4, 150, 10, 200));
		levels.put(16, new Entity(4, 170, 10, 200));
		levels.put(17, new Entity(4, 190, 10, 200));
		levels.put(18, new Entity(4, 210, 10, 200));
		levels.put(19, new Entity(4, 230, 10, 200));
		levels.put(20, new Entity(4, 250, 10, 200));
		levels.put(21, new Entity(4, 300, 10, 200));
		levels.put(22, new Entity(4, 350, 10, 200));
		levels.put(23, new Entity(4, 400, 10, 200));
		levels.put(24, new Entity(4, 450, 10, 200));
		levels.put(25, new Entity(4, 500, 15, 200));
		levels.put(26, new Entity(4, 550, 15, 200));
		levels.put(27, new Entity(4, 560, 15, 200));
		levels.put(28, new Entity(4, 600, 15, 200));
		levels.put(29, new Entity(4, 650, 15, 200));
		levels.put(30, new Entity(4, 700, 20, 200));
		levels.put(31, new Entity(4, 800, 20, 200));
		levels.put(32, new Entity(4, 900, 20, 200));
		levels.put(33, new Entity(4, 1000, 20, 200));
		levels.put(34, new Entity(4, 1100, 20, 200));
		levels.put(35, new Entity(4, 1200, 20, 200));
		levels.put(36, new Entity(4, 1300, 20, 200));
		levels.put(37, new Entity(4, 1400, 20, 200));
		levels.put(38, new Entity(4, 1500, 20, 200));
		levels.put(39, new Entity(4, 1600, 20, 200));
		levels.put(40, new Entity(5, 1700, 30, 200));
		levels.put(41, new Entity(5, 1900, 30, 200));
		levels.put(42, new Entity(5, 2100, 30, 200));
		levels.put(43, new Entity(5, 2300, 30, 200));
		levels.put(44, new Entity(5, 2500, 30, 200));
		levels.put(45, new Entity(5, 3000, 30, 200));
		levels.put(46, new Entity(5, 3500, 30, 200));
		levels.put(47, new Entity(5, 4000, 30, 200));
		levels.put(48, new Entity(5, 5000, 30, 200));
		levels.put(49, new Entity(5, 6000, 30, 200));
		levels.put(50, new Entity(5, 7000, 30, 200));
	}
	
	public void populateGemSettings() {
		Map<Integer, GemSettings> Green = new HashMap<Integer, GemSettings>();
		Green.put(1, new GemSettings(100, 2, 200));
		Green.put(2, new GemSettings(150, 3, 200));
		Green.put(3, new GemSettings(200, 4, 200));
		Green.put(4, new GemSettings(250, 5, 200));
		Green.put(5, new GemSettings(300, 6, 200));
		gemSettings.put("green", Green);
	}
	
	public class Entity {
		public int speed;
		public int hp;
		public int number;
		public int delay;
		
		public Entity(int speed, int hp, int number, int delay) {
			this.speed = speed;
			this.hp = hp;
			this.number = number;
			this.delay = delay;
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
