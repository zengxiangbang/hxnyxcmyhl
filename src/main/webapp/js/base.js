$(function (){
    //调用APP分享方法
    initAPPShareConfig(appimg, appname, apptitle, appurl, appcontent);

    // public business
    //last window
    $(".zbtn").on("click",function(){
        
        $("#zhongjian2").css("zIndex", "-999");
        var lotteryUrl = getWebPath() + "app/lottery.do";
        $.get(lotteryUrl, function(response){
            if(response.status == 10){
                sessionStorage.isLogin = 0;
                toIndexPage()
            }else if(response.status == 1){
                var prizeName = response.data.prizeName;
                var prizeId   = response.data.prizeId;
                if(prizeId > 2){
                    $("#qulingqu").hide();
                }
                var prizeTag
                if(prizeName && prizeId){
                    if(prizeId > 7){

                        if(prizeId == 8){
                            prizeTag = "<img src=\"images/shiwu1.png\" alt=\"\">";
                        }else if(prizeId == 9){
                            prizeTag = "<img src=\"images/shiwu2.png\" alt=\"\">";
                        }else if(prizeId == 10){
                            prizeTag = "<img src=\"images/shiwu3.png\" alt=\"\">";
                        }

                    }else{
                        if(prizeId == 2){
                            prizeName += "+三佳购物优惠券";
                        }
                        prizeTag = "<div class=\"textBg\">\n" +
                            "                <img src=\"images/zjTextBg.png\" alt=\"\">\n" +
                            "                <p id=\"prizeP\">\n" +
                            "                    恭喜您抽中" + prizeName + "好礼，答题不容易，答对看能力，猜灯谜达人就是你！\n" +
                            "                </p>\n" +
                            "            </div>";
                    }
                    $("#pop_btns").before(prizeTag);
                }
            }else if(response.status == 0){
                toErrorPage();
            }
        });

        $("#zhong").css("display", "flex");
    });

    //弹窗控制
    $(".close").on("click", function () {
        $(this).parent().hide();
    });


})


/*********************************APP分享***********************************/
var appimg = getWebPath() + "images/share.jpg";
var appname = "欢喜闹元宵 猜谜赢好礼";
var apptitle = "欢喜闹元宵 猜谜赢好礼";
var appurl = getWebPath() + "index.html?channel=app";
var appcontent = "手机话费抽不停！";
var appPlatform = new app86Platform();//获取平台实例
appPlatform.register('appShareCallBack',function(sdata){
    if(JSON.parse(sdata).state==2){
        var url = getWebPath() + "home/count.do?type=1";
        $.get(url, function(response){
        });
    }
});


/** 10086 APP 分享**/
function APPshareClick() {
    appPlatform.dispatchShare([encodeURIComponent(APPShareStr)]);
}

/** 10086 APP 分享初始化参数**/
function initAPPShareConfig(img, name, title, url, content) {
    var obj = {"activityId":750,"type":0,"normaltem":{"img1":"img1","id":-1,"name":"10086","title":"10086","url":"10086","content":"10086"},"specialList":[]};
    obj.normaltem.img1 = img;
    obj.normaltem.name = name;
    obj.normaltem.title = title;
    obj.normaltem.url = url;
    obj.normaltem.content = content;
    APPShareStr = JSON.stringify(obj);
}

/*********************************APP分享结束***********************************/
var browser = {
    versions: function () {
        var u = navigator.userAgent, app = navigator.appVersion;
        return {// 移动终端浏览器版本信息
            trident: u.indexOf('Trident') > -1, // IE内核
            presto: u.indexOf('Presto') > -1, // opera内核
            webKit: u.indexOf('AppleWebKit') > -1, // 苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, // 火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/), // 是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, // android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, // 是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, // 是否iPad
            webApp: u.indexOf('Safari') == -1,
            weixin: u.toLowerCase().indexOf('micromessenger') > -1,  //微信
            mobike: u.toLowerCase().indexOf('mobike') > -1,  //摩拜 Mobike/5.4.1 (iPhone; iOS 10.2.1; Scale/2.00)

            // 是否web应该程序，没有头部与底部
        };
    }(),
    language: (navigator.browserLanguage || navigator.language).toLowerCase()
}

/**微信APP；浏览器**/
function isweixinbrowser() {
    if (browser.versions == null) {
        return true;
    }
    return (browser.versions.weixin);
}


/**10086APP；浏览器**/
function is10086APP() {
    if (navigator.userAgent.indexOf("10086APP") == -1) {
        return false;
    }
    return true;
}

/** 摩拜APP，浏览器**/
function ismobikeAPP() {
    if (browser.versions == null) {
        return true;
    }
    return (browser.versions.mobike);
}

/** 安卓内核浏览器**/
function isAndroidBrowser() {
    if (browser.versions == null) {
        return true;
    }
    return (browser.versions.android);
}

/**苹果内核浏览器**/
function isAppleBrowser() {
    if (browser.versions == null) {
        return false;
    }
    return (browser.versions.ios || browser.versions.iPad || browser.versions.iPhone);
}

/**手机浏览器**/
function ismobilebrowser() {
    if (browser.versions == null) {
        return true;
    }
    return (browser.versions.ios || browser.versions.iPhone || browser.versions.iPad || browser.versions.android || browser.versions.mobile || browser.versions.weixin);
}

/**验证移动11位手机号数**/
function isMoblePhoneNumber(phoneNumber) {
    var reg = /^1[0-9]{10}$/;
    if (reg.test(phoneNumber)) {
        return true;
    } else {
        return false;
    }
}

/**获取10086APP 手机浏览器号码**/
function get10086APPMobile() {
    var mobile = getUrlParam('mobile');
    if (!mobile) {
        //alert("window.phone:" + window.phone)
        if (window.phone && window.phone.obtainPhone) {
            mobile = window.phone.obtainPhone();//	加密过的手机号
        }
    }
    return mobile;
}

/** url 参数 **/
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r !== null) return unescape(r[2]);
    return '';
}

// 接收数据
function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

/** web  路径**/
function getWebPath() {
    var webName = window.location.protocol;
    webName += "//" + window.location.hostname
    var pathName = document.location.pathname;
    var index = pathName.lastIndexOf("/");
    var result = pathName.substr(0, index + 1);
    return webName + result;
}


/**
 *
 *  json 数组排序
 *
 data ： 排序的数据
 例如：  {
                    result:[
                      {id:1,name:'中国银行'},
                      {id:3,name:'北京银行'},
                      {id:2,name:'河北银行'},
                      {id:10,name:'保定银行'},
                      {id:7,name:'涞水银行'}
                        ]
                 }
 sortFunction ： 排序的方法
 例如：根据Id 排序
 function sortId(a,b){
                    return a.id-b.id
                }
 *  **/
function jsonArraySort(data, sortFunction) {
    return data.data.sort(sortFunction);
}

/**
 * 号码切割，使用*替换切割部分
 * @param phone 手机号
 * @param start 起点
 * @param len 切割替换的长度
 * @returns {string} 切割后的号码
 * @see substring
 */
function substrFT(phone, start, len) {
    function combinatorSymbol(len, symbol) {
        var deFaultSymbol = '*';
        if (symbol) deFaultSymbol = symbol;
        var retSymbol = '';
        for (var i = 0; i < len; i++)
            retSymbol += deFaultSymbol;
        return retSymbol;
    }

    var nextLen = 0;
    if (!len) nextLen = 4;
    else nextLen = len;
    return phone.substring(0, start) + combinatorSymbol(nextLen) + phone.substring(start + nextLen);
}


// 取当前页面名称(不带后缀名)
function pageName() {
    var a = location.href;
    var b = a.split("/");
    var c = b.slice(b.length - 1, b.length).toString(String).split(".");
    return c.slice(0, 1)[0];
}

//取当前页面名称(带后缀名)
function pageNameWithSuffix() {
    var strUrl = location.href;
    var arrUrl = strUrl.split("/");
    var strPage = arrUrl[arrUrl.length - 1];
    return strPage;
}


function toErrorPage() {
    window.location.href = "http://gddqres.richworks.cn/dqtg/wap/1511/451106/congestion/index.html";
}

function toOfflinePage() {
    window.location.href = "http://gddqres.richworks.cn/dqtg/bye/index.html";
}
function toIndexPage() {
    window.location.href = getWebPath() + "index.html";
}
function toSanJiaPage() {
    var toSanJiaPageCount =  getWebPath() + "home/count.do?type=2";
    $.get(toSanJiaPageCount, function(response){
    });
    window.location.href = "http://m.ttcj.tv/ydhz/prm/201901250001";
};