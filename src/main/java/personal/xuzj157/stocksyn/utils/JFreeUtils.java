package personal.xuzj157.stocksyn.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class JFreeUtils {

    public static CategoryDataset categoryDataset(Map<String, Map<Double, Integer>> mapOri) {
        DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Map<Double, Integer>> entryMap : mapOri.entrySet()) {
            String name = entryMap.getKey();
            for (Map.Entry<Double, Integer> entry : entryMap.getValue().entrySet()) {
                defaultCategoryDataset.addValue(entry.getValue(), name, String.valueOf(entry.getKey()));
            }
        }
        return defaultCategoryDataset;
    }

    public static JFreeChart createChart(CategoryDataset categorydataset) {
        JFreeChart jfreechart = ChartFactory.createLineChart("随意想一个名字",// 图表标题
                "X", // 主轴标签（x轴）
                "Y",// 范围轴标签（y轴）
                categorydataset, // 数据集
                PlotOrientation.VERTICAL,// 方向
                false, // 是否包含图例
                true, // 提示信息是否显示
                false);

        return jfreechart;
    }

    public static void save(JFreeChart jFreeChart) throws IOException {
//        BufferedImage bufferedImage = jFreeChart.createBufferedImage(4000, 2000);
        File outputfile = new File(UUID.randomUUID().toString().replace("-", "") + ".jpg");
//        ImageIO.write(bufferedImage, "jpg", outputfile);

        ChartUtilities.saveChartAsJPEG(
                outputfile, //文件保存物理路径包括路径和文件名
                1.0f,    //图片质量 ，0.0f~1.0f
                jFreeChart, //图表对象
                1024,   //图像宽度 ，这个将决定图表的横坐标值是否能完全显示还是显示省略号
                768);

    }

    public static void allInOne(Map<String, Map<Double, Integer>> mapOri) throws IOException {
        CategoryDataset set = categoryDataset(mapOri);
        JFreeChart jFreeChart = createChart(set);
        save(jFreeChart);
    }

}