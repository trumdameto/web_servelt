package repositories;

import entities.HoaDon;
import entities.HoaDonChiTiet;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HDCTRepo {
	private Connection connection;

	public HDCTRepo() {
		try {
			this.connection = DBContext.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<HoaDonChiTiet> findAll() {
		ArrayList<HoaDonChiTiet> list = new ArrayList<>();
		String sql = "Select * from HoaDonChiTiet";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("id");
				Integer idHD = rs.getInt("IdHoaDon");
				Integer idSPCT = rs.getInt("IdSPCT");
				Integer soLuong = rs.getInt("soLuong");
				Integer trangThai = rs.getInt("trangThai");
				HoaDonChiTiet hoaDon = new HoaDonChiTiet();
				list.add(hoaDon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public void insert(HoaDonChiTiet hd) {
		String sql = "Insert into HoaDonChiTiet(IdHoaDon,IdSPCT,SoLuong,DonGia,ThoiGian,TrangThai) values (?,?,?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, hd.getIdHoaDon());
			ps.setInt(2, hd.getIdSPCT());
			ps.setInt(3, hd.getSoLuong());
			ps.setDouble(4, hd.getDonGia());
			ps.setTimestamp(5, hd.getThoiGian());
			ps.setInt(6, hd.getTrangThai());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
