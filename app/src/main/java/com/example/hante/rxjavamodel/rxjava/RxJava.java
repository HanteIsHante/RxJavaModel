package com.example.hante.rxjavamodel.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created By HanTe
 */

public class RxJava {

    Observable<String> mObservable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe (ObservableEmitter<String> e) throws Exception {
            e.onNext("我是大魔王");
        }
    });

    Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onSubscribe (Disposable d) {

        }

        @Override
        public void onNext (String s) {

        }

        @Override
        public void onError (Throwable e) {

        }

        @Override
        public void onComplete () {

        }
    };

//----------------串联起来

    public void RxJavaM(){




        Observable<Integer> mIntegerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe (ObservableEmitter<Integer> e) throws Exception {
                e.onNext(111);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());

        mIntegerObservable.observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept (Integer integer) throws Exception {

            }
        });
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept (Integer integer) throws Exception {

            }
        };
        mIntegerObservable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept (Integer integer) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept (Integer integer) throws Exception {

                    }
                });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe (Disposable d) {
                    d.dispose();
            }

            @Override
            public void onNext (Integer integer) {

            }

            @Override
            public void onError (Throwable e) {

            }

            @Override
            public void onComplete () {

            }
        };
        mIntegerObservable.subscribe(observer);// 订阅
    }
}
