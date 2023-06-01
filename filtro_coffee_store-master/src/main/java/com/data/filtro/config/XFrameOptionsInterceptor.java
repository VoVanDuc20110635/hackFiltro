package com.data.filtro.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;


public class XFrameOptionsInterceptor  implements HandlerInterceptor {
    private static final String STRICT_TRANSPORT_SECURITY_HEADER_NAME = "Strict-Transport-Security";
    private static final String STRICT_TRANSPORT_SECURITY_HEADER_VALUE = "max-age=31536000; includeSubDomains";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("Dang kiem tra request");
//        String referer = request.getHeader("Referer");
//        System.out.println(referer);
//        if (referer != null) {
//            // Kiểm tra nếu referer không chứa domain của YouTube hoặc Facebook http://127.0.0.1:5500
//            // && !referer.contains("127.0.0.1:5500")
//            if (!referer.contains("youtube.com") && !referer.contains("facebook.com")
//                    && !referer.contains("localhost:3030") && !referer.contains("127.0.0.1:5500"))  {
//                javax.servlet.http.HttpServletResponse httpResponse = (javax.servlet.http.HttpServletResponse) response;
//                httpResponse.sendError(javax.servlet.http.HttpServletResponse.SC_FORBIDDEN, "Access denied");
//            }
//        }
////        Cookie[] cookies = request.getCookies();
////        if (cookies != null) {
////            for (Cookie cookie : cookies) {
////                if (cookie.getName().equals("JSESSIONID")) {
//////                    cookie.setMaxAge(6000);
////                    // đặt thời gian sống của Cookie là 0 giây.
////                    response.addCookie(cookie);
////                    break;
////                }
////            }
////        }
//        request.getSession().invalidate();
//        String cookieString = request.getHeader("Cookie");
//        System.out.println(cookieString);
//        String[] cookieParts;
//        if (cookieString != null){
//            cookieParts = cookieString.split(";");}
//        else{
//            cookieParts = new String[0];
//        }
//
//        String jsessionId = null;
//
//        // Lặp qua từng phần tử để tìm cookie JSESSIONID
//        for (String part : cookieParts) {
//            // Loại bỏ khoảng trắng ở đầu và cuối phần tử
//            part = part.trim();
//
//            // Kiểm tra nếu phần tử bắt đầu bằng "JSESSIONID="
//            if (part.startsWith("JSESSIONID=")) {
//                // Lấy giá trị của cookie JSESSIONID bằng cách loại bỏ "JSESSIONID=" ở đầu
//                jsessionId = part.substring("JSESSIONID=".length());
//                break;
//            }
//        }
//        Cookie cookie = new Cookie("sessionID", jsessionId);
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
//        cookie.setSameSite("Strict");
//        String cookieNew = "sessionID=" + jsessionId +"; Secure; HttpOnly; SameSite=Strict";
//        response.setHeader("Set-Cookie", cookieNew);

        setHttpOnlyCookie(response, "ajs_user_id", getCookieValue(request,"ajs_user_id" ), 3600);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(STRICT_TRANSPORT_SECURITY_HEADER_NAME, STRICT_TRANSPORT_SECURITY_HEADER_VALUE);
        if (!isCookieHttpOnly(request, "ajs_user_id")){
            System.out.println("ajs_user_id" + " la httponly");
        }
        else{
            System.out.println("ajs_user_id" + " khong phai la httponly");
        }
//        response.setHeader("X-Frame-Options", "SAMEORIGIN");
//        response.setHeader("Content-Security-Policy", "script-src 'self' https://code.jquery.com " +
//                "https://kit.fontawesome.com " +
//                "https://unpkg.com " +
//                "https://cdn.jsdelivr.net ; " +
//                "frame-src 'self';");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Mã logic xử lý sau khi xử lý yêu cầu nhưng trước khi hiển thị kết quả (post-processing)
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Mã logic xử lý sau khi hoàn thành xử lý yêu cầu (clean-up tasks)
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            Cookie cookie = WebUtils.getCookie(request, session.getId());
//            if (cookie != null) {
//                cookie.setSameSite("Lax");
//                response.addCookie(cookie);
//            }
//        }
    }

    public void setHttpOnlyCookie(HttpServletResponse response, String cookieName, String cookieValue, int maxAge) {
        Cookie oldCookie = new Cookie(cookieName, cookieValue);
        Cookie newCookie = new Cookie(cookieName, cookieValue);
        oldCookie.setMaxAge(0);
        oldCookie.setPath("/");
        // dòng này có nghĩa việc ghi đè old cookie có thoi gian song = 0 sẽ áp dụng cho mọi đường dẫn
        response.addCookie(oldCookie);

        newCookie.setMaxAge(maxAge);
        newCookie.setHttpOnly(true);
        response.addCookie(newCookie);
    }
    public String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    public boolean isCookieHttpOnly(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.isHttpOnly();
                }
            }
        }
        return false; // Không tìm thấy cookie
    }
}
