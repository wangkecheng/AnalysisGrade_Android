package warron.phpprojectandroid.VC.FragHome;

import android.os.Environment;
import android.util.Log;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import warron.phpprojectandroid.Base.CacheTool;
import warron.phpprojectandroid.VC.FragHome.model.ClassModel;
import warron.phpprojectandroid.VC.FragHome.model.UserInfoModel;
import warron.phpprojectandroid.VC.FragHome.model.StudentModel;

public class GradeFactory {

    ArrayList<StudentModel> arrModel;
    ArrayList<StudentModel> arrCollectModel;//年级排名
    ArrayList<ClassModel> arrClassModel;//装的是班级模型
    int allProjectSumScore;
    int sumScoreChn;
    int sumScoreMath;
    int sumScoreEng;

    int sumScorePlitical;
    int sumScoreHistory;
    int sumScoreGrography;

    int sumScorePhysical;
    int sumScoreBiology;
    int sumScoreChimistry;

    int totalScoreLbl;
    float goodRate;//优生率
    float passRate;//及格率
    float difficultyRate;//学困率


    public static UserInfoModel getCofigModel() {
        DbManager manager  =  CacheTool.getDBManager();
        try {
            List<UserInfoModel> list = manager.selector(UserInfoModel.class)
                        .where("id", "=", 123)
                        .findAll();
                if (list != null && list.size() != 0) {
                    return list.get(0);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return new UserInfoModel();//返回一个所有字段都为空的model
    }
    public static void saveOrUpdate(Object object){
        try {
            CacheTool.getDBManager().saveOrUpdate(object);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private  static  GradeFactory instance  = null;
    public   static synchronized GradeFactory getInstance(){

        if (instance == null){
            instance = new GradeFactory();
            instance.sumScoreBiology  =
            instance.sumScoreChimistry=
            instance.sumScorePhysical =
            instance.sumScoreGrography=
            instance.sumScoreHistory  =
            instance.sumScorePlitical =
            instance.sumScoreEng      =
            instance.sumScoreMath     =
            instance.sumScoreChn      = 100;
            instance.allProjectSumScore = 900;
            instance.goodRate = (float) 0.8;
            instance.passRate = (float)0.6;
            instance.difficultyRate = (float)0.3;

//            UserInfoModel conModel = GradeFactory.getCofigModel();//
//            if (conModel.hasConfiguration == 1) {//如果有值
//                instance.sumScoreBiology  = conModel.sumScoreBiology;
//                instance.sumScoreChimistry= conModel.sumScoreChimistry;
//                instance.sumScorePhysical = conModel.sumScorePhysical;
//                instance.sumScoreGrography= conModel.sumScoreGrography;
//                instance.sumScoreHistory  = conModel.sumScoreHistory;
//                instance.sumScorePlitical = conModel.sumScorePlitical;
//                instance.sumScoreEng      = conModel.sumScoreEng;
//                instance.sumScoreMath     = conModel.sumScoreMath;
//                instance.sumScoreChn      =  conModel.sumScoreChn;
//                instance.allProjectSumScore =  conModel.allProjectSumScore;
//                instance.goodRate =  conModel.goodRate;
//                instance.passRate =  conModel.passRate;
//                instance.difficultyRate =  conModel.difficultyRate;
//            }
        }
        return instance;
    }
    public  void  initArrModel(String fileUrl){
        this.arrModel =  new ArrayList<StudentModel>();
        new Thread(){
            @Override
            public void run() {
                super.run();
                Workbook workbook = null;
                FileInputStream inputStream;
                try {
                    File file=new File(Environment.getExternalStorageDirectory()+File.separator+"analysis.xls");// Environment.getExternalStorageDirectory() getResources().getString(R.string.app_name)
                    workbook = Workbook.getWorkbook(file);
                    workbook.getNumberOfSheets();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Sheet sheet = workbook.getSheet(0);
                int rowCount = sheet.getRows();
                int colCount =  sheet.getColumns();
                for (int row = 1; row < rowCount; row++) {
                    StudentModel  model = new StudentModel();
                    for (int col = 0; col < colCount; col++) {
                        Cell cell = sheet.getCell(col, row);//第一个 列的，第二个 表示行
                        String value = cell.getContents();
                         if (value.length() != 0){
                             System.out.print("warron OK: " +value.toString());
                             switch (col) {
                                 case 0:  model.examName          = value; break;//考试名称
                                 case 1:  model.schoolName        = value; break;//学校名称
                                 case 2:  model.schoolID          = value; break;//学校代码
                                 case 3:  model.gradeName         = value; break;//年级
                                 case 4:  model.className         = value; break;//班级
                                 case 5:  model.studentName       = value; break;//姓名
                                 case 6:  model.enrollmentNumber  = Integer.parseInt(value); break;//学籍号
                                 case 7:  model.examinationNumber = Integer.parseInt(value); break;//考号
                                 case 8:  model.scoreChiness      = Float.parseFloat(value); break;//语文
                                 case 9:  model.scoreMath         = Float.parseFloat(value); break;//数学
                                 case 10: model.scoreEnglish      = Float.parseFloat(value); break;//英语
                                 case 11: model.scoreMorality     = Float.parseFloat(value); break;//品德
                                 case 12: model.scoreHistory      = Float.parseFloat(value); break;//历史
                                 case 13: model.scorePhysics      = Float.parseFloat(value); break;//物理
                                 case 14: model.scoreChemistry    = Float.parseFloat(value); break;//化学
                                 case 15: model.scoreBiology      = Float.parseFloat(value); break;//生物
                                 case 16: model.scoreGeography    = Float.parseFloat(value); break;//地理
                                 case 17: model.scoreTotal        = Float.parseFloat(value); break;//总分
                                 default: model.other             = value; break;//其他
                             }
                         }

                    }
                    if (model.studentName != null){
                        arrModel.add(model);
                    }
                }
                workbook.close();
                Comparator<StudentModel> comparator = new Comparator<StudentModel>() {
                    public int compare(StudentModel o1, StudentModel o2) {
                        float result =o2.scoreTotal -  o1.scoreTotal ; //  按降序
                        if (result == 0) { //成绩相等
                            return (int)(o1.enrollmentNumber - o2.enrollmentNumber); // 按学籍号
                        } else {
                            return (int)result;
                        }
                    }
                };
                Collections.sort(arrModel, comparator);
                int index = 0;
                for (StudentModel model : arrModel) {
                    model.gradeRank = index;
                    index++;
                    if (model.studentName != null)
                        Log.d("warrongrade",""+model.studentName.toString());
                }
            }
        }.start();

    }
}
