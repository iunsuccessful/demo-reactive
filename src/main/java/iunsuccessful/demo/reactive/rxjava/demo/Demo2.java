package iunsuccessful.demo.reactive.rxjava.demo;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 依韵 2019-11-07
 */
public class Demo2 {

    public static void main(String[] args) {
        Observable observable = Observable.just("Hello", "Hi", "Aloha");
//        observable.subscribe(System.out::println);
        observable.map(o -> o + " test").subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println(o);
            }
        });


    }

}
