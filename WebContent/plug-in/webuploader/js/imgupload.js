Upload = function(option) {
	var uploader;
	var me = this;
	me.form = "";
	me.uploadSuccess = function(file, data) {
	};
	me.onFileDequeued = function(file) {
	};
	me.uploadBeforeSend = function(object, data, headers) {
		
	};
	var fileList = option.fileList;
	var list = $('#' + fileList);
	var info = $('.msg'); // 文件总体选择信息。
	var statusBar = $('.statusBar'); // 状态栏，包括进度
	var state = 'pedding';
	var progress = $('.progress'); // 总体进度条
	var fileCount = 0; // 添加的文件数量
	var fileSize = 0;// 添加的文件总大小
	option.fileVal ? option.fileVal = option.fileVal : option.fileVal = 'file';
	var percentages = {};// 所有文件的进度信息，key为file id
	var fileNumLimit = (option.num == "" || option.num == null) ? 300 : option.num;
	var accept = {
		title : '选择文件',
		extensions : 'gif,jpg,jpeg,bmp,png,txt,doc,xls,xlsx',
		mimeTypes : 'image/jpg,image/jpeg,image/png'
	};
	if (option.accept == "xls") {
		accept = {
			title : '选择文件',
			extensions : 'xls,xlsx',
			mimeTypes : 'application/xls,application/xlsx'
		}
	}
	if (option.accept == "vedio") {
		accept = {
			title : '选择视频',
			extensions : 'flv,avi,mp4,mp3,wma,gif,jpg,jpeg,bmp,png',
			mimeTypes : 'video/x-flv,video/x-msvideo,video/mp4,audio/mpeg,audio/x-ms-wma,image/*'
		}
	}
	
	if (option.accept == "app") {
		accept = {
			title : '选择安装包',
			extensions : 'apk',
			mimeTypes : 'application/vnd.android.package-archive'
		}
	}

	// 上传文件总大小限制 500M
	var fileSizeLimit = 1024 * 1024 * 500;
	// 单个文件限制大小 100M
	var fileSingleSizeLimit = 1024 * 1024 * 100;
	// 优化retina, 在retina下这个值是2
	var ratio = window.devicePixelRatio || 1;
	// 缩略图大小
	var thumbnailWidth = 100 * ratio;
	var thumbnailHeight = 100 * ratio;
	var supportTransition = (function() {
		var s = document.createElement('p').style, r = 'transition' in s || 'WebkitTransition' in s || 'MozTransition' in s || 'msTransition' in s || 'OTransition' in s;
		s = null;
		return r;
	})();

	if (!WebUploader.Uploader.support()) {
		alert('上传控件不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
		throw new Error('WebUploader does not support the browser you are using.');
	}

	uploader = WebUploader.create({
		auto : true,
		pick : {
			id : '#' + option.filePicker
		},
		paste : document.body,
		accept : accept,
		swf : '../../plug-in/webuploader/js/Uploader.swf',
		disableGlobalDnd : false,
		chunked : false,
		fileVal : option.fileVal,
		server : option.server,
		formData : option.params,
		fileNumLimit : fileNumLimit,// 上传文件数量限制
		fileSizeLimit : fileSizeLimit,
		fileSingleSizeLimit : fileSingleSizeLimit
	});

	// 当有文件添加进来的时候
	uploader.on('fileQueued', function(file) {
		if(option.num<2){
			$("#"+file.id).remove();
		}		
		var $div = $('<div id="' + file.id + '" class="file-item thumbnail">' + '<p class="imgWrap"></p>' + '<p class="progress"><span></span></p>' + '<p class="title" style="max-width:100px;">' + file.name + '</p>' + '</div>'),

		$btns = $('<div class="file-panel" style="background: #01B7EE;opacity:0.8;">' + '<span class="cancel">删除</span></div>').appendTo($div), $prgress = $div.find('p.progress span'), $wrap = $div.find('p.imgWrap'), $info = $('<p class="error"></p>'),

		showError = function(code) {
			switch (code) {
			case 'exceed_size':
				text = '文件大小超出';
				break;
			case 'interrupt':
				text = '上传暂停';
				break;
			default:
				text = '上传失败，请重试';
				break;
			}
			$info.text(text).appendTo($div);
		};

		if (file.getStatus() === 'invalid') {
			showError(file.statusText);
		} else {
			$wrap.text('预览中');
			uploader.makeThumb(file, function(error, src) {
				if (error) {
					$wrap.html('<br><br>不能预览');
					// $wrap.text('<br><br>不能预览');
					return;
				}
				var img = $('<img src="' + src + '">');
				$wrap.empty().append(img);
			}, thumbnailWidth, thumbnailHeight);
			percentages[file.id] = [ file.size, 0 ];
			file.rotation = 0;
		}

		file.on('statuschange', function(cur, prev) {
			if (prev === 'progress') {
				$prgress.hide().width(0);
			} else if (prev === 'queued') {
				$div.off('mouseenter mouseleave');
			}

			// 成功
			if (cur === 'error' || cur === 'invalid') {
				console.log(file.statusText);
				showError(file.statusText);
				percentages[file.id][1] = 1;
			} else if (cur === 'interrupt') {
				showError('interrupt');
			} else if (cur === 'queued') {
				percentages[file.id][1] = 0;
			} else if (cur === 'progress') {
				$prgress.css('display', 'block');
			} else if (cur === 'complete') {
				$div.append('<span class="success"></span>');
			}
			$div.removeClass('state-' + prev).addClass('state-' + cur);
		});

		$div.on('mouseenter', function() {
			$btns.stop().animate({
				height : 30
			});
		});

		$div.on('mouseleave', function() {
			$btns.stop().animate({
				height : 0
			});
		});

		$btns.on('click', 'span', function() {
			var index = $(this).index(), deg;
			switch (index) {
			case 0:
				uploader.removeFile(file);
				return;
			}

			if (supportTransition) {
				deg = 'rotate(' + file.rotation + 'deg)';
				$wrap.css({
					'-webkit-transform' : deg,
					'-mos-transform' : deg,
					'-o-transform' : deg,
					'transform' : deg
				});
			} else {
				$wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
			}

		});

		// $li.appendTo( $queue );

		list.append($div);
	});

	// 文件上传过程中创建进度条实时显示。
	uploader.on('uploadProgress', function(file, percentage) {
		var li = $('#' + file.id), percent = li.find('.progress span');

		/* 避免重复创建 */
		if (!percent.length) {
			percent = $('<p class="progress"><span></span></p>').appendTo(li).find('span');
		}

		percent.css('width', percentage * 100 + '%');
	});
	/* 文件上传前事件。 */
	uploader.on('uploadBeforeSend', function(object, data, headers) {
		if (option.formId) {
			// $.extend(data, Fm.serialize(option.formId));
		}
		me.uploadBeforeSend(object, data, headers);
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on('uploadSuccess', function(file, response) {
		$('#' + file.id).addClass('upload-state-done');
		$('#' + fileList + 'Hidden').val(response.filePath);
		me.uploadSuccess(file, response);

	});
	uploader.onFileQueued = function(file) {
		fileCount++;
		fileSize += file.size;

		if (fileCount === 1) {
			statusBar.show();
		}
		setState('ready');
		updateTotalProgress();
	};

	uploader.onFileDequeued = function(file) {
		fileCount--;
		fileSize -= file.size;

		if (!fileCount) {
			setState('pedding');
		}

		removeFile(file);
		updateTotalProgress();
		me.onFileDequeued(file);
	};

	// 文件上传失败，现实上传出错。
	uploader.on('uploadError', function(file) {
		var li = $('#' + file.id), error = li.find('div.error');

		// 避免重复创建
		if (!error.length) {
			error = $('<div class="error"></div>').appendTo(li);
		}

		error.text('上传失败');
	});

	/* 完成上传完了，成功或者失败，先删除进度条。 */
	uploader.on('uploadComplete', function(file) {
		$('#' + file.id).find('.progress').remove();
	});
	/* 全部文件完成上传 */
	uploader.on('uploadFinished', function(file) {
		if (option.formId) {
			/* 手动执行表单提交跳转 */
			me.form.submit();
		}
	});
	function updateTotalProgress() {
		var loaded = 0, total = 0, spans = progress.children(), percent;

		$.each(percentages, function(k, v) {
			total += v[0];
			loaded += v[0] * v[1];
		});

		percent = total ? loaded / total : 0;

		spans.eq(0).text(Math.round(percent * 100) + '%');
		spans.eq(1).css('width', Math.round(percent * 100) + '%');
		updateStatus();
	}
	function setState(val) {
		var file, stats;
		if (val === state) {
			return;
		}
		state = val;

		switch (state) {
		case 'ready':
			statusBar.removeClass('element-invisible');
			uploader.refresh();
			break;
		}
		updateStatus();
	}
	function updateStatus() {
		var text = '', stats;
		if (state === 'ready') {
			text = '选中' + fileCount + '个文件，共' + WebUploader.formatSize(fileSize) + '。';
		} else if (state === 'confirm') {
			stats = uploader.getStats();
			if (stats.uploadFailNum) {
				text = '已成功上传' + stats.successNum + '张照片至XX相册，' + stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
			}

		} else {
			stats = uploader.getStats();
			text = '共' + fileCount + '张（' + WebUploader.formatSize(fileSize) + '），已上传' + stats.successNum + '张';

			if (stats.uploadFailNum) {
				text += '，失败' + stats.uploadFailNum + '张';
			}
		}

		info.html(text);
	}

	// 负责view的销毁
	function removeFile(file) {
		var $li = $('#' + file.id);
		delete percentages[file.id];
		updateTotalProgress();
		$li.off().find('.file-panel').off().end().remove();
	}

	me.upload = function(form) {
		me.form = form;
		uploader.upload();
	};
	return me;
}