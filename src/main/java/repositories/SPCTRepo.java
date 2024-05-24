package repositories;

import entities.SPCTCustom;
import entities.SanPhamChiTiet;
import utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SPCTRepo {
	private Connection connection;

	public SPCTRepo() {
		try {
			this.connection = DBContext.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<SanPhamChiTiet> findAll() {
		ArrayList<SanPhamChiTiet> list = new ArrayList<>();
		String sql = "Select * from SanPhamChiTiet";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt(1);
				Integer idKT = rs.getInt(2);
				Integer idMS = rs.getInt(3);
				Integer idSP = rs.getInt(4);
				String ma = rs.getString(5);
				Integer soLuong = rs.getInt(6);
				Double donGia = rs.getDouble(7);
				Integer trangThai = rs.getInt(8);

				SanPhamChiTiet spct = new SanPhamChiTiet(id, idKT, idMS, idSP, ma, soLuong, donGia, trangThai);
				list.add(spct);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<SanPhamChiTiet> findSpctByIdSp(int idSP) {
		ArrayList<SanPhamChiTiet> list = new ArrayList<>();
		String sql = "Select * from SanPhamChiTiet where IdSanPham=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,idSP);
			ps.execute();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				int idMS = rs.getInt("IdMauSac");
				int idKT = rs.getInt("IdKichThuoc");
				int idSanPham = rs.getInt("IdSanPham");
				String ma = rs.getString("MaSPCT");
				int soLuong = rs.getInt("SoLuong");
				double donGia = rs.getDouble("DonGia");
				int trangThai = rs.getInt("TrangThai");
				SanPhamChiTiet spct = new SanPhamChiTiet(id, idMS, idKT, idSanPham, ma, soLuong, donGia, trangThai);
				list.add(spct);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<SanPhamChiTiet> findSpctById(int idSP) {
		ArrayList<SanPhamChiTiet> list = new ArrayList<>();
		String sql = "Select * from SanPhamChiTiet where Id=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,idSP);
			ps.execute();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				int idMS = rs.getInt("IdMauSac");
				int idKT = rs.getInt("IdKichThuoc");
				int idSanPham = rs.getInt("IdSanPham");
				String ma = rs.getString("MaSPCT");
				int soLuong = rs.getInt("SoLuong");
				double donGia = rs.getDouble("DonGia");
				int trangThai = rs.getInt("TrangThai");
				SanPhamChiTiet spct = new SanPhamChiTiet(id, idMS, idKT, idSanPham, ma, soLuong, donGia, trangThai);
				list.add(spct);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void insert(SanPhamChiTiet spct) {
		String sql = "Insert into SanPhamChiTiet(IdMauSac,IdKichThuoc,IdSanPham,MaSPCT,SoLuong,DonGia,TrangThai) values (?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, spct.getIdMauSac());
			ps.setInt(2, spct.getIdKichThuoc());
			ps.setInt(3, spct.getIdSanPham());
			ps.setString(4, spct.getMaSPCT());
			ps.setInt(5, spct.getSoLuong());
			ps.setDouble(6, spct.getDonGia());
			ps.setInt(7, spct.getTrangThai());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(SanPhamChiTiet spct) {
		String sql = "Update SanPhamChiTiet set IdMauSac=?,IdKichThuoc=?,IdSanPham=?,MaSPCT=?,SoLuong=?,DonGia=?,TrangThai=? where id=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, spct.getIdMauSac());
			ps.setInt(2, spct.getIdKichThuoc());
			ps.setInt(3, spct.getIdSanPham());
			ps.setString(4, spct.getMaSPCT());
			ps.setInt(5, spct.getSoLuong());
			ps.setDouble(6, spct.getDonGia());
			ps.setInt(7, spct.getTrangThai());
			ps.setInt(8, spct.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(SanPhamChiTiet spct) {
		String sql = "DELETE from SanPhamChiTiet where id =?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, spct.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SanPhamChiTiet findById(Integer id) {
		SanPhamChiTiet spct = null;
		String sql = "Select * From SanPhamChiTiet where id = ?";

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				spct = new SanPhamChiTiet(
						rs.getInt("id"),
						rs.getInt("idKichThuoc"),
						rs.getInt("idMauSac"),
						rs.getInt("idSanPham"),
						rs.getString("maSPCT"),
						rs.getInt("soLuong"),
						rs.getDouble("donGia"),
						rs.getInt("trangThai")
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return spct;
	}

	public List<SanPhamChiTiet> paging(Optional<Integer> opPage, Optional<Integer> opLimit) {
		ArrayList<SanPhamChiTiet> list = new ArrayList<>();
		String sql = "Select * from SanPhamChiTiet order by id offset ? rows fetch next ? rows only";

		int page = opPage.isPresent() ? opPage.get() : 1;
		int limit = opLimit.isPresent() ? opLimit.get() : 10;

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			int offset = (page - 1) * limit;
			ps.setInt(1, offset);
			ps.setInt(2, limit);
			ps.execute();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt(1);
				Integer idKT = rs.getInt(2);
				Integer idMS = rs.getInt(3);
				Integer idSP = rs.getInt(4);
				String ma = rs.getString(5);
				Integer soLuong = rs.getInt(6);
				Double donGia = rs.getDouble(7);
				Integer trangThai = rs.getInt(8);

				SanPhamChiTiet spct = new SanPhamChiTiet(id, idKT, idMS, idSP, ma, soLuong, donGia, trangThai);
				list.add(spct);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int count() {
		String sql = "Select count(*) as Total from SanPhamChiTiet";

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.executeQuery();
			rs.next();
			int total = rs.getInt("Total");
			return total;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public List<SPCTCustom> findAllWithPropName(int idSP) {
		ArrayList<SPCTCustom> list = new ArrayList<>();
		String sql = "Select SPCT.ID,SPCT.MaSPCT,MauSac.Ten as tenMauSac,KichThuoc.ten as tenKichThuoc," +
				     "SPCT.SoLuong,SPCT.DonGia,SPCT.TrangThai from SanPhamChiTiet SPCT join " +
				" MauSac on SPCT.idMauSac = MauSac.id join "+
				" KichThuoc on SPCT.idKichThuoc = KichThuoc.id " +
				" where SPCT.idSanPham = ? ";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,idSP);
			ps.execute();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("ID");
				String tenMauSac = rs.getString("TenMauSac");
				String tenKichThuoc = rs.getString("TenKichThuoc");
				String ma = rs.getString("MaSPCT");
				Integer soLuong = rs.getInt("SoLuong");
				Double donGia = rs.getDouble("DonGia");
				Integer trangThai = rs.getInt("TrangThai");

				SPCTCustom spct = new SPCTCustom(id,tenMauSac,tenKichThuoc,ma,soLuong,donGia,trangThai);
				list.add(spct);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
