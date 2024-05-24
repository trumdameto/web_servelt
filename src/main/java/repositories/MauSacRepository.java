package repositories;

import entities.MauSac;
import utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MauSacRepository {
    private Connection connection;

    public MauSacRepository() {
        try {
            this.connection = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<MauSac> findAll() {
        ArrayList<MauSac> ds = new ArrayList<>();
        String sql = "SELECT * FROM MauSac";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String ten = rs.getString("ten");
                String ma = rs.getString("ma");
                int trangThai = rs.getInt("trangThai");
                ds.add(new MauSac(id, ma, ten, trangThai));
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addMauSac(MauSac ms) {
        String sql = "Insert into MauSac(ma,ten,trangThai) values(?,?,?) ";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ms.getMa());
            ps.setString(2, ms.getTen());
            ps.setInt(3, ms.getTrangThai());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMauSac(MauSac ms) {
        String sql = "Update MauSac set ma = ?,ten = ?,trangThai = ? where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ms.getMa());
            ps.setString(2, ms.getTen());
            ps.setInt(3, ms.getTrangThai());
            ps.setInt(4, ms.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMS(MauSac ms) {
        String sql = "DELETE FROM MauSac WHERE id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, ms.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MauSac findById(int id) {
        MauSac ms = null;
        String sql = "SELECT * FROM MauSac WHERE id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ms = new MauSac(
                        rs.getInt("id"),
                        rs.getString("ma"),
                        rs.getString("ten"),
                        rs.getInt("trangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ms;
    }

    public int getCountBySearch(String maTen, Integer trangThai) {
        String sql = "SELECT COUNT(*) FROM MauSac";

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
        String sql = "SELECT ID, Ten FROM MauSac WHERE ID IN (";
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

    public List<MauSac> findAll(String maTen, Integer trangThai, Optional<Integer> pageOp, Optional<Integer> limitOp) {
        Integer page = pageOp.isPresent() ? pageOp.get() : 1;
        Integer limit = limitOp.isPresent() ? limitOp.get() : 5;

        ArrayList<MauSac> ds = new ArrayList<>();
        String sql = "SELECT * FROM MauSac";
        if (maTen.length() != 0 || trangThai != null) {
            sql += " WHERE ";
        }
        if (maTen.length() != 0) {
            sql += " Ma like ? or Ten like ? ";
        }
        if (trangThai != null) {
            sql += (maTen.length() != 0) ? " and " : "";
            sql += " trangThai = ?";
        }
        sql += " ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 0;
            int offset = ((page - 1) * limit);
            if (maTen.length() != 0) {
                ps.setString(++i, "%" + maTen + "%");
                ps.setString(++i, "%" + maTen + "%");
            }
            if (trangThai != null) {
                ps.setInt(++i, trangThai);
            }
            ps.setInt(++i, offset);
            ps.setInt(++i, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String ten = rs.getString("ten");
                String ma = rs.getString("ma");
                int trangTh = rs.getInt("trangThai");
                MauSac ms = new MauSac(id, ma, ten, trangTh);
                ds.add(ms);
            }
            return ds;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
