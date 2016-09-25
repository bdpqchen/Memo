package com.example.chen.memo.model;

import android.view.View;

import com.example.chen.memo.presenter.ValidatePresenter;
import com.example.chen.memo.view.main.MainActivity;

/**
 * Created by cdc on 16-9-23.
 */
public interface ValidateModelImpl {
    void checkLogin(MainActivity view, ValidatePresenter validatePresenter);
    void setupPassword(MainActivity view, ValidatePresenter validatePresenter);
}
