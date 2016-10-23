package com.axn.rest.spring;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Class need to init spring web mvc
 */
public final class InitializeWebSpring extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected final Class<?>[] getRootConfigClasses() {
        return new Class[]{ConfigWebSpring.class};
    }

    @Override
    protected final Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected final String[] getServletMappings() {
        return new String[]{"/"};
    }
}
