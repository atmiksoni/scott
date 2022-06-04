package com.flyme.common.json.upload;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.flyme.common.util.ObjectUtils;

/**
 * 上传下载模型类
 * 
 */
public class UploadFile {
	private String byteField = "";// 二进制文件内容保存到数据库的对应实体类字段
	private String titleField = "";// 文件名(标题)保存到数据库的对应实体类字段
	private String uploadPath = "upload";// 文件保存目录根路径
	private String realPath;// 文件保存在硬盘的全路径对应实体字段
	private String extend = "extend";// 扩展名
	private boolean view = false;// 是否是预览
	private boolean rename = true;// 是否重命名
	private String swfpath;// 转换SWF
	private String cusPath;// 文件物理路径自定义子目录
	private String cusFileName;// 文件自定义名称
	private byte[] content;// 预览或下载时传入的文件二进制内容
	public Object object;// 文件对应实体对象
	private String fileKey;// 上传文件ID
	private String fileName;// 文件名
	private String filePath;// 文件路径
	public MultipartHttpServletRequest multipartRequest;
	private MultipartFile multipartFile;
	Map<String, MultipartFile> fileMap;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String mediaIds;// 微信图片ID
	private Boolean saveLocal = true;

	public UploadFile(HttpServletRequest request, Object object) {
		// 文件ID
		String fileKey = ObjectUtils.getParameter("fileKey");
		if (ObjectUtils.isNotEmpty(fileKey)) {
			this.fileKey = fileKey;
			this.request = request;
		} else {
			this.multipartRequest = (MultipartHttpServletRequest) request;
			this.fileMap = this.multipartRequest.getFileMap();
			init();

		}
		this.object = object;
	}

	public UploadFile(HttpServletRequest request) {
		String folder = ObjectUtils.getParameter(request, "folder");
		if (ObjectUtils.isNotEmpty(folder)) {
			/* 设置自定义上传目录(前端传递) */
			this.setCusPath(folder);
		}
		this.multipartRequest = (MultipartHttpServletRequest) request;
		init();

	}
	private void init() {
		this.multipartFile = multipartRequest.getFile("file");
		if (!ObjectUtils.isEmpty(multipartFile)) {
			this.fileName = multipartFile.getOriginalFilename();
		}
	}
	public Map<String, MultipartFile> getFileMap() {
		return fileMap;
	}

	public void setFileMap(Map<String, MultipartFile> fileMap) {
		this.fileMap = fileMap;
	}

	public UploadFile(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public UploadFile() {

	}

	public String getSwfpath() {
		return swfpath;
	}

	public void setSwfpath(String swfpath) {
		this.swfpath = swfpath;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public MultipartHttpServletRequest getMultipartRequest() {
		return multipartRequest;
	}

	public String get(String name) {
		return getMultipartRequest().getParameter(name);

	}

	public void setMultipartRequest(MultipartHttpServletRequest multipartRequest) {
		this.multipartRequest = multipartRequest;
	}

	public Object getObject() {
		return object;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getByteField() {
		return byteField;
	}

	public void setByteField(String byteField) {
		this.byteField = byteField;
	}

	public String getTitleField() {
		return titleField;
	}

	public void setTitleField(String titleField) {
		this.titleField = titleField;
	}

	public String getCusPath() {
		return cusPath;
	}

	public void setCusPath(String cusPath) {
		this.cusPath = cusPath;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public boolean isRename() {
		return rename;
	}

	public void setRename(boolean rename) {
		this.rename = rename;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCusFileName() {
		return cusFileName;
	}

	public void setCusFileName(String cusFileName) {
		this.cusFileName = cusFileName;
	}
	public String getMediaIds() {
		return mediaIds;
	}

	public void setMediaIds(String mediaIds) {
		this.mediaIds = mediaIds;
	}

	public Boolean getSaveLocal() {
		return saveLocal;
	}

	public void setSaveLocal(Boolean saveLocal) {
		this.saveLocal = saveLocal;
	}

}
