import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class DataGraphBean extends JPanel {
    private double[] xData;
    private double[] yData;
    private final int padding = 50; // Поля по краях графіку

    public void setXYData(double[] xData, double[] yData) {
        this.xData = xData;
        this.yData = yData;
        repaint(); // Перемалювати графік при оновленні даних
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // Малюємо координатну пряму
        g2d.setStroke(new BasicStroke(3)); // Товщина лінії

        // Горизонтальна вісь (ось x)
        int xAxisY = height / 2; // Координата y для відображення осі x у центрі
        g2d.drawLine(padding, xAxisY, width - padding, xAxisY);

        // Вертикальна вісь (ось y)
        int yAxisX = width / 2; // Координата x для відображення осі y у центрі
        g2d.drawLine(yAxisX, padding, yAxisX, height - padding);

        // Малюємо маркери на осі x (додатні та від'ємні значення)
        for (int i = -10; i <= 10; i++) {
            int x = (int) ((i + 10) * (width - 2 * padding) / 20.0 + padding);
            g2d.drawLine(x, xAxisY - 3, x, xAxisY + 3);
            String label = String.valueOf(i);
            g2d.drawString(label, x - g2d.getFontMetrics().stringWidth(label) / 2, xAxisY + 20);
        }

        // Малюємо маркери на осі y
        for (int i = -10; i <= 10; i++) {
            int y = (int) ((10 - i) * (height - 2 * padding) / 20.0 + padding);
            g2d.drawLine(yAxisX - 3, y, yAxisX + 3, y);
            String label = String.valueOf(i);
            g2d.drawString(label, yAxisX - 25, y + 5);
        }

        // Малюємо точки на графіку
        if (xData != null && yData != null) {
            int numPoints = Math.min(xData.length, yData.length);
            g2d.setColor(Color.RED);
            for (int i = 0; i < numPoints; i++) {
                int x = (int) ((xData[i] + 10) * (width - 2 * padding) / 20.0 + padding); // Масштабуємо значення x
                if (xData[i] < 0) {
                    x = (int) ((10 + xData[i]) * (width - 2 * padding) / 20.0 + padding); // Відображаємо від'ємні значення x
                }
                int y = (int) ((10 - yData[i]) * (height - 2 * padding) / 20.0 + padding); // Масштабуємо значення y
                Shape point = new Ellipse2D.Double(x - 2, y - 2, 4, 4); // Точка
                g2d.fill(point);
            }
        }

    }
}
