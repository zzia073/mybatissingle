package com.study.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @author ：fei
 * @date ：Created in 2019/10/12 0012 16:17
 * mybatis插件测试
 * <code>@Signature</code>插件拦截的方法签名，用官方提供的测试
 * intercept方法拦截方法执行
 * plugin包装要拦截的对象
 * setProperties插件拦截时需要的一些属性可以在其他两个方法中取出使用
 * 通过xml配置的方式配置plugin或者通过Configuration的addPlugin方法添加插件
 * 通过pagehelper分页插件学习插件使用的plugin方法，
 * 该方法是用mybatis提供的一个工具类Plugin的wrap方法动态代理的方式去包装当前拦截的对象
 *
 * ###############################################################################
 * ## plugin方法是核心方法，会将注解配置的拦截对象包装成代理对象并加入配置的要代理的方法 ##
 * ## 在执行这些方法的时候就是用该方法返回的代理对象去执行                            ##
 * ##############################################################################
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MyInterceptor implements Interceptor {
    private Properties properties;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        System.out.println(methodName + "方法执行前。。。");
        Object result = invocation.proceed();
        System.out.println(methodName + "方法执行后。。。");
        System.out.println("插件拦截时添加的属性：" + properties.getProperty("pluginProps1"));
        return result;
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("包装代理类：" + target.getClass().getSimpleName());
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        properties.setProperty("pluginProps1","插件新增属性1");
        this.properties = properties;
    }
}
