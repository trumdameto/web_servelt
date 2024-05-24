package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class HoaDon {
    private Integer id;
    private Integer idKH;
    private Integer idNV;
    private Date ngayMuaHang;
    private Integer trangThai;

    public HoaDon(Integer idKH, Integer idNV, Date ngayMuaHang, Integer trangThai) {
        this.idKH = idKH;
        this.idNV = idNV;
        this.ngayMuaHang = ngayMuaHang;
        this.trangThai = trangThai;
    }
}
