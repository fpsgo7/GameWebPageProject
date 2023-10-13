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
                alert('등록 완료되었습니다.');
                location.replace('/view/freeBoard');
            });
    });
}