package com.example.hante.rxjavamodel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.hante.rxjavamodel.model.User;
import com.example.hante.rxjavamodel.rxbus.RxBusMsg;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static io.reactivex.Observable.create;

public class RxBusActivity extends AppCompatActivity {

    private static final String TAG = "RxBusActivity";
    @BindView(R.id.rxBus_text)
    TextView rxBusText;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);
        ButterKnife.bind(this);
        RxJavaM();
        observable();
        flowable();

        RxBusMsg.getDefault().post(new User("汉庭", 222, 110));
    }



    private void RxJavaM () {

      Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe (ObservableEmitter<Integer> e) throws Exception {
                e.onNext(2);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply (Integer s) throws Exception {
                return "this is two "  + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept (String s) throws Exception {
                Log.d(TAG, "accept: 打印" + s);
            }
        });
        // flatMap 转换操作符
        create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe (ObservableEmitter<String> e) throws Exception {

            }
        }).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply (String s) throws Exception {
                return null;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept (String s) throws Exception {
                // 接收结果
            }
        });
        Observable<String> o1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe (ObservableEmitter<String> e) throws Exception {

            }
        });
        Observable<Integer> o = Observable.create(e -> {
            e.onNext(1);
            e.onNext(2);
            e.onComplete();
        });
        Observable<Integer> o2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe (ObservableEmitter<Integer> e) throws Exception {

            }
        });

        // zip  操作符，将两个Observable  组合到一起
        Observable.zip(o1, o2, new BiFunction<String, Integer, String>() {
            @Override
            public String apply (String s, Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept (String s) throws Exception {

            }
        });

        Observable.zip(o1, o2, (s, integer) -> {return  s + integer;})
                .subscribe(s -> { Log.d(TAG, "RxJavaM: 我是小黄人");});
    }



    private void flowable(){
        Flowable<String> flowable1 =  Flowable.create(e -> {
            e.onNext("哈哈哈哈");
        }, BackpressureStrategy.BUFFER);

        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe (FlowableEmitter<String> e) throws Exception {
                long requested = e.requested();

            }
        }, BackpressureStrategy.BUFFER); // add a param

        Subscriber<String> stringSubscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe (Subscription s) {
                s.request(Long.MAX_VALUE); // 下游处理 的能力
            }

            @Override
            public void onNext (String s) {

            }

            @Override
            public void onError (Throwable t) {

            }

            @Override
            public void onComplete () {

            }
        };
        flowable.subscribe(stringSubscriber);
    }


    public void observable(){

        Observable<String> obs =
                Observable.just("woshihante");
        Consumer consumer = new Consumer<String>() {
            @Override
            public void accept (String s) throws Exception {

            }
        };
        obs.subscribe(consumer);

        Flowable.just("")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept (String s) throws Exception {
                        Log.d(TAG, "accept: " + s);
                    }
                });

        Flowable.just("woshihante")
                .subscribe(s -> {
                    Log.d(TAG, "observable: " + s);
                });
        Flowable.just("我是大魔王")
                .map(s -> {
                    return s;
                });
        new Thread(new Runnable() {
            @Override
            public void run () {
                Log.d(TAG, "run: woshidamowang");
            }
        }).start();
        new Thread(() -> {
            int a = 1;
            int b = 2;
            Log.d(TAG, "Lambda: woshidamowang" + a + b);
        }).start();
    }
}
