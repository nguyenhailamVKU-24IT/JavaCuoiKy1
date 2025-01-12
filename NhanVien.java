package DoAnCuoiKy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class NhanVien {
	private Connection connection;

	public JPanel nhanVien() {
		JPanel nhanVien = new JPanel(new BorderLayout());

		JPanel layoutJPanel = new JPanel(new BorderLayout());
		layoutJPanel.setLayout(new BorderLayout());
		layoutJPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JTextField timKiemField = new JTextField();
		JButton timKiemButton = new JButton("Tìm Kiếm");
		layoutJPanel.add(timKiemField, BorderLayout.CENTER);
		layoutJPanel.add(timKiemButton, BorderLayout.EAST);

		nhanVien.add(layoutJPanel, BorderLayout.NORTH);

		DefaultTableModel bangNhanVien = new DefaultTableModel();
		JTable table = new JTable(bangNhanVien);
		bangNhanVien.addColumn("Mã Nhân Viên");
		bangNhanVien.addColumn("Họ Và Tên");
		bangNhanVien.addColumn("Giới Tính");
		bangNhanVien.addColumn("Ngày Sinh");
		bangNhanVien.addColumn("Số Điện Thoại");
		bangNhanVien.addColumn("Ca Làm");
		JScrollPane jScrollPane = new JScrollPane(table);
		nhanVien.add(jScrollPane, BorderLayout.CENTER);
		
		timKiemButton.addActionListener(e -> {
			String tuKhoa = timKiemField.getText().trim();
			if (tuKhoa.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập từ khoá để tìm kiếm!", "Thông Báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				capNhapBang(tuKhoa, bangNhanVien, table);
			}
		});

		timKiemField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				 if (timKiemField.getText().trim().isEmpty()) {
	                    table.setModel(bangNhanVien);
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

//			private void capNhapBang() {
//				String tuKhoa = timKiemField.getText().trim();
//				if (tuKhoa.isEmpty()) {
//					table.setModel(bangNhanVien);
//					return;
//				}
//				DefaultTableModel daDuocLoc = new DefaultTableModel();
//				daDuocLoc.addColumn("Mã Nhân Viên");
//				daDuocLoc.addColumn("Họ Và Tên");
//				daDuocLoc.addColumn("Giới Tính");
//				daDuocLoc.addColumn("Ngày Sinh");
//				daDuocLoc.addColumn("Số Điện Thoại");
//				daDuocLoc.addColumn("Ca Làm");
//
//				for (int i = 0; i < bangNhanVien.getRowCount(); i++) {
//					String maNV = bangNhanVien.getValueAt(i, 0).toString().toLowerCase();
//					String tenNV = bangNhanVien.getValueAt(i, 1).toString().toLowerCase();
//
//					if (maNV.contains(tuKhoa.toLowerCase()) || tenNV.contains(tuKhoa.toLowerCase())) {
//						daDuocLoc.addRow(new Object[] { bangNhanVien.getValueAt(i, 0), bangNhanVien.getValueAt(i, 1),
//								bangNhanVien.getValueAt(i, 2), bangNhanVien.getValueAt(i, 3),
//								bangNhanVien.getValueAt(i, 4), });
//					}
//
//				}
//				table.setModel(daDuocLoc);
//			}
//		});

//		timKiemButton.addActionListener(e -> {
//			String tuKhoa = timKiemField.getText().trim();
//			if (tuKhoa.isEmpty()) {
//				JOptionPane.showMessageDialog(null, "Vui lòng nhập từ khoá để tìm kiếm!", "Thông Báo",
//						JOptionPane.WARNING_MESSAGE);
//			}
		});

		JPanel formJPanel = new JPanel();
		formJPanel.setLayout(new GridLayout(4, 4, 10, 10));
		formJPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		formJPanel.add(new JLabel("Mã Nhân Viên:"));
		JTextField maNhanVienField = new JTextField();
		formJPanel.add(maNhanVienField);

		formJPanel.add(new JLabel("Họ Và Tên:"));
		JTextField tenField = new JTextField();
		formJPanel.add(tenField);

		formJPanel.add(new JLabel("Giới Tính:"));
		JPanel gioiTinhJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		JRadioButton namBox = new JRadioButton("Nam");
		JRadioButton nuBox = new JRadioButton("Nữ");
		ButtonGroup gioiTinhGroup = new ButtonGroup();
		gioiTinhGroup.add(namBox);
		gioiTinhGroup.add(nuBox);
		gioiTinhJPanel.add(namBox);
		gioiTinhJPanel.add(nuBox);
		formJPanel.add(gioiTinhJPanel);

		formJPanel.add(new JLabel("Ngày Sinh:"));
		JPanel ngaySinhJPanel = new JPanel(new GridLayout(1, 3, 0, 0));
		String ngay[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
		JComboBox ngayComboBox = new JComboBox(ngay);
		ngaySinhJPanel.add(ngayComboBox);

		String thang[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
		JComboBox thangComboBox = new JComboBox(thang);
		ngaySinhJPanel.add(thangComboBox);

		String nam[] = { "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001",
				"2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014",
				"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024" };
		JComboBox namComboBox = new JComboBox(nam);
		ngaySinhJPanel.add(namComboBox);
		formJPanel.add(ngaySinhJPanel);

		formJPanel.add(new JLabel("Số Điện Thoại:"));
		JTextField soDienThoaiField = new JTextField();
		formJPanel.add(soDienThoaiField);

		formJPanel.add(new JLabel("Ca Làm:"));
		String caLam[] = { "Ca Sáng: 7h - 11h", "Ca Chiều: 12h - 18h", "Ca Tối: 18h - 22h" };
		JComboBox caLamComboBox = new JComboBox(caLam);
		formJPanel.add(caLamComboBox);

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
		taiDuLieu(bangNhanVien);

		themButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String maNV = maNhanVienField.getText();
				String tenNV = tenField.getText();
				String gioiTinh = namBox.isSelected() ? "Nam" : nuBox.isSelected() ? "Nữ" : "";
				String ngaySinh = ngayComboBox.getSelectedItem() + "/" + thangComboBox.getSelectedItem() + "/"
						+ namComboBox.getSelectedItem();
				String soDT = soDienThoaiField.getText();
				String caLam = (String) caLamComboBox.getSelectedItem();

				if (tenNV.matches(".*[^\\p{L}\\s].*")) {
					JOptionPane.showMessageDialog(null, "Tên nhân viên không được chứa ký tự đặc biệt và số!", "Lỗi",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (!soDT.matches("\\d{10}")) {
					JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ!", "Lỗi",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (maNV.isEmpty() || tenNV.isEmpty() || gioiTinh.isEmpty() || ngaySinh.isEmpty() || soDT.isEmpty()
						|| caLam.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đủ thông tin!", "Lỗi",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					String kiemTraMaSQL = "SELECT COUNT(*) FROM nhanVien WHERE maNV = ?";
					PreparedStatement kiemTraStmt = connection.prepareStatement(kiemTraMaSQL);
					kiemTraStmt.setString(1, maNV);
					ResultSet rs = kiemTraStmt.executeQuery();

					if (rs.next() && rs.getInt(1) > 0) {
						JOptionPane.showMessageDialog(null, "Mã nhân viên đã tồn tại!", "Lỗi",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					String themVaoSQL = "INSERT INTO nhanVien (maNV, tenNV, gioiTinh, ngaySinh, soDT, caLam) VALUES (?, ?, ?, ?, ?, ?)";
					PreparedStatement stmt = connection.prepareStatement(themVaoSQL);
					stmt.setString(1, maNV);
					stmt.setString(2, tenNV);
					stmt.setString(3, gioiTinh);
					stmt.setString(4, ngaySinh);
					stmt.setString(5, soDT);
					stmt.setString(6, caLam);

					int hangThem = stmt.executeUpdate();

					if (hangThem > 0) {
						JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công!", "Thông Báo",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Không thêm được nhân viên!", "Lỗi",
								JOptionPane.WARNING_MESSAGE);
					}

					bangNhanVien.addRow(new Object[] { maNV, tenNV, gioiTinh, ngaySinh, soDT, caLam });

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
				int hangDaChon = table.getSelectedRow();
				if (hangDaChon == -1) {
					JOptionPane.showMessageDialog(null, "Vui Lòng chọn dòng để xoá!", "Thông Báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				String maNV = table.getValueAt(hangDaChon, 0).toString();
				int xacNhan = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xoá nhân viên này không?",
						"Xác Nhận", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (xacNhan == JOptionPane.YES_NO_OPTION) {
					try {
						String xoaSQL = "DELETE FROM nhanVien WHERE maNV = ?";
						PreparedStatement stmt = connection.prepareStatement(xoaSQL);
						stmt.setString(1, maNV);

						int hangXoa = stmt.executeUpdate();
						if (hangXoa > 0) {
							bangNhanVien.removeRow(hangDaChon);
							JOptionPane.showMessageDialog(null, "Xoá nhân viên thành công!", "Thông Báo",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Không thể xoá nhân viên!", "Lỗi",
									JOptionPane.WARNING_MESSAGE);
						}
					} catch (SQLException ex) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "Lỗi khi xoá nhân viên ra khỏi CSDL: " + ex.getMessage(),
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
					JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần cập nhập!", "Lỗi",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					String maNV = maNhanVienField.getText();
					String tenNV = tenField.getText();
					String gioiTinh = namBox.isSelected() ? "Nam" : nuBox.isSelected() ? "Nữ" : "";
					String ngaySinh = ngayComboBox.getSelectedItem() + "/" + thangComboBox.getSelectedItem() + "/"
							+ namComboBox.getSelectedItem();
					String soDT = soDienThoaiField.getText();
					String caLam = (String) caLamComboBox.getSelectedItem();

					if (tenNV.matches(".*[^\\p{L}\\s].*")) {
						JOptionPane.showMessageDialog(null, "Tên nhân viên không được chứa ký tự đặc biệt và số!", "Lỗi",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					if (!soDT.matches("\\d{10}")) {
						JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ!", "Lỗi",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					if (maNV.isEmpty() || tenNV.isEmpty() || gioiTinh.isEmpty() || ngaySinh.isEmpty() || soDT.isEmpty()
							|| caLam.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Vui lòng nhập đủ thông tin!", "Lỗi",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					try {
						String capNhapSQL = "UPDATE nhanVien SET tenNV = ?, gioiTinh = ?, ngaySinh = ?, soDT = ?, caLam = ? WHERE maNV = ?";
						PreparedStatement stmt = connection.prepareStatement(capNhapSQL);

						stmt.setString(1, tenNV);
						stmt.setString(2, gioiTinh);
						stmt.setString(3, ngaySinh);
						stmt.setString(4, soDT);
						stmt.setString(5, caLam);
						stmt.setString(6, maNV);

						int hangCapNhap = stmt.executeUpdate();

						if (hangCapNhap > 0) {

							bangNhanVien.setValueAt(maNV, hangDaChon, 0);
							bangNhanVien.setValueAt(tenNV, hangDaChon, 1);
							bangNhanVien.setValueAt(gioiTinh, hangDaChon, 2);
							bangNhanVien.setValueAt(ngaySinh, hangDaChon, 3);
							bangNhanVien.setValueAt(soDT, hangDaChon, 4);
							bangNhanVien.setValueAt(caLam, hangDaChon, 5);

							JOptionPane.showMessageDialog(null, "Cập nhập nhân viên thành công!", "Thông Báo",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Không thể cập nhập nhân viên!", "Lỗi",
									JOptionPane.WARNING_MESSAGE);
						}
					} catch (SQLException ex) {
						// TODO: handle exception
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "Lỗi khi cập nhập vào CSDL: " + ex.getMessage(), "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JButton lamMoiButton = new JButton("Làm Mới");
		formJPanel.add(lamMoiButton);

		lamMoiButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				maNhanVienField.setText("");
				tenField.setText("");
				gioiTinhGroup.clearSelection();
				ngayComboBox.setSelectedIndex(0);
				thangComboBox.setSelectedIndex(0);
				namComboBox.setSelectedIndex(0);
				soDienThoaiField.setText("");
				caLamComboBox.setSelectedIndex(0);
				JOptionPane.showMessageDialog(null, "Đã làm mới các trường dữ liệu!", "Thông Báo",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		nhanVien.add(formJPanel, BorderLayout.SOUTH);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int hang = table.rowAtPoint(evt.getPoint());
				if (hang >= 0) {
					maNhanVienField.setText(table.getValueAt(hang, 0).toString());
					tenField.setText(table.getValueAt(hang, 1).toString());

					String gioiTinh = table.getValueAt(hang, 2).toString();
					if (gioiTinh.equals("Nam")) {
						namBox.setSelected(true);
						nuBox.setSelected(false);
					} else if (gioiTinh.equals("Nữ")) {
						nuBox.setSelected(true);
						namBox.setSelected(false);
					}

					String[] ngaySinhParts = table.getValueAt(hang, 3).toString().split("/");
					ngayComboBox.setSelectedItem(ngaySinhParts[0]);
					thangComboBox.setSelectedItem(ngaySinhParts[1]);
					namComboBox.setSelectedItem(ngaySinhParts[2]);

					soDienThoaiField.setText(table.getValueAt(hang, 4).toString());
					caLamComboBox.setSelectedItem(table.getValueAt(hang, 5).toString());
				}
			}
		});
		return nhanVien;
	}
	
	private void capNhapBang(String tuKhoa, DefaultTableModel bangNhanVien, JTable table) {
		DefaultTableModel daDuocLoc = new DefaultTableModel();
		daDuocLoc.addColumn("Mã Nhân Viên");
		daDuocLoc.addColumn("Họ Và Tên");
		daDuocLoc.addColumn("Giới Tính");
		daDuocLoc.addColumn("Ngày Sinh");
		daDuocLoc.addColumn("Số Điện Thoại");
		daDuocLoc.addColumn("Ca Làm");

		for (int i = 0; i < bangNhanVien.getRowCount(); i++) {
			String maNV = bangNhanVien.getValueAt(i, 0).toString().toLowerCase();
			String tenNV = bangNhanVien.getValueAt(i, 1).toString().toLowerCase();

			if (maNV.contains(tuKhoa.toLowerCase()) || tenNV.contains(tuKhoa.toLowerCase())) {
				daDuocLoc.addRow(new Object[] { bangNhanVien.getValueAt(i, 0), bangNhanVien.getValueAt(i, 1),
						bangNhanVien.getValueAt(i, 2), bangNhanVien.getValueAt(i, 3),
						bangNhanVien.getValueAt(i, 4), });
			}

		}
		table.setModel(daDuocLoc);
	}

	private void taiDuLieu(DefaultTableModel model) {
		try {
			String query = "SELECT * FROM nhanVien";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				String maNV = resultSet.getString("maNV");
				String tenNV = resultSet.getString("tenNV");
				String gioiTinh = resultSet.getString("gioiTinh");
				String ngaySinh = resultSet.getString("ngaySinh");
				String soDT = resultSet.getString("SoDT");
				String caLam = resultSet.getString("caLam");

				model.addRow(new Object[] { maNV, tenNV, gioiTinh, ngaySinh, soDT, caLam });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
