package repositories;

import entities.NhanVien;
import entities.SanPham;
import utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SanPhamRepo {
	private static Connection connection;

	public SanPhamRepo() {
		try {
			this.connection = DBContext.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<SanPham> findAll() {
		ArrayList<SanPham> list = new ArrayList<>();
		String sql = "SELECT * FROM SanPham";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String ma = rs.getString(2);
				String ten = rs.getString(3);
				int trangThai = rs.getInt(4);
				SanPham sp = new SanPham(id, ma, ten, trangThai);
				list.add(sp);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void insertSp(SanPham sp) {
		String sql = "Insert into SanPham (ma,ten,trangThai) values (?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, sp.getMa());
			ps.setString(2, sp.getTen());
			ps.setInt(3, sp.getTrangThai());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateSp(SanPham sp) {
		String sql = "UPDATE SanPham SET ma = ?,ten = ?,trangThai = ? where id = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(4, sp.getId());
			ps.setString(1, sp.getMa());
			ps.setString(2, sp.getTen());
			ps.setInt(3, sp.getTrangThai());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteSp(SanPham sp) {
		String sql = "Delete from SanPham where id = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, sp.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SanPham findById(Integer id) {
		SanPham sp = null;
		String sql = "select * from SanPham where id =?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sp = new SanPham(
						rs.getInt("id"),
						rs.getString("ma"),
						rs.getString("ten"),
						rs.getInt("trangThai")
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sp;
	}

	public List<SanPham> findAll(String maTen, Integer trangThai, Optional<Integer> optionPage, Optional<Integer> optionLimit) {
		Integer page = optionPage.isPresent() ? optionPage.get() : 1;
		Integer limit = optionLimit.isPresent() ? optionLimit.get() : 5;

		ArrayList<SanPham> ds = new ArrayList<>();
		String sql = "SELECT * FROM SanPham";

		if (maTen.length() != 0 || trangThai != null) {
			sql += " WHERE ";
		}

		if (maTen.length() != 0) {
			sql += " Ma LIKE ? or Ten like ? ";
		}

		if (trangThai != null) {
			sql += (maTen.length() != 0) ? " AND " : "";
			sql += " trangThai = ? ";
		}

		sql += " order by Id offset ? rows fetch next ? rows only";


		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			int i = 0;
			int offset = (page -1) * limit;
			if (maTen.length() != 0) {
				ps.setString(++i, "%" + maTen + "%");
				ps.setString(++i, "%" + maTen + "%");
			}

			if (trangThai != null) {
				ps.setInt(++i, trangThai);
			}
			ps.setInt(++i,offset);
			ps.setInt(++i,limit);
			ps.execute();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String ma = rs.getString(2);
				String ten = rs.getString(3);
				int tt = rs.getInt(4);
				SanPham sp = new SanPham(id, ma, ten, tt);
				ds.add(sp);
			}
			return ds;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getCountBySearch(String maTen, Integer trangThai) {
		String sql = "SELECT COUNT(*) FROM SanPham";

		if (maTen.length() != 0 || trangThai != null) {
			sql += " WHERE ";
		}

		if (maTen.length() != 0) {
			sql += " Ma LIKE ? OR Ten LIKE ? ";
		}
		if (trangThai != null) {
			sql += (maTen.length() != 0) ? " AND " : "";
			sql += " trangThai = ?";
		}

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			int i = 0;
			if (maTen.length() != 0) {
				ps.setString(++i, "%" + maTen + "%");
				ps.setString(++i, "%" + maTen + "%");
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
