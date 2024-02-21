// 파라미터로 받은 토큰이 있다면 토큰을 로컬 스토리지에 저장한다.
const token = searchParam('token')
// 토큰이 존재하는 경우 로컬에 저장한다.
if(token){
    localStorage.setItem("access_token", token)
}
// url 파라미터 로부터 키에 해당하는 값을 찾아 반환한다.
function searchParam(key) {
  return new URLSearchParams(location.search).get(key);
}
