package entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SanPhamChiTiet {
    private Integer id;
    private Integer idMauSac;
    private Integer idKichThuoc;
    private Integer idSanPham;
    private String maSPCT;
    private Integer soLuong;
    private Double donGia;
    private Integer trangThai;

}
