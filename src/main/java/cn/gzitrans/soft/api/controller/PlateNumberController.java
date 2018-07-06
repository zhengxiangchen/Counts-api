package cn.gzitrans.soft.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gzitrans.soft.api.service.PictureUploadService;

@RestController
@RequestMapping("${basepath}/plateNumber")
public class PlateNumberController {
	
	public static Logger logger = LogManager.getLogger(PictureController.class);
	
	@Autowired
	private PictureUploadService pictureUploadService;
	
	
	/**
	 * 检查小程序传过来的用户openid以及批次号或车次号是否已经存在
	 * @param openId
	 * @param plateNumber
	 * @return
	 */
	@RequestMapping(value = "/checkPlateNumber", method = RequestMethod.GET)
	public boolean checkPlateNumber(@RequestParam String openId, @RequestParam String plateNumber){
		logger.info("小程序:[用户openid] = " + openId + ",[批次号plateNumber] = " + plateNumber);
		boolean res = pictureUploadService.checkPlateNumber(openId,plateNumber);
		return res;
	}
	
	
	/**
	 * 根据小程序传过来的用户openid以及批次号或车次号删除相应记录
	 * @param plateNumber
	 * @return
	 */
	@RequestMapping(value = "/deletePlate", method = RequestMethod.GET)
	public String deletePlate(@RequestParam String openId, @RequestParam String plateNumber){
		logger.info("小程序:[用户openid] = " + openId + ",[批次号plateNumber] = " + plateNumber + ",删除相应记录");
		pictureUploadService.deletePlate(openId,plateNumber);
		return "success";
	}

}
