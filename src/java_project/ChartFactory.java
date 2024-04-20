package java_project;



import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class ChartFactory {
    public static JFreeChart createPieChart(String title, DefaultPieDataset dataset, boolean legend, boolean tooltips, boolean urls) {
        return ChartFactory.createPieChart(
                title,  // chart title
                dataset,             // data
                legend,                
                tooltips,
                urls
        );
    }
}

