package cn.gzitrans.soft.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.gzitrans.soft.api.entity.CircularEntity;
import cn.gzitrans.soft.api.entity.EditorPictureEntity;
import cn.gzitrans.soft.api.entity.GridPictureEntity;
import cn.gzitrans.soft.api.entity.PictureUploadEntity;
import cn.gzitrans.soft.api.service.PictureUploadService;
import cn.gzitrans.soft.api.utils.FileUtils;
import cn.gzitrans.soft.api.utils.ImageHelper;

@RestController
@RequestMapping("${basepath}/picture")
public class PictureController {
	
	public static Logger logger = LogManager.getLogger(PictureController.class);
	
	@Value("${upload_path}")
	private String filePath;//D://all_images/
	
	@Value("${static_path}")
	private String staticPath;//http://127.0.0.1:8080/
	
	@Autowired
	private PictureUploadService pictureUploadService;
	
	
	/**
	 * 接收小程序传过来的图片以及标注信息
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam MultipartFile picture, @RequestParam String openId, 
			@RequestParam String plateNumber, @RequestParam String circularList){
		
		//存储上传的原图到服务器本地
        String fileName = "Counts" + Math.random()*1000 + picture.getOriginalFilename();
        try {
            FileUtils.uploadFile(picture.getBytes(), filePath, fileName);
        } catch (Exception e) {
        }
        
		
        PictureUploadEntity pictureUpload = new PictureUploadEntity();
        pictureUpload.setOpenId(openId);
        pictureUpload.setPlateNumber(plateNumber);
        pictureUpload.setPictureName(fileName);
        pictureUpload.setAreaCircle(circularList);
        
        //模拟已经计数完成了
        pictureUpload.setSignCircle(circularList);
        //end
        
        pictureUploadService.save(pictureUpload);
        
        logger.info("小程序图片上传计数:[用户openid] = " + openId + ",[批次号plateNumber] = " + plateNumber + ",[图片名称fileName] = " + fileName + ",[区域圆心坐标circularList] = " + circularList);
		
		return "success";
	}
	
	/**
	 * 根据用户id和批次号查找该条件下的图片记录并返回
	 * @param openId
	 * @param plateNumber
	 * @return
	 */
	@RequestMapping(value = "/getPictures", method = RequestMethod.GET)
	public ArrayList<GridPictureEntity> getPictures(@RequestParam String openId, @RequestParam String plateNumber){
		ArrayList<GridPictureEntity> returnList = new ArrayList<GridPictureEntity>();
		ArrayList<PictureUploadEntity> uploadList = pictureUploadService.getList(openId,plateNumber);
		
		PictureUploadEntity pictureUploadEntity = new PictureUploadEntity();
		GridPictureEntity gridPictureEntity;
		
		for(int i = 0; i < uploadList.size(); i++){
			pictureUploadEntity = uploadList.get(i);
			String signCircleString = pictureUploadEntity.getSignCircle();
			List<CircularEntity> circularList = JSON.parseArray(signCircleString, CircularEntity.class);
			
			gridPictureEntity = new GridPictureEntity();
			gridPictureEntity.setId(pictureUploadEntity.getId());
			gridPictureEntity.setPictureSrc(staticPath + pictureUploadEntity.getPictureName());
			gridPictureEntity.setSignCount(circularList.size());
			//gridPictureEntity.setNavigatorUrl("/pages/editorsign/editorsign?id=" + pictureUploadEntity.getId());
			gridPictureEntity.setNavigatorUrl("/pages/editorsign2/editorsign2?id=" + pictureUploadEntity.getId());
			
			returnList.add(gridPictureEntity);
		}
		
		return returnList;
	}
	
	
	
	/**
	 * 根据图片记录id取得图片对象
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getPicture", method = RequestMethod.GET)
	public EditorPictureEntity getPicture(@RequestParam Long id){
		EditorPictureEntity returnEntity = new EditorPictureEntity();
		
		PictureUploadEntity pictureUploadEntity = pictureUploadService.getById(id);
		String signCircleString = pictureUploadEntity.getSignCircle();
		List<CircularEntity> circularList = JSON.parseArray(signCircleString, CircularEntity.class);
		
		returnEntity.setId(id);
		returnEntity.setSrc(staticPath + pictureUploadEntity.getPictureName());
		returnEntity.setCircularList(circularList);
		
		return returnEntity;
	}
	
	
	/**
	 * 修改图片标记信息
	 * @param pictureInfo
	 */
	@RequestMapping(value = "/updatePicture", method = RequestMethod.GET)
	public void updatePicture(@RequestParam String pictureInfo){
		
		JSONObject obj = JSONObject.parseObject(pictureInfo);
		Long id = Long.valueOf(obj.get("id").toString());
		PictureUploadEntity pictureUploadEntity = pictureUploadService.getById(id);
		
		String befString = pictureUploadEntity.getSignCircle();
		
		String newString = obj.get("circularList").toString();
		
		String updateString = befString.substring(0, befString.length() - 1) + "," + newString.substring(1, newString.length());
		
		pictureUploadEntity.setSignCircle(updateString);
		
		pictureUploadService.save(pictureUploadEntity);
		
	}

}
