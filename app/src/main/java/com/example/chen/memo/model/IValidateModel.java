package com.example.chen.memo.model;

import com.example.chen.memo.presenter.ValidatePresenterImpl;
import com.example.chen.memo.view.main.MainActivity;

/**
 * Created by cdc on 16-9-23.
 */
public interface IValidateModel {
    void checkLogin(MainActivity view, ValidatePresenterImpl validatePresenterImpl);
    void setupPassword(MainActivity view, ValidatePresenterImpl validatePresenterImpl);
}
