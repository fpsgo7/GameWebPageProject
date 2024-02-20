// 기본적인 HTTP 요청을 보내는 함수
function httpRequest(method,url,body,success,fail){
  fetch(url,{
    method: method,
    headers: {
      'Content-Type':  'application/json',
    },
    body: body
    }).then(response => {
    if(response.status === 200 || response.status === 201 ){
      return success();
    }
    else {
      return fail();
    }
  });
}

// Oauth2 일떄 사용하는
// HTTP 요청을 보내는 함수
// 요청을 보낼때 액세스 토큰도 함꼐 보냅니다.
// 만약 응답에 권한이 없다는 에러코드가 발생하면
// 리프레시 토큰과 함꼐 새로운 액세스 토큰을 요청하고,
// 전달받은 액세스 토큰으로 다시 API를 요청한다.
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
      return success();
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
