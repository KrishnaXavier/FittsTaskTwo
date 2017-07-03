import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * <h1>Throughput</h1>
 * 
 * <h3>Summary</h3>
 * 
 * <ul>
 * <li>Calculate Fitts' throughput for a sequence of trials
 * <p>
 * 
 * <li>
 * Input data: task conditions, selection coordinates, task completion times
 * <p>
 * 
 * <li>Two uses:
 * 
 * <ul>
 * <li>As a class embedded in experiment software
 * <li>As a utility program to process data from a terminal prompt
 * </ul>
 * <p>
 * </ul>
 * 
 * <h3>Background</h3>
 * 
 * Throughput is a commonly used as a dependent variable in experimental research on pointing
 * devices or point-select techniques. However, in reviewing research papers where throughput is
 * used, different methods of calculating Throughput are found. Because of this, it is difficult to
 * compare results between research studies. Furthermore, it is often difficult to determine with
 * certainty how throughput was calculated. This API and Java class seek to remedy these problems by
 * (i) providing clear and simple instructions on how to calculate throughput and (ii) disseminating
 * a tool to do the calculations. Measures related to throughput are also calculated and described,
 * as noted below.
 * <p>
 * 
 * This is not a primer on Fitts' law or on Fitts' throughput. For background discussions, the
 * reader is directed to the references cited at the end of this API. Let's begin.
 * <p>
 * 
 * Fitts' throughput is calculated on a sequence of trials. The premise for this is twofold:
 * <p>
 * 
 * <ol>
 * <li>Throughput cannot be calculated on a single trial.
 * <p>
 * <li>A sequence of trials is the smallest set of user actions for which throughput can be
 * calculated as a measure of performance.
 * </ol>
 * <p>
 * 
 * (Note: A "sequence" is sometimes called a "block". The test conditions, including <i>A</i> and
 * <i>W</i>, are the same for the entire sequence.)
 * <p>
 * 
 * On the first point, the calculation of Throughput includes the variability in selection
 * coordinates. The variability is analogous to "noise" in the information-theoretic metaphor upon
 * which Fitts' law is based. Thus, multiple selections are required and from the coordinates of
 * selection, the variability in the coordinates is computed.
 * <p>
 * 
 * The second point is mostly of ecological concern. After performing a single sequence of trials,
 * the user pauses, rests, stretches, adjusts the apparatus, has a sip of tea, adjusts her position
 * on a chair, or something. There is a demarcation between sequences and for no particular purpose
 * other than to provide a break or pause between sequences, or perhaps to change to a different
 * test condition. It seems reasonable that once a sequence is over, it is over! Behaviours were
 * observed and measured and the next sequence should be treated as a separate unit of action with
 * separate performance measurements.
 * <p>
 * 
 * Related to the second point is the following: Throughput should not be calculated on larger sets
 * of raw data. For example, if six participants each perform five sequences of trials under the
 * same conditions, there are 6 &times; 5 &equals; 30 calculations of throughput, rather than a
 * single calculation using the pooled data.
 * <p>
 * 
 * The <code>Throughput</code> code may be used in two ways: (i) as a class embedded in
 * custom-designed software or (ii) as a utility program executed from a command prompt.
 * <p>
 * 
 * <!----------------------------------------------------------------------------------------->
 * <b>Throughput Class</b>
 * <p>
 * 
 * As a class embedded in custom-designed software, the <code>Throughput</code> class file is placed
 * in the same directory as other class files for the application. This API provides all the details
 * necessary to use the <code>Throughput</code> class.
 * <p>
 * 
 * Use of this class begins with the instantiation of a <code>Throughput</code> object. The
 * constructor receives the data necessary to characterise the sequence. The data consist of a
 * String, two doubles, four arrays, and two integers. The arrays are all of the same size, with the
 * size equal to the number of trials in the sequence. The data, or arguments, passed to the
 * constructor are as follows:
 * <p>
 * 
 * <table border=1 cellspacing=0 width=80% align=center cellpadding=5>
 * 
 * <tr bgcolor=#cccccc>
 * <th>Argument
 * <th>Type
 * <th>Description
 * 
 * <tr>
 * <td><code>code</code>
 * <td><code>String
 * <td>A code to represent the conditions used for testing.
 * This argument is used to associate test conditions (participant code, block code, device code,
 * etc.) with the sequence. A null string may be passed if no code is necessary.
 * 
 * <tr>
 * <td><code>amplitude</code>
 * <td><code>double
 * <td>Target amplitude for the sequence
 * 
 * <tr>
 * <td><code>width</code>
 * <td><code>double
 * <td>Target width for the sequence
 * 
 * <tr>
 * <td><code>from</code>
 * <td><code>Point2D.Double[]
 * <td>The specified starting coordinates for each trial (center of the "from" target)
 * 
 * <tr>
 * <td><code>to</code>
 * <td><code>Point2D.Double[]
 * <td>The specified ending coordinates for each trial (center of the "to" target)
 * 
 * <tr>
 * <td><code>select</code>
 * <td><code>Point2D.Double[]
 * <td>The coordinates of selection where each trial was terminated
 * 
 * <tr>
 * <td><code>mt</code>
 * <td><code>double[]
 * <td>The movement times (ms) for each trial
 * 
 * <tr>
 * <td><code>taskType</code>
 * <td><code>int
 * <td>A constant identifying if the task movements were one-dimensional (
 * <code>Throughput.ONE_DIMENSIONAL</code>) or two-dimensional (
 * <code>Throughput.TWO_DIMENSIONAL</code>)
 * 
 * <tr>
 * <td><code>responseType</code>
 * <td><code>int
 * <td> A constant identifying if the responses were serial (
 * <code>Throughput.SERIAL</code>) or discrete (<code>Throughput.DISCRETE</code>). This variable is
 * only used in calculating the effective target amplitude (see below).
 * </table>
 * <p>
 * 
 * The <code>Throughput</code> class was designed to be "universal" &mdash; as general purpose as
 * possible. It can be used both for serial and discrete tasks and for one-dimensional (1D) or
 * two-dimensional (2D) movement. For serial tasks, each trial immediately follows the preceding
 * trial. For discrete tasks, each trial includes a preparatory phase followed by a stimulus. Upon
 * detecting the stimulus, the user performs the trial. The time between the arrival of the stimulus
 * and the beginning of movement is called reaction time and is excluded from the movement time
 * recorded for the trial.
 * <p>
 * 
 * The one-dimensional (1D) case is the traditional back-and-forth task used by Fitts in his
 * original 1954 paper. The two-dimensional case is the task commonly used in accordance with the
 * ISO 9241-9 standard (updated in 2012 as ISO/TC 9241-411). For two-dimensional movements, a series
 * of targets are arranged around a layout circle. The trials proceed in a sequence. After each
 * selection, the next selection is the target on the opposite side of the layout circle. Every
 * second selection is beside a target previously selected, thus the movements progress around the
 * layout circle until all targets are selected.
 * <p>
 * 
 * Throughput is calculated for each sequence of trials as
 * <p>
 * 
 * <blockquote> <i>TP</i> = <i>ID</i><sub>e</sub> / <i>MT</i> </blockquote>
 * 
 * where
 * 
 * <blockquote> <i>ID</i><sub>e</sub> = log<sub>2</sub>(<i>A</i><sub>e</sub> / <i>W</i><sub>e</sub>
 * + 1) </blockquote>
 * 
 * and
 * 
 * <blockquote> <i>W</i><sub>e</sub> = 4.133 &times; <i>SD</i><sub>x</sub> </blockquote>
 * 
 * <i>MT</i> is the mean movement time per trial (in seconds). <i>ID</i><sub>e</sub> is the
 * effective index of difficulty (in bits). Throughput (<i>TP</i>) is the rate of information
 * processing (in bits per second).
 * <p>
 * 
 * The subscript "e" beside <i>ID</i> is for "effective". In the effective form, the index of
 * difficulty reflects the task the participant actually did rather than the task the participant
 * was presented with.
 * <p>
 * 
 * The effective target width (<i>W</i><sub>e</sub>) is calculated from <i>SD</i><sub>x</sub>, which
 * is the standard deviation in the selection coordinates for the sequence of trials. The selection
 * coordinates are projected onto the task axis to maintain the inherent one-dimensionality of
 * Fitts' law. The task axis is a line between the center of the desired start point ("from") and
 * the desired end point ("to"). The projection is done using simple calculations involving the
 * Pythagorean identity. Details are provide below and also in the source code.
 * <p>
 * 
 * The effective target amplitude, <i>A</i><sub>e</sub>, is the mean of the actual movement
 * amplitudes over a sequence of trials. <i>A</i><sub>e</sub> is measured along the task axis.
 * <p>
 * 
 * The following figure illustrates the geometry for a single trial, including the point of
 * selection:
 * <p>
 * 
 * <center> <img src="Throughput-1.jpg" width= 750> </center>
 * <p>
 * 
 * Although the figure shows a trial with horizontal movement to the right, the calculations are
 * valid for movements in any direction or angle. Circular targets are shown to provide a conceptual
 * visualization of the task. Other target shapes are possible, depending on the setup in the
 * experiment. The calculation begins by computing the length of the sides connecting the
 * <code>from</code>, <code>to</code>, and <code>select</code> points in the figure:
 * <p>
 * 
 * <blockquote><code>
 * double a = Math.hypot(x1 - x2, y1 - y2);<br>
 * double b = Math.hypot(x - x2, y - y2);<br>
 * double c = Math.hypot(x1 - x, y1 - y);
 * </blockquote></code>
 * 
 * The <i>x-y</i> coordinates correspond to the <code>from</code>
 * (<i>x</i><sub>1</sub>,&nbsp;<i>y</i><sub>1</sub>), <code>to</code>
 * (<i>x</i><sub>2</sub>,&nbsp;<i>y</i><sub>2</sub>), and <code>select</code>
 * (<i>x</i>,&nbsp;<i>y</i>) points in the figure. Given <code>a</code>, <code>b</code>, and
 * <code>c</code>, as above, <code>dx</code> is then calculated:
 * <p>
 * 
 * <blockquote><code>
 * double dx = (c * c - b * b - a * a) / (2.0 * a);
 * </blockquote></code>
 * 
 * Note that <code>dx</code> is 0 for a selection at the center of the target (as projected on the
 * task axis), positive for a selection on the far side of the center, and negative for a selection
 * on the near side.
 * <p>
 * 
 * The effective target amplitude is simply <code>a + dx</code>. For serial tasks, an additional
 * adjustment for <i>A</i><sub>e</sub> is to add <code>dx</code> from the previous trial (for all
 * trials after the first). This is necessary since each trial begins at the selection point of the
 * previous trial. For discrete tasks, the trial is assumed to begin at the center of the "from"
 * target.
 * <p>
 * 
 * The use of the effective target amplitude (<i>A</i><sub>e</sub>) has little influence on
 * throughput, provided selections are distributed about the center of the targets. However, it is
 * important to use <i>A</i><sub>e</sub> to prevent “gaming the system.” For example, if all
 * movements fall short and only traverse, say, ¾ of <i>A</i>, throughput is artificially inflated
 * if calculated using <i>A</i>. Using <i>A</i><sub>e</sub> prevents this. This is part of the
 * overall premise in using “effective” values: Participants get credit for what they actually did,
 * not for what they were asked to do.
 * <p>
 * 
 * Once a throughput object is instantiated, throughput and related measures are retrieved using
 * public instance methods. The most relevant methods are as follows:
 * <p>
 * 
 * <table border=1 cellspacing=0 width=80% align=center cellpadding=5>
 * 
 * <tr bgcolor=#cccccc>
 * <th>Method
 * <th>Return Type
 * <th>Description
 * 
 * <tr>
 * <td><code>getThroughput</code>
 * <td><code>double
 * <td>Throughput for the sequences of trials
 * 
 * <tr>
 * <td><code>getAe</code>
 * <td><code>double
 * <td>Effective target amplitude for the sequence
 * 
 * <tr>
 * <td><code>getWe</code>
 * <td><code>double
 * <td> Effective target width for the sequence
 * 
 * <tr>
 * <td><code>getIDe</code>
 * <td><code>double
 * <td> Effective index of difficulty for the sequence
 * 
 * <tr>
 * <td><code>getX</code>
 * <td><code>double
 * <td> Mean of the <i>x</i>-selection coordinates for the sequence, as
 * projected on the task axis and mapped relative to the center of the target. A return value of 0.0
 * corresponds to selections clustered about the center of the target, while positive or negative
 * values correspond to selections with a mean on the near-side or far-side of the center of the
 * target, respectively.
 * 
 * <tr>
 * <td><code>getSDx</code>
 * <td><code>double
 * <td> Standard deviation in the selection coordinates, as projected on the task axis
 * 
 * <tr>
 * <td><code>getDeltaX</code>
 * <td><code>double[]
 * <td>The <i>x</i>-selection coordinates, as projected on the task axis
 * 
 * <tr>
 * <td><code>getSkewness</code>
 * <td><code>double
 * <td>Skewness in the distribution formed by the selection coordinates
 * 
 * <tr>
 * <td><code>getKurtosis</code>
 * <td><code>double
 * <td>Kurtosis in the distribution formed by the selection coordinates
 * 
 * <tr>
 * <td><code>isNormal</code>
 * <td><code>boolean
 * <td>The result of a test of the null
 * hypothesis that the distribution of selection coordinates is normally distributed (<i>p</i> <
 * .05). The Lilliefors test is used. If <code>false</code> is returned the null hypothesis is
 * rejected, implying the distribution is not normal. If <code>true</code> is returned the null
 * hypothesis is not rejected, implying the distribution has passed the test for normality.
 * 
 * </table>
 * <p>
 * 
 * <!---------------------------------------------------------------------------------------->
 * <b>Throughput Utility Program</b>
 * <p>
 * 
 * The <code>Throughput</code> class may be executed from a command prompt to process data in a
 * file. The following is the usage message if executed without arguments:
 * <p>
 * 
 * <pre>
 *      PROMPT>java Throughput
 *      Usage: java Throughput datafile -t|-s
 *      
 *         where datafile = file containing data
 *               -t = table output
 *               -s = summary output (1 line per sequence)
 * </pre>
 * 
 * The first task in using <code>Throughput</code> as a utility is to organize the data in a file
 * and in the correct format. The format is simple. As an example, the data for a sequence with 20
 * trials are organized in 25 lines:
 * <p>
 * 
 * <pre>
 *      Line     Data (comment)
 *      1        Code header (String – once only)
 *      2        Code (String – once per sequence)
 *      3        A, W (2 doubles)
 *      4        Task type, Response type (2 String constants)
 *      5-24     From [x/y], To [x/y], Select [x/y], MT (7 doubles)
 *      25       Blank (next sequence begins on next line)
 * </pre>
 * 
 * Consider the file <code><a href="example-data.txt">example-data.txt</a></code>, which contains
 * data formatted as above. The <code>Throughput</code> utility processes the data as follows
 * (slightly abbreviated):
 * <p>
 * 
 * <pre>
 *      PROMPT>java Throughput example-data.txt -t
 *      Code = P07,B05,G03,C03
 *     A = 312.4, W = 130.2 (ID = 1.77)
 *     Task_type = 1D, Response_type = Serial
 *     Data...
 *     ============================================================
 *     xFrom    yFrom    xTo      yTo      xSelect   ySelect    MT
 *     ------------------------------------------------------------
 *     540.2    592.0    227.8    592.0    218.5     534.3      263
 *     227.8    592.0    540.2    592.0    529.5     496.3      268
 *     540.2    592.0    227.8    592.0    195.0     608.0      248
 *     227.8    592.0    540.2    592.0    533.5     547.0      234
 *     540.2    592.0    227.8    592.0    209.8     651.0      251
 *     227.8    592.0    540.2    592.0    607.0     554.0      253
 *     540.2    592.0    227.8    592.0    231.3     650.8      283
 *     227.8    592.0    540.2    592.0    540.8     568.3      215
 *     540.2    592.0    227.8    592.0    231.9     642.5      301
 *     227.8    592.0    540.2    592.0    560.5     567.0      267
 *     540.2    592.0    227.8    592.0    207.5     653.8      259
 *     227.8    592.0    540.2    592.0    524.5     604.8      259
 *     540.2    592.0    227.8    592.0    239.1     704.3      248
 *     227.8    592.0    540.2    592.0    515.0     610.9      243
 *     540.2    592.0    227.8    592.0    180.0     675.5      242
 *     227.8    592.0    540.2    592.0    501.0     606.5      253
 *     540.2    592.0    227.8    592.0    215.5     666.0      244
 *     227.8    592.0    540.2    592.0    572.0     621.5      256
 *     540.2    592.0    227.8    592.0    215.4     690.5      252
 *     227.8    592.0    540.2    592.0    521.5     641.8      210
 *     ============================================================
 * 
 *     Number_of_trials = 20
 *     Select(x'): 9.3, -10.7, 32.8, ...
 *     -----
 *     Mean(x') = 6.86
 *     SD(x') = 25.66
 *     Skewness = 0.51
 *     Kurtosis = 0.31
 *     Is_normal? = true
 *     -----
 *     Misses = 1
 *     Error_rate = 5.0%
 *     -----
 *     Ae = 327.1
 *     We = 106.1
 *     IDe = 2.03
 *     MT = 252.3
 *     Throughput = 8.04
 * </pre>
 * 
 * The <code>–t</code> option is used to provide output in a tabular format (see above). The first
 * part of the output simply echoes the input data in human readable form. After that, summary data
 * available through the <code>Throughput</code> class are shown, culminating with the value of
 * throughput (in bits per second).
 * <p>
 * 
 * As well as the values used in computing throughput, the <code>Throughput</code> utility provides
 * information about the distribution of the selection coordinates, as projected on the task axis.
 * This includes the skewness, kurtosis, and the results of a normality test. These data are useful
 * if the research seeks to examine whether the selection coordinates form a Gaussian distribution,
 * as assumed in the signal-and-noise model from which Fitts’ law emerged. The
 * <code>Is_normal?</code> output is the result of a normality test. The null hypothesis is that the
 * selection coordinates are normally distributed (<i>p</i> < .05). The Lilliefors test is used. If
 * false is returned the null hypothesis is rejected, implying the distribution is not normal. If
 * true is returned the null hypothesis is not rejected, implying the distribution has passed the
 * test for normality.
 * <p>
 * 
 * The <code>Throughput</code> utility also outputs the number of misses in the sequence and the
 * error rate (%). These data were not explicitly provided to the <code>Throughput</code> class.
 * They are calculated based on the geometry of the trials, the task type, and the selection
 * coordinates. The sequence of trials in the example above is from a target selection task using
 * finger input on a touchscreen device. The outcome was <i>TP</i> = 7.94 bps. This value is higher
 * than the <i>TP</i> typically reported for the mouse, which is generally in the 4 to 5 bps range.
 * <p>
 * 
 * The <code>–t</code> (table) option produces informative output; however, the organization is
 * awkward if the analysis involves hundreds of sequences of trials, as typical in experimental
 * research. For this, the <code>–s</code> (summary) option is more useful. With the <code>–s</code>
 * option, the output is a rectangular, comma-delimited matrix with full-precision data. There is a
 * header row followed by one summary row per sequence. The number of columns is <i>n</i> + 15,
 * where <i>n</i> is the number of comma-delimited items in the code string (see the first two lines
 * in <a href="example-data.txt"><code>example-data.txt</code></a>). The fifteen columns following
 * the code columns contain the summary data, excluding the raw data. The header line identifies the
 * data in each column.
 * <p>
 * 
 * The goal with the <code>–s</code> option is to provide output suitable for importing into a
 * spreadsheet or statistics application where the real work of analysing the data begins. Here's an
 * example for the data in <code><a href="example-data.txt">example-data.txt</a></code>:
 * <p>
 * 
 * <pre>
 *      PROMPT>java Throughput example-data.txt -s
 *      Participant,Block,Group,Condition,Task,Response,A,W,ID,N,Skewness,Kurtosis,IsNormal,Ae,We,IDe,MT,Misses,Throughput
 *      P07,B05,G03,C03,1D,Serial,312.406770,130.169500,1.765535,20,0.511808,0.314840,true,327.058351,106.062885,2.029851,252.340000,1,8.044111
 * </pre>
 * <p>
 * 
 * Imported into a spreadsheet, the data above appear as follows (click to enlarge):
 * <p>
 * 
 * <center> <a href="Throughput-2.jpg"><img src="Throughput-2.jpg" width=700></a> </center>
 * <p>
 * 
 * Of course, this is just a simple example. For a complete experiment, the data are likely to span
 * hundreds, perhaps thousands, of rows. With these, the task of summarizing and analysing the data
 * begins.
 * <p>
 * 
 * Good luck. For comments or questions, please get in touch (<code>mack "at" cse.yorku.ca</code> ).
 * <p>
 * 
 * <h3>References</h3>
 * <p>
 * 
 * 
 * 
 * Fitts, P. M., <a href="http://psycnet.apa.org/journals/xge/121/3/262/">The information capacity
 * of the human motor system in controlling the amplitude of movement</a>, <i>Journal of
 * Experimental Psychology</i>, <i>47</i>, 1954, 381-391. [<a href=
 * "https://www.cs.colorado.edu/~palen/courses/5919/CourseReadings/INFORMATION%20CAPACITY%20OF%20THE%20HUMAN%20MOTOR%20SYSTEM.pdf"
 * >PDF</a> &ndash; 1992 reprint]
 * <p>
 * 
 * MacKenzie, I. S., <a href="http://www.yorku.ca/mack/HCI.html">Fitts' law as a research and design
 * tool in human-computer interaction</a>, <i>Human-Computer Interaction</i>, <i>7</i>, 1992,
 * 91-139. [<a href="http://www.yorku.ca/mack/hci1992.pdf">PDF</a>]
 * <P>
 * 
 * Soukoreff, R. W. and MacKenzie, I. S., <a href="http://www.yorku.ca/mack/ijhcs2004.html">Towards
 * a standard for pointing device evaluation: Perspectives on 27 years of Fitts' law research in
 * HCI</a>, <i>International Journal of Human-Computer Studies</i>, <i>61</i>, 2004, 751-789. [<a
 * href="http://www.yorku.ca/mack/ijhcs2004.pdf">PDF</a>]
 * <P>
 * 
 * @author Scott MacKenzie, 2013-2016
 * @author William Soukoreff, 2013
 */
public class Throughput
{
	final static double LOG_TWO = 0.693147181;
	final static double SQRT_2_PI_E = 4.132731354;

	// int constants for response type
	final static int SERIAL = 100;
	final static int DISCRETE = 101;

	// int constants for tasks type
	final static int ONE_DIMENSIONAL = 200;
	final static int TWO_DIMENSIONAL = 201;

	/*
	 * The following are the core set of data values needed to compute throughput and other measures
	 * provided in this class.
	 */
	String code;
	double amplitude, width;
	Point2D.Double[] from, to, select;
	double[] mt;
	int numberOfTrials;
	boolean serialTask;
	int responseType, taskType;

	/*
	 * The following arrays are populated with values calculated from the data in the arrays above.
	 */
	double[] deltaX;
	double[] ae;
	int[] miss;

	// Blank constructor. Must be followed with setData
	Throughput()
	{
	}

	// Constructor with data
	Throughput(String codeArg, double amplitudeArg, double widthArg, int taskTypeArg, int responseTypeArg,
			Point2D.Double[] fromArg, Point2D.Double[] toArg, Point2D.Double[] selectArg, double[] mtArg)
	{
		setData(codeArg, amplitudeArg, widthArg, taskTypeArg, responseTypeArg, fromArg, toArg, selectArg, mtArg);
	}

	/**
	 * Set the data for this Throughput object. This method can be used to provide a new set of the
	 * data to the Throughput object (without instantiated a new object).
	 * 
	 */
	public void setData(String codeArg, double amplitudeArg, double widthArg, int taskTypeArg, int responseTypeArg,
			Point2D.Double[] fromArg, Point2D.Double[] toArg, Point2D.Double[] selectArg, double[] mtArg)
	{
		// load core requisite data
		code = codeArg;
		amplitude = amplitudeArg;
		width = widthArg;
		taskType = taskTypeArg;
		responseType = responseTypeArg;
		from = fromArg;
		to = toArg;
		select = selectArg;
		mt = mtArg;

		// calculate data that depend on the core data loaded above (begin by initializing arrays)
		numberOfTrials = mt.length;
		deltaX = new double[mt.length];
		ae = new double[mt.length];
		miss = new int[mt.length];

		// bug fix (Oct 22, 2015) thanks to Francesca Roig
		serialTask = (responseType == SERIAL);

		// fill deltaX, ae, and miss arrays
		for (int i = 0; i < to.length; ++i)
		{
			// NOTE: statement below commented out. Legacy code (but don't want to delete yet)
			// verifyAmplitudeData(amplitude, a, i, taskType, numberOfTrials);

			deltaX[i] = getTrialDeltaX(from[i], to[i], select[i]);

			/*
			 * Compute the effective movement amplitude. The computed amplitude, a, is adjusted by
			 * adding dx at the end of the trial to give the actual amplitude moved (as projected on
			 * the task axis).
			 * 
			 * For serial tasks, we also adjust for the starting position by adding dx from the
			 * previous trial (if i > 0).
			 */

			ae[i] = getTrialAe(from[i], to[i], select[i]);
			if (serialTask && i > 0)
				ae[i] += deltaX[i - 1];

			/*
			 * Compute whether or not the target was missed. This information is not explicitly
			 * provided to the Throughput class, so we need to calculate it. For the 1D case, the
			 * target is missed if deltaX is more than half the target width. For the 2D case, we
			 * assume the targets are circular, which is typically the case. The target is missed if
			 * the distance from the selection coordinate to the center of the target is greater
			 * than half the diameter (i.e., width) of the target circle.
			 */
			double distanceToCenter = Math.hypot(select[i].x - to[i].x, select[i].y - to[i].y);
			if (taskType == Throughput.ONE_DIMENSIONAL)
				miss[i] = Math.abs(deltaX[i]) > width / 2.0 ? 1 : 0;
			else if (taskType == Throughput.TWO_DIMENSIONAL)
				miss[i] = distanceToCenter > width / 2.0 ? 1 : 0;
			else
				miss[i] = -1;
		}
	}

	/*
	 * Verify the amplitude data. The Throughput object receives as arguments the "amplitude" for
	 * the sequence of trials as well as two Point2D.Double arrays specifying the center of the
	 * "from" and "to" targets for each trial. This method verifies the correctness of these data.
	 * The process and calculations are different for 1D tasks and 2D tasks. The 1D case is simple:
	 * The amplitude passed is the distance between the center of the two targets -- the "from" and
	 * "to" targets. This is identical for each trial in the sequence. The 2D case is more
	 * complicated.
	 * 
	 * For the 2D case, the amplitude passed to the Throughput object is the diameter of the layout
	 * circle. This value is not necessarily the amplitude of movement for the trials, even if the
	 * movements are perfectly executed. The movement amplitudes for perfect movements are
	 * calculated here, and given the label "taskAdjustedAmplitude". The calculations are different
	 * depending on whether there is an even number of targets or an odd number of targets.
	 * 
	 * 2D - Even Number of Targets. If there is an even number of targets, the first trial (index =
	 * 0) begins by selecting the start target and then moving directly across the layout circle to
	 * the target on the opposite side. The movement distance equals the diameter of the layout
	 * circle. However, the next trial (index = 1) is to the target beside the start target. The
	 * movement distance in this case is less than the diameter of the layout circle. The movement
	 * distance depends on the number of targets.
	 * 
	 * 2D - Odd Number of Targets. If there is an odd number of targets, the movement distance is
	 * the same for every trial. The distance is less than the diameter of the layout circle,
	 * because the target is slightly displaced from the location directly across the layout circle.
	 * 
	 * The calculations below account for the peculiarities of the tasks, as just described.
	 * 
	 * When all the calculations are done, we compare the taskAdjustedAmplitude to the value "a"
	 * calculated in the setData method and passed here as an argument. The taskAdjustedAmplitude
	 * and a should be the same. Because we are dealing with floating point arithmetic and there may
	 * be some integer rounding issues for the location of targets as rendered on the screen, the
	 * comparison only requires that taskAdjustedAmplitude and a are within WIGGLE units of each
	 * other (see below). If they are, all is well. If they aren't, there's a problem.
	 */
	@SuppressWarnings("unused")
	private void verifyAmplitudeData(double amplitude, double a, int trialIndex, int taskType, int numberOfTrials)
	{
		// allow this much difference when verifying amplitudes
		final double WIGGLE = 2.0;

		// calculate the adjusted amplitude
		double taskAdjustedAmplitude = -1.0;

		if (taskType == Throughput.TWO_DIMENSIONAL)
		{
			// even number of trials (taskAdjustedAmplitude is different for even- and odd-numbered
			// trials)
			if (numberOfTrials % 2 == 0)
				if (trialIndex % 2 == 0) // even-indexed trials
					taskAdjustedAmplitude = amplitude;
				else
				// odd-indexed trials
				{
					double b = amplitude * Math.sin(Math.PI / numberOfTrials);
					double theta = 0.5 * Math.PI * (numberOfTrials - 2) / numberOfTrials;
					double c = b * Math.sin(theta);
					double x = b * Math.cos(theta);
					taskAdjustedAmplitude = Math.sqrt((amplitude - x) * (amplitude - x) + c * c);
				}
			else
			// odd number of trials (taskAdjustedAmplitude is the same for every trial in the
			// sequence)
			{
				double b = amplitude * Math.sin(Math.PI / numberOfTrials);
				double m = 2.0 * numberOfTrials;
				double theta = 0.5 * ((Math.PI * (m - 2.0)) / m);
				double x = (b / 2.0) / Math.tan(theta);
				double h = amplitude - x;
				taskAdjustedAmplitude = Math.sqrt(h * h + (b / 2.0) * (b / 2.0));
			}
		} else if (taskType == Throughput.ONE_DIMENSIONAL)
		{
			taskAdjustedAmplitude = amplitude; // the 1D case is simple (but still worth checking)
		}

		if (Math.abs(a - taskAdjustedAmplitude) > WIGGLE)
			System.out.printf("Oops! amplitude=%1.1f, task_adjusted_amplitude= %1.2f, computed_amplitude= %1.2f\n",
					amplitude, taskAdjustedAmplitude, a);
	}

	/**
	 * Returns the code associated with this sequence of trials. The code is the string assigned to
	 * the sequence to associate test conditions (e.g., participant code, device code, etc.) with
	 * the sequence.
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * Returns the Throughput for the sequence of trials.
	 */
	public double getThroughput()
	{
		double aeMean = mean(ae);
		double sdx = sd(deltaX);
		double we = SQRT_2_PI_E * sdx;
		double ide = Math.log(aeMean / we + 1.0) / LOG_TWO; // bits
		double mtMean = mean(mt) / 1000.0; // seconds
		return ide / mtMean; // bits per second
	}

	/**
	 * Returns the mean movement time (ms) for the sequence of trials.
	 */
	public double getMT()
	{
		return mean(mt); // milliseconds
	}

	/**
	 * Returns the number of trials in this sequence.
	 */
	public int getNumberOfTrials()
	{
		return numberOfTrials;
	}

	/**
	 * Returns the task type for this sequence. The return value is the int given to the Throughput
	 * object (via the constructor or setData). Should be Throughput.ONE_DIMENSIONAL or
	 * Throughout.TWO_DIMENSIONAL.
	 */
	public int getTaskType()
	{
		return taskType;
	}

	/**
	 * Returns a string representing the task type for this sequence. The string returned is "1D",
	 * "2D", or "?" (if the task type is unknown).
	 */
	public String getTaskTypeString(int taskType)
	{
		if (taskType == ONE_DIMENSIONAL)
			return "1D";
		else if (taskType == TWO_DIMENSIONAL)
			return "2D";
		else
			return "?";
	}

	/**
	 * Returns the response type for this sequence. The value returned is the int constant passed to
	 * the Throughput object in the first place (via the constructor or setData). Should be
	 * Throughput.SERIAL or Throughput.DISCRETE.
	 */
	public int getResponseType()
	{
		return responseType;
	}

	/**
	 * Returns a string representing the response type for this sequence. The string returned is
	 * "Serial", "Discrete", of "?" (if the response type is unknown).
	 */
	public String getResponseTypeString(int responseType)
	{
		if (responseType == SERIAL)
			return "Serial";
		else if (responseType == DISCRETE)
			return "Discrete";
		else
			return "?";
	}

	/**
	 * Returns a point array containing the "from" points for the trials in this sequence. The
	 * "from" points are the coordinates of the center of the target from which each trial begins.
	 */
	public Point2D.Double[] getFrom()
	{
		return from;
	}

	/**
	 * Returns a point array containing the "to" points for the trials in this sequence. The "to"
	 * points are the coordinates of the center of the target to which each trial proceeds.
	 */
	public Point2D.Double[] getTo()
	{
		return to;
	}

	/**
	 * Returns a point array containing the "select" points for the trials in this sequence. The
	 * "select" points are the coordinates of the point of selection where each trial terminated.
	 */
	public Point2D.Double[] getSelect()
	{
		return select;
	}

	/**
	 * Returns the double array holding the mt (movement time) values for the trials in this
	 * sequence.
	 */
	public double[] getMTArray()
	{
		return mt;
	}

	/**
	 * Returns the standard deviation in the selection coordinates for this sequence of trials. The
	 * coordinates are projected onto the task axis.
	 */
	public double getSDx()
	{
		return sd(deltaX);
	}

	/**
	 * Returns the mean of the selection coordinates for this sequence of trials. The coordinates
	 * are projected onto the task axis.
	 */
	public double getX()
	{
		return mean(getDeltaX());
	}

	/**
	 * Returns the array of x-selection coordinates for this sequence of trials. The coordinates are
	 * projected onto the task axis.
	 */
	public double[] getDeltaX()
	{
		return deltaX;
	}

	/**
	 * Returns the specified amplitude for the trials in this sequence.
	 * 
	 * NOTE: This value is not used in calculating Throughput. It is provided only as a convenience.
	 */
	public double getA()
	{
		return amplitude;
	}

	/**
	 * Returns the effective amplitude for the trials in this sequence. The effective amplitude is
	 * the mean of the actual movement amplitudes for the sequence of trials, as projected on the
	 * task axis.
	 */
	public double getAe()
	{
		return mean(ae);
	}

	/**
	 * Returns the specified target width for this sequence of trials.
	 * 
	 * NOTE: This value is not used in calculating Throughput. It is provided only as a convenience.
	 */
	public double getW()
	{
		return width;
	}

	/**
	 * Returns the effective target width for this sequence of trials. The effective target width is
	 * 4.133 x SDx, where SDx is the standard deviation in the selection coordinates, as projected
	 * onto the task axis.
	 */
	public double getWe()
	{
		return SQRT_2_PI_E * getSDx();
	}

	/**
	 * Returns the specified index of difficulty for this sequence of trials. The specified index of
	 * difficulty is ID = log2(A/W + 1).
	 * 
	 * NOTE: This value is not used in calculating Throughput. It is provided only as a convenience.
	 */
	public double getID()
	{
		return Math.log(getA() / getW() + 1) / LOG_TWO;
	}

	/**
	 * Returns the effective index of difficulty for this sequence of trials. The effective index of
	 * difficulty, IDe = log2(Ae/We + 1).
	 */
	public double getIDe()
	{
		return Math.log(getAe() / (SQRT_2_PI_E * getSDx()) + 1.0) / LOG_TWO;
	}

	/**
	 * Returns the number of misses for this sequence.
	 */
	public int getMisses()
	{
		int count = 0;
		for (int i = 0; i < getNumberOfTrials(); ++i)
			count += miss[i];
		return count;
	}

	/**
	 * Returns the error rate as a percentage.
	 */
	public double getErrorRate()
	{
		return (double)getMisses() / getNumberOfTrials() * 100.0;
	}

	/**
	 * Returns the skewness in the selection coordinates for this sequence of trials. The selection
	 * coordinates are projected onto the task axis.
	 */
	public double getSkewness()
	{
		return getSkewness(getDeltaX());
	}

	/**
	 * Returns the kurtosis in the selection coordinates for this sequence of trials. The selection
	 * coordinates are projected onto the task axis.
	 */
	public double getKurtosis()
	{
		return getKurtosis(getDeltaX());
	}

	/**
	 * Returns a boolean holding the result of a Lilliefors test for normality. The test is done at
	 * an alpha of 0.05. The null hypothesis is that the selection coordinates in this sequence of
	 * trials, as projected on the task axis, are normally distributed. If true is returned, the
	 * null hypothesis is retained (not rejected). If false is returned, the null hypothesis is
	 * rejected.
	 */
	public boolean getIsNormal()
	{
		return Lilliefors.isNormal(getDeltaX());
	}

	// ===========================
	// S T A T I C _ M E T H O D S
	// ===========================

	/**
	 * Returns deltaX for a trial. The geometry for a trial is defined by three points: from (center
	 * of the "from" target), to (center of the "to" target), and select (the selection coordinate).
	 * These are used in computing deltaX, which is the distance from the selection coordinate to
	 * the target center, as projected on the task axis.
	 * <p>
	 * 
	 * NOTE: This calculation is correct, but a diagram helps to visualize the geometry. deltaX is
	 * negative for a selection on the "near side" of the target center (undershoot) and positive
	 * for a selection on the "far side" of the target center (overshoot). For a near-side
	 * selection, the a-b-c triangle is acute (i.e., a^2 + b^2 > c^2). For a far-side selection the
	 * a-b-c triangle is obtuse (i.e., a^2 + b^2 < c^2).
	 * <p>
	 * 
	 * NOTE: This method is defined as a static method so that is may be called by an application on
	 * a per-trial basis. Recall that instances of the Throughput class work with the data for the
	 * entire sequence.
	 * 
	 */
	public static double getTrialDeltaX(Point2D.Double from, Point2D.Double to, Point2D.Double select)
	{
		// start-of-trial coordinate (centre of the "from" target)
		double x1 = from.x;
		double y1 = from.y;

		// centre coordinate of the target to select (center of the "to" target)
		double x2 = to.x;
		double y2 = to.y;

		// actual selection coordinate ("select")
		double x = select.x;
		double y = select.y;

		// compute length of the sides of the triangle formed by the three points above
		double a = Math.hypot(x1 - x2, y1 - y2); // a is the specified amplitude
		double b = Math.hypot(x - x2, y - y2); // b is the distance from the selection point to the
												// target center
		double c = Math.hypot(x1 - x, y1 - y);

		// debug/demo code...
		// String type = (a * a + b * b) > (c * c) ? "ACUTE" : "obtuse";
		// System.out.printf("a=%7.2f   b=%7.2f   c=%7.2f   type=%6s   dx=%7.2f\n", a, b, c, type,
		// deltaX[i]);

		/*
		 * Compute and return deltaX. This calculation is correct, but a diagram helps to visualize
		 * the geometry. dx is negative for a selection on the "near side" of the target center
		 * (undershoot) and positive for a selection on the "far side" of the target center
		 * (overshoot). For a near-side selection, the a-b-c triangle is acute (i.e., a^2 + b^2 >
		 * c^2). For a far-side selection the a-b-c triangle is obtuse (i.e., a^2 + b^2 < c^2).
		 */
		return (c * c - b * b - a * a) / (2.0 * a);
	}

	/**
	 * Returns the effective amplitude (Ae) for a trial. The geometry for a trial is defined by
	 * three points: from (center of the "from" target), to (center of the "to" target), and select
	 * (the selection coordinate). These are used in computing Ae, which is A (the distance between
	 * the "from" and "to" points) plus deltaX. See as well, getTrialDeltaX.
	 * <p>
	 * 
	 * NOTE: The value of Ae calculated here assumes the trial started at the "from" coordinate. For
	 * serial responses, this may not be the case. An additional adjustment may be warranted for the
	 * beginning of the trial, such as adding deltaX from the previous trial (for all trials after
	 * the first trial in a sequence).
	 * <p>
	 * 
	 * NOTE: This method is defined as a static method so that is may be called by an application on
	 * a per-trial basis. Recall that instances of the Throughput class work with the data for the
	 * entire sequence.
	 * 
	 */
	public static double getTrialAe(Point2D.Double from, Point2D.Double to, Point2D.Double select)
	{
		double a = Math.hypot(to.x - from.x, to.y - from.y);
		double dx = getTrialDeltaX(from, to, select);
		return a + dx;
	}

	/**
	 * Returns the skewness in the specified array of doubles.
	 */
	public static double getSkewness(double[] d)
	{
		double m = mean(d);
		double sd = sd(d);
		double skew = 0.0;
		double n = (double)d.length;
		double factor = n / ((n - 1.0) * (n - 2.0));
		for (int i = 0; i < d.length; ++i)
			skew += Math.pow((d[i] - m) / sd, 3.0);
		skew *= factor;
		return skew;
	}

	/**
	 * Returns the kurtosis in the specified array of doubles.
	 */
	public static double getKurtosis(double[] d)
	{
		double m = mean(d);
		double sd = sd(d);
		double kur = 0.0;
		double n = (double)d.length;
		double factor1 = (n * (n + 1.0)) / ((n - 1.0) * (n - 2.0) * (n - 3.0));
		double factor2 = (3.0 * (n - 1.0) * (n - 1.0)) / ((n - 2.0) * (n - 3.0));
		for (int i = 0; i < d.length; ++i)
			kur += Math.pow((d[i] - m) / sd, 4.0);
		kur = factor1 * kur - factor2;
		return kur;
	}

	/**
	 * Returns a boolean holding the result of a Lilliefors test for normality on the specified
	 * array of doubles. The test is done at an alpha of 0.05. The null hypothesis is that the
	 * values in the array are normally distributed. If true is returned, the null hypothesis is
	 * retained (not rejected). If false is returned, the null hypothesis is rejected.
	 */
	public static boolean getIsNormal(double[] d)
	{
		return Lilliefors.isNormal(d);
	}

	/**
	 * Calculate the mean of the values in a double array.
	 */
	private static double mean(double n[])
	{
		double mean = 0.0;
		for (int j = 0; j < n.length; j++)
			mean += n[j];
		return mean / n.length;
	}

	/**
	 * Calculate the standard deviation of values in a double array.
	 */
	private static double sd(double[] n)
	{
		double m = mean(n);
		double t = 0.0;
		for (int j = 0; j < n.length; j++)
			t += (m - n[j]) * (m - n[j]);
		return Math.sqrt(t / (n.length - 1.0));
	}

	// =================================================
	// Throughput Utility - executed via the main method
	// =================================================

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException
	{
		// number of doubles on each data line
		final int NUM = 7;

		/*
		 * The following are the header labels for the data items written as output. The data items
		 * associated with these labels are obtained from public instance methods in the Throughput
		 * class.
		 */
		final String DATA_HEADER = "Task,Response,A,W,ID,N,Skewness,Kurtosis,IsNormal,Ae,We,IDe,MT,Misses,Throughput";

		// data needed to pass to Throughput constructor
		String code;
		double a, w;
		int taskType, responseType;
		Point2D.Double[] from, to, select;
		double[] mt;

		// this Throughput object will be used for each sequence analysed here
		Throughput tp = new Throughput();

		// initialize command line options to false
		boolean tableOption = false;
		boolean summaryOption = false;
		boolean demoOption = false;
		int dataSet = -1;

		String inFile = "";

		if (args.length != 2)
		{
			usage();
		} else
		{
			inFile = args[0];

			if (args[1].equals("-t"))
				tableOption = true;
			else if (args[1].equals("-s"))
				summaryOption = true;
			else
				usage();
		}

		code = "";
		a = -1.0;
		w = -1.0;
		taskType = -1;
		responseType = -1;

		boolean firstLine = true;

		// make sure file exists
		File f = new File(inFile);
		if (!f.exists())
		{
			System.out.println("File not found: " + inFile);
			System.exit(1);
		}

		// open file for input
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		String line;
		while ((line = br.readLine()) != null)
		{
			if (line.length() == 0 || line.charAt(0) == '#')
				continue;

			if (firstLine) // the first line is the code header
			{
				firstLine = false;
				if (summaryOption)
					System.out.println(line + "," + DATA_HEADER);
				continue; // get the next line
			}

			// the first non-blank, non-comment line (excluding the header) contains the code
			// for the sequence
			code = line;

			// read the next line
			line = br.readLine();
			StringTokenizer st = new StringTokenizer(line, ", ");

			// this line contains two tokens: the specified target amplitude followed by the
			// specified target width
			if (st.countTokens() != 2)
			{
				System.out.println("Oops! Expecting amplitude and width! Bye!");
				System.exit(0);
			}
			a = Double.parseDouble(st.nextToken());
			w = Double.parseDouble(st.nextToken());

			// read the next line
			line = br.readLine();
			st = new StringTokenizer(line, ", ");

			// this line contains two tokens: the task type ("1D" or "2D") and the response type
			// ("Serial" or
			// "Discrete")
			if (st.countTokens() != 2)
			{
				System.out.println("Oops! Expecting task type and response type! Bye!");
				System.exit(0);
			}

			// get task type
			String tmp = st.nextToken();
			if (tmp.toLowerCase().equals("1d"))
				taskType = Throughput.ONE_DIMENSIONAL;
			else if (tmp.toLowerCase().equals("2d"))
				taskType = Throughput.TWO_DIMENSIONAL;
			else
			{
				System.out.println("Oops! Illegal task type (must be \"1D\" or \"2D\")");
				System.exit(0);
			}

			// get response type
			tmp = st.nextToken();
			if (tmp.toLowerCase().equals("serial"))
				responseType = Throughput.SERIAL;
			else if (tmp.toLowerCase().equals("discrete"))
				responseType = Throughput.DISCRETE;
			else
			{
				System.out.println("Oops! Illegal response type (must be \"Serial\" or \"Discrete\"). Bye!");
				System.exit(0);
			}

			// subsequent lines contain the data for the sequence: 7 items (see below) for each
			// trial
			int numberOfTrials = 0;
			ArrayList<Point2D.Double> fromArray = new ArrayList<Point2D.Double>();
			ArrayList<Point2D.Double> toArray = new ArrayList<Point2D.Double>();
			ArrayList<Point2D.Double> selectArray = new ArrayList<Point2D.Double>();
			ArrayList<Double> mtArray = new ArrayList<Double>();

			/*
			 * The number of trials in the sequence is unknown at this point. Each line contains the
			 * data characterising a single trial. Here, we read lines and increment a count until a
			 * blank line is read, indicating the end of the sequence. The count is the number of
			 * trials in the sequence.
			 */
			while ((line = br.readLine()) != null && line.length() > 0)
			{
				++numberOfTrials;
				st = new StringTokenizer(line, ", ");
				if (st.countTokens() != 7)
				{
					System.out.println("Oops! Expecting 7 data values for each trial! Bye!");
					System.exit(0);
				}

				// 2 tokens: the center coordinate of the "from" target
				fromArray
						.add(new Point2D.Double(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken())));

				// 2 tokens: the center coordinate of the "to" target
				toArray.add(new Point2D.Double(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken())));

				// 2 tokens: the selection coordinate ("select")
				selectArray.add(new Point2D.Double(Double.parseDouble(st.nextToken()), Double.parseDouble(st
						.nextToken())));

				// 1 token: the movement time for the trial (in milliseconds)
				mtArray.add(new Double(Double.parseDouble(st.nextToken())));
			}

			// These casts are a bit tricky. See...
			// http://stackoverflow.com/questions/395030/quick-java-question-casting-an-array-of-objects-into-an-array-of-my-intended-cl
			from = (Point2D.Double[])fromArray.toArray(new Point2D.Double[numberOfTrials]);
			to = (Point2D.Double[])toArray.toArray(new Point2D.Double[numberOfTrials]);
			select = (Point2D.Double[])selectArray.toArray(new Point2D.Double[numberOfTrials]);

			// It's a bit messier to create the double array from the ArrayList. Here goes...
			mt = new double[numberOfTrials];
			for (int j = 0; j < mt.length; ++j)
				mt[j] = ((Double)mtArray.get(j)).doubleValue();

			// Give the data to the Throughput object (and do all the necessary calculations)
			tp.setData(code, a, w, taskType, responseType, from, to, select, mt);

			if (tableOption)
				outputTabularResults(tp);
			else if (summaryOption)
			{
				// output a single-line summary of the analysis for a single sequence of trials
				// (-s option)
				System.out.printf("%s,%s,%s,%f,%f,%f,%d,%f,%f,%b,%f,%f,%f,%f,%d,%f\n", tp.getCode(), tp
						.getTaskTypeString(taskType), tp.getResponseTypeString(responseType), tp.getA(), tp.getW(), tp
						.getID(), tp.getNumberOfTrials(), Throughput.getSkewness(tp.getDeltaX()), Throughput
						.getKurtosis(tp.getDeltaX()), Throughput.getIsNormal(tp.getDeltaX()), tp.getAe(), tp.getWe(),
						tp.getIDe(), tp.getMT(), tp.getMisses(), tp.getThroughput());
			}
		} // end of sequence; continue to read data until EOF
		br.close();
	}

	// output the results in tabular form (-t option)
	private static void outputTabularResults(Throughput tpArg)
	{
		// output summary results
		Throughput tp = tpArg;
		Point2D.Double[] from = tp.getFrom();
		Point2D.Double[] to = tp.getTo();
		Point2D.Double[] select = tp.getSelect();
		double[] mt = tp.getMTArray();

		// output table of raw data
		System.out.printf("Code = %s\n", tp.getCode());
		System.out.printf("A = %1.1f, W = %1.1f (ID = %1.2f)\n", tp.getA(), tp.getW(), tp.getID());
		System.out.printf("Task_type = %s, Response_type = %s\n", tp.getTaskTypeString(tp.getTaskType()), tp
				.getResponseTypeString(tp.getResponseType()));
		System.out.printf("Data...\n");
		System.out.printf("============================================================\n");
		System.out.printf("xFrom    yFrom    xTo      yTo      xSelect   ySelect    MT\n");
		System.out.printf("------------------------------------------------------------\n");
		for (int i = 0; i < tp.getNumberOfTrials(); ++i)
			System.out.printf("%5.1f    %5.1f    %5.1f    %5.1f    %5.1f     %5.1f    %5.0f\n", from[i].x, from[i].y,
					to[i].x, to[i].y, select[i].x, select[i].y, mt[i]);
		System.out.printf("============================================================\n\n");

		System.out.printf("Number_of_trials = %d\n", tp.getNumberOfTrials());
		System.out.printf("Select(x'): ");
		double[] deltaX = tp.getDeltaX();
		for (int i = 0; i < deltaX.length; ++i)
			System.out.printf("%1.1f, ", deltaX[i]);
		System.out.printf("\n");
		System.out.printf("-----\n");
		System.out.printf("Mean(x') = %1.2f\n", tp.getX());
		System.out.printf("SD(x') = %1.2f\n", tp.getSDx());
		System.out.printf("Skewness = %1.2f\n", Throughput.getSkewness(tp.getDeltaX()));
		System.out.printf("Kurtosis = %1.2f\n", Throughput.getKurtosis(tp.getDeltaX()));
		System.out.printf("Is_normal? = %b\n", Throughput.getIsNormal(tp.getDeltaX()));
		System.out.printf("-----\n");
		System.out.printf("Misses = %d\n", tp.getMisses());
		System.out.printf("Error_rate = %1.1f%%\n", tp.getErrorRate());
		System.out.printf("-----\n");
		System.out.printf("Ae = %1.1f\n", tp.getAe());
		System.out.printf("We = %1.1f\n", tp.getWe());
		System.out.printf("IDe = %1.2f\n", tp.getIDe());
		System.out.printf("MT = %1.1f\n", tp.getMT());
		System.out.printf("Throughput = %1.2f\n\n", tp.getThroughput());
	}

	private static void usage()
	{
		System.out.println("Usage: java Throughput datafile -t|-s");
		System.out.println();
		System.out.println("   where datafile = file containing data");
		System.out.println("         -t = table output");
		System.out.println("         -s = summary output (1 line per sequence)");
		System.exit(0);
	}
}
