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

}
