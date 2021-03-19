package com.packt.webstore.interceptor;

import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

public class PerformanceMonitorInterceptor implements HandlerInterceptor {
    ThreadLocal<StopWatch> stopWatchThreadLocal = new ThreadLocal<>();
    Logger logger = Logger.getLogger(String.valueOf(this.getClass()));

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        StopWatch stopWatch = new StopWatch(handler.toString());
        stopWatch.start(handler.toString());
        stopWatchThreadLocal.set(stopWatch);
        logger.info("Przetwarzanie żądania do ścieżki: " + getURLPath(httpServletRequest));
        logger.info("Przetwarzanie żądania rozpoczęto o: " + getCurrentTime());
        return true;
    }

    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy 'o' hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return formatter.format(calendar.getTime());
    }

    private String getURLPath(HttpServletRequest httpServletRequest) {
        String currentPath = httpServletRequest.getRequestURI();
        String queryString = httpServletRequest.getQueryString();
        queryString = queryString == null ? "" : "?" + queryString;
        return currentPath + queryString;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("Przetwarzanie żądania zakończono o: " + getCurrentTime());
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        StopWatch stopWatch = stopWatchThreadLocal.get();
        stopWatch.stop();
        logger.info("Łączny czas przetwarzania żądania: " + stopWatch.getTotalTimeMillis() + " ms");
        stopWatchThreadLocal.set(null);
        logger.info("=======================================================");

    }
}
