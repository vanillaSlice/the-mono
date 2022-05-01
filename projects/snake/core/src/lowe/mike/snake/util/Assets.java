package lowe.mike.snake.util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;

/**
 * {@code Assets} provides access to assets, such as {@link Texture}s, used in the <i>Snake</i>
 * game.
 * <p>
 * Instances of {@code Assets} cannot be created.
 *
 * @author Mike Lowe
 */
public final class Assets {

  /*
   * Describe the assets to load in.
   */
  private static final AssetDescriptor<Texture> SPLASH_BACKGROUND_ASSET_DESCRIPTOR
      = new AssetDescriptor<Texture>("splash-background.png", Texture.class);
  private static final AssetDescriptor<FreeTypeFontGenerator> FONT_GENERATOR_ASSET_DESCRIPTOR
      = new AssetDescriptor<FreeTypeFontGenerator>("TECHNOLIN.ttf",
      FreeTypeFontGenerator.class);
  private static final AssetDescriptor<Music> MUSIC_ASSET_DESCRIPTOR
      = new AssetDescriptor<Music>("music.mp3", Music.class);
  private static final AssetDescriptor<TextureAtlas> TEXTURE_ATLAS_ASSET_DESCRIPTOR
      = new AssetDescriptor<TextureAtlas>("textures.atlas", TextureAtlas.class);

  /*
   * Font properties.
   */
  private static final int LARGE_FONT_SIZE = 62;
  private static final int MEDIUM_FONT_SIZE = 36;
  private static final int SMALL_FONT_SIZE = 28;

  /*
   * Music properties.
   */
  private static final float MUSIC_VOLUME = .2f;

  /*
   * Used to load in assets.
   */
  private static AssetManager assetManager;

  /*
   * Assets.
   */
  private static Texture splashBackground;
  private static BitmapFont largeFont;
  private static BitmapFont mediumFont;
  private static BitmapFont smallFont;
  private static Music music;
  private static TextureRegion background;
  private static TextureRegion gameFrame;
  private static TextureRegion largeUpArrow;
  private static TextureRegion largeUpArrowPressed;
  private static TextureRegion largeRightArrow;
  private static TextureRegion largeRightArrowPressed;
  private static TextureRegion largeDownArrow;
  private static TextureRegion largeDownArrowPressed;
  private static TextureRegion largeLeftArrow;
  private static TextureRegion largeLeftArrowPressed;
  private static TextureRegion smallRightArrow;
  private static TextureRegion smallRightArrowPressed;
  private static TextureRegion smallLeftArrow;
  private static TextureRegion smallLeftArrowPressed;
  private static TextureRegion pause;
  private static TextureRegion pausePressed;
  private static TextureRegion block;

  // don't want instances
  private Assets() {
  }

  /**
   * Initialises the {@code Assets}.
   */
  public static void initialise() {
    assetManager = new AssetManager();
    loadSplashBackground();
    loadMainAssets();
  }

  /*
   * Wait until splash background texture is loaded before continuing.
   * This is so we can display the splash screen while the main assets
   * are still being loaded.
   */
  private static void loadSplashBackground() {
    assetManager.load(SPLASH_BACKGROUND_ASSET_DESCRIPTOR);
    assetManager.finishLoading();
    splashBackground = assetManager.get(SPLASH_BACKGROUND_ASSET_DESCRIPTOR);
    // apply smoothing filters
    splashBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
  }

  private static void loadMainAssets() {
    // need this so we can load in fonts
    assetManager.setLoader(FreeTypeFontGenerator.class,
        new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
    assetManager.load(FONT_GENERATOR_ASSET_DESCRIPTOR);
    assetManager.load(MUSIC_ASSET_DESCRIPTOR);
    assetManager.load(TEXTURE_ATLAS_ASSET_DESCRIPTOR);
  }

  /**
   * @return {@code true} if all loading is finished
   */
  public static boolean isFinishedLoading() {
    if (assetManager.update()) {
      loadFonts();
      loadMusic();
      loadTextureRegions();
      return true;
    } else {
      return false;
    }
  }

  private static void loadFonts() {
    FreeTypeFontGenerator fontGenerator = assetManager.get(FONT_GENERATOR_ASSET_DESCRIPTOR);

    FreeTypeFontGenerator.FreeTypeFontParameter parameter
        = new FreeTypeFontGenerator.FreeTypeFontParameter();
    // apply smoothing filters
    parameter.minFilter = Texture.TextureFilter.Linear;
    parameter.magFilter = Texture.TextureFilter.Linear;

    largeFont = loadFont(fontGenerator, parameter, LARGE_FONT_SIZE);
    mediumFont = loadFont(fontGenerator, parameter, MEDIUM_FONT_SIZE);
    smallFont = loadFont(fontGenerator, parameter, SMALL_FONT_SIZE);

    // finished with font generator so dispose it
    assetManager.unload(FONT_GENERATOR_ASSET_DESCRIPTOR.fileName);
  }

  private static BitmapFont loadFont(FreeTypeFontGenerator fontGenerator,
      FreeTypeFontGenerator.FreeTypeFontParameter parameter,
      int fontSize) {
    parameter.size = fontSize;
    return fontGenerator.generateFont(parameter);
  }

  private static void loadMusic() {
    music = assetManager.get(MUSIC_ASSET_DESCRIPTOR);
    music.setVolume(MUSIC_VOLUME);
    music.setLooping(true);
  }

  private static void loadTextureRegions() {
    TextureAtlas textureAtlas = assetManager.get(TEXTURE_ATLAS_ASSET_DESCRIPTOR);
    background = textureAtlas.findRegion("background");
    gameFrame = textureAtlas.findRegion("game-frame");
    largeUpArrow = textureAtlas.findRegion("large-up-arrow");
    largeUpArrowPressed = textureAtlas.findRegion("large-up-arrow-pressed");
    largeRightArrow = textureAtlas.findRegion("large-right-arrow");
    largeRightArrowPressed = textureAtlas.findRegion("large-right-arrow-pressed");
    largeDownArrow = textureAtlas.findRegion("large-down-arrow");
    largeDownArrowPressed = textureAtlas.findRegion("large-down-arrow-pressed");
    largeLeftArrow = textureAtlas.findRegion("large-left-arrow");
    largeLeftArrowPressed = textureAtlas.findRegion("large-left-arrow-pressed");
    smallRightArrow = textureAtlas.findRegion("small-right-arrow");
    smallRightArrowPressed = textureAtlas.findRegion("small-right-arrow-pressed");
    smallLeftArrow = textureAtlas.findRegion("small-left-arrow");
    smallLeftArrowPressed = textureAtlas.findRegion("small-left-arrow-pressed");
    pause = textureAtlas.findRegion("pause");
    pausePressed = textureAtlas.findRegion("pause-pressed");
    block = textureAtlas.findRegion("block");
  }

  /**
   * @return the splash background {@link Texture}
   */
  public static Texture getSplashBackground() {
    return splashBackground;
  }

  /**
   * @return the large {@link BitmapFont}
   */
  public static BitmapFont getLargeFont() {
    return largeFont;
  }

  /**
   * @return the medium {@link BitmapFont}
   */
  public static BitmapFont getMediumFont() {
    return mediumFont;
  }

  /**
   * @return the small {@link BitmapFont}
   */
  public static BitmapFont getSmallFont() {
    return smallFont;
  }

  /**
   * @return the game {@link Music}
   */
  public static Music getMusic() {
    return music;
  }

  /**
   * @return the background {@link TextureRegion}
   */
  public static TextureRegion getBackground() {
    return background;
  }

  /**
   * @return the game frame {@link TextureRegion}
   */
  public static TextureRegion getGameFrame() {
    return gameFrame;
  }

  /**
   * @return the large up arrow {@link TextureRegion}
   */
  public static TextureRegion getLargeUpArrow() {
    return largeUpArrow;
  }

  /**
   * @return the large up arrow pressed {@link TextureRegion}
   */
  public static TextureRegion getLargeUpArrowPressed() {
    return largeUpArrowPressed;
  }

  /**
   * @return the large right arrow {@link TextureRegion}
   */
  public static TextureRegion getLargeRightArrow() {
    return largeRightArrow;
  }

  /**
   * @return the large right arrow pressed {@link TextureRegion}
   */
  public static TextureRegion getLargeRightArrowPressed() {
    return largeRightArrowPressed;
  }

  /**
   * @return the large down arrow {@link TextureRegion}
   */
  public static TextureRegion getLargeDownArrow() {
    return largeDownArrow;
  }

  /**
   * @return the large down arrow pressed {@link TextureRegion}
   */
  public static TextureRegion getLargeDownArrowPressed() {
    return largeDownArrowPressed;
  }

  /**
   * @return the large left arrow {@link TextureRegion}
   */
  public static TextureRegion getLargeLeftArrow() {
    return largeLeftArrow;
  }

  /**
   * @return the large left arrow pressed {@link TextureRegion}
   */
  public static TextureRegion getLargeLeftArrowPressed() {
    return largeLeftArrowPressed;
  }

  /**
   * @return the small right arrow {@link TextureRegion}
   */
  public static TextureRegion getSmallRightArrow() {
    return smallRightArrow;
  }

  /**
   * @return the small right arrow pressed {@link TextureRegion}
   */
  public static TextureRegion getSmallRightArrowPressed() {
    return smallRightArrowPressed;
  }

  /**
   * @return the small left arrow {@link TextureRegion}
   */
  public static TextureRegion getSmallLeftArrow() {
    return smallLeftArrow;
  }

  /**
   * @return the small left arrow pressed {@link TextureRegion}
   */
  public static TextureRegion getSmallLeftArrowPressed() {
    return smallLeftArrowPressed;
  }

  /**
   * @return the pause {@link TextureRegion}
   */
  public static TextureRegion getPause() {
    return pause;
  }

  /**
   * @return the pause pressed {@link TextureRegion}
   */
  public static TextureRegion getPausePressed() {
    return pausePressed;
  }

  /**
   * @return the block {@link TextureRegion}
   */
  public static TextureRegion getBlock() {
    return block;
  }

  /**
   * Disposes the splash background {@link Texture}.
   */
  public static void disposeSplashBackground() {
    if (assetManager.isLoaded(SPLASH_BACKGROUND_ASSET_DESCRIPTOR.fileName)) {
      assetManager.unload(SPLASH_BACKGROUND_ASSET_DESCRIPTOR.fileName);
    }
  }

  /**
   * Dispose the assets loaded in.
   */
  public static void dispose() {
    assetManager.dispose();
    largeFont.dispose();
    mediumFont.dispose();
    smallFont.dispose();
  }
}
