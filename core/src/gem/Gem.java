package gem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.mygdx.game.RpgGame;

public class Gem implements IGem {

	private SpriteBatch batch;
	private TextureRegion texture;
	private int posX;
	private int posY;
	private TextButton button;

	public Gem(SpriteBatch batch, Stage stage, Texture texture, int posX, int posY) {
		this.texture = new TextureRegion(texture);
		this.texture.setRegionWidth(RpgGame.WIDTH/50);
		this.texture.setRegionHeight(RpgGame.HEIGHT/30);
		this.batch = batch;
		this.posX = posX;
		this.posY = posY;
		createButton();
		stage.addActor(button);
	}

	public void createButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		button = new TextButton("", style);
		button.setHeight(texture.getRegionHeight());
		button.setWidth(texture.getRegionWidth());
		button.setPosition(posX, posY);

		button.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				return false;
			}

			@Override
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				
			}
		});
	}

	@Override
	public void render() {
		batch.draw(texture, posX, posY);
	}

}
