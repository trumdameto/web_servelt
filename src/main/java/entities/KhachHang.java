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

public class KhachHang {
    private Integer id;
    private String ma;
    private String ten;
    private String sdt;
    private Integer trangThai;
}
