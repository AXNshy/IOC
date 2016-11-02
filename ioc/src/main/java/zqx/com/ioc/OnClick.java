package zqx.com.ioc;

import android.annotation.TargetApi;
import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/11/2.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EventBase(listenerType = View.OnClickListener.class,listenerSetter = "setOnClickListener",callbackMethod ="onClick")
public @interface OnClick {
    int[] value();
}
