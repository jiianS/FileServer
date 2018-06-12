package com.gudi.file.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gudi.file.dao.FileDaoInterface;
import com.gudi.util.HttpUtil;

@Service
public class FileService implements FileServiceInterface {
	
	@Autowired 
	FileDaoInterface fdi;
	
	@Override
	public HashMap<String, Object> fileUpload(MultipartFile[] files, String dir, HttpServletRequest req) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		System.out.println(param);
		
		
		for(int i = 0; i < files.length; i++) {
			HashMap<String, Object> fileMap = new HashMap<String, Object>();
			String fileNm = files[i].getOriginalFilename();
			
			try {
				byte[] bytes = files[i].getBytes();
//				String path = "D:/GDJ10/IDE/workspace/FileServer/src/main/webapp/resources/" + dir + "/";
				String path = "/var/www/html/resources/" + dir + "/";	//리눅스의 웹서버 -> 아파치가 갖고 있는 절대경로
//				String path = req.getSession().getServletContext().getRealPath("/") + "resources/" + dir + "/";
				//String dns = "http://gudi.iptime.org:10010/";	// 내가 올리고자하는 주소_부여받은 주소!
				String dns = "http://gudi.iptime.org:10010/";	// 내가 올리고자하는 주소_부여받은 주소!
				
				File dirF = new File(path);
				
				if(!dirF.exists()) {
					dirF.mkdirs();
				}
				
				File f = new File(path + fileNm);
				OutputStream out = new FileOutputStream(f);
				out.write(bytes);
				out.close();
				
				fileMap.put("fileName", fileNm);
				fileMap.put("filePath", path);
				fileMap.put("fileURL", dns + "resources/" + dir + "/" + fileNm);
				fileMap.put("boardNo", param.get("boardNo"));
				fileMap.put("userNo",  param.get("userNo"));
				/**dao 관련하여 작성되는 부분******************************************/
				
				fdi.insert(fileMap);
				
				/**************************************************************/
				
				list.add(fileMap);				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		map.put("upload", list);
		
		return map;
	}

}
