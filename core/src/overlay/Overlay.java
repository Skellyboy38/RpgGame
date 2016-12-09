package overlay;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import gem.GemHandler;
import gem.IGem;
import monster.Summoner;
import player.Player;
import tile.TileClickHandler;

public class Overlay {
	
	private Texture background;
	private Texture infoArea;
	private TileClickHandler clickHandler;
	private Summoner summoner;
	private SpriteBatch batch;
	private BitmapFont font;
	private Player player;
	private TextButton shopButton;
	private TextButton monsterPathButton;
	private TextButton gemChancesButton;
	private Texture shopButtonTexture;
	private Texture gemChancesTexture;
	private Stage stage;
	private boolean showGemChances;
	private GemHandler gemHandler;
	
	public Overlay(TileClickHandler clickHandler, Summoner summoner, Player player, GemHandler gemHandler) {
		this.clickHandler = clickHandler;
		this.gemHandler = gemHandler;
		this.summoner = summoner;
		this.showGemChances = false;
		batch = new SpriteBatch();
		background = new Texture("overlay.png");
		infoArea = new Texture("infoArea.png");
		shopButtonTexture = new Texture("shopButton.png");
		gemChancesTexture = new Texture("gemChances.png");
		font = new BitmapFont();
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.player = player;
		this.stage = new Stage();
		createShopButton();
		createMonsterPathButton();
		createGemChancesButton();
		stage.addActor(shopButton);
		stage.addActor(monsterPathButton);
		stage.addActor(gemChancesButton);
	}
	
	public void createGemChancesButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		gemChancesButton = new TextButton("", style);
		gemChancesButton.setHeight(50);
		gemChancesButton.setWidth(100);
		gemChancesButton.setPosition(640, 30);

		gemChancesButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				showGemChances = !showGemChances;
				return false;
			}
		});
	}
	
	public void createShopButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		shopButton = new TextButton("", style);
		shopButton.setHeight(50);
		shopButton.setWidth(100);
		shopButton.setPosition(880, 30);

		shopButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return false;
			}
		});
	}
	
	public void createMonsterPathButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		monsterPathButton = new TextButton("", style);
		monsterPathButton.setHeight(50);
		monsterPathButton.setWidth(100);
		monsterPathButton.setPosition(760, 30);

		monsterPathButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				summoner.toggleDisplayPath();
				return false;
			}
		});
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void render() {
		String shopMessage = "Shop" + "\nGold: " + player.getMoney() + "g";
		String pathMessage = "Show \nMonster Path";
		String gemChancesMessage = "Show Gem \nChances (%)";
		batch.begin();
		Color c = batch.getColor();
		batch.setColor(c.r, c.g, c.b, 0.6f);
		batch.draw(background, 0, 0);
		batch.setColor(c.r, c.g, c.b, 1f);
		batch.draw(shopButtonTexture, 880, 30);
		batch.draw(shopButtonTexture, 760, 30);
		batch.draw(shopButtonTexture, 640, 30);
		font.setColor(Color.YELLOW);
		font.getData().setScale(1f);
		font.draw(batch, "Current level: " + summoner.getLevel() + "\nCurrent HP: " + player.getHealth(), 10, 590);
		font.draw(batch, shopMessage, 890, 70);
		font.draw(batch, pathMessage, 766, 70);
		font.draw(batch, gemChancesMessage, 648, 70);
		if(clickHandler.getClickedGem() != null) {
			IGem gem = clickHandler.getClickedGem();
			TextureRegion texture = new TextureRegion(gem.getTexture());
			texture.setRegionWidth(50);
			texture.setRegionHeight(50);
			int damage = gem.getDamage();
			int speed = gem.getSpeed();
			int range = gem.getRange();
			int level = gem.getLevel();
			String name = gem.getName();
			String description = gem.getDescription();
			String info = "Name: " + name + "\nLevel: " + level + "\nDescription: " + description + "\nDamage: " + damage + "\nSpeed: " + speed + "\nRange: " + range;
			batch.draw(infoArea, 10, 10);
			batch.draw(texture, 400, 25);
			font.setColor(Color.BLACK);
			font.getData().setScale(0.7f);
			font.draw(batch, info, 16, 84);
		}
		else if(clickHandler.getClickedRock() != null) {
			String info = "I am a rock.";
			TextureRegion texture = new TextureRegion(clickHandler.getClickedRock().getTexture());
			texture.setRegionWidth(50);
			texture.setRegionHeight(50);
			batch.draw(infoArea, 10, 10);
			batch.draw(texture, 400, 25);
			font.setColor(Color.BLACK);
			font.getData().setScale(0.7f);
			font.draw(batch, info, 30, 80);
		}
		if(showGemChances) {
			Float[] gemChances = gemHandler.getGemChances();
			batch.draw(gemChancesTexture, 350, 110);
			font.setColor(Color.RED);
			font.getData().setScale(1.2f);
			font.draw(batch, gemChances[4]*100 + "%", 490, 216);
			font.draw(batch, gemChances[3]*100 + "%", 490, 282);
			font.draw(batch, gemChances[2]*100 + "%", 490, 348);
			font.draw(batch, gemChances[1]*100 + "%", 490, 414);
			font.draw(batch, gemChances[0]*100 + "%", 490, 480);
		}
		batch.end();
		stage.act();
	}
}
