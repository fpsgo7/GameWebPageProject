package Park.gamewebpage.util.cookie;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * 쿠키 작업을 위한 유틸리티
 * 클래스이다.
 */
public class CookieUtil {

    /**
     * 요청값(이름, 값, 만료 기간) 을 바탕으로 쿠키를 추가한다.
     */
    public static void addCookie(HttpServletResponse response,
                                 String name, String value, int maxAge){
        // 쿠키 객체 생성
        Cookie cookie = new Cookie(name,value);
        // 클라이언트가 쿠키를 반환해야 하는 쿠키의 경로를 지정
        cookie.setPath("/");
        // 쿠키의 최대 수명을 초 단위로 설정합니다.
        // 음수로 설정하면 웹브라우저가 닫히면 사라지는 쿠키가 된다.
        cookie.setMaxAge(maxAge);
        // 지정된 쿠키를 응답에 추가합니다.
        response.addCookie(cookie);
    }

    /**
     * 이름을 통해 쿠키를 삭제한다.
     * - 삭제후 재생성할 때 사용되기도 한다.
     * @param name 검색하기 위한 값
     */
    public static void deleteCookie(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String name){
        // 요청에서 쿠키들을 가져온다.
        Cookie[] cookies = request.getCookies();
        if(cookies == null)
            return;

        for(Cookie cookie : cookies){
            if(name.equals(cookie.getName())){
                // 쿠키는 실제로 삭제 할 수 없어
                // 만료기간을 설정하여, 만료시켜버린다.
                cookie.setValue("");
                cookie.setPath("/");// 모든 경로에서 삭제됨
                cookie.setMaxAge(0);// 쿠키 삭제
                response.addCookie(cookie);
            }
        }
    }

    /**
     * 객체 -- 직렬화 --> 쿠키
     * @param obj 쿠키 재료 객체
     * @return 쿠키를 위해 직렬화된 객체
     */
    public static String serializeToCookie(Object obj){
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    /**
     * 쿠키 -- 역직렬화 --> 객체
     * @param cookie 쿠키
     * @param tClass 원하는 클래스형 인자값
     * @return 객체화된 쿠키
     * @param <T> 원하는 클래스 자료형
     */
    public static <T> T deserializeToObject(Cookie cookie, Class<T> tClass){
        return tClass.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }
}
