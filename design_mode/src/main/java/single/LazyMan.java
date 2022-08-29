package single;

import java.lang.reflect.Constructor;

public class LazyMan {
    //懒汉式单例模式
    //第一步，私有化构造器
    private LazyMan() {
        System.out.println(Thread.currentThread().getName() + "OK");
    }

    //第二部，创建获取对象方法
    public volatile static LazyMan lazyMan;

    //双重检测锁模式的单例模式 DCL懒汉式
    public static LazyMan getInstance() {
        if (lazyMan == null) {
            synchronized (LazyMan.class) {
                if (lazyMan == null){
                    lazyMan = new LazyMan(); // 不是原子性操作
                }
            }

            /**
             * 1. 分配内存空间
             * 2. 执行构造方法
             * 3. 对象指向空间
             * 有可能发生指令重排现象。
             *
             * 123
             * 132  A
             * B  认为不等于null,但是还没有构造。  要保证不能指令重排
             */
        }
        return lazyMan;
    }


    //通过反射获取多个对象
    public static void main(String[] args) throws Exception {
        LazyMan instance = LazyMan.getInstance();
        Constructor<? extends LazyMan> declaredConstructor =
                instance.getClass().getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazyMan lazyMan = declaredConstructor.newInstance();
        System.out.println(lazyMan == instance);
    }
}


