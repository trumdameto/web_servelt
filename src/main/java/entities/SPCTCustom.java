package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SPCTCustom {
	private Integer id;
	private String tenMauSac;
	private String tenKichThuoc;
	private String maSPCT;
	private int soLuong;
	private double donGia;
	private int trangThai;
}
