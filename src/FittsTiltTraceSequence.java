
public class FittsTiltTraceSequence
{
	int sequenceIdx;
	double a, w;
	FittsTiltTraceTrial[] tiltTrial;
	double meanTRE, meanTAC, meanMDC, meanODC, meanMV, meanME, meanMO;
	double meanPT, meanST, meanMT, meanMaxTilt;
	
	FittsTiltTraceSequence(int sequenceIdxArg, double aArg, double wArg, FittsTiltTraceTrial[] tiltTrialArg)
	{
		sequenceIdx = sequenceIdxArg;
		a = aArg;
		w = wArg;
		tiltTrial = tiltTrialArg;
		
		// compute means for accuracy measures
		meanTRE = meanTAC = meanMDC = meanODC = meanMV = meanME = meanMO = 0.0;
		
		// compute means for other measures
		meanPT = meanST = meanMT = meanMaxTilt = 0.0;
		
		for (int i = 0; i < tiltTrial.length; ++i)
		{
			meanTRE += tiltTrial[i].tre;
			meanTAC += tiltTrial[i].tac;
			meanMDC += tiltTrial[i].mdc;
			meanODC += tiltTrial[i].odc;
			meanMV += tiltTrial[i].mv;
			meanME += tiltTrial[i].me;
			meanMO += tiltTrial[i].mo;	
			meanPT += tiltTrial[i].positioningTime;	
			meanST += tiltTrial[i].selectionTime;	
			meanMT += tiltTrial[i].movementTime;	
			meanMaxTilt += tiltTrial[i].maxTilt;	
		}		
		meanTRE /= tiltTrial.length;
		meanTAC /= tiltTrial.length;
		meanMDC /= tiltTrial.length;
		meanODC /= tiltTrial.length;
		meanMV /= tiltTrial.length;
		meanME /= tiltTrial.length;
		meanMO /= tiltTrial.length;
		meanPT /= tiltTrial.length;
		meanST /= tiltTrial.length;
		meanMT /= tiltTrial.length;
		meanMaxTilt /= tiltTrial.length;
		
	}
}
