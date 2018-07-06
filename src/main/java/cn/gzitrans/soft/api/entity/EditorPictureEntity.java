package cn.gzitrans.soft.api.entity;

import java.util.List;

public class EditorPictureEntity {
	
	private Long id;//图片的id
	
	private String src;//图片地址包含http://
	
	private List<CircularEntity> circularList;//记录所有标记圆的坐标及半径
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public List<CircularEntity> getCircularList() {
		return circularList;
	}

	public void setCircularList(List<CircularEntity> circularList) {
		this.circularList = circularList;
	}
	

}
