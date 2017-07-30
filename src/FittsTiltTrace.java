import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

/**
 * <h1>FittsTiltTrace</h1>
 * 
 * <h3>Summary</h3>
 * 
 * <ul>
 * <li>Utility software to plot the trace data in the sd3 files created by FittsTilt.
 * </ul>
 * 
 * <h3>Related References</h3>
 * 
 * <ul>
 * <li><a href="http://www.yorku.ca/mack/nordichi2012.html">The application of Fitts' law to tilt-based interaction</a>,
 * by MacKenzie and Teather (<i>NordiCHI 2012</i>). This paper presents a user study using FittsTilt, an Android
 * variation of FittsTaskTwo using device tilt for input.
 * (<a href="http://www.yorku.ca/mack/nordichi2012.html#Figure_9">Figure 9</a>,
 * <a href="http://www.yorku.ca/mack/nordichi2012.html#Figure_11">Figure 11</a>)
 * <p>
 * 
 * 
 * <li><a href="http://www.yorku.ca/mack/gi2014.html">Position vs. velocity control for tilt-based interaction</a>, by Teather and MacKenzie, a continuation of the
 * research in the paper above investigating order-of-control issues for tilt-based interaction.
 * (<a href="http://www.yorku.ca/mack/gi2014.html#Figure_13">Figure 13</a>)
 * </ul>
 * <p>
 * 
 * <h3>Program Operation</h3>
 * 
 * FittsTiltTrace is the same as FittsTrace except the sd3 input data are in a different format.
 * FittsTiltTrace processes the sd3 data created by the Android application FittsTilt (see Related References above).  An extra line is
 * included for each trial representing the tilt of the device.  An example follows:
 * <p>
 * 
 * <ul>
 * <li><a href="FittsTilt-P16-S01-B01-TG50-FE-sd3-example.txt">sd3 example</a>
 * </ul>
 * <p>
 * 
 * The data are comma-delimited, full precision, and there are lots of sample points; so, the file above is hard to view in a text editor.  
 * Below is an example of how this file might look after importing in to Excel.  Some sample points from the first trial are highlighted:
 * (click to enlarge)
 * <p>
 *   
 * <center>
 * <a href="FittsTiltTrace-2.jpg"><img src="FittsTiltTrace-2.jpg" width=500></a>
 * </center>
 * <p>
 * 
 * In the plots created by FittsTiltTrace, the width of the trace is proportional
 * to the device tilt.  Below is an example: (click to enlarge)
 * <p>
 * 
 * <center>
 * <a href="FittsTiltTrace-1.jpg"><img src="FittsTiltTrace-1.jpg" width=500></a>
 * </center>
 * <p>
 * 
 * See as well the figures in the above-noted Related References (links above).
 * <p>
 * 
 * @author Scott MacKenzie, 2011-2016
 */
public class FittsTiltTrace
{
	public static void main(String[] args)
	{
		if (args.length > 0)
		{
			System.out.println("Usage: FittsTiltTrace (see API for details)");
			System.exit(0);
		}

		// use look and feel for my system (Win32)
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
		}

		String tmp = "FittsTiltTrace.sd3"; // default filename
		FittsTiltTraceFrame frame = new FittsTiltTraceFrame(tmp);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("FittsTiltTrace");
		frame.pack();
		frame.setVisible(true);
	}
}

class FittsTiltTraceFrame extends JFrame implements ActionListener, ItemListener
{
	// the following avoids a "warning" with Java 1.5.0 complier (?)
	static final long serialVersionUID = 42L;

	final String[] SCALE_OPTIONS = { "0.4", "0.6", "0.8", "1.0", "1.2", "1.4", "1.6" };
	final int DEFAULT_INDEX = 3; // "1.0" (adjust as necessary)
	final Font F16 = new Font("sansserif", Font.PLAIN, 16);

	/*
	 * The visualization will show a circle at the location of selection. The circle diameter is equal to the ball
	 * diameter in the task. This seems quite large. The purpose in showing a large circle is to help illustrate the
	 * challenge of dwell-time selection (where where the cursor/ball must remain fully within the target for the
	 * specified dwell-time delay).
	 */
	final double BD = 20 * 1.075; // Nexus 4 FittsTilt ball diameter

	int sequenceIdx = -1;
	int trialIdx = -1;

	private TracePanel tp; // for drawing traces

	private JTextField file;
	private JButton browse;
	private JButton next;
	private JButton previous;
	private JButton exit;
	private JCheckBox showPointsCheckBox;
	private JCheckBox showTracesCheckBox;
	private JCheckBox trialByTrial;
	private JComboBox<String> scaleCombo;
	private JLabel scaleLabel;
	private JFileChooser fc;
	private File f;

	private boolean showTraces = true;
	private boolean showPoints = false;
	private boolean trialMode = false;

	double scale = 1.0;
	int ballDiameter = (int)(BD * scale + 0.5);

	String fileName;
	String participantCode, selectionMode, tiltGain;
	FittsTiltTraceSequence[] ts; // ... each of which will hold an array of TiltTrace objects

	public FittsTiltTraceFrame(String fileArg)
	{

		fc = new JFileChooser(new File("."));
		final String[] EXTENSIONS = { ".sd3" };
		
                fc.addChoosableFileFilter(new MyFileFilter(EXTENSIONS));                

		tp = new TracePanel();
		tp.setBorder(BorderFactory.createLineBorder(Color.gray));

		previous = new JButton("Previous");
		next = new JButton("Next");
		exit = new JButton("Exit");

		showTracesCheckBox = new JCheckBox("Show traces");
		showPointsCheckBox = new JCheckBox("Show points");
		trialByTrial = new JCheckBox("Trial Mode");
		showTracesCheckBox.setSelected(true);
		showPointsCheckBox.setSelected(false);
		trialByTrial.setSelected(false);

		scaleLabel = new JLabel("Scale: ");
		scaleLabel.setFont(F16);

		scaleCombo = new JComboBox<String>(SCALE_OPTIONS);
		scaleCombo.setSelectedIndex(DEFAULT_INDEX);
		scaleCombo.setMaximumSize(new Dimension(60, 25));

		scale = Double.parseDouble((String)scaleCombo.getSelectedItem());

		browse = new JButton("Browse...");
		previous.setFont(F16);
		next.setFont(F16);
		exit.setFont(F16);
		browse.setFont(F16);
		showTracesCheckBox.setFont(F16);
		showPointsCheckBox.setFont(F16);
		trialByTrial.setFont(F16);
		scaleCombo.setFont(F16);

		file = new JTextField();
		file.setEditable(false);
		file.setFocusable(false);
		file.setBackground(Color.white);
		Dimension d2 = browse.getPreferredSize();
		file.setMaximumSize(new Dimension(400, d2.height));
		file.setFont(F16);

		// -------------
		// add listeners
		// -------------

		next.addActionListener(this);
		previous.addActionListener(this);
		exit.addActionListener(this);
		browse.addActionListener(this);
		file.addActionListener(this);
		showTracesCheckBox.addItemListener(this);
		showPointsCheckBox.addItemListener(this);
		trialByTrial.addItemListener(this);
		scaleCombo.addActionListener(this);

		// ------------------
		// arrange components
		// ------------------

		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(scaleLabel);
		p1.add(scaleCombo);
		p1.add(Box.createRigidArea(new Dimension(5, 0)));
		p1.add(showTracesCheckBox);
		p1.add(showPointsCheckBox);
		p1.add(trialByTrial);
		p1.add(Box.createRigidArea(new Dimension(20, 0)));
		p1.add(file);
		p1.add(Box.createRigidArea(new Dimension(5, 0)));
		p1.add(browse);
		p1.add(Box.createRigidArea(new Dimension(20, 0)));
		p1.add(previous);
		p1.add(Box.createRigidArea(new Dimension(5, 0)));
		p1.add(next);
		p1.add(Box.createRigidArea(new Dimension(5, 0)));
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
		browse.doClick();
	}

	// -------------------------------
	// implement ItemListener method
	// -------------------------------
	public void itemStateChanged(ItemEvent ie)
	{
		Object source = ie.getSource();
		if (source == showTracesCheckBox)
			showTraces = !showTraces;
		else if (source == showPointsCheckBox)
			showPoints = !showPoints;
		else if (source == trialByTrial)
		{
			trialMode = !trialMode;
			trialIdx = 0;
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
			next();
		else if (source == previous)
			previous();
		else if (source == exit)
			System.exit(0);
		else if (source == browse)
		{
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				f = fc.getSelectedFile();
				loadData(f);
				file.setText(f.getName());
			}
		} else if (source == file)
		{
			loadData(new File(file.getText()));
		} else if (source == scaleCombo)
		{
			scale = Double.parseDouble((String)scaleCombo.getSelectedItem());
			ballDiameter = (int)(20.0 * scale + 0.5);
			tp.repaint();
		}
	}

	/*
	 * Load the sd3 data. This method must be tailored to the format of the data in the input file. The goal is to
	 * create and array of TiltSequence objects that will be subsequently accessed to show the targets and trace data
	 * from trials in the experiment.
	 */
	@SuppressWarnings("resource")
	private boolean loadData(File fArg)
	{
		String fileName = fArg.getName();
		String filePath = fArg.getPath();

		// get participant code
		int z = fileName.indexOf("P");
		participantCode = fileName.substring(z, z + 3);

		// first extract selection mode and TVG from file name
		if (fileName.indexOf("FE") > 0)
			selectionMode = "First_Entry";
		else if (fileName.indexOf("DW500") > 0)
			selectionMode = "Dwell_500";
		else
			selectionMode = "?";

		if (fileName.indexOf("TG25") > 0)
			tiltGain = "25";
		else if (fileName.indexOf("TG50") > 0)
			tiltGain = "50";
		else if (fileName.indexOf("TG100") > 0)
			tiltGain = "100";
		else if (fileName.indexOf("TG200") > 0)
			tiltGain = "200";
		else
			tiltGain = "?";

		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e)
		{
			JLabel tmp = new JLabel("File not found: " + filePath);
			tmp.setFont(F16);
			JOptionPane.showMessageDialog(this, tmp, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		Vector<FittsTiltTraceTrial> vTrial = new Vector<FittsTiltTraceTrial>();
		Vector<FittsTiltTraceSequence> vSequence = new Vector<FittsTiltTraceSequence>();
		int sequenceIdxSave = 0;
		double aSave = 0.0;
		double wSave = 0.0;
		try
		{
			br.readLine(); // discard 1st header
			br.readLine(); // discard 2nd header
			String timeLine = null;
			double a = 0.0;
			double w = 0.0;
			while ((timeLine = br.readLine()) != null)
			{
				String xLine = br.readLine().trim();
				String yLine = br.readLine().trim();
				String tiltLine = br.readLine().trim();

				// delete commas at end of lines (if any)
				if (timeLine.charAt(timeLine.length() - 1) == ',')
					timeLine = timeLine.substring(0, timeLine.length() - 1);
				if (xLine.charAt(xLine.length() - 1) == ',')
					xLine = xLine.substring(0, xLine.length() - 1);
				if (yLine.charAt(yLine.length() - 1) == ',')
					yLine = yLine.substring(0, yLine.length() - 1);
				if (tiltLine.charAt(tiltLine.length() - 1) == ',')
					tiltLine = tiltLine.substring(0, tiltLine.length() - 1);

				StringTokenizer stt = new StringTokenizer(timeLine, ",");
				StringTokenizer stx = new StringTokenizer(xLine, ",");
				StringTokenizer sty = new StringTokenizer(yLine, ",");
				StringTokenizer stTilt = new StringTokenizer(tiltLine, ",");

				int sequenceIdx = Integer.parseInt(stx.nextToken()); // sequence
				a = Double.parseDouble(stx.nextToken()); // a
				w = Double.parseDouble(stx.nextToken()); // w
				stx.nextToken(); // trial (implicit)
				double fromX = Double.parseDouble(stx.nextToken()); // from_x
				double fromY = Double.parseDouble(stx.nextToken()); // from_y
				double toX = Double.parseDouble(stx.nextToken()); // to_x
				double toY = Double.parseDouble(stx.nextToken()); // to_y
				stx.nextToken(); // "x=" (trace data begins with next token

				sty.nextToken(); // skip over the lead-in info for the y data
				sty.nextToken();
				sty.nextToken();
				sty.nextToken();
				sty.nextToken();
				sty.nextToken();
				sty.nextToken();
				sty.nextToken();
				sty.nextToken();

				stt.nextToken(); // skip over the lead-in info for the t data
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();

				stTilt.nextToken(); // skip over the lead-in info for the tilt data
				stTilt.nextToken();
				stTilt.nextToken();
				stTilt.nextToken();
				stTilt.nextToken();
				stTilt.nextToken();
				stTilt.nextToken();
				stTilt.nextToken();
				stTilt.nextToken();

				int n = stt.countTokens();

				if (n != stx.countTokens() || n != sty.countTokens() || n != stTilt.countTokens())
				{
					System.out.println("Oops!  Token count problem! Bye!");
					System.exit(0);
				}

				FittsTiltTracePoint[] tp = new FittsTiltTracePoint[n];

				for (int i = 0; i < n; ++i)
				{
					int time = Integer.parseInt(stt.nextToken());
					double x = Double.parseDouble(stx.nextToken());
					double y = Double.parseDouble(sty.nextToken());
					double tilt = Double.parseDouble(stTilt.nextToken());
					tp[i] = new FittsTiltTracePoint(x, y, time, tilt);
				}
				FittsTiltTraceTrial temp = new FittsTiltTraceTrial(fromX, fromY, toX, toY, w, ballDiameter, tp);
				if (sequenceIdx != sequenceIdxSave)
				{
					FittsTiltTraceTrial[] tt = new FittsTiltTraceTrial[vTrial.size()];
					vTrial.copyInto(tt);
					vSequence.add(new FittsTiltTraceSequence(sequenceIdxSave, aSave, wSave, tt));
					vTrial.clear();
				}
				vTrial.add(temp);
				sequenceIdxSave = sequenceIdx;
				aSave = a;
				wSave = w;
			}
			FittsTiltTraceTrial[] tt = new FittsTiltTraceTrial[vTrial.size()];
			vTrial.copyInto(tt);
			vSequence.add(new FittsTiltTraceSequence(sequenceIdxSave, aSave, wSave, tt));

		} catch (IOException e)
		{
			JLabel tmp = new JLabel("Data format error in file: " + fileName);
			tmp.setFont(F16);
			JOptionPane.showMessageDialog(this, tmp, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		ts = new FittsTiltTraceSequence[vSequence.size()];
		vSequence.copyInto(ts);

		sequenceIdx = 0;
		trialIdx = 0;
		this.repaint();
		return true;
	}

	// display trace data for the next sequence/trial
	public void next()
	{
		if (trialMode)
		{
			++trialIdx;
			if (trialIdx >= trialsPerSequence())
				trialIdx = 0;
		} else
		{
			++sequenceIdx;
			if (sequenceIdx >= numberOfSequences())
				sequenceIdx = 0;
		}
		this.repaint();
	}

	// display trace data for previous sequence/trial
	public void previous()
	{
		if (trialMode)
		{
			--trialIdx;
			if (trialIdx < 0)
				trialIdx = trialsPerSequence() - 1;
		} else
		{
			--sequenceIdx;
			if (sequenceIdx < 0)
				sequenceIdx = numberOfSequences() - 1;
		}
		this.repaint();
	}

	// return the number of sequences of trials (in this file/block)
	int numberOfSequences()
	{
		if (ts == null)
			return 0;
		else
			return ts.length;
	}

	// return the number of trials in the current sequence
	int trialsPerSequence()
	{
		if (ts == null)
			return 0;
		else
			return ts[0].tiltTrial.length;
	}

	// -----------------------------------------
	// A class to display the targets and traces
	// -----------------------------------------

	class TracePanel extends JPanel
	{                
		static final long serialVersionUID = 42L;

		TracePanel()
		{
			this.setBackground(Color.white);
		}

		final double BOTTOM_MARGIN = 50.0;

		public void drawTargets(Graphics g)
		{       System.out.println("drawTargets()");
			if (ts == null || ts.length == 0)
				return;

			double centerLayoutX = scale * (ts[sequenceIdx].tiltTrial[0].fromX + ts[sequenceIdx].tiltTrial[0].toX)
					/ 2.0;
			double centerLayoutY = scale * (ts[sequenceIdx].tiltTrial[0].fromY + ts[sequenceIdx].tiltTrial[0].toY)
					/ 2.0;
			double centerPanelX = this.getWidth() / 2.0;
			double centerPanelY = this.getHeight() / 2.0 - BOTTOM_MARGIN;
			double offsetX = centerPanelX - centerLayoutX;
			double offsetY = centerPanelY - centerLayoutY;

			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(Color.black);

			for (int i = 0; i < ts[sequenceIdx].tiltTrial.length; ++i)
			{
				double w = scale * ts[sequenceIdx].w; // same throughout sequence

				if (i == 0) // "from" target in 1st trial only (to avoid duplication)
				{
					double x = offsetX + scale * ts[sequenceIdx].tiltTrial[i].fromX - (w / 2);
					double y = offsetY + scale * ts[sequenceIdx].tiltTrial[i].fromY - (w / 2);
					Ellipse2D.Double e = new Ellipse2D.Double(x, y, w, w);
					g2.draw(e);
				}

				double x = offsetX + scale * ts[sequenceIdx].tiltTrial[i].toX - (w / 2);
				double y = offsetY + scale * ts[sequenceIdx].tiltTrial[i].toY - (w / 2);
				Ellipse2D.Double e = new Ellipse2D.Double(x, y, w, w);
				g2.draw(e);

				// in trial mode, draw target to select last (to occlude overlapping targets, if any)
				if (trialMode)
				{
					x = offsetX + scale * ts[sequenceIdx].tiltTrial[trialIdx].toX - (w / 2);
					y = offsetY + scale * ts[sequenceIdx].tiltTrial[trialIdx].toY - (w / 2);
					e = new Ellipse2D.Double(x, y, w, w);
					g2.setColor(Color.lightGray);
					g2.fill(e); // fill the target to select in light gray
					g2.setColor(Color.black);
					g2.draw(e);
				}
			}
		}

		public void drawTraces(Graphics g)
		{
			if (ts == null || ts.length == 0)
				return;

			double centerLayoutX = scale * (ts[sequenceIdx].tiltTrial[0].fromX + ts[sequenceIdx].tiltTrial[0].toX)
					/ 2.0;
			double centerLayoutY = scale * (ts[sequenceIdx].tiltTrial[0].fromY + ts[sequenceIdx].tiltTrial[0].toY)
					/ 2.0;
			double centerPanelX = this.getWidth() / 2.0;
			double centerPanelY = this.getHeight() / 2.0 - BOTTOM_MARGIN;
			double offsetX = centerPanelX - centerLayoutX;
			double offsetY = centerPanelY - centerLayoutY;

			Graphics2D g2 = (Graphics2D)g;
			Ellipse2D.Double e;

			for (int i = 0; i < ts[sequenceIdx].tiltTrial.length; ++i)
			{
				FittsTiltTracePoint[] tp = ts[sequenceIdx].tiltTrial[i].p;

				// draw special marker at start of sequence
				if (i == 0 && (!trialMode || (trialMode && trialIdx == i)))
				{
					double xx = offsetX + scale * ts[sequenceIdx].tiltTrial[i].p[0].x - 5;
					double yy = offsetY + scale * ts[sequenceIdx].tiltTrial[i].p[0].y - 5;
					e = new Ellipse2D.Double(xx, yy, 11, 11);
					g2.setColor(Color.BLACK);
					g2.draw(e);
					g2.setColor(Color.YELLOW);
					g2.fill(e);
				}

				int j;
				for (j = 0; j < tp.length - 1; ++j)
				{
					double x1 = offsetX + scale * tp[j].x;
					double y1 = offsetY + scale * tp[j].y;
					double x2 = offsetX + scale * tp[j + 1].x;
					double y2 = offsetY + scale * tp[j + 1].y;

					Line2D.Double inkSegment = new Line2D.Double(x1, y1, x2, y2);

					g2.setColor(new Color(0, 0, 128)); // deep blue

					// make the thickness proportional to the tilt magnitude
					float lineWidth = (float)tp[j + 1].tilt / 2f;
					g2.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

					if (showTraces && (!trialMode || (trialMode && trialIdx == i)))
						g2.draw(inkSegment); // draw it!

					Ellipse2D.Double pt = new Ellipse2D.Double(x2 - 1, y2 - 1, 2, 2);
					if (showPoints && (!trialMode || (trialMode && trialIdx == i)))
					{
						g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
						g2.setColor(Color.red);
						g2.draw(pt);
						g2.fill(pt);
					}
				}
			}

			// draw marker at selection point (do last to avoid occulsion)
			for (int i = 0; i < ts[sequenceIdx].tiltTrial.length; ++i)
			{
				int lastPointIdx = ts[sequenceIdx].tiltTrial[i].p.length - 1;
				double x = offsetX + scale * (ts[sequenceIdx].tiltTrial[i].p[lastPointIdx].x - ballDiameter / 2.0);
				double y = offsetY + scale * (ts[sequenceIdx].tiltTrial[i].p[lastPointIdx].y - ballDiameter / 2.0);
				e = new Ellipse2D.Double(x, y, ballDiameter, ballDiameter);
				g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2.setColor(Color.red);
				if (!trialMode || (trialMode && trialIdx == i))
				{
					g2.draw(e);
					g2.fill(e);
				}
			}
		}

		public void clear()
		{
			this.repaint();
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g); // paint background
			if (ts == null)
				return;
			else
				paintBlock(g);
		}

		private void paintBlock(Graphics g)
		{
			this.paintInfo(g);
			this.drawTargets(g);
			this.drawTraces(g);
		}

		private void paintInfo(Graphics g)
		{
			String header1 = String.format("Sequence: %d of %d (A=%.1f, W=%.1f)", (sequenceIdx + 1),
					numberOfSequences(), ts[sequenceIdx].a, ts[sequenceIdx].w);
			String header2 = trialMode ? String.format("Trial: %d of %d", (trialIdx + 1), trialsPerSequence())
					: "Trial: (all trials)";

			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(new Color(128, 0, 0));
			g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
			FontMetrics fm = g2.getFontMetrics();
			int height = fm.getHeight();

			int yOffset = 10;
			int line = 1;
			g2.drawString("Participant = " + participantCode, yOffset, line++ * height);
			g2.drawString("Selection mode = " + selectionMode, yOffset, line++ * height);
			g2.drawString("Tilt gain = " + tiltGain, yOffset, line++ * height);
			g2.drawString(header1, yOffset, line++ * height);
			g2.drawString(header2, yOffset, line++ * height);
			g2.drawString("-----", yOffset, line++ * height);

			if (trialMode)
			{
				g2.drawString(String.format("Number of points = %d",
						ts[sequenceIdx].tiltTrial[trialIdx].numberOfSamplePoints), yOffset, line++ * height);
				g2.drawString(String.format("PT = %d ms", ts[sequenceIdx].tiltTrial[trialIdx].positioningTime), yOffset,
						line++ * height);
				g2.drawString(String.format("ST = %d ms", ts[sequenceIdx].tiltTrial[trialIdx].selectionTime), yOffset,
						line++ * height);
				g2.drawString(String.format("MT = %d ms", ts[sequenceIdx].tiltTrial[trialIdx].movementTime), yOffset,
						line++ * height);
				g2.drawString(String.format("Max Tilt = %1.1f degrees", ts[sequenceIdx].tiltTrial[trialIdx].maxTilt),
						10, line++ * height);
				g2.drawString(String.format("TRE = %d", ts[sequenceIdx].tiltTrial[trialIdx].tre), yOffset,
						line++ * height);
				g2.drawString(String.format("TAC = %d", ts[sequenceIdx].tiltTrial[trialIdx].tac), yOffset,
						line++ * height);
				g2.drawString(String.format("MDC = %d", ts[sequenceIdx].tiltTrial[trialIdx].mdc), yOffset,
						line++ * height);
				g2.drawString(String.format("ODC = %d", ts[sequenceIdx].tiltTrial[trialIdx].odc), 10, line++ * height);
				g2.drawString(String.format("MV = %1.2f", ts[sequenceIdx].tiltTrial[trialIdx].mv), yOffset,
						line++ * height);
				g2.drawString(String.format("ME = %1.2f", ts[sequenceIdx].tiltTrial[trialIdx].me), yOffset,
						line++ * height);
				g2.drawString(String.format("MO = %1.2f", ts[sequenceIdx].tiltTrial[trialIdx].mo), yOffset,
						line++ * height);
			} else
			// sequence mode
			{
				g2.drawString("Performance measures (mean per sequence)", yOffset, line++ * height);
				g2.drawString(String.format("PT = %.1f ms", ts[sequenceIdx].meanPT), yOffset, line++ * height);
				g2.drawString(String.format("ST = %.1f ms", ts[sequenceIdx].meanST), yOffset, line++ * height);
				g2.drawString(String.format("MT = %.1f ms", ts[sequenceIdx].meanMT), yOffset, line++ * height);
				g2.drawString(String.format("Max tilt = %.1f degrees", ts[sequenceIdx].meanMaxTilt), yOffset,
						line++ * height);
				g2.drawString(String.format("TRE = %.1f", ts[sequenceIdx].meanTRE), yOffset, line++ * height);
				g2.drawString(String.format("TAC = %.1f", ts[sequenceIdx].meanTAC), yOffset, line++ * height);
				g2.drawString(String.format("MDC = %.1f", ts[sequenceIdx].meanMDC), yOffset, line++ * height);
				g2.drawString(String.format("ODC = %.1f", ts[sequenceIdx].meanODC), yOffset, line++ * height);
				g2.drawString(String.format("MV = %1.2f", ts[sequenceIdx].meanMV), yOffset, line++ * height);
				g2.drawString(String.format("ME = %1.2f", ts[sequenceIdx].meanME), yOffset, line++ * height);
				g2.drawString(String.format("MO = %1.2f", ts[sequenceIdx].meanMO), yOffset, line++ * height);
			}
		} // end PaintInfo
	} // end FittsTiltTracePanel
}