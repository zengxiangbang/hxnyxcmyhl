$(function () {
    var problem = [{
        id: 0,
        num: 4,
        answer: '有机可乘',
        fontLibrary: '一、有、天、鸡、乘、客、可、机、飞、冲',
        prompt: ["", "", "", "乘"]
    }, {
        id: 1,
        num: 2,
        answer: '人中',
        fontLibrary: '天、人、气、穴、虎、明、门、口、心、中',
        prompt: ["人", ""]
    }, {
        id: 2,
        num: 2,
        answer: '丘陵',
        fontLibrary: '鲁、丘、秋、孔、陵、日、子、土、草、位',
        prompt: ["丘", ""]
    }, {
        id: 3,
        num: 2,
        answer: '桂圆',
        fontLibrary: '月、枝、梨、圆、丹、凤、桂、荔、毛、园',
        prompt: ["", "圆"]
    }, {
        id: 4,
        num: 2,
        answer: '雨伞',
        fontLibrary: '水、衣、雪、台、人、雨、屋、窗、伞、批',
        prompt: ["雨", ""]
    }, {
        id: 5,
        num: 2,
        answer: '青蛙',
        fontLibrary: '蛙、燕、蜻、子、青、蜓、娃、鱼、水、草',
        prompt: ["青", ""]
    }, {
        id: 6,
        num: 4,
        answer: '始终不渝',
        fontLibrary: '不、终、都、未、渝、至、矢、始、志、临',
        prompt: ["", "", "", "渝"]

    }, {
        id: 7,
        num: 3,
        answer: '穿山甲',
        fontLibrary: '开、老、甲、天、川、山、兽、穿、洞、虎',
        prompt: ["", "", "甲"]
    }, {
        id: 8,
        num: 3,
        answer: '担担面',
        fontLibrary: '但、日、面、见、酸、担、担、杂、小、粉',
        prompt: ["担", "", ""]
    }, {
        id: 9,
        num: 2,
        answer: '秘鲁',
        fontLibrary: '国、鲁、美、本、秘、日、法、英、密、泰',
        prompt: ["", "鲁"]
    }, {
        id: 10,
        num: 4,
        answer: '蠢蠢欲动',
        fontLibrary: '瓜、怂、动、玉、蠢、笨、欲、蠢、二、则',
        prompt: ["", "", "", "动"]
    }, {
        id: 11,
        num: 4,
        answer: '改天换地',
        fontLibrary: '迁、土、天、还、改、地、动、惊、明、换',
        prompt: ["", "", "换", ""]
    }, {
        id: 12,
        num: 2,
        answer: '悉尼',
        fontLibrary: '本、顿、尼、亚、印、西、悉、利、日、希',
        prompt: ["悉", ""]
    }, {
        id: 13,
        num: 3,
        answer: '大白兔',
        fontLibrary: '免、佳、高、白、太、格、大、兔、力、步',
        prompt: ["", "白", ""]
    }, {
        id: 14,
        num: 2,
        answer: '绝缘',
        fontLibrary: '电、喙、光、绝、分、体、发、缘、开、放',
        prompt: ["", "缘"]
    }]

    var question_type = [
        {
            q1 : [0,1,2,3,4],
            q2 : [5,6,7,8,9],
            q3 : [10,11,12,13,14]
        },
        {
            q1 : [14,12,10,8,6],
            q2 : [4,2,0,1,3],
            q3 : [5,7,9,11,13]
        },
        {
            q1 : [1,3,5,7,9],
            q2 : [11,13,14,12,10],
            q3 : [8,6,4,2,0]
        }
    ];
    var date = new Date();
    var day = date.getDate();
    var type = sessionStorage.questionType;
    var question = question_type[0];
    if(type){
        type = parseInt(type);
        question = question_type[type];
    }
    var userProblem = question.q1; //这里传入用户所答问题
    if(day == 17){
        userProblem = question.q2;
    }else if(day == 18){
        userProblem = question.q3;
    }
    var numProblem = 0;

    var thisProblem = userProblem[numProblem];



    var onNum = 0; //输入文字个数
    var thisFonts = ''; //谜题输入结果
    var num = '';
    var promptType = false;








    function init(n) {
        
        //谜底几个字
        num = problem[thisProblem].num;

        onNum = 0;
        thisFonts = '';

        $(".subject img").attr("src", "images/t" + n + ".png");
        $(".optionBox img").attr("src", "images/op" + n + ".png");
        $(".bottomBox .m" + num + " span").each(function () {
            $(this).text("")
        })

        $(".m2").hide();
        $(".m3").hide();
        $(".m4").hide();
        if (num == 2) {
            $(".m2").show();
        } else if (num == 3) {
            $(".m3").show()
        } else if (num == 4) {
            $(".m4").show();
        } else {
            console.log("problem num 错误")
        }
    }
    init(thisProblem)

    $(".optionItem span").on("click", function () {
        var thisFontLibrary = problem[thisProblem].fontLibrary.split('、');
        var index = $(this).index();
        var thisFont = thisFontLibrary[index];
        $(".m" + num + " span").eq(onNum).text(thisFont);
        thisFonts += thisFont;
        if (onNum == problem[thisProblem].num-1) {
            $(".submitBtn").removeClass('notBtn')
        }
        if (onNum == problem[thisProblem].num) {
            $(".m" + num + " span").each(function () {
                $(this).text("")
            })
            onNum = 0;
            thisFonts = '';
            $(".submitBtn").addClass('notBtn')
            // $(".m" + num + " span").eq(onNum).text(thisFont);
            // thisFonts += thisFont;
            // onNum = onNum + 1;
        } else {
            onNum = onNum + 1;
        }
        console.log(thisFonts)
    })

    $(".submitBtn").on("click", function () {
        if (onNum != problem[thisProblem].num) {
            return false
        }
        var answers = problem[thisProblem].answer;
        console.log()
        if (thisFonts == answers) {
            console.log(numProblem)
            console.log(userProblem.length)
            if (numProblem == userProblem.length - 1) {
                submitFn()
            } else {
                numProblem = numProblem + 1;
                thisProblem = userProblem[numProblem];
                init(thisProblem);
                $(".submitBtn").addClass('notBtn');
                promptType = false;

            }
        } else {

            if (sessionStorage.helpTime == 0) {
                $("#wrongPop").show();
            } else {
                $("#wrongPop2").show();
            }

            $(".bottomBox .m" + num + " span").each(function () {
                $(this).text("");
                onNum = 0;
                thisFonts = '';
                $(".submitBtn").addClass('notBtn');
            })
        }
    });
    //跳转回答题页提示
    function showPromptType(){
        promptType = true;
        $("#promptPop").hide();
        $("#lookPop").css("display", "flex");
        var Prompts = problem[thisProblem].prompt;
        $(".look .m" + num + " span").each(function () {
            var indexs = $(this).index();
            $(this).text(Prompts[indexs]);
        });
        //更新求助次数
        var url = getWebPath() + "app/help.do";
        $.get(url, function(response){
            if(response.status == 10){
                sessionStorage.isLogin = 0;
                toIndexPage()
            }else if(response.status == 302){
                toOfflinePage();
            }else if(response.status == 1){
                sessionStorage.helpTime = parseInt(sessionStorage.helpTime) + 1;
            }
        });
    };


    //提示
    $(".promptBox").on("click", function () {

        if(promptType){
            $("#lookPop").css("display", "flex");
            var Prompts = problem[thisProblem].prompt;
            $(".look .m" + num + " span").each(function () {
                var indexs = $(this).index();
                $(this).text(Prompts[indexs]);
            });
        } else{
            if(sessionStorage.helpTime == 0){
                $("#promptPop").css("display", "flex");
            }else if(sessionStorage.helpTime >= 3){
                errorPop("今天的求助次数已用完")
            }else{
                task(sessionStorage.helpTime);
                showPromptType();
            }
        }
    });
    $(".lookPrompt").on("click", function () {
        //一键检测
        task(0);
        showPromptType();
    });

    $(".closePrompt").on("click", function () {
        $("#promptPop").hide();
    })

    var isSubmit = 0;
    //数据提交
    function submitFn() { //答完题
        if(isSubmit == 0){
            //ajax
            var submitUrl = getWebPath() + "/app/submit.do";
            $.get(submitUrl, function(response){
                if(response.status == 10){
                    sessionStorage.isLogin = 0;
                    toIndexPage();
                }else if(response.status == 302){
                    toOfflinePage();
                }else if(response.status == 1){
                }

            });
        }
        $("#zhongjian2").css("display", "flex");
        $("#zhongjian2").css("zIndex", "999");
        isSubmit = 1;
    }


    function task(time) {
        var class_name_android = "zz.dela.cmcc.traffic.activity.OneKeyCheckActivity";
        var class_name_ios = "OneKeyCheckoutController";


        if(time == 1){
            class_name_android = "zz.dela.cmcc.traffic.activity.DetailsPhoneActivity";
            class_name_ios = "MyFeeDetailViewController";
        }else if(time == 2){
            class_name_android = "zz.dela.cmcc.traffic.activity.TaoCanYuLiangActivity";
            class_name_ios = "MyPacketLeftController";
        }

        var appPlatform = new app86Platform();//获取平台实例
        appPlatform.openServiceOnMobile([class_name_android, class_name_ios, "hxnyxcmyhl"]);
    };
    //分享
    $("#share").on("click", function (){
        APPshareClick();
    });

    function errorPop(text){
        $("#errorPop .textBg2 p").text(text)
        $("#errorPop").css("display","flex")
    }

    $("#riddlesClose").on("click", function (){
        toIndexPage();
    });

    $(".ps_tishi").on("click", function (){
        $('.ps_tishi').hide();
    });


});