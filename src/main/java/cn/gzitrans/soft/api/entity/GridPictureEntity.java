package cn.gzitrans.soft.api.entity;

/**
 * 九宫格中的图片实体类
 * 包含:图片id,图片地址,图片中标记数量,跳转url链接
 * 创建人：Jarvan   
 * 创建时间：2018年7月4日 下午4:10:36
 */
public class GridPictureEntity {
	
	private Long id;//图片id(与picture_upload表中的id对应)
	
	private String pictureSrc;//图片地址(加上了http://前缀)
	
	private Integer signCount;//图片中标记的数量
	
	private String navigatorUrl;//小程序的跳转url链接(/pages/editorimg/editorimg?id=1)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPictureSrc() {
		return pictureSrc;
	}

	public void setPictureSrc(String pictureSrc) {
		this.pictureSrc = pictureSrc;
	}

	public Integer getSignCount() {
		return signCount;
	}

	public void setSignCount(Integer signCount) {
		this.signCount = signCount;
	}

	public String getNavigatorUrl() {
		return navigatorUrl;
	}

	public void setNavigatorUrl(String navigatorUrl) {
		this.navigatorUrl = navigatorUrl;
	}

}
