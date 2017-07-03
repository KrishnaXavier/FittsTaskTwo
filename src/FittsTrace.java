import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

/**
 * <h1>FittsTrace</h1>
 * 
 * <h3>Summary</h3>
 * 
 * <ul>
 * <li>Utility software to plot the trace data in the sd3 files created by FittsTaskTwo or FittsTaskOne.
 * </ul>
 * <p>
 * 
 * <h3>Related References</h3>
 * 
 * The following publications include figures showing trace data that were gathered using a version of this software:
 * <p>
 * 
 * <ul>
 * 
 * <li><a href="http://www.yorku.ca/mack/icchp2016b.html"> Comparison of two methods to control the mouse using a keypad</a>,
 * by Felzer, MacKenzie, and Magee (<i>ICCHP 2016</i> 
 * (<a href="http://www.yorku.ca/mack/icchp2016b.html#Figure_6">Figure 6</a>).
 * <p>
 * 
 * 
 * <li><a href="http://www.yorku.ca/mack/hcii2015b.html">Camera mouse + ClickerAID: Dwell vs. single-muscle click
 * actuation in mouse-replacement interfaces </a>, by Magee, Felzer, and MacKenzie (<i>HCII 2015</i>) 
 * (<a href="http://www.yorku.ca/mack/hcii2015b.html#Figure_9">Figure 9</a>,
 * <a href="http://www.yorku.ca/mack/hcii2015b.html#Figure_10">Figure 10</a>).
 * <p>
 * 
 * <li><a href="http://www.yorku.ca/mack/gi2014.html">Position vs. velocity control for tilt-based interaction</a>, by
 * Teather and MacKenzie (<i>GI 2014</i>) 
 * (<a href="http://www.yorku.ca/mack/gi2014.html#Figure_13">Figure 13</a>)
 * <p>
 * 
 * <li><a href="http://www.yorku.ca/mack/nordichi2012.html">FittsTilt: The application of Fitts� law to tilt-based
 * interaction</a>, by MacKenzie and Teather (<i>NordiCHI 2012</i>) 
 * (<a href="http://www.yorku.ca/mack/nordichi2012.html#Figure_9">Figure 9</a>, 
 * <a href="http://www.yorku.ca/mack/nordichi2012.html#Figure_11">Figure 11</a>)
 * <p>
 * 
 * <li><a href="http://www.yorku.ca/mack/cogain.html"> Evaluating eye tracking systems for computer input</a>, by
 * MacKenzie (in Majaranta, P., Aoki, H., Donegan, M., Hansen, D. W., Hansen, J. P., Hyrskykari, A., & R
 * �ih�, K.-J.
 * (Eds.). (2012). <i>Gaze interaction and applications of eye tracking: Advances in assistive technologies</i>, pp.
 * 205-225. Hershey, PA: IGI Global.) 
 * (<a href="http://www.yorku.ca/mack/cogain.html#Figure_15">Figure 15</a>)
 * <p>
 * 
 * <li><a href="http://www.yorku.ca/mack/FuturePlay1.html">The trackball controller: Improving the analog stick</a>, by
 * Natapov and MacKenzie ( <i>FuturePlay 2010</i>) 
 * (<a href="http://www.yorku.ca/mack/FuturePlay1.html#Figure_10">Figure 10</a>, 
 * <a href="http://www.yorku.ca/mack/FuturePlay1.html#Figure_11">Figure 11</a>)
 * </ul>
 * 
 * <h3>Input Data Format</h3>
 * 
 * The sd3 data processed by this application must be in a specific format. The first two lines are for information only
 * and are discarded. In the example sd3 file (see below), the first line is a title and the second line contains
 * comma-delimited labels identifying the rest of the data:
 * <p>
 * 
 * <pre>
 *     application name
 *     participant code
 *     condition code
 *     block code
 *     sequence - sequence number
 *     A - target amplitude (diameter of the layout circle in pixels)
 *     W - target width (diameter of target circle in pixels)
 *     trial - trial number within the sequence
 *     from_x - x coordinate of beginning of trial
 *     from_y - y coordinate of beginning of trial
 *     to_x - centre x coordinate of target circle
 *     to_y - centre y coordinate of target circle
 *     identifier ("t=", "x=", or "y=")
 * </pre>
 * 
 * The trace data begin after the first two lines just noted. Each trace is encoded in three consecutive lines: (i) the
 * timestamps, (ii) the <i>x</i> samples, and (iii) the <i>y</i> samples. <a href="FittsTaskTwo-sd3-example.txt">Click
 * here</a> for an example of an sd3 data file.
 * <p>
 * 
 * <h3>Running the Experiment Software</h3>
 * <p>
 * 
 * <a href="http://www.yorku.ca/mack/HCIbook/Running/">Click here</a> for instructions on launching/running the
 * application.
 * <p>
 * 
 * <h3>Program Operation</h3>
 * <p>
 * 
 * Upon launching, a file open dialog appears to choose an sd3 file:
 * <p>
 * 
 * 
 * <center><a href="FittsTrace-0.jpg"><img src="FittsTrace-0.jpg" width="500" alt="image"></a></center>
 * <p>
 * 
 * To use the application, navigate to a folder containing sd3 files and select an sd3 file to open.
 * <p>
 * 
 * <h3>Example Traces</h3>
 * 
 * The following screen snap shows the trace data from the example sd3 file:
 * <p>
 * <center><a href="FittsTrace-1.jpg"><img src="FittsTrace-1.jpg" width="500" alt="image"></a></center>
 * <p>
 * 
 * As well as identifying the test conditions, the UI displays the performance data associated with the traces,
 * including the mean movement time, the error rate, and the mean of the accuracy measures. (The latter are described in
 * <a href="http://www.yorku.ca/mack/CHI01.htm">Accuracy Measures for Evaluating Computer Pointing Devices</a> by
 * MacKenzie, Kauppinen, and Silfverberg (2001); see as well the <a href="AccuracyMeasures.html">API</a> for the
 * <code>AccuracyMeasures</code> class.)
 * <p>
 * 
 * A checkbox entry along the bottom of the UI allows the trace data to be inspected for individual trials:
 * <p>
 * <center><a href="FittsTrace-2.jpg"><img src="FittsTrace-2.jpg" width="500" alt="image"></a></center>
 * <p>
 * 
 * In trial mode, the performance data correspond to individual trials, rather than a sequence of trials.
 * <p>
 * 
 * There are additional checkbox entries to adjust the scale of the display and to control whether the output shows the
 * traces (default), the sample points, or both the traces and the sample points (see above).
 * <p>
 * 
 * @author Scott MacKenzie, 2009-2016
 */
public class FittsTrace
{
	public static void main(String[] args)
	{
		if (args.length > 0)
		{
			System.out.println("Usage: FittsTrace (see API for details)");
			System.exit(0);
		}

		// use look and feel for my system (Win32)
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
		}

		FittsTraceFrame frame = new FittsTraceFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("FittsTrace");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
	}
}

class FittsTraceFrame extends JFrame implements ActionListener, KeyListener, ItemListener
{
	// the following avoids a "warning" with Java 1.5.0 complier (?)
	static final long serialVersionUID = 42L;

	static final int MODE_1D = 100;
	static final int MODE_2D = 200;

	final String[] SCALE_OPTIONS =
	{ "0.4", "0.6", "0.8", "1.0", "1.2", "1.4", "1.6" };
	final int DEFAULT_INDEX = 3; // "1.0" (adjust as necessary)
	final Font F16 = new Font("sansserif", Font.PLAIN, 16);

	static int sequenceCounter = 1;
	static int trialCounter = 1;

	private TracePanel tp;
	private JTextField sd3File;
	private JButton browse;
	private JButton next;
	private JButton previous;
	private JButton exit;
	private JCheckBox showPoints;
	private JCheckBox showTraces;
	private JComboBox<String> scaleCombo;
	private JLabel scaleLabel;
	private JCheckBox trialByTrial;
	private JFileChooser fc;
	private File f;

	private double scale = 1.0;
	private boolean traces = true;
	private boolean points = false;
	private boolean trialMode = false;

	private int mode;

	public FittsTraceFrame()
	{
		// ----------------------------------
		// construct and configure components
		// ----------------------------------

		fc = new JFileChooser(new File("."));
		final String[] EXTENSIONS =
		{ ".sd3" };
		fc.addChoosableFileFilter(new MyFileFilter(EXTENSIONS));

		File sd3FileToOpen = null;
		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			sd3FileToOpen = fc.getSelectedFile();
		else
		{
			System.out.println("No sd3 file selected!  Exiting...");
			System.exit(0);
		}

		tp = new TracePanel(sd3FileToOpen.getPath());
		tp.setBorder(BorderFactory.createLineBorder(Color.gray));

		previous = new JButton("Previous");
		next = new JButton("Next");
		exit = new JButton("Exit");
		showTraces = new JCheckBox("Show traces");
		showPoints = new JCheckBox("Show points");
		trialByTrial = new JCheckBox("Trial Mode");
		showTraces.setSelected(true);
		showPoints.setSelected(false);
		trialByTrial.setSelected(false);

		scaleLabel = new JLabel("Scale: ");
		scaleLabel.setFont(F16);
		scaleCombo = new JComboBox<String>(SCALE_OPTIONS);
		scaleCombo.setSelectedIndex(DEFAULT_INDEX);
		scaleCombo.setMaximumSize(new Dimension(60, 25));

		scale = Double.parseDouble((String) scaleCombo.getSelectedItem());

		browse = new JButton("Browse...");
		previous.setFont(F16);
		next.setFont(F16);
		exit.setFont(F16);
		browse.setFont(F16);
		showTraces.setFont(F16);
		showPoints.setFont(F16);
		trialByTrial.setFont(F16);
		scaleCombo.setFont(F16);

		sd3File = new JTextField();
		Dimension d2 = browse.getPreferredSize();
		sd3File.setMaximumSize(new Dimension(400, d2.height));
		sd3File.setFont(F16);
		sd3File.setText(sd3FileToOpen.getName());

		// -------------
		// add listeners
		// -------------

		next.addActionListener(this);
		previous.addActionListener(this);
		exit.addActionListener(this);
		browse.addActionListener(this);
		sd3File.addActionListener(this);
		this.addKeyListener(this);
		showTraces.addItemListener(this);
		showPoints.addItemListener(this);
		trialByTrial.addItemListener(this);
		scaleCombo.addActionListener(this);

		next.requestFocus();

		// ------------------
		// arrange components
		// ------------------

		JPanel checkPanel = new JPanel();
		checkPanel.setLayout(new GridLayout(2, 1));
		checkPanel.add(showTraces);
		checkPanel.add(showPoints);

		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(scaleLabel);
		p1.add(scaleCombo);
		p1.add(Box.createRigidArea(new Dimension(5, 0)));
		p1.add(showTraces);
		p1.add(showPoints);
		p1.add(trialByTrial);
		p1.add(Box.createRigidArea(new Dimension(50, 0)));
		p1.add(sd3File);
		p1.add(Box.createRigidArea(new Dimension(5, 0)));
		p1.add(browse);
		p1.add(Box.createRigidArea(new Dimension(50, 0)));
		p1.add(previous);
		p1.add(Box.createRigidArea(new Dimension(10, 0)));
		p1.add(next);
		p1.add(Box.createRigidArea(new Dimension(10, 0)));
		p1.add(exit);
		p1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(tp);
		p.add(p1);
		p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		p.setPreferredSize(new Dimension(d.width, d.height - 45));

		this.pack();
		this.setContentPane(p);
		next.requestFocus();
	}

	// -------------------------------
	// implement ItemListener method
	// -------------------------------
	public void itemStateChanged(ItemEvent ie)
	{
		Object source = ie.getSource();
		if (source == showTraces)
			traces = !traces;
		else if (source == showPoints)
			points = !points;
		else if (source == trialByTrial)
		{
			trialMode = !trialMode;
			trialCounter = 1; // incremented to 1 before 1st draw
		}
		this.repaint();
	}

	// -------------------------------
	// implement ActionListener method
	// -------------------------------

	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();

		if (source == next)
			tp.next();

		else if (source == previous)
			tp.previous();

		else if (source == exit)
			System.exit(0);

		else if (source == browse)
		{
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				f = fc.getSelectedFile();
				tp.loadData(f.getPath());
				sd3File.setText(f.getName());
				// tp.makesd4();
				tp.repaint();

			}
		} else if (source == sd3File)
		{
			tp.loadData(sd3File.getText());
			tp.repaint();

		} else if (source == scaleCombo)
		{
			scale = Double.parseDouble((String) scaleCombo.getSelectedItem());
			tp.repaint();
		}
	}

	public void keyPressed(KeyEvent ke)
	{
	} // do nothing

	public void keyReleased(KeyEvent ke)
	{
	} // do nothing

	public void keyTyped(KeyEvent ke)
	{
		System.out.println(ke.getKeyChar());
	}

	// -----------------------------------------
	// A class to display the targets and traces
	// -----------------------------------------

	class TracePanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		Trial[] t;
		String fileName;
		String participantCode, conditionCode, blockCode;

		// offsets for scaling and shifting the draw objects
		double offsetX, offsetY;

		TracePanel(String fileArg)
		{
			this.setBackground(Color.white);
			loadData(fileArg);
		}

		public void computeOffsets()
		{
			if (t.length == 0)
				return;

			// This doesn't seem to work quite as hoped. The drawing objects are still
			// a little off from center. Works OK, but perhaps try to improve this later.
			double centerLayoutX = (scale * (t[0].getX1() + t[0].getX2())) / 2.0;
			double centerLayoutY = (scale * (t[0].getY1() + t[0].getY2())) / 2.0;

			double centerPanelX = this.getWidth() / 2.0;
			double centerPanelY = this.getHeight() / 2.0;

			offsetX = centerPanelX - centerLayoutX;
			offsetY = centerPanelY - centerLayoutY;
		}

		public void drawTargets(Graphics g)
		{
			if (t == null || t.length == 0)
				return;

			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.black);

			// w is the same for the entire sequence (calculate once)
			double w = scale * t[(sequenceCounter - 1) * trialsPerSequence()].w;
			double h = 0.6 * this.getHeight();

			for (int i = 0; i < t.length; ++i)
			{
				if (sequenceCounter == t[i].getSequence())
				{
					if (t[i].getTrialNumber() == 1) // "from" target in 1st trial
					{
						if (mode == MODE_1D) // The trace data are from FittsTaskOne (rectangular targets)
						{
							double x = offsetX + scale * t[i].getX1() - (w / 2.0);
							double y = offsetY + scale * t[i].getY1() - (h / 2.0);
							Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
							g2.draw(rect);

						} else // 2D: circular targets
						{
							double x = offsetX + scale * t[i].getX1() - (w / 2.0);
							double y = offsetY + scale * t[i].getY1() - (w / 2.0);
							Ellipse2D.Double e = new Ellipse2D.Double(x, y, w, w);
							g2.draw(e);
						}
					}
					if (mode == MODE_1D) // The trace data are from FittsTaskOne (rectangular targets)
					{
						double x = offsetX + scale * t[i].getX2() - (w / 2.0);
						double y = offsetY + scale * t[i].getY2() - (h / 2.0);
						Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
						g2.draw(rect);

					} else // circular targets
					{
						double x = offsetX + scale * t[i].getX2() - (w / 2.0);
						double y = offsetY + scale * t[i].getY2() - (w / 2.0);
						Ellipse2D.Double e = new Ellipse2D.Double(x, y, w, w);
						g2.draw(e);
					}
				}

				// in trial mode, draw target to select last (to occlude overlapping targets, if any)
				if (sequenceCounter == t[i].getSequence())
				{
					if (trialMode && trialCounter == t[i].getTrialNumber())
					{						
						//g2.setStroke(new BasicStroke(2));
						
						if (mode == MODE_1D) // The trace data are from FittsTaskOne (rectangular targets)
						{
							double x = offsetX + scale * t[i].getX2() - (w / 2.0);
							double y = offsetY + scale * t[i].getY2() - (h / 2.0);

							Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
							g2.setColor(Color.red);
							g2.fill(rect);
							g2.setStroke(new BasicStroke(2));
							g2.setColor(Color.black);
							g2.draw(rect);

						} else // 2D: circular targets
						{
							double x = offsetX + scale * t[i].getX2() - (w / 2.0);
							double y = offsetY + scale * t[i].getY2() - (w / 2.0);
							Ellipse2D.Double e = new Ellipse2D.Double(x, y, w, w);
							g2.setColor(Color.red);
							g2.fill(e); // fill the target to select 
							g2.setColor(Color.black);
							g2.draw(e);
						}
					}
				}
			}
		}

		public void drawTraces(Graphics g)
		{
			if (t == null || t.length == 0)
				return;

			for (int i = 0; i < t.length; ++i)
			{
				if (sequenceCounter == t[i].getSequence())
				{
					Point[] p = t[i].getPoints();
					Graphics2D g2 = (Graphics2D) g;
					int j;
					for (j = 0; j < p.length - 1; ++j)
					{
						double x1 = offsetX + scale * p[j].getX();
						double y1 = offsetY + scale * p[j].getY();
						double x2 = offsetX + scale * p[j + 1].getX();
						double y2 = offsetY + scale * p[j + 1].getY();
						Line2D.Double inkSegment = new Line2D.Double(x1, y1, x2, y2);
						g2.setColor(new Color(0, 0, 128)); // deep blue

						if (traces && (!trialMode || (trialMode && trialCounter == t[i].getTrialNumber())))
							g2.draw(inkSegment); // draw it!
						Ellipse2D.Double pt = new Ellipse2D.Double(x2 - 1, y2 - 1, 3, 3);

						if (points && (!trialMode || (trialMode && trialCounter == t[i].getTrialNumber())))
						{
							g2.draw(pt);
							g2.fill(pt);
						}
					}

					// draw marker at selection point
					double x = offsetX + scale * p[j].getX() - 2;
					double y = offsetY + scale * p[j].getY() - 2;
					Ellipse2D.Double e = new Ellipse2D.Double(x, y, 5, 5);

					if (t[i].getError() == 1)
						g2.setColor(Color.red);
					else
						g2.setColor(new Color(0x00bb00));

					if (!trialMode || (trialMode && trialCounter == t[i].getTrialNumber()))
					{
						g2.draw(e);
						g2.fill(e);
					}
				}
			}
		}

		private void paintInfo(Graphics g)
		{
			// first, make sure there is something to paint
			if (t.length == 0)
				return;

			// trialIdx is the index of the 1st trial in the sequence
			int trialIdx = (sequenceCounter - 1) * trialsPerSequence() + (trialCounter - 1);

			// get information about the sequence
			String sequenceInfo = String.format("Sequence: %d of %d (A=%d, W=%d)", sequenceCounter, numberOfSequences(),
					t[trialIdx].a, t[trialIdx].w);

			// get information about the trial
			String trialInfo = trialMode ? String.format("Trial: %d of %d", trialCounter, trialsPerSequence())
					: "Trial: (all trials)";

			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(128, 0, 0));
			g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
			FontMetrics fm = g2.getFontMetrics();
			int height = fm.getHeight();

			int yOffset = 10;
			int line = 1;
			g2.drawString("Participant = " + participantCode, yOffset, line++ * height);
			g2.drawString("Condition = " + conditionCode, yOffset, line++ * height);
			g2.drawString("Block = " + blockCode, yOffset, line++ * height);
			g2.drawString(sequenceInfo, yOffset, line++ * height);
			g2.drawString(trialInfo, yOffset, line++ * height);
			g2.drawString("-----", yOffset, line++ * height);

			// ------------------------------------
			// compute and output accuracy measures
			// ------------------------------------

			if (trialMode)
			{
				// 1st: get the data in the right format
				Point2D.Double from = new Point2D.Double(t[trialIdx].getX1(), t[trialIdx].getY1());
				Point2D.Double to = new Point2D.Double(t[trialIdx].getX2(), t[trialIdx].getY2());
				double width = t[trialIdx].w;

				Point2D.Double[] path = new Point2D.Double[t[trialIdx].p.length];
				for (int i = 0; i < path.length; ++i)
					path[i] = new Point2D.Double(t[trialIdx].p[i].x, t[trialIdx].p[i].y);

				// 2nd: create an AccuracyMeasures object, passing in the data
				AccuracyMeasures ac = new AccuracyMeasures(from, to, width, path);

				g2.drawString(String.format("Number of points = %d", t[trialIdx].p.length), yOffset, line++ * height);
				g2.drawString(String.format("MT = %d ms", t[trialIdx].getMT()), yOffset, line++ * height);
				g2.drawString(String.format("Error = %d", t[trialIdx].getError()), yOffset, line++ * height);
				g2.drawString(String.format("TRE = %d", ac.getTRE()), yOffset, line++ * height);
				g2.drawString(String.format("TAC = %d", ac.getTAC()), yOffset, line++ * height);
				g2.drawString(String.format("MDC = %d", ac.getMDC()), yOffset, line++ * height);
				g2.drawString(String.format("ODC = %d", ac.getODC()), 10, line++ * height);
				g2.drawString(String.format("MV = %1.2f", ac.getMV()), yOffset, line++ * height);
				g2.drawString(String.format("ME = %1.2f", ac.getME()), yOffset, line++ * height);
				g2.drawString(String.format("MO = %1.2f", ac.getMO()), yOffset, line++ * height);
			} else
			// sequence mode
			{
				// compute the means for the measures over all trials in the
				// sequence
				double meanMT = 0.0;
				double meanER = 0.0;
				double meanTRE = 0.0;
				double meanTAC = 0.0;
				double meanMDC = 0.0;
				double meanODC = 0.0;
				double meanMV = 0.0;
				double meanME = 0.0;
				double meanMO = 0.0;

				// iterate through the trials in this sequence
				for (int j = 0; j < trialsPerSequence(); ++j)
				{
					/*
					 * Note: trialIdx is the index of the first trial in the sequence while j is the index of the trial
					 * within the sequence
					 */

					// 1st: get the data in the right format
					Point2D.Double from = new Point2D.Double(t[trialIdx + j].getX1(), t[trialIdx + j].getY1());
					Point2D.Double to = new Point2D.Double(t[trialIdx + j].getX2(), t[trialIdx + j].getY2());
					double width = t[trialIdx + j].w;
					Point2D.Double[] path = new Point2D.Double[t[trialIdx + j].p.length];
					for (int i = 0; i < path.length; ++i)
						path[i] = new Point2D.Double(t[trialIdx + j].p[i].x, t[trialIdx + j].p[i].y);

					// 2nd: create an AccuracyMeasures object, passing in the data
					AccuracyMeasures ac = new AccuracyMeasures(from, to, width, path);

					int idx = (sequenceCounter - 1) * trialsPerSequence() + j;
					meanMT += t[idx].getMT();
					meanER += t[idx].getError();
					meanTRE += ac.getTRE();
					meanTAC += ac.getTAC();
					meanMDC += ac.getMDC();
					meanODC += ac.getODC();
					meanMV += ac.getMV();
					meanME += ac.getME();
					meanMO += ac.getMO();
				}

				meanMT /= trialsPerSequence();
				meanER /= trialsPerSequence();
				meanTRE = meanTRE / trialsPerSequence();
				meanTAC /= trialsPerSequence();
				meanMDC /= trialsPerSequence();
				meanODC /= trialsPerSequence();
				meanMV /= trialsPerSequence();
				meanME /= trialsPerSequence();
				meanMO /= trialsPerSequence();

				g2.drawString("Performance measures (sequence mean)", yOffset, line++ * height);
				g2.drawString(String.format("MT = %.1f ms", meanMT), yOffset, line++ * height);
				g2.drawString(String.format("Error rate = %.1f%%", meanER * 100.0), yOffset, line++ * height);
				g2.drawString("-----", yOffset, line++ * height);

				g2.drawString("Accuracy measures (mean per trial)", yOffset, line++ * height);
				g2.drawString(String.format("TRE = %.2f", meanTRE), yOffset, line++ * height);
				g2.drawString(String.format("TAC = %.2f", meanTAC), yOffset, line++ * height);
				g2.drawString(String.format("MDC = %.2f", meanMDC), yOffset, line++ * height);
				g2.drawString(String.format("ODC = %.2f", meanODC), yOffset, line++ * height);
				g2.drawString(String.format("MV = %1.2f", meanMV), yOffset, line++ * height);
				g2.drawString(String.format("ME = %1.2f", meanME), yOffset, line++ * height);
				g2.drawString(String.format("MO = %1.2f", meanMO), yOffset, line++ * height);
			}
		} // end PaintInfo

		// load the sd3 data into an array t of the class Trial
		private int loadData(String fileArg)
		{
			// set this flag if the data are from FittsTaskOne (false, otherwise)
			if (fileArg.indexOf("FittsTaskOne") >= 0)
				mode = MODE_1D;
			else
				mode = MODE_2D;

			BufferedReader br = null;
			try
			{
				br = new BufferedReader(new FileReader(fileArg));
			} catch (FileNotFoundException e)
			{
				errorExit("File not found error!\nFile: " + fileArg);
			}

			Vector<Trial> v = new Vector<Trial>();
			try
			{
				br.readLine();
				br.readLine();
				String tLine = null;
				while ((tLine = br.readLine()) != null)
				{
					String xLine = br.readLine().trim();
					String yLine = br.readLine().trim();

					// delete commas at end of lines (if any)
					if (tLine.length() > 0 && tLine.charAt(tLine.length() - 1) == ',')
						tLine = tLine.substring(0, tLine.length() - 1);
					if (xLine.length() > 0 && xLine.charAt(xLine.length() - 1) == ',')
						xLine = xLine.substring(0, xLine.length() - 1);
					if (yLine.length() > 0 && yLine.charAt(yLine.length() - 1) == ',')
						yLine = yLine.substring(0, yLine.length() - 1);

					StringTokenizer stt = new StringTokenizer(tLine, ",");
					StringTokenizer stx = new StringTokenizer(xLine, ",");
					StringTokenizer sty = new StringTokenizer(yLine, ",");

					/*
					 * New format for sd3 file includes 4 more columns on left (Filename, Participant, Condition,
					 * Block). Pass over these now.
					 */
					stt.nextToken(); // don't need the filename here
					participantCode = stt.nextToken();
					conditionCode = stt.nextToken();
					blockCode = stt.nextToken();

					stx.nextToken();
					stx.nextToken();
					stx.nextToken();
					stx.nextToken();

					sty.nextToken();
					sty.nextToken();
					sty.nextToken();
					sty.nextToken();

					int sequence = Integer.parseInt(stx.nextToken()); // sequence
					int a = Integer.parseInt(stx.nextToken()); // a
					int w = Integer.parseInt(stx.nextToken()); // w
					int trialNumber = Integer.parseInt(stx.nextToken()); // trial
					int x1 = Integer.parseInt(stx.nextToken()); // from_x
					int y1 = Integer.parseInt(stx.nextToken()); // from_y
					int x2 = Integer.parseInt(stx.nextToken()); // to_x
					int y2 = Integer.parseInt(stx.nextToken()); // to_y

					stx.nextToken(); // x= (trace data begins with next token)

					sty.nextToken(); // skip over the header info for the y data
					sty.nextToken();
					sty.nextToken();
					sty.nextToken();
					sty.nextToken();
					sty.nextToken();
					sty.nextToken();
					sty.nextToken();
					sty.nextToken();

					stt.nextToken(); // skip over the header info for the t data
					stt.nextToken();
					stt.nextToken();
					stt.nextToken();
					stt.nextToken();
					stt.nextToken();
					stt.nextToken();
					stt.nextToken();
					stt.nextToken();

					int[] timeStamp = new int[stt.countTokens()];
					Point[] traceData = new Point[stt.countTokens()];

					for (int i = 0; i < timeStamp.length; ++i)
					{
						timeStamp[i] = Integer.parseInt(stt.nextToken());
						int x = Integer.parseInt(stx.nextToken());
						int y = Integer.parseInt(sty.nextToken());
						traceData[i] = new Point(x, y);
					}
					v.add(new Trial(mode, sequence, a, w, trialNumber, x1, y1, x2, y2, timeStamp, traceData));
				}
			} catch (Exception e)
			{
				errorExit("Data format error!\nFile: " + fileArg + "\nConsult API for format requirements.");
			}
			t = new Trial[v.size()];
			v.copyInto(t);
			sequenceCounter = 1;
			return t.length;
		}

		/*
		 * Output error message in a GUI popup (rather than printing to the console). Then exit.
		 */
		private void errorExit(String message)
		{
			JTextArea tmp = new JTextArea(message);
			tmp.setFont(F16);
			tmp.setBackground(new Color(212, 208, 200));
			JOptionPane.showMessageDialog(this, tmp, "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		public String getFileName()
		{
			return fileName;
		}

		// return the number of sequences of trials
		int numberOfSequences()
		{
			if (t == null || t.length == 0) // no data
				return -1;
			else
				return t[t.length - 1].getSequence();
		}

		// return the number of trials in the current sequence
		int trialsPerSequence()
		{
			if (t == null)
				return 0;
			else
				return t.length / numberOfSequences();
		}

		public void clear()
		{
			this.repaint();
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g); // paint background
			this.paintInfo(g);
			this.computeOffsets();
			this.drawTargets(g);
			this.drawTraces(g);
		}

		// return the target width for the corresponding block
		int getW(int blockArg)
		{
			int w = -1;
			if (t == null || t.length == 0) // no data, return now
				return w;

			for (int i = 0; i < t.length; ++i)
			{
				if (blockArg == t[i].getSequence())
				{
					w = t[i].getW();
					break;
				}
			}
			return w;
		}

		// return the target amplitude for the corresponding sequence
		int getA(int sequenceArg)
		{
			int a = -1;
			if (t == null || t.length == 0) // no data, return now
				return a;

			for (int i = 0; i < t.length; ++i)
			{
				if (sequenceArg == t[i].getSequence())
				{
					a = t[i].getA();
					break;
				}
			}
			return a;
		}

		// display trace data for the next block of trials
		public void next()
		{
			if (trialMode)
			{
				++trialCounter;
				if (trialCounter > trialsPerSequence())
					trialCounter = 1;
			} else
			{
				++sequenceCounter;
				if (sequenceCounter > numberOfSequences())
					sequenceCounter = 1;
			}
			this.repaint();
		}

		// display trace data for previous block of trials
		public void previous()
		{
			if (trialMode)
			{
				--trialCounter;
				if (trialCounter < 1)
					trialCounter = trialsPerSequence();
			} else
			{
				--sequenceCounter;
				if (sequenceCounter == 0)
					sequenceCounter = numberOfSequences();
			}
			this.repaint();
		}

		// sd4 file for "expanded target width" error rates (x1, x2, x3)
		public void makesd4()
		{
			BufferedWriter bw = null;
			try
			{
				bw = new BufferedWriter(new FileWriter("data.sd4", true));
			} catch (IOException e)
			{
				System.out.println("I/O error opening data file");
				System.exit(1);
			}

			for (int i = 0; i < t.length; ++i)
			{
				String s = t[i].getSequence() + ", " + t[i].getA() + ", " + t[i].getW() + ", " + t[i].getTrialNumber()
						+ ", ";
				double r = (double) (t[i].getW()) / 2;
				int x2 = t[i].getX2();
				int y2 = t[i].getY2();
				Point[] p = t[i].getPoints();
				int x3 = p[p.length - 1].x;
				int y3 = p[p.length - 1].y;
				double d = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
				int e1 = d <= r ? 0 : 1;
				int e2 = d <= (2 * r) ? 0 : 1;
				int e3 = d <= (3 * r) ? 0 : 1;
				s += e1 + ", " + e2 + ", " + e3 + "\n";
				try
				{
					bw.write(s, 0, s.length());
					bw.flush();
				} catch (IOException e)
				{
					System.out.println("Error writing to sd4 file!");
					System.exit(1);
				}
			}
		}
	}

	class Trial
	{
		int mode;
		int sequence;
		int a;
		int w;
		int trialNumber;
		int x1;
		int y1;
		int x2;
		int y2;
		int[] t;
		Point[] p;

		Trial(int modeArg, int sequenceArg, int aArg, int wArg, int trialNumberArg, int x1Arg, int y1Arg, int x2Arg,
				int y2Arg, int[] tArg, Point[] pArg)
		{
			modeArg = mode;
			sequence = sequenceArg;
			a = aArg;
			w = wArg;
			trialNumber = trialNumberArg;
			x1 = x1Arg;
			y1 = y1Arg;
			x2 = x2Arg;
			y2 = y2Arg;
			t = tArg;
			p = pArg;
		}

		public String toString()
		{
			String s = "sequence=" + sequence + ", " + "a=" + a + ", " + "w=" + w + ", " + "trialNumber=" + trialNumber
					+ ", " + "x1=" + x1 + ", " + "y1=" + y1 + ", " + "x2=" + x2 + ", " + "y2=" + y2 + ", " + "samples=";

			for (int i = 0; i < p.length; ++i)
				s += "{" + t[i] + "," + p[i].getX() + "," + p[i].getY() + "},";
			return s;
		}

		public int getSequence()
		{
			return sequence;
		}

		public int getTrialNumber()
		{
			return trialNumber;
		}

		public int getA()
		{
			return a;
		}

		public int getW()
		{
			return w;
		}

		public int getMT()
		{
			return t[t.length - 1] - t[0];
		}

		public int getError()
		{
			if (mode == MODE_2D)
			{
				if (Math.sqrt(Math.pow(p[p.length - 1].x - x2, 2) + Math.pow(p[p.length - 1].y - y2, 2)) > w / 2.0f)
					return 1; // error
				else
					return 0; // no error
			} else // 1D mode
			{
				if (Math.abs(p[p.length - 1].x - x2) > w / 2.0f)
					return 1; // error
				else
					return 0; // no error

			}
		}

		public int getX1()
		{
			return x1;
		}

		public int getY1()
		{
			return y1;
		}

		public int getX2()
		{
			return x2;
		}

		public int getY2()
		{
			return y2;
		}

		public int[] getT()
		{
			return t;
		}

		public Point[] getPoints()
		{
			return p;
		}
	}

	/**
	 * A class to extend the FileFilter class (which is abstract) and implement the 'accept' and 'getDescription'
	 * methods.
	 */
	class MyFileFilter extends FileFilter
	{
		private String[] s;

		MyFileFilter(String[] sArg)
		{
			s = sArg;
		}

		// determine which files to display in the chooser
		public boolean accept(File fArg)
		{
			// if it's a directory, show it
			if (fArg.isDirectory())
				return true;

			// if the filename contains the extension, show it
			for (int i = 0; i < s.length; ++i)
				if (fArg.getName().toLowerCase().indexOf(s[i].toLowerCase()) > 0)
					return true;

			// filter out everything else
			return false;
		}

		public String getDescription()
		{
			String tmp = "";
			for (int i = 0; i < s.length; ++i)
				tmp += "*" + s[i] + " ";

			return tmp;
		}
	}
}
