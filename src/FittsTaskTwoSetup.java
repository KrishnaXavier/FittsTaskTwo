import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

// ---------
// S E T U P
// ---------

class FittsTaskTwoSetup extends JDialog implements ActionListener, FocusListener, ItemListener
{
	// the following avoids a "warning" with Java 1.5.0 complier (?)
	static final long serialVersionUID = 42L;

	FittsTaskTwoConfiguration c, cSave;
	JLabel banner;
	JTextField participantCode;
	JComboBox<String> conditionCode;
	JComboBox<String> blockCode;
	JComboBox<String> numberOfTrials;
	JTextField aField;
	JTextField wField;
	JTextField errorField;
	JTextField hysteresisField;
	JCheckBox randomizeCheckBox;
	JCheckBox beepOnErrorCheckBox;
	JCheckBox buttonDownCheckBox;
	JCheckBox mouseOverCheckBox;
	JButton backgroundColorButton;
	JButton foregroundColorButton;
	JButton targetColorButton;
	JButton buttonDownColorButton;
	JButton mouseOverColorButton;
	JButton okButton;
	JButton saveButton;
	JButton resetButton;
	JButton exitButton;

	final String[] CONDITION_CODES = { "C01", "C02", "C03", "C04", "C05", "C06", "C07", "C08", "C09", "C10", "C11",
			"C12", "C13", "C14", "C15", "C16", "C17", "C18", "C19", "C20", "C21", "C22", "C23", "C24", "C25" };
	final String[] BLOCK_CODES = { "B01", "B02", "B03", "B04", "B05", "B06", "B07", "B08", "B09", "B10", "B11", "B12",
			"B13", "B14", "B15", "B16", "B17", "B18", "B19", "B20", "B21", "B22", "B23", "B24", "B25" };
	final String[] ODD_NUMBERS = { "1", "3", "5", "7", "9", "11", "13", "15", "17", "19", "21", "23", "25" };
	final Font F14 = new Font("sansserif", Font.PLAIN, 14);

	FittsTaskTwoSetup(Frame owner, FittsTaskTwoConfiguration cArg)
	{
		super(owner, "Configure FittsTaskTwo", true);
		c = cArg;
		cSave = new FittsTaskTwoConfiguration(c.getParticipantCode(), c.getConditionCode(), c.getBlockCode(), c
				.getNumberOfTargets(), c.getA(), c.getW(), c.getRandomize(), c.getBeepOnError(), c
				.getButtonDownHighlight(), c.getMouseOverHighlight(), c.getErrorThreshold(), c.getHysteresis(), c
				.getBackgroundColor(), c.getForegroundColor(), c.getTargetColor(), c.getButtonDownColor(), c
				.getMouseOverColor());

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		banner = new JLabel("Setup For FittsTaskTwo", SwingConstants.CENTER);
		banner.setFont(new Font("sansserif", Font.PLAIN, 22));
		banner.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		participantCode = new JTextField();
		participantCode.setFont(F14);
		participantCode.addFocusListener(this);
		participantCode.addActionListener(this);

		conditionCode = new JComboBox<String>(CONDITION_CODES);
		conditionCode.setFont(F14);
		conditionCode.addActionListener(this);

		blockCode = new JComboBox<String>(BLOCK_CODES);
		blockCode.setFont(F14);
		blockCode.addActionListener(this);		

		numberOfTrials = new JComboBox<String>(ODD_NUMBERS);
		numberOfTrials.setFont(F14);
		numberOfTrials.addActionListener(this);

		aField = new JTextField(30); // determines width of right panel
		aField.setFont(F14);
		aField.addFocusListener(this);
		aField.addActionListener(this);

		wField = new JTextField();
		wField.setFont(F14);
		wField.addFocusListener(this);
		wField.addActionListener(this);

		errorField = new JTextField();
		errorField.setFont(F14);
		errorField.addFocusListener(this);
		errorField.addActionListener(this);

		hysteresisField = new JTextField();
		hysteresisField.setFont(F14);
		hysteresisField.addFocusListener(this);
		hysteresisField.addActionListener(this);

		randomizeCheckBox = new JCheckBox();
		randomizeCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		randomizeCheckBox.addItemListener(this);

		beepOnErrorCheckBox = new JCheckBox();
		beepOnErrorCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		beepOnErrorCheckBox.addItemListener(this);

		buttonDownCheckBox = new JCheckBox();
		buttonDownCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		buttonDownCheckBox.addItemListener(this);

		mouseOverCheckBox = new JCheckBox();
		mouseOverCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		mouseOverCheckBox.addItemListener(this);

		backgroundColorButton = new JButton("   ");
		foregroundColorButton = new JButton("   ");
		targetColorButton = new JButton("   ");
		buttonDownColorButton = new JButton("   ");
		mouseOverColorButton = new JButton("   ");

		backgroundColorButton.addActionListener(this);
		foregroundColorButton.addActionListener(this);
		targetColorButton.addActionListener(this);
		buttonDownColorButton.addActionListener(this);
		mouseOverColorButton.addActionListener(this);

		backgroundColorButton.setContentAreaFilled(false);
		backgroundColorButton.setOpaque(true);
		foregroundColorButton.setContentAreaFilled(false);
		foregroundColorButton.setOpaque(true);
		targetColorButton.setContentAreaFilled(false);
		targetColorButton.setOpaque(true);
		buttonDownColorButton.setContentAreaFilled(false);
		buttonDownColorButton.setOpaque(true);
		mouseOverColorButton.setContentAreaFilled(false);
		mouseOverColorButton.setOpaque(true);

		okButton = new JButton("OK");
		okButton.setFont(F14);
		okButton.addActionListener(this);

		saveButton = new JButton("Save");
		saveButton.setFont(F14);
		saveButton.addActionListener(this);

		resetButton = new JButton("Reset");
		resetButton.setFont(F14);
		resetButton.addActionListener(this);

		exitButton = new JButton("Exit");
		exitButton.setFont(F14);
		exitButton.addActionListener(this);

		okButton.setPreferredSize(resetButton.getPreferredSize());
		saveButton.setPreferredSize(resetButton.getPreferredSize());
		exitButton.setPreferredSize(resetButton.getPreferredSize());

		setDefaults();

		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(0, 1, 0, 10));

		JLabel l5 = new JLabel("Participant Code ", SwingConstants.RIGHT);
		l5.setFont(F14);
		leftPanel.add(l5);

		JLabel l5a = new JLabel("Condition Code ", SwingConstants.RIGHT);
		l5a.setFont(F14);
		leftPanel.add(l5a);

		JLabel l6 = new JLabel("Block Code ", SwingConstants.RIGHT);
		l6.setFont(F14);
		leftPanel.add(l6);		

		JLabel l7 = new JLabel("Number Of Targets ", SwingConstants.RIGHT);
		l7.setFont(F14);
		leftPanel.add(l7);

		JLabel l8 = new JLabel("Target Amplitudes ", SwingConstants.RIGHT);
		l8.setFont(F14);
		leftPanel.add(l8);

		JLabel l9 = new JLabel("Target Widths ", SwingConstants.RIGHT);
		l9.setFont(F14);
		leftPanel.add(l9);

		JLabel l10 = new JLabel("Error Threshold ", SwingConstants.RIGHT);
		l10.setFont(F14);
		leftPanel.add(l10);

		JLabel l11 = new JLabel("   Spatial Hysteresis ", SwingConstants.RIGHT);
		l11.setFont(F14);
		leftPanel.add(l11);

		leftPanel.add(randomizeCheckBox);
		leftPanel.add(beepOnErrorCheckBox);
		leftPanel.add(buttonDownCheckBox);
		leftPanel.add(mouseOverCheckBox);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(0, 1, 0, 10));
		rightPanel.add(participantCode);
		rightPanel.add(conditionCode);
		rightPanel.add(blockCode);			
		rightPanel.add(numberOfTrials);
		rightPanel.add(aField);
		rightPanel.add(wField);
		rightPanel.add(errorField);
		rightPanel.add(hysteresisField);
		JLabel tmp1 = new JLabel(" Randomize Target Conditions", SwingConstants.LEFT);
		tmp1.setFont(F14);
		rightPanel.add(tmp1);
		JLabel tmp2 = new JLabel(" Beep On Error", SwingConstants.LEFT);
		tmp2.setFont(F14);
		rightPanel.add(tmp2);
		JLabel tmp33 = new JLabel(" Button-down Highlight", SwingConstants.LEFT);
		tmp33.setFont(F14);
		rightPanel.add(tmp33);
		JLabel tmp3 = new JLabel(" Mouse-over Highlight", SwingConstants.LEFT);
		tmp3.setFont(F14);
		rightPanel.add(tmp3);

		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new GridLayout(2, 5));
		JLabel tmp4 = new JLabel("Background");
		tmp4.setFont(F14);
		colorPanel.add(tmp4);
		JLabel tmp5 = new JLabel("Foreground");
		tmp5.setFont(F14);
		colorPanel.add(tmp5);
		JLabel tmp6 = new JLabel("Target");
		tmp6.setFont(F14);
		colorPanel.add(tmp6);
		JLabel tmp66 = new JLabel("Button-down");
		tmp66.setFont(F14);
		colorPanel.add(tmp66);
		JLabel tmp7 = new JLabel("Mouse-over");
		tmp7.setFont(F14);
		colorPanel.add(tmp7);
		colorPanel.add(backgroundColorButton);
		colorPanel.add(foregroundColorButton);
		colorPanel.add(targetColorButton);
		colorPanel.add(buttonDownColorButton);
		colorPanel.add(mouseOverColorButton);
		colorPanel.setBorder(new TitledBorder(new EtchedBorder(), "Colours"));

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(leftPanel, "West");
		centerPanel.add(rightPanel, "East");
		centerPanel.add(colorPanel, "South");
		centerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Parameters"));

		JPanel OKExitPanel = new JPanel();
		OKExitPanel.add(okButton);
		OKExitPanel.add(saveButton);
		OKExitPanel.add(resetButton);
		OKExitPanel.add(exitButton);
		OKExitPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		top.add("North", banner);
		top.add("Center", centerPanel);
		top.add("South", OKExitPanel);
		top.add("West", new JLabel("            "));
		top.add("East", new JLabel("            "));
		this.setContentPane(top);
		this.pack();
		okButton.requestFocus();
	}

	private int[] getArray(String s)
	{
		StringTokenizer st = new StringTokenizer(s);
		int tmp[] = new int[st.countTokens()];
		for (int i = 0; i < tmp.length; ++i)
			tmp[i] = Integer.parseInt(st.nextToken());
		return tmp;
	}

	public void itemStateChanged(ItemEvent ie)
	{
		Object source = ie.getSource();
		if (source == randomizeCheckBox)
			c.setRandomize(randomizeCheckBox.isSelected());
		else if (source == beepOnErrorCheckBox)
			c.setBeepOnError(beepOnErrorCheckBox.isSelected());
		else if (source == buttonDownCheckBox)
			c.setButtonDownHighlight(buttonDownCheckBox.isSelected());
		else if (source == mouseOverCheckBox)
			c.setMouseOverHighlight(mouseOverCheckBox.isSelected());
	}

	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if (source == exitButton)
			System.exit(0);
		else if (source == saveButton)
			saveToFile();
		else if (source == resetButton)
			setDefaults();
		else if (source == okButton)
			this.setVisible(false);
		else if (source == conditionCode)
			c.setConditionCode(CONDITION_CODES[conditionCode.getSelectedIndex()]);
		else if (source == blockCode)
			c.setBlockCode(BLOCK_CODES[blockCode.getSelectedIndex()]);		
		else if (source == numberOfTrials)			
			c.setNumberOfTrials(Integer.parseInt(ODD_NUMBERS[numberOfTrials.getSelectedIndex()]));
		else if (source == participantCode)
			c.setParticipantCode(participantCode.getText());
		else if (source == aField)
			c.setA(this.getArray(aField.getText()));
		else if (source == wField)
			c.setW(this.getArray(aField.getText()));
		else if (source == errorField)
			c.setErrorThreshold(Integer.parseInt(errorField.getText()));
		else if (source == hysteresisField)
			c.setHysteresis(Double.parseDouble(hysteresisField.getText()));
		else if (source == backgroundColorButton)
		{
			Color tmp = JColorChooser.showDialog(this, "Choose Background color", c.getBackgroundColor());
			if (tmp != null)
			{
				backgroundColorButton.setBackground(tmp);
				c.setBackgroundColor(tmp);
			}
		} else if (source == foregroundColorButton)
		{
			Color tmp = JColorChooser.showDialog(this, "Choose Foreground color", c.getForegroundColor());
			if (tmp != null)
			{
				foregroundColorButton.setBackground(tmp);
				c.setForegroundColor(tmp);
			}
		} else if (source == targetColorButton)
		{
			Color tmp = JColorChooser.showDialog(this, "Choose Target color", c.getTargetColor());
			if (tmp != null)
			{
				targetColorButton.setBackground(tmp);
				c.setTargetColor(tmp);
			}
		} else if (source == buttonDownColorButton)
		{
			Color tmp = JColorChooser.showDialog(this, "Choose Button-down color", c.getTargetColor());
			if (tmp != null)
			{
				buttonDownColorButton.setBackground(tmp);
				c.setButtonDownColor(tmp);
			}
		} else if (source == mouseOverColorButton)
		{
			Color tmp = JColorChooser.showDialog(this, "Choose Mouse-over color", c.getMouseOverColor());
			if (tmp != null)
			{
				mouseOverColorButton.setBackground(tmp);
				c.setMouseOverColor(tmp);
			}
		}
		return;
	}

	public void focusGained(FocusEvent fe)
	{
	}

	public void focusLost(FocusEvent fe)
	{
		Object source = fe.getSource();
		if (source == participantCode)
			c.setParticipantCode(participantCode.getText());
		else if (source == aField)
			c.setA(this.getArray(aField.getText()));
		else if (source == wField)
			c.setW(this.getArray(wField.getText()));
		else if (source == errorField)
			c.setErrorThreshold(Integer.parseInt(errorField.getText()));
		else if (source == hysteresisField)
			c.setHysteresis(Double.parseDouble(hysteresisField.getText()));
	}

	private void setDefaults()
	{
		c.setParticipantCode(cSave.getParticipantCode());
		c.setConditionCode(cSave.getConditionCode());
		c.setBlockCode(cSave.getBlockCode());	
		c.setNumberOfTrials(cSave.getNumberOfTargets());
		c.setA(cSave.getA());
		c.setW(cSave.getW());
		c.setRandomize(cSave.getRandomize());
		c.setBeepOnError(cSave.getBeepOnError());
		c.setButtonDownHighlight(cSave.getButtonDownHighlight());
		c.setMouseOverHighlight(cSave.getMouseOverHighlight());
		c.setErrorThreshold(cSave.getErrorThreshold());
		c.setHysteresis(cSave.getHysteresis());
		c.setBackgroundColor(cSave.getBackgroundColor());
		c.setForegroundColor(cSave.getForegroundColor());
		c.setTargetColor(cSave.getTargetColor());
		c.setButtonDownColor(cSave.getButtonDownColor());
		c.setMouseOverColor(cSave.getMouseOverColor());

		participantCode.setText(c.getParticipantCode());

		for (int i = 0; i < CONDITION_CODES.length; ++i)
		{
			if (c.getConditionCode().equals(CONDITION_CODES[i]))
			{
				conditionCode.setSelectedIndex(i);
				break;
			}
		}
		for (int i = 0; i < BLOCK_CODES.length; ++i)
		{
			if (c.getBlockCode().equals(BLOCK_CODES[i]))
			{
				blockCode.setSelectedIndex(i);
				break;
			}
		}			
						
		for (int i = 0; i < ODD_NUMBERS.length; ++i)
		{
			if (c.getNumberOfTargets() == Integer.parseInt(ODD_NUMBERS[i]))
			{
				numberOfTrials.setSelectedIndex(i);
				break;
			}
		}
		aField.setText(c.getAString());
		wField.setText(c.getWString());
		randomizeCheckBox.setSelected(c.getRandomize());
		beepOnErrorCheckBox.setSelected(c.getBeepOnError());
		buttonDownCheckBox.setSelected(c.getButtonDownHighlight());
		mouseOverCheckBox.setSelected(c.getMouseOverHighlight());
		errorField.setText("" + c.getErrorThreshold());
		hysteresisField.setText("" + c.getHysteresis());
		backgroundColorButton.setBackground(c.getBackgroundColor());
		foregroundColorButton.setBackground(c.getForegroundColor());
		targetColorButton.setBackground(c.getTargetColor());
		buttonDownColorButton.setBackground(c.getButtonDownColor());
		mouseOverColorButton.setBackground(c.getMouseOverColor());
	}

	private void saveToFile()
	{
		PrintWriter out;
		try
		{
			out = new PrintWriter(new BufferedWriter(new FileWriter(c.getFilename())));
			out.print(c.toString());
			out.flush();
			out.close();
		} catch (IOException ioe)
		{
			JOptionPane.showMessageDialog(null, "Unable to save config to " + c.getFilename()
					+ ".\nPlease ensure the file is writable.", "I/O Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean showFittsTaskTwoSetup(Frame f)
	{
		this.setLocationRelativeTo(f);
		this.setVisible(true);
		return true;
	}
}