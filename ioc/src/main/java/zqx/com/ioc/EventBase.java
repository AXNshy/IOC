package zqx.com.ioc;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/11/2.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    // 1.设置事件监听的方法,配置方法的名字
    String listenerSetter();
    // 2.事件监听的类型
    Class<?> listenerType();
    // 3.回调方法的名字
    String callbackMethod();
}
