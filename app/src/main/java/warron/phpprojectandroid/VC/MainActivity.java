package warron.phpprojectandroid.VC;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;


import eu.long1.spacetablayout.TabOnclickLisener;
import warron.phpprojectandroid.Base.BaseActivity;
import warron.phpprojectandroid.Base.CacheTool;
import warron.phpprojectandroid.Base.MyApplication;
import warron.phpprojectandroid.R;
import warron.phpprojectandroid.Tools.ToolbarHelper;
import warron.phpprojectandroid.VC.FragHome.FragHome;
import warron.phpprojectandroid.VC.FragHome.GradeFactory;
import warron.phpprojectandroid.VC.FragHome.Setting.SettingVC;
import warron.phpprojectandroid.VC.FragHome.model.UserInfoModel;

public class MainActivity extends BaseActivity {
    SpaceTabLayout tabLayout;
    private ToolbarHelper toolbarHelper;

    List<Fragment> fragmentList;//fragment 页卡项目
    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DbManager manager = CacheTool.getDBManager();
        UserInfoModel model = GradeFactory.getCofigModel();
        model.sumScoreChn = 1234;
        model.keyId = "123";
        try {
            manager.saveOrUpdate(model);
            UserInfoModel test = manager.selector(UserInfoModel.class).where("keyId", "=", "123").findFirst();

        } catch (DbException e) {
            e.printStackTrace();
        }


        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.MOUNT_FORMAT_FILESYSTEMS,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.DELETE_CACHE_FILES};//,Manifest.permission.READ_EXTERNAL_STORAGE,

        ActivityCompat.requestPermissions(this, permissions, 321);
        fragmentList = new ArrayList<>();
        fragmentList.add(new FragHome());
        final RelativeLayout coordinatorLayout = (RelativeLayout) findViewById(R.id.rlayout);

        List<String> fragTitList = new ArrayList<String>();//标题
        fragTitList.add("导出方式");

        List<Integer> fragImgList = new ArrayList<Integer>();//图片
        fragImgList.add(R.mipmap.conversation_nor);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);
        tabLayout.initialize(viewPager, getSupportFragmentManager(), fragmentList,fragTitList,fragImgList);
        tabLayout.setTabOnClickListener(new TabOnclickLisener() {
            @Override
            public void tabOnclick(Integer index, String tabTit) {
                Snackbar snackbar
                        = Snackbar.make(coordinatorLayout, "Welcome to SpaceTabLayout"+ tabLayout.getCurrentPosition(), Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
            @Override
            public void tabMoveAction(Integer index, String tabTit) {
                if (tabTit.equals("我的")){
                    setToolBar(tabTit,true);
                    return;
                }
                setToolBar(tabTit,false);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void setToolBar(String title, Boolean isShowRightBtn){//切换页卡的时候，改变标题
        toolbarHelper.setTitle(title);
        toolbarHelper.setIsShowRightBtn(isShowRightBtn);//是否显示右边的按钮
    }
    @Override
    protected void initToolBar(final ToolbarHelper toolbarHelper, Boolean isShowBackBtn) {
        super.initToolBar(toolbarHelper,false); // 默认不显示原生标题
        this.toolbarHelper = toolbarHelper;
        toolbarHelper.setTitle("朋友圈");
        toolbarHelper.setMenuTitle("编辑", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ViewPager viewPager =  (ViewPager) findViewById(R.id.viewPager);

            }
        });
    }
}