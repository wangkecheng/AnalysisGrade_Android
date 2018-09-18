package warron.phpprojectandroid.Base;

import android.app.Application;
import android.content.Intent;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;


public class CacheTool {

    public static DbManager getDBManager(){
        DbManager manager = ((MyApplication)x.app()).creatDB();
        return manager;
    }

    public static void saveOrUpdate(Object object){
        try {
            getDBManager().saveOrUpdate(object);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
