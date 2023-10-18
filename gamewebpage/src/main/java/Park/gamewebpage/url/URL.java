package Park.gamewebpage.url;

/**
 * URL 경로를 저장해두는 공간으로
 * 자바 파일 이외에 저장된 경로는 변수로 입력하기 어려워서
 * 임시로 문자열로 작성하였다.
 */
public class URL {
    // FreeBoardAPI
    /**
     * freeBoard.js
     */
    public final static String FREE_BOARD_API
            = "/api/freeBoard";
    public final static String FREE_BOARD_API_BY_ID
            = "/api/freeBoard/{id}";
    // FreeBoardView
    /**
     * freeBoard.js
     */
    public final static String FREE_BOARD_VIEW
            = "/view/freeBoard";
    public final static String FREE_BOARD_VIEW_BY_ID
            = "/view/freeBoard/{id}";
    public final static String CREATE_FREE_BOARD_VIEW
            = "/view/createFreeBoard";
    public final static String UPDATE_FREE_BOARD_VIEW
            = "/view/updateFreeBoard";

    // User API
    public final static String USER_LOGIN_API_VIEW
            = "/login";
    public final static String USER
            = "/user";
    public final static String USER_LOGOUT
            = "/logout";
    // UserView
    public final static String USER_LOGIN_VIEW
            = "/login";
}
