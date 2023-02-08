package com.mra.satpro.skyinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.mra.satpro.SkyInvaderPlay;
import com.mra.satpro.utilerias.Global;

import java.util.concurrent.TimeUnit;

public class SpaceInvadersGame extends Game {

	private HeroFlight hero;
	
	private int fieldHeight, fieldWidth;
	private int invadersCount = 4;
	private int invaderGap = 10; //10px gap between ships
	private int invaderH, invaderW;
	private int invaderRows = 1; //rows of invader
	private static final int WIN = 0;
	private static final int LOOSE = 1;
	private static final int NAVEDESTROY = 2;
	private static final int INIT = 3;
	private static final int NOT_YET = -1;
	
	//private int gameOver = WIN;
	private int gameOver = INIT;

	private EnemyFlight[] invaders;
	private Bullet[] bullets;
	private int maxBullets = 2;
	private int maxEnemyBullets = 2;

	private int activeInvaders = invadersCount;
	private int score = 0;
	private ScoreTable scoreTable;
	private Paint paint = new Paint();
	private Bitmap background = Bitmaps.getBitmap(Bitmaps.BACKGROUND);
	private Rect screenRect = new Rect();
	private LevelBoss currentBoss = null;
	
	private Bullet[] enemyBullets;
	private long elapsedShootTime = 0;
	private long shootInterval = 1000;
	private char heroLives = 2;

	//Variable de num of invader
	public static int numInvader = 0;
	private Context cont;
	private static  int resCorrecta = 9;
	private static LinearLayout espacio;

	public SpaceInvadersGame(Context context)
	{
		super(context);

		cont = context;
		mCountDownTimer = null;

		DisplayMetrics metrics = new DisplayMetrics();
		
		/*((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		fieldHeight = metrics.heightPixels;
		fieldWidth = metrics.widthPixels;*/

		espacio = SkyInvaderPlay.espacio;
		double height = espacio.getHeight();
		double width = espacio.getWidth();

		fieldHeight = (int) height;
		fieldWidth = (int) width;

		screenRect.left = 0;
		screenRect.top = 0;
		screenRect.right = fieldWidth;
		screenRect.bottom = fieldHeight;

		invaders = new EnemyFlight[invadersCount];
		bullets = new Bullet[maxBullets];

		maxEnemyBullets = 2;
		heroLives = 2;

		enemyBullets = new Bullet[maxEnemyBullets];
		paint.setColor(Color.YELLOW);
		paint.setTextSize(96);
		paint.setFakeBoldText(true);
		// amount in a row

		reset();
	}
	
	private void makeNewHero() {
		//Let's calc the size and pos of the hero ship.
		int hero_h = invaderH*2;
		int hero_w = invaderH*2;
	//	int hero_w = Math.round((float)hero_h*(float)0.5);

		int hero_pos_left = (fieldWidth - hero_w)/2; //so we centered the hero ship
		//int hero_pos_top = fieldHeight - hero_h - invaderGap*6;
		int hero_pos_top = fieldHeight - fieldHeight/3;

		Log.e("hero_pos_top", hero_pos_top+"");

		hero = new HeroFlight(hero_pos_left,
				hero_pos_top,
				hero_pos_left + hero_w,
				hero_pos_top + hero_h,
				10, Color.BLUE);

		hero.setVisible(true);		
	}

	public void reset() {

		int numInRow = invadersCount / invaderRows;

		invaderW = ((fieldWidth - 40) - invaderGap)/(6) - invaderGap;
		invaderH = invaderW;

		//int newInvaderW = 150;
		//int newInvaderheight = 150;

        Log.e("invaderW", invaderW+"");
        Log.e("invaderH", invaderH+"");

        int newInvaderW = invaderW;
        int newInvaderheight = invaderH;

        makeNewHero();

		heroLives = 2;
		maxEnemyBullets = 2;

		for (int i = 0; i < invaderRows; ++i) {
			for (int j = 0; j < numInRow; ++j) {
				invaders[i*numInRow + j]
						= new EnemyFlight(20 + invaderGap + j*(newInvaderW + invaderGap),
						invaderGap + (i*newInvaderheight + invaderGap),
								20 + invaderGap + j*(newInvaderW + invaderGap) + newInvaderW,
						invaderGap + (i*newInvaderheight + invaderGap) + newInvaderheight,
								8,
								Color.RED);
		/*		Log.e("InvaderTop:", (invaderGap + (i*invaderH + invaderGap))+"");
				Log.e("LEFT:", (20 + invaderGap + j*(invaderW + invaderGap))+"");
				Log.e("RIGTH:", (20 + invaderGap + j*(invaderW + invaderGap) + invaderW)+"");
				Log.e("BOTTOM:", (invaderGap + (i*invaderH + invaderGap) + invaderH)+"");*/
				invaders[i*numInRow + j].setDownShift(10);
				invaders[i*numInRow + j].setVisible(true);
			}
		}
		
		float speed = -((float)fieldHeight/ (float)1500)*10;

		for (int i = 0; i < maxBullets; ++i) {
			bullets[i] = new Bullet();
			bullets[i].setVisible(false);
			bullets[i].setSpeed(Math.round(speed));
			bullets[i].setSpaceArea(0, 0, fieldWidth, fieldHeight);
		}

		speed = (float)(fieldHeight/ (float)3000 )*10;

		
		for (int i = 0; i < maxEnemyBullets; ++i) {
			enemyBullets[i] = new EnemyBullet();
			enemyBullets[i].setVisible(false);
			enemyBullets[i].setSpeed(Math.round(speed));
			enemyBullets[i].setSpaceArea(invaderGap, invaderGap, fieldWidth - invaderGap, fieldHeight - invaderGap);
		}

		activeInvaders = invadersCount;
		scoreTable = new ScoreTable();

		scoreTable.setBoundRect(invaderGap, fieldHeight - invaderGap*4, fieldWidth - invaderGap, fieldHeight - invaderGap);
		scoreTable.setLives(heroLives);
		currentBoss = null;

		/*String materiaEstudio = SkyInvaderPlay.materiaEstudio;
		int preguntaSiguiente = Global.preguntaSigPlayGlobal;
		int totalPreguntas = SkyInvaderPlay.preguntasTotales;

		if(gameOver == LOOSE || gameOver == WIN) {
			pregNext(materiaEstudio, preguntaSiguiente, totalPreguntas);
		}*/

		gameOver = NOT_YET;

	}

	public void pregNext(final String mat, final int next, final int toPreg){

		if(gameOver == LOOSE) {

			SkyInvaderPlay.initiatePopupAyuda(cont, next);
			gameOver = INIT;
			//SkyInvaderPlay.siguientePreguntaButton(preguntaSiguiente, materiaEstudio, totalPreguntas);
			//reset();
		}
		if(gameOver == WIN){

			Global.aciertos = Global.aciertos + 1;
			//SkyInvaderPlay.confeti();
			SkyInvaderPlay.initiatePopupCorrecta(cont, next);
			//SkyInvaderPlay.siguientePreguntaButton(next, mat, toPreg);
			//SkyInvaderPlay.siguientePreguntaButtonVideo(next, mat, toPreg);
			gameOver = INIT;

		/*	Timer segundos = new Timer();
			final TimerTask task = new TimerTask() {

				public void run() {
					SkyInvaderPlay.siguientePreguntaButton(next, mat, toPreg);
					gameOver = INIT;
				}
			};

			//segundos.schedule(task, 1,3000L);

			///////////////
			final Handler m_handler;
			Runnable m_handlerTask ;
			m_handler = new Handler();
			m_handlerTask = new Runnable()
			{
				@Override
				public void run() {

					// do something
					m_handler.postDelayed(task, 1000);

				}
			};
			m_handlerTask.run();*/

			//reset();
		}

	}


	@Override
	public void update(long timeDelta) {

		if (gameOver != NOT_YET) {
			return;
		}

		synchronized (this) {

			elapsedShootTime += timeDelta;

			if (!hero.isVisible()) {
				if ( (heroLives) > 0) {
					--heroLives;
					scoreTable.setLives(heroLives);
					makeNewHero();
				} else {
					//Anterior gameOver = LOOSE;
					gameOver = NAVEDESTROY;
				}
			}

			// handle the bullets sent by a hero
			for (int i = 0; i < maxBullets; ++i) {
				if (bullets[i].isVisible()) {
					bullets[i].update(timeDelta);
				}
			}

			if (activeInvaders > 0) {
				updateInvaders(timeDelta);
				handleCollisions();
			}

			if ( (currentBoss != null) && currentBoss.isVisible()) {
				currentBoss.update(timeDelta);
				handleBossBulletCollisions();
			}

			if (activeInvaders == 0) {
				if ((currentBoss != null) && currentBoss.isDefeated()) {
					gameOver = WIN;
				} else if (currentBoss == null) {
					currentBoss = createLevelBoss();
					maxEnemyBullets = 1;
					currentBoss.setVisible(true);
				}
			}

			if (elapsedShootTime >= shootInterval) {
				elapsedShootTime = 0;

				if (activeInvaders > 0) {

					int index = invadersCount;

					//silly logic to choose a ship that will fire a bullet
					do {
						--index;
						if (!invaders[index].isKilled() && ( Math.random() > 0.6f)) {
							Log.e("indexInvader", index+"");
							break;
						}
					} while(index > 0);

					//if we still have our invaders, one of them will fire a bullet
					fireEnemyBullet(invaders[index].getBoundRect(), hero.getBoundRect());
				} else {
					//there are no ships, that means we have a boss fighting
					fireEnemyBullet(currentBoss.getBoundRect(), hero.getBoundRect());
				}
			}

			// handle the bullets sent by a boss
			for (int i = 0; i < maxEnemyBullets; ++i) {
				if (enemyBullets[i].isVisible()) {
					//currently enemy bullet is using parent's update()
					//so the bullets flight straight
					//TODO: override the method to shoot the bullets to the hero's location
					enemyBullets[i].update(timeDelta);
				}
			}

			handleHeroBulletsCollisions();

		}
	}

	private void handleHeroBulletsCollisions() {
		for (int i = 0; i < maxEnemyBullets; ++i) {
			if (enemyBullets[i].isVisible()) {
				if (Rect.intersects(enemyBullets[i].getBoundRect(), hero.getBoundRect())) {

					enemyBullets[i].setVisible(false);
					hero.addDamage(enemyBullets[i].getPower());
				}
			}
		}				
	}

	private void handleBossBulletCollisions() {
		for (int i = 0; i < maxBullets; ++i) {
			if (bullets[i].isVisible()) {
				if (Rect.intersects(bullets[i].getBoundRect(), currentBoss.getBoundRect())) {

					bullets[i].setVisible(false);
					currentBoss.addDamage(bullets[i].getPower());
					
					if (currentBoss.isKilled()) {
						score += currentBoss.getScore();
						scoreTable.setScore(score);
					}
				}
			}
		}
	}

	private LevelBoss createLevelBoss() {
		LevelBoss boss = new BigBrain((fieldWidth - 140)/2, invaderGap, (fieldWidth - 140)/2 + 140, invaderGap + 140, 10, 0);

		boss.setSpaceArea(invaderGap, invaderGap, fieldWidth - invaderGap, fieldHeight - invaderH*2);

		return boss;
	}

	private void updateInvaders(long timeDelta) {
		boolean isWallMet = false;
		Rect tmpRect;

		// Run through the all invaders and update their positions
		for (int i = 0; i < invadersCount; ++i) {
			if (invaders[i].isVisible()) {
				invaders[i].update(timeDelta);

				tmpRect = invaders[i].getBoundRect();

				// if we met a wall, remember that
				if ((tmpRect.left < invaderGap)
						|| (tmpRect.right > (fieldWidth - invaderGap))) {
					isWallMet = true;
				}
			 }
		}

		// if we met a wall, then we need to count the bounce and reverse
		// the direction (maybe)
		if (isWallMet) {
			for (int i = 0; i < invadersCount; ++i) {
				if (invaders[i].isVisible()) {
					invaders[i].handleWall();
					invaders[i].stepBack();
				}
			}

		}
	}

	private void handleCollisions() {
		// Now calculate collisions
		for (int i = 0; i < maxBullets; ++i) {
			if (bullets[i].isVisible()) {

				for (int j = 0; j < invadersCount; ++j) {
					if (invaders[j].isVisible()) {
						// Remove the bullet and the battleship
						if (Rect.intersects(invaders[j].getBoundRect(),
							bullets[i].getBoundRect())) {
							bullets[i].setVisible(false);

							//invaders[j].setKilled(true);
							invaders[j].addDamage(bullets[i].getPower());
							//invaders[j].handleWall();
							invaders[j].setSpeed(100);

							if (invaders[j].isKilled()) {
							/*	SkyInvaderPlay play = new SkyInvaderPlay();
								play.evaluaRespuesta();*/
								numInvader = j;

								resCorrecta = SkyInvaderPlay.respCorrecta;
								if(resCorrecta==numInvader){
									gameOver = WIN;
								}else{
									gameOver = LOOSE;
								}

							//	muestraRespuest(numInvader);
								Log.e("resCorrecta+++",resCorrecta+"");
								Log.e("invaders_respouesta",j+"");
								handleAMurder(j);
							}
						}
					}
				}
			}
		}
	}

	
	private void handleAMurder(int index) {

		--activeInvaders;
		score += invaders[index].getScore();
		scoreTable.setScore(score);
		
		if (activeInvaders < 10) {
			for (int k = 0; k < invadersCount; ++k) {

				if (invaders[k].isVisible()) {
					if (invaders[k].getSpeed() > 0) {
						invaders[k].setSpeed(invaders[k].getSpeed() + 6);
					} else {
						invaders[k].setSpeed(invaders[k].getSpeed() - 6);
					}

					// frequency of steps for invaders is increased
					if (invaders[k].getJumpInterval() > 80) {
						invaders[k].setJumpInterval(invaders[k].getJumpInterval() - 30);
					}

					invaders[k].setDownShift(invaders[k].getDownShift() + 1);
				}
			}
		}
	}

	@Override
	public void drawFrame(Canvas canvas) {
		//Draw Background
		synchronized(this) {
			
			drawBackground(canvas);

			//draw a hero
			if (hero.isVisible()) {
				hero.draw(canvas);
			}

			drawEnemies(canvas);
			
			drawBullets(canvas);
			
			drawEnemyBullets(canvas);
	
			scoreTable.draw(canvas);

			drawMisc(canvas);
		}
	}

	private void drawEnemies(Canvas canvas) {

		if (activeInvaders > 0) {
			for(int i = 0; i < invadersCount; ++i) {
				if (invaders[i].isVisible()) {
						numInvader = i;
					invaders[i].draw(canvas);

				}
			}
		}
		
		if (currentBoss != null && currentBoss.isVisible()) {
			currentBoss.draw(canvas);
		}
	}

	private void drawBackground(Canvas canvas) {
		//canvas.drawARGB(255, 1, 1, 1);
		canvas.drawBitmap(background, null, screenRect, null);
	}
	
	private void drawBullets(Canvas canvas) {
		for (int i = 0; i < maxBullets; ++i) {
			if (bullets[i].isVisible()) {
				bullets[i].draw(canvas);
			}
		}
	}

	private void drawMisc(Canvas canvas) {
		if (gameOver != NOT_YET) {
			canvas.drawARGB(160, 1, 1, 1);

			paint.setTextSize(60);
			if (gameOver == WIN) {
				paint.setColor(Color.GREEN);
				canvas.drawText("Good!", fieldWidth/12 , fieldHeight/3 - 50, paint);
				canvas.drawText("Tap here to continue!", fieldWidth/12 , fieldHeight/2 - 50, paint);

			} else if(gameOver == LOOSE){
				paint.setColor(Color.RED);
				canvas.drawText("Failed!", fieldWidth/12 , fieldHeight/3 - 50, paint);
				canvas.drawText("Tap here to continue!", fieldWidth/12 , fieldHeight/2 - 50, paint);

			} else if(gameOver == NAVEDESTROY){
				paint.setColor(Color.BLUE);
				canvas.drawText(" ¡Destroy!", fieldWidth/6 , fieldHeight/2 - 50, paint);
			}
		}		
	}
	
	private void drawEnemyBullets(Canvas canvas) {
		for (int i = 0; i < maxEnemyBullets; ++i) {
			if (enemyBullets[i].isVisible()) {
				enemyBullets[i].draw(canvas);
			}
		}		
	}
	
	private void fireEnemyBullet(Rect src, Rect dest) {
		synchronized(this) { 
			for (int i = 0; i < maxEnemyBullets; ++i) {
				if (!(enemyBullets[i].isVisible())) {
					int left = src.centerX() - 5;
					int right = src.centerX() + 5;
					int top = src.bottom;
					int bottom = src.bottom + 20;

					enemyBullets[i].setBoundRect(left, top, right, bottom);
					enemyBullets[i].setVisible(true);
					break;
				}
			}
		}
	}

	private void fireBullet() {
		synchronized(this) { 
			for (int i = 0; i < maxBullets; ++i) {
				if (!(bullets[i].isVisible())) {
					int left = hero.getBoundRect().centerX() - 5;
					int right = hero.getBoundRect().centerX() + 5;
					int top = hero.getBoundRect().top - 20;
					int bottom = hero.getBoundRect().top;

					bullets[i].setBoundRect(left, top, right, bottom);
					bullets[i].setVisible(true);
					break;
				}
			}
		}
	}

	private static CountDownTimer mCountDownTimer;

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		
		switch (arg1.getAction()) {
			case MotionEvent.ACTION_DOWN: {

				    if (gameOver == NAVEDESTROY ) {
					      reset();
					}

					if (gameOver == WIN || gameOver == LOOSE ) {
						String materiaEstudio = SkyInvaderPlay.materiaEstudio;
						int preguntaSiguiente = Global.preguntaSigPlayGlobal;
						int totalPreguntas = SkyInvaderPlay.preguntasTotales;

						pregNext(materiaEstudio, preguntaSiguiente, totalPreguntas);

						mCountDownTimer = new CountDownTimer(60000, 1000) {
							public void onTick(long millisUntilFinished) {
								String timerValue =   String.format("%02d:%02d:%02d",
										TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
										TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
												TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), // The change is in this line
										TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
												TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
							}

							public void onFinish() {
								reset();
							}
						}.start();


						/*SkyInvaderPlay juego = new SkyInvaderPlay();
						juego.pause();
						juego.temporizadorFromSpace();*/

					}

					if (gameOver != NOT_YET ) {
						//reset();
					} else if (!hero.isKilled()){
	
						int x = Math.round(arg1.getX());
						int y = Math.round(arg1.getY());

						boolean intersects = false;

						Rect longRect = new Rect(hero.getBoundRect());

						int width = longRect.width();
						
						if (x > longRect.right) {
							longRect.right = x + width/2;
						} else if (x < longRect.left){
							longRect.left = x - width/2;
						}
						
						//Let's calculate a collision
						for (int i = 0; i < maxEnemyBullets; ++i) {
							if (enemyBullets[i].isVisible()) {
								if (Rect.intersects(longRect, enemyBullets[i].getBoundRect()) ) {
									enemyBullets[i].setVisible(false);
									hero.setPos(enemyBullets[i].getBoundRect().left, enemyBullets[i].getBoundRect().top);
									hero.addDamage(enemyBullets[i].getPower());

									intersects = true;
									break;
								}
							}
						}

						if (!intersects) {
							hero.setPos(x,y);
							fireBullet();
						}


					}


				}break;

			case MotionEvent.ACTION_MOVE: {
				if (!hero.isKilled()){
					hero.setPos(Math.round(arg1.getX()), Math.round(arg1.getY()));
				}
				}break;

			case MotionEvent.ACTION_UP: {
				}
			case MotionEvent.ACTION_CANCEL: {
				}break;
				
			default:
				return false;
		}

		return true;
	}
}
