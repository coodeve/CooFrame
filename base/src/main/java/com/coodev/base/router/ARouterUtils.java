package com.coodev.base.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.launcher.ARouter;
import com.coodev.base.BuildConfig;
import com.coodev.base.utils.ViewUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * {@link ARouter}使用
 * 1.Base module中，需要在dependencies中添加：
 * implementation "com.alibaba:arouter-api:1.5.1"
 * annotationProcessor "com.alibaba:arouter-compiler:1.5.1"
 * 2. 在每一个module build.gradle的defaultConfig中加入：
 * javaCompileOptions {
 * annotationProcessorOptions{
 * arguments = [moduleName:project.getName()]
 * }
 * }
 * <p>
 * 在dependencies中加入注解器，否则无法生成索引文件：
 * annotationProcessor "com.alibaba:arouter-compiler:1.5.1"
 */

public class ARouterUtils {

    public static void init(Application application) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
    }

    /**
     * 路由地址设定
     * 注意可以单独设置group
     * 默认 /dev/test1 中dev就是group值，每个module的group值不应该相同
     */
    @Route(path = "/dev/test1")
    public static class TestActivity extends Activity {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Intent intent = getIntent();//正常通过intent获取参数

        }
    }

    /**
     * 简单跳转
     * 测试
     * url配置:
     * <activity android:name=".activity.SchemeFilterActivity">
     * <!-- Scheme -->
     * <intent-filter>
     * <data
     * android:host="m.aliyun.com"
     * android:scheme="arouter"/>
     * <p>
     * <action android:name="android.intent.action.VIEW"/>
     * <p>
     * <category android:name="android.intent.category.DEFAULT"/>
     * <category android:name="android.intent.category.BROWSABLE"/>
     * </intent-filter>
     * </activity>
     *
     * @param route 可以是route参数,也可以是url(需要在AndroidManifest.xml中配置)
     */
    public static void navigate(String route) {
        ARouter.getInstance().build(route).navigation();
    }

    /**
     * 添加监听
     *
     * @param route    path
     * @param context  context
     * @param callback 回调
     */
    public static void navigate(String route, Context context, NavigationCallback callback) {
        ARouter.getInstance().build(route).navigation(context, callback);
    }

    /**
     * 获取fragment对象
     *
     * @param route   fragment的path
     * @param context context
     * @return Fragment
     */
    public static Fragment navigate(String route, Context context) {
        return (Fragment) ARouter.getInstance().build(route).navigation();
    }

    /**
     * 携带参数跳
     * 测试
     *
     * @param route  path
     * @param params 跳转参数
     */
    public static void navigate(String route, Map<String, String> params) {
        Postcard postcard = ARouter.getInstance().build(route);
        if (params != null && !params.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                postcard.withString(next.getKey(), next.getValue());
            }
        }
        postcard.navigation();
    }

    // 比较经典的应用就是在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查
    // 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
    @Interceptor(priority = 8, name = "测试用拦截器")
    public class TestInterceptor implements IInterceptor {
        @Override
        public void process(Postcard postcard, InterceptorCallback callback) {
            callback.onContinue(postcard);  // 处理完成，交还控制权
            // callback.onInterrupt(new RuntimeException("我觉得有点异常"));      // 觉得有问题，中断路由流程

            // 以上两种至少需要调用其中一种，否则不会继续路由
        }

        @Override
        public void init(Context context) {
            // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
        }
    }

    @Interceptor(priority = 10, name = "权限检查拦截器")
    public class PermissionInterceptor implements IInterceptor {
        @Override
        public void process(Postcard postcard, InterceptorCallback callback) {
            callback.onContinue(postcard);  // 处理完成，交还控制权
            // callback.onInterrupt(new RuntimeException("我觉得有点异常"));      // 觉得有问题，中断路由流程

            // 以上两种至少需要调用其中一种，否则不会继续路由
        }

        @Override
        public void init(Context context) {
            // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
        }
    }

    // 降级策略
    // 实现DegradeService接口，并加上一个Path内容任意的注解即可
    @Route(path = "/dev/test1")
    public static class DegradeServiceImpl implements DegradeService {
        @Override
        public void onLost(Context context, Postcard postcard) {
            // do something.
        }

        @Override
        public void init(Context context) {

        }
    }


    /**
     * -------------跨模块调用对象的方法----------------
     */

    // 拓展
    public static interface FragmentProvider extends IProvider {
        Fragment newInstance(Activity context, int containerId, FragmentManager fragmentManager,
                             Bundle bundle, String tag);
    }

    // 实现
    @Route(path = "/coodev/new_fragment_provider")
    public static class NewFragmentProvider implements FragmentProvider {
        @Override
        public void init(Context context) {

        }

        @Override
        public Fragment newInstance(Activity context, int containerId, FragmentManager fragmentManager,
                                    Bundle bundle, String tag) {
            return ViewUtils.replaceFragment(context, containerId, fragmentManager, bundle, tag);
        }
    }

    // 调用
    public void testProvider(Activity context, int containerId, FragmentManager fragmentManager,
                             Bundle bundle, String tag) {
        ((FragmentProvider) ARouter.getInstance().build("/coodev/new_fragment_provider").navigation(context))
                .newInstance(context, containerId, fragmentManager, bundle, tag);
    }

}
