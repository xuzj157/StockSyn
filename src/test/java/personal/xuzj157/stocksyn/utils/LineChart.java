package personal.xuzj157.stocksyn.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import personal.xuzj157.stocksyn.pojo.bo.Serie;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * @author ccw
 * @date 2014-6-11
 * <p>
 * ����ͼ���裺<br/>
 * 1���������ݼ���<br/>
 * 2������Chart��<br/>
 * 3:���ÿ���ݣ���ֹ������ʾ�����<br/>
 * 4:�����ӽ�����Ⱦ��<br/>
 * 5:���������ֽ�����Ⱦ<br/>
 * 6:ʹ��chartPanel����<br/>
 * <p>
 * </p>
 */
public class LineChart {
    public LineChart() {
    }

    public static DefaultCategoryDataset createDataset() {
        String[] categories = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Vector<Serie> series = new Vector<Serie>();
        series.add(new Serie("Tokyo", new Double[]{49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4}));
        series.add(new Serie("New York", new Double[]{83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3}));
        series.add(new Serie("London", new Double[]{48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2}));
        series.add(new Serie("Berlin", new Double[]{42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1}));
        DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);
        return dataset;
    }

    public static JFreeChart createChart() {
        JFreeChart chart = ChartFactory.createLineChart(
                "Monthly Average Rainfall", "", "Rainfall (mm)", createDataset());
        return chart;
    }

    public static void main(String[] args) throws IOException {

        ChartUtilities.saveChartAsJPEG(
                new File("1111.png"), //文件保存物理路径包括路径和文件名
                1.0f,    //图片质量 ，0.0f~1.0f
                createChart(), //图表对象
                1024,   //图像宽度 ，这个将决定图表的横坐标值是否能完全显示还是显示省略号
                768);

    }

}
