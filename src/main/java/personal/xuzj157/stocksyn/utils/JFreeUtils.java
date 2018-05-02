package personal.xuzj157.stocksyn.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
        dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14));         //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));  //垂直标题
        ValueAxis rangeAxis = xyplot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字体

        return jfreechart;
    }

    public static void save(JFreeChart jFreeChart) throws IOException {
        BufferedImage bufferedImage = jFreeChart.createBufferedImage(4000, 2000);
        File outputfile = new File(UUID.randomUUID().toString().replace("-", "") + ".jpeg");
        ImageIO.write(bufferedImage, "jpg", outputfile);
    }
}