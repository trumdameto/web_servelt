package entities;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HoaDonChiTiet")

public class HDCT {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "IdHoaDon")
	private Integer idHoaDon;

	@Column(name = "IdSPCT")
	private Integer idSPCT;

	@Column(name = "SoLuong")
	private Integer soLuong;

	@Column(name = "DonGia")
	private Double donGia;

	@Column(name = "ThoiGian")
	private Timestamp thoiGian;

	@Column(name = "TrangThai")
	private Integer trangThai;
}
