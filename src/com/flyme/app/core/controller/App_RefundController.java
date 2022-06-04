package com.flyme.app.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.flyme.common.util.context.SysConfigUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.thirdsdk.weixin.service.WxAppPayService;

/**
 * 退款管理
 */
@Controller
@RequestMapping("/app")
public class App_RefundController extends MbsBaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WxAppPayService wxAppPayService;
//	@Autowired
//	private IOrderService orderService;

	private String serverName = SysConfigUtil.getConfigByName("serverName");// 服务器地址

	/**
	 * APP退款
	 *//*
	@RequestMapping(value = "refund_sub.rm")
	@Authorization
	public ApiJson refund_sub(@CurrentUser Account account, HttpServletResponse response, HttpServletRequest request) {
		ApiJson j = new ApiJson();
		String orderId = ObjectUtils.getParameter("orderId");
		Order order = orderService.findById(orderId);
		if (ObjectUtils.isEmpty(order)) {
			return JsonUtil.writeError(response, "订单不存在");
		}
		String payType = order.getPayType().name();
		Integer orderType = order.getOrderType().getIndex();
		RefundRequest refundInfo = orderService.getRefund(request, order, orderType);
		switch (payType) {
		case "WxPay":
			WxRefund(refundInfo, order);
			break;
		case "Alipay":
			AliRefund(refundInfo, order);
			break;
		case "Balance":

			break;
		}
		return JsonUtil.writeResponse(response, j);
	}

	*//**
	 * 微信退款
	 *//*
	private ApiJson WxRefund(RefundRequest refundInfo, Order order) {
		ApiJson j = new ApiJson();
		WxPayRefundRequest refundRequest = WxPayRefundRequest.newBuilder().outRefundNo(refundInfo.getOutRefundNo()).outTradeNo(refundInfo.getOutTradeNo()).refundFee(refundInfo.getRefundFee()).totalFee(refundInfo.getTotalFee()).build();
		WxPayRefundResult wxRefundResult = new WxPayRefundResult();
		try {
			wxRefundResult = this.wxAppPayService.refund(refundRequest);
			String return_code = wxRefundResult.getReturnCode();
			if (return_code.equals("SUCCESS")) {
				RefundResult refundResult = new RefundResult();
				orderService.refundSuccess(refundResult);
			}
		} catch (WxPayException e) {
			this.logger.error(e.getErrCodeDes());
		}
		return j;
	}

	*//**
	 * 支付宝退款
	 *//*
	private ApiJson AliRefund(RefundRequest refundInfo, Order order) {
		ApiJson j = new ApiJson();
		AlipayPrams ap = new AlipayPrams();
		String tradeNo = order.getTradeNo();
		String orderNo = order.getOrderNo();
		Integer retrunMoney = refundInfo.getRefundFee();
		Map<String, String> map = ap.getRefundParams(serverName + "/api/alirefund_success.rm", orderNo.substring(1), DateUtil.getDateTime(), "1", tradeNo + "^" + retrunMoney + "^" + "协商退款");// 支付宝交易号^退款金额^备注
		try {
			String aString = AlipaySubmit.buildRequest("", "", map);
			System.out.println(aString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}

	*//**
	 * 支付宝退款成功回调
	 *//*
	@RequestMapping("/alirefund_success.rm")
	@ResponseBody
	public String alirefund_success(HttpServletRequest request) {
		String result = "success";
		RefundCallBack refundCallBack = new RefundCallBack(request);
		if (refundCallBack.getSuccess()) {// 验证通过
			log.info("退款回调成功");
			RefundResult refundResult = new RefundResult();
			orderService.refundSuccess(refundResult);
			result = "success";
		} else {
			result = "failure";
		}
		return result;
	}*/

}