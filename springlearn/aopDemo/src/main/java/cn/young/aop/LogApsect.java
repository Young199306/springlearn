package cn.young.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.Arrays;

    @Aspect
    @Component
    public class LogApsect {

        private static final Logger logger = LoggerFactory.getLogger(LogApsect.class);

        ThreadLocal<Long> startTime = new ThreadLocal<>();
        ThreadLocal<Long> endTime = new ThreadLocal<>();

        // 第一个*代表返回类型不限
        // 第二个*代表所有类
        // 第三个*代表所有方法
        // (..) 代表参数不限
        @Pointcut("execution(public * cn.young.controller.*.*(..))")
        @Order(1) // Order 代表优先级，数字越小优先级越高,切面如果只有一个切面可以不用Order
        public void pointCut() {
        };



        @Before(value = "pointCut()")
        public void before(JoinPoint joinPoint) {
            System.out.println("-----Before------");
            //下面处理核心业务逻辑
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            logger.info("<=====================================================");
            logger.info("请求来源： =》" + request.getRemoteAddr());
            logger.info("请求URL：" + request.getRequestURL().toString());
            logger.info("请求方式：" + request.getMethod());
            logger.info("响应方法：" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logger.info("请求参数：" + Arrays.toString(joinPoint.getArgs()));
            logger.info("------------------------------------------------------");

        }

        // 定义需要匹配的切点表达式，同时需要匹配参数
        //如果controller中的方法不带参数，则不会走该方法
        @Around("pointCut() && args(arg)")
        public Object around(ProceedingJoinPoint pjp, String arg) throws Throwable {
            System.out.println("------Around start！------参数："+arg);
            String result = null;
            startTime.set(System.currentTimeMillis());
            Object object = pjp.proceed();//继续执行被切入的方法
            endTime.set(System.currentTimeMillis());

            System.out.println("方法执行耗时："+(endTime.get()-startTime.get()));
            try {
                result = object.toString() + "aop String";
                System.out.println("主体方法返回结果："+result);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            System.out.println("------Around end!------");
            return object;
        }

        @After("within(cn.young.controller.*Controller)")
        public void after() {
            System.out.println("------After------");
        }

        @AfterReturning(pointcut = "pointCut()", returning = "rst")
        public void afterRunning(Response rst) {
            if (startTime.get() == null) {
                startTime.set(System.currentTimeMillis());
            }
            System.out.println("方法执行完执行...afterRunning");
            logger.info("耗时（毫秒）：" + (System.currentTimeMillis() - startTime.get()));
            logger.info("返回数据：{}", rst);
            logger.info("==========================================>");
        }

        //针对异常的处理方法
        @AfterThrowing("within(cn.young.controller.*Controller)")
        public void afterThrowing() {
            System.out.println("-----After Exception Happened------");
        }
    }


