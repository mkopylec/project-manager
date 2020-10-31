package com.github.mkopylec.projectmanager.core.common;

import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class OperationsDelayer extends OncePerRequestFilter implements Ordered {

    private String saveOperationsAttribute = "delayedOperations_" + getClass().getSimpleName();
    private HttpServletRequest request;

    protected OperationsDelayer(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setAttribute(saveOperationsAttribute, new ArrayList<Runnable>());
        filterChain.doFilter(request, response);
        getDelayedOperations().forEach(Runnable::run);
    }

    protected void addDelayedOperation(Runnable operation) {
        getDelayedOperations().add(operation);
    }

    @SuppressWarnings("unchecked")
    private List<Runnable> getDelayedOperations() {
        return (List<Runnable>) request.getAttribute(saveOperationsAttribute);
    }
}
