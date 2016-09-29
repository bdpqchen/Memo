package com.example.chen.memo.presenter;


import com.example.chen.memo.view.common.NextActivity;
import com.example.chen.memo.view.main.MainActivity;

/**
 * Created by cdc on 16-9-23.
 */
public interface IValidatePresenter {
    void login(MainActivity view, NextActivity nextActivity);
    void loginSuccess();

    void setup(MainActivity view, NextActivity nextActivity);

    void setupSuccess();
}
