
//vue框架
var vm = new Vue({

    el:'#page-container',
    methods:{
        //退出登录
        loginOut:function (event) {
            console.log("退出登录");
            $.ajax({
                type:'GET',
                url:'/lottery/admin/exit',
                success:function () {
                    window.location.href = "./login.html";
                },
                error:function () {
                    console.log("退出异常...")
                    window.location.href = "./login.html";
                }
            })
        }
    }

})