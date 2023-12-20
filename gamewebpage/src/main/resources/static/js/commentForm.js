//자유 게시판 댓글 추가 기능
const createCommentButton = document.getElementById('createFreeBoardComment-btn');

if (createCommentButton) {
    createCommentButton.addEventListener('click', event => {
      // url의 id 값 가져오기
      let freeBoardId = document.getElementById('freeBoard-id').value;
      // JavaScript 값이나 객체를 JSON 문자열로 변환
      body = JSON.stringify({
        comment: document.getElementById('comment').value,
      });
      function success() {
        alert('등록 완료되었습니다.');
        location.replace('/view/freeBoard/'+freeBoardId);
      }
      function fail() {
        alert('등록 실패하였습니다..');
        location.replace('/view/freeBoard/'+freeBoardId);
      }
      if(loginStyle=="Oauth2Login"){
        oauth2HttpRequest('POST','/api/freeBoard/'+freeBoardId+'/freeBoardComment',body,success,fail);
      }else{
        httpRequest('POST','/api/freeBoard/'+freeBoardId+'/freeBoardComment',body,success,fail);
      }

    });
}
// 댓글 수정하기
// 버튼의 onclick 에 함수 실행을위한 함수명과 매계변수로 id 값을 넣어
// 해당버튼이 클릭되면 id값을 받아 실행된다.
function updateComment(id) {
  alert(id);
  let freeBoardId = document.getElementById('freeBoard-id').value;
  body = JSON.stringify({
      comment: document.getElementById('updateFreeBoardComment-comment'+id).value
  })
  function success() {
    alert('수정 완료되었습니다.');
    location.replace('/view/freeBoard/'+freeBoardId);
  }
  function fail() {
    alert('수정 실패하였습니다.');
    location.replace('/view/freeBoard/'+freeBoardId);
  }
  if(loginStyle=="Oauth2Login"){
      oauth2HttpRequest( 'PATCH','/api/freeBoard/'+freeBoardId+'/freeBoardComment/'+id,body,success,fail);
  }else{
      httpRequest( 'PATCH','/api/freeBoard/'+freeBoardId+'/freeBoardComment/'+id,body,success,fail);
  }
}

function deleteComment(id){
    alert(id);
    let freeBoardId = document.getElementById('freeBoard-id').value;
    function success() {
      alert('삭제 완료되었습니다.');
      location.replace('/view/freeBoard/'+freeBoardId);
    }
    function fail() {
      alert('삭제 실패하였습니다.');
      location.replace('/view/freeBoard/'+freeBoardId);
    }
     if(loginStyle=="Oauth2Login"){
        oauth2HttpRequest('DELETE','/api/freeBoard/'+freeBoardId+'/freeBoardComment/'+id,null,success,fail);
    }else{
        httpRequest('DELETE','/api/freeBoard/'+freeBoardId+'/freeBoardComment/'+id,null,success,fail);
    }
}

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
