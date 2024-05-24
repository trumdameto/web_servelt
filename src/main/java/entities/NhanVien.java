package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NhanVien {
    private Integer id;
    private String ten;
    private String ma;
    private String tenDangNhap;
    private String matKhau;
    private Integer trangThai;
}
