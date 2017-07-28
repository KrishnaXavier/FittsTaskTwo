import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

import java.io.*;

/**
 * <h1>FittsTaskTwo</h1>
 * 
 * <h3>Summary</h3>
 * 
 * <ul>
 * <li>Experiment software to evaluate the performance of computer pointing devices.
 * <p>
 * <li>Implements a two-dimensional Fitts' law task, as per Task #2 in ISO 9241-9 (updated in 2012 as ISO/TC 9241-411).
 * <p>
 * 
 * <li>Performance data are gathered and saved in output files for follow-up analyses.
 * <p>
 * </ul>
 * 
 * <h3>Related References</h3>
 * 
 * The following publications present research using a version of this software for experimental testing of pointing
 * devices or interaction techniques.
 * <p>
 * 
 * <ul>
 * 
 * <li><a href="http://www.yorku.ca/mack/hcii2015b.html">Camera mouse + ClickerAID: Dwell vs. single-muscle click
 * actuation in mouse-replacement interfaces </a>, by Magee, Felzer, and MacKenzie (<i>HCII 2015</i>).
 * <p>
 * 
 * <li><a href="http://ieeexplore.ieee.org/xpl/articleDetails.jsp?tp=&arnumber=6733317">Human-computer interface
 * controlled by the lip</a>, by Jos&eacute; and de Deus Lopes (<i>IEEE Journal of Biomedical and Health Informatics
 * 2015</i>).
 * <p>
 * 
 * <li><a href="http://pro.sagepub.com/content/56/1/521.short">The design, implementation, and evaluation of a pointing
 * device for a wearable computer</a>, by Calvo, Burnett, Finomore, and Perugini (<i>HFES 2012</i>).
 * <p>
 * 
 * <li><a href="http://www.yorku.ca/mack/FuturePlay1.html"> The trackball controller: Improving the analog stick</a>, by
 * Natapov and MacKenzie (<i>FuturePlay 2010</i>).
 * <p>
 * 
 * <li><a href="http://www.yorku.ca/mack/hfes2009.html"> Evaluation of mouse and touch input for a tabletop display
 * using Fitts� reciprocal tapping task</a>, by Sasangohar, MacKenzie, and Scott (<i>HFES 2009</i>).
 * <p>
 * 
 * <li><a href="http://www.yorku.ca/mack/3dui2009.html">Effects of tracking technology, latency, and spatial jitter on
 * object movement</a>, by Teather, Pavlovych, Stuerzling, and MacKenzie (<i>3DUI 2009</i>)
 * <p>
 * 
 * <li><a href="http://www.yorku.ca/mack/eics2009a.html"> An empirical comparison of �Wiimote� gun attachments for
 * pointing tasks</a>, by McArthur, Castellucci, and MacKenzie (<i>EICS 2009</i>).
 * <p>
 * 
 * <li><a href="http://www.yorku.ca/mack/hcii2007.html"> Evaluating eye tracking with ISO 9241 � Part 9</a>, by Zhang
 * and MacKenzie (<i>HCII 2007</i>).
 * <p>
 * 
 * <li><a href="http://www.yorku.ca/mack/CHI01.htm">Accuracy measures for evaluating computer pointing devices</a>, by
 * MacKenzie, Kauppinen, and Silfverberg (<i>CHI 2001</i>).
 * <p>
 * </ul>
 * 
 * The following publications provide background information on Fitts' law and experimental testing using the Fitts'
 * paradigm.
 * <p>
 * 
 * <ul>
 * <li><a href="http://www.yorku.ca/mack/ijhcs2004.pdf">Towards a standard for pointing device evaluation: Perspectives
 * on 27 years of Fitts� law research in HCI</a>, by Soukoreff and MacKenzie (<i>IJHCS 2004</i>).
 * <p>
 * 
 * <li><a href="http://www.yorku.ca/mack/HCI.html">Fitts' law as a research and design tool in human-computer
 * interaction</a>, by MacKenzie (<i>HCI 1992</i>).
 * <p>
 * </ul>
 * 
 * <h3>Running the Experiment Software</h3>
 * <p>
 * 
 * <a href="http://www.yorku.ca/mack/HCIbook/Running/">Click here</a> for instructions on launching/running the
 * application.
 * <p>
 * 
 * <h3>Setup Parameters</h3>
 * 
 * Upon launching, the program presents a setup dialog:
 * <p>
 * <center><a href="FittsTaskTwo-0.jpg"><img src="FittsTaskTwo-0.jpg" width="400"></a></center>
 * <p>
 * </center>
 * 
 * The default parameter settings are read from <a href="FittsTaskTwo.cfg">FittsTaskTwo.cfg</a> but may be changed
 * through the setup dialog for the current invocation. Changes may be saved by clicking the "Save" button (see above).
 * <p>
 * 
 * The setup parameters are as follows:
 * <p>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="6" valign="top">
 * <tr bgcolor="#cccccc">
 * <th>Parameter
 * <th>Description
 * 
 * <tr>
 * <td valign="top">Participant code
 * <td>Identifies the current participant. This is used in forming the names for the output data files. Also, the sd2
 * output data file includes a column with the participant code.
 * <p>
 * 
 * <tr>
 * <td valign="top">Condition code
 * <td>An arbitrary code used to associate a test condition with a block of trials. This parameter might be useful if
 * the software is used in an experiment where a condition is not inherently part of the application. For example, if
 * the software is used to compare a mouse, touchpad, and joystick, the mouse condition could be "C01", the touchpad
 * condition "C02", and the joystick condition "C03". The condition code is used in forming the name for the output data
 * file. Also, the sd2 output data file contains a column with the condition code.
 * <p>
 * 
 * <tr>
 * <td valign="top">Block code
 * <td>Identifies the block of testing. This is used in forming the names for the output data files. Also, the sd2
 * output data file includes a column with the participant code.
 * <p>
 * 
 * <tr>
 * <td valign="top">Number of targets
 * <td>Specifies the number of targets that appear in the layout circle. Because the click on the first target begins
 * the sequence, the trials continue until the first target is selected again.
 * <p>
 * 
 * Only odd numbers are supported. Using an odd number ensures that the distance between successive targets is the same
 * for each trial. This distance is a bit less than the specified amplitude, which is the diameter of the layout circle.
 * <p>
 * 
 * <tr>
 * <td valign="top">Target amplitudes
 * <td>Specifies the diameter of the layout circle (in pixels).
 * <p>
 * 
 * <tr>
 * <td valign="top">Target widths
 * <td>Specifies the diameter of the target circles (in pixels).
 * <p>
 * 
 * <p>
 * Note: The total number of <i>A-W</i> conditions (sequences) in the current block is <i>n</i> &times; <i>m</i>, where
 * <i>n</i> is the number of target amplitudes and <i>m</i> is the number of target widths.
 * <p>
 * 
 * <tr>
 * <td valign="top">Error threshold
 * <td>Specifies an error threshold (%) above which a sequence of trials is deemed an outlier. In this case, the
 * sequence is repeated. A value of 101 effective disables this feature.
 * <p>
 * 
 * Note: Data are not saved for an outlier sequence. However, the sd2 output file includes a data column for the
 * sequence repeat count ("SRC") for each <i>A-W</i> condition.
 * <p>
 * 
 * <tr>
 * <td valign="top">Spatial hysteresis
 * <td>A scaling factor to create a larger virtual target &ndash; a hysteresis zone &ndash; to improve target selection.
 * The mouse pointer is deemed to enter the target when it enters the "real target". The pointer is deemed to exit the
 * target when the pointer exits the hysteresis zone.
 * <p>
 * 
 * With SH = 2.0, for example, the hysteresis zone has 2&times; the diameter of the real target. The default value of
 * 1.0 essentially disables this feature.
 * <p>
 * 
 * The idea of spatial hysteresis has not been tested experimentally. It is simply an idea to improve selection for
 * small targets when there is limited space available between the targets. If anyone is interested in testing this idea
 * experimentally, please let me know (Scott MacKenzie, mack "at" cse.yorku.ca).
 * <p>
 * 
 * <tr>
 * <td valign="top">Randomize target conditions
 * <td>A checkbox item. If checked, a random-without-replacement algorithm is used to select the <i>A-W</i> condition
 * for trial sequences.
 * <p>
 * 
 * <tr>
 * <td valign="top">Beep on error
 * <td>A checkbox item. If checked, outputs a beep if a selection is in error (i.e., outside the target on button-up).
 * <p>
 * 
 * <tr>
 * <td valign="top">Button-down highlight
 * <td>A checkbox item. If checked, uses a different target colour when the mouse button is down (and the cursor is
 * above a target).
 * <p>
 * 
 * <tr>
 * <td valign="top">Mouse-over highlight
 * <td>A checkbox item. If checked, uses a different target colour when the cursor is above a target.
 * <p>
 * 
 * <tr>
 * <td valign="top">Colours
 * <td>Five buttons that launch colour choosers to set the colors for the display.
 * <p>
 * </table>
 * </blockquote>
 * 
 * <h3>Operation</h3>
 * 
 * Testing begins when the user clicks "OK" in the setup dialog. The first condition appears. Below is an example:
 * <p>
 * 
 * <center><a href="FittsTaskTwo-1.jpg"><img src="FittsTaskTwo-1.jpg" width="500"></a></center>
 * <p>
 * </center>
 * 
 * The user begins a sequence of trials by clicking on the start circle (above, red). The highlight moves to a target on
 * the opposite side of the layout circle. Clicking continues in an opposing pattern rotating around the layout circle.
 * A sequence is finished when the start target is again highlighted and selected.
 * <p>
 * 
 * Errors are permitted. The only exception is that the initial click to start a sequence of trials must be inside the
 * start circle.
 * <p>
 * 
 * At the end of a sequence of trials, a popup window appears showing results for the sequence ("Sequence Summary"):
 * <p>
 * <center><a href="FittsTaskTwo-2.jpg"><img src="FittsTaskTwo-2.jpg" width="300"></a></center>
 * <p>
 * 
 * After the last sequence of trials, a popup window appears showing results for the block appears ("Block Summary"):
 * <p>
 * <center><a href="FittsTaskTwo-3.jpg"><img src="FittsTaskTwo-3.jpg" width="400"></a></center>
 * <p>
 * 
 * <h3>Output Data Files</h3>
 * 
 * There are three output data files: sd1, sd2, and sd3. ("sd" is for "summary data".) The data are comma-delimited for
 * easy importing into a spreadsheet or statistics program.
 * <p>
 * 
 * <h4>sd1 Output File</h4>
 * 
 * The sd1 file contains the following summary data for each trial:
 * <p>
 * 
 * <pre>
 *     "FittsTaskTwo" - application identifier
 *     Participant code - from setup dialog
 *     Condition code - from setup dialog
 *     Block code - from setup dialog
*     Trial - trial number
*     A - amplitude (distance to target in pixels)
*     W - width (diameter of target in pixels)
*     Ae - effective amplitude (pixels; see below)
*     dx - delta x (pixels; see below)
*     PT - pointing time (ms)
*     ST - selection time (ms) - the time the button is down
*     MT - movement time (ms) - Note: MT = PT + ST
*     Error - 0 = hit, 1 = miss
*     TRE - target re-entries
*     TAC - task axis crossings
*     MDC - movement direction changes
*     ODC - orthogonal direction changes
*     MV - movement variability
*     ME - movement error
*     MO - movement offset
 * </pre>
 * 
 * The first four entries are settings from the Setup dialog. The next three entries (Trial, A, W) are the task
 * conditions. The next six (Ae, dx, PT, ST, MT, Error) are gross measures of the participant's performance for each
 * trial.
 * <p>
 * 
 * Ae is the effective amplitude &ndash; the actual cursor distance projected on the task axis. Ae < A for undershoots
 * and Ae > A for overshoots.
 * <p>
 * 
 * dx is the delta x of the selection coordinates. It is normalized relative to the center of the target and to the task
 * axis. For example, dx = 1 is the equivalent of a one-pixel overshoot while dx = &minus;1 is the equivalent of a
 * one-pixel undershoot. Note that dx = 0 does not mean selection was precisely at the centre of the target. It means
 * selection was on the line orthogonal to the task axis going through the centre of the target. This is consistent with
 * the inherently one-dimensional nature of Fitts' law.
 * <p>
 * 
 * The last seven measures (TRE, TAC, MDC, ODC, MV, ME, MO) capture accuracy characteristics of the cursor path during a
 * trial. These measures are fully described in <a href="http://www.yorku.ca/mack/CHI01.htm">Accuracy Measures for
 * Evaluating Computer Pointing Devices</a> by MacKenzie, Kauppinen, and Silfverberg (2001). See as well the
 * <a href="AccuracyMeasures.html">API</a> for the <code>AccuracyMeasures</code> class.
 * <p>
 * 
 * <h4>sd2 Output File</h4>
 * 
 * The sd2 file contains summary data for a sequence of trials, specifically
 * 
 * <pre>
 *     "FittsTaskTwo" - application identifier
 *     Participant code - from setup dialog
 *     Condition code - from setup dialog
 *     Block code - from setup dialog
*     SRC - sequence repeat count
*     Trials - number of trials
*     A - target amplitude (pixels)
*     W - target width (diameter) (pixels)
*     ID - index of difficulty (bits)
*     Ae - effective target amplitude (pixels)
*     We - effective target width (pixels)
*     IDe - effective index of difficulty (bits)
*     PT - pointing time (ms)
*     ST - selection time (ms)
*     MT - movement time (ms)
*     ER - error rate (%)
*     TP - throughput (bits/s)
*     TRE - target re-entries
*     TAC - task axis crossings
*     MDC - movement direction changes
*     ODC - orthogonal direction changes
*     MV - movement variability
*     ME - movement error
*     MO - movement offset
 * </pre>
 * 
 * The first four entries are settings from the Setup dialog. The next entry is SRC (sequence repeat count), which is
 * the number of times the sequence was repeated due the error threshold being exceeded. The next four entries (Trials,
 * A, W, ID) are the task conditions.
 * <p>
 * 
 * The remaining entries are measures of participant behaviour, computed over a sequence of trials for the specified A-W
 * condition. (Note: A "sequence" is a series of trials performed one after the other. The number of trials in the
 * sequence is <i>n</i>, where <i>n</i> is the number of targets. All the values, except TP, are means, computed over
 * the trials in the sequence.
 * <p>
 * 
 * TP is the Fitts' law throughput, in bits/s, computed over the sequence. The calculation of TP uses the dx values in
 * the sd1 file (see above). The standard deviation in the dx values for all trials in a sequence is <i>SD</i>
 * <sub>x</sub>. This is used in the calculation of throughput as follows:
 * <p>
 * 
 * <blockquote> <i>W</i><sub>e</sub> = 4.133 &times; <i>SD</i><sub>x</sub>
 * <p>
 * 
 * <i>ID</i><sub>e</sub> = log<sub>2</sub>(<i>A</i><sub>e</sub> / <i>W</i><sub>e</sub> + 1)
 * <p>
 * 
 * <i>TP</i> = <i>ID</i><sub>e</sub> / <i>MT</i>
 * <p>
 * </blockquote>
 * 
 * The actual calculation is performed using the <code>Throughput</code> class. Consult the
 * <a href="Throughput.html">API</a> for complete details.
 * <p>
 * 
 * <h4>sd3 Output File</h4>
 * 
 * The sd3 file contains trace data. For each trial, the on-going timestamps, <I>x</I> coordinates, and <I>y</I>
 * coordinates are collected and saved. A separate utility, <a href="FittsTrace.html">FittsTrace</a>, facilitates
 * viewing the trace data.
 * <p>
 * 
 * The following are examples of "sd" (summary data) files:
 * <p>
 * 
 * <ul>
 * <li><a href="FittsTaskTwo-sd1-example.txt">sd1 example</a>
 * <li><a href="FittsTaskTwo-sd2-example.txt">sd2 example</a>
 * <li><a href="FittsTaskTwo-sd3-example.txt">sd3 example</a>
 * </ul>
 * <p>
 * 
 * Actual output files use "FittsTaskTwo" as the base filename. This is followed by the participant code, the condition
 * code, and the block code, for example, <code>FittsTaskTwo-P15-C01-B01.sd1</code>.
 * <p>
 * 
 * In most cases, the sd2 data files are the primary files used for the data analyses in an experimental evaluation. The
 * data in the sd2 files are full-precision, comma-delimited, to facilitate importing into a spreadsheet or statistics
 * application. Below is an example for the sd2 file above, after importing into Microsoft <i>Excel</i>: (click to
 * enlarge)
 * <p>
 * 
 * <center> <a href="FittsTaskTwo-4.jpg"><img src="FittsTaskTwo-4.jpg" width=1000></a> </center>
 * <p>
 * 
 * <h3>Miscellaneous</h3>
 * 
 * When using this program in an experiment, it is a good idea to terminate all other applications and disable the
 * system's network connection. This will maintain the integrity of the data collected and ensure that the program runs
 * without hesitations.
 * <p>
 * 
 * @author Scott MacKenzie, 2008-2016
 * @author Steven Castellucci, 2014
 */
public class FittsTaskTwo
{        
	public static void main(String[] args) throws IOException
	{       
                System.out.println("main da FittsTaskTwo iniciado");
		if (args.length > 1 || (args.length == 1 && args[0].equals("?")))
			usage();

		// use look and feel for my system (Win32)
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
		}

		// read *default* configuration data from file
		// Fixed to allow command-line argument.
		String filename = args.length > 0 ? args[0] : null;
		FittsTaskTwoConfiguration c = readConfigurationData(filename);

		// use setup to allow changes to default configuration
		FittsTaskTwoSetup s = new FittsTaskTwoSetup(null, c);
		s.showFittsTaskTwoSetup(null);

		// Setup is done. do it!
		FittsTaskTwoFrame frame = new FittsTaskTwoFrame(c);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("FittsTaskTwo");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
	}                

	private static void usage()
	{
		System.out.println("usage: javac FittsTaskTwo [file]\n\n"
				+ "where file = name of file containing configuration arguments\n"
				+ "             (default is 'FittsTaskTwo.cfg')\n");
		System.exit(0);
	}

	private static FittsTaskTwoConfiguration readConfigurationData(String filename)
	{
		String fileName = filename != null ? filename : "FittsTaskTwo.cfg";

		// Added for executable Jar:
		File cfgFile = new File(fileName);
		if (!cfgFile.exists())
		{
			String msg = "The " + fileName + " file was not found. Would you\n"
					+ "like a default configuration file to be created for you?";
			int resp = JOptionPane.showConfirmDialog(null, msg, "Missing Configuration File",
					JOptionPane.YES_NO_OPTION);
			if (resp == JOptionPane.YES_OPTION)
			{
				Scanner in = new Scanner(FittsTaskTwo.class.getResourceAsStream("FittsTaskTwo.cfg"));
				PrintStream out = null;
				try
				{
					out = new PrintStream(cfgFile);
				} catch (IOException e)
				{
					showError("Error opening cfg file: " + fileName);
					System.exit(0);
				}
				while (in.hasNextLine())				
					out.println(in.nextLine());
				
				out.flush();
				out.close();
				in.close();
			}
		}

		Scanner inFile = null;
		try
		{
			inFile = new Scanner(cfgFile);
		} catch (FileNotFoundException e)
		{
			showError("Configuration file not found: " + fileName);
			System.exit(0);
		}

		String participantCode = getNextLineInConfigurationFile(inFile);
		String conditionCode = getNextLineInConfigurationFile(inFile);
		String blockCode = getNextLineInConfigurationFile(inFile);
		int numberOfTargets = Integer.parseInt(getNextLineInConfigurationFile(inFile));
		String targetAmplitudes = getNextLineInConfigurationFile(inFile);
		String targetWidths = getNextLineInConfigurationFile(inFile);
		String randomizeString = getNextLineInConfigurationFile(inFile);
		String beepOnErrorString = getNextLineInConfigurationFile(inFile);
		String buttonDownHighlightString = getNextLineInConfigurationFile(inFile);
		String mouseOverHighlightString = getNextLineInConfigurationFile(inFile);
		int errorThreshold = Integer.parseInt(getNextLineInConfigurationFile(inFile));
		double hysteresis = Double.parseDouble(getNextLineInConfigurationFile(inFile));
		String backgroundColorString = getNextLineInConfigurationFile(inFile);
		String foregroundColorString = getNextLineInConfigurationFile(inFile);
		String targetColorString = getNextLineInConfigurationFile(inFile);
		String buttonDownColorString = getNextLineInConfigurationFile(inFile);
		String mouseOverColorString = getNextLineInConfigurationFile(inFile);

		// some additional processing needed from some of the config args

		String[] args = targetAmplitudes.trim().split("\\s+");
		int[] a = new int[args.length];
		for (int i = 0; i < a.length; ++i)
			a[i] = Integer.parseInt(args[i]);

		args = targetWidths.trim().split("\\s+");
		int[] w = new int[args.length];
		for (int i = 0; i < w.length; ++i)
			w[i] = Integer.parseInt(args[i]);

		boolean randomize = randomizeString.equals("yes") ? true : false;
		boolean beepOnError = beepOnErrorString.equals("yes") ? true : false;
		boolean buttonDownHighlight = buttonDownHighlightString.equals("yes") ? true : false;
		boolean mouseOverHighlight = mouseOverHighlightString.equals("yes") ? true : false;

		args = backgroundColorString.trim().split("\\s+");
		if (args.length != 3)
		{
			showError("Error reading background colour. 3 args needed!");
			System.exit(1);
		}
		Color backgroundColor = new Color(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
				Integer.parseInt(args[2]));

		args = foregroundColorString.trim().split("\\s+");
		if (args.length != 3)
		{
			showError("Error reading foreground colour. 3 args needed!");
			System.exit(1);
		}
		Color foregroundColor = new Color(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
				Integer.parseInt(args[2]));

		args = targetColorString.trim().split("\\s+");
		if (args.length != 3)
		{
			showError("Error reading target colour. 3 args needed!");
			System.exit(1);
		}
		Color targetColor = new Color(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));

		args = buttonDownColorString.trim().split("\\s+");
		if (args.length != 3)
		{
			showError("Error reading button-down colour. 3 args needed!");
			System.exit(1);
		}
		Color buttonDownColor = new Color(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
				Integer.parseInt(args[2]));

		args = mouseOverColorString.trim().split("\\s+");
		if (args.length != 3)
		{
			showError("Error reading mouse-over colour. 3 args needed!");
			System.exit(1);
		}
		Color mouseOverColor = new Color(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
				Integer.parseInt(args[2]));

		// --- finished reading configuration variables ---

		FittsTaskTwoConfiguration c = new FittsTaskTwoConfiguration(participantCode, conditionCode, blockCode,
				numberOfTargets, a, w, randomize, beepOnError, buttonDownHighlight, mouseOverHighlight, errorThreshold,
				hysteresis, backgroundColor, foregroundColor, targetColor, buttonDownColor, mouseOverColor);		
		c.setFilename(fileName);
		return c;
	}

	// get next line with configuration data (skipping over blank lines or lines beginning with "#")
	private static String getNextLineInConfigurationFile(Scanner inFile)
	{
		String s = null;
		while (inFile.hasNextLine())
		{
			s = inFile.nextLine();			
			if (!(s.trim().length() == 0 || s.charAt(0) == '#'))
				break;
		}		
		return s;
	}

	static void showError(String msg)
	{
		JOptionPane.showMessageDialog(null, msg, "I/O Error", JOptionPane.ERROR_MESSAGE);
	}
}

class FittsTaskTwoFrame extends JFrame implements MouseMotionListener, MouseListener, ComponentListener{
	// the following avoids a "warning" with Java 1.5.0 complier (?)
	static final long serialVersionUID = 42L;

	private TaskPanel tp;
	private MessagePanel sequenceResults;
	private MessagePanel blockResults;
	private MessagePanel repeatSequence;
	private BufferedWriter bw1; // writer for sd1 file
	private BufferedWriter bw2; // writer for sd2 file
	private BufferedWriter bw3; // writer for sd3 file
	private int trial;
	private long movementTime;
	private long buttonDown; // button down time
	private long buttonUp; // button up time
	private long tOld;
	private long start;
	private int[] xClick; // log of x clicks (including 1st click)
	private Point[] clickPoint; // log of click point (including 1st click)
	private ArrayList<String> traceData;

	// arguments read from configuration file
	private int numberOfTargets; // number of trials per target condition
	private int numberOfSequences; // number of target conditions
	private int[] a; // target amplitude conditions
	private int[] w; // target width conditions
	private boolean randomize; // randomize target conditions (if 'yes')
	private boolean beepOnError; // beep on error (if 'yes')
	private boolean buttonDownHighlight; // highlight target on button-down
	private boolean mouseOverHighlight; // highlight target on mouse-over
	private double errorThreshold; // error rate threshold (%)
	private double hysteresis; // hysteresis factor for out-of-target detection

	private FittsTaskTwoBlock block;
	private boolean beginTrial;
	private boolean inTarget = false;
	private long[] tSample;
	private Point2D.Double[] xySample;
	private int sIdx;

	FittsTaskTwoConfiguration c;

	public FittsTaskTwoFrame(FittsTaskTwoConfiguration cArg) throws IOException
	{
		c = cArg;
		numberOfTargets = c.getNumberOfTargets();
		a = c.getA();
		w = c.getW();
		randomize = c.getRandomize();
		beepOnError = c.getBeepOnError();
		buttonDownHighlight = c.getButtonDownHighlight();
		mouseOverHighlight = c.getMouseOverHighlight();
		errorThreshold = c.getErrorThreshold();
		hysteresis = c.getHysteresis();

		numberOfSequences = a.length * w.length;

		tSample = new long[1000];
		xySample = new Point2D.Double[1000];

		// fill Point array now. Just set x's and y's later (faster!)
		for (int i = 0; i < 1000; ++i)
			xySample[i] = new Point2D.Double(-1.0, -1.0);

		sIdx = 0;
		beginTrial = false;
		trial = 0;		
		initVariables();

		traceData = new ArrayList<String>();

		block = new FittsTaskTwoBlock(numberOfSequences, numberOfTargets, a, w, randomize);

		// open sd1, sd2, and sd3 files
		String fileName = "FittsTaskTwo-" + c.getParticipantCode() + "-" + c.getConditionCode() + "-"
				+ c.getBlockCode();

		// Check to see if output data files exist. If so, issue an overwrite warning before proceeding.
		JLabel warning = new JLabel("Output data file exists. Overwrite?");
		warning.setFont(new Font("sansserif", Font.PLAIN, 16));
		final Object[] OPTIONS = { "No", "Yes" };
		if ((new File(fileName + ".sd1")).exists() || new File(fileName + ".sd2").exists()
				|| new File(fileName + ".sd3").exists())
		{
			if (JOptionPane.showOptionDialog(this, warning, "Caution", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, OPTIONS, OPTIONS[0]) == 0)
			{
				System.exit(1);
			}
		}

		try
		{
			bw1 = new BufferedWriter(new FileWriter(fileName + ".sd1"));
			bw2 = new BufferedWriter(new FileWriter(fileName + ".sd2"));
			bw3 = new BufferedWriter(new FileWriter(fileName + ".sd3"));
		} catch (IOException e)
		{
			showError("I/O error opening data file");
			System.exit(1);
		}

		// write header lines to data files (flush as well)
		try
		{
			// sd1 header
			String s = "App,Participant,Condition,Block,Trial," + FittsTaskTwoTrial.getTrialHeader() + "\n";
			bw1.write(s, 0, s.length());
			bw1.flush();

			// sd2 header
			s = "App,Participant,Condition,Block," + FittsTaskTwoBlock.getBlockHeader() + "\n";
			bw2.write(s, 0, s.length());
			bw2.flush();

			// sd3 header
			s = "TRACE DATA\n";
			s += "App,Participant,Condition,Block,Sequence,A,W,Trial,from_x,from_y,to_x,to_y,{t_x_y}\n";
			bw3.write(s, 0, s.length());
			bw3.flush();
		} catch (IOException e)
		{
			showError("Error writing header lines to data files");
			System.exit(1);
		}

		// ----------------------------------
		// construct and configure components
		// ----------------------------------

		// create panels for popup windows
		Color back = new Color(255, 255, 215);
		Color fore = new Color(0, 0, 153);
		sequenceResults = new MessagePanel("", 24, back, fore);
		back = new Color(233, 255, 255);
		fore = new Color(127, 32, 13);
		blockResults = new MessagePanel("", 24, back, fore);
		back = new Color(255, 245, 255);
		fore = new Color(153, 0, 0);
		repeatSequence = new MessagePanel("", 42, back, fore);

		// main task panel
		int idxA = block.nextSequenceIndex() / w.length;
		int idxW = block.nextSequenceIndex() % w.length;
		tp = new TaskPanel(a[idxA], w[idxW], 1, numberOfSequences, c.getForegroundColor(), c.getTargetColor(),
				c.getButtonDownColor(), c.getMouseOverColor());
		tp.setBackground(c.getBackgroundColor());

		// -------------
		// add listeners
		// -------------

		tp.addMouseMotionListener(this);
		tp.addMouseListener(this);
		tp.addComponentListener(this);

		// ------------------
		// arrange components
		// ------------------

		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		p1.add(tp, "Center");

		JPanel p = new JPanel(new BorderLayout());
		p.add(p1, "Center");
		p.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setContentPane(p);
	}

	void showError(String msg)
	{
		JOptionPane.showMessageDialog(null, msg, "I/O Error", JOptionPane.ERROR_MESSAGE);
	}

	// init variables that depend on configuration parameters
	private void initVariables()
	{
		xClick = new int[numberOfTargets + 1]; // need one extra for initial click to begin sequence
		clickPoint = new Point[numberOfTargets + 1];
	}

	// -----------------------------------------
	// implement MouseMotionListener methods (2)
	// -----------------------------------------

	public void mouseDragged(MouseEvent me)
	{
		// avoid occasional weird exception if mouse moved before task panel exists
		if (tp == null)
			return;

		// determine if cursor is inside the target
		if (!inTarget && tp.inTarget(me.getX(), me.getY(), 1.0))
			inTarget = true;
		else if (inTarget && !tp.inTarget(me.getX(), me.getY(), hysteresis))
			inTarget = false;

		// button-down highlighting (maybe)
		if (buttonDownHighlight)
			if (inTarget)
				tp.buttonDownHighlightOn();
			else
				tp.buttonDownHighlightOff();

		// mouse-over highlighting (maybe)
		if (mouseOverHighlight && !buttonDownHighlight)
			if (inTarget)
				tp.mouseOverHighlightOn();
			else
				tp.mouseOverHighlightOff();

		if (trial > 0)
		{
			if (beginTrial)
				beginTrial = false;
			tSample[sIdx] = me.getWhen() - buttonUp;
			xySample[sIdx].x = me.getX();
			xySample[sIdx++].y = me.getY();
		}
	}

	public void mouseMoved(MouseEvent me)
	{
		// avoid occasional weird exception if mouse moved before task panel exists
		if (tp == null)
			return;

		// determine if cursor is inside target
		if (!inTarget && tp.inTarget(me.getX(), me.getY(), 1.0))
			inTarget = true;
		else if (inTarget && !tp.inTarget(me.getX(), me.getY(), hysteresis))
			inTarget = false;

		// mouse-over highlighting (maybe)
		if (mouseOverHighlight)
			if (inTarget)
				tp.mouseOverHighlightOn();
			else
				tp.mouseOverHighlightOff();

		if (trial > 0)
		{
			if (beginTrial)
				beginTrial = false;
			tSample[sIdx] = me.getWhen() - buttonUp;
			xySample[sIdx].x = me.getX();
			xySample[sIdx++].y = me.getY();
		}
	}

	// -----------------------------------
	// implement MouseListener methods (5)
	// -----------------------------------

	public void mouseClicked(MouseEvent me)
	{
	}

	public void mouseEntered(MouseEvent me)
	{
	}

	public void mouseExited(MouseEvent me)
	{
	}

	// a mouse button was pressed

	public void mousePressed(MouseEvent me)
	{
		buttonDown = me.getWhen();
		beginTrial = true;

		// determine if cursor is inside target
		if (!inTarget && tp.inTarget(me.getX(), me.getY(), 1.0))
			inTarget = true;
		else if (inTarget && !tp.inTarget(me.getX(), me.getY(), hysteresis))
			inTarget = false;

		// button-down highlighting (maybe)
		if (buttonDownHighlight)
			if (inTarget)
				tp.buttonDownHighlightOn();
			else
				tp.buttonDownHighlightOff();

		if (trial > 0)
		{
			tSample[sIdx] = me.getWhen() - buttonUp;
			xySample[sIdx].x = me.getX();
			xySample[sIdx++].y = me.getY();
		}
	}

	// ===========================
	// M O U S E _ R E L E A S E D
	// ===========================

	public void mouseReleased(MouseEvent me)
	{
		int x = me.getX();
		int y = me.getY();

		// determine if cursor is inside target
		if (!inTarget && tp.inTarget(me.getX(), me.getY(), 1.0))
			inTarget = true;
		else if (inTarget && !tp.inTarget(me.getX(), me.getY(), hysteresis))
			inTarget = false;

		// do not allow a sequence to begin unless the first selection is inside the start target
		if (trial == 0 && !inTarget)
			return;

		// at beginning of 1st trial, set buttonUp now
		if (trial == 0)
			buttonUp = me.getWhen();

		// last trace sample (will be overwritten for first sample of first trial)
		tSample[sIdx] = me.getWhen() - buttonUp; // old button up
		xySample[sIdx].x = me.getX();
		xySample[sIdx++].y = me.getY();

		buttonUp = me.getWhen(); // new button up

		xClick[trial] = x;
		clickPoint[trial] = new Point(x, y);
		String s = "";

		if (trial == 0) // beginning of a sequence of n trials
		{
			start = buttonUp;
			sIdx = 0;
		}
		tOld = movementTime;
		movementTime = buttonUp - start;

		int error = inTarget ? 0 : 1;
		if (error == 1 && beepOnError)
			this.getToolkit().beep();

		if (trial > 0) // process trial (first click doesn't count)
		{
			FittsTaskTwoTrial t = block.getSequence(block.getIDX()).getTrial(trial - 1);
			t.setXFrom(tp.centerPoint[tp.targetOrder[trial - 1]].x);
			t.setYFrom(tp.centerPoint[tp.targetOrder[trial - 1]].y);
			t.setXTo(tp.centerPoint[tp.targetOrder[trial]].x);
			t.setYTo(tp.centerPoint[tp.targetOrder[trial]].y);
			t.setXSelect(xySample[sIdx - 1].x);
			t.setYSelect(xySample[sIdx - 1].y);

			// set the Ae and deltaX for the trial (let the Throughput class do the work)
			t.setAe(Throughput.getTrialAe(new Point2D.Double(t.xFrom, t.yFrom), new Point2D.Double(t.xTo, t.yTo),
					new Point2D.Double(t.xSelect, t.ySelect)));
			t.setDx(Throughput.getTrialDeltaX(new Point2D.Double(t.xFrom, t.yFrom), new Point2D.Double(t.xTo, t.yTo),
					new Point2D.Double(t.xSelect, t.ySelect)));

			t.setPt(movementTime - tOld - (buttonUp - buttonDown));
			t.setSt(buttonUp - buttonDown);
			t.setMt(movementTime - tOld);
			t.setErr(error);

			// trace-related data for sd3 file (store in Trial object)
			t.setTraceTimestamps(tSample, sIdx);
			t.setTracePoints(xySample, sIdx);

			// collect trace data at end of trial
			String leadin = "FittsTaskTwo" + "," + c.getParticipantCode() + "," + c.getConditionCode() + ","
					+ c.getBlockCode() + "," + tp.getCurrentSequence() + "," + Math.round(t.amplitude) + ","
					+ Math.round(t.width) + "," + trial + "," + Math.round(t.xFrom) + "," + Math.round(t.yFrom) + ","
					+ Math.round(t.xTo) + "," + Math.round(t.yTo) + ",";
			String s1 = leadin;
			s1 += "t=,";
			for (int i = 0; i < sIdx; ++i)
				s1 += tSample[i] + ",";
			s1 += "\n";
			s1 += leadin;
			s1 += "x=,";
			for (int i = 0; i < sIdx; ++i)
				s1 += Math.round(xySample[i].x) + ",";
			s1 += "\n";
			s1 += leadin;
			s1 += "y=,";
			for (int i = 0; i < sIdx; ++i)
				s1 += Math.round(xySample[i].y) + ",";
			s1 += "\n";
			traceData.add(s1);

			// 1st sample of next trial begins at same x,y but with t = 0
			sIdx = 0; // start again
			tSample[sIdx] = 0;
			xySample[sIdx++] = new Point2D.Double(x, y);
		}

		// prepare for next trial

		if (trial < numberOfTargets) // not end of sequence
		{
			tp.advanceActiveTarget(trial);
			++trial;
		} else
		// end of sequence, see if it needs to be repeated
		{
			block.getSequence(block.getIDX()).computeSequenceSummaryStats();
			double errorRate = block.getSequence(block.getIDX()).getER();

			if (errorRate > errorThreshold) // sequence must be repeated
			{
				// ++sequenceRepeatCount;
				block.getSequence(block.getIDX()).incrementSequenceRepeatCount();
				s = block.getSequence(block.getIDX()).getRepeatSequence();
				repeatSequence.setText(s);
				JOptionPane.showMessageDialog(this, repeatSequence, "Repeat Sequence", JOptionPane.INFORMATION_MESSAGE);

				traceData.clear();

				trial = 0;
				int idxA = block.nextSequenceIndex() / w.length;
				int idxW = block.nextSequenceIndex() % w.length;
				tp.setNewAW(a[idxA], w[idxW], block.getIDX() + 1);
				tp.resetActiveTarget();
				tp.repaint();
				return;
			}
			// fs.getSequence(fs.nextSequenceIndex()).setSequenceRepeatCount(sequenceRepeatCount);

			// write trial data and trace data at end of sequence
			for (int i = 0; i < block.getSequence(block.getIDX()).getTrials(); ++i)
			{
				// write trial data to sd1 file
				StringBuilder sb = new StringBuilder();
				sb.append("FittsTaskTwo,");
				sb.append(c.getParticipantCode()).append(',');
				sb.append(c.getConditionCode()).append(',');
				sb.append(c.getBlockCode()).append(',');
				sb.append(block.getSequence(block.getIDX()).getTrialData(i)).append('\n');
				try
				{
					bw1.write(sb.toString(), 0, sb.length());
					bw1.flush();
				} catch (IOException e)
				{
					showError("I/O error writing to sd1 file");
					System.exit(1);
				}

				// write trace data to sd3 file
				Iterator<String> it;
				it = traceData.iterator();
				while (it.hasNext())
				{
					final String tmp = it.next();
					try
					{
						bw3.write(tmp, 0, tmp.length());
						bw3.flush();
					} catch (IOException e)
					{
						showError("I/O error writing to sd3 file");
						System.exit(1);
					}
				}
				traceData.clear();
			}

			// present sequence results in popup window
			sequenceResults.setText(block.getSequence(block.getIDX()).getSequenceSummary());
			JOptionPane.showMessageDialog(this, sequenceResults, "Sequence summary", JOptionPane.INFORMATION_MESSAGE);

			if (block.lastSequence()) // end of last sequence (done!)
			{
				block.buildArrays();
				for (int i = 0; i < block.getSequences(); ++i)
				{
					s = "FittsTaskTwo" + "," + c.getParticipantCode() + "," + c.getConditionCode() + ","
							+ c.getBlockCode() + "," + block.getSequenceData(i) + "\n";
					try
					{
						bw2.write(s, 0, s.length());
					} catch (IOException e)
					{
						showError("I/O error writing data to sd2 file");
						System.exit(1);
					}
				}
				try
				{
					bw1.close();
					bw2.close();
				} catch (IOException e)
				{
					showError("Error closing data files");
					System.exit(1);
				}

				// present summary block results in popup window
				blockResults.setText(block.getBlockSummary());
				JOptionPane.showMessageDialog(this, blockResults, "Block summary", JOptionPane.INFORMATION_MESSAGE);

				System.exit(0); // we're done!
			} else
			// prepare for the next sequence
			{
				beginTrial = true;
				trial = 0;
				block.nextSequence();
				int idxA = block.nextSequenceIndex() / w.length;
				int idxW = block.nextSequenceIndex() % w.length;
				tp.setNewAW(a[idxA], w[idxW], block.getIDX() + 1);
				tp.resetActiveTarget();
				tp.repaint();
			}
		}
	} // end mouseReleased

	// ---------------------------------------
	// implement ComponentListener methods (4)
	// ---------------------------------------

	public void componentHidden(ComponentEvent e)
	{
	}

	public void componentMoved(ComponentEvent e)
	{
	}

	public void componentResized(ComponentEvent e)
	{
		if (tp.getWidth() > 0)
		{
			tp.configure();
			tp.repaint();
		}
	}

	public void componentShown(ComponentEvent e)
	{
	}

	// --------------------
	// define inner classes
	// --------------------

	class MessagePanel extends JPanel
	{
		// the following avoids a "warning" with Java 1.5.0 complier (?)
		static final long serialVersionUID = 42L;

		private String text;
		private Font f;
		private FontMetrics fm;
		private int width, height;

		MessagePanel(String sArg, int fontSize, Color backArg, Color foreArg)
		{
			f = new Font("Monospaced", Font.BOLD, fontSize);
			this.setFont(f);
			fm = this.getFontMetrics(f);
			this.setText(sArg);
			this.setForeground(foreArg);
			this.setBackground(backArg);
			this.setBorder(BorderFactory.createLineBorder(Color.gray));
			this.setPanelSize();
		}

		private void setPanelSize()
		{
			StringTokenizer st = new StringTokenizer(text, "\n");

			// height is a simple function of the number of lines
			height = fm.getHeight() * st.countTokens();
			height += 20; // extra space above and below

			// for width, find the widest line
			width = 0;
			while (st.hasMoreTokens())
			{
				int tmp = fm.stringWidth(st.nextToken());
				width = width < tmp ? tmp : width;
			}
			width += 30; // extra space, left and right
			this.setPreferredSize(new Dimension(width, height));
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;

			FontMetrics fm = g2.getFontMetrics();
			int h = fm.getHeight(); // pixel height of characters (for given font)

			StringTokenizer st = new StringTokenizer(text, "\n");
			int count = st.countTokens();

			for (int i = 0; i < count; ++i)
				g2.drawString(st.nextToken(), 10, h + (i * h));
		}

		public void setText(String textArg)
		{
			text = textArg;
			setPanelSize();
		}
	}

	// ***********************************
	// A class for presenting the targets
	// ***********************************
	class TaskPanel extends JPanel
	{
		// the following avoids a "warning" with Java 1.5.0 complier (?)
		static final long serialVersionUID = 42L;

		private int a;
		private int w;
		private int currentSequence;
		private int totalSequences;
		private String progress1, progress2;
		private Ellipse2D.Double[] target;
		int activeTarget = -1;
		public Point[] centerPoint;
		public int[] targetOrder;
		Color foregroundColor;
		Color targetColor;
		Color buttonDownColor;
		Color mouseOverColor;

		TaskPanel(int aArg, int wArg, int cbArg, int tbArg, Color foregroundColorArg, Color targetColorArg,
				Color buttonDownColorArg, Color mouseOverColorArg)
		{
			foregroundColor = foregroundColorArg;
			targetColor = targetColorArg;
			buttonDownColor = buttonDownColorArg;
			mouseOverColor = mouseOverColorArg;
			target = new Ellipse2D.Double[numberOfTargets];
			for (int i = 0; i < target.length; ++i)
				target[i] = new Ellipse2D.Double(0, 0, 0, 0);
			centerPoint = new Point[numberOfTargets];
			currentSequence = cbArg;
			totalSequences = tbArg;
			setNewAW(aArg, wArg, currentSequence);
			this.setBackground(Color.WHITE);
			this.setBorder(BorderFactory.createLineBorder(Color.gray));

			/*
			 * Build an array of indices of the order of highlighted targets. The algorithm is slightly different if the
			 * number of targets is odd. An advantage of using an odd number of targets is that the movement amplitude
			 * is exactly the same for all trials. If the number of targets is even, the movement amplitude is different
			 * from one trial to the next. "+1" is used because the first selection is used just to start the sequence.
			 */
			targetOrder = new int[numberOfTargets + 1];
			targetOrder[0] = numberOfTargets / 4 * 3; // 1st target is at the top (3/4 around circle)

			int inc = numberOfTargets / 2 + numberOfTargets % 2;
			if (numberOfTargets % 2 == 0) // even number of targets
				for (int i = 1; i < numberOfTargets + 1; ++i)
					targetOrder[i] = (targetOrder[i - 1] + (numberOfTargets + 1) / 2 + ((i + 1) % 2)) % numberOfTargets;
			else
				// odd number of targets
				for (int i = 1; i < numberOfTargets + 1; ++i)
					targetOrder[i] = (inc + targetOrder[i - 1]) % numberOfTargets;

			resetActiveTarget();
		}

		private void setNewAW(int aArg, int wArg, int cbArg)
		{
			a = aArg;
			w = wArg;
			currentSequence = cbArg;
			progress1 = "Sequence " + currentSequence + " of " + totalSequences;
			progress2 = "(A = " + a + ", W = " + w + ")";
			configure();
		}

		private void configure()
		{
			double centreX = this.getWidth() / 2;
			double centreY = this.getHeight() / 2;
			for (int i = 0; i < numberOfTargets; ++i)
			{
				double x = centreX + (a / 2) * Math.cos(2 * Math.PI * ((double)i / numberOfTargets));
				double y = centreY + (a / 2) * Math.sin(2 * Math.PI * ((double)i / numberOfTargets));
				centerPoint[i] = new Point((int)Math.round(x), (int)Math.round(y));
				target[i].x = x - w / 2;
				target[i].y = y - w / 2;
				target[i].width = w;
				target[i].height = w;
			}
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			paintTargets(g);
		}

		public boolean inTarget(double x, double y, double hysteresisFactor)
		{
			// kludge to avoid exception if mouse-over occurs before targets exist
			if (target == null || activeTarget == -1)
				return false;

			double xx = target[activeTarget].x;
			double yy = target[activeTarget].y;
			double w = target[activeTarget].width;
			double h = target[activeTarget].height;
			w *= hysteresisFactor;
			h *= hysteresisFactor;
			xx = xx - 0.5 * (w - target[activeTarget].width);
			yy = yy - 0.5 * (h - target[activeTarget].height);
			Ellipse2D.Double virtualTarget = new Ellipse2D.Double(xx, yy, w, h);

			return virtualTarget.contains(x, y);
		}

		public void clear()
		{
			this.repaint();
		}

		public void advanceActiveTarget(int trial)
		{
			activeTarget = targetOrder[trial + 1];
			this.repaint();
		}

		public void resetActiveTarget()
		{
			activeTarget = targetOrder[0]; // first target is at top (3/4 around circle)
		}

		private void paintTargets(Graphics g)
		{
			final Stroke INK_STROKE = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			final Color MESSAGE_COLOR = new Color(127, 0, 0);
			final Font MESSAGE_FONT = new Font("SannsSerif", Font.ITALIC, 24);

			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(foregroundColor);
			g2.setStroke(INK_STROKE); // set desired stroke
			for (int i = 0; i < numberOfTargets; ++i)
				g2.draw(target[i]);

			g2.setColor(targetColor);
			g2.fill(target[activeTarget]);

			// output progress message
			g2.setColor(MESSAGE_COLOR);
			g2.setFont(MESSAGE_FONT);
			int height = g2.getFontMetrics().getHeight(); // of characters (for given font)
			g2.drawString(progress1, 10, 10 + height);
			g2.drawString(progress2, 10, 10 + 2 * height);
		}

		public void buttonDownHighlightOn()
		{
			Graphics2D g2 = (Graphics2D)this.getGraphics();
			g2.setColor(buttonDownColor);
			g2.fill(target[activeTarget]);
		}

		public void buttonDownHighlightOff()
		{
			Graphics2D g2 = (Graphics2D)this.getGraphics();
			g2.setColor(targetColor);
			g2.fill(target[activeTarget]);
		}

		public void mouseOverHighlightOn()
		{
			Graphics2D g2 = (Graphics2D)this.getGraphics();
			g2.setColor(mouseOverColor);
			g2.fill(target[activeTarget]);
		}

		public void mouseOverHighlightOff()
		{
			Graphics2D g2 = (Graphics2D)this.getGraphics();
			g2.setColor(targetColor);
			g2.fill(target[activeTarget]);
		}

		public int getCurrentSequence()
		{
			return currentSequence;
		}
	}
}
