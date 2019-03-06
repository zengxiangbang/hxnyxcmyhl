//jsdoc ../../../mui/js/platform.js -d ../../../mui/js/out

(function (window) {
    //兼容ios方法
    //声明JavascriptBridge的方法
    function setupWebViewJavascriptBridge(callback) {
        if (navigator.userAgent.indexOf('10086APP') == -1 || window.cmcc_url) {
            console.info("非app内的wk下");
            return;
        }
        if (window.WebViewJavascriptBridge) {
            return callback(WebViewJavascriptBridge);
        }
        if (window.WVJBCallbacks) {
            return window.WVJBCallbacks.push(callback);
        }
        window.WVJBCallbacks = [callback];
        var WVJBIframe = document.createElement('iframe');
        WVJBIframe.style.display = 'none';
        WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
        document.documentElement.appendChild(WVJBIframe);
        setTimeout(function () {
            document.documentElement.removeChild(WVJBIframe)
        }, 0)
    }

    var us = window.navigator.userAgent;
    /**
     * @class app86Platform
     * @description
     *平台类
     * @constructor app86Platform
     * @return {Void}
     * @example
     * var appPlatform=new app86Platform();
     */
    var platform = function () {

    }

    platform.prototype = {
        us: us,
        call: function (funcname, params, callback) {

        },
        /**
         *
         * @inner
         * @description
         *注册一个js方法，供客户端调用
         * @method register
         * @for app86Platform
         * @param {String} funcname 方法名
         * @param {Function} func 方法实体
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.register('get',function(){});
         *
         */
        register: function (funcname, func) {
            if (typeof  funcname != 'string') {
                console.info("缺失函数名称");
                return;
            }
            if (typeof  func != 'function') {
                console.info("缺失函数实体");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.cmcc_url) {
                    window[funcname] = func;
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.registerHandler(funcname, func);
                    })
                }
            } else {
                console.info("非app内");
                return;
            }

        },
        /**
         * @inner
         * @description
         *打开新webview
         * @function openWebview
         * @param  {Array} params [title,url,false] <br />
         * 标题  url  是否全屏，即去掉导航栏。false不去掉导航；true去掉导航
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.openWebview(['标题','http://wx.10085.cn',false]);
         *
         */
        openWebview: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 3) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.cmcc_url && window.cmcc_url.gotoURL) {
                    window.cmcc_url.gotoURL(params[0], params[1], params[2]);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('gotoURL', params);
                    })
                }
            } else {
                window.location.href = params[1];
            }
        },
        /**
         * @inner
         * @description
         *获取手机明文省编码
         * @function getProvince
         * @param  {Function} callback 回调函数 <br />
         * 回调函数内，可以获取到省编码p
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.getProvince(function(p){
         * //打印省份
         * console.info(p);
         * });
         *
         */
        getProvince: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_phone && window.g_phone.sendPhoneToH5) {
                    var a = window.g_phone.sendPhoneToH5(),
                        b = a.split(","),
                        p = b[1];
                    callback && callback(p);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('sendPhoneToH5', null, function (response) {
                            var p = response.split(",")[1];
                            callback && callback(p);
                        });
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *获取加密手机号
         * @function getEncryptionPhoneNumber
         * @param  {Function} callback 回调函数 <br />
         * 回调函数内，可以获取加密手机号
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.getEncryptionPhoneNumber(function(encodePhoneNumber){
         * //打印加密手机号
         * console.info(encodePhoneNumber);
         * });
         *
         */
        getEncryptionPhoneNumber: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                var encodePhoneNumber = '';
                //加密id
                if (window.g_phone && window.g_phone.getEncryptionPhoneNumber) {
                    encodePhoneNumber = window.g_phone.getEncryptionPhoneNumber();
                    callback && callback(encodePhoneNumber);
                }
                else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('getEncryptionPhoneNumber', [], function (res) {
                            encodePhoneNumber = res;
                            callback && callback(encodePhoneNumber);
                        });
                    })
                }
            } else {
                console.info("非app内");
                return;
            }

        },
        /**
         * @inner
         * @description
         *获取基础信息
         * @function getAllBaseInfor
         * @param  {Array} params ['留言模块id',true] <br />
         * 留言模块id、是否过滤默认昵称:false不过滤，默认昵称如用户1111
         * @param  {Function} callback 回调函数 <br />
         * 回调函数内，获取基础信息
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.getAllBaseInfor(['info',false],function(response){
         * //打印获取到的基础信息json对象
         * console.info(response);
         * });
         *
         */
        getAllBaseInfor: function (params, callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                var response = {};
                if (window.g_utils) {
                    //是否wlan
                    if (window.g_utils && window.g_utils.isCurrentWiFi) {
                        response.isWifi = window.g_utils.isCurrentWiFi();
                    }
                    //是否移动中
                    if (window.g_utils && window.g_utils.isMoving) {
                        response.isMove = window.g_utils.isMoving();
                    }
                    //加密id
                    if (window.g_phone && window.g_phone.getEncryptionPhoneNumber) {
                        response.encryptionPhoneNumber = window.g_phone.getEncryptionPhoneNumber();
                    }
                    //留言灰度
                    if (window.g_comment && window.g_comment.leaveOpenGray) {    //新版本兼容
                        response.liuyanShowFLag = window.g_comment.leaveOpenGray(params[0]);
                    } else if (window.g_comment && window.g_comment.commentShow) {   //老版本兼容
                        response.liuyanShowFLag = window.g_comment.commentShow();
                    }
                    //头像
                    if (window.g_utils && window.g_utils.obtainUserIcon) {
                        response.usericon = window.g_utils.obtainUserIcon();
                        var userImg = new Image();
                        userImg.src = response.usericon;
                        userImg.onerror = function () {
                            response.usericon = ""
                        };
                    }
                    if (response.usericon == "null" || !(response.usericon)) {
                        response.usericon = "";
                    }
                    //省份
                    if (window.g_phone && window.g_phone.sendPhoneToH5) {
                        var a = window.g_phone.sendPhoneToH5(),
                            b = a.split(","),
                            c = b[0],
                            p = b[1];
                        response.prov = p;
                    }

                    //昵称,是否过滤默认昵称:false不过滤，默认昵称如用户1111
                    if (window.g_utils && window.g_utils.getNickName) {
                        response.nickName = window.g_utils.getNickName(params[1]);
                    }
                    if (!response.nickName) {
                        response.nickName = "用户" + c.substr(7);
                    }

                    callback && callback(response);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('getAllReturnValue', params, function (res) {
                            response = JSON.parse(res);

                            var userImg = new Image();
                            userImg.src = response.usericon;
                            userImg.onerror = function () {
                                response.usericon = ""
                            };
                            if (response.usericon == "null" || !(response.usericon)) {
                                response.usericon = "";
                            }


                            if (!response.nickName) {
                                response.nickName = "用户" + response.num.substr(7);
                            }
                            response.num = "";
                            callback && callback(response);
                        });
                    })
                }
            } else {
                console.info("非app内");
                return;
            }

        },
        /**
         * @inner
         * @description
         *触发任务中心，写留言任务
         * @function dispatchCommentTask
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.dispatchCommentTask();
         *
         */
        dispatchCommentTask: function () {
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_task && window.g_task.onComment) {
                    window.g_task.onComment();
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('onComment');
                    })
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *触发任务中心资讯分享任务
         * @function dispatchShareTask
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.dispatchShareTask();
         *
         */
        dispatchShareTask: function () {
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_task && window.g_task.onCommentShare) {
                    window.g_task.onCommentShare();
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('onCommentShare');
                    })
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *进入电台
         * @function dispatchListenerAudioTask
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.dispatchListenerAudioTask();
         *
         */
        dispatchListenerAudioTask: function () {
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_task && window.g_task.onListenerAudio) {
                    window.g_task.onListenerAudio();
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('onListenerAudio');
                    })
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *触发任务中心收藏资讯任务
         * @function dispatchCollectionTask
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.dispatchCollectionTask();
         *
         */
        dispatchCollectionTask: function () {
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_task && window.g_task.onCollection) {
                    window.g_task.onCollection();
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('onCollection');
                    })
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *调用原生服务
         * @function openServiceOnMobile
         * @param  {Array} params ['androd','ios','source'] <br />
         * 安卓类、ios类、来源业务信息字符串，不能用中文。
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * 调用我的收藏服务
         * appPlatform.openServiceOnMobile(['zz.dela.cmcc.traffic.activity.CollectionActivity','CMMyFavoriteViewController','infoshoucang']);
         *
         */
        openServiceOnMobile: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 3) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_service && window.g_service.clickServiceOnMobile) {
                    window.g_service.clickServiceOnMobile(params[0], params[1], params[2]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('clickServiceOnMobile', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *获取加密手机号和省编码
         * @function getEncodeProvince
         * @param  {Function} callback 回调函数 <br />
         * 回调函数内，可以获取加密手机号和省编码
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.getEncodeProvince(function(p){
         * //打印加密串
         * console.info(p);
         * });
         *
         */
        getEncodeProvince: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.phone && window.phone.obtainPhoneAndProvince) {
                    var p = window.phone.obtainPhoneAndProvince();
                    callback && callback(p);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('obtainPhoneAndProvince', null, function (res) {
                            callback && callback(res);
                        });
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *只获取加密手机号
         * @function getEncodePhone
         * @param  {Function} callback 回调函数 <br />
         * 回调函数内，可以获取加密手机号
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.getEncodePhone(function(p){
         * //打印加密串
         * console.info(p);
         * });
         *
         */
        getEncodePhone: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.phone && window.phone.obtainPhone) {
                    var p = window.phone.obtainPhone();
                    callback && callback(p);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('obtainPhone', null, function (res) {
                            callback && callback(res);
                        });
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *调用分享
         * @function dispatchShare
         * @param  {Array} params [str] <br />
         * 分享信息参数，加密的json字符串，具体参数描述见集成文档。
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.dispatchShare([encodeURIComponent('{"activityId":711,"type":0,"normaltem":{"id":-1,"name":"10086","title":"10086","url":"http://wx.10086.cn/gfms/11/shaiwanglingyingdajiang/shaiwanglingshare.html?s…ctivityId=799&biaoqian=1078&baifenhao=16&shoujihao=188****1985&xinyu=32611","content":"非法第三方的的是非得失是否的"},"specialList":[]}')]);
         *
         */
        dispatchShare: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_share && window.g_share.clickOnAndroid) {
                    window.g_share.clickOnAndroid(params[0]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('clickOnAndroid', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *调用图片预览
         * @function startShowImageActivity
         * @param  {Array} params [oriUrl, imgArry] <br />
         * 要打开的图片地址；要切换预览的图片数组，必须包含本次要打开的图片地址。
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.startShowImageActivity(['https://forum.online-cmcc.cn/bbs/mui/images/logo_10086.png',['https://forum.online-cmcc.cn/bbs/mui/images/logo_10086.png','https://forum.online-cmcc.cn/bbs/mui/images/logo_10086.png']]);
         *
         */
        startShowImageActivity: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 2) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.imageListener && window.imageListener.startShowImageActivity) {
                    window.imageListener.startShowImageActivity(params[0], params[1]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('startShowImageActivity', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         * 唤醒手机通讯录</br>
         *
         * 获取用户所选联系人信息,需定义getPhoneNumberResult,
         *当用户完成选择联系人操作后会自动调用该方法，手机号和姓名以参数形式传递过来
         * （若该联系人有多个号码，则会自动弹窗提示用户选择一个手机号）
         * 如：
         * var appPlatform=new app86Platform();
         * appPlatform.register('getPhoneNumberResult',function(userNumber, userName){
                //这里是业务信息
            });
         *
         * @function readContactsNumber
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.readContactsNumber();
         *
         */
        readContactsNumber: function () {
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_contacts && window.g_contacts.readContactsNumber) {
                    window.g_contacts.readContactsNumber();
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('readContactsNumber');
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *唤醒短信,发送短信
         * @function sendsmsMessage
         * @param  {Array} params [sendtext,sendphone] <br />
         * sendphone：当手机号为多个的时候中间以“;”隔开，例：sendphone=“10086；10085”；eg：['sendtextTest','10086;10085']
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.sendsmsMessage(['sendtextTest','10086;10085']);
         *
         */
        sendsmsMessage: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 2) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_contacts && window.g_contacts.sendsmsMessage) {
                    window.g_contacts.sendsmsMessage(params[0], params[1]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('sendsmsMessage', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *关闭当前webview，返回
         * @function doFinish
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.doFinish();
         *
         */
        doFinish: function () {
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_finish && window.g_finish.doFinish) {
                    window.g_finish.doFinish();
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('doFinish');
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *跳转知识库详情页面
         * @function clickQueryOnMobile
         * @param  {Array} params [str] <br />
         * 带参数的完整详情页地址。
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * var str = newProtocol+"//" + newHost + '/gfms/11/kefu/xiangqing.html?question_id=' + m + "&params=" + params + "&versionCode="+versionCode;
         * appPlatform.clickQueryOnMobile([str]);
         *
         */
        clickQueryOnMobile: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_query && window.g_query.clickQueryOnMobile) {
                    window.g_query.clickQueryOnMobile(params[0]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('clickQueryOnMobile', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *打开新webview，可以控制状态栏有无
         * @function gotoURLWithStatusBarConfig
         * @param  {Array} params [title,url,false,true] <br />
         * 标题；  url； 是否全屏，即去掉导航栏：false不去掉导航，true去掉导航；是否保留状态栏，true保留状态栏；false去掉状态栏
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.gotoURLWithStatusBarConfig(['标题','http://wx.10085.cn',false,true]);
         *
         */
        gotoURLWithStatusBarConfig: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 4) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.cmcc_url && window.cmcc_url.gotoURLWithStatusBarConfig) {
                    window.cmcc_url.gotoURLWithStatusBarConfig(params[0], params[1], params[2], params[3]);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('gotoURLWithStatusBarConfig', params);
                    })
                }
            } else {
                window.location.href = params[1];
            }
        },
        /**
         * @inner
         * @description
         *使用原生客户端右上角分享，调用该方法传递给客户端分享参数
         * @function clickShareOnMobile
         * @param  {Array} params [str] <br />
         * 分享信息参数，加密的json字符串，具体参数描述见集成文档。
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.clickShareOnMobile([encodeURIComponent('{"activityId":711,"type":0,"normaltem":{"id":-1,"name":"10086","title":"10086","url":"http://wx.10086.cn/gfms/11/shaiwanglingyingdajiang/shaiwanglingshare.html?s…ctivityId=799&biaoqian=1078&baifenhao=16&shoujihao=188****1985&xinyu=32611","content":"非法第三方的的是非得失是否的"},"specialList":[]}')]);
         *
         */
        clickShareOnMobile: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_share && window.g_share.clickShareOnMobile) {
                    window.g_share.clickShareOnMobile(params[0]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('clickShareOnMobile', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *设置原生导航栏title
         * @function setPageTitle
         * @param  {Array} params [str] <br />
         * 要设置的标题
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.setPageTitle(['标题1']);
         *
         */
        setPageTitle: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_title && window.g_title.setPageTitle) {
                    window.g_title.setPageTitle(params[0]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('setPageTitle', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         * 单一渠道分享卡片
         * @function onSingleShare
         * @param  {Array} params [0,"测试内容", "测试标题", "https://www.baidu.com/", "https://www.baidu.com/img/bd_logo1.png"] <br />
         * 分享渠道，0微信，1朋友圈，2qq，3qq空间，4微博，5短信；分享内容；分享标题 ；分享url；分享图标
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.onSingleShare([0,"测试内容", "测试标题", "https://www.baidu.com/", "https://www.baidu.com/img/bd_logo1.png"]);
         *
         */
        onSingleShare: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 5) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_share && window.g_share.onSingleShare) {
                    window.g_share.onSingleShare(params[0], params[1], params[2], params[3], params[4]);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('onSingleShare', params);
                    })
                }
            } else {
                window.location.href = params[1];
            }
        },
        /**
         * @inner
         * @description
         * 单一渠道分享图片
         * @function onSingleShareImage
         * @param  {Array} params [0,"base64图片数据"] <br />
         * 分享渠道，0微信，1朋友圈，2qq，3qq空间，4微博，5短信；base64图片数据
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.onSingleShareImage([0,$("#qrcode img").attr("src").replace("data:image/png;base64,", "")]);
         *
         */
        onSingleShareImage: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 2) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_share && window.g_share.onSingleShareImage) {
                    window.g_share.onSingleShareImage(params[0], params[1]);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('onSingleShareImage', params);
                    })
                }
            } else {
                window.location.href = params[1];
            }
        },
        /**
         * @inner
         * @description
         * 获取用户昵称
         * @function getNickName
         * @param  {Array} params [false] <br />
         * 昵称,是否过滤默认昵称:false不过滤，默认昵称如用户1111
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.getNickName([false],function(p){
         * //打印昵称
         * console.info(p);
         * });
         *
         */
        getNickName: function (params, callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_utils && window.g_utils.getNickName) {
                    var a = window.g_utils.getNickName(params[0]);
                    callback && callback(a);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('getNickName', params, function (response) {
                            var p = response;
                            callback && callback(p);
                        });
                    })
                }
            } else {
                window.location.href = params[1];
            }
        },
        /**
         * @inner
         * @description
         *返回用户头像链接
         * @function obtainUserIcon
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.obtainUserIcon(function(p){
         * //打印头像
         * console.info(p);
         * });
         */
        obtainUserIcon: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_utils && window.g_utils.obtainUserIcon) {
                    var a = window.g_utils.obtainUserIcon();
                    callback && callback(a);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('obtainUserIcon', null, function (response) {
                            var p = response;
                            callback && callback(p);
                        });
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         * 获取留言灰度
         * @function leaveOpenGray
         * @param  {Array} params ['faqapp'] <br />
         * 留言模块id
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.leaveOpenGray(['faqapp'],function(p){
         * //打印灰度
         * console.info(p);
         * });
         *
         */
        leaveOpenGray: function (params, callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_comment && window.g_comment.leaveOpenGray) {
                    var a = window.g_comment.leaveOpenGray(params[0]);
                    callback && callback(a);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('leaveOpenGray', params, function (response) {
                            var p = response;
                            callback && callback(p);
                        });
                    })
                }
            } else {
                window.location.href = params[1];
            }
        },
        /**
         * @inner
         * @description
         *展示与否，右上角客服图标入口；默认不展示
         * @function showCustomService
         * @param  {Array} params [true, "25"] <br />
         * //展示与否；来源业务编码，即当前业务
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.showCustomService([true,"25"]);
         *
         */
        showCustomService: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 2) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.cmcc_url && window.cmcc_url.showKeFu) {
                    window.cmcc_url.showKeFu(params[0], params[1]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('showKeFu', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *传递给留言悬浮框，主副标题，供独立留言页使用
         * @function getPageTitleInfo
         * @param  {Array} params [encodeURIComponent("宽带预约"),encodeURIComponent("移动光宽带，一键预约，上门办理")] <br />
         * //编码的，留言主副标题
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.getPageTitleInfo([encodeURIComponent("宽带预约"),encodeURIComponent("移动光宽带，一键预约，上门办理")]);
         *
         */
        getPageTitleInfo: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 2) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_leaveMessage && window.g_leaveMessage.getPageTitleInfo) {
                    window.g_leaveMessage.getPageTitleInfo(params[0], params[1]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('getPageTitleInfo', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *刷新副号列表
         * @function refreshList
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.refreshList();
         *
         */
        refreshList: function () {
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_info && window.g_info.refreshList) {
                    window.g_info.refreshList();
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('refreshList');
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *隐藏右上角分享图标
         * @function hiddenShareIcon
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.hiddenShareIcon();
         *
         */
        hiddenShareIcon: function () {
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_share && window.g_share.hiddenShareIcon) {
                    window.g_share.hiddenShareIcon();
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('hiddenShareIcon');
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        getHeduohaoRSAPhoneNumber: function (params, callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_phone && window.g_phone.getHeduohaoRSAPhoneNumber) {
                    var a = window.g_phone.getHeduohaoRSAPhoneNumber(params[0]);
                    callback && callback(a);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('getHeduohaoRSAPhoneNumber', params, function (response) {
                            var p = response;
                            callback && callback(p);
                        });
                    })
                }
            } else {
                window.location.href = params[1];
            }
        },
        getHeduohaoMD5Sign: function (params, callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_phone && window.g_phone.getHeduohaoMD5Sign) {
                    var a = window.g_phone.getHeduohaoMD5Sign(params[0]);
                    callback && callback(a);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('getHeduohaoMD5Sign', params, function (response) {
                            var p = response;
                            callback && callback(p);
                        });
                    })
                }
            } else {
                window.location.href = params[1];
            }
        },
        gotoPointExchangeDetail: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 2) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_info && window.g_info.gotoPointExchangeDetail) {
                    window.g_info.gotoPointExchangeDetail(params[0], params[1]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('gotoPointExchangeDetail', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *控制web页面bounce效果
         * @function isWebBounce
         * @param  {Array} params [false] <br />
         * true可以回弹、false不可以回弹
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.isWebBounce([false]);
         *
         */
        isWebBounce: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_utils && window.g_utils.isWebBounce) {
                    window.g_utils.isWebBounce(params[0]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('isWebBounce', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *获取状态栏高度
         * @function getStatusBarHeight
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.getStatusBarHeight(function(p){
         * //打印
         * console.info(p);
         * });
         */
        getStatusBarHeight: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_info && window.g_info.getStatusBarHeight) {
                    var a = window.g_info.getStatusBarHeight();
                    callback && callback(a);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('getStatusBarHeight', null, function (response) {
                            var p = response;
                            callback && callback(p);
                        });
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *是否开启沉浸式阅读
         * @function setNeedScrollHide
         * @param  {Array} params [true] <br />
         * true开启、false不开，默认不开
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.setNeedScrollHide([true]);
         *
         */
        setNeedScrollHide: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.cmcc_news && window.cmcc_news.setNeedScrollHide) {
                    window.cmcc_news.setNeedScrollHide(params[0]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('setNeedScrollHide', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        showMoreIcon: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.cmcc_url && window.cmcc_url.showMoreIcon) {
                    window.cmcc_url.showMoreIcon(params[0]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('showMoreIcon', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        decryptByRSA: function (params, callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_utils && window.g_utils.decryptByRSA) {
                    var a = window.g_utils.decryptByRSA(params[0]);
                    callback && callback(a);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('decryptByRSA', params, function (response) {
                            var p = response;
                            callback && callback(p);
                        });
                    })
                }
            } else {
                window.location.href = params[1];
            }
        },
        openClientGraffiti: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_graffiti && window.g_graffiti.openClientGraffiti) {
                    window.g_graffiti.openClientGraffiti(params[0]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('openClientGraffiti', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        openNewClientGraffiti: function () {
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_graffiti && window.g_graffiti.openNewClientGraffiti) {
                    window.g_graffiti.openNewClientGraffiti();
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('openNewClientGraffiti');
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *发起检测语音权限
         * @function validateVoiceAbility
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.validateVoiceAbility();
         *
         */
        validateVoiceAbility: function () {
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_service && window.g_service.validateVoiceAbility) {
                    window.g_service.validateVoiceAbility();
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('validateVoiceAbility');
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *短语音按钮操作调用方法
         * @function operationVoiceLog
         * @param  {Array} params [type] <br />
         * type :1开始说话；2完成说话；3按着移到外边不松开；4、按着移到外边松开，取消说话5说话不超过一秒钟,6划出去再划回来，7没触发hold
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.operationVoiceLog([1]);
         *
         */
        operationVoiceLog: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_service && window.g_service.operationVoiceLog) {
                    window.g_service.operationVoiceLog(params[0]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('operationVoiceLog', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *设备是否在移动中
         * @function isMoving
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.isMoving(function(p){
         * //打印
         * console.info(p);
         * });
         */
        isMoving: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_utils && window.g_utils.isMoving) {
                    var a = window.g_utils.isMoving();
                    callback && callback(a);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('isMoving', null, function (response) {
                            var p = response;
                            callback && callback(p);
                        });
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *当前网络是否是Wi-Fi
         * @function isCurrentWiFi
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.isCurrentWiFi(function(p){
         * //打印
         * console.info(p);
         * });
         */
        isCurrentWiFi: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_utils && window.g_utils.isCurrentWiFi) {
                    var a = window.g_utils.isCurrentWiFi();
                    callback && callback(a);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('isCurrentWiFi', null, function (response) {
                            var p = response;
                            callback && callback(p);
                        });
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        getTextSize: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.cmcc_text_size && window.cmcc_text_size.getTextSize) {
                    var a = window.cmcc_text_size.getTextSize();
                    callback && callback(a);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('getTextSize', null, function (response) {
                            var p = response;
                            callback && callback(p);
                        });
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        getNewsTextSize: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.cmcc_text_size && window.cmcc_text_size.getNewsTextSize) {
                    var a = window.cmcc_text_size.getNewsTextSize();
                    callback && callback(a);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('getNewsTextSize', null, function (response) {
                            var p = response;
                            callback && callback(p);
                        });
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *设置资讯字体大小
         * @function setNewsTextSize
         * @param  {Array} params [0] <br />
         * 0，1，2，3档位
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.setNewsTextSize([1]);
         *
         */
        setNewsTextSize: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.cmcc_text_size && window.cmcc_text_size.setNewsTextSize) {
                    window.cmcc_text_size.setNewsTextSize(params[0]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('setNewsTextSize', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        setTopicPKVote: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 2) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.cmcc_topic_pk && window.cmcc_topic_pk.setTopicPKVote) {
                    window.cmcc_topic_pk.setTopicPKVote(params[0],params[1]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('setTopicPKVote', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        getTopicPKInfo: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 1) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.cmcc_topic_pk && window.cmcc_topic_pk.getTopicPKInfo) {
                    window.cmcc_topic_pk.getTopicPKInfo(params[0]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('getTopicPKInfo', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        getXsd: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_phone && window.g_phone.sendPhoneToH5) {
                    var a = window.g_phone.sendPhoneToH5(),
                        b = a.split(","),
                        p = b[0];
                    callback && callback(p);
                } else {
                    setupWebViewJavascriptBridge(function () {
                        WebViewJavascriptBridge.callHandler('sendPhoneToH5', null, function (response) {
                            var p = response.split(",")[0];
                            callback && callback(p);
                        });
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *获取加密手机号和省编码，app48版本以上支持
         * @function obtainPhoneAndProvinceSecurer
         * @param  {Function} callback 回调函数 <br />
         * 回调函数内，可以获取加密手机号和省编码
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.obtainPhoneAndProvinceSecurer(function(p){
         * //打印加密串
         * console.info(p);
         * });
         *
         */
        obtainPhoneAndProvinceSecurer: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.phone && window.phone.obtainPhoneAndProvinceSecurer) {
                    var p = window.phone.obtainPhoneAndProvinceSecurer();
                    callback && callback(p);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('obtainPhoneAndProvinceSecurer', null, function (res) {
                            callback && callback(res);
                        });
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
        /**
         * @inner
         * @description
         *只获取加密手机号,app48版本以上支持
         * @function obtainPhoneSecurer
         * @param  {Function} callback 回调函数 <br />
         * 回调函数内，可以获取加密手机号
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * appPlatform.obtainPhoneSecurer(function(p){
         * //打印加密串
         * console.info(p);
         * });
         *
         */
        obtainPhoneSecurer: function (callback) {
            if (typeof  callback != 'function') {
                console.info("缺失回调函数");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.phone && window.phone.obtainPhoneSecurer) {
                    var p = window.phone.obtainPhoneSecurer();
                    callback && callback(p);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('obtainPhoneSecurer', null, function (res) {
                            callback && callback(res);
                        });
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
		 /**
         * @inner
         * @description
         *调用原生服务，该功能仅支持支持话费账单，一键检测，套餐余量，我的账户功能，3.5.3版本(版本号48)新增，3.5.3版本之后客户端可使用该js方法：
		 *其中sourceId由弹窗配置管理平台获得
         * @function clickServiceWithActivityDialog
         * @param  {Array} params ['androd','ios','source'] <br />
         * 安卓类、ios类、来源业务信息字符串，不能用中文。
         * @return {Void}
         * @example
         * var appPlatform=new app86Platform();
         * 调用我的收藏服务
         * appPlatform.clickServiceWithActivityDialog(['zz.dela.cmcc.traffic.activity.CollectionActivity','CMMyFavoriteViewController','infoshoucang']);
         *
         */
        clickServiceWithActivityDialog: function (params) {
            if (!(params instanceof Array)) {
                console.info("参数必须为数组");
                return;
            }
            if (params.length < 3) {
                console.info("参数数组内元素不足");
                return;
            }
            if (this.us.indexOf('10086APP') > -1) {
                if (window.g_service && window.g_service.clickServiceWithActivityDialog) {
                    window.g_service.clickServiceWithActivityDialog(params[0], params[1], params[2]);
                } else {
                    setupWebViewJavascriptBridge(function (bridge) {
                        WebViewJavascriptBridge.callHandler('clickServiceWithActivityDialog', params);
                    });
                }
            } else {
                console.info("非app内");
                return;
            }
        },
    }

    //支持amd，cmd，变量化
    if (typeof module != 'undefined' && module.exports) {
        module.exports = platform;
    } else if (typeof define == 'function' && define.amd) {
        define(function () {
            return platform;
        });
    } else {
        window.app86Platform = platform;
    }
})(window);
