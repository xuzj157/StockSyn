package personal.xuzj157.stocksyn.utils.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import personal.xuzj157.stocksyn.pojo.vo.Coordinate;

import java.io.File;
import java.util.*;

/**
 * 散点图
 */
public class ScatterPlotUtils {

    public static void allInOne(Map<String, List<Coordinate>> map,
                                String title, String xTitle, String yTitle,
                                int width, int height) throws Exception {

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        for (Map.Entry<String, List<Coordinate>> entry : map.entrySet()) {
            XYSeries xySeries = new XYSeries(entry.getKey());
            for (Coordinate coordinate : entry.getValue()) {
                xySeries.add(coordinate.getXAxis(), coordinate.getYAxis());
            }
            xySeriesCollection.addSeries(xySeries);
        }
        JFreeChart xylineChart = ChartFactory.createScatterPlot(
                title, xTitle, yTitle,
                xySeriesCollection,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartUtilities.saveChartAsJPEG(new File("img/" + title + ".jpeg"), xylineChart, width, height);

    }

    public static void main(String[] args) throws Exception {
        Map<String, List<Coordinate>> map = new HashMap<>();
        for (int j = 1; j < 4; j++) {
            List<Coordinate> coordinates = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                Coordinate coordinate = new Coordinate();
                coordinate.setXAxis(Math.random() * j);
                coordinate.setYAxis(Math.random() * j);
                coordinates.add(coordinate);
            }
            map.put(String.valueOf(j), coordinates);
        }
        ScatterPlotUtils.allInOne(map, "title", "xtitle", "ytitle", 1000, 600);
    }

}
