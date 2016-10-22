package com.example.chen.memo.model;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.SimpleCrypto;
import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.bean.Imitation;
import com.example.chen.memo.bean.Memo;
import com.example.chen.memo.presenter.ValidatePresenterImpl;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.utils.PrefUtils;
import com.example.chen.memo.view.main.MainActivity;
import com.example.chen.memo.view.memo.MemoListActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

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
    private final String STATUS_WHERE = "status = ?";
    private final String VALID_DATA = String.valueOf(CustomApplication.RECORD_STATUS_VALID);


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
        builder.setNeutralButton(R.string.find_back_pwd, findBackPwdListener);
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
                    Toast.makeText(view, "密码验证成功", Toast.LENGTH_SHORT).show();
                    validatePresenter.loginSuccess();
                    LogUtils.v("密码验证成功");
                } else {
                    //密码错误次数累加器
                    int i = PrefUtils.getErrorPwdCount();
                    PrefUtils.setErrorPwdCount(i+1);
                    if (i < ValidatePresenterImpl.maxErrorPwdTime) {
                        Toast.makeText(view, R.string.error_pwd_text, Toast.LENGTH_SHORT).show();
                        //dialog;
                    } else {
                        //dialog.dismiss();//找回密码提示框*
                        validatePresenter.findBackPwdDialog();

                        Toast.makeText(view, "找回密码", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    };

    private DialogInterface.OnClickListener findBackPwdListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            validatePresenter.onPositiveFirst();
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

    public ArrayList<String> initImitateData(String realData, int position) {

        final int size = 27;
        ArrayList<String> arr = new ArrayList<>(size);
        //0-3
        arr.add("夏季的考验炎热的夏季往往是考验人毅力的时候，每个人的");
        arr.add("不同的目标就决定了不同的方式,我们同样指向学生的写作水平的提高");
        arr.add("骑着三轮车往回走，经过一条无人的小巷时，从小巷的拐角处，");
        arr.add("就是说一个人的气韵，无论经历过多少变迁，依旧可以做到从容");

        //4-7
        arr.add("匆匆而逝的光阴，多少寂寞呈几番黯然的绽放。惊醒的落叶");
        arr.add("仿佛在梦里遇见。无数次被人提及的时光，此刻，她就像是一位身穿淡绿衣裙，有着精致妆容的女子");
        arr.add("远远看去秋姑娘早已把山涂成五颜六色的了。有黄黄的银杏树");
        arr.add("我把这个故事转给你，希望你能回答我，");

        arr.add("一边不停地在心里祈祷着。此时正是上班高峰期，前面一辆辆车子就像一只只乌龟似的趴在地上半天才“挪”一步");
        arr.add("可还是晚了一点点，跑进校门时，铃声已经响起。");
        arr.add("低头族是一个过度使用电子产品的结果。当然，这并不能怪他们，电子产品就像一个黑洞");
        arr.add("学校其实很一般，不过是本科，而且我的高考成绩");

        arr.add("我曾经也是低头族当中的一员,每天在家都会时不时的打开手机.但是这种长时间盯着屏幕的行为已");
        arr.add("上宽带网首先满足了我爸爸的要求，爸爸再也不用顶着烈日去售报厅买报纸了，");
        arr.add("上网之后，我就像是长了一双千里眼，什么信息都能看到，例如：美国NBA篮球赛的情况");
        arr.add("她一定要请我吃饭，我就吃了，吃完后我付了账，又带着她在");

        arr.add("现在的人，平日里工作都很忙，又要忙学习，又要忙生活。但是我观察发现，大家虽然忙");
        arr.add("孩子小毛病还特多。不用电动剃须刀，不吃洋快餐，");
        arr.add("与之前活过的日子相比较，发现这才叫做真正有意义的活着，真正");
        arr.add("在解放路距离交通指挥信号灯400米处，有一辆自备");

        arr.add("表姐到家里来做客，一个下午，道尽了为人妈妈的烦恼和辛酸。表姐家有女初长成，");
        arr.add(" 别人爱你的时候，你就是世界 别人不爱你了，你就什么都不是");
        arr.add("在新牙刷的包装盒上也可以贴一张，记录上次换牙刷的时间");
        arr.add("追尾事故车头受损位置应该在右边，也就是副驾驶室的位置，因为司机往往是最先觉察危险的人");

        arr.add("回到家再整理整理，誊写在本子上。做完再一条条划掉");
        arr.add("国庆放假时间的安排：第一天，准备行李，路途的必备品");
        arr.add("周五开会时间14:20-16:00.地点：综合办公楼大厅");
        arr.add("从那天起，我的身后多了一个小跟班，瘦小的你，在我的保护下才可以茁壮的成长");

        arr.add("周三交大物作业，截止到22点，作业标题为姓名加学号加课题名称");
        arr.add("周末约了表哥去打篮球，到时跟他打电话");
        arr.add("老马借了800块，说是下周还。。。。");
        arr.add("坐在咖啡馆里，两个人之间的气氛很是尴尬，没有什么话题，");

        arr.add("从下周开始使用最新版的计时系统，保持文档的更新进度到9月份为止");
        arr.add("无论是走路吃饭喝咖啡都在上网的普通人，还是高度需要社交网络刷存在感的网红，网络瘫痪简直比末日还可怕！");
        arr.add("我是暂时还不清楚到底是谁干的，但是感觉他一定来头很大！要我说就是中国和俄罗斯！");
        arr.add("可是他们的钱太少了，少得只够维持最基本的日常生活开支。");
        //arr.size();

        ArrayList<String> realList = new ArrayList<>(10);
        int l = 0;
        for(int i = 0; i < 10; i++){
            if(i != position){
                //每组三选一

                int j = l * 4;
                int k = j + 3;
                l++;
                realList.add(i, arr.get(getRandom(j, k)));
            }else{
                realList.add(position, realData);
            }
        }
        return realList;
    }

    public int getRandom(int least, int total){
        int a = total - least;
        double B = Math.random() * a;
        return (int) (B + least);
    }

    public int getMemoDataCount() {
        return DataSupport.where(STATUS_WHERE, VALID_DATA).count(Memo.class);
    }

    public String getOneRealDataMemo(){
        String s = "";
        List<Memo> memoList = DataSupport.select("memo").where(STATUS_WHERE, VALID_DATA).find(Memo.class);
        int total = memoList.size();
        if(total > 0){
            s = memoList.get(getRandom(1, total)).getMemo();
        }
        return s;
    }

    public String getOneRealDataDiary() {
        String s = "";
        List<Diary> diaryList = DataSupport.select("diary").where(STATUS_WHERE, VALID_DATA).find(Diary.class);
        int total = diaryList.size();
        if(total > 0){
            s = diaryList.get(getRandom(1, total)).getDiary();
        }
        return s;
    }
}
