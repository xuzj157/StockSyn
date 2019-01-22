package personal.xuzj157.stocksyn.utils.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class LineChartUtils {

    public static void allInOne(Map<String, Map<Double, Integer>> map,
                                String title, String xTitle, String yTitle,
                                int width, int height) {
        try {

            JFreeChart chart = ChartFactory.createLineChart(
                    title, xTitle, yTitle, ChartUtils.categoryDataset(map));
            ChartUtilities.saveChartAsJPEG(
                    new File("img/"+title + ".jpeg"), //文件保存物理路径包括路径和文件名
                    1.0f,    //图片质量 ，0.0f~1.0f
                    chart, //图表对象
                    width,   //图像宽度 ，这个将决定图表的横坐标值是否能完全显示还是显示省略号
                    height);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main( String[ ] args )throws Exception {
        final XYSeries firefox = new XYSeries( "Firefox" );
        firefox.add( 1.0 , 1.0 );
        firefox.add( 2.0 , 4.0 );
        firefox.add( 3.0 , 3.0 );

        final XYSeries chrome = new XYSeries( "Chrome" );
        chrome.add( 1.0 , 4.0 );
        chrome.add( 2.0 , 5.0 );
        chrome.add( 3.0 , 6.0 );

        final XYSeries iexplorer = new XYSeries( "InternetExplorer" );
        iexplorer.add( 3.0 , 4.0 );
        iexplorer.add( 4.0 , 5.0 );
        iexplorer.add( 5.0 , 4.0 );

        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( firefox );
        dataset.addSeries( chrome );
        dataset.addSeries( iexplorer );

        JFreeChart xylineChart = ChartFactory.createScatterPlot(
                "Browser usage statastics",
                "Category",
                "Score",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        int width = 640;   /* Width of the image */
        int height = 480;  /* Height of the image */
        File XYChart = new File( "D:/XYLineChart.jpeg" );
        ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
    }
}
