import java.awt.geom.Point2D;

// -------------------------
// F I T T S S E Q U E N C E
// -------------------------

class FittsTaskTwoSequence
{
	private FittsTaskTwoTrial[] ft;
	private int numberOfTargets;
	private int a;
	private int w;
	private int sequenceRepeatCount;
	private double pt; // pointing time
	private double st; // selection time

	/*
	 * The accuracy measures are calculated and stored in each trial. Here, we are concerned with the means for the
	 * entire sequence.
	 */
	private double tre; // target re-entries
	private double tac; // task-axis crossings
	private double mdc; // movement direction changes
	private double odc; // orthogonal direction changes
	private double mv; // movement variability
	private double me; // movement error
	private double mo; // movement offset

	/*
	 * The Throughput object will provide us with the throughput for the sequence and other measures (e.g., ID,
	 * IDe, error rate, etc).
	 */
	private Throughput tp;

	FittsTaskTwoSequence(int numberOfTargetsArg, int aArg, int wArg)
	{
		tp = new Throughput();

		numberOfTargets = numberOfTargetsArg;
		a = aArg;
		w = wArg;

		// create an array to hold information about each trial in the sequence
		ft = new FittsTaskTwoTrial[numberOfTargets];

		// fill the array with FittsTaskTwoTrial instances
		for (int i = 0; i < numberOfTargets; ++i)
			ft[i] = new FittsTaskTwoTrial(a, w);

		sequenceRepeatCount = 0;
	}

	public FittsTaskTwoTrial getTrial(int i)
	{
		return ft[i];
	}

	public void setSequenceRepeatCount(int n)
	{
		sequenceRepeatCount = n;
	}

	public int getSequenceRepeatCount()
	{
		return sequenceRepeatCount;
	}

	public void clearSequenceRepeatCount()
	{
		sequenceRepeatCount = 0;
	}
	
	public void incrementSequenceRepeatCount()
	{
		++sequenceRepeatCount;
	}

	public int getTrials()
	{
		return ft.length;
	}

	public double getID() // index of difficulty (bits)
	{
		// need to compute the mean, because A is different from trial to trial
		double id = 0.0;
		for (int i = 0; i < ft.length; ++i)
			id += tp.getID();
		id /= ft.length;
		return id;
	}

	public double getIDe() // effective index of difficulty (bits)
	{
		return tp.getIDe(); 
	}

	public double getTP() // throughput (bits/s)
	{
		return tp.getThroughput();
	}

	public void computeSequenceSummaryStats()
	{
		/*
		 * The accuracy measures are stored in the AccuracyMeasures object in the FittsTaskTwoTrial instance. Here, we
		 * are computing the mean of the measures for the sequence. We are also computing the mean of the positioning
		 * time (pt) and selection time (st) values, since these are not handled in the Throughput object.
		 */
		pt = 0.0;
		st = 0.0;
		tre = 0.0;
		tac = 0.0;
		mdc = 0.0;
		odc = 0.0;
		mv = 0.0;
		me = 0.0;
		mo = 0.0;
		for (int i = 0; i < ft.length; ++i)
		{
			pt += ft[i].getPT();
			st += ft[i].getST();
			tre += ft[i].getTRE();
			tac += ft[i].getTAC();
			mdc += ft[i].getMDC();
			odc += ft[i].getODC();
			mv += ft[i].getMV();
			me += ft[i].getME();
			mo += ft[i].getMO();
		}
		pt /= ft.length;
		st /= ft.length;
		tre /= ft.length;
		tac /= ft.length;
		mdc /= ft.length;
		odc /= ft.length;
		mv /= ft.length;
		me /= ft.length;
		mo /= ft.length;

		/*
		 * Prepare and pass on the necessary data to the Throughput object. We'll let the Throughput object take of
		 * computing throughput and related measures.
		 */
		Point2D.Double[] from = new Point2D.Double[ft.length];
		Point2D.Double[] to = new Point2D.Double[ft.length];
		Point2D.Double[] select = new Point2D.Double[ft.length];
		double[] mt = new double[ft.length];
		for (int i = 0; i < ft.length; ++i)
		{
			from[i] = new Point2D.Double(ft[i].xFrom, ft[i].yFrom);
			to[i] = new Point2D.Double(ft[i].xTo, ft[i].yTo);
			select[i] = new Point2D.Double(ft[i].xSelect, ft[i].ySelect);
			mt[i] = (double)ft[i].mt;
		}

		// OK, let the Throughput object work its magic!
		tp.setData("nocode", (double)a, (double)w, Throughput.TWO_DIMENSIONAL, Throughput.SERIAL, from, to, select, mt);
	}

	public double getER()
	{
		return tp.getErrorRate();
	}

	public int getA()
	{
		return (int)Math.round(tp.getA());
	}

	public int getW()
	{
		return (int)Math.round(tp.getW());
	}

	public double getTotalErrors()
	{
		return tp.getMisses();
	}

	public double getAe()
	{
		return tp.getAe();
	}

	public double getWe()
	{
		return tp.getWe();
	}

	public double getPT()
	{
		return pt;
	}

	public double getST()
	{
		return st;
	}

	public double getMT()
	{
		return tp.getMT();
	}

	public double getTRE()
	{
		return tre;
	}

	public double getTAC()
	{
		return tac;
	}

	public double getMDC()
	{
		return mdc;
	}

	public double getODC()
	{
		return odc;
	}

	public double getMV()
	{
		return mv;
	}

	public double getME()
	{
		return me;
	}

	public double getMO()
	{
		return mo;
	}

	// this is the data, as written to the .sd2 file
	public String getSequenceData()
	{
		return this.getSequenceRepeatCount() + "," + this.getTrials() + "," + this.getA() + "," + this.getW() + ","
				+ this.getID() + "," + this.getAe() + "," + this.getWe() + "," + this.getIDe() + "," + this.getPT()
				+ "," + this.getST() + "," + this.getMT() + "," + this.getER() + "," + this.getTP() + ","
				+ this.getTRE() + "," + this.getTAC() + "," + this.getMDC() + "," + this.getODC() + "," + this.getMV()
				+ "," + this.getME() + "," + this.getMO();
	}

	public String getTrialData(int n)
	{
		return n + "," + ft[n].getTrialData();
	}

	public static String getSequenceHeader()
	{
		return "SRC,Trials,A,W,ID,Ae,We,IDe(bits),PT(ms),ST(ms),MT(ms),ER(%),TP(bps)," + "TRE,TAC,MDC,ODC,MV,ME,MO";
	}

	// this is the data, as presented in the popup window at the end of a sequence
	public String getSequenceSummary()
	{
		return "TASK CONDITIONS:\n" + String.format("   Trials = %d\n", tp.getNumberOfTrials())
				+ String.format("   A = %1.1f\n", tp.getA()) + String.format("   W = %1.1f\n", tp.getW())
				+ String.format("   ID = %1.1f bits\n", tp.getID()) + "MOVEMENT BEHAVIOUR:\n"
				+ String.format("   Ae = %1.1f\n", tp.getAe()) + String.format("   We = %1.1f\n", tp.getWe())
				+ String.format("   IDe = %1.1f bits\n", tp.getIDe())
				+ String.format("   Sequence repeats = %d\n", this.getSequenceRepeatCount())
				+ String.format("   Errors = %d\n", tp.getMisses()) + "PARTICIPANT PERFORMANCE:\n"
				+ String.format("   MT = %1.1f ms/trial\n", tp.getMT())
				+ String.format("   ER = %1.1f%%\n", tp.getErrorRate())
				+ String.format("   TP = %1.1f bits/s", tp.getThroughput());
	}

	public String getRepeatSequence()
	{
		return "OOPS!!!\n" + "Too many errors: " + tp.getMisses() + "\n" + "Please Repeat Sequence";
	}
}
