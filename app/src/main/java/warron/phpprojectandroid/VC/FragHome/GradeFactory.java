package warron.phpprojectandroid.VC.FragHome;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

import me.zhouzhuo.zzexcelcreator.ZzFormatCreator;


public class GradeFactory {
    /**
     * Excel保存路径
     */

    String ExcelPath ;
    ArrayList<StudentModel> arrModel;
    ArrayList<ClassModel> arrCollectModel;//年级排名
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
                    try {
                        instance.calculateCollectTable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(context, "所有信息已经初始化完毕", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

    }

    private void createExcel() {//把表创建好 把sheet创建好

        StudentModel model =  arrModel.get(0);
        new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                try {
                    StudentModel model =  arrModel.get(0);
                    creator.createExcel(ExcelPath, params[0]);
                    String excelPath = ExcelPath +"/"+ model.examName + "成绩分析表.xls";
                    for (ClassModel classModel : arrClassModel) {
                        String  fileName = classModel.className +"班学生排序";
                        instance.addSheet(excelPath,fileName);
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
                    instance.addSheet(excelPath,"分析表");
                    instance.addSheet(excelPath,"班级之间排名");
                    instance.addSheet(excelPath,"校学生成绩排名");
                }
            }

        }.execute(model.examName+"成绩分析表");

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
                    Toast.makeText(context, "导出汇总表失败", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "导出汇总表成功", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(xlsDataMuArr,ExcelPath + "/" + model.examName + "成绩分析表.xls");
    }

    public  void genearteClassRankTable() throws WriteException {

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
                            .openSheet(1); //班级之间排名
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
                    Toast.makeText(context, "导出班级间排名失败", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "导出班级间排名成功", Toast.LENGTH_SHORT).show();
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

        StudentModel modelS = arrModel.get(0);
        final WritableCellFormat format = ZzFormatCreator
                .getInstance()
                .createCellFont(WritableFont.ARIAL)
                .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                .setFontSize(14)
                .setFontColor(Colour.DARK_GREEN)
                .getCellFormat();

        for(ClassModel classModel :arrClassModel) {
            ArrayList<String>  xlsDataMuArr = new ArrayList<String>();// 创建存放XLS文件数据的数组
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
                xlsDataMuArr.add(model.gradeRank +""  );////校排
                xlsDataMuArr.add(model.classRank +""  );//班排
                xlsDataMuArr.add(model.className  );//班级
                xlsDataMuArr.add(model.enrollmentNumber  +"");//学籍号
                xlsDataMuArr.add(model.examinationNumber +"");////考号
                xlsDataMuArr.add(model.scoreChiness +"" );//语文
                xlsDataMuArr.add(model.scoreMath  +""   );//数学
                xlsDataMuArr.add(model.scoreEnglish  +"");//英语
                xlsDataMuArr.add(model.scoreMorality +"");//品德/政治
                xlsDataMuArr.add(model.scoreHistory +"" );//历史
                xlsDataMuArr.add(model.scorePhysics +"" );//物理
                xlsDataMuArr.add(model.scoreChemistry +"");//化学
                xlsDataMuArr.add(model.scoreBiology +"");//生物
                xlsDataMuArr.add(model.scoreGeography +"");//地理
                xlsDataMuArr.add(model.scoreTotal +"");//总分
            }

            new AsyncTask<Object,Void,Integer>(){
                @Override
                protected Integer doInBackground(Object... objects) {
                    ClassModel classModel = (ClassModel) objects[0];
                    ArrayList<String> xlsDataMuArr = (ArrayList<String>) objects[1];
                    int cols = 20;//列数，根据需求修改
                    int rows = xlsDataMuArr.size() / cols;
                    try {
                        int sheetIndex = Integer.parseInt(classModel.gradeRank) + 3 - 1;
                        creator.openExcel(new File((String) objects[2]))
                                .openSheet(sheetIndex); //
                        for (int i = 0; i < rows; i++) {
                            for (int j = 0; j < cols; j++) {
                                String value = (String) xlsDataMuArr.get(i * cols + j);
                                creator.setColumnWidth(j, 25)
                                        .setRowHeight(i, 400)
                                        .fillContent(j, i, value, format);
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
                    if (aVoid != 1) {
                        Toast.makeText(context, "导出各班级学生排名失败", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "导出各班级学生排名成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute(classModel,xlsDataMuArr,ExcelPath +"/"+ modelS.examName + "成绩分析表.xls");
        }
    }

    //汇总统计
    public void calculateCollectTable() throws Exception {

        arrCollectModel = new ArrayList<ClassModel>();
        arrCollectModel.addAll(this.arrClassModel);

        float totalScore =  this.allProjectSumScore;
        float goodRate   =  this.goodRate;
        float paasRate   =  this.passRate;

        for (ClassModel  classModel : arrCollectModel) {
            for (StudentModel  stuModel : classModel.arrStuModel) {
                int stuCount = classModel.arrStuModel.size();
                if (stuModel.scoreChiness >= totalScore * goodRate) {//计算优生人数
                    classModel.chnGoodStuNum += 1;
                    classModel.chnGoodRate = classModel.chnGoodStuNum/stuCount;
                }
                if (stuModel.scoreChiness >= totalScore * paasRate) {//计算及格人数
                    classModel.chnPassStuNum += 1;
                    classModel.chnPassRate = classModel.chnPassStuNum /stuCount;
                }

                if (stuModel.scoreMath >= totalScore * goodRate) { //计算优生人数
                    classModel.mathGoodStuNum += 1;
                    classModel.mathGoodRate = classModel.mathGoodStuNum /stuCount;
                }
                if (stuModel.scoreMath >= totalScore * paasRate) {//计算及格人数
                    classModel.mathPassStuNum += 1;
                    classModel.mathPassRate = classModel.mathPassStuNum /stuCount;
                }

                if (stuModel.scoreEnglish >= totalScore * goodRate) {//计算优生人数
                    classModel.EngGoodStuNum += 1;
                    classModel.EngGoodRate = classModel.EngGoodStuNum/stuCount;
                }
                if (stuModel.scoreEnglish >= totalScore * paasRate) {//计算及格人数
                    classModel.EngPassStuNum += 1;
                    classModel.EngPassRate = classModel.EngPassStuNum /stuCount;
                }

                if (stuModel.scoreMorality >= totalScore * goodRate) {//计算优生人数
                    classModel.moralityGoodStuNum += 1;
                    classModel.moralityGoodRate = classModel.moralityGoodStuNum/stuCount;
                }
                if (stuModel.scoreMorality >= totalScore * paasRate) {//计算及格人数
                    classModel.moralityPassStuNum += 1;
                    classModel.moralityPassRate = classModel.moralityPassStuNum/stuCount;
                }

                if (stuModel.scoreHistory >= totalScore * goodRate) {//计算优生人数
                    classModel.historyGoodStuNum += 1;
                    classModel.historyGoodRate = classModel.historyGoodStuNum/stuCount;
                }
                if (stuModel.scoreHistory >= totalScore * paasRate) {//计算及格人数
                    classModel.historyPassStuNum += 1;
                    classModel.historyPassRate = classModel.historyPassStuNum/stuCount;
                }

                if (stuModel.scoreGeography >= totalScore * goodRate) {//计算优生人数
                    classModel.geoGoodStuNum += 1;
                    classModel.geoGoodRate = classModel.geoGoodStuNum/stuCount;
                }
                if (stuModel.scoreGeography >= totalScore * paasRate) {//计算及格人数
                    classModel.geoPassStuNum += 1;
                    classModel.geoPassRate = classModel.geoPassStuNum/stuCount;
                }

                if (stuModel.scorePhysics >= totalScore * goodRate) {//计算优生人数
                    classModel.phyGoodStuNum += 1;
                    classModel.phyGoodRate = classModel.phyGoodStuNum/stuCount;
                }
                if (stuModel.scorePhysics >= totalScore * paasRate) {//计算及格人数
                    classModel.phyPassStuNum += 1;
                    classModel.phyPassRate = classModel.phyPassStuNum/stuCount;
                }

                if (stuModel.scoreChemistry >= totalScore * goodRate) {//计算优生人数
                    classModel.chemistryGoodStuNum += 1;
                    classModel.chemistryGoodRate = classModel.chemistryGoodStuNum/stuCount;
                }
                if (stuModel.scoreChemistry >= totalScore * paasRate) {//计算及格人数
                    classModel.chemistryPassStuNum += 1;
                    classModel.chemistryPassRate = classModel.chemistryPassStuNum/stuCount;
                }

                if (stuModel.scoreBiology >= totalScore * goodRate) {//计算优生人数
                    classModel.bioGoodStuNum += 1;
                    classModel.bioGoodRate = classModel.bioGoodStuNum/stuCount;
                }
                if (stuModel.scoreBiology >= totalScore * paasRate) {//计算及格人数
                    classModel.bioPassStuNum += 1;
                    classModel.bioPassRate = classModel.bioPassStuNum/stuCount;
                }

                //计算语文 总成绩
                classModel.chnTotalScore += stuModel.scoreChiness;
                classModel.chnAveageScore =  classModel.chnTotalScore/stuCount;

                //计算数学  总成绩
                classModel.mathTotalScore += stuModel.scoreMath;
                classModel.mathAveageScore =  classModel.mathTotalScore/stuCount;

                //计算英语  总成绩
                classModel.EngTotalScore += stuModel.scoreEnglish;
                classModel.EngAveageScore =  classModel.EngTotalScore/stuCount;

                //计算政治  总成绩
                classModel.moralityTotalScore += stuModel.scoreMorality;
                classModel.moralityAveageScore =  classModel.moralityTotalScore/stuCount;

                //计算历史  总成绩
                classModel.historyTotalScore += stuModel.scoreHistory;
                classModel.historyAveageScore =  classModel.historyTotalScore/stuCount;

                //计算地理  总成绩
                classModel.geoTotalScore += stuModel.scoreGeography;
                classModel.geoAveageScore =  classModel.geoTotalScore/stuCount;

                //计算物理  总成绩
                classModel.phyTotalScore += stuModel.scorePhysics;
                classModel.phyAveageScore =  classModel.phyTotalScore/stuCount;

                //计算化学  总成绩
                classModel.chemistryTotalScore += stuModel.scoreChemistry;
                classModel.chemistryAveageScore =  classModel.chemistryTotalScore/stuCount;

                //计算生物  总成绩
                classModel.bioTotalScore += stuModel.scoreBiology;
                classModel.bioAveageScore = classModel.bioTotalScore/stuCount;
            }
        }
        String[] values = new String[]{"chn","Eng","math","morality","history","geo","phy","chemistry","bio"};
        ArrayList<String> arrTemp = new ArrayList<String>();
        arrTemp.addAll(Arrays.asList(values));

        for (String prefixStr : arrTemp) {

            final String keyName =  prefixStr +"AveageScore";
            Comparator<ClassModel> comparator = new Comparator<ClassModel>() {//根据平均成绩排
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

            int index = arrCollectModel.size();
            for (ClassModel  classModel : arrCollectModel) {
                String propertyStr = prefixStr + "AveageValue";
                String  indexStr = index + "";
                setProperty(classModel,propertyStr,indexStr);
                index --;
            }
        }
        for (String prefixStr : arrTemp) {
            int  index = arrCollectModel.size();
            final String keyName =  prefixStr +"GoodRate";
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
                String indexStr = index  + "";
                setProperty(classModel,propertyStr,indexStr);
                index --;
            }
        }
        for (String prefixStr : arrTemp) {
            int index = arrCollectModel.size();
            final String keyName =  prefixStr +"PassRate";//根据及格率
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
                String indexStr = index  + "";
                setProperty(classModel,propertyStr,indexStr);
                index --;
            }
        }
        //计算各科总分值  TotalValue  chnAveageValue chnGoodValue chnPassValue
        for (ClassModel  classModel : arrCollectModel) {
            for (String prefixStr : arrTemp) {
                String aveageValue = (String) getKeyValue(prefixStr +"AveageValue",classModel); //平均分单值

                String goodValue = (String) getKeyValue(prefixStr +"GoodValue",classModel);   //优生率单值

                String passValue = (String) getKeyValue(prefixStr +"PassValue",classModel);//及格率单值

                int totalValue = Integer.parseInt(aveageValue) + Integer.parseInt(goodValue) + Integer.parseInt(passValue);
                String totalValuePro = prefixStr + "TotalValue";
                setProperty(classModel,totalValuePro,totalValue);//设置各科各自的总单值
                classModel.classTotalValue += totalValue;//计算所有有学科总单值
            }
        }

        Comparator<ClassModel> comparator = new Comparator<ClassModel>() {//根据总评分 确定 名次
            public int compare(ClassModel o1, ClassModel o2) {

                float result = (float)getKeyValue("classTotalValue",o1) - (float)getKeyValue("classTotalValue",o2) ; //
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

            final String keyName =  prefixStr +"TotalValue";
            Comparator<ClassModel> comparator2 = new Comparator<ClassModel>() {//根据总评分 确定 名次
                public int compare(ClassModel o1, ClassModel o2) {

                    float result = (float)getKeyValue(keyName,o1) - (float)getKeyValue(keyName,o2) ; //
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
                setProperty(classModel,proRankValue,index);
                index --;
            }
        }
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
