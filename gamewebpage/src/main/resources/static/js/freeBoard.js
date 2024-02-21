//createFreeBoard.html
//freeBoard.html
//updateFreeBoard.html
/* 생성 기능 */
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
      // JavaScript 값이나 객체를 JSON 문자열로 변환
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
      if(loginStyle=="Oauth2Login"){
        // oauth2HttpRequest 함수를 통하여 http 요청을 한다
        oauth2HttpRequest('POST','/api/freeBoard',body,success,fail);
      }else{
        httpRequest('POST','/api/freeBoard',body,success,fail);
      }

    });
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
          alert('수정 완료되었습니다.');
          location.replace('/view/freeBoard/'+id)
        }

        function fail() {
          alert('수정 실패하였습니다.');
          location.replace('/view/freeBoard/'+id)
        }

        if(loginStyle=="Oauth2Login"){
            oauth2HttpRequest('PATCH','/api/freeBoard/'+id,body,success,fail);
        }else{
            httpRequest('PATCH','/api/freeBoard/'+id,body,success,fail);
        }
    });
}

// 삭제기능
const deleteButton = document.getElementById('delete-btn');

if(deleteButton){
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('freeBoard-id').value;
        function success() {
          alert('삭제 완료되었습니다.');
          location.replace('/view/freeBoard')
        }

        function fail() {
          alert('삭제 실패하였습니다.');
          location.replace('/view/freeBoard')
        }
        if(loginStyle=="Oauth2Login"){
            /* http 메서드는 DELETE 이며 경로는 /api/freeBoard/'+id 이다. */
            oauth2HttpRequest('DELETE','/api/freeBoard/'+id,null,success,fail);
        }else{
            httpRequest('DELETE','/api/freeBoard/'+id,null,success,fail);
        }


    });
}
