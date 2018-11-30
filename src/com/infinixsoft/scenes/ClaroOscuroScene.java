package com.infinixsoft.scenes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import com.infinixsoft.TexturePacker.Box;
import com.infinixsoft.TexturePacker.ClaroOscuro;
import com.infinixsoft.asdraopuestos.GameActivity;
import com.infinixsoft.asdraopuestos.SceneManager;
import com.infinixsoft.utils.SceneType;
import com.infinixsoft.utils.SoundType;
import com.infinixsoft.utils.SpriteType;


public class ClaroOscuroScene {

	private static Scene scene;
	private static GameActivity context;
	private static TextureRegion bgTextureRegion;
	private static TexturePackTextureRegionLibrary mSpritesheetTexturePackTextureRegionLibrary;
	private static TexturePackTextureRegionLibrary mSpritesheetBoxTexturePackTextureRegionLibrary;
	public static Sprite box1BacksideSprite, box2BacksideSprite, box1FrontsideSprite, box2FrontsideSprite;

	// ===========================================================
	// Set Resources
	// ===========================================================

	private static int placedItems = 0, totalItems = 6;
	private static String backgroundPath = "gfx/bgclarooscuro.png";
	private static String texturePackerName = "clarooscuro.xml";
	private static String boxTexturePackerName = "box.xml";

	/**
	 * Load the scene and any assets we need.
	 */
	@SuppressWarnings("static-access")
	public static void load(GameActivity myActivity) {

		context = myActivity;
		placedItems = 0;
		
		try {
			ITexture bgTexture = new BitmapTexture(context.getTextureManager(), new IInputStreamOpener() {
				public InputStream open() throws IOException {
					return context.getAssets().open(backgroundPath);
				}
			});
			bgTexture.load();
			bgTextureRegion = TextureRegionFactory.extractFromTexture(bgTexture);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			final TexturePack spritesheetTexturePack = new TexturePackLoader(context.getTextureManager(), "gfx/spritesheets/")
					.loadFromAsset(context.getAssets(), texturePackerName);
			spritesheetTexturePack.loadTexture();
			mSpritesheetTexturePackTextureRegionLibrary = spritesheetTexturePack.getTexturePackTextureRegionLibrary();
		} catch (final TexturePackParseException e) {
			Debug.e(e);
		}

		try {
			final TexturePack spritesheetBoxTexturePack = new TexturePackLoader(context.getTextureManager(), "gfx/spritesheets/")
					.loadFromAsset(context.getAssets(), boxTexturePackerName);
			spritesheetBoxTexturePack.loadTexture();
			mSpritesheetBoxTexturePackTextureRegionLibrary = spritesheetBoxTexturePack.getTexturePackTextureRegionLibrary();
		} catch (final TexturePackParseException e) {
			Debug.e(e);
		}

		scene = new Scene();

		Background background = new Background(org.andengine.util.color.Color.WHITE);
		scene.setBackground(background);
		Sprite bgSprite = new Sprite(0, 0, bgTextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(bgSprite);
		scene.setOnAreaTouchTraversalFrontToBack();
		
		//getinfobutton
		scene = context.getInfoButtonSprite(scene, SceneType.CLAROOSCURO);

		ClaroOscuro texturePackerInterface = new ClaroOscuro() {
		};

		// Getting texture region from TexturePacker files
		TextureRegion type1TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.TYPE1_ID);
		TextureRegion type2TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.TYPE2_ID);

		TextureRegion boxTxt1TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.BOXTXT1_ID);
		TextureRegion boxTxt2TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.BOXTXT2_ID);

		TextureRegion item1TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.ITEM1_ID);
		TextureRegion item2TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.ITEM2_ID);
		TextureRegion item3TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.ITEM3_ID);
		TextureRegion item4TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.ITEM4_ID);
		TextureRegion item5TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.ITEM5_ID);
		TextureRegion item6TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.ITEM6_ID);

		// Add box backside sprite
		TextureRegion boxBacksideTextureRegion = mSpritesheetBoxTexturePackTextureRegionLibrary.get(Box.BOXBACKSIDE_ID);
		box1BacksideSprite = new Sprite(200f, 328f, boxBacksideTextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(box1BacksideSprite);

		box2BacksideSprite = new Sprite(605f, 328f, boxBacksideTextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(box2BacksideSprite);

		// Add items to scene
		addItem(item1TextureRegion, 98, 94, 1);
		addItem(item2TextureRegion, 262, 133, 2);
		addItem(item3TextureRegion, 362, 123, 1);
		addItem(item5TextureRegion, 605, 90, 2);
		addItem(item4TextureRegion, 493, 105, 2);
		addItem(item6TextureRegion, 778, 111, 1);

		scene.setTouchAreaBindingOnActionDownEnabled(true);

		TextureRegion boxFrontsideTextureRegion = mSpritesheetBoxTexturePackTextureRegionLibrary.get(Box.BOXFRONTSIDE_ID);

		// Add box frontside sprite
		box1FrontsideSprite = new Sprite(167f, 326f, boxFrontsideTextureRegion, context.getVertexBufferObjectManager());
		box1FrontsideSprite.attachChild(new Sprite(144 - boxTxt1TextureRegion.getWidth() / 2, box1FrontsideSprite.getHeight() / 2 + 20,
				boxTxt1TextureRegion, context.getVertexBufferObjectManager()));
		scene.attachChild(box1FrontsideSprite);

		box2FrontsideSprite = new Sprite(572f, 326f, boxFrontsideTextureRegion, context.getVertexBufferObjectManager());
		box2FrontsideSprite.attachChild(new Sprite(144 - boxTxt2TextureRegion.getWidth() / 2, box2FrontsideSprite.getHeight() / 2 + 20,
				boxTxt2TextureRegion, context.getVertexBufferObjectManager()));
		scene.attachChild(box2FrontsideSprite);

		// Add example type sprite
		Sprite type1Sprite = new Sprite(44, 479, type1TextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(type1Sprite);

		Sprite type2Sprite = new Sprite(832, 475, type2TextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(type2Sprite);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private static void addItem(final TextureRegion textureRegion, final int pX, final int pY, final int type) {
		final SpriteType sprite = new SpriteType(pX, pY, textureRegion, context.getVertexBufferObjectManager(), type) {
			boolean mGrabbed = false;

			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					context.playSound(SoundType.PICK);
					this.setScale(1.1f);
					this.mGrabbed = true;
					this.clearEntityModifiers();
					break;
				case TouchEvent.ACTION_MOVE:
					if (this.mGrabbed) {
						this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
					}
					break;
				case TouchEvent.ACTION_UP:
					if (this.mGrabbed) {
						this.mGrabbed = false;
						this.setScale(1f);
						if (this.getType() == 1) {
							if (this.collidesWith(box1FrontsideSprite)) {
								putInBox(this, 1);
							} else {
								this.moveToInitialPosition();
							}
						}
						if (this.getType() == 2) {
							if (this.collidesWith(box2FrontsideSprite)) {
								putInBox(this, 2);
							} else {
								this.moveToInitialPosition();
							}
						}
					}
					break;
				}
				return true;
			}

			private void putInBox(final SpriteType sprite, int box) {

				context.playSound(SoundType.CORRECT);
				
				if (box == 1) {
					float rotation = new Random().nextInt(20) - 10;
					sprite.registerEntityModifier(new RotationModifier(0.3f, sprite.getRotation(), rotation) {
						protected void onModifierFinished(org.andengine.entity.IEntity pItem) {
				
							int max = (int) (195 - (sprite.getWidth()));
							Random rnd = new Random();
							int posX = rnd.nextInt(max) + 45;
							int posY = rnd.nextInt((int) (sprite.getHeight() / 2));
							sprite.registerEntityModifier(new MoveModifier(0.5f, sprite.getX(), box1FrontsideSprite.getX() + posX, sprite
									.getY(), box1FrontsideSprite.getY() - posY));
						};
					});
				} else if (box == 2) {
					float rotation = new Random().nextInt(20) - 10;
					sprite.registerEntityModifier(new RotationModifier(0.3f, sprite.getRotation(), rotation) {
						protected void onModifierFinished(org.andengine.entity.IEntity pItem) {
				
							int max = (int) (195 - (sprite.getWidth()));
							Random rnd = new Random();
							int posX = rnd.nextInt(max) + 45;
							int posY = rnd.nextInt((int) (sprite.getHeight() / 2));
							sprite.registerEntityModifier(new MoveModifier(0.5f, sprite.getX(), box2FrontsideSprite.getX() + posX, sprite
									.getY(), box2FrontsideSprite.getY() - posY));
						};
					});

				}
				
				scene.unregisterTouchArea(sprite);

				placedItems = placedItems + 1;

				if (placedItems == totalItems) {
					// Intent i = new Intent(ChicoGrandeScene.this,
					// ClaroOscuroScene.class);
					// i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
					// Intent.FLAG_ACTIVITY_CLEAR_TOP |
					// Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
					// startActivity(i);
//					context.toastOnUIThread("ok!");

//					CutScene.load(context);
			
					sprite.registerEntityModifier(new DelayModifier(1f) {
						protected void onModifierFinished(org.andengine.entity.IEntity pItem) {
							SceneManager.setScene(CutScene.run(SceneType.CLAROOSCURO));
						};
					});
				}
			}
		};

		scene.attachChild(sprite);
		scene.registerTouchArea(sprite);
	}

	/**
	 * Return the scene for when the scene is called to become active.
	 */
	public static Scene run() {
		return scene;
	}

	/**
	 * Unload any assets here - to be called later.
	 */
	public static void unload() {

	}
}
