package com.example.hante.rxjavamodel.rxbus;


import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.SerializedSubscriber;

/**
 * Created By HanTe
 */

public final class RxBusMsg {

    private  final FlowableProcessor<Object> mBus;
    private  static volatile RxBusMsg sRxBus = null;

    private RxBusMsg() {
        // 调用 toSerialized()，保证线程安全
        mBus = PublishProcessor.create().toSerialized();
    }

    public static synchronized RxBusMsg getDefault(){
        if(sRxBus == null) {
            synchronized(RxBusMsg.class){
                if (sRxBus == null){
                    sRxBus = new RxBusMsg();
                }
            }
        }
        return  sRxBus;
    }


    /**
     *
     * @param o 发送对象
     */
    public void post(Object o){
       new SerializedSubscriber<>(mBus).onNext(o);
    }


    /**
     *  确定接收消息的类型
     */
    public <T> Flowable<T> toFlowable(Class<T> tClass){
        return mBus.ofType(tClass);
    }

    /**
     *
     * @return 判断是否有订阅者
     */
    public boolean hasSubscribers(){
        return mBus.hasSubscribers();
    }
}
