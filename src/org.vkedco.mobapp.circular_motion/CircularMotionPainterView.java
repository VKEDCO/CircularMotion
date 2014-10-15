package org.vkedco.mobapp.circular_motion;

/*
 * *************************************************
 * CicurlarMotionPainterView.java is a custom view
 * used by CircularMotionMainActivity.java
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 *************************************************
 */

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircularMotionPainterView extends View {
	// Paints
	Paint mBackgroundPaint 			= null;
	Paint mForeCircleRedPaint 		= null;
	Paint mForeCircleBluePaint 		= null;
	Paint mForeCircleMagentaPaint 	= null;
	Paint mForeCircleGrayPaint 		= null;
	Paint mYellowPaint 				= null;
	Paint mAxisPaint 				= new Paint(Color.DKGRAY);
	
	// Red Motion Circle 4-Tuple 01
	RotatingCircle mRedMotionCircle 					= null;
	SinusoidCircle mRedSinusoidCircle 				= null;
	CurveCircle mRedCurveMotionCircle 		= null;
	SinusoidCurveCircle mRedCurveSinusoidCircle 	= null;

	// Blue Motion Circle 4-Tuple 02
	RotatingCircle mBlueMotionCircle 					= null;
	SinusoidCircle mBlueSinusoidCircle 				= null;
	CurveCircle mBlueCurveMotionCircle		= null;
	SinusoidCurveCircle mBlueCurveSinusoidCircle 	= null;

	// Magenta Motion Circle 4-Tuple 03
	RotatingCircle mMagentaMotionCircle 				= null;
	SinusoidCircle mMagentaSinusoidCircle 			= null;
	CurveCircle mMagentaCurveMotionCircle 	= null;
	SinusoidCurveCircle mMagentaCurveSinusoidCircle = null;
	
	// Yellow Motion Circle 4-Tuple 04
	RotatingCircle mYellowMotionCircle 				= null;
	SinusoidCircle mYellowSinusoidCircle 			= null;
	CurveCircle mYellowCurveMotionCircle 		= null;
	SinusoidCurveCircle mYellowCurveSinusoidCircle 	= null;
	
	CircularMotionApp mApp = null;

	private int mOriginX = 0;
	private int mOriginY = 0;
	private int mSinusoidOriginX = 0;
	private int mSinusoidOriginY = 0;
	private int mLengthOfXAxis = 0;
	private int mLengthOfYAxis = 0;
	private int mLengthOfSinusoidXAxis = 0;
	private int mLengthOfSinusoidYAxis = 0;
	private int mRhoMax = 0;
	
	final int MAGENTA_AVRG_FPS 	= 360;
	final int RED_AVRG_FPS 		= 360;
	final int BLUE_AVRG_FPS 	= 360;
	final int YELLOW_AVRG_FPS 	= 360;
	
	ArrayList<Circle> mCircles = null;

	public CircularMotionPainterView(Context context, AttributeSet atrs) {
		super(context, atrs);
		Log.d("CircularMotionPainterView", "constructor");
		mBackgroundPaint = new Paint();
		mBackgroundPaint.setColor(Color.TRANSPARENT);

		mForeCircleRedPaint = new Paint();
		mForeCircleRedPaint.setColor(Color.RED);
		mForeCircleRedPaint.setAntiAlias(true);

		mForeCircleBluePaint = new Paint();
		mForeCircleBluePaint.setColor(Color.BLUE);
		mForeCircleBluePaint.setAntiAlias(true);

		mForeCircleMagentaPaint = new Paint();
		mForeCircleMagentaPaint.setColor(Color.MAGENTA);
		mForeCircleMagentaPaint.setAntiAlias(true);

		mForeCircleGrayPaint = new Paint();
		mForeCircleGrayPaint.setColor(Color.DKGRAY);
		mForeCircleGrayPaint.setAntiAlias(true);
		
		mYellowPaint = new Paint();
		mYellowPaint.setColor(Color.YELLOW);
		mYellowPaint.setAntiAlias(true);

		mOriginX 		= 220;
		mOriginY 		= 280;
		mLengthOfXAxis 	= 320;
		mLengthOfYAxis 	= 320;

		mSinusoidOriginX = 460;
		mSinusoidOriginY = 280;
		mLengthOfSinusoidXAxis = 2 * mLengthOfXAxis;
		mLengthOfSinusoidYAxis = mLengthOfYAxis;
		
		mRhoMax = mLengthOfXAxis / 2;

		createCircleMotionGroups();
	}

	private void createCircleMotionGroups() {
		mCircles = new ArrayList<Circle>();
		this.createRedMotionCircleGroup();
		this.createBlueMotionCircleGroup();
		this.createMagentaMotionCircleGroup();
		this.createYellowMotionCircleGroup();
	}
	
	private void createRedMotionCircleGroup() {
		// this is the circle that rotates around the 2D Coordinate system on the left
		// Original value of the red circle's amplitude is mLengthOfXAxis / 2.
		final int RED_CIRCLE_AMP 	= (this.mLengthOfXAxis / 2);
		final int RED_CIRCLE_HZ  	= 1;
		mRedMotionCircle = 
				new RotatingCircle(
						mOriginX + (mLengthOfXAxis / 2), 	// mc_x - x-coord of the circle's center
						mOriginY, 							// mc_y - y-coord of the circle's center
						10, 								// mc_r - circle's radius
						mOriginX, 							// x coordinate of the system's origin 
						mOriginY, 							// y coordinate of the system's origin
						RED_CIRCLE_AMP,						// circle's amplitude, distance b/w system's origin
															// and circle's center
						mRhoMax, 							// maximum amplitude possible
						0,									// initial phase of the circle
						-1, 								// direction of rotation, -1 means counterclockwise
						RED_CIRCLE_HZ,								// HZ = number of circles around system's origin per unit of time 
						RED_AVRG_FPS, 						// average number of frames per second
						mForeCircleRedPaint // paint
		);
		mRedMotionCircle.setMaxPeriod(4); // 4 * mRhoMax
		
		// Every RotatingCircle has a corresponding SinusoidMotionCircle.
		// The SinusoidMotionCircle is used to depict the corresponding position
		// of the RotatingCircle on the sinusoid curve.
		mRedSinusoidCircle = new SinusoidCircle(mRedMotionCircle,
												this.mSinusoidOriginX, 
												this.mSinusoidOriginY);
		mRedMotionCircle.setSinosoidCircle(mRedSinusoidCircle);

		mRedCurveMotionCircle = new CurveCircle(mOriginX
				+ (mLengthOfXAxis / 2), 
				mOriginY, 
				4, 
				mOriginX, 
				mOriginY,
				RED_CIRCLE_AMP, 
				mRhoMax, 
				0, 
				-1, 
				RED_CIRCLE_HZ, 
				RED_AVRG_FPS,
				mForeCircleRedPaint);
		mRedCurveMotionCircle.setIsOnCurve(true);
		mRedCurveMotionCircle.setMaxPeriod(4);
		mRedCurveSinusoidCircle = new SinusoidCurveCircle(mRedCurveMotionCircle,
				this.mSinusoidOriginX, this.mSinusoidOriginY);
		mRedCurveMotionCircle.setSinosoidCircle(mRedCurveSinusoidCircle);
		mCircles.add(mRedMotionCircle);
	}
	
	private void createBlueMotionCircleGroup() {
		final int BLUE_CIRCLE_AMP 	= (this.mLengthOfXAxis / 2) - 30;
		final int BLUE_CIRCLE_HZ	= 2;
		mBlueMotionCircle = new RotatingCircle(mOriginX + (mLengthOfXAxis / 2), // mc_x
				mOriginY, // mc_x
				10, // mc_r
				mOriginX, mOriginY, // mc_origin_x, mc_origin_y
				BLUE_CIRCLE_AMP, // rho
				mRhoMax, // rho_max
				0, -1, 
				BLUE_CIRCLE_HZ, 
				BLUE_AVRG_FPS, // hz=2, avrg_fps = 20
				mForeCircleBluePaint);
		mBlueSinusoidCircle = new SinusoidCircle(mBlueMotionCircle,
				this.mSinusoidOriginX, this.mSinusoidOriginY);
		mBlueMotionCircle.setMaxPeriod(4);
		mBlueMotionCircle.setSinosoidCircle(mBlueSinusoidCircle);

		mBlueCurveMotionCircle = new CurveCircle(mOriginX
				+ (mLengthOfXAxis / 2), mOriginY, 4, mOriginX, mOriginY,
				BLUE_CIRCLE_AMP, 
				mRhoMax, 
				0, -1, 
				BLUE_CIRCLE_HZ, 
				BLUE_AVRG_FPS,
				mForeCircleBluePaint);
		mBlueCurveMotionCircle.setIsOnCurve(true);
		mBlueCurveSinusoidCircle = new SinusoidCurveCircle(mBlueCurveMotionCircle,
				this.mSinusoidOriginX, this.mSinusoidOriginY);
		mBlueCurveMotionCircle.setMaxPeriod(4);
		mBlueCurveMotionCircle.setSinosoidCircle(mBlueCurveSinusoidCircle);

		mCircles.add(mBlueMotionCircle);
	}
	
	private void createMagentaMotionCircleGroup() {
		final int MAGENTA_CIRCLE_AMP 	= (this.mLengthOfXAxis / 2) - 60;
		final int MAGENTA_CIRCLE_HZ  	= 3;
		mMagentaMotionCircle = new RotatingCircle(mOriginX + (mLengthOfXAxis / 2), // mc_x
				mOriginY, // mc_x
				10, // mc_r
				mOriginX, mOriginY, // mc_origin_x, mc_origin_y
				MAGENTA_CIRCLE_AMP, // rho
				mRhoMax, // rho_max
				0, -1, 
				MAGENTA_CIRCLE_HZ, // hz
				MAGENTA_AVRG_FPS,  // avrg_fps = 20 
				mForeCircleMagentaPaint);
		mMagentaSinusoidCircle = new SinusoidCircle(mMagentaMotionCircle,
				this.mSinusoidOriginX, this.mSinusoidOriginY);
		mMagentaMotionCircle.setMaxPeriod(4);
		mMagentaMotionCircle.setSinosoidCircle(mMagentaSinusoidCircle);

		mMagentaCurveMotionCircle = new CurveCircle(mOriginX
				+ (mLengthOfXAxis / 2), 
				mOriginY, 4, 
				mOriginX, mOriginY,
				MAGENTA_CIRCLE_AMP, // rho
				mRhoMax, // rho_max
				0, -1, 
				MAGENTA_CIRCLE_HZ, // hz
				MAGENTA_AVRG_FPS,  // afps = 360 
				mForeCircleMagentaPaint);
		mMagentaCurveMotionCircle.setIsOnCurve(true);
		mMagentaCurveSinusoidCircle = new SinusoidCurveCircle(mMagentaCurveMotionCircle,
				this.mSinusoidOriginX, this.mSinusoidOriginY);
		mMagentaCurveMotionCircle.setMaxPeriod(4);
		mMagentaCurveMotionCircle.setSinosoidCircle(mMagentaCurveSinusoidCircle);

		mCircles.add(mMagentaMotionCircle);
	}
	
	private void createYellowMotionCircleGroup() {
		final int YELLOW_CIRCLE_AMP 	= (this.mLengthOfXAxis / 2) - 90;
		final int YELLOW_CIRCLE_HZ  	= 4;
		mYellowMotionCircle = new RotatingCircle(mOriginX + (mLengthOfXAxis / 2), // mc_x
				mOriginY, 	// mc_x
				10, 		// mc_r
				mOriginX, mOriginY, // mc_origin_x, mc_origin_y
				YELLOW_CIRCLE_AMP, // rho
				mRhoMax, // rho_max
				0, -1, 
				YELLOW_CIRCLE_HZ, 
				YELLOW_AVRG_FPS, // hz=4, avrg_fps = 20
				mYellowPaint);
		mYellowSinusoidCircle = new SinusoidCircle(mYellowMotionCircle,
				this.mSinusoidOriginX, this.mSinusoidOriginY);
		mYellowMotionCircle.setMaxPeriod(4);
		mYellowMotionCircle.setSinosoidCircle(mYellowSinusoidCircle);

		mYellowCurveMotionCircle = new CurveCircle(mOriginX
				+ (mLengthOfXAxis / 2), 
				mOriginY, 5, 
				mOriginX, mOriginY,
				YELLOW_CIRCLE_AMP, // rho
				mRhoMax, // rho_max
				0, -1, 
				YELLOW_CIRCLE_HZ, 
				YELLOW_AVRG_FPS, // hz=4, avrg_fps=20
				mYellowPaint);
		mYellowCurveMotionCircle.setIsOnCurve(true);
		mYellowCurveSinusoidCircle = new SinusoidCurveCircle(mYellowCurveMotionCircle,
				this.mSinusoidOriginX, this.mSinusoidOriginY);
		mYellowCurveMotionCircle.setMaxPeriod(4);
		mYellowCurveMotionCircle.setSinosoidCircle(mYellowCurveSinusoidCircle);

		mCircles.add(mYellowMotionCircle);
	}

	// This method redraws the entire canvas
	public void draw(Canvas canvas) {
		final int width = canvas.getWidth();
		final int height = canvas.getHeight();
		// 1. draw background rectangle that covers the entire
		// canvas
		canvas.drawRect(0, 0, width, height, mBackgroundPaint);
		// 2. draw red circles on canvas
		// mRedMotionCircle.setPaint(mAxisPaint);
		// mBlueMotionCircle.setPaint(mAxisPaint);
		synchronized (this) {
			
			this.drawXAxis(canvas);
			this.drawYAxis(canvas);
			this.drawSinusoidXAxis(canvas);
			this.drawSinusoidYAxis(canvas);
			this.drawCirclesOnCanvas(canvas);
			this.drawSinusoidCurves(canvas);
			// 3. force redraw
			this.invalidate();
		}
	}
	
	// draw sinusoids
	private void drawSinusoidCurves(Canvas canvas) {
		if ( mApp.isRedCircleDisplayed() ) {
			this.drawSinusoidCurve(mRedCurveMotionCircle, canvas);
		}
		
		if ( mApp.isBlueCircleDisplayed() ) {
			this.drawSinusoidCurve(mBlueCurveMotionCircle, canvas);
		}
		
		if ( mApp.isMagentaCircleDisplayed() ) {
			this.drawSinusoidCurve(mMagentaCurveMotionCircle, canvas);
		}
		
		if ( mApp.isYellowCircleDisplayed() ) {
			this.drawSinusoidCurve(mYellowCurveMotionCircle, canvas);
		}
	}

	// draw all Circles in mCricles
	private void drawCirclesOnCanvas(Canvas canvas) {
		for (Circle c : mCircles) {
			if (c instanceof RotatingCircle) {
				if ( c.getPaint().getColor() == Color.RED && mApp.isRedCircleDisplayed() ) {
					drawMotionCircle((RotatingCircle) c, canvas);
					canvas.drawLine(mOriginX, mOriginY,
							((RotatingCircle) c).getX(),
							((RotatingCircle) c).getY(), mAxisPaint);
				}
				else if ( c.getPaint().getColor() == Color.BLUE && mApp.isBlueCircleDisplayed() ) {
					drawMotionCircle((RotatingCircle) c, canvas);
					canvas.drawLine(mOriginX, mOriginY,
							((RotatingCircle) c).getX(),
							((RotatingCircle) c).getY(), mAxisPaint);
				}
				else if ( c.getPaint().getColor() == Color.MAGENTA && mApp.isMagentaCircleDisplayed() ) {
					drawMotionCircle((RotatingCircle) c, canvas);
					canvas.drawLine(mOriginX, mOriginY,
							((RotatingCircle) c).getX(),
							((RotatingCircle) c).getY(), mAxisPaint);
				}
				else if ( c.getPaint().getColor() == Color.YELLOW && mApp.isYellowCircleDisplayed() ) {
					drawMotionCircle((RotatingCircle) c, canvas);
					canvas.drawLine(mOriginX, mOriginY,
							((RotatingCircle) c).getX(),
							((RotatingCircle) c).getY(), mAxisPaint);
					/*
					canvas.drawText("NumRhos = " + ((RotatingCircle)c).getNumRhosInMaxPeriod(), 
							450, 450, mAxisPaint);
					canvas.drawText("MaxPeriod = " + ((RotatingCircle)c).getMaxPeriod(), 
							450, 470, mAxisPaint);
					canvas.drawText("Rho = " + ((RotatingCircle)c).getRho(),
							450, 490, mAxisPaint);
					canvas.drawText("NumRhos = " + ((RotatingCircle)c).getMaxPeriod()/((RotatingCircle)c).getRho(),
							450, 510, mAxisPaint);
					*/
	
				}
			}
		}
	}

	private void drawMotionCircle(RotatingCircle motion_circle, Canvas canvas) {
		canvas.drawCircle(motion_circle.getX(), motion_circle.getY(), motion_circle.getR(), 
				motion_circle.getPaint());
		final SinusoidCircle sinusoid_circle = motion_circle.getSinosoidCircle();
		if (sinusoid_circle != null) {
			canvas.drawCircle(sinusoid_circle.getX(), 
							  sinusoid_circle.getY(), 
							  sinusoid_circle.getR(), 
							  sinusoid_circle.getPaint());
		}
	}

	private void drawSinusoidCurve(CurveCircle cmc, Canvas canvas) {
		cmc.setRadTheta(0);
		cmc.setNumCompletedCircles(0);
		SinusoidCurveCircle sc = cmc.getSinosoidCircle();
		sc.move(cmc);
		
		// this is a faulty logic. I need to compute the number of moves
		// in the distance [mSinusoidOriginX, mSinusoidOriginX + mLengthOfSinusoidXAxis/2]
		int num_moves = cmc.getNumSinusoidCircleMovesInOneCycle();
		int num_periods = (int)Math.ceil(this.mLengthOfSinusoidXAxis/(1.0*cmc.getPeriod()));
		num_moves *= num_periods;
		//canvas.drawText("Num Circles Moves == " + num_moves, 450, 455, mAxisPaint);
		//canvas.drawText("Num Perdiods      == " + num_periods, 450, 470, mAxisPaint);
		//canvas.drawText("mLengthOfSinusoidXAxis == " + this.mLengthOfSinusoidXAxis, 450, 480, mAxisPaint);
		//canvas.drawText("cmc.getPeriod() == " + cmc.getPeriod(), 450, 490, mAxisPaint);
		//final float x_limit = (float)(this.mSinusoidOriginX + cmc.getMaxPeriod());
		while ( num_moves > 0 ) 
		{
			canvas.drawCircle(sc.getX(), sc.getY(), sc.getR(), sc.getPaint());	
			cmc.move();
			num_moves--;
			/*
			if ( c.getPaint().getColor() == Color.BLUE ) {
				canvas.drawText("NumCompletedCircles == " + c.getNumCompletedCircles(), 
						450, 450, mAxisPaint);
			}
			*/
		}
		
		cmc.setX(mSinusoidOriginX);
		//cmc.setNumCompletedCircles(0);
	}

	public void setOriginX(int x) {
		mOriginX = x;
	}

	public void setOriginY(int y) {
		mOriginY = y;
	}

	public int getOriginX() {
		return mOriginX;
	}

	public int getOriginY() {
		return mOriginY;
	}

	public void setLengthOfXAxis(int len) {
		mLengthOfXAxis = len;
	}

	public void setLengthOfYAxis(int len) {
		mLengthOfYAxis = len;
	}

	public int getLengthOfXAxis() {
		return mLengthOfXAxis;
	}

	public int getLengthOfYAxis() {
		return mLengthOfYAxis;
	}

	public void drawXAxis(Canvas canvas) {
		int half_len = (int) (this.mLengthOfXAxis / 2.0f);
		canvas.drawLine(this.mOriginX - half_len, this.mOriginY, this.mOriginX
				+ half_len, this.mOriginY, this.mAxisPaint);
	}

	public void drawYAxis(Canvas canvas) {
		int half_len = (int) (this.mLengthOfYAxis / 2.0f);
		canvas.drawLine(this.mOriginX, this.mOriginY - half_len, this.mOriginX,
				this.mOriginY + half_len, this.mAxisPaint);
	}

	public void drawSinusoidXAxis(Canvas canvas) {
		canvas.drawLine(this.mSinusoidOriginX, this.mSinusoidOriginY,
				this.mSinusoidOriginX + this.mLengthOfSinusoidXAxis,
				this.mSinusoidOriginY, this.mAxisPaint);
		canvas.drawLine(
				this.mSinusoidOriginX + (float) this.mRhoMax,
				this.mSinusoidOriginY + 10f, 
				this.mSinusoidOriginX + (float) this.mRhoMax,
				this.mSinusoidOriginY - 10f, this.mAxisPaint);
		canvas.drawLine(
				this.mSinusoidOriginX + this.mRhoMax * 2.0f,
				this.mSinusoidOriginY + 10f, 
				this.mSinusoidOriginX + this.mRhoMax * 2.0f,
				this.mSinusoidOriginY - 10f, this.mAxisPaint);
		canvas.drawLine(
				this.mSinusoidOriginX + this.mRhoMax * 3.0f,
				this.mSinusoidOriginY + 10f, 
				this.mSinusoidOriginX + this.mRhoMax * 3.0f,
				this.mSinusoidOriginY - 10f, this.mAxisPaint);
		canvas.drawLine(
				this.mSinusoidOriginX + this.mRhoMax * 4.0f,
				this.mSinusoidOriginY + 10f,
				this.mSinusoidOriginX + this.mRhoMax * 4.0f,
				this.mSinusoidOriginY - 10f, this.mAxisPaint);
	}

	public void drawSinusoidYAxis(Canvas canvas) {
		int half_len = (int) (this.mLengthOfSinusoidYAxis / 2.0f);
		canvas.drawLine(this.mSinusoidOriginX,
				this.mSinusoidOriginY - half_len, this.mSinusoidOriginX,
				this.mSinusoidOriginY + half_len, this.mAxisPaint);
	}

	public boolean detectMotionCircleContact() {
		if (mRedMotionCircle.getRadTheta() == mBlueMotionCircle.getRadThetaDelta()) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Circle> getCircles() {
		return mCircles;
	}

	public int getSinosoidOriginX() {
		return mSinusoidOriginX;
	}

	public int getSinosoidOriginY() {
		return mSinusoidOriginY;
	}
	
	public void setApp(CircularMotionApp capp) {
		this.mApp = capp;
	}
	
	public int getRhoMax() {
		return mRhoMax;
	}
	
	public double getRedRho() {
		if ( this.mRedMotionCircle != null ) {
			return this.mRedMotionCircle.getRho();
		}
		else {
			return -1;
		}
	}
	
	public double getBlueRho() {
		if ( this.mBlueMotionCircle != null ) {
			return this.mBlueMotionCircle.getRho();
		}
		else {
			return -1;
		}
	}
	
	public double getMagentaRho() {
		if ( this.mMagentaMotionCircle != null ) {
			return this.mMagentaMotionCircle.getRho();
		}
		else {
			return -1;
		}
	}
	
	public double getYellowRho() {
		if ( this.mYellowMotionCircle != null ) {
			return this.mYellowMotionCircle.getRho();
		}
		else {
			return -1;
		}
	}
}
