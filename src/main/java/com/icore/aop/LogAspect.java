package com.icore.aop;

import com.icore.util.FastJsonUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import org.apache.ibatis.javassist.ClassClassPath;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Aspect
@Component
public class LogAspect {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    @Pointcut("execution(* com.icore.web..*.*(..)) && @annotation(com.icore.aop.LogAnnotation)")
    public void executeService(){}


    @Before("executeService()")
    public void logInput(JoinPoint joinPoint) throws Throwable{
        //获取参数值
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        //获取方法
        Method method =signature.getMethod();
        String[] parameterNames = signature.getParameterNames();

        StringBuffer tip = new StringBuffer();
        for(int i=0;i<parameterNames.length;i++){
            tip.append(parameterNames[i]).append("=").append(FastJsonUtil.toJSON(args[i])).append(",");
        }

        if(tip.length()>1){
            //删除多余的逗号
            tip.deleteCharAt(tip.length()-1);
        }
        log.info("AOP日志打印：{}.{} in {}",method.getDeclaringClass().getSimpleName(),method.getName(),FastJsonUtil.toJSON(tip.toString()));
    }

    @AfterReturning(value="executeService()",returning = "rvt")
    public void loginOutput(JoinPoint joinPoint,Object rvt)throws Throwable{

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();

        Method method =signature.getMethod();

        log.info("AOP日志打印：{}.{} out {}",method.getDeclaringClass().getSimpleName(),method.getName(),FastJsonUtil.toJSON(rvt));
    }


    /**
     * 判断是否为基本类型：包括String
     *
     * @param clazz clazz
     * @return true：是; false：不是
     */
    private boolean isPrimite(Class<?> clazz) {
        if (clazz.isPrimitive() || clazz == String.class) {
            return true;
        } else {
            return false;
        }
    }



    /**
     * 使用javassist来获取方法参数名称
     *
     * @param class_name  类名
     * @param method_name 方法名
     * @return
     * @throws Exception
     */
    private String[] getFieldsName(String class_name, String method_name) throws Exception {
        Class<?> clazz = Class.forName(class_name);
        String clazz_name = clazz.getName();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);

        CtClass ctClass = pool.get(clazz_name);
        CtMethod ctMethod = ctClass.getDeclaredMethod(method_name);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                .getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return null;
        }
        String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramsArgsName.length; i++) {
            paramsArgsName[i] = attr.variableName(i + pos);
        }
        return paramsArgsName;
    }


    /**
     * 打印方法参数值 基本类型直接打印，非基本类型需要转成json字符串方法
     *
     * @param paramsArgsName  方法参数名数组
     * @param paramsArgsValue 方法参数值数组
     * @return
     */
    private String logParam(String[] paramsArgsName, Object[] paramsArgsValue) {
        //if (ArrayUtils.isEmpty(paramsArgsName) || ArrayUtils.isEmpty(paramsArgsValue)) {
           // logger.info("该方法没有参数");
            //return null;
       // }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < paramsArgsName.length; i++) {
            // 参数名
            String name = paramsArgsName[i];
            // 参数值
            Object value = paramsArgsValue[i];
            buffer.append(name + " = ");
            if (isPrimite(value.getClass())) {
                buffer.append(value + "  ,");
            } else {
                buffer.append(FastJsonUtil.toJSON(value) + "  ,");
            }
        }
        return buffer.toString();
    }


}
