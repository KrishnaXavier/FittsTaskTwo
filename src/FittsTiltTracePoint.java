
class FittsTiltTracePoint
{
	double x, y;
	int timeStamp;
	double tilt;
	FittsTiltTracePoint(double xArg, double yArg, int tArg, double tiltArg)
	{
		x = xArg;
		y = yArg;
		timeStamp = tArg;
		tilt = tiltArg;
	}
}
