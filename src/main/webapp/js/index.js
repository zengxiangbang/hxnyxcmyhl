$(function () {

    //登录
    login();
    //抽奖

    $(".ruleBtn").on("click", function () {
        $("#ruleBox").css("display","flex");
    });


    $(".prizeBtn").on("click", function () {
        $("#prizeBox").css("display","flex");
        var url = getWebPath() + 'app/prize.do';
        $.ajax({
            type: 'GET',
            url: url,
            data: {},
            dataType: 'json',
            context: $('body'),
            success: function(result){
                
                if(result.status == 1){
                    if(result.data){
                        if(result.data.length == 0){
                            $(".pop .cont ul").hide();
                            $("#noList").css("display","block;");
                        }else{
                            prizeList(result.data);
                        }
                    }
                }else if(result.status == 10){
                    sessionStorage.isLogin = 0;
                    toIndexPage()
                }else if(result.status == 302){
                    //活动下线
                    toOfflinePage()
                }else{
                    toErrorPage();
                }
            },
            error: function(xhr, type){
                toErrorPage();
            }
        })
    });


    //开始猜谜
    $("#indoxBtn").on("click", function () {
        var url = getWebPath() + 'app/guess.do';
        $.ajax({
            type: 'GET',
            url: url,
            data: {},
            dataType: 'json',
            context: $('body'),
            success: function(result){
                
                if(result.status == 1){
                    if(result.data.isSuccess == 1 && result.data.isLottery == 1){
                        $("#noTime").css("display","flex");
                    }else if(result.data.isSuccess == 1 && result.data.isLottery == 0 ){
                        $("#zhongjian2").css("zIndex", "999");
                    }else if(result.data.isSuccess == 0 && result.data.isLottery == 0) {
                        window.location.href = getWebPath() + "riddles.html?num=" + Math.random();
                    }else{
                        toErrorPage();
                    }
                    sessionStorage.helpTime = result.data.helpTime;
                }else if(result.status == 10){
                    sessionStorage.isLogin = 0;
                    toIndexPage()
                }else if(result.status == 302){
                    //活动下线
                    toOfflinePage()
                }else{
                    toErrorPage();
                }
            },
            error: function(xhr, type){
                toErrorPage();
            }
        })
    });

    //遍历奖品列表
    function prizeList(list) {
        $(".pop .cont ul").html("");
        var tag = "";

        $.each(list, function(index, item){
            var haveSanjia = false;
            if(item.prizeId == 1){
                haveSanjia = true;
            }
            tag =
                "<li>" +
                "                    <i>" + item.prizeName + "</i>" +
                "                    <span onclick=\"toSanJiaPage()\" class=\"btns\">" +
                "                        <img src=\"images/list_btn_" + haveSanjia + ".png\" alt=\"\">" +
                "                    </span>" +
                "                </li>";

            $(".pop .cont ul").append(tag);
        });

    }

    //分享
    $("#share").on("click", function (){
        APPshareClick();
    });

    $("#noTimeShare").on("click", function (){
        APPshareClick();
    });

});
function login() {

    if(!is10086APP()) {
        $("#noApp").css("display", "flex");
        return;
    }


    var versionCode = getUrlParam("versionCode");//app客户端打开H5的url时，拼接的公共参数包括版本号。
    appPlatform = new app86Platform();//获取平台实例， 这是全局的获取sdk实例，整个h5提前调用一次就行,其他方法全部从该实例引出，app86Platform这个全局对象定义于上面sdk文件中。
    if(versionCode >= 48){
        appPlatform.obtainPhoneSecurer(function(res){      //获取加密手机号

            var type = 2;
            myLogin(res,type);
        })
    }else{
        appPlatform.getEncodePhone(function(res){      //获取加密手机号
            var type = 1;
            myLogin(res,type);
        })
    }

};
function myLogin(mobile, type) {

    if(!sessionStorage.isLogin || sessionStorage.isLogin == 0){
        var url = getWebPath() + '/home/login.do';

        $.ajax({
            type: 'POST',
            url: url,
            data: {
                "mobile":mobile,
                "type": type
            },
            dataType: 'json',
            context: $('body'),
            success: function(result){
                //登录成功
                if(result.status == 1){
                    sessionStorage.isLogin = 1;
                    if(result.data.questionType){
                        sessionStorage.questionType = result.data.questionType;
                    }
                }else if(result.status == 20){
                    $("#noActiveUser").css("display","flex");
                } else if(result.status == 302){
                    toOfflinePage();
                }else{
                    toErrorPage();
                }
            },
            error: function(){
                toErrorPage();
            }
        })
    }
}

