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

public abstract class UnitOfWork extends OncePerRequestFilter implements Ordered {

    private String writeOperationsAttribute = "writeOperations_" + getClass().getSimpleName();
    private HttpServletRequest request;

    protected UnitOfWork(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setAttribute(writeOperationsAttribute, new ArrayList<Runnable>());
        filterChain.doFilter(request, response);
        getWriteOperations().forEach(Runnable::run);
    }

    protected void addWriteOperation(Runnable operation) {
        getWriteOperations().add(operation);
    }

    @SuppressWarnings("unchecked")
    private List<Runnable> getWriteOperations() {
        return (List<Runnable>) request.getAttribute(writeOperationsAttribute);
    }
}
