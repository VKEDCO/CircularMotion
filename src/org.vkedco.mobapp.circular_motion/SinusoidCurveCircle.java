package org.vkedco.mobapp.circular_motion;

/*
 * *************************************************
 * SinusoidCurveCircle class is used to draw sinusoids
 * on the right side of the CircularMotionPainterView.java
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 *************************************************
 */

public class SinusoidCurveCircle extends Circle {

	float mSinusoidOriginX = 0f;
	float mSinosoidOriginY = 0f;
	int mQuadCount = 0;
	
	public SinusoidCurveCircle(CurveCircle mc, float sinOriginX, float sinOriginY) {
		super(sinOriginX, sinOriginY, mc.getR(), mc.getPaint());
		mSinusoidOriginX = sinOriginX;
		mSinosoidOriginY = sinOriginY;
	}
	
	public void move(CurveCircle mc) {
		float deltaX = computeDeltaX(mc);
		mX = mSinusoidOriginX + deltaX;
		
		final float deltaY = (float)(mc.getRotDir()*Math.cos(mc.getRadTheta())*mc.getRho());		
		mY = mSinosoidOriginY + deltaY; // this is correct
	}
	
	public float getSinosoidOriginX() {
		return mSinusoidOriginX;
	}
	
	final static double PI_OVER_2 		= Math.PI/2.0;
	final static double THREE_PI_OVER_2 = 3*PI_OVER_2;
	final static double TWO_PI 			= 2*Math.PI;
	final static double FOUR_PI 		= 4*Math.PI;
	
	public float computeDeltaX(CurveCircle mc) {
		float x = (float)(SinusoidCurveCircle.Y(mc.getRadTheta(), mc.getRho()));
		return (float)(x + mc.getNumCompletedCircles() * mc.getPeriod());
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

