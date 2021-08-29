var $$ = {
    JWT_TOKEN_COOKIE_KEY: 'torder-jwt-token',
    JWT_TOKEN_REQUEST_PREFIX: 'Bearer ',
    JWT_TOKEN_RESPONSE_KEY: 'api-key',
    LOGIN_PAGE_URL: '/login.html',
    checkLogin: function() {
      if ($.cookie(this.JWT_TOKEN_COOKIE_KEY))
          return true;
      else
          return false;
    },
    logout: function() {
        $.removeCookie(this.JWT_TOKEN_COOKIE_KEY);
    },
    //$.ajax를 간단하게 사용하기위한 wrapper method
    //jwt 토큰이 존재한다면 자동으로 입력되도록 구현
    ajax: function(method, url, data, success, errorMsg) {
        var ajaxObj = {};
        ajaxObj.method = method;
        ajaxObj.url = url;
        ajaxObj.contentType = 'application/json';
        if ($.cookie(this.JWT_TOKEN_COOKIE_KEY)) {
            ajaxObj.headers = {};
            ajaxObj.headers[this.JWT_TOKEN_RESPONSE_KEY] = this.JWT_TOKEN_REQUEST_PREFIX + $.cookie(this.JWT_TOKEN_COOKIE_KEY);
        }
        if (typeof(data) === 'object') {
            if (method.toUpperCase() !== 'GET')
                ajaxObj.data = JSON.stringify(data);
            else {
                if (data && Object.keys(data).length > 0)
                    ajaxObj.url = ajaxObj.url + '?' + $.param(data);
            }
        }
        ajaxObj.success = success;
        ajaxObj.error = function(e) {
            console.log(e);
            if (e.status === 401) {
                alert('세션이 만료되었습니다.\n다시 로그인해주세요.');
                location.href = $$.LOGIN_PAGE_URL;
            }
            if (e.responseJSON) {
                var json = e.responseJSON;
                console.log(json);
                toastr['error'](errorMsg + '<br>error code : ' + json.error.status + '<br>error message : ' + json.error.message);
            }
            else if (e.responseText) {
                console.log(e.responseText);
                toastr['error'](e.responseText);
            }
        };

        $.ajax(ajaxObj);
    }
}