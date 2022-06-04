utils = {
	setParam : function(name, value) {
		localStorage.setItem(name, value)
	},
	removeItem : function(name, value) {
		localStorage.removeItem(name)
	},
	getParam : function(name) {
		return localStorage.getItem(name)
	}
}
product = {
	goodsId : 0,
	name : "",
	goodsCount : 0,
	icon : "",
	price : 0.00,
};
SC = {
	// 向购物车中添加商品
	add : function(product) {
		var ShoppingCart = utils.getParam("ShoppingCart");
		if (ShoppingCart == null || ShoppingCart == "") {
			// 第一次加入商品
			var jsonstr = {
				"productlist" : [ {
					"goodsId" : product.goodsId,
					"name" : product.name,
					"icon" : product.icon,
					"goodsCount" : product.goodsCount,
					"price" : product.price
				} ],
				"totalNumber" : product.goodsCount,
				"totalAmount" : (product.price * product.goodsCount)
			};
			utils.setParam("ShoppingCart", "'" + JSON.stringify(jsonstr));
		} else {
			var jsonstr = JSON.parse(ShoppingCart.substr(1, ShoppingCart.length));
			var productlist = jsonstr.productlist;
			var result = false;
			// 查找购物车中是否有该商品
			for ( var i in productlist) {
				if (productlist[i].goodsId == product.goodsId) {
					productlist[i].goodsCount = parseInt(productlist[i].goodsCount) + parseInt(product.goodsCount);
					result = true;
				}
			}
			if (!result) {
				// 没有该商品就直接加进去
				productlist.push({
					"goodsId" : product.goodsId,
					"name" : product.name,
					"goodsCount" : product.goodsCount,
					"icon" : product.icon,
					"price" : product.price
				});
			}
			// 重新计算总价
			jsonstr.totalNumber = parseInt(jsonstr.totalNumber) + parseInt(product.goodsCount);
			jsonstr.totalAmount = parseFloat(jsonstr.totalAmount) + (parseInt(product.goodsCount) * parseFloat(product.price));
			// 保存购物车
			utils.setParam("ShoppingCart", "'" + JSON.stringify(jsonstr));
		}
	},
	// 修改给买商品数量
	update : function(goodsId, goodsCount) {
		if (goodsCount > 0) {
			var ShoppingCart = utils.getParam("ShoppingCart");
			var jsonstr = JSON.parse(ShoppingCart.substr(1, ShoppingCart.length));
			var productlist = jsonstr.productlist;

			for ( var i in productlist) {
				if (productlist[i].goodsId == goodsId) {
					jsonstr.totalNumber = parseInt(jsonstr.totalNumber) + (parseInt(goodsCount) - parseInt(productlist[i].goodsCount));
					jsonstr.totalAmount = parseFloat(jsonstr.totalAmount) + ((parseInt(goodsCount) * parseFloat(productlist[i].price)) - parseInt(productlist[i].goodsCount) * parseFloat(productlist[i].price));
					productlist[i].goodsCount = parseInt(goodsCount);
					utils.setParam("ShoppingCart", "'" + JSON.stringify(jsonstr));
					return;
				}
			}
		} else {
			this.del(goodsId);
		}
	},
	// 获取购物车中的所有商品
	getData : function() {
		var ShoppingCart = utils.getParam("ShoppingCart");
		var jsonstr = JSON.parse(ShoppingCart.substr(1, ShoppingCart.length));
		var productlist = jsonstr.productlist;
		if (productlist) {
			$("#shop_car_foot").show();
			$(".numtag").html("");
			$(".numtag").removeClass("angle");
			for ( var i in jsonstr.productlist) {
				var goodsCount = productlist[i].goodsCount;
				var goodsId = productlist[i].goodsId;
				if (goodsCount > 0) {
					$("#angle" + goodsId).html("x" + goodsCount);
					$("#angle" + goodsId).addClass("angle");
				}
			}
		}
		if (productlist && productlist.length == 0) {
			$("#shop_car_foot").hide();
			$("#pop_shopcar").hide();
			$("#mark").hide();
		}
		return jsonstr;
	},
	// 判断购物车中是否存在商品
	exist : function(goodsId) {
		var ShoppingCart = utils.getParam("ShoppingCart");
		var jsonstr = JSON.parse(ShoppingCart.substr(1, ShoppingCart.length));
		var productlist = jsonstr.productlist;
		var result = false;
		for ( var i in productlist) {
			if (productlist[i].goodsId == product.goodsId) {
				result = true;
			}
		}
		return result;
	},
	// 删除购物车中商品
	del : function(goodsId) {
		var ShoppingCart = utils.getParam("ShoppingCart");
		var jsonstr = JSON.parse(ShoppingCart.substr(1, ShoppingCart.length));
		var productlist = jsonstr.productlist;
		var list = [];
		for ( var i in productlist) {
			if (productlist[i].goodsId == goodsId) {
				jsonstr.totalNumber = parseInt(jsonstr.totalNumber) - parseInt(productlist[i].goodsCount);
				jsonstr.totalAmount = parseFloat(jsonstr.totalAmount) - parseInt(productlist[i].goodsCount) * parseFloat(productlist[i].price);
			} else {
				list.push(productlist[i]);
			}
		}
		jsonstr.productlist = list;
		utils.setParam("ShoppingCart", "'" + JSON.stringify(jsonstr));
	},
	// 清空购物车中商品
	clearAll : function() {
		var jsonstr = {
			"productlist" : [],
			"totalNumber" : 0,
			"totalAmount" : 0
		};
		utils.setParam("ShoppingCart", "'" + JSON.stringify(jsonstr));
	}
};