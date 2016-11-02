package zqx.com.ioc;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/2.
 */

public class Shy {
    public static void bind(Activity activity) {
        injectLayout(activity);
        injectViews(activity);
        injectEvents(activity);
    }

    private static void injectEvents(Activity activity) {
        Class<? extends Activity> activityclass = activity.getClass();
        Method[] methods = activityclass.getDeclaredMethods();
        //traverse all methods
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            //get all annotation under method
            for (Annotation anno : annotations) {
                //返回anno的注释类型
                Class<? extends Annotation> annotype = anno.annotationType();
                //get EventBase annotation on annotation,获取注释上的注释
                EventBase eb = annotype.getAnnotation(EventBase.class);
                //设置listener方法
                String listenerSetter = eb.listenerSetter();
                //listener类型
                Class<?> listenerType = eb.listenerType();
                //listener调用回调
                String callback = eb.callbackMethod();
                try {
                    Method valueMethod = annotype.getDeclaredMethod("value");
                    try {


                        int[] viewIds = (int[]) valueMethod.invoke(anno);

                        DynamicHandler handler = new DynamicHandler(activity);
                        handler.addMethod(callback,method);
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                new Class<?>[]{listenerType},handler);

                        for(int vId:viewIds){
                            View view = activity.findViewById(vId);
                            Method setEventListenerMethod=view.getClass()
                                    .getMethod(listenerSetter,listenerType);
                            setEventListenerMethod.invoke(view,listener);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectViews(Activity activity) {
        Class<? extends Activity> activityclass = activity.getClass();
        Field[] mFields = activityclass.getDeclaredFields();
        for (Field field : mFields) {
            ViewInject view = field.getAnnotation(ViewInject.class);
            if (view != null) {
                int viewId = view.value();
                activity.findViewById(viewId);
                try {
                    field.setAccessible(true);
                    field.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectLayout(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentViewInject contentview = clazz.getAnnotation(ContentViewInject.class);
        int mLayoutId = contentview.value();
        if (contentview != null)// 存在
        {
            int contentViewLayoutId = contentview.value();
            try
            {
                Method method = clazz.getMethod("setContentView",
                        int.class);
                method.setAccessible(true);
                method.invoke(activity, contentViewLayoutId);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        activity.setContentView(mLayoutId);
    }
}
