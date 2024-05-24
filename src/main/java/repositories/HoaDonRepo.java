package repositories;

import entities.HoaDon;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HoaDonRepo {
    private Connection connection;

    public HoaDonRepo() {
        try {
            this.connection = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<HoaDon> findAll() {
        ArrayList<HoaDon> list = new ArrayList<>();
        String sql = "Select * from HoaDon";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer idKH = rs.getInt("IdKH");
                Integer idNV = rs.getInt("IdNV");
                Date ngayMua = new Date(rs.getDate("ngayMuaHang").getTime());
                Integer trangThai = rs.getInt("trangThai");
                HoaDon hoaDon = new HoaDon(id, idKH, idNV, ngayMua, trangThai);
                list.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insert(HoaDon hd) {
        String sql = "Insert into HoaDon(IdKh,IdNV,NgayMuaHang,TrangThai) values (?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, hd.getIdKH());
            ps.setInt(2, hd.getIdNV());
            ps.setDate(3, new Date(hd.getNgayMuaHang().getTime()));
            ps.setInt(4, hd.getTrangThai());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void update(HoaDon hd) {
        String sql = "Update HoaDon set idKH =?,idNV=?,ngayMuaHang=?,trangThai=? where id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, hd.getIdKH());
            ps.setInt(2, hd.getIdNV());
            ps.setDate(3, new Date(hd.getNgayMuaHang().getTime()));
            ps.setInt(4, hd.getTrangThai());
            ps.setInt(5, hd.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(HoaDon hd) {
        String sql = "Delete from HoaDon where id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, hd.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HoaDon findById(Integer id){
        HoaDon hd=null;

        String sql = "Select * from HoaDon where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                hd = new HoaDon(
                        rs.getInt("id"),
                        rs.getInt("IdKH"),
                        rs.getInt("IdNV"),
                        rs.getDate("ngayMuaHang"),
                        rs.getInt("trangThai")
                );
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return hd;
    }

    public List<HoaDon> paging(Optional<Integer> opPage,Optional<Integer> opLimit){
        ArrayList<HoaDon> list = new ArrayList<>();
        int page = opPage.isPresent() ? opPage.get() : 1;
        int limit = opLimit.isPresent() ? opLimit.get() : 5;

        String sql = "Select * From HoaDon Order by id offset ? rows fetch next ? rows only";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int offset = (page -1) * limit;
            ps.setInt(1,offset);
            ps.setInt(2,limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                HoaDon hd = new HoaDon(
                        rs.getInt("id"),
                        rs.getInt("IdKH"),
                        rs.getInt("IdNV"),
                        rs.getDate("ngayMuaHang"),
                        rs.getInt("trangThai")
                );
                list.add(hd);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
    public int count(){
        String sql = "Select Count(*) as Total from HoaDon";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            rs.next();
            int total = rs.getInt("Total");
            return total;
        } catch (Exception e){
            e.printStackTrace();
        }

        return -1;
    }
}
