package DoAnCuoiKy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DangNhap {
	private Connection connection;
	public DangNhap() {
		JFrame dangNhapFrame = new JFrame("Quản Lý Vật Liệu Xây Dựng");
		dangNhapFrame.setSize(600, 300);
		dangNhapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dangNhapFrame.setResizable(false);
		dangNhapFrame.setLocationRelativeTo(null);
		
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

		JPanel leftJPanel = new JPanel();
		leftJPanel.setLayout(new BoxLayout(leftJPanel, BoxLayout.Y_AXIS));
		leftJPanel.setBackground(new Color(32, 178, 170));
		leftJPanel.setPreferredSize(new Dimension(200, 300));

		JLabel iconLabel = new JLabel(new ImageIcon(DangNhap.class.getResource("icon_nguoi.png")));
		iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftJPanel.add(iconLabel);

		JLabel chuJLabel = new JLabel("Quản Lý Vật Liệu Xây Dựng");
		chuJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftJPanel.add(chuJLabel);

		dangNhapFrame.add(leftJPanel, BorderLayout.WEST);

		JPanel rightJPanel = new JPanel();
		rightJPanel.setLayout(null);
		
		JLabel taiKhoanJLabel = new JLabel("Tài Khoản:");
		taiKhoanJLabel.setBounds(50, 30, 90, 25);
		rightJPanel.add(taiKhoanJLabel);

		JTextField taiKhoanField = new JTextField();
		taiKhoanField.setBounds(150, 30, 180, 25);
		rightJPanel.add(taiKhoanField);

		JLabel matKhauJLabel = new JLabel("Mật Khẩu:");
		matKhauJLabel.setBounds(50, 72, 80, 25);
		rightJPanel.add(matKhauJLabel);

		JPasswordField matKhauField = new JPasswordField();
		matKhauField.setBounds(150, 70, 180, 25);
		rightJPanel.add(matKhauField);

		JButton dangNhapButton = new JButton("Đăng Nhập");
		dangNhapButton.setBounds(75, 110, 120, 30);
		rightJPanel.add(dangNhapButton);
		
		JButton dangkyButton = new JButton("Đăng Ký");
		dangkyButton.setBounds(190, 110, 120, 30);
		rightJPanel.add(dangkyButton);

		dangNhapFrame.add(rightJPanel);
		
		dangNhapButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String taikhoan = taiKhoanField.getText();
		        String matKhau = new String(matKhauField.getPassword());

		        
		        String query = "SELECT * FROM nguoiDung WHERE BINARY taiKhoan = ? AND BINARY matKhau = ?";
		        
		        try (PreparedStatement pst = connection.prepareStatement(query)) {
		            pst.setString(1, taikhoan);
		            pst.setString(2, matKhau);

		            ResultSet rs = pst.executeQuery();

		            if (rs.next()) {
		                JOptionPane.showMessageDialog(dangNhapFrame, "Đăng Nhập Thành Công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
		                dangNhapFrame.dispose();
		                new DoAn();
		            } else {
		                JOptionPane.showMessageDialog(dangNhapFrame, "Tài Khoản Hoặc Mật Khẩu Sai", "Lỗi", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(dangNhapFrame, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        }
		    }
		});
		
		dangkyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dangNhapFrame.dispose();
				new DangKy();
			}
		});

		dangNhapFrame.setVisible(true);
	}
}
