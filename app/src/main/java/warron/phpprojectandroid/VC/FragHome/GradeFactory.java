package warron.phpprojectandroid.VC.FragHome;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator;
import warron.phpprojectandroid.Base.CacheTool;
import warron.phpprojectandroid.R;
import warron.phpprojectandroid.VC.FragHome.model.ClassModel;
import warron.phpprojectandroid.VC.FragHome.model.UserInfoModel;
import warron.phpprojectandroid.VC.FragHome.model.StudentModel;

import me.zhouzhuo.zzexcelcreator.ZzExcelCreator;
import me.zhouzhuo.zzexcelcreator.ZzFormatCreator;
public class GradeFactory {
    /**
     * Excel保存路径
     */

    String ExcelPath ;
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
    Context context;//操作的上下文
    final  ZzExcelCreator creator =  ZzExcelCreator.getInstance();

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
    public   static synchronized GradeFactory getInstance(Context context){

        if (instance == null){
           instance = new GradeFactory();
            instance.context = context;
            instance.ExcelPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +context.getResources().getString(R.string.app_name)+File.separator+"成绩分析表";
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
        }
        UserInfoModel conModel = GradeFactory.getCofigModel();//
        if (conModel.hasConfiguration == 1) {//如果有值
            instance.sumScoreBiology  = conModel.sumScoreBiology;
            instance.sumScoreChimistry= conModel.sumScoreChimistry;
            instance.sumScorePhysical = conModel.sumScorePhysical;
            instance.sumScoreGrography= conModel.sumScoreGrography;
            instance.sumScoreHistory  = conModel.sumScoreHistory;
            instance.sumScorePlitical = conModel.sumScorePlitical;
            instance.sumScoreEng      = conModel.sumScoreEng;
            instance.sumScoreMath     = conModel.sumScoreMath;
            instance.sumScoreChn      =  conModel.sumScoreChn;
            instance.allProjectSumScore =  conModel.allProjectSumScore;
            instance.goodRate =  conModel.goodRate;
            instance.passRate =  conModel.passRate;
            instance.difficultyRate =  conModel.difficultyRate;
        }
        return instance;
    }
    public  void  initArrModel(String fileUrl){
        this.arrModel =  new ArrayList<StudentModel>();
        new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                Workbook workbook = null;
                FileInputStream inputStream;
                try {//需要修改
                    File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator + context.getResources().getString(R.string.app_name)+File.separator+"analysis.xls");//
                    workbook = Workbook.getWorkbook(file);
                    workbook.getNumberOfSheets();
                    Sheet sheet = workbook.getSheet(0);
                    int rowCount = sheet.getRows();
                    int colCount =  sheet.getColumns();
                    for (int row = 1; row < rowCount; row++) {
                        StudentModel  model = new StudentModel();
                        for (int col = 0; col < colCount; col++) {
                            Cell cell = sheet.getCell(col, row);//第一个 列的，第二个 表示行
                            String value = cell.getContents();
                            if (value.length() != 0){
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
                    }
                    return  1;
                } catch (Exception e) {
                    e.printStackTrace();
                    return  0;
                }
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid == 1) {
                    instance.caculateArrClassModel();
                    instance.calculateClassRank();
                    instance.createExcel();
                }
            }
        }.execute();

    }

    private void createExcel() {

        StudentModel model =  arrModel.get(0);
        new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                try {
                    StudentModel model =  arrModel.get(0);
                     creator.createExcel(ExcelPath, params[0])
                            .createSheet(params[1])
                            .close();
                    return 1;
                } catch (IOException | WriteException e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid == 1) {

                }
            }

        }.execute(model.examName+"成绩分析表","校学生成绩排名");
    }
    /**
     * 新增Sheet
     */

    private void addSheet(String excelName, String sheetName) {
        if (excelName == null){
            Toast.makeText(context, "表名不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sheetName  == null) {
            Toast.makeText(context, "Sheet名不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                try {
                     creator.openExcel(new File(params[0]))  //如果不想覆盖文件，注意是openExcel
                            .createSheet(params[1])
                            .close();
                    return 1;
                } catch (IOException | WriteException e) {
                    e.printStackTrace();
                    return 0;
                } catch (BiffException e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid != 1) {
                    Toast.makeText(context, "表格创建失败！", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(excelName, sheetName);
    }


    public  void generateAllStuRank() throws WriteException {//创建校所有学生成绩排名表
        ArrayList<String>  xlsDataMuArr = new ArrayList<String>(); // 创建存放XLS文件数据的数组
        // 第一行内容
        xlsDataMuArr.add("考试名称");
        xlsDataMuArr.add("学校名称");
        xlsDataMuArr.add("学校代码");
        xlsDataMuArr.add("年级");
        xlsDataMuArr.add("校排");
        xlsDataMuArr.add("班排");
        xlsDataMuArr.add("班级");
        xlsDataMuArr.add("姓名");
        xlsDataMuArr.add("学籍号");
        xlsDataMuArr.add("考号");
        xlsDataMuArr.add("语文");
        xlsDataMuArr.add("数学");
        xlsDataMuArr.add("英语");
        xlsDataMuArr.add("品德/政治");
        xlsDataMuArr.add("历史");
        xlsDataMuArr.add("物理");
        xlsDataMuArr.add("化学");
        xlsDataMuArr.add("生物");
        xlsDataMuArr.add("地理");
        xlsDataMuArr.add("总分");
        for (StudentModel model :arrModel) {
            xlsDataMuArr.add(model.examName);//考试名称
            xlsDataMuArr.add(model.schoolName);//学校名称
            xlsDataMuArr.add(model.schoolID  );//学校代码
            xlsDataMuArr.add(model.gradeName);//年级
            xlsDataMuArr.add(model.gradeRank + "");////校排
            xlsDataMuArr.add(model.classRank + "");//班排
            xlsDataMuArr.add(model.className);//班级
            xlsDataMuArr.add(model.studentName);//姓名
            xlsDataMuArr.add(model.enrollmentNumber + "");//学籍号
            xlsDataMuArr.add(model.examinationNumber+"");////考号
            xlsDataMuArr.add(model.scoreChiness +"" );//语文
            xlsDataMuArr.add(model.scoreMath     +"");//数学
            xlsDataMuArr.add(model.scoreEnglish  +"");//英语
            xlsDataMuArr.add(model.scoreMorality +"");//品德/政治
            xlsDataMuArr.add(model.scoreHistory  +"");//历史
            xlsDataMuArr.add(model.scorePhysics  +"");//物理
            xlsDataMuArr.add(model.scoreChemistry+"");//化学
            xlsDataMuArr.add(model.scoreBiology  +"");//生物
            xlsDataMuArr.add(model.scoreGeography+"");//地理
            xlsDataMuArr.add(model.scoreTotal    +"");//总分
        }
        final WritableCellFormat format = ZzFormatCreator
                .getInstance()
                .createCellFont(WritableFont.ARIAL)
                .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                .setFontSize(14)
                .setFontColor(Colour.DARK_GREEN)
                .getCellFormat();
        StudentModel model =  arrModel.get(0);
        new  AsyncTask<Object, Void, Integer>(){
           @Override
           protected Integer doInBackground(Object... objects) {
               ArrayList<String> xlsDataMuArrT = (ArrayList<String>) objects[0];
               String examName = (String) objects[1];
               try {
                    creator.openExcel(new File(examName))
                           .openSheet(0);   //打开第0个sheet
                   int cols = 20;//列数，根据需求修改
                   int rows = xlsDataMuArrT.size()/cols;
                   for (int i = 0; i < rows ; i ++) {
                       for (int j = 0 ;j < cols; j++) {
                           String value = xlsDataMuArrT.get(i * cols + j);
                           creator.setColumnWidth(j, 25)
                                   .setRowHeight(i, 400)
                                   .fillContent(j, i, value, format);
                       }
                   }
                   creator.close();
                   return 1;
               } catch (IOException e) {
                   e.printStackTrace(); return 0;
               } catch (WriteException e) {
                   e.printStackTrace();return 0;
               } catch (BiffException e) {
                   e.printStackTrace();return 0;
               }
           }

            @Override
           protected void onPostExecute(Integer aVoid) {
               super.onPostExecute(aVoid);
               if (aVoid != 1) {
                   Toast.makeText(context, "进程失败", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(context, "进程成功", Toast.LENGTH_SHORT).show();
               }
           }
       }.execute(xlsDataMuArr,ExcelPath + "/" + model.examName + "成绩分析表.xls");
    }

    public  void genearteClassRankTable() throws WriteException {

        StudentModel modelS = arrModel.get(0);
        this.addSheet( ExcelPath +"/"+ modelS.examName + "成绩分析表.xls","班级之间排名");
        // 创建存放XLS文件数据的数组
        ArrayList<String>  xlsDataMuArr = new ArrayList<String>();

        // 第一行内容
        xlsDataMuArr.add("考试名称");
        xlsDataMuArr.add("学校名称");
        xlsDataMuArr.add("学校代码");
        xlsDataMuArr.add("年级");
        xlsDataMuArr.add("班级");
        xlsDataMuArr.add("班级校排");
        xlsDataMuArr.add("总人数");
        xlsDataMuArr.add("班级平均分");
        xlsDataMuArr.add("班级总分");
        xlsDataMuArr.add("前10");
        xlsDataMuArr.add("前50");
        xlsDataMuArr.add("前100");
        xlsDataMuArr.add("前200");
        xlsDataMuArr.add("前500");
        xlsDataMuArr.add("前1000");
        xlsDataMuArr.add("前2000");
        xlsDataMuArr.add("优生人数(总成绩"+allProjectSumScore+"的"+Math.round(goodRate*100)+"%)");
        xlsDataMuArr.add("优生率");
        xlsDataMuArr.add("及格人数(总成绩"+allProjectSumScore+"的"+Math.round(passRate*100)+"%)");
        xlsDataMuArr.add("及格率");
        xlsDataMuArr.add("学困人数(总成绩"+allProjectSumScore+"的"+Math.round(difficultyRate*100)+"%)");
        xlsDataMuArr.add("学困率");
        for (ClassModel  model : arrClassModel) {
            xlsDataMuArr.add(model.examName);//考试名称
            xlsDataMuArr.add(model.schoolName);//学校名称
            xlsDataMuArr.add(model.schoolID);//学校代码
            xlsDataMuArr.add(model.gradeName );//年级
            xlsDataMuArr.add(model.className );//班级
            xlsDataMuArr.add(model.gradeRank+"");//班级校排
            xlsDataMuArr.add(model.totalStuNum+"");//总人数
            xlsDataMuArr.add(model.aveageScore+"");//班级平均分
            xlsDataMuArr.add(model.scoreTotal+"");//班级总分
            xlsDataMuArr.add(model.beforTen+"");//前10
            xlsDataMuArr.add(model.beforFifty+"");//前50
            xlsDataMuArr.add(model.beforOneHundred+"");//前100
            xlsDataMuArr.add(model.beforTwoHundred+"");//前200
            xlsDataMuArr.add(model.beforFiveHundred+"");//前500
            xlsDataMuArr.add(model.beforOneThousand+"");//前1000
            xlsDataMuArr.add(model.beforTwoThousand+"");//前2000
            xlsDataMuArr.add(model.goodStuNum+"");//优生人数
            xlsDataMuArr.add(model.goodStuNumRate+"");//优生率
            xlsDataMuArr.add(model.passStuNum+"");//及格人数
            xlsDataMuArr.add(model.passStuNumRate+"");//及格率
            xlsDataMuArr.add(model.difficultyStuNum+"");//学困人数
            xlsDataMuArr.add(model.difficultyStuNumRate+"");//学困率
        }
        final WritableCellFormat format = ZzFormatCreator
                .getInstance()
                .createCellFont(WritableFont.ARIAL)
                .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                .setFontSize(14)
                .setFontColor(Colour.DARK_GREEN)
                .getCellFormat();
        StudentModel model =  arrModel.get(0);
        new AsyncTask<Object,Void,Integer>(){

            @Override
            protected Integer doInBackground(Object... objects) {
                ArrayList<String> xlsDataMuArrT = (ArrayList<String>) objects[0];
                int cols = 22;//列数，根据需求修改
                int rows = xlsDataMuArrT.size() / cols;
                String examName = String.valueOf(objects[1]);
                try {
                    creator.openExcel(new File(examName))
                           .openSheet(1); //打开第1个sheet
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            String value = xlsDataMuArrT.get(i * cols + j);
                             creator.setColumnWidth(j, 25)
                                    .setRowHeight(i, 400)
                                    .fillContent(j, i, value, format);
                        }
                    }
                    creator.close();
                    return  1;
                } catch (IOException e) {
                    e.printStackTrace(); return  0;
                } catch (BiffException e) {
                    e.printStackTrace(); return  0;
                } catch (RowsExceededException e) {
                    e.printStackTrace(); return  0;
                } catch (WriteException e) {
                    e.printStackTrace(); return  0;
                }
            }
            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid != 1) {
                    Toast.makeText(context, "进程失败", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "进程成功", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(xlsDataMuArr,ExcelPath +"/"+ model.examName + "成绩分析表.xls");
    }

    public  void caculateArrClassModel(){//初始化班级信息
        arrClassModel = new ArrayList<ClassModel>();
        HashMap<String,ClassModel> dict = new HashMap<String,ClassModel>();
        for (StudentModel  stuModel : arrModel){
            ClassModel  classModel;
            if (!dict.keySet().contains(stuModel.className)) {
                classModel = new ClassModel();
                classModel.examName = stuModel.examName;
                classModel.schoolName = stuModel.schoolName;
                classModel.schoolID = stuModel.schoolID;
                classModel.className = stuModel.className;//班级名称
                classModel.gradeName = stuModel.gradeName;//年级名称
                classModel.arrStuModel = new ArrayList<StudentModel>();
                dict.put(stuModel.className,classModel);
            }else{
                classModel = dict.get(stuModel.className);
            }
            classModel.arrStuModel.add(stuModel);//将学生添加到对应班级中
            stuModel.classRank = classModel.arrStuModel.size();//计算该同学在班级内的排名
            classModel.scoreTotal  +=  stuModel.scoreTotal;//计算班级总分
            classModel.totalStuNum += 1;//计算班级总人数
        }
        if (dict.values().size() > 0){//学生班级内排序
            arrClassModel.addAll(dict.values());
            Comparator<ClassModel> comparator = new Comparator<ClassModel>() {
                public int compare(ClassModel o1, ClassModel o2) {
                    float result =o2.scoreTotal -  o1.scoreTotal ; //  按降序
                    if (result == 0) { //成绩相等
                        return (int)(Integer.parseInt(o1.className) - Integer.parseInt(o2.className)); // 按学籍号
                    } else {
                        return (int)result;
                    }
                }
            };
            Collections.sort(arrClassModel, comparator);
            int index = 1;
            for (ClassModel  model : arrClassModel) {
                model.gradeRank = index + "";
                index++;
            }
        }
    }
    public void calculateClassRank(){
        for (ClassModel   classModel : arrClassModel) {
            for (StudentModel stuModel : classModel.arrStuModel) {
                int rank = stuModel.gradeRank;

                if(rank <= 10){//计算前 多少个
                    classModel.beforTen += 1 ;
                }
                if(rank <= 50){
                    classModel.beforFifty += 1 ;
                }
                if(rank <= 100){
                    classModel.beforOneHundred += 1 ;
                }
                if (rank <= 200){
                    classModel.beforTwoHundred+= 1 ;
                }
                if(rank <= 500){
                    classModel.beforFiveHundred  += 1 ;
                }
                if(rank <= 1000){
                    classModel.beforOneThousand += 1 ;
                }
                if(rank <= 2000){
                    classModel.beforTwoThousand += 1;
                }
                //计算优生人数
                if (stuModel.scoreTotal >= allProjectSumScore * goodRate) {
                    classModel.goodStuNum += 1;
                }
                //计算及格人数
                if (stuModel.scoreTotal >= allProjectSumScore * passRate) {
                    classModel.passStuNum += 1;
                }
                //计算学困人数
                if (stuModel.scoreTotal <= allProjectSumScore * difficultyRate) {
                    classModel.difficultyStuNum += 1;
                }
            }
        }

        if (arrClassModel.size() > 0){
            int index = 1;
            for (ClassModel model : arrClassModel) {
                model.goodStuNumRate = (float) model.goodStuNum/model.arrStuModel.size();
                model.passStuNumRate = (float)model.passStuNum /model.arrStuModel.size();
                model.difficultyStuNumRate = (float)model.difficultyStuNum/model.arrStuModel.size();
                model.aveageScore = (float)model.scoreTotal/model.arrStuModel.size();
                index++;
            }
        }
    }

    public  void genearteAllStuInClassRankTables() throws WriteException, InterruptedException {//一班级为一张表 表中是该班中学生的排名

        // 创建存放XLS文件数据的数组
        int cols = 20;//列数，根据需求修改
        int sheetIndex = 2;
        StudentModel modelS = arrModel.get(0);
        try {
            creator.openExcel(new File(ExcelPath +"/"+ modelS.examName + "成绩分析表.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        for (ClassModel classModel : arrClassModel) {
            ArrayList  xlsDataMuArr = new ArrayList();

            // 第一行内容
            xlsDataMuArr.add("考试名称");
            xlsDataMuArr.add("学校名称");
            xlsDataMuArr.add("学校代码");
            xlsDataMuArr.add("年级");
            xlsDataMuArr.add("姓名");
            xlsDataMuArr.add("校排");
            xlsDataMuArr.add("班排");
            xlsDataMuArr.add("班级");
            xlsDataMuArr.add("学籍号");
            xlsDataMuArr.add("考号");
            xlsDataMuArr.add("语文");
            xlsDataMuArr.add("数学");
            xlsDataMuArr.add("英语");
            xlsDataMuArr.add("品德/政治");
            xlsDataMuArr.add("历史");
            xlsDataMuArr.add("物理");
            xlsDataMuArr.add("化学");
            xlsDataMuArr.add("生物");
            xlsDataMuArr.add("地理");
            xlsDataMuArr.add("总分");
            for (StudentModel  model : classModel.arrStuModel) {
                xlsDataMuArr.add(model.examName );//考试名称
                xlsDataMuArr.add(model.schoolName );//学校名称
                xlsDataMuArr.add(model.schoolID   );//学校代码
                xlsDataMuArr.add(model.gradeName  );//年级
                xlsDataMuArr.add(model.studentName);//姓名
                xlsDataMuArr.add(model.gradeRank  );////校排
                xlsDataMuArr.add(model.classRank  );//班排
                xlsDataMuArr.add(model.className  );//班级
                xlsDataMuArr.add(model.enrollmentNumber );//学籍号
                xlsDataMuArr.add(model.examinationNumber);////考号
                xlsDataMuArr.add(model.scoreChiness );//语文
                xlsDataMuArr.add(model.scoreMath    );//数学
                xlsDataMuArr.add(model.scoreEnglish );//英语
                xlsDataMuArr.add(model.scoreMorality);//品德/政治
                xlsDataMuArr.add(model.scoreHistory );//历史
                xlsDataMuArr.add(model.scorePhysics );//物理
                xlsDataMuArr.add(model.scoreChemistry);//化学
                xlsDataMuArr.add(model.scoreBiology);//生物
                xlsDataMuArr.add(model.scoreGeography);//地理
                xlsDataMuArr.add(model.scoreTotal);//总分
            }

            String  fileName = modelS.className +"班_学生排序";
            int rows = xlsDataMuArr.size()/cols;


            final WritableCellFormat format = ZzFormatCreator
                    .getInstance()
                    .createCellFont(WritableFont.ARIAL)
                    .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                    .setFontSize(14)
                    .setFontColor(Colour.DARK_GREEN)
                    .getCellFormat();
            Thread.sleep(1000);
            addSheet(ExcelPath +"/"+ modelS.examName + "成绩分析表.xls",fileName);
            Thread.sleep(1000);
            new AsyncTask<Object,Void,Integer>(){

                @Override
                protected Integer doInBackground(Object... objects) {
                    ArrayList<String> xlsDataMuArrT = (ArrayList<String>) objects[0];
                    int cols = 22;//列数，根据需求修改
                    int rows = xlsDataMuArrT.size() / cols;
                    int sheetIndex = Integer.parseInt((String) objects[1]);
                    String examName = String.valueOf(objects[1]);
                    creator.openSheet(sheetIndex); //打开第1个sheet
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            String value = xlsDataMuArrT.get(i * cols + j);
                            try {
                                creator.setColumnWidth(j, 25)
                                        .setRowHeight(i, 400)
                                        .fillContent(j, i, value, format);
                            } catch (WriteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return  1;
                }
                @Override
                protected void onPostExecute(Integer aVoid) {
                    super.onPostExecute(aVoid);
                    if (aVoid != 1) {
                        Toast.makeText(context, "进程失败", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "进程成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute(xlsDataMuArr,ExcelPath +"/"+ modelS.examName + "成绩分析表.xls",sheetIndex + "");
            sheetIndex ++;
        }
        try {
            creator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
