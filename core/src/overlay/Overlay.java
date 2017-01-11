package overlay;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
import com.mygdx.game.RpgGame;

import gem.GemHandler;
import gem.IGem;
import gem.SpecialGem;
import monster.Summoner;
import player.Player;
import settings.Settings;
import tile.Coordinate;
import tile.TileClickHandler;

public class Overlay {
	
	public static final int BUTTON_TEXT_PADDING_WIDTH = 6;
	public static final int BUTTON_TEXT_PADDING_HEIGHT = 40;
	public static final int BUTTON_HEIGHT = 50;
	public static final int BUTTON_WIDTH = 100;
	public static final int MINIMIZE_BUTTON_WIDTH = 20;
	public static final int MINIMIZE_BUTTON_HEIGHT = 20;
	public static final int GEM_DISPLAY_WIDTH = 50;
	public static final Coordinate UPGRADE_GEM_CHANCES = new Coordinate(50, 350);
	public static final Coordinate SHOP = new Coordinate(780, 30);
	public static final Coordinate PATH = new Coordinate(660, 30);
	public static final Coordinate GEM_CHANCES = new Coordinate(540, 30);
	public static final Coordinate MINIMIZE_BUTTON_COORDINATE = new Coordinate(900, 70);
	public static final Coordinate INFO_CORNER = new Coordinate(10, 590);
	public static final Coordinate GEM_INFO_AREA = new Coordinate(10, 10);
	public static final Coordinate GEM_INFO_TEXT = new Coordinate(16, 84);
	public static final Coordinate GEM = new Coordinate(400, 25);
	public static final Coordinate GEM_CHANCES_IMAGE = new Coordinate(350, 110);
	public static final Coordinate GEM_CHANCES_1 = new Coordinate(490, 480);
	public static final Coordinate GEM_CHANCES_2 = new Coordinate(490, 414);
	public static final Coordinate GEM_CHANCES_3 = new Coordinate(490, 348);
	public static final Coordinate GEM_CHANCES_4 = new Coordinate(490, 282);
	public static final Coordinate GEM_CHANCES_5 = new Coordinate(490, 216);
	public static final Coordinate UPGRADE_SPECIAL_GEM = new Coordinate(350, 100);

	private Texture background;
	private Texture infoArea;
	private Texture buttonTexture;
	private Texture gemChancesTexture;
	private Texture shopTexture;
	private Texture minimizeTexture;
	private Texture maximizeTexture;
	
	private TileClickHandler clickHandler;
	private Summoner summoner;
	private SpriteBatch batch;
	private BitmapFont font;
	private Player player;
	private Stage stage;
	private GemHandler gemHandler;
	private NumberFormat formatter;
	
	private TextButton shopButton;
	private TextButton monsterPathButton;
	private TextButton gemChancesButton;
	private TextButton minimizeButton;
	private TextButton maximizeButton;
	private TextButton upgradeGemChancesButton;
	private TextButton upgradeSpecialGemButton;
	
	private boolean showGemChances;
	private boolean showShop;
	private boolean isMinimized;
	private String purchaseMessage;

	public Overlay(TileClickHandler clickHandler, Summoner summoner, Player player, GemHandler gemHandler, AssetManager manager) {
		
		this.clickHandler = clickHandler;
		this.gemHandler = gemHandler;
		this.summoner = summoner;
		this.showGemChances = false;
		this.showShop = false;
		this.isMinimized = false;
		this.batch = new SpriteBatch();
		this.background = manager.get("overlay.png", Texture.class);
		this.infoArea = manager.get("infoArea.png", Texture.class);
		this.buttonTexture = manager.get("shopButton.png", Texture.class);
		this.gemChancesTexture = manager.get("gemChances.png", Texture.class);
		this.shopTexture = manager.get("shop.png", Texture.class);
		this.minimizeTexture = manager.get("minimizeButton.png", Texture.class);
		this.maximizeTexture = manager.get("maximizeButton.png", Texture.class);
		this.player = player;
		this.stage = new Stage();
		this.font = new BitmapFont();
		this.formatter = new DecimalFormat("#0.0");
		
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		createShopButton();
		createMonsterPathButton();
		createGemChancesButton();
		createUpgradeGemChancesButton();
		createMinimizeButton();
		createMaximizeButton();
		createUpgradeSpecialGemButton();
		
		stage.addActor(shopButton);
		stage.addActor(monsterPathButton);
		stage.addActor(gemChancesButton);
		stage.addActor(minimizeButton);
		
		purchaseMessage = "";
	}
	
	public void reset() {
		this.showGemChances = false;
		this.showShop = false;
		this.isMinimized = false;
		
		maximizeButton.remove();
		upgradeGemChancesButton.remove();
		
		stage.addActor(shopButton);
		stage.addActor(monsterPathButton);
		stage.addActor(gemChancesButton);
		stage.addActor(minimizeButton);
		purchaseMessage = "";
	}
	
	public void createUpgradeSpecialGemButton() {
		upgradeSpecialGemButton = new TextButton("", RpgGame.BUTTON_STYLE);
		upgradeSpecialGemButton.setHeight(BUTTON_HEIGHT);
		upgradeSpecialGemButton.setWidth(BUTTON_WIDTH);
		upgradeSpecialGemButton.setPosition(UPGRADE_SPECIAL_GEM.getX(), UPGRADE_SPECIAL_GEM.getY());

		upgradeSpecialGemButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				SpecialGem specialGem = (SpecialGem)clickHandler.getClickedGem();
				if(player.canBuy(specialGem.getUpgradePrice())) {
					player.spendMoney(specialGem.getUpgradePrice());
					specialGem.upgrade();
				}
				return false;
			}
		});
	}

	public void createMinimizeButton() {
		minimizeButton = new TextButton("", RpgGame.BUTTON_STYLE);
		minimizeButton.setHeight(MINIMIZE_BUTTON_HEIGHT);
		minimizeButton.setWidth(MINIMIZE_BUTTON_WIDTH);
		minimizeButton.setPosition(MINIMIZE_BUTTON_COORDINATE.getX(), MINIMIZE_BUTTON_COORDINATE.getY());

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
		maximizeButton = new TextButton("", RpgGame.BUTTON_STYLE);
		maximizeButton.setHeight(MINIMIZE_BUTTON_HEIGHT);
		maximizeButton.setWidth(MINIMIZE_BUTTON_WIDTH);
		maximizeButton.setPosition(MINIMIZE_BUTTON_COORDINATE.getX(), MINIMIZE_BUTTON_COORDINATE.getY());

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
		gemChancesButton = new TextButton("", RpgGame.BUTTON_STYLE);
		gemChancesButton.setHeight(BUTTON_HEIGHT);
		gemChancesButton.setWidth(BUTTON_WIDTH);
		gemChancesButton.setPosition(GEM_CHANCES.getX(), GEM_CHANCES.getY());

		gemChancesButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				showGemChances = !showGemChances;
				closeShop();
				return false;
			}
		});
	}
	
	public void createUpgradeGemChancesButton() {
		upgradeGemChancesButton = new TextButton("", RpgGame.BUTTON_STYLE);
		upgradeGemChancesButton.setHeight(BUTTON_HEIGHT);
		upgradeGemChancesButton.setWidth(BUTTON_WIDTH);
		upgradeGemChancesButton.setPosition(UPGRADE_GEM_CHANCES.getX(), UPGRADE_GEM_CHANCES.getY());

		upgradeGemChancesButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(gemHandler.canIncreaseGemChances()) {
					int price = Settings.upgradePrices.get(gemHandler.getGemChancesLevel());
					if(player.canBuy(price)) {
						purchaseMessage = "Purchase successful.";
						player.spendMoney(price);
						gemHandler.increaseGemChances();
					}
					else {
						purchaseMessage = "Not enough money.";
					}
				}
				return false;
			}
		});
	}

	public void createShopButton() {
		shopButton = new TextButton("", RpgGame.BUTTON_STYLE);
		shopButton.setHeight(BUTTON_HEIGHT);
		shopButton.setWidth(BUTTON_WIDTH);
		shopButton.setPosition(SHOP.getX(), SHOP.getY());

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
		monsterPathButton = new TextButton("", RpgGame.BUTTON_STYLE);
		monsterPathButton.setHeight(BUTTON_HEIGHT);
		monsterPathButton.setWidth(BUTTON_WIDTH);
		monsterPathButton.setPosition(PATH.getX(), PATH.getY());

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
		font.draw(batch, "Current level: " + summoner.getLevel() + "\nCurrent HP: " + player.getHealth() + "\nFPS: " + Gdx.graphics.getFramesPerSecond(), INFO_CORNER.getX(), INFO_CORNER.getY());
		if(isMinimized) {
			batch.draw(maximizeTexture, maximizeButton.getX(), maximizeButton.getY());
		}
		else {
			Color c = batch.getColor();
			batch.setColor(c.r, c.g, c.b, 0.6f);
			batch.draw(background, 0, 0);
			batch.setColor(c.r, c.g, c.b, 1f);
			batch.draw(minimizeTexture, minimizeButton.getX(), minimizeButton.getY());
			batch.draw(buttonTexture, SHOP.getX(), SHOP.getY());
			batch.draw(buttonTexture, PATH.getX(), PATH.getY());
			batch.draw(buttonTexture, GEM_CHANCES.getX(), GEM_CHANCES.getY());
			font.draw(batch, shopMessage, SHOP.getX() + BUTTON_TEXT_PADDING_WIDTH, SHOP.getY() + BUTTON_TEXT_PADDING_HEIGHT);
			font.draw(batch, pathMessage, PATH.getX() + BUTTON_TEXT_PADDING_WIDTH, PATH.getY() + BUTTON_TEXT_PADDING_HEIGHT);
			font.draw(batch, gemChancesMessage, GEM_CHANCES.getX() + BUTTON_TEXT_PADDING_WIDTH, GEM_CHANCES.getY() + BUTTON_TEXT_PADDING_HEIGHT);
			if(clickHandler.getClickedGem() != null) {
				IGem gem = clickHandler.getClickedGem();
				TextureRegion texture = new TextureRegion(gem.getTexture());
				texture.setRegionWidth(GEM_DISPLAY_WIDTH);
				texture.setRegionHeight(GEM_DISPLAY_WIDTH);
				int damage = gem.getDamage();
				int speed = gem.getSpeed();
				int range = gem.getRange();
				int level = gem.getLevel();
				String name = gem.getName();
				float critChance = gem.getCritChance();
				String description = gem.getDescription();
				String info = "Name: " + name + 
						"\nDescription: " + description +
						"\nLevel: " + level + "          Damage: " + damage + 
						"\nSpeed: " + speed + "        Range: " + range +
						"\nCrit Chance: " + critChance;
				batch.draw(infoArea, GEM_INFO_AREA.getX(), GEM_INFO_AREA.getY());
				batch.draw(texture, GEM.getX(), GEM.getY());
				font.setColor(Color.BLACK);
				font.getData().setScale(0.7f);
				font.draw(batch, info, GEM_INFO_TEXT.getX(), GEM_INFO_TEXT.getY());
				if(clickHandler.getClickedGem() instanceof SpecialGem) {
					SpecialGem specialGem = (SpecialGem)clickHandler.getClickedGem();
					stage.addActor(upgradeSpecialGemButton);
					font.draw(batch, "Upgrade: "+specialGem.getUpgradePrice(), upgradeSpecialGemButton.getX() + BUTTON_TEXT_PADDING_WIDTH, 
							upgradeSpecialGemButton.getY() + BUTTON_TEXT_PADDING_HEIGHT);
				}
				else {
					upgradeSpecialGemButton.remove();
				}
			}
			else if(clickHandler.getClickedRock() != null) {
				String info = "I am a rock.";
				TextureRegion texture = new TextureRegion(clickHandler.getClickedRock().getTexture());
				texture.setRegionWidth(GEM_DISPLAY_WIDTH);
				texture.setRegionHeight(GEM_DISPLAY_WIDTH);
				batch.draw(infoArea, GEM_INFO_AREA.getX(), GEM_INFO_AREA.getY());
				batch.draw(texture, GEM.getX(), GEM.getY());
				font.setColor(Color.BLACK);
				font.getData().setScale(0.7f);
				font.draw(batch, info, GEM_INFO_TEXT.getX(), GEM_INFO_TEXT.getY());
			}
			if(showGemChances) {
				Float[] gemChances = gemHandler.getGemChances();
				batch.draw(gemChancesTexture, GEM_CHANCES_IMAGE.getX(), GEM_CHANCES_IMAGE.getY());
				font.setColor(Color.RED);
				font.getData().setScale(1.2f);
				font.draw(batch, formatter.format(gemChances[4]*100) + "%", GEM_CHANCES_5.getX(), GEM_CHANCES_5.getY());
				font.draw(batch, formatter.format(gemChances[3]*100) + "%", GEM_CHANCES_4.getX(), GEM_CHANCES_4.getY());
				font.draw(batch, formatter.format(gemChances[2]*100) + "%", GEM_CHANCES_3.getX(), GEM_CHANCES_3.getY());
				font.draw(batch, formatter.format(gemChances[1]*100) + "%", GEM_CHANCES_2.getX(), GEM_CHANCES_2.getY());
				font.draw(batch, formatter.format(gemChances[0]*100) + "%", GEM_CHANCES_1.getX(), GEM_CHANCES_1.getY());
			}
			if(showShop) {
				batch.draw(shopTexture, 0, 100);
				batch.draw(buttonTexture, upgradeGemChancesButton.getX(), upgradeGemChancesButton.getY());
				font.setColor(Color.WHITE);
				font.getData().setScale(2f);
				font.draw(batch, "Upgrade Gem chances! Only " + Settings.upgradePrices.get(gemHandler.getGemChancesLevel()) + "g!", 50, 450);
				font.setColor(Color.YELLOW);
				font.getData().setScale(1f);
				font.draw(batch, "Purchase?\n" + Settings.upgradePrices.get(gemHandler.getGemChancesLevel()) + "g", upgradeGemChancesButton.getX() + 6, upgradeGemChancesButton.getY() + 40);
				if(!gemHandler.canIncreaseGemChances()) {
					purchaseMessage = "Max level gem chances reached.";
					font.setColor(Color.YELLOW);
				}
				else if(purchaseMessage.contains("success")) {
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
