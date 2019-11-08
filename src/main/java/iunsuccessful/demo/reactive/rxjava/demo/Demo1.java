package iunsuccessful.demo.reactive.rxjava.demo;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
 * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
 * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
 * Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
 *
 *
 * subscribeOn(Scheduler.io()) 和 observeOn(AndroidSchedulers.mainThread()) 的使用方式非常常见，它适用于多数的 『后台线程取数据，主线程显示』的程序策略。
 *
 * 依韵 2019-11-07
 */
public class Demo1 {

    private static final Logger logger = LoggerFactory.getLogger(Demo1.class);

    public static void main(String[] args) throws Exception {

        // 第一步：初始化Observable
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            logger.info("Observable emit 1");
            e.onNext(1);


        })
                .observeOn(Schedulers.single())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() { // 第三步：订阅

            // 第二步：初始化Observer
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                logger.info("observer on subscribe...");
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                logger.info("observer on next...");
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
//                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                logger.info("onError : value : " + e.getMessage() + "\n" );
            }

            @Override
            public void onComplete() {
                logger.info("onComplete" + "\n" );
            }
        });

        System.in.read();

    }

}
