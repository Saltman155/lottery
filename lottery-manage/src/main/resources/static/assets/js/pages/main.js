
jQuery(function() {
    $("#exit-system-btn").on("click",$.exitSystem);
    $("#load-activity-btn").on("click",$.loadActivityList);
    $("#load-user-btn").on("click",$.loadUserBtn);
    $("#script-btn").on("click",$.scriptBtn);
    // $("#activity-register-btn").on("click",)
});



$.exitSystem = function(){
    console.log("退出登录");
    $.ajax({
        type:'GET',
        url:'/lottery/admin/exit',
        success:function () {
            window.location.href = "./login.html";
        },
        error:function () {
            console.log("退出异常...");
            window.location.href = "./login.html";
        }
    })
}

$.loadActivityList = function(){
    console.log("载入活动列表");
    $("#main-container").load("activity_list.html");
}

$.loadUserBtn = function(){
    console.log("载入用户列表");
    $("#main-container").load("user_list.html");
}


$.activityRegister = function(){
    console.log("载入活动注册");
    // $("#main-container").load("index.html");
}

$.scriptBtn = function () {
    console.log("载入策略列表");
    $("#main-container").load("script_list.html");
}
