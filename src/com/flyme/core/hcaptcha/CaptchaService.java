package com.flyme.core.hcaptcha;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaService {
	
	/**
	 * 生成验证码令牌
	 */
	public String genToken();
	
	/**
	 * 校验token
	 * @param token 被校验的token
	 * @return token中的验证码字符
	 * @throws Exception
	 */
	public String getCaptcha(String token) throws Exception;

	/**
	 * 验证输入的验证码与令牌是否匹配
	 * @param code 输入的验证码
	 * @param token 令牌
	 * @return 匹配结果
	 * @throws Exception 匹配时产生的异常
	 */
	public boolean doVerify(String code, String token);
	
	/**
	 * 根据指定的captcha生成相应的验证码图片
	 */
	public BufferedImage getCaptchaImg(String captcha);
	
	public void getCaptch(HttpServletRequest request, HttpServletResponse response, String token);
	
}
