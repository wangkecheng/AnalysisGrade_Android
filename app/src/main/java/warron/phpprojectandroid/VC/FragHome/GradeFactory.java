package warron.phpprojectandroid.VC.FragHome;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
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

import me.zhouzhuo.zzexcelcreator.ZzFormatCreator;
import warron.phpprojectandroid.VC.MainActivity;


public class GradeFactory {
    /**
     * Excel保存路径
     */

    String ExcelPath ;
    ArrayList<StudentModel> arrModel;
    ArrayList<ClassModel> arrCollectModel;//年级排名
    ArrayList<ClassModel> arrClassModel;//装的是班级模型
    public int allProjectSumScore;
    public int sumScoreChn;
    public int sumScoreMath;
    public int sumScoreEng;

    public int sumScorePlitical;
    public int sumScoreHistory;
    public int sumScoreGrography;

    public int sumScorePhysical;
    public int sumScoreBiology;
    public int sumScoreChimistry;
    public String fileUrl;
    public int totalScoreLbl;
    public float goodRate;//优生率
    public float passRate;//及格率
    public float difficultyRate;//学困率
    Context context;//操作的上下文
    public GradeFactoryInterface gradeFactoryInterface;
    final  ZzExcelCreator creator =  ZzExcelCreator.getInstance();
    String keyName;

    public static UserInfoModel getCofigModel() {
        DbManager manager  =  CacheTool.getDBManager();
        try {
            UserInfoModel model = manager.selector(UserInfoModel.class).where("keyId", "=", "123").findFirst();;
            if (model != null) {
                return model;
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
            instance.ExcelPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"成绩分析"+File.separator+"成绩分析表";
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
            instance.goodRate = (float)0.8;
            instance.passRate = (float)0.6;
            instance.difficultyRate = (float)0.3;
            UserInfoModel conModel = GradeFactory.getCofigModel();//
            if (conModel.hasConfiguration != null && Integer.parseInt(conModel.hasConfiguration) == 1) {//如果有值
                instance.sumScoreBiology  = Integer.parseInt(conModel.sumScoreBiology);
                instance.sumScoreChimistry= Integer.parseInt(conModel.sumScoreChimistry);
                instance.sumScorePhysical = Integer.parseInt(conModel.sumScorePhysical);
                instance.sumScoreGrography= Integer.parseInt(conModel.sumScoreGrography);
                instance.sumScoreHistory  = Integer.parseInt(conModel.sumScoreHistory);
                instance.sumScorePlitical = Integer.parseInt(conModel.sumScorePlitical);
                instance.sumScoreEng      = Integer.parseInt(conModel.sumScoreEng);
                instance.sumScoreMath     = Integer.parseInt(conModel.sumScoreMath);
                instance.sumScoreChn      = Integer.parseInt(conModel.sumScoreChn);
                instance.allProjectSumScore =  Integer.parseInt(conModel.allProjectSumScore);
                instance.goodRate =  Float.parseFloat(conModel.goodRate);
                instance.passRate =  Float.parseFloat(conModel.passRate);
                instance.difficultyRate =  Float.parseFloat(conModel.difficultyRate);
            }
        }
        return instance;
    }
    public  void  initArrModel(final String fileUrl){
        if (fileUrl==null ||fileUrl.length() == 0){
            if (gradeFactoryInterface != null){
                gradeFactoryInterface.alertInfo("请先选择文件",false);
            }
            return;
        }
        this.arrModel =  new ArrayList<StudentModel>();
        new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                Workbook workbook = null;
                FileInputStream inputStream;
                try {//需要修改params[0]
                    File file=new File(params[0]);//Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator + "成绩分析"+File.separator+"analysis.xls"
                    workbook = Workbook.getWorkbook(file);
                    workbook.getNumberOfSheets();
                    Sheet sheet = workbook.getSheet(0);
                    int rowCount = sheet.getRows();
                    int colCount =  sheet.getColumns();
                    int finishCount = 0 ;
                    int totalCount = (rowCount - 1) * colCount;

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
                            if (gradeFactoryInterface != null){
                                finishCount += 1;
                                boolean isFinish = false;
                                int progress =  (int)((finishCount/(totalCount *1.0)) * 100);
                                if (finishCount >= totalCount){
                                    isFinish = true;
                                    progress = 100;
                                }
                                gradeFactoryInterface.onProgress(progress,isFinish);
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
                    int index = 1;
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
                   instance.calculate();
                }
            }
        }.execute(fileUrl);

    }

    public void recalculate(){
        if (fileUrl==null ||fileUrl.length() == 0){
            if (gradeFactoryInterface != null){
                gradeFactoryInterface.alertInfo("设置完成,选择文件后可导出",true);
            }
            return;
        }
        calculate();
    }
    private  void  calculate(){
        if (arrModel.size() == 0){
            initArrModel(fileUrl);
        }
        instance.caculateArrClassModel();
        instance.calculateClassRank();
        instance.createExcel();
        instance.calculateCollectTable();
        if (gradeFactoryInterface!=null){
            gradeFactoryInterface.alertInfo("所有信息已经初始化完毕",true);
        }
    }
    private void createExcel() {//把表创建好 把sheet创建好

        StudentModel model =  arrModel.get(0);
        new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                try {
                    StudentModel model =  arrModel.get(0);
                    creator.createExcel(ExcelPath, params[0]);
                    for (int i = arrClassModel.size() - 1;i>=0;i--){
                        ClassModel classModel = arrClassModel.get(i);
                        String  fileName = classModel.className +"班学生排序表";
                        creator.createSheet(fileName);
                    }
                    creator.close();
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
                    StudentModel model =  arrModel.get(0);
                    String excelPath = ExcelPath +"/"+ model.examName + "成绩分析表.xls";
                    instance.fileUrl = excelPath;
                    instance.addSheet(excelPath,"校学生成绩排名表");
                    instance.addSheet(excelPath,"班级之间排名表");
                    instance.addSheet(excelPath,"汇总分析表");
                }
            }

        }.execute(model.examName+"成绩分析表");
    }
    /**
     * 新增Sheet
     */

    private void addSheet(String excelName, String sheetName) {
        if (gradeFactoryInterface != null){
            if (excelName == null){
                gradeFactoryInterface.alertInfo("表名不能为空！",false);
                return;
            }
            if (sheetName  == null) {
                gradeFactoryInterface.alertInfo("Sheet名不能为空！",false);
                return;
            }
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
                if (gradeFactoryInterface!=null){
                    if (aVoid != 1) {
                        gradeFactoryInterface.alertInfo("表格创建失败！",true);
                    }
                }

            }
        }.execute(excelName, sheetName);
    }

    public  void generateAllStuRank() {//创建校所有学生成绩排名表
        if (fileUrl==null ||fileUrl.length() == 0){
            if (gradeFactoryInterface != null){
                gradeFactoryInterface.alertInfo("请先选择文件",false);
            }
            return;
        }
        if (arrModel.size() == 0){
            initArrModel(fileUrl);
        }
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
        StudentModel model =  arrModel.get(0);
        new  AsyncTask<Object, Void, Integer>(){
            @Override
            protected Integer doInBackground(Object... objects) {
                ArrayList<String> xlsDataMuArrT = (ArrayList<String>) objects[0];
                String examName = (String) objects[1];
                try {
                    creator.openExcel(new File(examName))
                            .openSheet(2);   //打开第0个sheet
                    int cols = 20;//列数，根据需求修改
                    int rows = xlsDataMuArrT.size()/cols;
                    final WritableCellFormat format = ZzFormatCreator
                            .getInstance()
                            .createCellFont(WritableFont.ARIAL)
                            .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                            .setFontSize(14)
                            .setFontColor(Colour.DARK_GREEN)
                            .getCellFormat();
                    int finishCount = 0;
                    int totalCount =  xlsDataMuArrT.size();
                    for (int i = 0; i < rows ; i ++) {
                        for (int j = 0 ;j < cols; j++) {
                            String value = xlsDataMuArrT.get(i * cols + j);
                            creator.setColumnWidth(j, 25)
                                    .setRowHeight(i, 400)
                                    .fillContent(j, i, value, format);
                            if (gradeFactoryInterface != null){
                                finishCount += 1;
                                boolean isFinish = false;
                                int progress =  (int)((finishCount/(totalCount *1.0)) * 100);
                                if (finishCount >= totalCount){
                                      isFinish = true;
                                     progress = 100;
                                }
                                gradeFactoryInterface.onProgress(progress,isFinish);
                            }
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
                if (gradeFactoryInterface!=null){
                    if (aVoid == 1) {
                        gradeFactoryInterface.alertInfo("导出校学生排名成功",true);
                    }else{
                        gradeFactoryInterface.alertInfo("导出校学生排名失败",false);
                    }
                }
            }
        }.execute(xlsDataMuArr,fileUrl);
    }

    public  void genearteClassRankTable()  {//班级之间排名表
        if (fileUrl==null ||fileUrl.length() == 0){
            if (gradeFactoryInterface != null){
                gradeFactoryInterface.alertInfo("请先选择文件",false);
            }
            return;
        }
        if (arrModel.size() == 0){
            initArrModel(fileUrl);
        }
        StudentModel modelS = arrModel.get(0);
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
            xlsDataMuArr.add(new DecimalFormat("0.00").format(model.aveageScore)+"");//班级平均分
            xlsDataMuArr.add(model.scoreTotal+"");//班级总分
            xlsDataMuArr.add(model.beforTen+"");//前10
            xlsDataMuArr.add(model.beforFifty+"");//前50
            xlsDataMuArr.add(model.beforOneHundred+"");//前100
            xlsDataMuArr.add(model.beforTwoHundred+"");//前200
            xlsDataMuArr.add(model.beforFiveHundred+"");//前500
            xlsDataMuArr.add(model.beforOneThousand+"");//前1000
            xlsDataMuArr.add(model.beforTwoThousand+"");//前2000
            xlsDataMuArr.add(model.goodStuNum+"");//优生人数
            xlsDataMuArr.add(new DecimalFormat("0.00").format(model.goodStuNumRate)+"");//优生率
            xlsDataMuArr.add(model.passStuNum+"");//及格人数
            xlsDataMuArr.add(new DecimalFormat("0.00").format(model.passStuNumRate)+"");//及格率
            xlsDataMuArr.add(model.difficultyStuNum+"");//学困人数
            xlsDataMuArr.add(new DecimalFormat("0.00").format(model.difficultyStuNumRate)+"");//学困率
        }

        new AsyncTask<Object,Void,Integer>(){

            @Override
            protected Integer doInBackground(Object... objects) {
                ArrayList<String> xlsDataMuArrT = (ArrayList<String>) objects[0];
                int cols = 22;//列数，根据需求修改
                int rows = xlsDataMuArrT.size() / cols;
                String examName = String.valueOf(objects[1]);
                try {
                    creator.openExcel(new File(examName))
                            .openSheet(1); //班级之间排名
                    final WritableCellFormat format = ZzFormatCreator
                            .getInstance()
                            .createCellFont(WritableFont.ARIAL)
                            .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                            .setFontSize(14)
                            .setFontColor(Colour.DARK_GREEN)
                            .getCellFormat();
                    StudentModel model =  arrModel.get(0);
                    int finishCount = 0;
                    int totalCount =  xlsDataMuArrT.size();
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            String value = xlsDataMuArrT.get(i * cols + j);
                            creator.setColumnWidth(j, 25)
                                    .setRowHeight(i, 400)
                                    .fillContent(j, i, value, format);
                            if (gradeFactoryInterface != null){
                                finishCount += 1;
                                boolean isFinish = false;
                                int progress =  (int)((finishCount/(xlsDataMuArrT.size() *1.0)) * 100);
                                if (finishCount >= totalCount){
                                    isFinish = true;
                                    progress = 100;
                                }
                                gradeFactoryInterface.onProgress(progress,isFinish);
                            }
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
                if (gradeFactoryInterface!=null){
                    if (aVoid == 1) {
                        gradeFactoryInterface.alertInfo("导出班级间排名表成功",true);
                    }else{
                        gradeFactoryInterface.alertInfo("导出班级间排名表失败",false);
                    }
                }
            }
        }.execute(xlsDataMuArr,fileUrl);
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

    public  void genearteAllStuInClassRankTables() {//一班级为一张表 表中是该班中学生的排名
        if (fileUrl==null ||fileUrl.length() == 0){
            if (gradeFactoryInterface != null){
                gradeFactoryInterface.alertInfo("请先选择文件",false);
            }
            return;
        }
        if (arrModel.size() == 0){
            initArrModel(fileUrl);
        }
         AsyncTask task =   new AsyncTask<Object,Void,Integer>(){
                @Override
                protected Integer doInBackground(Object... objects) {
                    ArrayList<ClassModel> arrClassModel = (ArrayList<ClassModel>) objects[0];
                    int finishCount = 0;
                    int totalCount = 0;
                    int cols = 20;//列数，根据需求修改
                    for(ClassModel classModel :arrClassModel) {
                        totalCount += (classModel.arrStuModel.size() + 1) *cols;
                    }
                    for(ClassModel classModel :arrClassModel) {
                        ArrayList<String> xlsDataMuArr = new ArrayList<String>();// 创建存放XLS文件数据的数组
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
                        for (StudentModel model : classModel.arrStuModel) {
                            xlsDataMuArr.add(model.examName);//考试名称
                            xlsDataMuArr.add(model.schoolName);//学校名称
                            xlsDataMuArr.add(model.schoolID);//学校代码
                            xlsDataMuArr.add(model.gradeName);//年级
                            xlsDataMuArr.add(model.studentName);//姓名
                            xlsDataMuArr.add(model.gradeRank + "");////校排
                            xlsDataMuArr.add(model.classRank + "");//班排
                            xlsDataMuArr.add(model.className);//班级
                            xlsDataMuArr.add(model.enrollmentNumber + "");//学籍号
                            xlsDataMuArr.add(model.examinationNumber + "");////考号
                            xlsDataMuArr.add(model.scoreChiness + "");//语文
                            xlsDataMuArr.add(model.scoreMath + "");//数学
                            xlsDataMuArr.add(model.scoreEnglish + "");//英语
                            xlsDataMuArr.add(model.scoreMorality + "");//品德/政治
                            xlsDataMuArr.add(model.scoreHistory + "");//历史
                            xlsDataMuArr.add(model.scorePhysics + "");//物理
                            xlsDataMuArr.add(model.scoreChemistry + "");//化学
                            xlsDataMuArr.add(model.scoreBiology + "");//生物
                            xlsDataMuArr.add(model.scoreGeography + "");//地理
                            xlsDataMuArr.add(model.scoreTotal + "");//总分
                        }
                        int rows = xlsDataMuArr.size() / cols;
                        try {
                            int sheetIndex = Integer.parseInt(classModel.gradeRank) + 3 - 1;
                            creator.openExcel(new File((String) objects[1]))
                                    .openSheet(sheetIndex); //
                            WritableCellFormat format = ZzFormatCreator
                                    .getInstance()
                                    .createCellFont(WritableFont.ARIAL)
                                    .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                                    .setFontSize(14)
                                    .setFontColor(Colour.DARK_GREEN)
                                    .getCellFormat();

                            for (int i = 0; i < rows; i++) {
                                for (int j = 0; j < cols; j++) {
                                    String value = (String) xlsDataMuArr.get(i * cols + j);
                                    creator.setColumnWidth(j, 25)
                                            .setRowHeight(i, 400)
                                            .fillContent(j, i, value, format);
                                    if (gradeFactoryInterface != null){
                                        finishCount += 1;
                                        boolean isFinish = false;
                                        int progress =  (int)((finishCount/(totalCount *1.0)) * 100);
                                        if (finishCount >= totalCount){
                                            isFinish = true;
                                            progress = 100;
                                        }
                                        gradeFactoryInterface.onProgress(progress,isFinish);
                                    }
                                }
                            }
                            creator.close();
                        } catch (WriteException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (BiffException e) {
                            e.printStackTrace();
                        }
                    }
                    return  1;
                }
                @Override
                protected void onPostExecute(Integer aVoid) {
                    super.onPostExecute(aVoid);
                    if (gradeFactoryInterface!=null){
                        if (aVoid == 1) {
                            gradeFactoryInterface.alertInfo("导出各班级学生排名表成功",true);
                        }else{
                            gradeFactoryInterface.alertInfo("导出各班级学生排名表失败",false);
                        }
                    }
                }
            }.execute(arrClassModel,fileUrl);

    }

    public void calculateCollectTable() { //汇总统计

        arrCollectModel = new ArrayList<ClassModel>();
        arrCollectModel.addAll(this.arrClassModel);

        float totalScore =  this.allProjectSumScore;
        float goodRate   =  this.goodRate;
        float paasRate   =  this.passRate;

        for (ClassModel  classModel : arrCollectModel) {
            for (StudentModel  stuModel : classModel.arrStuModel) {
                int stuCount = classModel.arrStuModel.size();
                if (stuModel.scoreChiness >= sumScoreChn * goodRate) {// 语文 计算优生人数
                    classModel.chnGoodStuNum += 1;
                    classModel.chnGoodRate = (float) ((classModel.chnGoodStuNum * 1.0)/stuCount);
                }
                if (stuModel.scoreChiness >= sumScoreChn * paasRate) {// 语文 计算及格人数
                    classModel.chnPassStuNum += 1;
                    classModel.chnPassRate = (float) (classModel.chnPassStuNum* 1.0 /stuCount);
                }

                if (stuModel.scoreMath >= sumScoreMath * goodRate) { // 数学 计算优生人数
                    classModel.mathGoodStuNum += 1;
                    classModel.mathGoodRate = (float) (classModel.mathGoodStuNum* 1.0 /stuCount);
                }
                if (stuModel.scoreMath >= sumScoreMath * paasRate) {// 数学 计算及格人数
                    classModel.mathPassStuNum += 1;
                    classModel.mathPassRate = (float) (classModel.mathPassStuNum* 1.0 /stuCount);
                }

                if (stuModel.scoreEnglish >= sumScoreEng * goodRate) {// 英语 计算优生人数
                    classModel.EngGoodStuNum += 1;
                    classModel.EngGoodRate = (float) (classModel.EngGoodStuNum* 1.0/stuCount);
                }
                if (stuModel.scoreEnglish >= sumScoreEng * paasRate) {// 英语 计算及格人数
                    classModel.EngPassStuNum += 1;
                    classModel.EngPassRate = (float) (classModel.EngPassStuNum* 1.0 /stuCount);
                }

                if (stuModel.scoreMorality >= sumScorePlitical * goodRate) {// 政治 计算优生人数
                    classModel.moralityGoodStuNum += 1;
                    classModel.moralityGoodRate = (float) (classModel.moralityGoodStuNum* 1.0/stuCount);
                }
                if (stuModel.scoreMorality >= sumScorePlitical * paasRate) {// 政治  计算及格人数
                    classModel.moralityPassStuNum += 1;
                    classModel.moralityPassRate = (float) (classModel.moralityPassStuNum* 1.0/stuCount);
                }

                if (stuModel.scoreHistory >= sumScoreHistory * goodRate) {// 历史 计算优生人数
                    classModel.historyGoodStuNum += 1;
                    classModel.historyGoodRate = (float) (classModel.historyGoodStuNum* 1.0/stuCount);
                }
                if (stuModel.scoreHistory >= sumScoreHistory * paasRate) {// 历史 计算及格人数
                    classModel.historyPassStuNum += 1;
                    classModel.historyPassRate = (float) (classModel.historyPassStuNum* 1.0/stuCount);
                }

                if (stuModel.scoreGeography >= sumScoreGrography * goodRate) {// 地理 计算优生人数
                    classModel.geoGoodStuNum += 1;
                    classModel.geoGoodRate = (float) (classModel.geoGoodStuNum* 1.0/stuCount);
                }
                if (stuModel.scoreGeography >= sumScoreGrography * paasRate) {// 地理 计算及格人数
                    classModel.geoPassStuNum += 1;
                    classModel.geoPassRate = (float) (classModel.geoPassStuNum* 1.0/stuCount);
                }

                if (stuModel.scorePhysics >= sumScorePhysical * goodRate) {// 物理 计算优生人数
                    classModel.phyGoodStuNum += 1;
                    classModel.phyGoodRate = (float) (classModel.phyGoodStuNum* 1.0/stuCount);
                }
                if (stuModel.scorePhysics >= sumScorePhysical * paasRate) {// 物理 计算及格人数
                    classModel.phyPassStuNum += 1;
                    classModel.phyPassRate = (float) (classModel.phyPassStuNum* 1.0/stuCount);
                }

                if (stuModel.scoreChemistry >= sumScoreChimistry * goodRate) {// 化学 计算优生人数
                    classModel.chemistryGoodStuNum += 1;
                    classModel.chemistryGoodRate = (float) (classModel.chemistryGoodStuNum* 1.0/stuCount);
                }
                if (stuModel.scoreChemistry >= sumScoreChimistry * paasRate) {// 化学 计算及格人数
                    classModel.chemistryPassStuNum += 1;
                    classModel.chemistryPassRate = (float) (classModel.chemistryPassStuNum* 1.0/stuCount);
                }

                if (stuModel.scoreBiology >= sumScoreBiology * goodRate) {// 生物 计算优生人数
                    classModel.bioGoodStuNum += 1;
                    classModel.bioGoodRate = (float) (classModel.bioGoodStuNum* 1.0/stuCount);
                }
                if (stuModel.scoreBiology >= sumScoreBiology * paasRate) {// 生物 计算及格人数
                    classModel.bioPassStuNum += 1;
                    classModel.bioPassRate = (float) (classModel.bioPassStuNum* 1.0/stuCount);
                }

                //计算语文 总成绩
                classModel.chnTotalScore += stuModel.scoreChiness;
                classModel.chnAveageScore = (float) (classModel.chnTotalScore* 1.0/stuCount);

                //计算数学  总成绩
                classModel.mathTotalScore += stuModel.scoreMath;
                classModel.mathAveageScore = (float) (classModel.mathTotalScore* 1.0/stuCount);

                //计算英语  总成绩
                classModel.EngTotalScore += stuModel.scoreEnglish;
                classModel.EngAveageScore = (float) (classModel.EngTotalScore* 1.0/stuCount);

                //计算政治  总成绩
                classModel.moralityTotalScore += stuModel.scoreMorality;
                classModel.moralityAveageScore = (float) (classModel.moralityTotalScore* 1.0/stuCount);

                //计算历史  总成绩
                classModel.historyTotalScore += stuModel.scoreHistory;
                classModel.historyAveageScore = (float) (classModel.historyTotalScore* 1.0/stuCount);

                //计算地理  总成绩
                classModel.geoTotalScore += stuModel.scoreGeography;
                classModel.geoAveageScore = (float) (classModel.geoTotalScore* 1.0/stuCount);

                //计算物理  总成绩
                classModel.phyTotalScore += stuModel.scorePhysics;
                classModel.phyAveageScore = (float) (classModel.phyTotalScore* 1.0/stuCount);

                //计算化学  总成绩
                classModel.chemistryTotalScore += stuModel.scoreChemistry;
                classModel.chemistryAveageScore = (float) (classModel.chemistryTotalScore* 1.0/stuCount);

                //计算生物  总成绩
                classModel.bioTotalScore += stuModel.scoreBiology;
                classModel.bioAveageScore = (float) (classModel.bioTotalScore* 1.0/stuCount);
            }
        }

        String[] values = new String[]{"chn","Eng","math","morality","history","geo","phy","chemistry","bio"};
        ArrayList<String> arrTemp = new ArrayList<String>();
        arrTemp.addAll(Arrays.asList(values));

        for (String prefixStr : arrTemp) {

            keyName =  prefixStr +"AveageScore";
            Comparator<ClassModel> comparator = new Comparator<ClassModel>() {//根据平均成绩排
                public int compare(ClassModel o1, ClassModel o2) {

                    float result =   (float)getKeyValue(keyName,o2) - (float)getKeyValue(keyName,o1) ; //
                    if (result == 0) {
                        return Integer.parseInt(o1.gradeRank) - Integer.parseInt(o2.gradeRank);
                    } else {
                        return (int)result;
                    }
                }
            };
            Collections.sort(arrCollectModel, comparator);

            int index = arrCollectModel.size();
            for (ClassModel  classModel : arrCollectModel) {
                String propertyStr = prefixStr + "AveageValue";
                try {
                    setProperty(classModel,propertyStr,index);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                index --;
            }
        }
        for (String prefixStr : arrTemp) {
            int  index = arrCollectModel.size();
           keyName =  prefixStr +"GoodRate";
            Comparator<ClassModel> comparator = new Comparator<ClassModel>() {////根据优生率
                public int compare(ClassModel o1, ClassModel o2) {

                    float result = (float)getKeyValue(keyName,o1) - (float)getKeyValue(keyName,o2) ; //
                    if (result == 0) {
                        return Integer.parseInt(o1.gradeRank) - Integer.parseInt(o2.gradeRank);
                    } else {
                        return (int)result;
                    }
                }
            };
            Collections.sort(arrCollectModel, comparator);
            for (ClassModel  classModel : arrCollectModel) {
                String propertyStr = prefixStr + "GoodValue";
                try {
                    setProperty(classModel,propertyStr,index);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                index --;
            }
        }
        for (String prefixStr : arrTemp) {
            int index = arrCollectModel.size();
            keyName =  prefixStr +"PassRate";//根据及格率
            Comparator<ClassModel> comparator = new Comparator<ClassModel>() {////根据优生率
                public int compare(ClassModel o1, ClassModel o2) {

                    float result = (float)getKeyValue(keyName,o1) - (float)getKeyValue(keyName,o2) ; //
                    if (result == 0) {
                        return Integer.parseInt(o1.gradeRank) - Integer.parseInt(o2.gradeRank);
                    } else {
                        return (int)result;
                    }
                }
            };
            Collections.sort(arrCollectModel, comparator);

            for (ClassModel  classModel : arrCollectModel) {
                String propertyStr = prefixStr + "PassValue";
                try {
                    setProperty(classModel,propertyStr,index);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                index --;
            }
        }
        //计算各科总分值  TotalValue  chnAveageValue chnGoodValue chnPassValue
        for (ClassModel  classModel : arrCollectModel) {
            for (String prefixStr : arrTemp) {
                int aveageValue = (int) getKeyValue(prefixStr +"AveageValue",classModel); //平均分单值
                int goodValue   = (int) getKeyValue(prefixStr +"GoodValue",classModel);   //优生率单值
                int passValue   = (int) getKeyValue(prefixStr +"PassValue",classModel);   //及格率单值
                int totalValue = aveageValue + goodValue + passValue;
                String totalValuePro = prefixStr + "TotalValue";
                try {
                    setProperty(classModel,totalValuePro,totalValue);//设置各科各自的总单值
                } catch (Exception e) {
                    e.printStackTrace();
                }
                classModel.classTotalValue += totalValue;//计算所有有学科总单值
            }
        }

        Comparator<ClassModel> comparator = new Comparator<ClassModel>() {//根据总评分 确定 名次
            public int compare(ClassModel o1, ClassModel o2) {

                int result = (int)getKeyValue("classTotalValue",o1) - (int)getKeyValue("classTotalValue",o2) ; //
                if (result == 0) {
                    return Integer.parseInt(o1.gradeRank) - Integer.parseInt(o2.gradeRank);
                } else {
                    return (int)result;
                }
            }
        };
        Collections.sort(arrCollectModel, comparator);
        int classTotalRank = 1;
        for (ClassModel  model : arrCollectModel) {
            model.classTotalRank =  classTotalRank;
            classTotalRank ++;
        }
        for (String prefixStr : arrTemp) {//给每一学科 分值(单值之和)  定个班级之间的名次 分值越高， 名次值越高

             keyName =  prefixStr +"TotalValue";
            Comparator<ClassModel> comparator2 = new Comparator<ClassModel>() {//根据总评分 确定 名次
                public int compare(ClassModel o1, ClassModel o2) {

                    int result = (int)getKeyValue(keyName,o1) - (int)getKeyValue(keyName,o2) ; //
                    if (result == 0) {
                        return Integer.parseInt(o1.gradeRank) - Integer.parseInt(o2.gradeRank);
                    } else {
                        return (int)result;
                    }
                }
            };
            Collections.sort(arrCollectModel, comparator2);

            for (ClassModel  classModel : arrCollectModel) {
                int index = arrCollectModel.size();
                String  proRankValue = prefixStr + "RankValue";
                try {
                    setProperty(classModel,proRankValue,index);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                index --;
            }
        }
    }

    public void generateCollectTable(){//生成汇总表
        if (fileUrl==null ||fileUrl.length() == 0){
            if (gradeFactoryInterface != null){
                gradeFactoryInterface.alertInfo("请先选择文件",false);
            }
            return;
        }
        if (arrModel.size() == 0){
            initArrModel(fileUrl);
        }
        int cols = 40;//列数，根据需求修改
        ArrayList<String>  xlsDataMuArr = new ArrayList<String>();// 创建存放XLS文件数据的数组

        // 第一行内容
        xlsDataMuArr.add("班级");
        xlsDataMuArr.add("指标");
        xlsDataMuArr.add("语");
        xlsDataMuArr.add("数");
        xlsDataMuArr.add("外");
        xlsDataMuArr.add("政");
        xlsDataMuArr.add("史");
        xlsDataMuArr.add("地");
        xlsDataMuArr.add("物");
        xlsDataMuArr.add("化");
        xlsDataMuArr.add("生");
        xlsDataMuArr.add("语单值");xlsDataMuArr.add("分值");xlsDataMuArr.add("名次");
        xlsDataMuArr.add("数单值");xlsDataMuArr.add("分值");xlsDataMuArr.add("名次");
        xlsDataMuArr.add("外单值");xlsDataMuArr.add("分值");xlsDataMuArr.add("名次");
        xlsDataMuArr.add("政单值");xlsDataMuArr.add("分值");xlsDataMuArr.add("名次");
        xlsDataMuArr.add("史单值");xlsDataMuArr.add("分值");xlsDataMuArr.add("名次");
        xlsDataMuArr.add("地单值");xlsDataMuArr.add("分值");xlsDataMuArr.add("名次");
        xlsDataMuArr.add("物单值");xlsDataMuArr.add("分值");xlsDataMuArr.add("名次");
        xlsDataMuArr.add("化单值");xlsDataMuArr.add("分值");xlsDataMuArr.add("名次");
        xlsDataMuArr.add("生单值");xlsDataMuArr.add("分值");xlsDataMuArr.add("名次");
        xlsDataMuArr.add("总评分");
        xlsDataMuArr.add("名次");


        //列数据
        for (ClassModel  model : arrCollectModel) {
            xlsDataMuArr.add(model.className);
            xlsDataMuArr.add("平均分");
            String[] values = new String[]{"chn","Eng","math","morality","history","geo","phy","chemistry","bio"};
            ArrayList<String> arrTemp = new ArrayList<String>();
            arrTemp.addAll(Arrays.asList(values));
            for (String  prefixStr : arrTemp) {//列数据第一行 平均模块
                float value = (float) getKeyValue(prefixStr + "AveageScore",model);
                xlsDataMuArr.add(new DecimalFormat("0.00").format(value) + "");
            }
            for (String prefixStr : arrTemp) {//列数据第一行 单值模块

                xlsDataMuArr.add(getKeyValue(prefixStr+ "AveageValue",model) + "");//单值
                xlsDataMuArr.add(getKeyValue(prefixStr +"TotalValue",model) + "");//学科 平均分 及格率，优生率 单值 总和
                xlsDataMuArr.add(getKeyValue(prefixStr +"RankValue",model)+"");//学科  名次
            }

            xlsDataMuArr.add(model.classTotalValue +"");//总评分
            xlsDataMuArr.add(model.classTotalRank +"");//总名次
            xlsDataMuArr.add(" ");
            xlsDataMuArr.add("及格率");
            for (String  prefixStr : arrTemp) {//列数据第二行
                xlsDataMuArr.add(new DecimalFormat("0.00").format(getKeyValue(prefixStr +"PassRate",model)));//前面几个
            }

            for (String prefixStr : arrTemp) {//列数据第二行
                int value = (int) getKeyValue(prefixStr +"PassValue",model);//前面几个
                if (prefixStr != arrTemp.get(arrTemp.size() - 1)) {
                    xlsDataMuArr.add(value+"");
                    xlsDataMuArr.add(" ");
                    xlsDataMuArr.add(" ");
                }else{
                    xlsDataMuArr.add(value+"");//生单值 跳两格 并换行  后面的总评分和总名次
                    xlsDataMuArr.add(" ");
                    xlsDataMuArr.add(" ");
                    xlsDataMuArr.add(" ");
                    xlsDataMuArr.add(" ");
                }
            }
            xlsDataMuArr.add(" ");
            //优生率
            xlsDataMuArr.add("优生率");
            for (String  prefixStr : arrTemp) {//列数据第二行
                float value = (float) getKeyValue(prefixStr + "GoodRate",model);//前面几个
                xlsDataMuArr.add(new DecimalFormat("0.00").format(value));
            }
            for (String  prefixStr : arrTemp) {//列数据第二行
                int value = (int) getKeyValue(prefixStr +"GoodValue",model);//前面几个
                if (prefixStr != arrTemp.get(arrTemp.size() - 1)) {
                    xlsDataMuArr.add(value+"");
                    xlsDataMuArr.add(" ");
                    xlsDataMuArr.add(" ");
                }else{
                    xlsDataMuArr.add(value+"");//生单值 跳两格 并换行  后面的总评分和总名次   第一个班的数据解析完成 多加个换行
                    xlsDataMuArr.add(" ");
                    xlsDataMuArr.add(" ");
                    xlsDataMuArr.add(" ");
                    xlsDataMuArr.add(" ");
                }
            }
            for (int i = 0 ;i <cols;i++) {
                xlsDataMuArr.add(" ");
            }
        }
        ClassModel classModel = arrClassModel.get(0);
        AsyncTask task =   new AsyncTask<Object,Void,Integer>(){
            @Override
            protected Integer doInBackground(Object... objects) {
                ClassModel classModel = (ClassModel) objects[0];
                ArrayList<String> xlsDataMuArr = (ArrayList<String>) objects[1];
                int cols = 40;//列数，根据需求修改
                int rows = xlsDataMuArr.size() / cols;
                try {
                    creator.openExcel(new File((String) objects[2]))
                            .openSheet(0); //
                    WritableCellFormat format = ZzFormatCreator
                            .getInstance()
                            .createCellFont(WritableFont.ARIAL)
                            .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                            .setFontSize(14)
                            .setFontColor(Colour.DARK_GREEN)
                            .getCellFormat();
                    int finishCount= 0;
                    int totalCount =  xlsDataMuArr.size();
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            String value = (String) xlsDataMuArr.get(i * cols + j);
                            creator.setColumnWidth(j, 25)
                                    .setRowHeight(i, 400)
                                    .fillContent(j, i, value, format);
                            if (gradeFactoryInterface != null){
                                finishCount += 1;
                                boolean isFinish = false;
                                int progress =  (int)((finishCount/(xlsDataMuArr.size() *1.0)) * 100);
                                if (finishCount >= totalCount){
                                    isFinish = true;
                                    progress = 100;
                                }
                                gradeFactoryInterface.onProgress(progress,isFinish);
                            }
                        }
                    }
                    //合并指定单元格
                    for (int i = 0; i < arrClassModel.size() * 3 + arrClassModel.size(); i++) {//有多少个班 每个班占据一大行 i 是行
                        for (int j = 0; j < 40; j++) {//j是列
                            if ((i - 1)%4 != 0) {
                                continue;
                            }
                            if (j == 0 ||
                                    j == 12 || j == 13 ||
                                    j == 15 || j == 16 ||
                                    j == 18 || j == 19 ||
                                    j == 21 || j == 22 ||
                                    j == 24 || j == 25 ||
                                    j == 27 || j == 28 ||
                                    j == 30 || j == 31 ||
                                    j == 33 || j == 34 ||
                                    j == 36 || j == 37 ||
                                    j == 38 ||
                                    j == 39 || j == 40) {
                                 creator.mergeRow(j,i,i + 2);
                            }
                        }
                        if (i >= 4 & i%4 ==0){
                            creator.mergeColumn(i,0,40);
                        }
                    }
                    creator.close();
                    return  1;
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                } catch (BiffException e) {
                    e.printStackTrace();
                    return 0;
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                    return 0;
                } catch (WriteException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                if (gradeFactoryInterface!=null){
                    if (aVoid == 1) {
                        gradeFactoryInterface.alertInfo("导出汇总分析表成功",true);
                    }else{
                        gradeFactoryInterface.alertInfo("导出汇总分析表失败",false);
                    }
                }
            }
        }.execute(classModel,xlsDataMuArr,fileUrl);



    }
    static Object getKeyValue(String keyName, Object o){
        try {
            String firstLetter = keyName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + keyName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            System.out.println("属性不存在");
            return null;
        }
    }
    //定义一个可以将propertyName的属性的值设置为value的方法。
    static void setProperty(Object obj,String propertyName,Object value) throws Exception{

        Class cls =obj.getClass();//获取obj字节码
        Field field =cls.getDeclaredField(propertyName);//得到propertyName字段

        field.setAccessible(true);//因为对象的属性是私有的，先把权限打开。（暴力反射）
        field.set(obj, value);//将传入的obj对象中为propertyName的属性的值设置为value.
    }
}

