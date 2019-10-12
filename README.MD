##mybatis单项目学习

---

[引用网址(https://mybatis.org/mybatis-3/getting-started.html)](https://mybatis.org/mybatis-3/getting-started.html)

---
####一、mybatis的整体结构

![mybatis整体结构](https://yuyuan2018.oss-cn-beijing.aliyuncs.com/test/20191009/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20191009174727.png)

####二、SqlSessionFactory构建

1. XML方式构建，通过配置mybatis-config.xml的方式解析Configuration构建SqlSessionFactory
2. 通过注解方式配置Configuration 参考SqlSessionFactoryNoXML类

以上两种方式不能同时配置一个名称空间的方法

####三、Configuration的个元素配置

1. properties的加载顺序（后边的会覆盖前边已经加载的）
   1. property标签属性
   2. properties标签的resource或url
   3. 构造Configuration时传入的pros属性
2. setting属性cacheEnabled、驼峰转换等全局设置
3. typeAliases 默认的和package
4. typeHandlers 自定义类型处理实现BaseTypeHandler
5. objectFactory 默认mybatis用DefaultObjectFactory实例化结果类
    用户可以自定义实例化方式
6. plugins mybatis插件
   1. mybatis插件是以Interceptor的方式注册到Configuration中的在Configuration中构造四大对象时都会在完成之后
   调用interceptorChain#pluginAll方法该方法返回对应的四个对象，在pluginAll方法中调用Interceptor#plugin可以
   看出plugin方法是用来包装四大对象的
   2. Configuration中四大对象的方法分别对应
      1. newParameterHandler --> ParameterHandler
      2. newResultSetHandler --> ResultSetHandler
      3. newStatementHandler --> StatementHandler
      4. newExecutor --> Executor 
   3. 四大对象可拦截的方法
      1. Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
      2. ParameterHandler (getParameterObject, setParameters)
      3. ResultSetHandler (handleResultSets, handleOutputParameters)
      4. StatementHandler (prepare, parameterize, batch, update, query)
   4. 插件的创建需要实现Interceptor接口可参考分页插件实现学习
7. environments配置，默认的环境配置default="development"可以配置多个环境
    环境中事务管理之后基本都由spring去管理dataSource一般使用阿里的druid
8. mappers配置一般使用package包扫描的方式其他方式有resource、url、class三种方式
9. mapper.xml文件的配置
   1. cache对应每个namespace配置各自的缓存默认<cache/>就可以实现
   2. cache-ref 引用其他名称空间中的缓存对象
   3. resultMap ==> association|collection 多级映射
   4. sql sql片段
   5. select|insert|update|delete
10. Dynamic SQL 动态sql标签主要包含 if|choose(when,otherwise)|trim(where,set)|foreach
11. 日志的配置mybatis支持多种格式的日志配置在settings标签中
    1. &lt;setting name="logImpl" value="LOG4J"/>
    2. value中有效值为SLF4J, LOG4J, LOG4J2, JDK_LOGGING, COMMONS_LOGGING, STDOUT_LOGGING, NO_LOGGING