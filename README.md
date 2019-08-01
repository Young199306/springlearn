# springlearn
spring相关知识点学习整理笔记

version 1.0.1
主要功能：搭建基础框架
问题及解决方案：
Q1.使用了@ResponseBody注解，返回的值在浏览器中乱码

解决方案：尝试按照网上的方法啊设置了IDEA和Tomcat后依旧没有解决问题，最后发现是配置文件问题
需要加上如下配置

<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
    <property name="messageConverters">
      <list>
        <bean class="org.springframework.http.converter.StringHttpMessageConverter">
          <property name="supportedMediaTypes">
            <list>
              <value>text/plain;charset=utf-8</value>
              <value>text/html;charset=UTF-8</value>
            </list>
          </property>
        </bean>
      </list>
    </property>
  </bean>
  
  并且该注解必须需要加在注解驱动<mvc:annotation-driven />之前，否则会失效

version 1.0.2
主要功能：添加AOP功能
