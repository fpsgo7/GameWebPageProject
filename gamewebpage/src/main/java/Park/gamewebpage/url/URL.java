package Park.gamewebpage.url;

/**
 * URL 경로를 저장해두는 공간으로
 * 자바 파일 이외에 저장된 경로는 변수로 입력할 수 없어서
 * 경로를 입력하였다.
 * 해당 경로를 수정하게된다면
 * 주석에서 나와있는 ,js, html 파일에서 도 수정해주면된다.
 */
public class URL {
    /* Util */
    public final static String API
            = "/api";

    /* FreeBoardAPI */
    /**
     * freeBoard.js
     */
    public final static String FREE_BOARD_API
            = "/api/freeBoard";
    public final static String FREE_BOARD_API_BY_ID
            = "/api/freeBoard/{id}";

    /* FreeBoardView */
    /**
     * freeBoard.js
     * header.html
     */
    public final static String FREE_BOARD_VIEW
            = "/view/freeBoard";
    /**
     * freeBoardList.html
     */
    public final static String FREE_BOARD_VIEW_BY_ID
            = "/view/freeBoard/{id}";
    /**
     * freeBoardList.html
     */
    public final static String CREATE_FREE_BOARD_VIEW
            = "/view/createFreeBoard";
    /**
     * freeBoard.html
     */
    public final static String UPDATE_FREE_BOARD_VIEW
            = "/view/updateFreeBoard";

    /* FreeBoardComment*/
    /**
     * commendForm.js
     */
    public final static String FREE_BOARD_COMMENT_API
            = "/api/freeBoard/{freeBoardId}/freeBoardComment";

    /**
     * commendForm.js
     */
    public final static String FREE_BOARD_COMMENT_API_BY_ID
            = "/api/freeBoard/{freeBoardId}/freeBoardComment/{id}";
    /* User API */
    /**
     * login.html
     */
    public final static String USER_LOGIN_API_VIEW
            = "/login";
    /**
     * signup.html
     */
    public final static String USER_API
            = "/user";
    /**
     * userinfo.html
     */
    public final static String USER_DELETE_API
            = "/api/user/delete";
    /**
     * freeBoardList.html
     */
    public final static String USER_LOGOUT_API
            = "/logout";

    /* UserView */
    public final static String USER_SIGNUP_VIEW
            = "/signup";
    /**
     * header.html
     */
    public final static String USER_VIEW_BY_EMAIL
            = "/view/user/{email}";
    public final static String USER_VIEW
            = "/view/user/";

    /* Token */
    public final static String TOKEN_API
            =  "/api/token";

    /* Game Character*/
    public static final String GAME_CHARACTER_API_BY_EMAIL
            = "/api/gameCharacter/{email}";

    public static final String GAME_CHARACTER_API
            = "/api/gameCharacter";

    public static final String GAME_CHARACTER_RANK_API
            = "/api/gameCharacterRank";

    /**
     * header.html
     */
    public static final String GAME_CHARACTER_VIEW_BY_EMAIL
            = "/view/gameCharacter/{email}";

    /**
     * gameCharacterRank.html
     */
    public static final String GAME_CHARACTER_RANK_VIEW
            = "/view/gameCharacterRank";

}
