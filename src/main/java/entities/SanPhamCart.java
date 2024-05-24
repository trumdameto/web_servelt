package entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamCart {
	public int soLuong;
	public SanPhamChiTiet spct;

	public void soLuongUpdate() {
		this.soLuong++;
	}
}
