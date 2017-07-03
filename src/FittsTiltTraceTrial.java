import java.awt.geom.Point2D;

public class FittsTiltTraceTrial
{
	final float TWO_TIMES_PI = 6.283185307f;

	double fromX, fromY, toX, toY;
	double w;
	double selectX, selectY;
	int positioningTime, selectionTime, movementTime;
	int ballDiameter; // diameter of the ball that is moved to a target (only used to compute positioning time)

	FittsTiltTracePoint[] p; // raw trace points

	AccuracyMeasures ac; // 7 accuracy measures (see below)
	int tre; // target re-entries
	int tac; // task axis crossings
	int mdc; // movement direction changes
	int odc; // orthogonal direction changes
	double mv; // movement variability
	double me; // movement error
	double mo; // movement offset

	int numberOfSamplePoints; // ... in the trace array

	double maxTilt; // maximum tilt angle (degrees) during a trial

	FittsTiltTraceTrial(double fromXArg, double fromYArg, double toXArg, double toYArg, double wArg, int ballDiameterArg,
			FittsTiltTracePoint[] pArg)
	{
		fromX = fromXArg;
		fromY = fromYArg;
		toX = toXArg;
		toY = toYArg;
		w = wArg;
		ballDiameter = ballDiameterArg;
		p = pArg;

		selectX = p[p.length - 1].x;
		selectY = p[p.length - 1].y;

		// do things that require iterating over the trace points
		Point2D.Double[] path = new Point2D.Double[p.length];
		int startTime = p[0].timeStamp;
		movementTime = p[p.length - 1].timeStamp - startTime;
		boolean ballEnteredTarget = false;
		for (int i = 0; i < path.length; ++i)
		{
			// create an array of Point2D.Double's (for AccuracyMeasures)
			path[i] = new Point2D.Double(p[i].x, p[i].y);

			// find the maximum tilt
			if (p[i].tilt > maxTilt)
				maxTilt = p[i].tilt;

			// calculated positioning time (time to first-entry)
			double distanceFromTarget = Math.hypot(p[i].x - toX, p[i].y - toY);
			if (!ballEnteredTarget && distanceFromTarget < (w / 2.0 - ballDiameter / 2.0))
			{
				ballEnteredTarget = true;
				positioningTime = p[i].timeStamp - startTime;
			}
		}
		selectionTime = movementTime - positioningTime;

		ac = new AccuracyMeasures(new Point2D.Double(fromX, fromY), new Point2D.Double(toX, toY), w, path);
		tre = ac.getTRE();
		tac = ac.getTAC();
		mdc = ac.getMDC();
		odc = ac.getODC();
		mv = ac.getMV();
		me = ac.getME();
		mo = ac.getMO();
		numberOfSamplePoints = path.length;
	}

	/*
	 * public String tPoints() { StringBuilder sb = new StringBuilder(); for (int i = 0; i < p.length; ++i)
	 * sb.append(p[i].timeStamp + ","); sb.deleteCharAt(sb.length() - 1); // delete last comma return sb.toString(); }
	 * 
	 * public String xPoints() { StringBuilder sb = new StringBuilder(); for (int i = 0; i < p.length; ++i)
	 * sb.append(p[i].x + ","); sb.deleteCharAt(sb.length() - 1); // delete last comma return sb.toString(); }
	 * 
	 * public String yPoints() { StringBuilder sb = new StringBuilder(); for (int i = 0; i < p.length; ++i)
	 * sb.append(p[i].y + ","); sb.deleteCharAt(sb.length() - 1); // delete last comma return sb.toString(); }
	 * 
	 * public String tiltPoints() { StringBuilder sb = new StringBuilder(); for (int i = 0; i < p.length; ++i)
	 * sb.append(p[i].tilt + ","); sb.deleteCharAt(sb.length() - 1); // delete last comma return sb.toString(); }
	 */
}
