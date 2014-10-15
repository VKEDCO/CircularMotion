package org.vkedco.mobapp.circular_motion;

/*
 * *************************************************
 * CurveCircle class is used to draw sinusoids
 * on the right side of the CircularMotionPainterView.java
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 *************************************************
 */

import android.graphics.Paint;

public class CurveCircle extends Circle {
	double mRadTheta 			= 0;
	double mRadThetaDelta 		= 0;
	int mOriginX 				= 0; 	// x-coord of the axis center
	int mOriginY 				= 0; 	// y-coord of the axis center
	double mRho  				= 0; 	// rho
	SinusoidCurveCircle mSinosoidCurveCircle = null;
	int mRotDir 				= 0;
	double mRhoMax 				= 0;
	double mMaxPeriod 			= 0;
	double mNumRhosInMaxPeriod 	= 0;
	boolean mFullCircleReached 	= false;
	int mNumCompletedCircles = 0; 	// every time a full circle is reached, this is
	                     			// incremented by 1. If nNumCircles == mHZ, then mNumCompletedCircles is reset to 0;
	                     			// I have to modify: incrementRadTheta(); SinusoidCircle.computeDeltaX(); 
	int mHZ = 0; 					// number of circles per second; this is constant;
	boolean mIsOnCurve = false;
	
	public CurveCircle(float x, float y, float r, Paint p) {
		super(x, y, r, p);
	}
	
	// mc_x, mc_y - x and y of the motion circle's center
	// mc_r - radius of the motion circle
	// mc_origin_x, mc_origin_y - x and y of the center of the coordinate system
	// where the motion circle rotates
	// rho - radius at which the motion circle rotates, i.e., the distance between
	//       (mc_origin_x, mc_origin_y) and (mc_x, mc_y)
	// rho_max - the max value of rho allowed by the program
	// mc_rad_theta - theta angle of the motion circle
	// rot_dir - rotational direction: -1 counter clockwise; 1 clockwise
	// hz - number of circles per unit of time
	// avrg_fps - avrg frames per second
	// p - Paint of the motion circle
	public CurveCircle(float mc_x, float mc_y, float mc_r,
			int mc_origin_x, int mc_origin_y,
			double rho, double rho_max, 
			double rad_theta,
			int rot_dir, int hz, int avrg_fps, Paint p) {
		super(mc_x, mc_y, mc_r, p);
		mRadTheta 		= rad_theta;
		mOriginX  		= mc_origin_x;
		mOriginY  		= mc_origin_y;
		mRho      		= rho;
		mRotDir   		= rot_dir;
		mRadThetaDelta 	= RotatingCircle.calculateRadThetaDelta(hz, avrg_fps, rot_dir);
		mRhoMax   		= rho_max;
		mHZ       		= hz;
	}
	
	public double getRadTheta() { return mRotDir*mRadTheta; }
	
	public void setRadTheta(double theta) { mRadTheta = theta; }
	
	public double getRadThetaDelta() { return mRadThetaDelta; }
	
	public void setRadThetaDelta(double d) { mRadThetaDelta = d; }
	
	public void incrementRadTheta() {
		double radTheta = mRadTheta + mRadThetaDelta;
		if ( Math.abs(radTheta) >= 2*Math.PI ) {
			mRadTheta = mRotDir*(Math.abs(radTheta) - 2*Math.PI);
			this.mNumCompletedCircles += 1;
		}
		else {
			mRadTheta = radTheta;
		}
	}
	
	public void setNumCompletedCircles(int nc) {
		this.mNumCompletedCircles = nc;
	}
	
	public int getNumCompletedCircles() {
		return this.mNumCompletedCircles;
	}
	
	public int getHZ() {
		return this.mHZ;
	}
	
	public void recalculateCenterXY() {
		this.mX = (int) (mOriginX + mRho*Math.cos(mRadTheta));
		this.mY = (int) (mOriginY + mRho*Math.sin(mRadTheta));
	}
	
	public void move() {
		incrementRadTheta(); // increment number of completed circles by 1 if necessary
		recalculateCenterXY();
		if ( mSinosoidCurveCircle != null ) {
			mSinosoidCurveCircle.move(this);
		}
	}
	
	public double getDegTheta() 	 { return RotatingCircle.radsToDegrees(mRadTheta); }
	public double getDegThetaDelta() { return RotatingCircle.radsToDegrees(mRadThetaDelta); }
	
	public static double radsToDegrees(double rads) {
		return rads * 180/Math.PI;
	}
	
	public static double calculateOmega(int hz) {
		return 2*Math.PI*hz;
	}
	
	public static double calculateRadThetaDelta(int hz, int avrg_fps, int rot_dir) {
		return rot_dir*calculateOmega(hz)/avrg_fps;
	}
	
	public double getRho() {
		return mRho;
	}
	
	public void setRho(double rho) {
		mRho = rho;
		setNumRhosInMaxPeriod();
	}
	
	public void setSinosoidCircle(SinusoidCurveCircle sc) {
		mSinosoidCurveCircle = sc;
	}
	
	public int getRotDir() { return mRotDir; }
	
	public SinusoidCurveCircle getSinosoidCircle() { return mSinosoidCurveCircle; }	
	
	public void setMaxPeriod(int num_rho_max) {
		mMaxPeriod = num_rho_max * mRhoMax;
		setNumRhosInMaxPeriod();
	}
	
	public double getMaxPeriod() {
		return mMaxPeriod;
	}
	
	public double getRhoMax() {
		return mRhoMax;
	}
	
	private void setNumRhosInMaxPeriod() {
		mNumRhosInMaxPeriod = mMaxPeriod/mRho;
	}
	
	public double getNumRhosInMaxPeriod() {
		return mNumRhosInMaxPeriod;
	}
	
	public double getPeriod() {
		return 4 * mRho;
	}
	
	public void setIsOnCurve(boolean v) {
		this.mIsOnCurve = v;
	}
	
	public boolean isOnCurve() {
		return mIsOnCurve;
	}
	
	public int getNumSinusoidCircleMovesInOneCycle() {
		return Math.abs((int)(( this.getHZ() * 2 * Math.PI )/this.mRadThetaDelta));
	}

}
