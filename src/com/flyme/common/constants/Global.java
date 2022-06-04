package com.flyme.common.constants;

import java.math.BigDecimal;

/**
 * 全局变量定义
 */
public class Global {
	/* 应用名称 */
	public static final String APPNAME = "国创医院";
	/* 授权key */
	public static final String LICENCEKEY = "bfpms";
	public static final String TOKEN = "token";
	public static final String dialect = "MySql";

	/* 短信请求key */
	public static final String SMSKEY = "hOWt3hiakXHrePCqDKUsPz5T6f7j8P";
	/** 融云key */
	public static final String RONGAPPKEY = "vnroth0kv4nfo";
	/** 融云Secret */
	public static final String RONGAPPSECRET = "gVi9yJZdLI";
	/**
	 * 部署路径属性名称
	 */
	public static final String CONTEXT_PATH = "base";
	public static final String Verion = "version";
	/** 是否开启正式支付 */
	public static final Boolean Pay_Test = true;
	public static final String QRCODE_DEF = "res/app/logo/qrcode.png";
	/* 超管用户名 */
	public static final String SUPERADMIN = "admin";
	/* 分页条 */
	public static final String PAGERSTR = "pager";
	/* 默认密码 */
	public static final String DEFAULTPWD = "888888";
	/* 验证码时效 */
	public static final int SMSTCODEVAILD = 3;
	public static String codeTpl = "您的验证码是{0},请不要把验证码泄露给其他人";

	/* 皮肤 */
	public static String THEME = "flydew";// 皮肤:default,vogue,flydew
	/* 状态 */
	public static final String ENABLE = "是";
	public static final String DISABLE = "否";
	public static final String TRUE = "true";
	public static final String FALSAE = "false";
	/* 积分获取类型 */
	public static final int POINTS_ADD = 1;
	public static final int POINTS_DEL = 2;
	/* 账户余额 */
	public static final int BALANCE_CHARGE = 1;// 充值
	public static final int BALANCE_COST = 2;// 消费
	public static final int BALANCE_CASH = 3;// 提现
	public static final int BALANCE_AWARD = 4;// 分销奖励
	public static final int BALANCE_PUSHMONEY = 5;// 订单提成奖励
	/** 轮播图分类 */
	public static final int SLIDER_APP = 1;
	public static final int SLIDER_PC = 2;

	/** 新闻分类 */
	public static final int NOTICE_GG = 1;
	public static final int NOTICE_DT = 2;
	/* 状态 */
	public static final Integer INT_ENABLE = 1;
	public static final Integer INT_DISABLE = 0;
	/* 状态 */
	public static final String STR_ENABLE = "1";
	public static final String STR_DISABLE = "0";

	/* 使用状态 */
	public static final int NO_USE = 0;// 未使用
	public static final int YES_USE = 1;// 已使用
	public static final int FAIL_USE = 2;// 不可用

	/* 处理状态 */
	public static final int NO_HANDLE = 0;// 未处理
	public static final int PASS_HANDLE = 1;// 处理通过
	public static final int FAIL_HANDLE = -1;// 处理未通过

	/* 消息类型 */
	public static final int MSG_PRIVATE = 1;// 个人
	public static final int MSG_SYSTEM = 2;// 系统
	/* 读取状态 */
	public static final int RED_NO = 0;// 未读
	public static final int RED_YES = 1;// 已读
	/* 删除状态 */
	public static final Integer DEL_NO = 0;
	public static final Integer DEL_YES = 1;

	/* 状态值定义 */
	public static final int INT_0 = 0;
	public static final int INT_1 = 1;
	public static final int INT_2 = 2;
	public static final int INT_3 = 3;
	/* 区域等级 */
	public static final int AREA_LEVEL_1 = 1;// 一级区域
	public static final int AREA_LEVEL_2 = 2;// 二级区域
	public static final int AREA_LEVEL_3 = 3;// 三级区域
	public static final int AREA_LEVEL_4 = 4;// 三级区域
	/* 信息提示 */
	public static final String AJAX_SUCCESS = "y";
	public static final String SUCCESS_OK = "ok";
	public static final String SUCCESS_FAIL = "fail";
	public static final String AJAX_ERROR = "n";
	public static final String WX_SUCCESS = "SUCCESS";
/*	public static final Integer MSGCODE_OK = 100;
	public static final Integer MSGCODE_FAIL = 101;
	public static final Integer TOKEN_FAIL = 102;*/
	
	public static final Integer CODE_OK = 100;
	public static final Integer CODE_FAIL = 101;
	public static final Integer CODE_TOKEN_FAIL = 102;
	
	/* 业务设置参数 */
	public static final String SYSCONFIG_EMAIL = "Email";
	public static final String SYSCONFIG_PHONE = "Phone";
	public static final String SYSCONFIG_ADDRESS = "Address";
	public static final String SYSCONFIG_DISTANCE = "Distance";
	public static final String SYSCONFIG_LOGINPOINTSSTATUS = "LoginPointsStatus";
	public static final String SYSCONFIG_LOGINPOINTS = "LoginPoints";
	
	public static final String SYSCONFIG_PAYPOINTSSTATUS = "PayPointsStatus";
	public static final String SYSCONFIG_PAYPOINTS = "PayPoints";
	
	public static final String SYSCONFIG_RECHARGEPOINTSSTATUS = "RechargePointsStatus";
	public static final String SYSCONFIG_RECHARGEPOINTS = "RechargePoints";

	public static final String PUSH_FIVE = "PUSH_FIVE";
	public static final String PUSH_FORE = "PUSH_FORE";
	public static final String PUSH_THREE = "PUSH_THREE";
	public static final String DRIVER_COUNT = "DriverCount";
	public static final String SHIPPER_COUNT = "ShipperCount";

	/* 经纬度 */
	public static final BigDecimal DEFAULT_LNG=new BigDecimal("112.4344710000");
	public static final BigDecimal DEFAULT_LAT=new BigDecimal("34.6630400000");
	
	/* 评论类型 */
	public static final Integer SHIPPER_EVALUATE = 1;
	public static final Integer DRIVER_EVALUATE = 2;
	
	public static String ResultType_LIST = "list";// 结果集循环转JSON
	public static String ResultType_MAP = "map";// 结果集自动转JSON
	
	//推送环境
	public static boolean PUSH_STATUS = false;// 推送货主
	
	//推送相关
	public static final String PUSH_SYS="SYS";//系统
	public static final String PUSH_MSG="MSG";//消息
	public static final String PUSH_IDENTITY_PASS="IDENTITY_PASS";//认证成功
	public static final String PUSH_IDENTITY_FAIL="IDENTITY_FAIL";//认证失败
	public static final String PUSH_APPOINTMENT = "APPOINTMENT";//预约
	public static final String PUSH_ORDERBUS = "ORDERBUS";//订单
	
	//护士级别
	public static final String NURSE_LEVEL_GRADUATION="graduation";
	public static final String NURSE_LEVEL_PRIMARY="primary";
	public static final String NURSE_LEVEL_INTERMEDIATE="intermediate";
	public static final String NURSE_LEVEL_DEPUTYSENIOR="deputySenior";
	public static final String NURSE_LEVEL_POSITIVEADVANCED="positiveAdvanced";
	public static final String DefalultColor = "00E862EA";
	
	public static final Integer Normal_workTime=0;//正常工资
	public static final Integer OnePointFive_workTime=1;//1.5倍工资
	public static final Integer Dobule_workTime=2;//双倍工资
}
