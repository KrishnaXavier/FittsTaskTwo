import java.awt.Color;

// -------------------------
// C O N F I G U R A T I O N
// -------------------------

class FittsTaskTwoConfiguration
{
	String filename, participantCode, conditionCode, blockCode;
	int numberOfTargets, errorThreshold;
	int[] a, w;
	boolean randomize, beepOnError, buttonDownHighlight, mouseOverHighlight;
	Color backgroundColor, foregroundColor, targetColor, buttonDownColor, mouseOverColor;
	double hysteresis;

	FittsTaskTwoConfiguration(String participantCodeArg, String conditionCodeArg, String blockCodeArg, int numberOfTargetsArg, int[] aArg, int[] wArg,
			boolean randomizeArg, boolean beepOnErrorArg, boolean buttonDownHighlightArg,
			boolean mouseOverHighlightArg, int errorThresholdArg, double hysteresisArg, Color backgroundColorArg,
			Color foregroundColorArg, Color targetColorArg, Color buttonDownColorArg, Color mouseOverColorArg)
	{
		participantCode = participantCodeArg;
		conditionCode = conditionCodeArg;
		blockCode = blockCodeArg;		
		numberOfTargets = numberOfTargetsArg;
		a = aArg;
		w = wArg;
		randomize = randomizeArg;
		beepOnError = beepOnErrorArg;
		buttonDownHighlight = buttonDownHighlightArg;
		mouseOverHighlight = mouseOverHighlightArg;
		errorThreshold = errorThresholdArg;
		hysteresis = hysteresisArg;
		backgroundColor = backgroundColorArg;
		foregroundColor = foregroundColorArg;
		targetColor = targetColorArg;
		buttonDownColor = buttonDownColorArg;
		mouseOverColor = mouseOverColorArg;
	}
	
	public void setFilename(String filenameArg)
	{
		filename = filenameArg;
	}

	public void setParticipantCode(String participantCodeArg)
	{
		participantCode = participantCodeArg;
	}

	public void setConditionCode(String conditionCodeArg)
	{
		conditionCode = conditionCodeArg;
	}

	public void setBlockCode(String blockCodeArg)
	{
		blockCode = blockCodeArg;
	}

	public void setNumberOfTrials(int n)
	{
		numberOfTargets = n;
	}

	public void setA(int[] aArg)
	{
		a = aArg;
	}

	public void setW(int[] wArg)
	{
		w = wArg;
	}

	public void setRandomize(boolean b)
	{
		randomize = b;
	}

	public void setBeepOnError(boolean b)
	{
		beepOnError = b;
	}

	public void setButtonDownHighlight(boolean b)
	{
		buttonDownHighlight = b;
	}

	public void setMouseOverHighlight(boolean b)
	{
		mouseOverHighlight = b;
	}

	public void setErrorThreshold(int n)
	{
		errorThreshold = n;
	}

	public void setHysteresis(double d)
	{
		hysteresis = d;
	}

	public void setBackgroundColor(Color c)
	{
		backgroundColor = c;
	}

	public void setForegroundColor(Color c)
	{
		foregroundColor = c;
	}

	public void setTargetColor(Color c)
	{
		targetColor = c;
	}

	public void setButtonDownColor(Color c)
	{
		buttonDownColor = c;
	}

	public void setMouseOverColor(Color c)
	{
		mouseOverColor = c;
	}
	
	public String getFilename()
	{
		return filename;
	}

	public String getParticipantCode()
	{
		return participantCode;
	}

	public String getConditionCode()
	{		
		return conditionCode;
	}

	public String getBlockCode()
	{		
		return blockCode;
	}	

	public int getNumberOfTargets()
	{
		return numberOfTargets;
	}

	public int[] getA()
	{
		return a;
	}

	public int[] getW()
	{
		return w;
	}

	public boolean getRandomize()
	{
		return randomize;
	}

	public boolean getBeepOnError()
	{
		return beepOnError;
	}

	public boolean getButtonDownHighlight()
	{
		return buttonDownHighlight;
	}

	public boolean getMouseOverHighlight()
	{
		return mouseOverHighlight;
	}

	public int getErrorThreshold()
	{
		return errorThreshold;
	}

	public double getHysteresis()
	{
		return hysteresis;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public Color getForegroundColor()
	{
		return foregroundColor;
	}

	public Color getTargetColor()
	{
		return targetColor;
	}

	public Color getButtonDownColor()
	{
		return buttonDownColor;
	}

	public Color getMouseOverColor()
	{
		return mouseOverColor;
	}

	public String getAString()
	{
		String s = "";
		for (int i = 0; i < a.length; ++i)
			s += a[i] + " ";
		s = s.trim();
		return s;
	}

	public String getWString()
	{
		String s = "";
		for (int i = 0; i < w.length; ++i)
			s += w[i] + " ";
		s = s.trim();
		return s;
	}
	
	public String getColorString(Color c)
	{
		return "" + c.getRed() + " " + c.getGreen() + " " + c.getBlue();
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder(4500);
		sb.append("\n");
		sb.append("# ----------------------------------------\n");
		sb.append("# Configuration arguments for FittsTaskTwo\n");
		sb.append("# ----------------------------------------\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# PARTICIPANT CODE (part of output data file names)\n");
		sb.append(getParticipantCode() + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# CONDITION CODE (part of output data file names)\n");
		sb.append("#\n");
		sb.append("# This is an arbitrary code used to identify a particular test condition that\n");
		sb.append("# is associated with the testing.  The condition code (as well as the participant\n");
		sb.append("# code and block code) appears in the filename and in columns in the output data\n");
		sb.append("# files to facilitate analyses.\n");
		sb.append(getConditionCode() + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# BLOCK CODE (part of output data file names)\n");
		sb.append(getBlockCode() + "\n");
		sb.append("\n");		
		sb.append("# -----\n");
		sb.append("# NUMBER OF TARGETS (n) per target condition (use an odd number; e.g., 9 or 17)\n");
		sb.append("#\n");
		sb.append("# The number of trials equals the number of targets.  Because the\n"); 
		sb.append("# the click on the first target begins the sequence, the sequence\n"); 
		sb.append("# continues until the first target is selected again.\n");
		sb.append("#\n");
		sb.append("# An odd number is recommended since this ensures that the movement amplitude is\n");
		sb.append("# the same for each trial.  If an even number is used, a movement to a target\n");
		sb.append("# directly across the layout circle spans the specified movement amplitude.\n");
		sb.append("# Since the following movement is to the target beside the previous \"from\"\n");
		sb.append("# target, the movement amplitude for this trial (and every 2nd trial) is less\n");
		sb.append("# than the specified movement amplitude.\n");
		sb.append("#\n");
		sb.append("# If an odd number is used the actual movement amplitude is equal for each trial\n");
		sb.append("# but is still a bit less than the specified movement amplitude (which equals the\n"); 
		sb.append("# diameter of the layout circle.\n");
		sb.append(getNumberOfTargets() + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# TARGET AMPLITUDES (space delimited)\n");
		sb.append("#\n");
		sb.append("# The amplitude is the diameter of the layout circle.  It is the \"nominal\"\n"); 
		sb.append("# distance the cursor must travel for each trial, but see comments above.\n");
		sb.append("#\n");
		sb.append("# The total number of A-W conditions (sequences) will be n x m, where n is the\n");
		sb.append("# number of target amplitudes and m is the number of target widths.\n");
		sb.append(getAString() + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# TARGET WIDTHS (space delimited)\n");
		sb.append(getWString() + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# RANDOMIZE TARGET CONDITIONS (yes,no)\n");
		sb.append("#\n");
		sb.append("# If \"yes\", a random-without-replacement algorithm is used to sequence the\n"); 
		sb.append("# A-W conditions.\n");  
		sb.append("#\n");
		sb.append("# The order that the A-W conditions appear in the sd2 output data file is the\n"); 
		sb.append("# order in which the conditions appeared during testing (regardless of\n"); 
		sb.append("# whether this argument is \"yes\" or \"no\").\n");
		sb.append((getRandomize() ? "yes" : "no") + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# BEEP ON ERROR (yes,no)\n");
		sb.append((getBeepOnError() ? "yes" : "no") + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# BUTTON-DOWN HIGHLIGHT (yes,no).\n");
		sb.append("#\n");
		sb.append("# If \"yes\", the target color changes to the specified \"button-down\" colour\n");
		sb.append("# when the mouse button is down and the pointer is over the target.\n");
		sb.append((getButtonDownHighlight() ? "yes" : "no") + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# MOUSE-OVER HIGHLIGHT (yes,no).\n");  
		sb.append("#\n");
		sb.append("# If \"yes\", the target color changes to the specified \"highlight\" colour\n"); 
		sb.append("# on \"mouse over\" (see below).  It reverts to the \"target\" color if\n"); 
		sb.append("# the tracking symbol exits the target. It might be useful to set this\n"); 
		sb.append("# argument to \"yes\" for input configurations where there is no mouse\n");
		sb.append("# pointer (e.g.,using touch input or eye tracking input).\n");
		sb.append((getMouseOverHighlight() ? "yes" : "no") + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# ERROR RATE THRESHOLD (%).\n");  
		sb.append("#\n");
		sb.append("# A sequence is repeated if the error rate in percent is\n");
		sb.append("# equal to or higher than this integer argument.  Use \"101\" to disable this feature.\n");
		sb.append("#\n");
		sb.append("# The sd2 output data file contains a column (\"SRC\") for the sequence repeat count\n");
		sb.append("# for each A-W condition.\n");
		sb.append(getErrorThreshold() + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# SPATIAL HYSTERESIS (SH).\n"); 
		sb.append("#\n"); 
		sb.append("# This is a scaling factor to create a larger virtual target -- a hysteresis zone --\n");
		sb.append("# to improve target selection.  The mouse pointer is deemed to enter the\n"); 
		sb.append("# target when it enters the \"real target\".  The pointer is deemed to exit\n");
		sb.append("# the target when the pointer exits the hysteresis zone.\n"); 
		sb.append("#\n");
		sb.append("# With SH = 2.0, for example, the hysteresis zone has 2x the diameter\n"); 
		sb.append("# of the real target.  The default value of 1.0 essentially disables this feature.\n");
		sb.append("#\n");
		sb.append("# The idea of spatial hystersis has not been tested experimentally.  It is simply\n");
		sb.append("# an idea that I've had as a method to improve selection for small targets when\n");
		sb.append("# there is space available between the targets.  If anyone is interested in testing\n");
		sb.append("# this idea experimentally, please let me know (Scott MacKenzie, mack@cse.yorku.ca).\n");
		sb.append(getHysteresis() + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# BACKGROUND R G B COLOR (e.g. use \"255 255 255\" for white)\n");
		sb.append(getColorString(getBackgroundColor()) + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# FOREGROUND R G B COLOR\n"); 
		sb.append(getColorString(getForegroundColor()) + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# TARGET R G B COLOR\n"); 
		sb.append(getColorString(getTargetColor()) + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# BUTTON-DOWN R G B COLOR\n"); 
		sb.append(getColorString(getButtonDownColor()) + "\n");
		sb.append("\n");
		sb.append("# -----\n");
		sb.append("# MOUSE-OVER R G B COLOR\n"); 
		sb.append(getColorString(getMouseOverColor()) + "\n");
		sb.append("\n");
		sb.append("# --- end ---\n");
		return sb.toString();
	}
}