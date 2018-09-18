package warron.phpprojectandroid.VC.FragHome.Setting;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.xutils.DbManager;
import org.xutils.db.table.ColumnEntity;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import warron.phpprojectandroid.Base.BaseActivity;
import warron.phpprojectandroid.Base.CacheTool;
import warron.phpprojectandroid.Base.MyApplication;
import warron.phpprojectandroid.R;
import warron.phpprojectandroid.Tools.ToolbarHelper;
import warron.phpprojectandroid.VC.FragHome.GradeFactory;
import warron.phpprojectandroid.VC.FragHome.model.UserInfoModel;

public class SettingVC extends BaseActivity implements View.OnClickListener {
//    @Override
    EditText editTextChn,editTextMath,editTextEng,
             editTextMiliraty,editTextHis,editTextGeo,
              editTextPhy,editTextBio,editTextChemis,
             editTextGood,editTextPass,editTextDiculity;
    TextView totalScoreLbl;
    Button comfirmBtn;
    UserInfoModel model ;
    public int getContentView() {
        return R.layout.vc_activity_setting;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editTextChn     = (EditText) findViewById(R.id.setting_Chn);
        editTextMath    = (EditText) findViewById(R.id.setting_Math);
        editTextEng     = (EditText) findViewById(R.id.setting_Eng);
        editTextMiliraty= (EditText) findViewById(R.id.setting_Milirality);
        editTextHis     = (EditText) findViewById(R.id.setting_His);
        editTextGeo     = (EditText) findViewById(R.id.setting_Geo);
        editTextPhy     = (EditText) findViewById(R.id.setting_Phy);
        editTextBio     = (EditText) findViewById(R.id.setting_Bio);
        editTextChemis  = (EditText) findViewById(R.id.setting_Chemis);
        editTextGood    = (EditText) findViewById(R.id.setting_Good);
        editTextPass    = (EditText) findViewById(R.id.setting_Pass);
        editTextDiculity= (EditText) findViewById(R.id.setting_Difculity);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                int score = 0;
                if (s.length() != 0){
                    score = Integer.parseInt(String.valueOf(s));
                }
                GradeFactory factory =   GradeFactory.getInstance();
                if (editTextChn.isFocused()){
                    factory.sumScoreChn = score;
                }
                else if (editTextMath.isFocused()) {
                    factory.sumScoreChn = score;
                }
                else  if (editTextEng.isFocused()){
                    factory.sumScoreMath = score;
                }
                else  if (editTextMiliraty.isFocused()){
                    factory.sumScoreEng = score;
                }
                else  if (editTextHis.isFocused()){
                    factory.sumScorePlitical = score;
                }
                else  if (editTextGeo.isFocused()){
                    factory.sumScoreHistory = score;
                }
                else  if (editTextPhy.isFocused()){
                    factory.sumScoreGrography = score;
                }
                else  if (editTextChemis.isFocused()){
                    factory.sumScorePhysical = score;
                }
                else  if (editTextBio.isFocused()){
                    factory.sumScoreBiology = score;
                }
                else  if (editTextPass.isFocused()){
                    factory.sumScoreChimistry = score;
                }
            }
        };
        editTextChn     .addTextChangedListener(textWatcher);
        editTextMath    .addTextChangedListener(textWatcher);
        editTextEng     .addTextChangedListener(textWatcher);
        editTextMiliraty.addTextChangedListener(textWatcher);
        editTextHis     .addTextChangedListener(textWatcher);
        editTextGeo     .addTextChangedListener(textWatcher);
        editTextPhy     .addTextChangedListener(textWatcher);
        editTextBio     .addTextChangedListener(textWatcher);
        editTextChemis  .addTextChangedListener(textWatcher);

        totalScoreLbl   = (TextView) findViewById(R.id.setting_totalScore);
        comfirmBtn      =  (Button)  findViewById(R.id.setting_Confirm);
        comfirmBtn.setOnClickListener(this);
        model = GradeFactory.getCofigModel();
//        try {
//            ArrayList<UserInfoModel> modelList = (ArrayList<UserInfoModel>) CacheTool.getDBManager().selector(UserInfoModel.class).where("keyId", "=", "123").findAll();
//            modelList.size();
//
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
        if (model.hasConfiguration == 1){
            editTextChn      .setText(model.sumScoreChn+"");
            editTextMath     .setText(model.sumScoreMath+"");
            editTextEng      .setText(model.sumScoreEng+"");
            editTextMiliraty .setText(model.sumScorePlitical+"");
            editTextHis      .setText(model.sumScoreHistory+"");
            editTextGeo      .setText(model.sumScoreGrography+"");
            editTextPhy      .setText(model.sumScorePhysical+"");
            editTextBio      .setText(model.sumScoreBiology+"");
            editTextChemis   .setText(model.sumScoreChimistry+"");
            editTextGood     .setText(model.goodRate+"");
            editTextPass     .setText(model.passRate+"");
            editTextDiculity .setText(model.difficultyRate +"");
            setData();
        }
    }
    public void setData(){

        GradeFactory.getInstance(this).allProjectSumScore  = model.sumScoreChn + model.sumScoreMath +  model.sumScoreEng
                + model.sumScorePlitical +model.sumScoreHistory +  model.sumScoreGrography
                + model.sumScorePhysical + model.sumScoreBiology + model.sumScoreChimistry;
        totalScoreLbl.setText("总分: "+ GradeFactory.getInstance(this).allProjectSumScore +"");
    }
    @Override
    public void onClick(View v) {
        GradeFactory factory = GradeFactory.getInstance();
        model.sumScoreBiology = factory.sumScoreBiology;
        model.sumScoreChimistry = factory.sumScoreChimistry;
        model.sumScorePhysical = factory.sumScorePhysical;
        model.sumScoreGrography = factory.sumScoreGrography;
        model.sumScoreHistory = factory.sumScoreHistory;
        model.sumScorePlitical = factory.sumScorePlitical;
        model.sumScoreEng = factory.sumScoreEng;
        model.sumScoreMath = factory.sumScoreMath;
        model.sumScoreChn = factory.sumScoreChn;
        model.allProjectSumScore = factory.allProjectSumScore;
        model.goodRate = factory.goodRate = Float.parseFloat(String.valueOf(editTextGood.getText()));
        model.passRate = factory.passRate = Float.parseFloat(String.valueOf(editTextPass.getText()));
        model.difficultyRate = factory.difficultyRate = Float.parseFloat(String.valueOf(editTextDiculity.getText()));;
        model.hasConfiguration = 1;

        try {
            DbManager manager =  CacheTool.getDBManager();

            TableEntity<UserInfoModel> entity = manager.getTable(UserInfoModel.class);
            LinkedHashMap<String, ColumnEntity> columnMap = entity.getColumnMap();
            String json = JSON.toJSONString(model);
            Map<String, String> map = JSON.parseObject(json, Map.class);//将对象中的属性和值转换成字典
            for (String column : map.keySet()) {//如果不存在列就添加 同时要在UserInfoModel中添加 列
                if (!columnMap.containsKey(column)) {
                    manager.addColumn(UserInfoModel.class, column);
                }
            }
            model.keyId = "123";
            manager.saveOrUpdate(model);
            manager.close();
        } catch (DbException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GradeFactory.getInstance().recalculate();
        finish();
//        x.task().run(new Runnable() { // 异步执行
//            @Override
//            public void run() {
//                x.task().post(new Runnable() { // UI同步执行
//                    @Override
//                    public void run() {
//                        try {
//                            DbManager manager = ((MyApplication) x.app()).manager;
//                            TableEntity<UserInfoModel> entity = manager.getTable(UserInfoModel.class);
//                            LinkedHashMap<String, ColumnEntity> columnMap = entity.getColumnMap();
//                            String json = JSON.toJSONString(model);
//                            Map<String, String> map = JSON.parseObject(json, Map.class);//将对象中的属性和值转换成字典
//                            for (String column : map.keySet()) {//如果不存在列就添加 同时要在UserInfoModel中添加 列
//                                if (!columnMap.containsKey(column)) {
//                                    manager.addColumn(UserInfoModel.class, column);
//                                }
//                            }
//                            model.keyId = "123";
//                            manager.saveOrUpdate(model);
//                            manager.close();
//                        } catch (DbException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        x.task().post(new Runnable() { // UI同步执行
//                            @Override
//                            public void run() {
//                                GradeFactory.getInstance().recalculate();
//                                finish();
//                            }
//                        });
//                    }
//                });
//            }
//        });
    }
    @Override
    protected void initToolBar(final ToolbarHelper toolbarHelper, Boolean isShowBackBtn) {
        super.initToolBar(toolbarHelper,false); // 默认不显示原生标题
        toolbarHelper.setTitle("设置");
        toolbarHelper.setIsShowRightBtn(false);
    }

}
