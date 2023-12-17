//비밀번호가 맞는지 체크하기
const checkPWButton = document.getElementById('checkPassword-btn');
const popup = document.getElementById('popup');
const popupdark = document.getElementById('popupdark');
if (checkPWButton) {
    checkPWButton.addEventListener('click', event => {
      // JavaScript 값이나 객체를 JSON 문자열로 변환
      body = JSON.stringify({
        password: document.getElementById('checkPassword').value,
      });
      function success(json) {
        if(json.items[0].isTrue=="true"){
            alert("인증 완료");
            document.getElementById("popup").style.display ='none';
            document.getElementById("popupdark").style.display ='none';
        }else{
            alert("비밀번호가 틀림니다.");
        }
      }
      function fail() {
      }
      if(loginStyle=="Oauth2Login"){
        oauth2HttpRequest('POST','/api/user/password',body,success,fail);
      }else{
        httpRequest('POST','/api/user/password',body,success,fail);
      }
    });
}
// 기본적인 HTTP 요청을 보내는 함수
function httpRequest(method,url,body,success,fail){
  fetch(url,{
    method: method,
    headers: {
      'Content-Type':  'application/json',
    },
    body: body
    }).then((response) =>{
        if(response.status === 200 || response.status === 201 ){
          response.json()
          .then((json) => {
            return success(json);
          });
        }
        else {
          return fail();
        }
    });
}

// HTTP 요청을 보내는 함수
function oauth2HttpRequest(method,url,body,success,fail){
  fetch(url,{
    method: method,
    headers: {
      // 로컬 스토리지에서 액세스 토큰 값을 헤더에 추가한다.
      Authorization: 'Bearer ' + localStorage.getItem('access_token'),
      'Content-Type':  'application/json',
    },
    body: body
  }).then(response => {
    if(response.status === 200 || response.status === 201 ){
      response.json()
      .then((json) => {
        return success(json);
      });
    }
    const refresh_token = getCookie('refresh_token');
    if(response.status === 401 && refresh_token){
      fetch('/api/token',{
        method: 'POST',
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('access_token'),
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          refreshToken: getCookie('refresh_token'),
        }),
      }).then(res => {
        if(res.ok){
          return res.json();
        }
      }).then(result =>{
        // 재발급이 성공하면 로컬 스토리지 값을 새로운 액세스 토큰으로 한다.
        localStorage.setItem('access_token', result.accessToken);
        oauth2HttpRequest(method,url,body,success,fail);
      }).catch(error => fail());
    }else {
      return fail();
    }
  });
}
// 쿠키를가져오는 함수
function getCookie(key){
  var result = null;
  var cookie = document.cookie.split(';');
  cookie.some(function(item){
    item = item.replace(' ','');

    var dic = item.split('=');

    if(key === dic[0]){
      result = dic[1];
      return true;
    }
  });
  return result;
}