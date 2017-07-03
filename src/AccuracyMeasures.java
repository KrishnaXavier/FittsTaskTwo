import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * <h1>AccuracyMeasures</h1>
 * 
 * <h3>Summary</h3>
 * 
 * <ul>
 * <li>A class or application to perform accuracy measurements on the path of a cursor during target
 * selection tasks
 * <p>
 * 
 * <li>The accuracy measures computed are
 * 
 * <ul>
 * <li>TRE (target re-entries)
 * <li>TAC (task axis crossings)
 * <li>MDC (movement direction changes)
 * <li>ODC (orthogonal direction changes)
 * <li>MV (movement variability)
 * <li>MO (movement offset)
 * <li>ME (movement error)
 * </ul>
 * <p>
 * 
 * <li>Full descriptions are in <a href="http://www.yorku.ca/mack/CHI01.htm">Accuracy Measures for
 * Evaluating Computer Pointing Devices</a> by MacKenzie, Kauppinen, and Silfverberg (2001).
 * <p>
 * 
 * <li>Two uses:
 * 
 * <ul>
 * <li>As a class embedded in custom-designed software
 * <li>As a utility program executed from a command prompt
 * </ul>
 * <p>
 * </ul>
 * 
 * <h3>AccuracyMeasures Class</h3>
 * <p>
 * 
 * As a class embedded in custom-designed software, the <code>AccuracyMeasures</code> class file is
 * placed in the same directory as other class files for the application. This API provides all the
 * details necessary to use the <code>AccuracyMeasures</code> class. An
 * <code>AccuracyMeasures</code> object requires the following arguments (data) which are provided
 * either via the constructor or using the <code>setData</code> method:
 * <p>
 * 
 * <ul>
 * <li><code>from</code> &ndash; a <code>Point2D.Double</code> holding the coordinate of the
 * designated beginning of the trial (center of the "from" target)
 * <p>
 * <li><code>to</code> &ndash; a <code>Point2D.Double</code> holding the coordinate of the
 * designated ending of the trial (center of the "to" target)
 * <p>
 * <li><code>width</code> &ndash; a <code>double</code> representing the width of the (circular)
 * target to select
 * <p>
 * <li><code>path</code> &ndash; a <code>Point2D.Double</code> array holding the points representing
 * the path of the cursor during the trial
 * </ul>
 * <p>
 * 
 * Once the data are provided to the <code>AccuracyMeasures</code> object, the accuracy measures are
 * immediately calculated. They are retrieved in the application using get-methods.
 * <p>
 * 
 * <h3>AccuracyMeasures Utility Program</h3>
 * <p>
 * 
 * As a utility program, <code>AccuracyMeasures</code> is executed from a command prompt. The
 * following is the usage message, if invoked without arguments:
 * <p>
 * 
 * <pre><blockquote>
 * Usage: java AccuracyMeasures -d|infile [-t] [-v]
 * 
 *    where -d = demo using internal data set
 *          infile = read data from 'infile'
 *          -t = table output
 *          -v = verbose output
 *</pre>
 * </blockquote>
 * 
 * There must be at least one command-line argument. This is <code>-d</code> for the demo option, or
 * the name of a file containing the input data to process (see below).
 * <p>
 * 
 * A good way to get started is to execute <code>AccuracyMeasures</code> from the command prompt
 * with the <code>-d</code> (demo) option:
 * <p>
 * 
 * <pre><blockquote>
 *PROMPT>java AccuracyMeasures -d
 *========================
Demo of AccuracyMeasures
========================
From = 856.5, 501.0
To = 515.0, 159.5
Amplitude = 483.0
Width = 60.0
Number of sample points = 149
Sample points...
(842.0, 499.0) (847.0, 501.0) (850.0, 503.0) (852.0, 504.0) (854.0, 505.0) (856.0, 506.0) (858.0, 507.0) (860.0, 507.0) 
(863.0, 508.0) (864.0, 508.0) (865.0, 507.0) (865.0, 505.0) (862.0, 501.0) (858.0, 496.0) (854.0, 490.0) (848.0, 483.0) 
(839.0, 474.0) (830.0, 465.0) (819.0, 455.0) (807.0, 445.0) (794.0, 434.0) (781.0, 423.0) (768.0, 412.0) (754.0, 401.0) 
(742.0, 391.0) (728.0, 380.0) (715.0, 368.0) (699.0, 355.0) (688.0, 345.0) (675.0, 334.0) (659.0, 321.0) (637.0, 302.0) 
(625.0, 292.0) (613.0, 282.0) (602.0, 273.0) (591.0, 265.0) (580.0, 257.0) (562.0, 247.0) (554.0, 242.0) (546.0, 238.0) 
(539.0, 235.0) (534.0, 232.0) (528.0, 230.0) (523.0, 228.0) (520.0, 227.0) (516.0, 226.0) (514.0, 225.0) (511.0, 224.0) 
(508.0, 223.0) (506.0, 222.0) (503.0, 221.0) (501.0, 219.0) (498.0, 218.0) (496.0, 217.0) (493.0, 216.0) (491.0, 215.0) 
(488.0, 213.0) (486.0, 212.0) (484.0, 210.0) (482.0, 209.0) (482.0, 208.0) (481.0, 207.0) (481.0, 206.0) (481.0, 205.0) 
(481.0, 204.0) (481.0, 203.0) (482.0, 202.0) (482.0, 202.0) (482.0, 202.0) (483.0, 201.0) (484.0, 201.0) (485.0, 200.0) 
(486.0, 200.0) (490.0, 201.0) (492.0, 201.0) (495.0, 201.0) (497.0, 201.0) (500.0, 201.0) (503.0, 200.0) (506.0, 199.0) 
(509.0, 199.0) (512.0, 197.0) (515.0, 195.0) (518.0, 193.0) (521.0, 190.0) (523.0, 187.0) (526.0, 182.0) (530.0, 174.0) 
(533.0, 168.0) (536.0, 160.0) (537.0, 156.0) (538.0, 151.0) (540.0, 144.0) (541.0, 141.0) (541.0, 140.0) (542.0, 140.0) 
(543.0, 141.0) (544.0, 142.0) (544.0, 145.0) (545.0, 148.0) (546.0, 154.0) (548.0, 160.0) (549.0, 168.0) (551.0, 176.0) 
(553.0, 185.0) (556.0, 195.0) (558.0, 203.0) (560.0, 211.0) (563.0, 220.0) (566.0, 230.0) (568.0, 238.0) (571.0, 244.0) 
(573.0, 250.0) (574.0, 253.0) (575.0, 258.0) (576.0, 262.0) (575.0, 270.0) (573.0, 269.0) (571.0, 268.0) (569.0, 266.0) 
(567.0, 265.0) (565.0, 263.0) (563.0, 260.0) (562.0, 258.0) (559.0, 255.0) (557.0, 251.0) (552.0, 244.0) (548.0, 237.0) 
(543.0, 232.0) (541.0, 229.0) (538.0, 226.0) (536.0, 223.0) (533.0, 220.0) (531.0, 217.0) (528.0, 213.0) (527.0, 211.0) 
(525.0, 207.0) (523.0, 204.0) (521.0, 201.0) (519.0, 198.0) (517.0, 195.0) (515.0, 191.0) (514.0, 189.0) (512.0, 186.0) 
(510.0, 184.0) (508.0, 181.0) (506.0, 179.0) (504.0, 177.0) (502.0, 175.0) 
Accuracy measures...
TRE = 1
TAC = 4
MDC = 4
ODC = 5
MV = 24.53
ME = 26.84
MO = 20.53
Done!
 * </pre>
 * </blockquote>
 * 
 * The demo uses embedded data for the <code>from</code>, <code>to</code>, <code>width</code>, and
 * <code>path</code> arguments. In the demo, the data are echoed to the console in a human-readable
 * format followed by the accuracy measures.
 * <p>
 * 
 * The data for the demo are available in a text file that is distributed with
 * <code>AccuracyMeasures</code>. The file is <a
 * href="AccuracyMeasures-demo.txt">AccuracyMeasures-demo.txt</a>. The same output is generated if
 * <code>AccuracyMeasures</code> is executed as follows:
 * <p>
 * 
 * <pre><blockquote>
 * PROMPT>java AccuracyMeasures AccuracyMeasures-demo.txt -t
 * (same output as above)
 * </pre>
 * </blockquote>
 * <p>
 * 
 * The accuracy measures in the demo indicate 1 target re-entry, 4 task axis crossings, 4 movement
 * direction changes, and so on. Clearly, cursor movement for this trial was not a direct line
 * between the "from" and "to" target points. With a little effort, a graphic rendering of the trial
 * can be created in Excel. Below is an example. The chart shows the "from" target, the "to" target,
 * the cursor path, and the task axis:
 * <p>
 * 
 * <center><img src="AccuracyMeasures-1.jpg" width="600"></center>
 * <p>
 * 
 * <a href="AccuracyMeasures-demo.xlsx">Click here</a> to view the spreadsheet used to create the
 * above chart. Using visual inspection, the accuracy measures can be assessed. One target re-entry
 * is evident, as are 4 task axis crossings, 4 movement direction changes, and 5 orthogonal
 * direction changes. The continuous measures of MV, ME, and MO are more difficult to quantify by
 * visual inspection. They can be verified by calculation in the spreadsheet, if desired.
 * <p>
 * 
 * The <code>-t</code> (table) option provides tabular output. While nice for a demo, this format is
 * awkward if analysing the data for numerous trials collected in an experiment. For this, the
 * <code>-t</code> option is omitted:
 * <p>
 * 
 * <pre><blockquote>
 * PROMPT>java AccuracyMeasures AccuracyMeasures-demo.txt
 * Participant,Session,Block,Gain,Mode,Trial,A,W,TRE,TAC,MDC,ODC,MV,MO,ME
 * P01,S01,B01,100,M1,2,500,60,1,3,4,4,24.530310,20.525079,26.836838
 * </pre>
 * </blockquote>
 * <p>
 * 
 * The output contains two lines: a header line and a data line. Each entry in the header identifies
 * the type of information in the corresponding entry in the data. The entries are full-precision,
 * comma-delimited for importing into a spreadsheet or statistics application for further analyses.
 * <p>
 * 
 * In the output above, there are 15 comma-delimited values on each line. The first 8 hold a "code"
 * that associates test conditions with the 7 accuracy measures that follow.
 * <p>
 * 
 * The code entries in both lines are provided in the input data file. This allows the
 * <code>AccuracyMeasures</code> utility to be as flexible as possible. If your experiment included
 * "feedback mode" as independent variable, then "Feedback_Mode" can be provided as an entry in the
 * code header. The corresponding entries in the data lines might contain "Audio" for trials using
 * audio feedback or "Vibrotactile" for trials using vibrotactile feedback. The use of codes,
 * including the number of codes, is entirely determined in the data provided to the
 * <code>AccuracyMeasures</code> utility.
 * <p>
 * 
 * <h3>Input Data Format</h3>
 * <p>
 * 
 * To help describe the input data format, the data in <a
 * href="AccuracyMeasures-demo.txt">AccuracyMeasures-demo.txt</a> are copied below (slightly
 * abbreviated):
 * <p>
 * 
 * <pre><blockquote>
 *# ========================================          
# Test data for the AccuracyMeasures class          
# ========================================          
Participant,Session,Block,Gain,Mode,Trial,A,W       
P01,S01,B01,100,M1,2,500,60                         
856.5,501.0                                         
515.0,159.5                                         
60                                                  
842,847,850,852,854,856,858,860,863,864,865,865,862, ...
499,501,503,504,505,506,507,507,508,508,507,505,501, ...

(data for the next trial begin here, after a blank line)
 * </pre>
 * </blockquote>
 * 
 * For each trial, the input data may begin with comments. Comment lines begin with "#". They are
 * ignored. The first non-blank, non-comment line must contain the code header. The comma-delimited
 * entries in the code header appear in the first line of output. The code header is provided once
 * only &ndash; with the first trial of input. Following the code header is the code. This contains
 * the comma-delimited codes associated with this trial. Five lines of data follow. The first three
 * contain, in order, the <code>from</code> (<i>x</i><sub><font size=-2>1</sub></font>,
 * <i>y</i><sub><font size=-2>1</sub></font>), <code>to</code> (<i>x</i><sub><font
 * size=-2>2</sub></font>, <i>y</i><sub>2<font size=-2></sub></font>), and <code>width</code> data.
 * The last two lines contain the cursor path data, first the <i>x</i>-points, then the
 * <i>y</i>-points. Obviously, there must be the same number of comma-delimited entries in these two
 * lines.
 * <p>
 * 
 * Following the data, there is a blank line to separate the data for this trial from the data for
 * the next trial. This pattern is repeated for each trial to be processed.
 * <p>
 * 
 * Using the <code>AccuracyMeasures utility</code> requires input data as described above. As an
 * example, in the paper <a href="http://www.yorku.ca/mack/nordichi2012.html">FittsTilt: The
 * Application of Fitts' Law To Tilt-based Interaction</a> by MacKenzie and Teather (2012), accuracy
 * measures were computed and analysed in an experiment using device tilt to control a cursor and
 * select targets. Cursor path data were collected for every trial in the experiment. There were
 * more than 10,000 trials, so a large amount of data were collected. The following three files show
 * the general idea of how the <code>AccuracyMeasures</code> utility was used for this experiment.
 * The files are abbreviated to show only the first 50 trials.
 * <p>
 * 
 * <ul>
 * <li><a href="AccuracyMeasures-input.txt">AccuracyMeasures-input.txt</a> - the raw data from the
 * first 50 trials, in the required format.
 * <p>
 * 
 * <li><a href="AccuracyMeasures-output.txt">AccuracyMeasures-output.txt</a> - the output from the
 * <code>AccuracyMeasures</code> utility.
 * <p>
 * 
 * <li><a href="AccuracyMeasures-spreadsheet.xlsx">AccuracyMeasures-spreadsheet.xlsx</a> - the Excel
 * spreadsheet into which the data above were imported.
 * </ul>
 * <p>
 * 
 * Good luck. If you have any comments or suggestions, please get in touch (mack "at" cse.yorku.ca).
 * 
 * @author Scott MacKenzie, 2013-2016
 * 
 */
public class AccuracyMeasures
{
	// accuracy measures (from MacKenzie, Silfverberg, & Kauppinen, 2001)
	private int tre; // target re-entries
	private int tac; // task axis crossings
	private int mdc; // movement direction change
	private int odc; // orthogonal direction change
	private double mv; // movement variability
	private double me; // movement error
	private double mo; // movement offset

	private Point2D.Double from, to;
	private double w;
	private Point2D.Double[] path, transformedPath;

	// private double a; // amplitude of trial (distance between from & to)

	private final int THRESHOLD_TAC = 5;
	private final int THRESHOLD_MDC = 10;
	private final int THRESHOLD_ODC = 10;

	private int thresholdTAC; // used for TAC calculation
	private int thresholdMDC;
	private int thresholdODC;

	private boolean thresholdTACIsSet = false;
	private boolean thresholdMDCIsSet = false;
	private boolean thresholdODCIsSet = false;

	/**
	 * Empty constructor. Must follow with setData.
	 */
	public AccuracyMeasures()
	{

	}

	/**
	 * Construct an AccuracyMeasures object.
	 */
	public AccuracyMeasures(Point2D.Double fromArg, Point2D.Double toArg, double widthArg, Point2D.Double[] pathArg)
	{
		setData(fromArg, toArg, widthArg, pathArg);
	}

	/**
	 * Set the data, or set new data, for this AccuracyMeasures object.
	 */
	public void setData(Point2D.Double fromArg, Point2D.Double toArg, double widthArg, Point2D.Double[] pathArg)
	{
		from = fromArg;
		to = toArg;
		// a = Math.hypot(to.x - from.x, to.y - from.y);
		w = widthArg;
		path = pathArg;
		transformedPath = transform(from, to, path);

		if (!thresholdTACIsSet)
			thresholdTAC = THRESHOLD_TAC;
		if (!thresholdMDCIsSet)
			thresholdMDC = THRESHOLD_MDC;
		if (!thresholdODCIsSet)
			thresholdODC = THRESHOLD_ODC;

		computeAccuracyMeasures();
	}

	/*
	 * Return a transformed array of points, such that "from" is (0,0) and "to" is (x,0), where x is
	 * the specified movement amplitude for the trial. In other words, we are transforming the path
	 * such that the trial begins at the origin and moves horizontally to the right. This
	 * facilitates computing the accuracy measures.
	 */
	private Point2D.Double[] transform(Point2D.Double from, Point2D.Double to, Point2D.Double[] p)
	{
		Point2D.Double[] tp = new Point2D.Double[p.length];

		double xTranslate = from.x;
		double yTranslate = from.y;
		for (int i = 0; i < p.length; ++i)
			tp[i] = new Point2D.Double((int)(p[i].x - xTranslate), (int)(p[i].y - yTranslate));

		double xDelta = to.x - from.x;
		double yDelta = to.y - from.y;
		double theta = Math.atan((double)yDelta / xDelta);
		theta = (2.0 * Math.PI) - theta;

		for (int i = 0; i < tp.length; ++i)
		{
			double xx = tp[i].x;
			double yy = tp[i].y;
			tp[i].x = -(xx * Math.cos(theta) - yy * Math.sin(theta));
			tp[i].y = (xx * Math.sin(theta) + yy * Math.cos(theta));
		}
		return tp;
	}

	/*
	 * The work is done here.
	 */
	private void computeAccuracyMeasures()
	{
		// -------------------------
		tre = 0;
		double radius = (double)w / 2.0;

		StringBuilder pattern = new StringBuilder();
		for (int i = 0; i < path.length; ++i)
		{
			double d = Math.hypot((double)to.x - path[i].x, (double)to.y - path[i].y);
			if (d < radius)
				pattern.append("1");
			else
				pattern.append("0");
		}

		// smooth pattern (remove single- or double-runs of 0s or 1s)
		smooth(pattern);

		// count the target entries
		for (int i = 0; i < pattern.length() - 1; ++i)
		{
			if (pattern.charAt(i) == '0' && pattern.charAt(i + 1) == '1') // path has entered the
																			// target
				++tre;
		}

		--tre; // adjust (don't count first target entry)
		tre = tre < 0 ? 0 : tre;

		// -----------------------
		tac = 0;
		boolean belowAxis = false;
		boolean aboveAxis = false;
		for (int i = 0; i < transformedPath.length; ++i)
		{
			// don't check for TAC if pointer is inside either target
			// double d1 = Math.hypot(transformedPath[i].x, transformedPath[i].y); // distance from
			// 0,0
			// double d2 = Math.hypot(a - transformedPath[i].x, transformedPath[i].y); // distance
			// from A,0
			// if (d1 < radius || d2 < radius)
			// continue;

			// NOTE: labels here assume Java's coordinate system
			if (transformedPath[i].y > thresholdTAC)
				belowAxis = true;
			if (transformedPath[i].y < -thresholdTAC)
				aboveAxis = true;
			if (i == 0)
				continue; // just get bearings on first sample

			if (belowAxis && transformedPath[i].y < -thresholdTAC)
			{
				++tac;
				belowAxis = false;
				aboveAxis = true;
			} else if (aboveAxis && transformedPath[i].y > thresholdTAC)
			{
				++tac;
				belowAxis = true;
				aboveAxis = false;
			}
		}

		// -------------------
		mdc = 0;
		pattern = new StringBuilder();
		for (int i = 0; i < transformedPath.length - 1; ++i)
		{
			// build a pattern showing direction changes from one point to the next
			if ((transformedPath[i + 1].y - transformedPath[i].y) >= 0)
				pattern.append("1");
			else
				pattern.append("0");
		}

		// smooth pattern (remove single- or double-runs of 0s or 1s)
		smooth(pattern);

		// only increment mdc if deltaY > threshold from one direction change to the next
		for (int i = 1; i < pattern.length(); ++i)
		{
			int firstIdx = i - 1;
			while (i < pattern.length() && pattern.charAt(i) == pattern.charAt(i - 1))
				++i;
			int secondIdx = i;
			if (secondIdx < pattern.length() - 1
					&& Math.abs(transformedPath[firstIdx].y - transformedPath[secondIdx].y) > thresholdMDC)
				++mdc;
		}

		// ------------------
		odc = 0;
		pattern = new StringBuilder();
		for (int i = 0; i < transformedPath.length - 1; ++i)
		{
			// stop checking once the cursor enters the target
			// double d = Math.hypot(a - transformedPath[i].x, transformedPath[i].y); // distance
			// from A,0
			// if (d < radius)
			// break;

			if ((transformedPath[i + 1].x - transformedPath[i].x) >= 0)
				pattern.append("1");
			else
				pattern.append("0");
		}

		// smooth pattern (remove single- or double-runs of 0s or 1s)
		smooth(pattern);

		// only increment odc if deltaX > threshold from one direction change to the next
		for (int i = 1; i < pattern.length(); ++i)
		{
			int firstIdx = i - 1;
			while (i < pattern.length() && pattern.charAt(i) == pattern.charAt(i - 1))
				++i;
			int secondIdx = i;
			// System.out.printf("i1=%d, i2=%d, y1=%.1f, y2=%.1f\n", firstIdx, secondIdx,
			// transformedPath[firstIdx].y,
			// transformedPath[secondIdx].y);

			if (secondIdx < pattern.length() - 1
					&& Math.abs(transformedPath[firstIdx].x - transformedPath[secondIdx].x) > thresholdODC)
				++odc;
		}

		// ----------------------
		mv = me = mo = 0.0;
		double[] yy = new double[transformedPath.length];
		for (int i = 0; i < transformedPath.length; ++i)
			yy[i] = transformedPath[i].y;
		double meanY = mean(yy);
		for (int i = 0; i < transformedPath.length; ++i)
		{
			mv += ((double)transformedPath[i].y - meanY) * ((double)transformedPath[i].y - meanY);
			me += Math.abs(transformedPath[i].y);
		}
		mv = Math.sqrt(mv / (transformedPath.length - 1));
		me = me / transformedPath.length;
		mo = meanY;
	}

	private void smooth(StringBuilder sb)
	{
		// smooth the pattern (1st pass)
		for (int i = 0; i < sb.length() - 3; ++i)
		{
			if (sb.subSequence(i, i + 3).equals("101"))
				sb.replace(i, i + 3, "111");
			else if (sb.subSequence(i, i + 3).equals("010"))
				sb.replace(i, i + 3, "000");
		}

		// smooth the pattern (2nd pass)
		for (int i = 0; i < sb.length() - 4; ++i)
		{
			if (sb.subSequence(i, i + 4).equals("1001"))
				sb.replace(i, i + 4, "1111");
			else if (sb.subSequence(i, i + 4).equals("0110"))
				sb.replace(i, i + 4, "0000");
		}
		// return sb;
	}

	private double mean(double n[])
	{
		double mean = 0.0;
		for (int j = 0; j < n.length; j++)
			mean += n[j];
		return mean / n.length;
	}

	/**
	 * Set the threshold for computing task axis crossings. The threshold is the pixel distance the
	 * path must deviate from the task axis to be considered "off" the task axis. The default is 5.
	 * <p>
	 * 
	 * NOTE: Execute <code>setData</code> after using this method (so the accuracy measures are
	 * computed using the new threshold.)
	 */
	public void setTACThreshold(int thresholdArg)
	{
		thresholdTAC = thresholdArg;
		thresholdTACIsSet = true;
	}

	/**
	 * Set the threshold for computing movement direction changes. The threshold is the
	 * <i>y</i>-axis distance between consecutive transitions from "moving left" to "moving right",
	 * or vice versa.
	 * <p>
	 * 
	 * NOTE: Execute <code>setData</code> after using this method (so the accuracy measures are
	 * computed using the new threshold.)
	 */
	public void setMDCThreshold(int thresholdArg)
	{
		thresholdMDC = thresholdArg;
		thresholdMDCIsSet = true;
	}

	/**
	 * Set the threshold for computing orthogonal direction changes. The threshold is the
	 * <i>x</i>-axis distance between consecutive transitions from "moving forward" to
	 * "moving backward", or vice versa.
	 * <p>
	 * 
	 * NOTE: Execute <code>setData</code> after using this method (so the accuracy measures are
	 * computed using the new threshold.)
	 */
	public void setODCThreshold(int thresholdArg)
	{
		thresholdODC = thresholdArg;
		thresholdODCIsSet = true;
	}

	/**
	 * Returns the transformed path array for this trial. The transformation rotates and translates
	 * the path points, maintaining their relationship with the <code>from</code> and
	 * <code>to</code> points, such that the new <code>from</code> point is (0.0, 0.0) and the new
	 * <code>to</code> point is (<i>x</i>, 0.0), with <i>x</i> equal to the specified movement
	 * amplitude for the trial (i.e., in code,
	 * <code>x = Math.hypot(from.x - to.x, from.y - to.y)</code>). Note that the first and last
	 * points of the transformed path array will only be (0.0, 0.0) and (<i>x</i>, 0.0) if the path
	 * for this trial started at <code>from</code> and finished at <code>to</code>.
	 * 
	 */
	public Point2D.Double[] getTransformedPath()
	{
		return transformedPath;
	}

	/**
	 * Returns the number target re-entries.
	 * <p>
	 * 
	 * A target re-entry is logged each time the path enters the target after the first entry. To
	 * implement this, a string pattern is created representing the state of the path for each
	 * sample point: "0" = outside the target, "1" = inside the target. Then, the pattern is
	 * smoothed, excluding single- or double-runs of 0s or 1s. The smoothed pattern is then scanned
	 * with TRE incremented for each occurrence of "01"(excluding the first occurrence).
	 */
	public int getTRE()
	{
		return tre;
	}

	/**
	 * Returns the number of task axis crossings.
	 * <p>
	 * 
	 * A task axis crossing is logged each time the path transitions from "above" the task axis to
	 * "below" the task axis. A threshold is used to prevent artificially inflating TAC for
	 * reasonably direct movements that oscillate about the task axis. The default threshold is 5. A
	 * point is "above" if it is > threshold units above the task axis or "below" if it is >
	 * threshold units below the task axis. A TAC is logged for each above-below or below-above
	 * transition. Transition detection includes hysteresis (i.e., the last state is maintained
	 * until the opposite threshold is crossed).
	 * <p>
	 * 
	 * See also <code>setThresholdTAC</code>.
	 */
	public int getTAC()
	{
		return tac;
	}

	/**
	 * Returns the number of movement direction changes.
	 * <p>
	 * 
	 * A movement direction change is logged when the cursor path transitions from "moving left" to
	 * "moving right", or vice versa. To implement this, the movement direction for each pair of
	 * points in the path is coded using "1" if the path is moving left or "0" if the path moving
	 * right. An example pattern might be "1111001111011100010000111111". Then, a smoothing
	 * algorithm traverses the pattern to remove single- or double-runs of 0s or 1s. For the
	 * example, this yields "1111111111111100000000111111". Then, each transition point is compared
	 * with the next transition point. (The final point is considered a transition point.) If the
	 * difference in the <i>y</i> coordinates exceeds a threshold, an MDC is logged. The default
	 * threshold is 10. For the example, MDC = 2 (provided the threshold requirement is met for both
	 * transitions).
	 * <p>
	 * 
	 * See also <code>setThresholdMDC</code>.
	 */
	public int getMDC()
	{
		return mdc;
	}

	/**
	 * Returns the number of orthogonal direction changes.
	 * <p>
	 * 
	 * An orthogonal direction change is logged when the cursor path transitions from
	 * "moving forward" to "moving backward", or vice versa. To implement this, the movement
	 * direction for each pair of points in the path is coded using "1" if the path is moving
	 * forward or "0" if the path is moving backward. An example pattern might be
	 * "1111001111011100010000111111". Then, a smoothing algorithm traverses the pattern to remove
	 * single- or double-runs of 0s or 1s. For the example, this yields
	 * "1111111111111100000000111111". Then, each transition point is compared with the next
	 * transition point. (The final point is considered a transition point.) If the difference in
	 * the <i>x</i> coordinates exceeds a threshold, an ODC is logged. The default threshold is 10.
	 * For the example, ODC = 2 (provided the threshold requirement is met for both transitions).
	 * <p>
	 * 
	 * See also <code>setThresholdODC</code>.
	 */
	public int getODC()
	{
		return odc;
	}

	/**
	 * Returns the movement variability.
	 */
	public double getMV()
	{
		return mv;
	}

	/**
	 * Returns the movement error.
	 */
	public double getME()
	{
		return me;
	}

	/**
	 * Returns the movement offset.
	 */
	public double getMO()
	{
		return mo;
	}

	/**
	 * Returns the "from" point for this trial.
	 */
	public Point2D.Double getFrom()
	{
		return from;
	}

	/**
	 * Returns the "to" point for this trial.
	 */
	public Point2D.Double getTo()
	{
		return to;
	}

	/**
	 * Returns the transformed <code>from</code> point for this trial. The transformed
	 * <code>from</code> point is (0.0, 0.0).
	 */
	public Point2D.Double getTransformedFrom()
	{
		return new Point2D.Double(0.0, 0.0);
	}

	/**
	 * Returns the transformed <code>to</code> point for this trial. The transformed <code>to</code>
	 * point is (<i>x</i>, 0.0) with <i>x</i> equal to the specified movement amplitude for this
	 * trial (i.e., in code, <code>x = Math.hypot(from.x - to.x, from.y - to.y)</code>).
	 */
	public Point2D.Double getTransformedTo()
	{
		double x = Math.hypot(from.x - to.x, from.y - to.y);
		return new Point2D.Double(x, 0);
	}

	/**
	 * Returns the target width.
	 */
	public double getWidth()
	{
		return w;
	}

	/**
	 * Returns the array of path points.
	 */
	public Point2D.Double[] getPath()
	{
		return path;
	}

	public static void main(String[] args) throws IOException
	{
		final String DATA_HEADER = "TRE,TAC,MDC,ODC,MV,ME,MO";

		AccuracyMeasures accuracyMeasures = new AccuracyMeasures(); // use empty constructor (use
																	// setData later)

		String inFileName = "";
		BufferedReader br = null;

		// these data are initialized and passed to the AccuracyMeasures constructor
		String codeHeader = "";
		String code;
		Point2D.Double from, to;
		Double width;
		Point2D.Double[] path;

		// demo data
		final Point2D.Double DEMO_FROM = new Point2D.Double(856.5, 501.0);
		final Point2D.Double DEMO_TO = new Point2D.Double(515.0, 159.5);
		final double DEMO_WIDTH = 60.0f;
		final double[] DEMO_PATH_X = { 842, 847, 850, 852, 854, 856, 858, 860, 863, 864, 865, 865, 862, 858, 854, 848,
				839, 830, 819, 807, 794, 781, 768, 754, 742, 728, 715, 699, 688, 675, 659, 637, 625, 613, 602, 591,
				580, 562, 554, 546, 539, 534, 528, 523, 520, 516, 514, 511, 508, 506, 503, 501, 498, 496, 493, 491,
				488, 486, 484, 482, 482, 481, 481, 481, 481, 481, 482, 482, 482, 483, 484, 485, 486, 490, 492, 495,
				497, 500, 503, 506, 509, 512, 515, 518, 521, 523, 526, 530, 533, 536, 537, 538, 540, 541, 541, 542,
				543, 544, 544, 545, 546, 548, 549, 551, 553, 556, 558, 560, 563, 566, 568, 571, 573, 574, 575, 576,
				575, 573, 571, 569, 567, 565, 563, 562, 559, 557, 552, 548, 543, 541, 538, 536, 533, 531, 528, 527,
				525, 523, 521, 519, 517, 515, 514, 512, 510, 508, 506, 504, 502 };

		final double[] DEMO_PATH_Y = { 499, 501, 503, 504, 505, 506, 507, 507, 508, 508, 507, 505, 501, 496, 490, 483,
				474, 465, 455, 445, 434, 423, 412, 401, 391, 380, 368, 355, 345, 334, 321, 302, 292, 282, 273, 265,
				257, 247, 242, 238, 235, 232, 230, 228, 227, 226, 225, 224, 223, 222, 221, 219, 218, 217, 216, 215,
				213, 212, 210, 209, 208, 207, 206, 205, 204, 203, 202, 202, 202, 201, 201, 200, 200, 201, 201, 201,
				201, 201, 200, 199, 199, 197, 195, 193, 190, 187, 182, 174, 168, 160, 156, 151, 144, 141, 140, 140,
				141, 142, 145, 148, 154, 160, 168, 176, 185, 195, 203, 211, 220, 230, 238, 244, 250, 253, 258, 262,
				270, 269, 268, 266, 265, 263, 260, 258, 255, 251, 244, 237, 232, 229, 226, 223, 220, 217, 213, 211,
				207, 204, 201, 198, 195, 191, 189, 186, 184, 181, 179, 177, 175 };

		// initialize command line options to false
		boolean tableOption = false;
		boolean demoOption = false;
		boolean verboseOption = false;

		if (args.length == 0)
		{
			System.out.println("Usage: java AccuracyMeasures -d|infile [-t] [-v]");
			System.out.println();
			System.out.println("   where -d = demo using internal data set");
			System.out.println("         infile = read data from \'infile\'");
			System.out.println("         -t = table output");
			System.out.println("         -v = verbose output");
			System.exit(0);
		} else
		{
			if (args[0].equals("-d"))
				demoOption = true;
			else
				inFileName = args[0];
			for (int i = 1; i < args.length; ++i)
			{
				if (args[i].equals("-t"))
					tableOption = true;
				else if (args[i].equals("-v"))
					verboseOption = true;
			}
		}

		// open input file
		if (!demoOption)
		{
			try
			{
				br = new BufferedReader(new FileReader(inFileName));
			} catch (IOException e)
			{
				System.out.println("Can't open file '" + inFileName + "' for reading");
				System.exit(0);
			}
		}

		if (demoOption)
		{
			from = DEMO_FROM;
			to = DEMO_TO;
			width = DEMO_WIDTH;
			path = new Point2D.Double[DEMO_PATH_X.length];
			for (int i = 0; i < path.length; ++i)
				path[i] = new Point2D.Double(DEMO_PATH_X[i], DEMO_PATH_Y[i]);

			accuracyMeasures.setData(from, to, width, path);

			outputTabularResults(accuracyMeasures, verboseOption);

		} else
		{
			String line1, line2;
			StringTokenizer st1, st2;
			boolean codeHeaderRead = false;
			while ((line1 = br.readLine()) != null)
			{
				if (line1.length() == 0 || line1.charAt(0) == '#')
					continue; // skip blank or comment lines

				// the 1st non-blank, non-comment line must be the code header (it appears just
				// once)
				if (!codeHeaderRead)
				{
					codeHeader = line1;
					codeHeaderRead = true;
					if (!tableOption) // summary only
					{
						System.out.printf("%s,%s\n", codeHeader, DATA_HEADER); // once only, 1st
																				// line of output
					}
					continue;
				}

				// code
				code = line1;

				// from
				line1 = br.readLine();
				st1 = new StringTokenizer(line1, ", ");
				if (st1.countTokens() != 2)
				{
					System.out.printf("Oops! Two values expected (fromX, fromY)! Bye!\n");
					System.exit(0);
				}
				from = new Point2D.Double(Double.parseDouble(st1.nextToken()), Double.parseDouble(st1.nextToken()));

				// to
				line1 = br.readLine();
				st1 = new StringTokenizer(line1, ", ");
				if (st1.countTokens() != 2)
				{
					System.out.printf("Oops! Two values expected (toX, toY)! Bye!\n");
					System.exit(0);
				}
				to = new Point2D.Double(Double.parseDouble(st1.nextToken()), Double.parseDouble(st1.nextToken()));

				// width
				line1 = br.readLine();
				st1 = new StringTokenizer(line1, ", ");
				if (st1.countTokens() != 1)
				{
					System.out.printf("Oops! One values expected (width)! Bye!\n");
					System.exit(0);
				}
				width = Double.parseDouble(st1.nextToken());

				// path
				line1 = br.readLine();
				line2 = br.readLine();
				st1 = new StringTokenizer(line1, ", ");
				st2 = new StringTokenizer(line2, ", ");

				int n = st1.countTokens();
				if (n != st2.countTokens())
				{
					System.out.printf("Oops! The number of x and y path points must be the same. Bye!\n");
					System.exit(0);
				}
				path = new Point2D.Double[n];
				for (int i = 0; i < n; ++i)
					path[i] = new Point2D.Double(Double.parseDouble(st1.nextToken()), Double.parseDouble(st2
							.nextToken()));

				// We've got all the data. Give to AccuracyMeasures object
				accuracyMeasures.setData(from, to, width, path);

				if (tableOption)
					outputTabularResults(accuracyMeasures, verboseOption);
				else
				{
					System.out.printf("%s,%d,%d,%d,%d,%f,%f,%f\n", code, accuracyMeasures.getTRE(), accuracyMeasures
							.getTAC(), accuracyMeasures.getMDC(), accuracyMeasures.getODC(), accuracyMeasures.getMV(),
							accuracyMeasures.getME(), accuracyMeasures.getMO());
				}
			}
		}
	}

	private static void outputTabularResults(AccuracyMeasures acArg, boolean verboseOption)
	{
		AccuracyMeasures ac = acArg;
		Point2D.Double from = ac.getFrom();
		Point2D.Double to = ac.getTo();
		double width = ac.getWidth();
		Point2D.Double[] path = ac.getPath();

		System.out.printf("========================\n");
		System.out.printf("Demo of AccuracyMeasures\n");
		System.out.printf("========================\n");
		System.out.printf("From = %1.1f, %1.1f\n", from.x, from.y);
		System.out.printf("To = %1.1f, %1.1f\n", to.x, to.y);

		double a = Math.hypot(from.x - to.x, from.y - to.y);
		System.out.printf("Amplitude = %1.1f\n", a);
		System.out.printf("Width = %1.1f\n", width);
		int n = path.length;
		System.out.printf("Number of sample points = %d\n", n);
		System.out.printf("Sample points...\n");
		for (int i = 0; i < path.length; ++i)
		{
			System.out.printf("(%1.1f, %1.1f) ", path[i].x, path[i].y);
			if ((i + 1) % 8 == 0)
				System.out.printf("\n");
		}
		System.out.printf("\n");

		if (verboseOption)
		{
			System.out.printf("-----\n");
			System.out.printf("Transformed sample points...\n");
			Point2D.Double[] tp = ac.getTransformedPath();
			for (int i = 0; i < tp.length; ++i)
			{
				System.out.printf("(%1.1f, %1.1f) ", tp[i].x, tp[i].y);
				if ((i + 1) % 8 == 0)
					System.out.printf("\n");
			}
			System.out.printf("\n");

			Point2D.Double tf = ac.getTransformedFrom();
			System.out.printf("Transformed from = (%1.1f, %1.1f)\n", tf.x, tf.y);

			Point2D.Double tt = ac.getTransformedTo();
			System.out.printf("Transformed to = (%1.1f, %1.1f)\n", tt.x, tt.y);
			System.out.printf("-----\n");
		}

		System.out.printf("Accuracy measures...\n");
		System.out.printf("TRE = %d\n", ac.getTRE());
		System.out.printf("TAC = %d\n", ac.getTAC());
		System.out.printf("MDC = %d\n", ac.getMDC());
		System.out.printf("ODC = %d\n", ac.getODC());
		System.out.printf("MV = %1.2f\n", ac.getMV());
		System.out.printf("ME = %1.2f\n", ac.getME());
		System.out.printf("MO = %1.2f\n", ac.getMO());
		System.out.printf("Done!\n");
	}
}