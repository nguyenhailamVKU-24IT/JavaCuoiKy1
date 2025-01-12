package DoAnCuoiKy;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class BieuDo {
    private Connection connection;
    private ChartPanel currentChartPanel;

    public BieuDo() {
        String url = "jdbc:mysql://localhost:3306/quanLyVatLieuXayDung";
        String user = "root";
        String password = "Hailam123@";

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối thành công!");
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public JPanel bieuDo() {
        JPanel bieuDoPanel = new JPanel(new BorderLayout());

        // Tạo biểu đồ ban đầu
        currentChartPanel = taoBieuDo();

        // Thêm biểu đồ vào giao diện
        bieuDoPanel.add(currentChartPanel, BorderLayout.CENTER);

        // Thêm nút cập nhật
        JButton capNhatButton = new JButton("Cập nhật dữ liệu");
        capNhatButton.addActionListener(e -> {
            // Cập nhật lại biểu đồ
            bieuDoPanel.remove(currentChartPanel);
            currentChartPanel = taoBieuDo();
            bieuDoPanel.add(currentChartPanel, BorderLayout.CENTER);
            bieuDoPanel.revalidate();
            bieuDoPanel.repaint();
        });
        bieuDoPanel.add(capNhatButton, BorderLayout.SOUTH);

        return bieuDoPanel;
    }

    private ChartPanel taoBieuDo() {
        DefaultCategoryDataset duLieu = new DefaultCategoryDataset();

        try {
            String bieuDo = "SELECT tenSP, soLuongNhap, soLuongXuat, tonKho FROM trangchu";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(bieuDo);

            while (resultSet.next()) {
                String tenSP = resultSet.getString("tenSP");
                int soLuongNhap = resultSet.getInt("soLuongNhap");
                int soLuongXuat = resultSet.getInt("soLuongXuat");
                int tonKho = resultSet.getInt("tonKho");

                duLieu.addValue(soLuongNhap, "Số Lượng Nhập", tenSP);
                duLieu.addValue(soLuongXuat, "Số Lượng Xuất", tenSP);
                duLieu.addValue(tonKho, "Tồn Kho", tenSP);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }

        JFreeChart bieuDoCot = ChartFactory.createBarChart(
            "Biểu Đồ Thống Kê Sản Phẩm",
            "Tên Sản Phẩm",
            "Số Lượng",
            duLieu
        );
        return new ChartPanel(bieuDoCot);
    }
}