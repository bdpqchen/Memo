package com.example.chen.memo.model;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.SimpleCrypto;
import com.example.chen.memo.bean.Imitation;
import com.example.chen.memo.presenter.ValidatePresenterImpl;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.utils.PrefUtils;
import com.example.chen.memo.view.main.MainActivity;

import java.util.ArrayList;

import static com.example.chen.memo.application.CustomApplication.KEY_UNIQUE_PASSWORD;

/**
 * Created by cdc on 16-9-23.
 */
public class ValidateModelImpl implements IValidateModel {

    private MainActivity view;
    private ValidatePresenterImpl validatePresenter;
    private EditText dialogPwd;
    private EditText setupPwd1;
    private EditText setupPwd2;

    @Override
    public void checkLogin(final MainActivity view, final ValidatePresenterImpl validatePresenterImpl) {

        this.view = view;

        LayoutInflater inflater = LayoutInflater.from(view);
        View layout = inflater.inflate(R.layout.dialog_input, null);
        dialogPwd = (EditText) layout.findViewById(R.id.dialog_pwd);
        this.validatePresenter = validatePresenterImpl;
        AlertDialog.Builder builder = new AlertDialog.Builder(view);
        builder.setTitle(R.string.hint_input_pwd);
        builder.setNegativeButton(R.string.btn_negative, null);
        builder.setNeutralButton(R.string.find_back_pwd, null);
        builder.setPositiveButton(R.string.btn_positive, positiveListener);
        builder.setView(layout);
        builder.show();

    }

    private DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //删除该item记录
            String unique_pwd = PrefUtils.uniquePwd();
            String input_pwd = String.valueOf(dialogPwd.getText());
            LogUtils.i("dialogPwd.gettext()", input_pwd);
            //隐藏输入法
            try {
                LogUtils.i("input", input_pwd);
                String pwd = SimpleCrypto.enCrypto(input_pwd, KEY_UNIQUE_PASSWORD);
                LogUtils.i("pwd_former", SimpleCrypto.deCrypto(pwd, KEY_UNIQUE_PASSWORD));
                LogUtils.i("pwd", pwd);
                LogUtils.i("preference_former", SimpleCrypto.deCrypto(unique_pwd, KEY_UNIQUE_PASSWORD));
                LogUtils.i("preference ", unique_pwd);
                //密码验证成功
                if (pwd.equals(unique_pwd) && !input_pwd.equals("")) {
                    dialog.dismiss();
                    view.hideKeyboard();
                    //清空错误密码统计
                    PrefUtils.setErrorPwdCount(0);
                    validatePresenter.loginSuccess();
                } else {
                    //密码错误次数累加器
                    int i = PrefUtils.getErrorPwdCount();
                    PrefUtils.setErrorPwdCount(i+1);
                    if (i < ValidatePresenterImpl.maxErrorPwdTime) {
                        Toast.makeText(view, R.string.error_pwd_text, Toast.LENGTH_SHORT).show();
                        //dialog;
                    } else {
                        //dialog.dismiss();//找回密码提示框*

                        Toast.makeText(view, "找回密码", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    };


    @Override
    public void setupPassword(final MainActivity view, final ValidatePresenterImpl validatePresenterImpl) {
        this.view = view;
        this.validatePresenter = validatePresenterImpl;

        //加载自定义布局
        LayoutInflater inflater = LayoutInflater.from(view);
        View layout = inflater.inflate(R.layout.setup_password_dialog, null);
        setupPwd1 = (EditText) layout.findViewById(R.id.pwd1);
        setupPwd2 = (EditText) layout.findViewById(R.id.pwd2);

        AlertDialog.Builder builder = new AlertDialog.Builder(view);
        builder.setTitle(R.string.first_enter);
        builder.setView(layout);
        builder.setPositiveButton(R.string.btn_positive, setPwdPositiveListener);
        builder.show();


    }

    private DialogInterface.OnClickListener setPwdPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            String pwd1 = String.valueOf(setupPwd1.getText());
            String pwd2 = String.valueOf(setupPwd2.getText());
            view.hideKeyboard();
            if (pwd1.equals(pwd2) && !pwd1.equals("")) {
                try {
                    String pwd = SimpleCrypto.enCrypto(pwd1, KEY_UNIQUE_PASSWORD);
                    PrefUtils.setUniquePwd(pwd);
                    PrefUtils.setFirstOpen(false);
                    dialog.dismiss();
                    Toast.makeText(view, R.string.set_pwd_succeed, Toast.LENGTH_LONG).show();
                    validatePresenter.setupSuccess();
                    //startActivity(SettingsActivity.class);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                //密码输入不一致
                Toast.makeText(view, R.string.not_same_pwd, Toast.LENGTH_SHORT).show();
            }

        }

    };

    public void initImitateData() {

        final int size = 20;
        ArrayList<String> arr = new ArrayList<>(size);
        arr.add("夏季的考验炎热的夏季往往是考验人毅力的时候，每个人的");
        arr.add("不同的目标就决定了不同的方式,我们同样指向学生的写作水平的提高");
        arr.add("就是说一个人的气韵，无论经历过多少变迁，依旧可以做到从容");
        arr.add("匆匆而逝的光阴，多少寂寞呈几番黯然的绽放。惊醒的落叶");
        arr.add("仿佛在梦里遇见。无数次被人提及的时光，此刻，她就像是一位身穿淡绿衣裙，有着精致妆容的女子");
        arr.add("远远看去秋姑娘早已把山涂成五颜六色的了。有黄黄的银杏树");
        arr.add("一边不停地在心里祈祷着。此时正是上班高峰期，前面一辆辆车子就像一只只乌龟似的趴在地上半天才“挪”一步");
        arr.add("可还是晚了一点点，跑进校门时，铃声已经响起。");
        arr.add("低头族是一个过度使用电子产品的结果。当然，这并不能怪他们，电子产品就像一个黑洞");
        arr.add("我曾经也是低头族当中的一员,每天在家都会时不时的打开手机.但是这种长时间盯着屏幕的行为已");
        arr.add("上宽带网首先满足了我爸爸的要求，爸爸再也不用顶着烈日去售报厅买报纸了，");
        arr.add("上网之后，我就像是长了一双千里眼，什么信息都能看到，例如：美国NBA篮球赛的情况");
        arr.add("现在的人，平日里工作都很忙，又要忙学习，又要忙生活。但是我观察发现，大家虽然忙");
        arr.add("小毛病还特多。不用电动剃须刀，不吃洋快餐，");
        arr.add("");
        arr.add("");
        arr.add("");
        arr.add("");
        arr.add("");
        arr.add("");
        arr.add("");
        arr.add("");
        arr.add("");
        arr.add("");
        arr.add("");
        Imitation imitation = new Imitation();
        for(int i = 0; i < size; i++){
            imitation.setContent(arr.get(i));
            imitation.save();
        }



    }
}
