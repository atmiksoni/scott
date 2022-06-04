(function() {
	/*
	 * 将相同代码拆出来方便维护
	 */
	window.RongDemo = {
		common : function(WebIMWidget, config, $scope) {
			WebIMWidget.init(config);

			/*
			 * WebIMWidget.setUserInfoProvider(function(targetId, obj) {
			 * obj.onSuccess({ name: "用户：" + targetId }); });
			 */

			WebIMWidget.setGroupInfoProvider(function(targetId, obj) {
				obj.onSuccess({
					name : '群组：' + targetId
				});
			})

			$scope.setconversation = function() {
				if (!!$scope.targetId) {
					WebIMWidget.setConversation(Number($scope.targetType), $scope.targetId, "用户：" + $scope.targetId);
					WebIMWidget.show();
				}
			};

			// $scope.customerserviceId = "KEFU145914839332836";
			$scope.setcustomerservice = function() {
				WebIMWidget.setConversation(Number(RongIMLib.ConversationType.CUSTOMER_SERVICE),
						$scope.customerserviceId);
				WebIMWidget.show();
			}

			$scope.show = function() {
				WebIMWidget.show();
			};

			$scope.hidden = function() {
				WebIMWidget.hidden();
			};

			WebIMWidget.show();

			// 示例：获取 userinfo.json 中数据，根据 targetId 获取对应用户信息
			WebIMWidget.setUserInfoProvider(function(targetId, obj) {
				$.ajax({
					url : "get_rong_userinfo.hm",
					async : false,
					data : {
						userInfoId : targetId
					},
					success : function(result) {
						obj.onSuccess({
							id : targetId,
							name : result.object.name,
							portraitUri : "../"+result.object.portraitUri
						});
					}
				});
			});

			// 示例：获取 online.json 中数据，根据传入用户 id 数组获取对应在线状态
			// WebIMWidget.setOnlineStatusProvider(function(arr, obj) {
			// $http({
			// url: "/online.json"
			// }).success(function(rep) {
			// obj.onSuccess(rep.data);
			// })
			// });
		}
	}

})();



function startCall(){
	
	var WebIM = RongIMClient.getInstance();

	var mediaType_vedio = RongIMLib.VoIPMediaType.MEDIA_VEDIO; //视频通话
	var mediaType_audio = RongIMLib.VoIPMediaType.MEDIA_AUDIO; //音频通话

	var converType = RongIMLib.ConversationType.PRIVATE;
	var targetId = "37F356589F4F42DA96233BC74266ABF2";
	var invertUserIds = ["FD890F2106554C04A4347FA03C6B3D48"];
	var extra = "";
	
	WebIM.startCall(converType,targetId,invertUserIds,mediaType_vedio,extra,{
		  onSuccess:function(){
		     // => startCall successfully
		  },
		  onError:function(err){
			  console.log("视频发起失败：错误码"+err);
		     // => startCall error
		  }
		});
}



function initIMCall(){
	var options = {
			  container : {
			    local: null //local 为放置视频窗口的 DOM 节点
			  }
			};
	RongIMLib.RongCallLib.init(options);
	showTips("初始化 音频库" + getTimer(begin)); 
	
	RongIMClient.setOnReceiveMessageListener({
	    onReceived: function(message) {
	        switch (message.messageType) {
	          case RongIMClient.MessageType.InviteMessage:
	            // 收到音视频通话邀请
	          break;
	          case RongIMClient.MessageType.SummaryMessage:
	            // 结束语音通话后收到
	          break;
	          case RongIMClient.MessageType.RingingMessage:
	            // 响铃消息
	          break;
	          case RongIMClient.MessageType.AcceptMessage:
	            // 同意接听音视频通话消息
	          break;
	          case RongIMClient.MessageType.MediaModifyMessage:
	            // 视频转音频消息
	          break;
	        }
	    }
	  });
}