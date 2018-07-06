package cn.gzitrans.soft.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gzitrans.soft.api.dao.PictureUploadDao;
import cn.gzitrans.soft.api.entity.PictureUploadEntity;

@Service
public class PictureUploadService {
	
	@Autowired
	private PictureUploadDao pictureUploadDao;
	
	/**
	 * 保存图片信息
	 * @param user
	 */
	public void save(PictureUploadEntity pictureUpload){
		pictureUploadDao.save(pictureUpload);
    }

	/**
	 * 根据用户id和批次号查找图片记录
	 * @param openId
	 * @param plateNumber
	 * @return
	 */
	public ArrayList<PictureUploadEntity> getList(String openId, String plateNumber) {
		return pictureUploadDao.getList(openId,plateNumber);
	}

	/**
	 * 检查批次号是否已经存在
	 * @param plateNumber
	 * @return
	 */
	public boolean checkPlateNumber(String openId, String plateNumber) {
		Integer count = pictureUploadDao.checkPlateNumber(openId,plateNumber);
		boolean res = false;
		if(count > 0){
			res = true;
		}
		return res;
	}

	/**
	 * 删除相应记录
	 * @param openId
	 * @param plateNumber
	 */
	public void deletePlate(String openId, String plateNumber) {
		pictureUploadDao.deletePlate(openId,plateNumber);
	}

	/**
	 * 根据id返回对象
	 * @param id
	 * @return
	 */
	public PictureUploadEntity getById(Long id) {
		return pictureUploadDao.getById(id);
	}

	/**
	 * 根据openid返回批次号列表
	 * @param openId
	 * @return
	 */
	public List<String> getPlateListByOpenId(String openId) {
		return pictureUploadDao.getPlateListByOpenId(openId);
	}

}
