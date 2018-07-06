package cn.gzitrans.soft.api.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cn.gzitrans.soft.api.entity.PictureUploadEntity;

public interface PictureUploadDao extends CrudRepository<PictureUploadEntity, Long>{

	@Query("from PictureUploadEntity where openId = ? and plateNumber = ?")
	ArrayList<PictureUploadEntity> getList(String openId, String plateNumber);

	@Query("select count(*) from PictureUploadEntity where openId = ? and plateNumber = ?")
	Integer checkPlateNumber(String openId, String plateNumber);

	@Modifying
	@Transactional
	@Query("delete from PictureUploadEntity where openId = ? and plateNumber = ?")
	void deletePlate(String openId, String plateNumber);

	@Query("from PictureUploadEntity where id = ?")
	PictureUploadEntity getById(Long id);

	@Query("select plateNumber from PictureUploadEntity where openId = ?")
	List<String> getPlateListByOpenId(String openId);

}
