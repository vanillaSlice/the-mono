package lowe.mike.snake.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

/**
 * {@code Utils} provides useful helper methods that are repeatedly used in the game.
 * <p>
 * Instances of {@code Utils} cannot be created.
 *
 * @author Mike Lowe
 */
public final class Utils {

  private static final Color PRIMARY_FONT_COLOR = new Color(0xffffffff);
  private static final Color SECONDARY_FONT_COLOR = new Color(0x727272ff);

  // don't want instances
  private Utils() {
  }

  /**
   * Creates a {@link Label} with the given {@link BitmapFont} and text.
   *
   * @param font the {@link BitmapFont}
   * @param text text to initialise the {@link Label} with
   * @return the {@link Label}
   */
  public static Label createTextLabel(BitmapFont font, String text) {
    Label.LabelStyle style = new Label.LabelStyle();
    style.font = font;
    style.fontColor = PRIMARY_FONT_COLOR;
    Label label = new Label(text, style);
    label.setAlignment(Align.center);
    return label;
  }

  /**
   * Creates a {@link Label} with the given {@link BitmapFont} and number.
   *
   * @param font the {@link BitmapFont}
   * @param number number to initialise the {@link Label} with
   * @return the {@link Label}
   */
  public static Label createNumberLabel(BitmapFont font, int number) {
    return createTextLabel(font, Integer.toString(number));
  }

  /**
   * Sets a {@link Label}'s text to a given number.
   *
   * @param label the {@link Label} to update
   * @param number the number to set the {@link Label}'s text to
   */
  public static void updateNumberLabel(Label label, int number) {
    label.setText(Integer.toString(number));
  }

  /**
   * Creates a {@link TextButton} with the given {@link BitmapFont} and text.
   *
   * @param font the {@link BitmapFont}
   * @param text text to initialise the {@link TextButton} with
   * @return the {@link TextButton}
   */
  public static TextButton createTextButton(BitmapFont font, String text) {
    return createTextButton(font, text, false);
  }

  private static TextButton createTextButton(BitmapFont font, String text, boolean checkable) {
    TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
    style.fontColor = PRIMARY_FONT_COLOR;
    style.downFontColor = SECONDARY_FONT_COLOR;
    style.overFontColor = style.downFontColor;
    if (checkable) {
      style.checkedFontColor = style.downFontColor;
    }
    style.font = font;
    TextButton button = new TextButton(text, style);
    button.align(Align.center);
    return button;
  }

  /**
   * Creates a checkable {@link TextButton} with the given {@link BitmapFont} and text.
   *
   * @param font the {@link BitmapFont}
   * @param text text to initialise the {@link TextButton} with
   * @return the {@link TextButton}
   */
  public static TextButton createCheckableTextButton(BitmapFont font, String text) {
    return createTextButton(font, text, true);
  }

  /**
   * Creates an {@link ImageButton} with the given up and down {@link TextureRegion}s.
   *
   * @param up the up {@link TextureRegion}
   * @param down the down {@link TextureRegion}
   * @return the {@link ImageButton}
   */
  public static ImageButton createImageButton(TextureRegion up, TextureRegion down) {
    ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
    style.imageUp = new TextureRegionDrawable(up);
    style.imageDown = new TextureRegionDrawable(down);
    style.imageOver = style.imageDown;
    ImageButton button = new ImageButton(style);
    button.align(Align.center);
    return button;
  }

  /**
   * Play or stop a {@link Music} instance depending on the value passed in.
   *
   * @param music the {@link Music} instance
   * @param playMusic if the {@link Music} should be played
   */
  public static void playMusic(Music music, boolean playMusic) {
    if (playMusic) {
      music.play();
    } else {
      music.stop();
    }
  }

  /**
   * @return a simple {@link Table} menu
   */
  public static Table createMenu() {
    Table menu = new Table();
    menu.setFillParent(true);
    menu.center();
    return menu;
  }
}
