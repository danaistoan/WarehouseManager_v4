package com.tgs.warehouse.spring;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by dana on 5/12/2017.
 */
public class WarehouseInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WarehouseConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/api/*"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return super.getServletFilters();
//        Filter [] singleton = { new AuthenticationFilter(), new AuthorizationFilter() };
//        return singleton;
    }
}
