package personal.xuzj157.stocksyn.utils.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.RefineryUtilities;

public class ScatterPlot {

    public static void data(String title, String[] a, String[] b) {
        DefaultXYDataset xydataset = new DefaultXYDataset();

        double[][] data = new double[2][a.length];
        for (int i = 0; i < a.length; i++) {
            data[0][i] = Double.parseDouble(a[i]);
            data[1][i] = Double.parseDouble(b[i]);
        }
        xydataset.addSeries("牛的无线定位", data);

        XYTextAnnotation text1 = new XYTextAnnotation("", 2, 2);
        XYTextAnnotation text2 = new XYTextAnnotation("", 4, 4);
        XYTextAnnotation text3 = new XYTextAnnotation("", 7, 5);

        final JFreeChart chart = ChartFactory.createScatterPlot("", "", "", xydataset, PlotOrientation.VERTICAL, false, false, false);

        XYPlot xyplot = (XYPlot) chart.getPlot();
        xyplot.addAnnotation(text1);
        xyplot.addAnnotation(text2);
        xyplot.addAnnotation(text3);

        ChartFrame frame = new ChartFrame(title, chart);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String[] a = {"7", "2", "4"};
        String[] b = {"5", "2", "4"};
        ScatterPlot.data("title", a, b);
    }

}
