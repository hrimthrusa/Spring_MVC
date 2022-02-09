package ru.griva.spring.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer { // Реализуем три метода абстрактного класса. (Замена web.xml)

    @Override
    protected Class<?>[] getRootConfigClasses() { // Метод не используется
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() { // Этот класс понимает, где находится конфигурация Spring (В классе SpringConfig)
        return new Class[] {SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() { // Все запросы от пользователя посылаются на DispatcherServlet
        return new String[] {"/"};
    }

    @Override // Эти два метода реализуют фильтр для чтения скрытого поля _method и реализации запросов помимо GET и POST (их перенаправления на нужные методы контроллера)
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }
}
