package entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SanPham {
    private Integer id;
    private String ma;
    private String ten;
    private Integer trangThai;
}
