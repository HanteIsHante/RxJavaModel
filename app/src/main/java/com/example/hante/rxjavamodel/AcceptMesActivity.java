package com.example.hante.rxjavamodel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.hante.rxjavamodel.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AcceptMesActivity extends AppCompatActivity {

    private static final String TAG = "AcceptMesActivity";
    @BindView(R.id.accept_message)
    TextView acceptMessage;
    @BindView(R.id.User_Text)
    TextView UserText;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_mes);
        ButterKnife.bind(this);

        observer();
    }

    private void observer () {
        Observer<String> observer;
        Observer<User> obsUser;
        observer = new Observer<String>() {
            @Override
            public void onSubscribe (Disposable d) {

            }

            @Override
            public void onNext (String s) {
                Log.d(TAG, "onNext: 输出内容" + s);

                List<String> li = new ArrayList<>();
                li.add(s);

                acceptMessage.setText(li.toString());
            }

            @Override
            public void onError (Throwable e) {
                Log.d(TAG, "onNext: ERROR 错误信息" + e);
            }

            @Override
            public void onComplete () {

            }
        };
        obsUser = new Observer<User>() {
            @Override
            public void onSubscribe (Disposable d) {

            }

            @Override
            public void onNext (User user) {
                UserText.setText("展现内容" + user.getAge()+ "---"  + user.getName() + "---" + user
                        .getPhone());
            }

            @Override
            public void onError (Throwable e) {

            }

            @Override
            public void onComplete () {

            }
        };
        MainActivity.obsIterable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        MainActivity.obsUser.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(obsUser);

    }
}
