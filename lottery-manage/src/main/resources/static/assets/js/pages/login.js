/*
 *  Document   : base_pages_login.js
 *  Author     : pixelcave
 *  Description: Custom JS code used in Login Page
 */

var BasePagesLogin = function() {
    // Init Login Form Validation, for more examples you can check out https://github.com/jzaefferer/jquery-validation
    var initValidationLogin = function(){
        jQuery('.js-validation-login').validate({
            errorClass: 'help-block text-right animated fadeInDown',
            errorElement: 'div',
            errorPlacement: function(error, e) {
                jQuery(e).parents('.form-group > div').append(error);
            },
            highlight: function(e) {
                jQuery(e).closest('.form-group').removeClass('has-error').addClass('has-error');
                jQuery(e).closest('.help-block').remove();
            },
            success: function(e) {
                jQuery(e).closest('.form-group').removeClass('has-error');
                jQuery(e).closest('.help-block').remove();
            },
            rules: {
                'login-username': {
                    required: true,
                    email:true
                },
                'login-password': {
                    required: true,
                    passwordCheck: true
                }
            },
            messages: {
                'login-username': {
                    required: '请输入登录邮箱！',
                    email: '请输入正确的邮箱！'
                },
                'login-password': {
                    required: '请输入用户密码！',
                    minlength: '请输入正确的密码！'
                }
            },
            //是否在获取焦点时验证
            onfocusout:false,
            //是否在敲击键盘时验证
            onkeyup:false,
            //提交表单后，（第一个）未通过验证的表单获得焦点
            focusInvalid:true,
            //当未通过验证的元素获得焦点时，移除错误提示
            focusCleanup:false,
            //捕获提交事件
            submitHandler:function(form){
                console.log("提交登录请求");
                var formData = {
                    "email" : null,
                    "password" : null,
                    "explainTime" : null
                };
                formData.email = $("#login-username").val();
                formData.password = $("#login-password").val();
                var checked = document.getElementById("login-remember-me").checked;
                if(checked){
                    console.log("保存登录信息-有效期7天");
                    formData.explainTime = 604800;
                }else{
                    console.log("不保存登录信息-有效期6小时");
                    formData.explainTime = 21600;
                }
                $.ajax({
                    type:'POST',
                    url:'/lottery/admin/login',
                    data:formData,
                    success:function (data) {
                        if(data.code === 0){
                            window.location.href = "./index.html";
                        }else{
                            alert(data.message);
                        }
                    },
                    error:function () {
                        alert("系统繁忙");
                    }
                })
            }
        });
    };

    //自定义密码验证
    $.validator.addMethod("passwordCheck",function(value,element,params){
        var regex = /^[\w_-]{6,16}$/;
        return this.optional(element)||(regex.test(value));
    },"请输入正确的密码！");

    return {
        init: function () {
            // Init Login Form Validation
            initValidationLogin();
        }
    };

}();

// Initialize when page loads
jQuery(function(){ BasePagesLogin.init(); });