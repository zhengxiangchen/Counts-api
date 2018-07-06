package cn.gzitrans.soft.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mini_picture_upload")
public class PictureUploadEntity {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name = "openid")
	private String openId;//用户唯一标识
	
	@Column(name = "plate_number")
	private String plateNumber;//车次或批次号
	
	@Column(name = "picture_name")
	private String pictureName;//图片名称
	
	@Column(name = "area_circle")
	private String areaCircle;//区域圆坐标以及半径信息用json格式记录tring类型存储
	
	@Column(name = "sign_circle")
	private String signCircle;//识别后一些点的坐标信息json格式

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public String getAreaCircle() {
		return areaCircle;
	}

	public void setAreaCircle(String areaCircle) {
		this.areaCircle = areaCircle;
	}

	public String getSignCircle() {
		return signCircle;
	}

	public void setSignCircle(String signCircle) {
		this.signCircle = signCircle;
	}
	
}
