package lowe.mike.blueprintpong;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.utils.Disposable;

/**
 * {@code Assets} provides access to assets, such as {@link Texture}s, used in the <i>Blueprint
 * Pong</i> game.
 *
 * @author Mike Lowe
 */
public final class Assets implements Disposable {

  /*
   * Describe the assets to load in.
   */
  private static final AssetDescriptor<FreeTypeFontGenerator> FONT_GENERATOR_ASSET_DESCRIPTOR
      = new AssetDescriptor<FreeTypeFontGenerator>("BluprintDEMO.otf", FreeTypeFontGenerator.class);
  private static final AssetDescriptor<Texture> SPLASH_BACKGROUND_TEXTURE_ASSET_DESCRIPTOR
      = new AssetDescriptor<Texture>("splash-background.png", Texture.class);
  private static final AssetDescriptor<Texture> BACKGROUND_TEXTURE_ASSET_DESCRIPTOR
      = new AssetDescriptor<Texture>("background.png", Texture.class);
  private static final AssetDescriptor<Texture> BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR
      = new AssetDescriptor<Texture>("button-up.png", Texture.class);
  private static final AssetDescriptor<Texture> BUTTON_DOWN_TEXTURE_ASSET_DESCRIPTOR
      = new AssetDescriptor<Texture>("button-down.png", Texture.class);
  private static final AssetDescriptor<Texture> LINE_TEXTURE_ASSET_DESCRIPTOR
      = new AssetDescriptor<Texture>("line.png", Texture.class);
  private static final AssetDescriptor<Texture> PADDLE_TEXTURE_ASSET_DESCRIPTOR
      = new AssetDescriptor<Texture>("paddle.png", Texture.class);
  private static final AssetDescriptor<Texture> BALL_TEXTURE_ASSET_DESCRIPTOR
      = new AssetDescriptor<Texture>("ball.png", Texture.class);
  private static final AssetDescriptor<Sound> PADDLE_HIT_SOUND_ASSET_DESCRIPTOR
      = new AssetDescriptor<Sound>("paddle-hit.ogg", Sound.class);
  private static final AssetDescriptor<Sound> WALL_HIT_SOUND_ASSET_DESCRIPTOR
      = new AssetDescriptor<Sound>("wall-hit.ogg", Sound.class);
  private static final AssetDescriptor<Sound> POINT_SCORED_SOUND_ASSET_DESCRIPTOR
      = new AssetDescriptor<Sound>("point-scored.ogg", Sound.class);

  /*
   * Font properties.
   */
  private static final Color FONT_COLOUR = Color.WHITE;
  private static final int EXTRA_LARGE_FONT_SIZE = 252;
  private static final int LARGE_FONT_SIZE = 162;
  private static final int MEDIUM_FONT_SIZE = 108;

  private final AssetManager assetManager = new AssetManager();
  private BitmapFont extraLargeFont;
  private BitmapFont largeFont;
  private BitmapFont mediumFont;

  /**
   * Creates a new {@code Assets} instance.
   */
  Assets() {
    loadSplashBackgroundTexture();
    loadMainAssets();
  }

  /*
   * Wait until splash background texture is loaded before continuing.
   * This is so we can display the splash screen while the main assets
   * are still being loaded.
   */
  private void loadSplashBackgroundTexture() {
    loadAsset(SPLASH_BACKGROUND_TEXTURE_ASSET_DESCRIPTOR);
    assetManager.finishLoading();
    addSmoothingFilter(getSplashBackgroundTexture());
  }

  private void loadAsset(AssetDescriptor... assetDescriptors) {
    for (AssetDescriptor assetDescriptor : assetDescriptors) {
      assetManager.load(assetDescriptor);
    }
  }

  private static void addSmoothingFilter(Texture... textures) {
    for (Texture texture : textures) {
      texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
  }

  private void loadMainAssets() {
    // need this so we can load in fonts
    assetManager.setLoader(
        FreeTypeFontGenerator.class,
        new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver())
    );
    loadAsset(
        FONT_GENERATOR_ASSET_DESCRIPTOR,
        BACKGROUND_TEXTURE_ASSET_DESCRIPTOR,
        BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR,
        BUTTON_DOWN_TEXTURE_ASSET_DESCRIPTOR,
        LINE_TEXTURE_ASSET_DESCRIPTOR,
        PADDLE_TEXTURE_ASSET_DESCRIPTOR,
        BALL_TEXTURE_ASSET_DESCRIPTOR,
        PADDLE_HIT_SOUND_ASSET_DESCRIPTOR,
        WALL_HIT_SOUND_ASSET_DESCRIPTOR,
        POINT_SCORED_SOUND_ASSET_DESCRIPTOR
    );
  }

  /**
   * @return {@code true} if all loading is finished
   */
  public boolean isFinishedLoading() {
    if (assetManager.update()) {
      loadFonts();
      addSmoothingFilter(
          getBackgroundTexture(),
          getLineTexture(),
          getPaddleTexture(),
          getBallTexture()
      );
      return true;
    } else {
      return false;
    }
  }

  private void loadFonts() {
    FreeTypeFontGenerator fontGenerator = assetManager.get(FONT_GENERATOR_ASSET_DESCRIPTOR);

    FreeTypeFontGenerator.FreeTypeFontParameter parameter =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.color = FONT_COLOUR;
    // apply smoothing filters
    parameter.minFilter = Texture.TextureFilter.Linear;
    parameter.magFilter = Texture.TextureFilter.Linear;

    extraLargeFont = loadFont(fontGenerator, parameter, EXTRA_LARGE_FONT_SIZE);
    largeFont = loadFont(fontGenerator, parameter, LARGE_FONT_SIZE);
    mediumFont = loadFont(fontGenerator, parameter, MEDIUM_FONT_SIZE);

    // finished with font generator so dispose it
    assetManager.unload(FONT_GENERATOR_ASSET_DESCRIPTOR.fileName);
  }

  private BitmapFont loadFont(FreeTypeFontGenerator fontGenerator,
      FreeTypeFontGenerator.FreeTypeFontParameter parameter,
      int fontSize) {
    parameter.size = fontSize;
    BitmapFont font = fontGenerator.generateFont(parameter);
    Scaling.scaleFont(font);
    return font;
  }

  /**
   * @return the extra large {@link BitmapFont}
   */
  public BitmapFont getExtraLargeFont() {
    return extraLargeFont;
  }

  /**
   * @return the large {@link BitmapFont}
   */
  public BitmapFont getLargeFont() {
    return largeFont;
  }

  /**
   * @return the medium {@link BitmapFont}
   */
  public BitmapFont getMediumFont() {
    return mediumFont;
  }

  /**
   * @return the splash background {@link Texture}
   */
  public Texture getSplashBackgroundTexture() {
    return assetManager.get(SPLASH_BACKGROUND_TEXTURE_ASSET_DESCRIPTOR);
  }

  /**
   * @return the background {@link Texture}
   */
  public Texture getBackgroundTexture() {
    return assetManager.get(BACKGROUND_TEXTURE_ASSET_DESCRIPTOR);
  }

  /**
   * @return the button up {@link Texture}
   */
  public Texture getButtonUpTexture() {
    return assetManager.get(BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR);
  }

  /**
   * @return the button down {@link Texture}
   */
  public Texture getButtonDownTexture() {
    return assetManager.get(BUTTON_DOWN_TEXTURE_ASSET_DESCRIPTOR);
  }

  /**
   * @return the line {@link Texture}
   */
  public Texture getLineTexture() {
    return assetManager.get(LINE_TEXTURE_ASSET_DESCRIPTOR);
  }

  /**
   * @return the paddle {@link Texture}
   */
  public Texture getPaddleTexture() {
    return assetManager.get(PADDLE_TEXTURE_ASSET_DESCRIPTOR);
  }

  /**
   * @return the ball {@link Texture}
   */
  public Texture getBallTexture() {
    return assetManager.get(BALL_TEXTURE_ASSET_DESCRIPTOR);
  }

  /**
   * @return the paddle hit {@link Sound}
   */
  public Sound getPaddleHitSound() {
    return assetManager.get(PADDLE_HIT_SOUND_ASSET_DESCRIPTOR);
  }

  /**
   * @return the wall hit {@link Sound}
   */
  public Sound getWallHitSound() {
    return assetManager.get(WALL_HIT_SOUND_ASSET_DESCRIPTOR);
  }

  /**
   * @return the point scored {@link Sound}
   */
  public Sound getPointScoredSound() {
    return assetManager.get(POINT_SCORED_SOUND_ASSET_DESCRIPTOR);
  }

  /**
   * Disposes the splash background {@link Texture}.
   */
  public void disposeSplashBackgroundTexture() {
    if (assetManager.isLoaded(SPLASH_BACKGROUND_TEXTURE_ASSET_DESCRIPTOR.fileName)) {
      assetManager.unload(SPLASH_BACKGROUND_TEXTURE_ASSET_DESCRIPTOR.fileName);
    }
  }

  @Override
  public void dispose() {
    assetManager.dispose();
    extraLargeFont.dispose();
    largeFont.dispose();
    mediumFont.dispose();
  }
}
