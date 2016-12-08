package overlay;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gem.IGem;
import monster.Summoner;
import tile.TileClickHandler;

public class Overlay {
	
	private Texture background;
	private Texture infoArea;
	private TileClickHandler clickHandler;
	private Summoner summoner;
	private SpriteBatch batch;
	private BitmapFont font;
	
	public Overlay(TileClickHandler clickHandler, Summoner summoner) {
		this.clickHandler = clickHandler;
		this.summoner = summoner;
		batch = new SpriteBatch();
		background = new Texture("overlay.png");
		infoArea = new Texture("infoArea.png");
		font = new BitmapFont();
	}
	
	public void render() {
		batch.begin();
		Color c = batch.getColor();
		batch.setColor(c.r, c.g, c.b, 0.6f);
		batch.draw(background, 0, 0);
		batch.setColor(c.r, c.g, c.b, 1f);
		font.setColor(Color.BLACK);
		font.getData().setScale(2f);
		font.draw(batch, "Current level: " + summoner.getLevel(), 550, 50);
		if(clickHandler.getClickedGem() != null) {
			IGem gem = clickHandler.getClickedGem();
			TextureRegion texture = new TextureRegion(gem.getTexture());
			texture.setRegionWidth(50);
			texture.setRegionHeight(50);
			int damage = gem.getDamage();
			int speed = gem.getSpeed();
			int range = gem.getRange();
			String name = gem.getName();
			String description = gem.getDescription();
			String info = "Name: " + name + "\nDescription: " + description + "\nDamage: " + damage + "\nSpeed: " + speed + "\nRange: " + range;
			batch.draw(infoArea, 10, 10);
			batch.draw(texture, 300, 25);
			font.setColor(Color.BLACK);
			font.getData().setScale(0.7f);
			font.draw(batch, info, 30, 80);
		}
		else if(clickHandler.getClickedRock() != null) {
			String info = "I am a rock.";
			TextureRegion texture = new TextureRegion(clickHandler.getClickedRock().getTexture());
			texture.setRegionWidth(50);
			texture.setRegionHeight(50);
			batch.draw(infoArea, 10, 10);
			batch.draw(texture, 300, 25);
			font.setColor(Color.BLACK);
			font.getData().setScale(0.7f);
			font.draw(batch, info, 30, 80);
		}
		batch.end();
	}
}
