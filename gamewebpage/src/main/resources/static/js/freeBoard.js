// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
      body = JSON.stringify({
        title: document.getElementById('title').value,
        content: document.getElementById('content').value,
      });
      function success() {
        alert('등록 완료되었습니다.');
        location.replace('/view/freeBoard');
      }
      function fail() {
        alert('등록 실패하였습니다..');
        location.replace('/view/freeBoard');
      }
      httpRequest('POST','/api/freeBoard',body,success,fail)
    });
}

// HTTP 요청을 보내는 함수
function httpRequest(method,url,body,success,fail){
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
        httpRequest(method,url,body,success,fail);
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

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    // 클릭 이벤트가 감지되면 수정 API 요청
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        body = JSON.stringify({
          title: document.getElementById('title').value,
          content: document.getElementById('content').value
        })

        function success() {
          alter('수정 완료되었습니다.');
          location.replace('/view/freeBoard/'+id)
        }

        function fail() {
          alter('수정 실패하였습니다.');
          location.replace('/view/freeBoard/'+id)
        }

        httpRequest('PUT','/api/freeboard/'+id,body,success,fail);
    });
}

// 삭제기능
const deleteButton = document.getElementById('delete-btn');

if(deleteButton){
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('freeBoard-id').value;
        function success() {
          alter('삭제 완료되었습니다.');
          location.replace('/view/freeBoard')
        }

        function fail() {
          alter('삭제 실패하였습니다.');
          location.replace('/view/freeBoard')
        }

        httpRequest('DELETE','/api/freeBoard/'+id,null,success,fail);
    });
}
