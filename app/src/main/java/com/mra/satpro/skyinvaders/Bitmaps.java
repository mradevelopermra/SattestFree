package com.mra.satpro.skyinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mra.satpro.R;


public class Bitmaps {

	private Bitmaps(){}
	private Context context;
	private static Bitmaps instance = null;
	//TODO: need to make a hash table and retain the bitmaps in cache to allow reuse
	//private static Map<Integer, Bitmap> bmpCache = new HashMap<Integer, Bitmap>(); 
	
	public static final int ALIEN1 = 0;
	public static final int ALIEN2 = 1;

	public static final int ALIEN3 = 13;
	public static final int ALIEN4 = 14;

	public static final int ALIEN5 = 15;
	public static final int ALIEN6 = 16;

	public static final int ALIEN7 = 17;
	public static final int ALIEN8 = 18;


	public static final int HERO = 2;
	public static final int BULLET = 3;
	public static final int BACKGROUND = 4;
	public static final int BOOM = 5;
	public static final int BIG_BRAIN1 = 6;
	public static final int BIG_BRAIN2 = 7;
	public static final int BIG_BRAIN3 = 8;
	public static final int BIG_BRAIN4 = 9;
	public static final int BIG_BRAIN5 = 10;
	public static final int BIG_BRAIN6 = 11;
	public static final int ENEMY_BULLET = 12;
	
	
	public static void createInstance(Context context) {
		instance = new Bitmaps();
		instance.context = context;
	}
	
	private static Bitmaps getInstance() {
		return instance;
	}
	
	public static Bitmap getBitmap (int id) {
		
		Bitmap bmp;
		int rId = 0;
		switch (id) {
			case ALIEN1: {
				//rId = R.drawable.brainruno;
				//rId = R.mipmap.aveuno;
				rId = R.mipmap.ralphuno;
			}break;

			case ALIEN2: {
				//rId = R.drawable.brainrunodos;
				//rId = R.mipmap.aveunouno;
				rId = R.mipmap.ralhpunouno;
			}break;

			case ALIEN3: {
				//rId = R.drawable.braindos;
				//rId = R.mipmap.avedos;
				rId = R.mipmap.ralphdos;
			}break;

			case ALIEN4: {
				//rId = R.drawable.braindosdos;
				//rId = R.mipmap.avedosdos;
				rId = R.mipmap.ralphdosdos;
			}break;

			case ALIEN5: {
				//rId = R.drawable.brainrtres;
				//rId = R.mipmap.avetres;
				rId = R.mipmap.ralphtres;
			}break;

			case ALIEN6: {
				//rId = R.drawable.brainrtresdos;
				//rId = R.mipmap.avetrestres;
				rId = R.mipmap.ralphtrestres;
			}break;

			case ALIEN7: {
				//rId = R.drawable.brainrcuatro;
				//rId = R.mipmap.avecuatro;
				rId = R.mipmap.ralphcuatro;
			}break;

			case ALIEN8: {
				//rId = R.drawable.braincuatrodos;
				//rId = R.mipmap.avecuatrocuatro;
				rId = R.mipmap.ralphcuatrocuatro;
			}break;

			case HERO: {
				//rId = R.drawable.ship2;
              //  rId = R.mipmap.peluso;
				rId = R.mipmap.changuito_juego;
			}break;

			
			case BULLET: {
				rId = R.drawable.bullet;
			}break;

			case BACKGROUND: {
				//rId = R.mipmap.esecenariouno;
				rId = R.mipmap.fondoedificio;
			}break;
			
			case BOOM: {
				rId = R.drawable.boom;
			}break;

			case BIG_BRAIN1: {
				rId = R.drawable.bigbrain1;
			}break;
			case BIG_BRAIN2: {
				rId = R.drawable.bigbrain2;
			}break;
			case BIG_BRAIN3: {
				rId = R.drawable.bigbrain3;
			}break;
			case BIG_BRAIN4: {
				rId = R.drawable.bigbrain4;
			}break;
			case BIG_BRAIN5: {
				rId = R.drawable.bigbrain5;
			}break;
			case BIG_BRAIN6: {
				rId = R.drawable.bigbrain6;
			}break;
			case ENEMY_BULLET: {
				rId = R.drawable.enemybullet;
			}break;
		}

		bmp = BitmapFactory.decodeResource(getInstance().context.getResources(), rId);

		return bmp;
	}
}
