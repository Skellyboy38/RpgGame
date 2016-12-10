package overlay;
import com.badlogic.gdx.Gdx;
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
	private TextButton minimizeButton;
	private TextButton maximizeButton;
	private TextButton upgradeGemChancesButton;
	private Texture shopButtonTexture;
	private Texture gemChancesTexture;
	private Texture shopTexture;
	private Texture minimizeTexture;
	private Texture maximizeTexture;
	private Stage stage;
	private boolean showGemChances;
	private boolean showShop;
	private boolean isMinimized;
	private GemHandler gemHandler;
	private String purchaseMessage;

	public Overlay(TileClickHandler clickHandler, Summoner summoner, Player player, GemHandler gemHandler) {
		this.clickHandler = clickHandler;
		this.gemHandler = gemHandler;
		this.summoner = summoner;
		this.showGemChances = false;
		this.showShop = false;
		this.isMinimized = false;
		batch = new SpriteBatch();
		background = new Texture("overlay.png");
		infoArea = new Texture("infoArea.png");
		shopButtonTexture = new Texture("shopButton.png");
		gemChancesTexture = new Texture("gemChances.png");
		shopTexture = new Texture("shop.png");
		minimizeTexture = new Texture("minimizeButton.png");
		maximizeTexture = new Texture("maximizeButton.png");
		font = new BitmapFont();
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.player = player;
		this.stage = new Stage();
		createShopButton();
		createMonsterPathButton();
		createGemChancesButton();
		createUpgradeGemChancesButton();
		createMinimizeButton();
		createMaximizeButton();
		stage.addActor(shopButton);
		stage.addActor(monsterPathButton);
		stage.addActor(gemChancesButton);
		stage.addActor(minimizeButton);
		purchaseMessage = "";
	}

	public void createMinimizeButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		minimizeButton = new TextButton("", style);
		minimizeButton.setHeight(20);
		minimizeButton.setWidth(20);
		minimizeButton.setPosition(900, 70);

		minimizeButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				showGemChances = false;
				closeShop();
				minimize();
				return false;
			}
		});
	}

	public void createMaximizeButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		maximizeButton = new TextButton("", style);
		maximizeButton.setHeight(20);
		maximizeButton.setWidth(20);
		maximizeButton.setPosition(900, 70);

		maximizeButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				maximize();
				return false;
			}
		});
	}

	public void maximize() {
		maximizeButton.remove();
		stage.addActor(shopButton);
		stage.addActor(monsterPathButton);
		stage.addActor(gemChancesButton);
		stage.addActor(minimizeButton);
		isMinimized = false;
	}

	public void minimize() {
		minimizeButton.remove();
		shopButton.remove();
		monsterPathButton.remove();
		gemChancesButton.remove();
		stage.addActor(maximizeButton);
		closeShop();
		isMinimized = true;
	}

	public void openShop() {
		showGemChances = false;
		showShop = true;
		stage.addActor(upgradeGemChancesButton);
	}

	public void closeShop() {
		purchaseMessage = "";
		showShop = false;
		upgradeGemChancesButton.remove();
	}

	public void createGemChancesButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		gemChancesButton = new TextButton("", style);
		gemChancesButton.setHeight(50);
		gemChancesButton.setWidth(100);
		gemChancesButton.setPosition(540, 30);

		gemChancesButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				showGemChances = !showGemChances;
				closeShop();
				return false;
			}
		});
	}
	
	public void createUpgradeGemChancesButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		upgradeGemChancesButton = new TextButton("", style);
		upgradeGemChancesButton.setHeight(50);
		upgradeGemChancesButton.setWidth(100);
		upgradeGemChancesButton.setPosition(50, 350);

		upgradeGemChancesButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(player.canBuy(50)) {
					if(gemHandler.canIncreaseGemChances()) {
						purchaseMessage = "Purchase successful.";
						player.spendMoney(50);
						gemHandler.increaseGemChances();
					}
					else {
						purchaseMessage = "MAX gem chances level.";
					}
				}
				else {
					purchaseMessage = "Not enough money.";
				}
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
		shopButton.setPosition(780, 30);

		shopButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(showShop) {
					closeShop();
				}
				else {
					openShop();
				}
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
		monsterPathButton.setPosition(660, 30);

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
		font.setColor(Color.YELLOW);
		font.getData().setScale(1f);
		font.draw(batch, "Current level: " + summoner.getLevel() + "\nCurrent HP: " + player.getHealth() + "\nFPS: " + Gdx.graphics.getFramesPerSecond(), 10, 590);
		if(isMinimized) {
			batch.draw(maximizeTexture, maximizeButton.getX(), maximizeButton.getY());
		}
		else {
			Color c = batch.getColor();
			batch.setColor(c.r, c.g, c.b, 0.6f);
			batch.draw(background, 0, 0);
			batch.setColor(c.r, c.g, c.b, 1f);
			batch.draw(minimizeTexture, minimizeButton.getX(), minimizeButton.getY());
			batch.draw(shopButtonTexture, 780, 30);
			batch.draw(shopButtonTexture, 660, 30);
			batch.draw(shopButtonTexture, 540, 30);
			font.draw(batch, shopMessage, 786, 70);
			font.draw(batch, pathMessage, 666, 70);
			font.draw(batch, gemChancesMessage, 548, 70);
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
			if(showShop) {
				batch.draw(shopTexture, 0, 100);
				batch.draw(shopButtonTexture, upgradeGemChancesButton.getX(), upgradeGemChancesButton.getY());
				font.setColor(Color.WHITE);
				font.getData().setScale(2f);
				font.draw(batch, "Upgrade Gem chances! Only 50g!", 50, 450);
				font.setColor(Color.YELLOW);
				font.getData().setScale(1f);
				font.draw(batch, "Purchase \n(50g)", upgradeGemChancesButton.getX() + 6, upgradeGemChancesButton.getY() + 40);
				if(purchaseMessage.contains("success")) {
					font.setColor(Color.GREEN);
				}
				else {
					font.setColor(Color.RED);
				}
				font.getData().setScale(2f);
				font.draw(batch, purchaseMessage, 180, 390);
			}
		}
		batch.end();
		stage.act();
	}
}
