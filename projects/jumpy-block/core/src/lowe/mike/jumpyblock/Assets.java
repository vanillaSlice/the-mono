package lowe.mike.jumpyblock;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code Assets} provides access to assets, such as {@link Texture}s, used in the <i>Jumpy
 * Block</i> game.
 *
 * @author Mike Lowe
 */
public final class Assets {

  private static final AssetDescriptor<Music> MUSIC_ASSET_DESCRIPTOR =
      new AssetDescriptor<Music>("music.mp3", Music.class);
  private static final AssetDescriptor<FreeTypeFontGenerator> FONT_GENERATOR_ASSET_DESCRIPTOR
      = new AssetDescriptor<FreeTypeFontGenerator>("font.ttf", FreeTypeFontGenerator.class);
  private static final AssetDescriptor<Texture> BLOCK_TEXTURE_ASSET_DESCRIPTOR
      = new AssetDescriptor<Texture>("block.png", Texture.class);
  private static final AssetDescriptor<Texture> GROUND_SECTION_TEXTURE_ASSET_DESCRIPTOR
      = new AssetDescriptor<Texture>("ground-section.png", Texture.class);
  private static final AssetDescriptor<Texture> WALL_TEXTURE_ASSET_DESCRIPTOR
      = new AssetDescriptor<Texture>("wall.png", Texture.class);

  private static final float MUSIC_VOLUME = .2f;
  private static final FreeTypeFontGenerator.FreeTypeFontParameter FONT_PARAMETER
      = new FreeTypeFontGenerator.FreeTypeFontParameter();
  private static final float FONT_LINE_HEIGHT = 55f;

  static {
    FONT_PARAMETER.color = Color.BLACK;
    FONT_PARAMETER.borderWidth = 3f;
    FONT_PARAMETER.borderColor = Color.GREEN;
  }

  public final Music music;
  public final Texture blockTexture;
  public final Texture groundSectionTexture;
  public final Texture wallTexture;

  private final AssetManager assetManager = new AssetManager();
  private final FreeTypeFontGenerator fontGenerator;
  private final Map<Integer, BitmapFont> fonts = new HashMap<Integer, BitmapFont>();

  /**
   * Creates a new {@code Assets} instance and loads the assets used throughout the game.
   */
  public Assets() {
    // use asset manager to load in assets
    FreeTypeFontGeneratorLoader loader =
        new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver());
    this.assetManager.setLoader(FreeTypeFontGenerator.class, loader);
    this.assetManager.load(MUSIC_ASSET_DESCRIPTOR);
    this.assetManager.load(FONT_GENERATOR_ASSET_DESCRIPTOR);
    this.assetManager.load(BLOCK_TEXTURE_ASSET_DESCRIPTOR);
    this.assetManager.load(GROUND_SECTION_TEXTURE_ASSET_DESCRIPTOR);
    this.assetManager.load(WALL_TEXTURE_ASSET_DESCRIPTOR);

    // wait for asset manager to finish loading
    this.assetManager.finishLoading();

    // assign loaded assets to variables
    this.music = this.assetManager.get(MUSIC_ASSET_DESCRIPTOR);
    this.music.setVolume(MUSIC_VOLUME);
    this.music.setLooping(true);
    this.fontGenerator = this.assetManager.get(FONT_GENERATOR_ASSET_DESCRIPTOR);
    this.blockTexture = this.assetManager.get(BLOCK_TEXTURE_ASSET_DESCRIPTOR);
    this.groundSectionTexture = this.assetManager.get(GROUND_SECTION_TEXTURE_ASSET_DESCRIPTOR);
    this.wallTexture = this.assetManager.get(WALL_TEXTURE_ASSET_DESCRIPTOR);
  }

  /**
   * @param size size of the font to generate
   * @return a {@link BitmapFont}
   */
  public BitmapFont generateFont(int size) {
    // use cached version if it exists
    if (fonts.containsKey(size)) {
      return fonts.get(size);
    }
    FONT_PARAMETER.size = size;
    BitmapFont font = fontGenerator.generateFont(FONT_PARAMETER);
    fonts.put(size, font);
    font.getData().setLineHeight(FONT_LINE_HEIGHT);
    return font;
  }

  /**
   * Dispose all assets.
   */
  public void dispose() {
    assetManager.dispose();
    // need to do this as the asset manager doesn't handle font disposal
    for (BitmapFont font : fonts.values()) {
      font.dispose();
    }
  }
}
