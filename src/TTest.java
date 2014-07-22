import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.FastMath;

public class TTest {

	private double[] toPrimitive(Double[] DoublerArray) {

		double[] result = new double[DoublerArray.length];
		for (int i = 0; i < DoublerArray.length; i++) {
			result[i] = DoublerArray[i].doubleValue();
		}
		return result;
	}

	public ArrayList<Double> runTTest(Vector<Double> vector1,
			Vector<Double> vector2) {

		ArrayList<Double> returnList = new ArrayList<Double>();

		ArrayList<Double> arrayList1 = new ArrayList<Double>(vector1);
		ArrayList<Double> arrayList2 = new ArrayList<Double>(vector2);

		double sum1 = 0;
		double mean1 = 0;
		for (int i = 0; i < arrayList1.size(); i++) {
			sum1 += arrayList1.get(i);
		}
		mean1 = sum1 / arrayList1.size();

		double sum2 = 0;
		double mean2 = 0;
		for (int i = 0; i < arrayList2.size(); i++) {
			sum2 += arrayList2.get(i);
		}
		mean2 = sum2 / arrayList2.size();

		Double[] sample1BeforePrimitive = vector1.toArray(new Double[vector1
				.size()]);
		Double[] sample2BeforePrimitive = vector2.toArray(new Double[vector2
				.size()]);
		double[] sampleMP1 = toPrimitive(sample1BeforePrimitive);
		double[] sampleMP2 = toPrimitive(sample2BeforePrimitive);

		double d = tTest(sampleMP1, sampleMP2);
		DecimalFormat df = new DecimalFormat("#.###");

		if (Double.parseDouble(df.format(d)) < 0.05) {
			returnList.add(1.0);
			returnList.add(mean1);
			returnList.add(mean2);
			// System.out.println("significantly different!");
		} else {
			returnList.add(0.0);
		}
		// else System.out.println("not significantly different");

		return returnList;

	}

	public double tTest(final double[] sample1, final double[] sample2)
			throws NullArgumentException, NumberIsTooSmallException,
			MaxCountExceededException {

		checkSampleData(sample1);
		checkSampleData(sample2);
		// No try-catch or advertised exception because args have just been
		// checked
		return tTest(StatUtils.mean(sample1), StatUtils.mean(sample2),
				StatUtils.variance(sample1), StatUtils.variance(sample2),
				sample1.length, sample2.length);

	}

	private void checkSampleData(final double[] data)
			throws NullArgumentException, NumberIsTooSmallException {

		if (data == null) {
			throw new NullArgumentException();
		}
		if (data.length < 2) {
			throw new NumberIsTooSmallException(
					LocalizedFormats.INSUFFICIENT_DATA_FOR_T_STATISTIC,
					data.length, 2, true);
		}

	}

	protected double tTest(final double m1, final double m2, final double v1,
			final double v2, final double n1, final double n2)
			throws MaxCountExceededException, NotStrictlyPositiveException {

		final double t = FastMath.abs(t(m1, m2, v1, v2, n1, n2));
		final double degreesOfFreedom = df(v1, v2, n1, n2);
		TDistribution distribution = new TDistribution(degreesOfFreedom);
		return 2.0 * distribution.cumulativeProbability(-t);

	}

	protected double t(final double m1, final double m2, final double v1,
			final double v2, final double n1, final double n2) {
		return (m1 - m2) / FastMath.sqrt((v1 / n1) + (v2 / n2));
	}

	protected double df(double v1, double v2, double n1, double n2) {
		return (((v1 / n1) + (v2 / n2)) * ((v1 / n1) + (v2 / n2)))
				/ ((v1 * v1) / (n1 * n1 * (n1 - 1d)) + (v2 * v2)
						/ (n2 * n2 * (n2 - 1d)));
	}

}
