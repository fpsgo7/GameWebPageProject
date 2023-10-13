// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        fetch('/api/freeBoard', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value,
                // 회원가입 전까진 임시값 사용
                writerId: "1",
                writerName: "1"
            })
        })
            .then(() => {
                location.replace('/view/freeBoard');
            });
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    // 클릭 이벤트가 감지되면 수정 API 요청
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch('/api/freeBoard/'+id, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
            .then(() => {
                location.replace('/view/freeBoard/'+id);
            });
    });
}

// 삭제기능
const deleteButton = document.getElementById('delete-btn');

if(deleteButton){
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('freeBoard-id').value;
        fetch('/api/freeBoard/'+id,{
            method: 'DELETE'
        })
        .then(() => {
            location.replace('/view/freeBoard');
        });
    });
}