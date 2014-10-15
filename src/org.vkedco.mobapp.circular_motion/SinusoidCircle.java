package org.vkedco.mobapp.circular_motion;

/*
 * *************************************************
 * SinusoidCircle class defines Circle objects drawn
 * by CircularMotionPainterView defined in CircularMotionPainterView.java.
 * These circles model the sinusoid motion equivalent of
 * their rotating counterparts.
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 *************************************************
 */

public class SinusoidCircle extends Circle {

	float mSinosoidOriginX = 0f;
	float mSinosoidOriginY = 0f;
	int mQuadCount = 0;
	
	public SinusoidCircle(RotatingCircle mc, float sinOriginX, float sinOriginY) {
		super(sinOriginX, sinOriginY, mc.getR(), mc.getPaint());
		mSinosoidOriginX = sinOriginX;
		mSinosoidOriginY = sinOriginY;
	}
	
	public void move(RotatingCircle mc) {
		// This is what you need to do on the motion circle
		if ( //mX >= mSinusoidOriginX + mc.getMaxPeriod() 
				//&&
				mc.getNumCompletedCircles() == mc.getHZ() 
				) 
		{
			mX = mSinosoidOriginX;
			mc.setNumCompletedCircles(0);
		}
		
		float deltaX = computeDeltaX(mc);
		mX = mSinosoidOriginX + deltaX;
		
		final float deltaY = (float)(mc.getRotDir()*Math.cos(mc.getRadTheta())*mc.getRho());		
		mY = mSinosoidOriginY + deltaY; // this is correct
	}
	
	public float getSinosoidOriginX() {
		return mSinosoidOriginX;
	}
	
	final static double PI_OVER_2 		= Math.PI/2.0;
	final static double THREE_PI_OVER_2 = 3*PI_OVER_2;
	final static double TWO_PI 			= 2*Math.PI;
	final static double FOUR_PI 		= 4*Math.PI;
	
	public float computeDeltaX(RotatingCircle mc) {
		float x = (float)(SinusoidCircle.Y(mc.getRadTheta(), mc.getRho()));
		return (float)(x + mc.getNumCompletedCircles()*mc.getPeriod());
	}
	
	private static float Y(double theta_rad, double rho) {
		if ( 0 <= theta_rad && theta_rad <= PI_OVER_2 ) {
			return (float)(rho*Math.sin(theta_rad));
		}
		else if ( PI_OVER_2 < theta_rad && theta_rad <= Math.PI ) {
			return (float)(rho*(2 - Math.sin(theta_rad)));
		}
		else if ( Math.PI < theta_rad && theta_rad <= THREE_PI_OVER_2 ) {
			return (float)(rho*(2 - Math.sin(theta_rad)));
		}
		else if ( THREE_PI_OVER_2 < theta_rad && theta_rad <= TWO_PI ) {
			return (float)(rho*(4 + Math.sin(theta_rad)));
		}
		else {
			return 0f;
		}
	}
}
