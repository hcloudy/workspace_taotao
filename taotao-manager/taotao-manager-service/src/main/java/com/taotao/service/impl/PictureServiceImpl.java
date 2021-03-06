package com.taotao.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FastDFSClient;
import com.taotao.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService{
	@Value("${IMAGE_SERVER_BASE_USL}")
	private String IMAGE_SERVER_BASE_USL;

	@Override
	public PictureResult uploadPic(MultipartFile picFile) {
		PictureResult pictureResult = new PictureResult();
		if(picFile.isEmpty()) {
			pictureResult.setError(1);
			pictureResult.setMessage("图片为空");
			return pictureResult;
		}
		try {
			//取图片的扩展名
			String originalFilename = picFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			FastDFSClient fc = new FastDFSClient("classpath:properties/client.conf");
			String url = fc.uploadFile(picFile.getBytes(), extName);
			url = IMAGE_SERVER_BASE_USL + url;
			pictureResult.setError(0);
			pictureResult.setUrl(url);
		} catch (Exception e) {
			pictureResult.setError(1);
			pictureResult.setMessage("图片上传失败");
			e.printStackTrace();
		}
		return pictureResult;
	}
	
}
