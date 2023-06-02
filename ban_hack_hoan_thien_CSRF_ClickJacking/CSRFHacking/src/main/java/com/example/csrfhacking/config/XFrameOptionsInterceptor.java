package com.example.csrfhacking.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class XFrameOptionsInterceptor  implements HandlerInterceptor {
    public static String sessionId;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Dang kiem tra request");
        String cookie = request.getHeader("Cookie");
        System.out.println(cookie);
        sessionId = cookie;
//        if (referer != null) {
//            // Kiểm tra nếu referer không chứa domain của YouTube hoặc Facebook http://127.0.0.1:5500
//            // && !referer.contains("127.0.0.1:5500")
//            if (!referer.contains("youtube.com") && !referer.contains("facebook.com")
//                    && !referer.contains("localhost:3030") && !referer.contains("127.0.0.1:5500"))  {
//                javax.servlet.http.HttpServletResponse httpResponse = (javax.servlet.http.HttpServletResponse) response;
//                httpResponse.sendError(javax.servlet.http.HttpServletResponse.SC_FORBIDDEN, "Access denied");
//            }
//        }
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
}
