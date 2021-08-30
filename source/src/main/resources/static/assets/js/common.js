var $$ = {
    JWT_TOKEN_COOKIE_KEY: 'torder-jwt-token',
    JWT_TOKEN_REQUEST_PREFIX: 'Bearer ',
    JWT_TOKEN_RESPONSE_KEY: 'api-key',
    LOGIN_PAGE_URL: '/login.html',
    //로그인 여부 확인 메서드
    checkLogin: function() {
      if ($.cookie(this.JWT_TOKEN_COOKIE_KEY))
          return true;
      else
          return false;
    },
    //로그아웃 메서드 (서버요청을 따로 보내지않고 쿠키만 삭제시킴)
    logout: function() {
        if (confirm('로그아웃 하시겠습니까?')) {
            $.removeCookie(this.JWT_TOKEN_COOKIE_KEY);
            location.href = this.LOGIN_PAGE_URL;
        }
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
            if (e.status === 401) {
                alert('세션이 만료되었습니다.\n다시 로그인해주세요.');
                location.href = $$.LOGIN_PAGE_URL;
            }
            if (e.responseJSON) {
                var json = e.responseJSON;
                console.error(json);
                toastr['error'](errorMsg + '<br>error code : ' + json.error.status + '<br>error message : ' + json.error.message);
            }
            else if (e.responseText) {
                console.error(e.responseText);
                toastr['error'](e.responseText);
            }
        };

        $.ajax(ajaxObj);
    }
}