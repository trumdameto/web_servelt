package repositories;

import entities.KichThuoc;
import entities.NhanVien;
import utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class NhanVienRepo {
    private Connection connection;

    public NhanVienRepo() {
        try {
            this.connection = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<NhanVien> findAll() {
        ArrayList<NhanVien> list = new ArrayList<>();
        String sql = "select * from NhanVien";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String ten = rs.getString("ten");
                String ma = rs.getString("ma");
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");
                int trangThai = rs.getInt("trangThai");
                NhanVien nv = new NhanVien(id, ten, ma, tenDangNhap, matKhau, trangThai);
                list.add(nv);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public NhanVien findById(Integer id) {
        NhanVien nv = null;
        String sql = "select * from NhanVien where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nv = new NhanVien(
                        rs.getInt("id"),
                        rs.getString("ten"),
                        rs.getString("ma"),
                        rs.getString("tenDangNhap"),
                        rs.getString("matKhau"),
                        rs.getInt("trangThai")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }

    public void deleteNv(NhanVien nv) {
        String sql = "delete from NhanVien where id =?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, nv.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertNv(NhanVien nv) {
        String sql = "insert into NhanVien(ten,ma,tenDangNhap,matKhau,trangThai) values(?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nv.getTen());
            ps.setString(2, nv.getMa());
            ps.setString(3, nv.getTenDangNhap());
            ps.setString(4, nv.getMatKhau());
            ps.setInt(5, nv.getTrangThai());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNv(NhanVien nv) {
        String sql = "Update NhanVien set ten = ?, ma = ?, tenDangNhap = ?,matKhau = ?, trangThai = ? where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(6, nv.getId());
            ps.setString(1, nv.getTen());
            ps.setString(2, nv.getMa());
            ps.setString(3, nv.getTenDangNhap());
            ps.setString(4, nv.getMatKhau());
            ps.setInt(5, nv.getTrangThai());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<NhanVien> findAll(String maTen, Integer trangThai, Optional<Integer> optionPage, Optional<Integer> optionLimit) {
        Integer page = optionPage.isPresent() ? optionPage.get() : 1;
        Integer limit = optionLimit.isPresent() ? optionLimit.get() : 5;

        ArrayList<NhanVien> ds = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";

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
                int id = rs.getInt("id");
                String ten = rs.getString("ten");
                String ma = rs.getString("ma");
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");
                int tt = rs.getInt("trangThai");
                NhanVien nv = new NhanVien(id, ten, ma, tenDangNhap, matKhau, tt);
                ds.add(nv);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountBySearch(String maTen, Integer trangThai) {
        String sql = "SELECT COUNT(*) FROM NhanVien";

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

    public HashMap<Integer, String> findTenByIds(List<Integer> listId)
    {
        HashMap<Integer, String> ds = new HashMap<>();
        String sql = "SELECT ID, Ten FROM KichThuoc WHERE ID IN (";
        for (int i = 0; i <listId.size(); i++) {
            sql += "?";
            sql += i == listId.size() - 1 ? "" : ",";
        }
        sql += ")";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            for (int i = 0; i <listId.size(); i++) {
                ps.setInt(i+1, listId.get(i));
            }

            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String ten = rs.getString("Ten");
                ds.put(id, ten);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

}
