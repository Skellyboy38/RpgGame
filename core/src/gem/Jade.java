package gem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bullets.JadeBullet;
import monster.Monster;
import player.Player;
import settings.Settings;
import tile.TileClickHandler;

public class Jade extends SpecialGem {
	
	private String name;
	private String description;
	private float moneyChance;
	private int moneyAmount;
	private int poisonDamage;
	private int poisonDuration;
	private Player player;

	public Jade(SpriteBatch batch, ShapeRenderer renderer, Stage stage, TileClickHandler clickHandler,
			int posX, int posY, String type, int level, Player player, AssetManager manager) {
		super(batch, renderer, stage, clickHandler, "jade_animation", posX, posY, type, level, manager);
		
		this.player = player;
		this.name = "Jade";
		this.description = "Jade has a chance on hit to earn money and poison.";
		this.moneyChance = 0.05f*level;
		this.moneyAmount = 10*level;
		this.poisonDamage = Settings.gemSettings.get("jade").get(level).poisonAmount;
		this.poisonDuration = Settings.gemSettings.get("jade").get(level).poisonDuration;
	}
	
	@Override
	public int getPoisonDamage() {
		return poisonDamage;
	}
	
	@Override
	public int getPoisonDuration() {
		return poisonDuration;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public void hit(Monster m) {
		if(!m.isDead()) {
			elapsedTime = 0;
			canHit = false;
			bullets.add(new JadeBullet(posX + textureRegion.getRegionWidth()/2, 
					posY + textureRegion.getRegionHeight()/2, m, this, player,
					moneyChance, moneyAmount));
		}
	}

}
