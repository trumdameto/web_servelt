package repositories;

import entities.KhachHang;
import entities.KichThuoc;
import utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KhachHangRepo {
	private Connection connection;

	public KhachHangRepo() {

		try {
			this.connection = DBContext.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<KhachHang> findAll() {
		ArrayList<KhachHang> list = new ArrayList<>();
		String sql = "Select * from KhachHang ";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String ma = rs.getString(2);
				String ten = rs.getString(3);
				String sdt = rs.getString(4);
				int trangThai = rs.getInt(5);
				KhachHang kh = new KhachHang(id, ma, ten, sdt, trangThai);
				list.add(kh);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void insert(KhachHang kh) {
		String sql = "Insert into KhachHang (ma,ten,sdt,trangThai) values (?,?,?,?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, kh.getMa());
			ps.setString(2, kh.getTen());
			ps.setString(3, kh.getSdt());
			ps.setInt(4, kh.getTrangThai());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(KhachHang kh) {
		String sql = "Update KhachHang set ma = ?,ten = ?,sdt = ?,trangThai = ? where id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(5, kh.getId());
			ps.setString(1, kh.getMa());
			ps.setString(2, kh.getTen());
			ps.setString(3, kh.getSdt());
			ps.setInt(4, kh.getTrangThai());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(KhachHang kh) {
		String sql = "Delete from KhachHang where id =?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, kh.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public KhachHang findById(int id) {
		KhachHang kh = null;
		String sql = "Select *from KhachHang where id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				kh = new KhachHang(
						rs.getInt("id"),
						rs.getString("ma"),
						rs.getString("ten"),
						rs.getString("sdt"),
						rs.getInt("trangThai")
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kh;
	}

	public List<KhachHang> findAll(String maTen, String soDT, Integer trangThai, Optional<Integer> optionPage, Optional<Integer> optionLimit) {
		int page = optionPage.isPresent() ? optionPage.get() : 1;
		int limit = optionLimit.isPresent() ? optionLimit.get() : 5;
		ArrayList<KhachHang> ds = new ArrayList<>();
		String sql = "SELECT * FROM KhachHang";

		if (maTen.length() != 0 || soDT.length() != 0 || trangThai != null) {
			sql += " WHERE ";
		}
		if (maTen.length() != 0) {
			sql += " (Ma LIKE ? OR Ten LIKE ?) ";
		}
		if (soDT.length() != 0) {
			sql += (maTen.length() != 0) ? " AND " : "";
			sql += " SDT LIKE ? ";
		}
		if (trangThai != null) {
			sql += (maTen.length() != 0 || soDT.length() != 0) ? " AND " : "";
			sql += " trangThai = ? ";
		}
		sql += " ORDER BY Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			int i = 0;
			int offset = (page - 1) * limit;
			if (maTen.length() != 0) {
				ps.setString(++i, "%" + maTen + "%");
				ps.setString(++i, "%" + maTen + "%");
			}
			if (soDT.length() != 0) {
				ps.setString(++i, "%" + soDT + "%");
			}
			if (trangThai != null) {
				ps.setInt(++i, trangThai);
			}
			ps.setInt(++i, offset);
			ps.setInt(++i, limit);
			ps.execute();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String ma = rs.getString(2);
				String ten = rs.getString(3);
				String sdt = rs.getString(4);
				int state = rs.getInt(5);
				KhachHang kh = new KhachHang(id, ma, ten, sdt, state);
				ds.add(kh);
			}
			return ds;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getCountBySearch(String maTen, String soDT, Integer trangThai) {
		String sql = "SELECT COUNT(*) FROM KhachHang";

		if (maTen.length() != 0 || soDT.length() != 0 || trangThai != null) {
			sql += " WHERE ";
		}

		if (maTen.length() != 0) {
			sql += " (Ma LIKE ? OR Ten LIKE ?) ";
		}
		if (soDT.length() != 0) {
			sql += (maTen.length() != 0) ? " AND " : "";
			sql += " SDT LIKE ? ";
		}
		if (trangThai != null) {
			sql += (maTen.length() != 0 || soDT.length() != 0) ? " AND " : "";
			sql += " trangThai = ?";
		}

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			int i = 0;
			if (maTen.length() != 0) {
				ps.setString(++i, "%" + maTen + "%");
				ps.setString(++i, "%" + maTen + "%");
			}
			if (soDT.length() != 0) {
				ps.setString(++i, "%" + soDT + "%");
			}
			if (trangThai != null) {
				ps.setInt(++i, trangThai);
			}
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
