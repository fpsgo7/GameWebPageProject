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
