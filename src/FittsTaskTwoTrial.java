import java.awt.geom.Point2D;

// ---------------------
// F I T T S T R I A L
// ---------------------

class FittsTaskTwoTrial
{
	// conditions
	public double amplitude; // amplitude (of presented trial)
	public double width; // width (of presented trial)
	public double xFrom;
	public double yFrom;
	public double xTo;
	public double yTo;
	public double xSelect;
	public double ySelect;

	// observations
	private double ae; // effective ('actual') amplitude
	private double dx; // delta x
	private long pt; // positioning time
	private long st; // selection time
	public long mt; // movement time (Note: mt = pt + st)
	private int err; // error
	private long[] t; // timestamps
	
	AccuracyMeasures am;

	FittsTaskTwoTrial(int aArg, int wArg)
	{
		amplitude = aArg;
		width = wArg;
	}

	public void setAe(double aeArg)
	{
		ae = aeArg;
	}

	public void setDx(double dxArg)
	{
		dx = dxArg;
	}

	public void setPt(long ptArg)
	{
		pt = ptArg;
	}

	public void setSt(long stArg)
	{
		st = stArg;
	}

	public void setMt(long mtArg)
	{
		mt = mtArg;
	}

	public void setErr(int errArg)
	{
		err = errArg;
	}

	public void setXFrom(double x)
	{
		xFrom = x;
	}

	public void setYFrom(double y)
	{
		yFrom = y;
	}

	public void setXTo(double x)
	{
		xTo = x;
	}

	public void setYTo(double y)
	{
		yTo = y;
	}

	public void setXSelect(double x)
	{
		xSelect = x;
	}

	public void setYSelect(double y)
	{
		ySelect = y;
	}

	public void setTraceTimestamps(long[] tArg, int n) // timestamps for sample points
	{
		t = new long[n];
		for (int i = 0; i < t.length; ++i)
			t[i] = tArg[i];
	}

	public void setTracePoints(Point2D.Double[] pArg, int n) // x,y sample points
	{
		// create an array of just the right size to pass to the AccuracyMeasures constructor
		Point2D.Double[] p = new Point2D.Double[n];
		for (int i = 0; i < p.length; ++i)
			p[i] = pArg[i];
		
		am = new AccuracyMeasures(new Point2D.Double(xFrom, yFrom), new Point2D.Double(xTo, yTo), width, p);
	}
	
	// get methods for positioning time and selection time (NOTE: MT = PT + ST)
	public long getPT()
	{
		return pt;
	}

	public long getST()
	{
		return st;
	}

	// get methods for accuracy measures
	public int getTRE()
	{
		return am.getTRE();
	}

	public int getTAC()
	{
		return am.getTAC();
	}

	public int getMDC()
	{
		return am.getMDC();
	}

	public int getODC()
	{
		return am.getODC();
	}

	public double getMV()
	{
		return am.getMV();
	}

	public double getME()
	{
		return am.getME();
	}

	public double getMO()
	{
		return am.getMO();
	}

	public String toString()
	{
		return "A=" + amplitude + " W=" + width + " Ae=" + ae + " dx=" + dx + " pt=" + pt + " st=" + st + " mt=" + mt + " err="
				+ err + "tre=" + getTRE() + " tac=" + getTAC() + " mdc=" + getMDC() + " odc=" + getODC() + " mv=" + getMV() + " me=" + getME()
				+ " mo=" + getMO();
	}

	public String getTrialData()
	{
		return amplitude + "," + width + "," + ae + "," + dx + "," + pt + "," + st + "," + mt + "," + err + "," + getTRE() + "," + getTAC()
				+ "," + getMDC() + "," + getODC() + "," + getMV() + "," + getME() + "," + getMO();
	}

	public static String getTrialHeader()
	{
		return "A,W,Ae,dx,PT(ms),ST(ms),MT(ms),Errors,TRE,TAC,MDC,ODC,MV,ME,MO";
	}
}

