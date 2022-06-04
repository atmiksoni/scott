package com.flyme.weixin.core.handler;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.flyme.weixin.core.builder.TextBuilder;
import com.flyme.weixin.core.service.WeixinService;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class MsgHandler extends AbstractHandler {

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {

		WeixinService weixinService = (WeixinService) wxMpService;

		if (!wxMessage.getMsgType().equals(WxConsts.XML_MSG_EVENT)) {
			// TODO 可以选择将消息保存到本地
		}
		// TODO 组装回复消息
		String content = "";
		// 当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
		if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服") && weixinService.hasKefuOnline()) {
		
			return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
		}
		if (StringUtils.startsWithAny(wxMessage.getContent(), "openId")) {
			content=wxMessage.getFromUser();
		}

		return new TextBuilder().build(content, wxMessage, weixinService);

	}

}
