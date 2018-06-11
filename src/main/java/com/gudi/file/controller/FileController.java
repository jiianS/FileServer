package com.gudi.file.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gudi.file.service.FileServiceInterface;
import com.gudi.util.HttpUtil;

@Controller
public class FileController {
	
	@Autowired
	FileServiceInterface fsi;
	
	@RequestMapping("/")
	public String main() {
		return "main";
	}

	@RequestMapping("/FileUpload/{dir}")
	public void fileUpload(@RequestParam("file") MultipartFile[] files, @PathVariable("dir") String dir, HttpServletRequest req, HttpServletResponse res) {
		HttpUtil.makeJsonWriter(res, fsi.fileUpload(files, dir, req));
	}
}
