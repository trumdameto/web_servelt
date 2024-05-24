package repositories;

import entities.KichThuoc;
import utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class KichThuocRepo {
    private Connection connection;

    public KichThuocRepo() {
        try {
            this.connection = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<KichThuoc> findAll() {
        List<KichThuoc> list = new ArrayList<>();
        String sql = "Select * from KichThuoc";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KichThuoc kt = new KichThuoc(
                        rs.getInt("id"),
                        rs.getString("ma"),
                        rs.getString("ten"),
                        rs.getInt("trangThai")
                );
                list.add(kt);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insertKt(KichThuoc kt) {
        String sql = "Insert into KichThuoc (ma,ten,trangThai) values (?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, kt.getMa());
            ps.setString(2, kt.getTen());
            ps.setInt(3, kt.getTrangThai());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateKt(KichThuoc kt) {
        String sql = "Update KichThuoc set ma = ?,ten = ?,trangThai = ? where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, kt.getMa());
            ps.setString(2, kt.getTen());
            ps.setInt(3, kt.getTrangThai());
            ps.setInt(4, kt.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteKt(KichThuoc kt) {
        String sql = "DELETE FROM KichThuoc WHERE id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, kt.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public KichThuoc findById(int id) {
        KichThuoc kt = null;
        String sql = "SELECT * FROM KichThuoc WHERE id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                kt = new KichThuoc(
                        rs.getInt("id"),
                        rs.getString("ma"),
                        rs.getString("ten"),
                        rs.getInt("trangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kt;
    }

    public List<KichThuoc> findAll(String maTen, Integer trangThai,Optional<Integer> optionPage,Optional<Integer> optionLimit) {
        Integer page = optionPage.isPresent() ? optionPage.get() : 1;
        Integer limit = optionLimit.isPresent() ? optionLimit.get() : 5;

        ArrayList<KichThuoc> ds = new ArrayList<>();
        String sql = "SELECT * FROM KichThuoc";

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
                String ten1 = rs.getString("ten");
                String ma1 = rs.getString("ma");
                int trangThai1 = rs.getInt("trangThai");
                KichThuoc kt = new KichThuoc(id, ma1, ten1, trangThai1);
                ds.add(kt);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountBySearch(String maTen, Integer trangThai) {
        String sql = "SELECT COUNT(*) FROM KichThuoc";

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
            ResultSet rs = ps.executeQuery();
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
