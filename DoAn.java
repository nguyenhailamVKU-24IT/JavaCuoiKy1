package DoAnCuoiKy;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class DoAn {
	private Connection connection;
	
	public DoAn() {
		JFrame frame = new JFrame("Quản Lý Vật Liệu Xây Dựng");
		frame.setSize(1250, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(new Color(32, 178, 170));
		leftPanel.setMinimumSize(new Dimension(150, 600));
		leftPanel.setPreferredSize(new Dimension(200, 600));

		JLabel iconLabel = new JLabel(new ImageIcon(DoAn.class.getResource("icon_1nguoi.png")));
		iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftPanel.add(Box.createVerticalStrut(20));
		leftPanel.add(iconLabel);

		JLabel welcomeLabel = new JLabel("Welcome, Admin", JLabel.CENTER);
		welcomeLabel.setForeground(Color.BLACK);
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftPanel.add(welcomeLabel);

		JLabel duongThangJLabel = new JLabel("-----------------------------", JLabel.CENTER);
		duongThangJLabel.setForeground(Color.BLACK);
		duongThangJLabel.setFont(new Font("Arial", Font.BOLD, 16));
		duongThangJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftPanel.add(duongThangJLabel);

		JButton homeButton = new JButton("Trang Chủ");
		homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		homeButton.setMaximumSize(new Dimension(180, 30));
		leftPanel.add(homeButton);

		JButton nhanVienButton = new JButton("Nhân Viên");
		nhanVienButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		nhanVienButton.setMaximumSize(new Dimension(180, 30));
		leftPanel.add(nhanVienButton);

		JButton thongKeButton = new JButton("Thống Kê");
		thongKeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		thongKeButton.setMaximumSize(new Dimension(180, 30));
		leftPanel.add(thongKeButton);

		JButton dangXuatButton = new JButton("Đăng Xuất");
		dangXuatButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		dangXuatButton.setMaximumSize(new Dimension(180, 30));
		leftPanel.add(dangXuatButton);

		dangXuatButton.addActionListener(e -> {
			frame.dispose();
			new DangNhap();
		});

		leftPanel.add(Box.createVerticalGlue());
		leftPanel.add(dangXuatButton);
		leftPanel.add(Box.createVerticalStrut(10));

		JPanel rightJPanel = new JPanel(new CardLayout());
		JPanel tranngchu = new JPanel(new BorderLayout());

		JPanel layoutJPanel = new JPanel(new BorderLayout());
		layoutJPanel.setLayout(new BorderLayout());
		layoutJPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JTextField timKiemField = new JTextField();
		JButton timkiemButton = new JButton("Tìm Kiếm");
		layoutJPanel.add(timKiemField, BorderLayout.CENTER);
		layoutJPanel.add(timkiemButton, BorderLayout.EAST);
		tranngchu.add(layoutJPanel, BorderLayout.NORTH);

		DefaultTableModel bangModel = new DefaultTableModel();
		JTable table = new JTable(bangModel);
		bangModel.addColumn("Mã Sản Phẩm");
		bangModel.addColumn("Tên Sản Phẩm");
		bangModel.addColumn("Đơn Vị Tính");
		bangModel.addColumn("Số Lượng Nhập");
		bangModel.addColumn("Số Lượng Xuất");
		bangModel.addColumn("Giá Nhập");
		bangModel.addColumn("Giá Bán");
		bangModel.addColumn("Tồn Kho");
		JScrollPane jScrollPane = new JScrollPane(table);
		tranngchu.add(jScrollPane, BorderLayout.CENTER);

		timkiemButton.addActionListener(e -> {
			String tuKhoa = timKiemField.getText().trim();
			if (tuKhoa.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập từ khoá dể tìm kiếm!", "Lỗi",
						JOptionPane.WARNING_MESSAGE);
			} else {
				capNhapBang(tuKhoa, bangModel, table);
			}
		});

		timKiemField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				if (timKiemField.getText().trim().isEmpty()) {
					table.setModel(bangModel);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
		 });

		JPanel formJPanel = new JPanel();
		formJPanel.setLayout(new GridLayout(5, 4, 10, 10));
		formJPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		formJPanel.add(new JLabel("Mã Sản Phẩm:"));
		JTextField maspField = new JTextField();
		formJPanel.add(maspField);

		formJPanel.add(new JLabel("Tên Sản Phẩm:"));
		JTextField tenspField = new JTextField();
		formJPanel.add(tenspField);

		formJPanel.add(new JLabel("Đơn Vị Tính"));
		String donViTinh[] = { "Gam: g", "Kilogam: Kg", "Mét: m", "Thùng", "Bao" };
		JComboBox donViTinhComboBox = new JComboBox(donViTinh);
		formJPanel.add(donViTinhComboBox);

		formJPanel.add(new JLabel("Số Lượng Nhập:"));
		JTextField soLuongNhapField = new JTextField();
		formJPanel.add(soLuongNhapField);

		formJPanel.add(new JLabel("Số Lượng Xuất:"));
		JTextField soLuongXuatField = new JTextField();
		formJPanel.add(soLuongXuatField);

		formJPanel.add(new JLabel("Giá Nhập:"));
		JTextField giaNhapField = new JTextField();
		formJPanel.add(giaNhapField);

		formJPanel.add(new JLabel("Giá Bán:"));
		JTextField giaBanField = new JTextField();
		formJPanel.add(giaBanField);

		formJPanel.add(new JLabel("Tồn Kho:"));
		JTextField tonKhoField = new JTextField();
		formJPanel.add(tonKhoField);

		JButton themButton = new JButton("Thêm");
		formJPanel.add(themButton);

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
		taiDuLieu(bangModel);

		themButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String maSP = maspField.getText().trim();
				String tenSP = tenspField.getText().trim();
				String donVT = (String) donViTinhComboBox.getSelectedItem();
				String soLuongNhap = soLuongNhapField.getText().trim();
				String soLuongXuat = soLuongXuatField.getText().trim();
				String giaNhap = giaNhapField.getText().trim();
				String giaBan = giaBanField.getText().trim();
				String tonKho = tonKhoField.getText().trim();

				if (tenSP.matches(".*[^\\p{L}\\p{N}\\s].*")) {
					JOptionPane.showMessageDialog(null, "Tên sản phẩm không được chứa ký tự đặc biệt!", "Lỗi",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (maSP.isEmpty() || tenSP.isEmpty() || soLuongNhap.isEmpty() || soLuongXuat.isEmpty()
						|| giaNhap.isEmpty() || giaBan.isEmpty() || tonKho.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					String kiemTraMaSQL = "SELECT COUNT(*) FROM trangchu WHERE maSP = ?";
					PreparedStatement kiemTraStmt = connection.prepareStatement(kiemTraMaSQL);
					kiemTraStmt.setString(1, maSP);
					ResultSet rs = kiemTraStmt.executeQuery();

					if (rs.next() && rs.getInt(1) > 0) {
						JOptionPane.showMessageDialog(null, "Mã sản phẩm đã tồn tại!", "Lỗi",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					int slNhap = Integer.parseInt(soLuongNhap);
					int slXuat = Integer.parseInt(soLuongXuat);
					double giaNhapDouble = Double.parseDouble(giaNhap);
					double giaBanDouble = Double.parseDouble(giaBan);
					int tonKhoInt = Integer.parseInt(tonKho);

					String themvaoSQl = "INSERT INTO trangchu (maSP, tenSP, donVT, soLuongNhap, soLuongXuat, giaNhap, giaBan, tonKho) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement stms = connection.prepareStatement(themvaoSQl);
					stms.setString(1, maSP);
					stms.setString(2, tenSP);
					stms.setString(3, donVT);
					stms.setInt(4, slNhap);
					stms.setInt(5, slXuat);
					stms.setDouble(6, giaNhapDouble);
					stms.setDouble(7, giaBanDouble);
					stms.setInt(8, tonKhoInt);

					int hangThem = stms.executeUpdate();

					if (hangThem > 0) {
						JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công!", "Thông Báo",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Không thêm được sản phẩm!", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					}

					bangModel.addRow(new Object[] { maSP, tenSP, donVT, slNhap, slXuat, giaNhapDouble, giaBanDouble,
							tonKhoInt });

				} catch (NumberFormatException ex) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null,
							"Các trường số lượng, giá nhập, giá bán, và tồn kho phải là số!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				} catch (SQLException ex) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Lỗi khi thêm vào cơ sở dữ liệu: " + ex.getMessage(), "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton xoaButton = new JButton("Xoá");
		formJPanel.add(xoaButton);

		xoaButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int hangDachon = table.getSelectedRow();
				if (hangDachon == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để xoá!", "Lỗi",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				String maSP = table.getValueAt(hangDachon, 0).toString();
				int xacNhan = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xoá sản phầm này không?",
						"Xác Nhận", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (xacNhan == JOptionPane.YES_NO_OPTION) {
					try {
						String xoaSQL = "DELETE FROM trangchu WHERE maSP = ?";
						PreparedStatement stmt = connection.prepareStatement(xoaSQL);
						stmt.setString(1, maSP);

						int hangXoa = stmt.executeUpdate();

						if (hangXoa > 0) {
							bangModel.removeRow(hangDachon);
							JOptionPane.showMessageDialog(null, "Xoá sản phẩm thành công!", "Thông Báo",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showConfirmDialog(null, "Không thể xoá sản phẩm!", "Lỗi",
									JOptionPane.WARNING_MESSAGE);
						}
					} catch (SQLException ex) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "Lỗi khi xoá sản phẩm ra khỏi CSDL: " + ex.getMessage(),
								"Lỗi", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JButton capNhapButton = new JButton("Cập Nhập");
		formJPanel.add(capNhapButton);

		capNhapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int hangDaChon = table.getSelectedRow();
				if (hangDaChon == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần cập nhập!", "Lỗi",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				String maSP = maspField.getText();
				String tenSP = tenspField.getText();
				String donViTinh = (String) donViTinhComboBox.getSelectedItem();
				String soLuongNhap = soLuongNhapField.getText();
				String soLuongxuat = soLuongXuatField.getText();
				String giaNhap = giaNhapField.getText();
				String giaBan = giaBanField.getText();
				String tonKho = tonKhoField.getText();

				if (tenSP.matches(".*[^\\p{L}\\p{N}\\s].*")) {
					JOptionPane.showMessageDialog(null, "Tên sản phẩm không được chứa ký tự đặc biệt!", "Lỗi",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (maSP.isEmpty() || tenSP.isEmpty() || donViTinh.isEmpty() || soLuongNhap.isEmpty()
						|| soLuongxuat.isEmpty() || giaNhap.isEmpty() || giaBan.isEmpty() || tonKho.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đủ thông tin!", "Lỗi",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					int slNhap = Integer.parseInt(soLuongNhap);
					int slXuat = Integer.parseInt(soLuongxuat);
					Double giaNhaPDouble = Double.parseDouble(giaNhap);
					Double giaBanDouble = Double.parseDouble(giaBan);
					int tonKhoInt = Integer.parseInt(tonKho);

					String capNhapSQL = "UPDATE trangchu SET tenSP = ?, donvT = ?, soLuongNhap = ?, soLuongXuat = ?, "
							+ "giaNhap = ?, giaBan = ?, tonKho = ? WHERE maSP = ?";
					PreparedStatement stmt = connection.prepareStatement(capNhapSQL);

					stmt.setString(1, tenSP);
					stmt.setString(2, donViTinh);
					stmt.setInt(3, slNhap);
					stmt.setInt(4, slXuat);
					stmt.setDouble(5, giaNhaPDouble);
					stmt.setDouble(6, giaBanDouble);
					stmt.setInt(7, tonKhoInt);
					stmt.setString(8, maSP);

					int hangCapNhap = stmt.executeUpdate();

					if (hangCapNhap > 0) {

						bangModel.setValueAt(maSP, hangDaChon, 0);
						bangModel.setValueAt(tenSP, hangDaChon, 1);
						bangModel.setValueAt(donViTinh, hangDaChon, 2);
						bangModel.setValueAt(soLuongNhap, hangDaChon, 3);
						bangModel.setValueAt(soLuongxuat, hangDaChon, 4);
						bangModel.setValueAt(giaNhap, hangDaChon, 5);
						bangModel.setValueAt(giaBan, hangDaChon, 6);
						bangModel.setValueAt(tonKho, hangDaChon, 7);

						JOptionPane.showMessageDialog(null, "Cập nhập sản phẩm thành công", "Thông Báo",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Không thể cập nhập sản phẩm!", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException ex) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null,
							"Các trường số lượng, giá nhập, giá bán, và tồn kho phải là số!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				} catch (SQLException ex) {
					// TODO: handle exception
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Lỗi khi cập nhập vào cơ sở dữ liệu: " + ex.getMessage(), "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton lamMoiButton = new JButton("Làm Mới");
		formJPanel.add(lamMoiButton);

		lamMoiButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				maspField.setText("");
				tenspField.setText("");
				donViTinhComboBox.setSelectedIndex(0);
				soLuongNhapField.setText("");
				soLuongXuatField.setText("");
				giaNhapField.setText("");
				giaBanField.setText("");
				tonKhoField.setText("");
				JOptionPane.showMessageDialog(null, "Đã làm mới các trường dữ liệu!", "Thông Báo",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		tranngchu.add(formJPanel, BorderLayout.SOUTH);

		rightJPanel.add(tranngchu, "Trang Chủ");

		NhanVien nhanVien = new NhanVien();
		rightJPanel.add(nhanVien.nhanVien(), "Nhân Viên");

		BieuDo bieuDo = new BieuDo();
		rightJPanel.add(bieuDo.bieuDo(), "Thống Kê");

		CardLayout cardLayout = (CardLayout) rightJPanel.getLayout();

		homeButton.addActionListener(e -> cardLayout.show(rightJPanel, "Trang Chủ"));
		nhanVienButton.addActionListener(e -> cardLayout.show(rightJPanel, "Nhân Viên"));
		thongKeButton.addActionListener(e -> cardLayout.show(rightJPanel, "Thống Kê"));

		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightJPanel);
		jSplitPane.setDividerLocation(200);
		jSplitPane.setOneTouchExpandable(true);
		jSplitPane.setDividerSize(8);

		frame.add(jSplitPane, BorderLayout.CENTER);

		frame.setVisible(true);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int hang = table.rowAtPoint(evt.getPoint());
				if (hang >= 0) {
					String maSP = table.getValueAt(hang, 0).toString();
					String tenSP = table.getValueAt(hang, 1).toString();
					String donVT = table.getValueAt(hang, 2).toString();
					String soLuongNhap = table.getValueAt(hang, 3).toString();
					String soLuongXuat = table.getValueAt(hang, 4).toString();
					String giaNhap = table.getValueAt(hang, 5).toString();
					String giaBan = table.getValueAt(hang, 6).toString();
					String tonKho = table.getValueAt(hang, 7).toString();

					maspField.setText(maSP);
					tenspField.setText(tenSP);
					donViTinhComboBox.setSelectedItem(donVT);
					soLuongNhapField.setText(soLuongNhap);
					soLuongXuatField.setText(soLuongXuat);
					giaNhapField.setText(giaNhap);
					giaBanField.setText(giaBan);
					tonKhoField.setText(tonKho);
				}
			}
		});

	}
	private void capNhapBang(String tuKhoa, DefaultTableModel bangModel, JTable table) {
        DefaultTableModel daDuocLoc = new DefaultTableModel();
        daDuocLoc.addColumn("Mã Sản Phẩm");
        daDuocLoc.addColumn("Tên Sản Phẩm");
        daDuocLoc.addColumn("Đơn Vị Tính");
        daDuocLoc.addColumn("Số Lượng Nhập");
        daDuocLoc.addColumn("Số Lượng Xuất");
        daDuocLoc.addColumn("Giá Nhập");
        daDuocLoc.addColumn("Giá Bán");
        daDuocLoc.addColumn("Tồn Kho");

        for (int i = 0; i < bangModel.getRowCount(); i++) {
            String maSP = bangModel.getValueAt(i, 0).toString().toLowerCase();
            String tenSP = bangModel.getValueAt(i, 1).toString().toLowerCase();

            if (maSP.contains(tuKhoa.toLowerCase()) || tenSP.contains(tuKhoa.toLowerCase())) {
                daDuocLoc.addRow(new Object[]{
                        bangModel.getValueAt(i, 0), bangModel.getValueAt(i, 1),
                        bangModel.getValueAt(i, 2), bangModel.getValueAt(i, 3),
                        bangModel.getValueAt(i, 4), bangModel.getValueAt(i, 5),
                        bangModel.getValueAt(i, 6), bangModel.getValueAt(i, 7)
                });
            }
        }
        table.setModel(daDuocLoc);
    }

	private void taiDuLieu(DefaultTableModel model) {
		try {
			String query = "SELECT * FROM trangchu";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				String maSP = resultSet.getString("maSP");
				String tenSP = resultSet.getString("tenSP");
				String donVT = resultSet.getString("donVT");
				int soLuongNhap = resultSet.getInt("soLuongNhap");
				int soLuongXuat = resultSet.getInt("soLuongXuat");
				double giaNhap = resultSet.getDouble("giaNhap");
				double giaBan = resultSet.getDouble("giaBan");
				int tonKho = resultSet.getInt("tonKho");

				model.addRow(new Object[] { maSP, tenSP, donVT, soLuongNhap, soLuongXuat, giaNhap, giaBan, tonKho });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
