package com.example.hante.rxjavamodel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.hante.rxjavamodel.model.User;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.to_next)
    Button toNext;


    private Observer<String> observer;
    private Observer<Integer> observerInteger;
    private Observable<String> observable;
    private Observable<String> obsJust;
    public static Observable<String> obsIterable;
    public static Observable<String> obsDefer;
    public static Observable<User> obsUser;
    private Observable<Integer> obsMap;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        observable();
        observer();
        observable.subscribe(observer);
        obsJust.subscribe(observer);
        obsIterable.subscribe(observer);
        obsDefer.subscribe(observer);
        obsMap.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())//需要编译 rxAndroid
                .subscribe(observerInteger);
        obsMap.subscribe(new Consumer<Integer>() {
            @Override
            public void accept (Integer integer) throws Exception {
                Log.d(TAG, "accept: 哼哼哈嘿" + integer);
            }
        });
    }

    private void observer () {
        // Observer 的创建
        observer = new Observer<String>() {
            private Disposable mDisposable;

            @Override
            public void onSubscribe (Disposable d) {
                //d.dispose();
                mDisposable = d;
            }

            // 观察者接收到通知，进行相关操作
            @Override
            public void onNext (String s) {
                Log.d(TAG, "onNext: 输出 " + s);
                Toast.makeText(MainActivity.this, "显示" + s, Toast.LENGTH_SHORT).show();

                // 根据情况来执行dispose();
                mDisposable.dispose();// 切断输出
            }

            @Override
            public void onError (Throwable e) {

            }

            @Override
            public void onComplete () {
                Toast.makeText(MainActivity.this, "任务完成 哈希", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onComplete: 任务完成hello baby");
            }
        };

        observerInteger = new Observer<Integer>() {
            @Override
            public void onSubscribe (Disposable d) {

            }

            @Override
            public void onNext (Integer integer) {
                Log.d(TAG, "apply: map() 操作符 输出" + integer);

            }

            @Override
            public void onError (Throwable e) {

            }

            @Override
            public void onComplete () {

            }
        };
    }

    private void observable () {

        //Observable的 Create 创建：
        observable = Observable.create(new ObservableOnSubscribe<String>() {
            // 可观测的发射器
            @Override
            public void subscribe (ObservableEmitter<String> e) throws Exception {

                for(int i = 0; i < 10; i++) {
                    e.onNext("传送：" + i);
                }
                e.onComplete();
            }
        });

        // Just 方式
        obsJust = Observable.just("Hello! I`m OK");

        // fromIterable()方式
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            list.add("Hello 小白 " + i);
        }
        obsIterable = Observable.fromIterable((Iterable<String>) list);

        // defer() 方式
        obsDefer = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call () throws Exception {
                return Observable.just("Hello 大魔王");
            }
        });

        //interval( )方式 创建一个按固定时间间隔发射整数序列的Observable，可用作定时器。即按照固定2秒一次调用onNext()方法。
        Observable<Long> observable = Observable.interval(2, TimeUnit.SECONDS);

        /**
         * map()操作符，就是把原来的Observable对象转换成另一个Observable对象，
         * 同时将传输的数据进行一些灵活的操作，方便Observer获得想要的数据形式。
         */
        obsMap = Observable.just("五福临门").map(new Function<String, Integer>() {
            @Override
            public Integer apply (String s) throws Exception {
                Log.d(TAG, "apply: map() 操作符" + s);
                return s.length(); // 输出的是 s.length() 长度
            }
        });

        /**
         * flatMap()对于数据的转换比map()更加彻底，如果发送的数据是集合，
         * flatmap()重新生成一个Observable对象，
         * 并把数据转换成Observer想要的数据形式。
         * 它可以返回任何它想返回的Observable对象。
         */
        Observable<Object> obsFlatMap = Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply (List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        });


//        ------------线程调度的使用-------------
        // Disposable 阻断连接
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe (ObservableEmitter<Integer> e) throws Exception {

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept (Integer integer) throws Exception {

                    }
                });

        Observable<String> objS = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe (ObservableEmitter<String> e) throws Exception {

            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
//----------------Flowable--------------

        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe (FlowableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "subscribe: 下游处理事件能力" + e.requested());
                for(int i = 0; i < 10; i++) {
                    e.onNext(i);
                    Log.d(TAG, "subscribe: " + e.requested());
                }
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR); // 增加了个参数 选择被压

        Subscriber<Integer> getSubscriber = new Subscriber<Integer>() {
            private Subscription mSubscription;

            @Override
            public void onSubscribe (Subscription s) {
                Log.d(TAG, "onSubscribe: ");
//                s.request(Long.MAX_VALUE);// 必写 通知被观察者 ，观察者处理事件能力 int  观察者 处理能力
                s.request(2);
//                s.request(9999);
                mSubscription = s;
            }

            @Override
            public void onNext (Integer integer) {

                Log.d(TAG, "onNext: 输出" + integer);
//                mSubscription.cancel();//切断
            }

            @Override
            public void onError (Throwable t) {
                Log.d(TAG, "onError: 发现异常 " + t);
            }

            @Override
            public void onComplete () {

            }
        };
        flowable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber);
// ======================================================================

        obsUser =  Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe (ObservableEmitter<User> e) throws Exception {
                User u = new User("hante", 110, 10);
                e.onNext(u);
            }
        });
    }


    @OnClick(R.id.to_next)
    public void onClick () {
        Toast.makeText(this, "跳转", Toast.LENGTH_SHORT).show();
        Intent ito = new Intent(getApplicationContext(), AcceptMesActivity.class);
        startActivity(ito);
    }
}
