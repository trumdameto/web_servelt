package entities;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class HoaDonChiTiet {
    private Integer id;
    private Integer idHoaDon;
    private Integer idSPCT;
    private Integer soLuong;
    private Double donGia;
    private Timestamp thoiGian;
    private Integer trangThai;

    public HoaDonChiTiet(Integer idHoaDon, Integer idSPCT, Integer soLuong, Double donGia, Timestamp thoiGian, Integer trangThai) {
        this.idHoaDon = idHoaDon;
        this.idSPCT = idSPCT;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
    }
}
