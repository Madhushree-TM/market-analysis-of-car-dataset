import java.awt.*;
import java.awt.event.*;

public class BarChartExample extends Frame {

    public BarChartExample() {
        // Set up the Frame
        super("AWT Bar Chart Example");
        setSize(800, 600);
        setVisible(true);

        // Add WindowListener to handle window close operation
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // Override the paint method to draw the bar chart
    @Override
    public void paint(Graphics g) {
        // Sample data
        int[] values = {70, 40, 90, 60, 100};
        String[] labels = {"A", "B", "C", "D", "E"};

        // Set up the chart properties
        int barWidth = 50;
        int gap = 20;
        int maxValue = 100;
        int chartHeight = 400;
        int chartWidth = values.length * (barWidth + gap) + gap;

        // Draw axes
        g.drawLine(50, 50, 50, chartHeight + 50); // Y-axis
        g.drawLine(50, chartHeight + 50, chartWidth + 50, chartHeight + 50); // X-axis

        // Draw bars
        for (int i = 0; i < values.length; i++) {
            int barHeight = (int) (((double) values[i] / maxValue) * chartHeight);
            int x = 50 + (i * (barWidth + gap)) + gap;
            int y = chartHeight + 50 - barHeight;
            g.setColor(Color.BLUE);
            g.fillRect(x, y, barWidth, barHeight);

            // Draw value above the bar
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(values[i]), x + (barWidth / 2) - 5, y - 5);

            // Draw label below the bar
            g.drawString(labels[i], x + (barWidth / 2) - 5, chartHeight + 70);
        }
    }

    public static void main(String[] args) {
        new BarChartExample();
    }
}

