package warron.phpprojectandroid.VC.FragHome.model;

import java.util.ArrayList;

public class ClassModel {


 public int passStuNum;//及格人数 达到总成绩的60%
 public float passStuNumRate;//及格人数 达到总成绩的60%比率

 public int goodStuNum;//优生人数 达到总成绩的80%
 public float goodStuNumRate;//优生人数 达到总成绩的80%比率

 public int difficultyStuNum;// 学习成绩差人数  总成绩30以下
 public float difficultyStuNumRate;// 学习成绩差人数  总成绩30以下比率
 public String examName;//考试名称
 public String schoolName;//学校名称
 public String schoolID;//学校代码
 public String gradeName;//年级
 public String gradeRank;//排名 班级之间的成绩排名
 public String className;//班级名称
 public int totalStuNum;//学生总人数
 public float aveageScore;//平均分
 public float scoreTotal;//班级总分
 public int beforTen;//前10
 public int beforFifty;//50
 public int beforOneHundred;//100
 public int beforTwoHundred;//200
 public int beforFiveHundred;//500
 public int beforOneThousand;//1000
 public int beforTwoThousand;//2000
 public ArrayList<StudentModel> arrStuModel;//班级中的学生

 public float chnTotalScore;//总成绩 语文
 public float chnAveageScore;//平均成绩
 public int chnAveageValue;//单值 分值越高,单值越高 如 14个班 如优生率排第一，值为14
 public float chnGoodRate;//优生率
 public int chnGoodStuNum;//优生人数
 public int chnGoodValue;//单值
 public float chnPassRate;//及格率
 public int chnPassValue;//单值
 public int chnPassStuNum;//及格人数
 public int chnTotalValue;// 总单值 三个单值之和
 public int chnRankValue;// 排名,总单值 排名，分值越高 排名值越高

 public float mathTotalScore;//总成绩 数学
 public float mathAveageScore;//平均成绩
 public int mathAveageValue;//单值 分值越高,单值越高 如 14个班 优生率排第一，值为14
 public float mathGoodRate;//优生率
 public int mathGoodValue;//单值
 public int mathGoodStuNum;//优生人数
 public float mathPassRate;//及格率
 public int mathPassValue;//单值
 public int mathPassStuNum;//通过人数
 public int mathTotalValue;// 总单值 三个单值之和
 public int mathRankValue;//排名,总单值 排名，分值越高 排名值越高

 public float EngTotalScore;//总成绩 英语
 public float EngAveageScore;//平均成绩
 public int EngAveageValue;//单值 分值越高,单值越高 如 14个班 优生率排第一，值为14
 public float EngGoodRate;//优生率
 public int EngGoodValue;//单值
 public int EngGoodStuNum;//优生人数
 public float EngPassRate;//及格率
 public int EngPassValue;//单值
 public int EngPassStuNum;//通过人数
 public int EngTotalValue;// 总单值 三个单值之和
 public int EngRankValue;//排名,总单值 排名，分值越高 排名值越高

 public float moralityTotalScore;//总成绩 政治
 public float moralityAveageScore;//平均成绩
 public int moralityAveageValue;//单值 分值越高,单值越高 如 14个班 优生率排第一，值为14
 public float moralityGoodRate;//优生率
 public int moralityGoodValue;//单值
 public int moralityGoodStuNum;//优生人数
 public float moralityPassRate;//及格率
 public int moralityPassValue;//单值
 public int moralityPassStuNum;//通过人数
 public int moralityTotalValue;// 总单值 三个单值之和
 public int moralityRankValue;//排名,总单值 排名，分值越高 排名值越高

 public float historyTotalScore;//总成绩 历史
 public float historyAveageScore;//平均成绩
 public int historyAveageValue;//单值 分值越高,单值越高 如 14个班 优生率排第一，值为14
 public float historyGoodRate;//优生率
 public int historyGoodValue;//单值
 public int historyGoodStuNum;//优生人数
 public float historyPassRate;//及格率
 public int historyPassValue;//单值
 public int historyPassStuNum;//通过人数
 public int historyTotalValue;// 总单值 三个单值之和
 public int historyRankValue;//排名,总单值 排名，分值越高 排名值越高

 public float geoTotalScore;//总成绩
 public float geoAveageScore;//平均成绩
 public int geoAveageValue;//单值 分值越高,单值越高 如 14个班 优生率排第一，值为14
 public float geoGoodRate;//优生率
 public int geoGoodValue;//单值
 public int geoGoodStuNum;//优生人数
 public float geoPassRate;//及格率
 public int geoPassValue;//单值
 public int geoPassStuNum;//通过人数
 public int geoTotalValue;// 总单值 三个单值之和
 public int geoRankValue;//排名,总单值 排名，分值越高 排名值越高

 public float bioTotalScore;//总成绩 地理
 public float bioAveageScore;//平均成绩
 public int bioAveageValue;//单值 分值越高,单值越高 如 14个班 优生率排第一，值为14
 public float bioGoodRate;//优生率
 public int bioGoodValue;//单值
 public int bioGoodStuNum;//优生人数
 public float bioPassRate;//及格率
 public int bioPassValue;//单值
 public int bioPassStuNum;//通过人数
 public int bioTotalValue;// 总单值 三个单值之和
 public int bioRankValue;//排名,总单值 排名，分值越高 排名值越高

 public float phyTotalScore;//总成绩 物理
 public float phyAveageScore;//平均成绩
 public int phyAveageValue;//单值 分值越高,单值越高 如 14个班 优生率排第一，值为14
 public float phyGoodRate;//优生率
 public int phyGoodValue;//单值
 public int phyGoodStuNum;//优生人数
 public float phyPassRate;//及格率
 public int phyPassValue;//单值
 public int phyPassStuNum;//通过人数
 public int phyTotalValue;// 总单值 三个单值之和
 public int phyRankValue;//排名,总单值 排名，分值越高 排名值越高


 public float chemistryTotalScore;//总成绩 化学
 public float chemistryAveageScore;//平均成绩
 public int chemistryAveageValue;//单值 分值越高,单值越高 如 14个班 优生率排第一，值为14
 public float chemistryGoodRate;//优生率
 public int chemistryGoodValue;//单值
 public int chemistryGoodStuNum;//优生人数
 public float chemistryPassRate;//及格率
 public int chemistryPassValue;//单值
 public int chemistryPassStuNum;//通过人数
 public int chemistryTotalValue;// 总单值 三个单值之和
 public int chemistryRankValue;//排名,总单值 排名，分值越高 排名值越高

 public int classTotalValue;//总评分 各学科总单值 的和
 public int classTotalRank;//总排名 总评分越高 排名越靠前

 public int getPassStuNum() {
  return passStuNum;
 }

 public void setPassStuNum(int passStuNum) {
  this.passStuNum = passStuNum;
 }

 public float getPassStuNumRate() {
  return passStuNumRate;
 }

 public void setPassStuNumRate(float passStuNumRate) {
  this.passStuNumRate = passStuNumRate;
 }

 public int getGoodStuNum() {
  return goodStuNum;
 }

 public void setGoodStuNum(int goodStuNum) {
  this.goodStuNum = goodStuNum;
 }

 public float getGoodStuNumRate() {
  return goodStuNumRate;
 }

 public void setGoodStuNumRate(float goodStuNumRate) {
  this.goodStuNumRate = goodStuNumRate;
 }

 public int getDifficultyStuNum() {
  return difficultyStuNum;
 }

 public void setDifficultyStuNum(int difficultyStuNum) {
  this.difficultyStuNum = difficultyStuNum;
 }

 public float getDifficultyStuNumRate() {
  return difficultyStuNumRate;
 }

 public void setDifficultyStuNumRate(float difficultyStuNumRate) {
  this.difficultyStuNumRate = difficultyStuNumRate;
 }

 public String getExamName() {
  return examName;
 }

 public void setExamName(String examName) {
  this.examName = examName;
 }

 public String getSchoolName() {
  return schoolName;
 }

 public void setSchoolName(String schoolName) {
  this.schoolName = schoolName;
 }

 public String getSchoolID() {
  return schoolID;
 }

 public void setSchoolID(String schoolID) {
  this.schoolID = schoolID;
 }

 public String getGradeName() {
  return gradeName;
 }

 public void setGradeName(String gradeName) {
  this.gradeName = gradeName;
 }

 public String getGradeRank() {
  return gradeRank;
 }

 public void setGradeRank(String gradeRank) {
  this.gradeRank = gradeRank;
 }

 public String getClassName() {
  return className;
 }

 public void setClassName(String className) {
  this.className = className;
 }

 public int getTotalStuNum() {
  return totalStuNum;
 }

 public void setTotalStuNum(int totalStuNum) {
  this.totalStuNum = totalStuNum;
 }

 public float getAveageScore() {
  return aveageScore;
 }

 public void setAveageScore(float aveageScore) {
  this.aveageScore = aveageScore;
 }

 public float getScoreTotal() {
  return scoreTotal;
 }

 public void setScoreTotal(float scoreTotal) {
  this.scoreTotal = scoreTotal;
 }

 public int getBeforTen() {
  return beforTen;
 }

 public void setBeforTen(int beforTen) {
  this.beforTen = beforTen;
 }

 public int getBeforFifty() {
  return beforFifty;
 }

 public void setBeforFifty(int beforFifty) {
  this.beforFifty = beforFifty;
 }

 public int getBeforOneHundred() {
  return beforOneHundred;
 }

 public void setBeforOneHundred(int beforOneHundred) {
  this.beforOneHundred = beforOneHundred;
 }

 public int getBeforTwoHundred() {
  return beforTwoHundred;
 }

 public void setBeforTwoHundred(int beforTwoHundred) {
  this.beforTwoHundred = beforTwoHundred;
 }

 public int getBeforFiveHundred() {
  return beforFiveHundred;
 }

 public void setBeforFiveHundred(int beforFiveHundred) {
  this.beforFiveHundred = beforFiveHundred;
 }

 public int getBeforOneThousand() {
  return beforOneThousand;
 }

 public void setBeforOneThousand(int beforOneThousand) {
  this.beforOneThousand = beforOneThousand;
 }

 public int getBeforTwoThousand() {
  return beforTwoThousand;
 }

 public void setBeforTwoThousand(int beforTwoThousand) {
  this.beforTwoThousand = beforTwoThousand;
 }

 public ArrayList<StudentModel> getArrStuModel() {
  return arrStuModel;
 }

 public void setArrStuModel(ArrayList<StudentModel> arrStuModel) {
  this.arrStuModel = arrStuModel;
 }

 public float getChnTotalScore() {
  return chnTotalScore;
 }

 public void setChnTotalScore(float chnTotalScore) {
  this.chnTotalScore = chnTotalScore;
 }

 public float getChnAveageScore() {
  return chnAveageScore;
 }

 public void setChnAveageScore(float chnAveageScore) {
  this.chnAveageScore = chnAveageScore;
 }

 public int getChnAveageValue() {
  return chnAveageValue;
 }

 public void setChnAveageValue(int chnAveageValue) {
  this.chnAveageValue = chnAveageValue;
 }

 public float getChnGoodRate() {
  return chnGoodRate;
 }

 public void setChnGoodRate(float chnGoodRate) {
  this.chnGoodRate = chnGoodRate;
 }

 public int getChnGoodStuNum() {
  return chnGoodStuNum;
 }

 public void setChnGoodStuNum(int chnGoodStuNum) {
  this.chnGoodStuNum = chnGoodStuNum;
 }

 public int getChnGoodValue() {
  return chnGoodValue;
 }

 public void setChnGoodValue(int chnGoodValue) {
  this.chnGoodValue = chnGoodValue;
 }

 public float getChnPassRate() {
  return chnPassRate;
 }

 public void setChnPassRate(float chnPassRate) {
  this.chnPassRate = chnPassRate;
 }

 public int getChnPassValue() {
  return chnPassValue;
 }

 public void setChnPassValue(int chnPassValue) {
  this.chnPassValue = chnPassValue;
 }

 public int getChnPassStuNum() {
  return chnPassStuNum;
 }

 public void setChnPassStuNum(int chnPassStuNum) {
  this.chnPassStuNum = chnPassStuNum;
 }

 public int getChnTotalValue() {
  return chnTotalValue;
 }

 public void setChnTotalValue(int chnTotalValue) {
  this.chnTotalValue = chnTotalValue;
 }

 public int getChnRankValue() {
  return chnRankValue;
 }

 public void setChnRankValue(int chnRankValue) {
  this.chnRankValue = chnRankValue;
 }

 public float getMathTotalScore() {
  return mathTotalScore;
 }

 public void setMathTotalScore(float mathTotalScore) {
  this.mathTotalScore = mathTotalScore;
 }

 public float getMathAveageScore() {
  return mathAveageScore;
 }

 public void setMathAveageScore(float mathAveageScore) {
  this.mathAveageScore = mathAveageScore;
 }

 public int getMathAveageValue() {
  return mathAveageValue;
 }

 public void setMathAveageValue(int mathAveageValue) {
  this.mathAveageValue = mathAveageValue;
 }

 public float getMathGoodRate() {
  return mathGoodRate;
 }

 public void setMathGoodRate(float mathGoodRate) {
  this.mathGoodRate = mathGoodRate;
 }

 public int getMathGoodValue() {
  return mathGoodValue;
 }

 public void setMathGoodValue(int mathGoodValue) {
  this.mathGoodValue = mathGoodValue;
 }

 public int getMathGoodStuNum() {
  return mathGoodStuNum;
 }

 public void setMathGoodStuNum(int mathGoodStuNum) {
  this.mathGoodStuNum = mathGoodStuNum;
 }

 public float getMathPassRate() {
  return mathPassRate;
 }

 public void setMathPassRate(float mathPassRate) {
  this.mathPassRate = mathPassRate;
 }

 public int getMathPassValue() {
  return mathPassValue;
 }

 public void setMathPassValue(int mathPassValue) {
  this.mathPassValue = mathPassValue;
 }

 public int getMathPassStuNum() {
  return mathPassStuNum;
 }

 public void setMathPassStuNum(int mathPassStuNum) {
  this.mathPassStuNum = mathPassStuNum;
 }

 public int getMathTotalValue() {
  return mathTotalValue;
 }

 public void setMathTotalValue(int mathTotalValue) {
  this.mathTotalValue = mathTotalValue;
 }

 public int getMathRankValue() {
  return mathRankValue;
 }

 public void setMathRankValue(int mathRankValue) {
  this.mathRankValue = mathRankValue;
 }

 public float getEngTotalScore() {
  return EngTotalScore;
 }

 public void setEngTotalScore(float engTotalScore) {
  EngTotalScore = engTotalScore;
 }

 public float getEngAveageScore() {
  return EngAveageScore;
 }

 public void setEngAveageScore(float engAveageScore) {
  EngAveageScore = engAveageScore;
 }

 public int getEngAveageValue() {
  return EngAveageValue;
 }

 public void setEngAveageValue(int engAveageValue) {
  EngAveageValue = engAveageValue;
 }

 public float getEngGoodRate() {
  return EngGoodRate;
 }

 public void setEngGoodRate(float engGoodRate) {
  EngGoodRate = engGoodRate;
 }

 public int getEngGoodValue() {
  return EngGoodValue;
 }

 public void setEngGoodValue(int engGoodValue) {
  EngGoodValue = engGoodValue;
 }

 public int getEngGoodStuNum() {
  return EngGoodStuNum;
 }

 public void setEngGoodStuNum(int engGoodStuNum) {
  EngGoodStuNum = engGoodStuNum;
 }

 public float getEngPassRate() {
  return EngPassRate;
 }

 public void setEngPassRate(float engPassRate) {
  EngPassRate = engPassRate;
 }

 public int getEngPassValue() {
  return EngPassValue;
 }

 public void setEngPassValue(int engPassValue) {
  EngPassValue = engPassValue;
 }

 public int getEngPassStuNum() {
  return EngPassStuNum;
 }

 public void setEngPassStuNum(int engPassStuNum) {
  EngPassStuNum = engPassStuNum;
 }

 public int getEngTotalValue() {
  return EngTotalValue;
 }

 public void setEngTotalValue(int engTotalValue) {
  EngTotalValue = engTotalValue;
 }

 public int getEngRankValue() {
  return EngRankValue;
 }

 public void setEngRankValue(int engRankValue) {
  EngRankValue = engRankValue;
 }

 public float getMoralityTotalScore() {
  return moralityTotalScore;
 }

 public void setMoralityTotalScore(float moralityTotalScore) {
  this.moralityTotalScore = moralityTotalScore;
 }

 public float getMoralityAveageScore() {
  return moralityAveageScore;
 }

 public void setMoralityAveageScore(float moralityAveageScore) {
  this.moralityAveageScore = moralityAveageScore;
 }

 public int getMoralityAveageValue() {
  return moralityAveageValue;
 }

 public void setMoralityAveageValue(int moralityAveageValue) {
  this.moralityAveageValue = moralityAveageValue;
 }

 public float getMoralityGoodRate() {
  return moralityGoodRate;
 }

 public void setMoralityGoodRate(float moralityGoodRate) {
  this.moralityGoodRate = moralityGoodRate;
 }

 public int getMoralityGoodValue() {
  return moralityGoodValue;
 }

 public void setMoralityGoodValue(int moralityGoodValue) {
  this.moralityGoodValue = moralityGoodValue;
 }

 public int getMoralityGoodStuNum() {
  return moralityGoodStuNum;
 }

 public void setMoralityGoodStuNum(int moralityGoodStuNum) {
  this.moralityGoodStuNum = moralityGoodStuNum;
 }

 public float getMoralityPassRate() {
  return moralityPassRate;
 }

 public void setMoralityPassRate(float moralityPassRate) {
  this.moralityPassRate = moralityPassRate;
 }

 public int getMoralityPassValue() {
  return moralityPassValue;
 }

 public void setMoralityPassValue(int moralityPassValue) {
  this.moralityPassValue = moralityPassValue;
 }

 public int getMoralityPassStuNum() {
  return moralityPassStuNum;
 }

 public void setMoralityPassStuNum(int moralityPassStuNum) {
  this.moralityPassStuNum = moralityPassStuNum;
 }

 public int getMoralityTotalValue() {
  return moralityTotalValue;
 }

 public void setMoralityTotalValue(int moralityTotalValue) {
  this.moralityTotalValue = moralityTotalValue;
 }

 public int getMoralityRankValue() {
  return moralityRankValue;
 }

 public void setMoralityRankValue(int moralityRankValue) {
  this.moralityRankValue = moralityRankValue;
 }

 public float getHistoryTotalScore() {
  return historyTotalScore;
 }

 public void setHistoryTotalScore(float historyTotalScore) {
  this.historyTotalScore = historyTotalScore;
 }

 public float getHistoryAveageScore() {
  return historyAveageScore;
 }

 public void setHistoryAveageScore(float historyAveageScore) {
  this.historyAveageScore = historyAveageScore;
 }

 public int getHistoryAveageValue() {
  return historyAveageValue;
 }

 public void setHistoryAveageValue(int historyAveageValue) {
  this.historyAveageValue = historyAveageValue;
 }

 public float getHistoryGoodRate() {
  return historyGoodRate;
 }

 public void setHistoryGoodRate(float historyGoodRate) {
  this.historyGoodRate = historyGoodRate;
 }

 public int getHistoryGoodValue() {
  return historyGoodValue;
 }

 public void setHistoryGoodValue(int historyGoodValue) {
  this.historyGoodValue = historyGoodValue;
 }

 public int getHistoryGoodStuNum() {
  return historyGoodStuNum;
 }

 public void setHistoryGoodStuNum(int historyGoodStuNum) {
  this.historyGoodStuNum = historyGoodStuNum;
 }

 public float getHistoryPassRate() {
  return historyPassRate;
 }

 public void setHistoryPassRate(float historyPassRate) {
  this.historyPassRate = historyPassRate;
 }

 public int getHistoryPassValue() {
  return historyPassValue;
 }

 public void setHistoryPassValue(int historyPassValue) {
  this.historyPassValue = historyPassValue;
 }

 public int getHistoryPassStuNum() {
  return historyPassStuNum;
 }

 public void setHistoryPassStuNum(int historyPassStuNum) {
  this.historyPassStuNum = historyPassStuNum;
 }

 public int getHistoryTotalValue() {
  return historyTotalValue;
 }

 public void setHistoryTotalValue(int historyTotalValue) {
  this.historyTotalValue = historyTotalValue;
 }

 public int getHistoryRankValue() {
  return historyRankValue;
 }

 public void setHistoryRankValue(int historyRankValue) {
  this.historyRankValue = historyRankValue;
 }

 public float getGeoTotalScore() {
  return geoTotalScore;
 }

 public void setGeoTotalScore(float geoTotalScore) {
  this.geoTotalScore = geoTotalScore;
 }

 public float getGeoAveageScore() {
  return geoAveageScore;
 }

 public void setGeoAveageScore(float geoAveageScore) {
  this.geoAveageScore = geoAveageScore;
 }

 public int getGeoAveageValue() {
  return geoAveageValue;
 }

 public void setGeoAveageValue(int geoAveageValue) {
  this.geoAveageValue = geoAveageValue;
 }

 public float getGeoGoodRate() {
  return geoGoodRate;
 }

 public void setGeoGoodRate(float geoGoodRate) {
  this.geoGoodRate = geoGoodRate;
 }

 public int getGeoGoodValue() {
  return geoGoodValue;
 }

 public void setGeoGoodValue(int geoGoodValue) {
  this.geoGoodValue = geoGoodValue;
 }

 public int getGeoGoodStuNum() {
  return geoGoodStuNum;
 }

 public void setGeoGoodStuNum(int geoGoodStuNum) {
  this.geoGoodStuNum = geoGoodStuNum;
 }

 public float getGeoPassRate() {
  return geoPassRate;
 }

 public void setGeoPassRate(float geoPassRate) {
  this.geoPassRate = geoPassRate;
 }

 public int getGeoPassValue() {
  return geoPassValue;
 }

 public void setGeoPassValue(int geoPassValue) {
  this.geoPassValue = geoPassValue;
 }

 public int getGeoPassStuNum() {
  return geoPassStuNum;
 }

 public void setGeoPassStuNum(int geoPassStuNum) {
  this.geoPassStuNum = geoPassStuNum;
 }

 public int getGeoTotalValue() {
  return geoTotalValue;
 }

 public void setGeoTotalValue(int geoTotalValue) {
  this.geoTotalValue = geoTotalValue;
 }

 public int getGeoRankValue() {
  return geoRankValue;
 }

 public void setGeoRankValue(int geoRankValue) {
  this.geoRankValue = geoRankValue;
 }

 public float getBioTotalScore() {
  return bioTotalScore;
 }

 public void setBioTotalScore(float bioTotalScore) {
  this.bioTotalScore = bioTotalScore;
 }

 public float getBioAveageScore() {
  return bioAveageScore;
 }

 public void setBioAveageScore(float bioAveageScore) {
  this.bioAveageScore = bioAveageScore;
 }

 public int getBioAveageValue() {
  return bioAveageValue;
 }

 public void setBioAveageValue(int bioAveageValue) {
  this.bioAveageValue = bioAveageValue;
 }

 public float getBioGoodRate() {
  return bioGoodRate;
 }

 public void setBioGoodRate(float bioGoodRate) {
  this.bioGoodRate = bioGoodRate;
 }

 public int getBioGoodValue() {
  return bioGoodValue;
 }

 public void setBioGoodValue(int bioGoodValue) {
  this.bioGoodValue = bioGoodValue;
 }

 public int getBioGoodStuNum() {
  return bioGoodStuNum;
 }

 public void setBioGoodStuNum(int bioGoodStuNum) {
  this.bioGoodStuNum = bioGoodStuNum;
 }

 public float getBioPassRate() {
  return bioPassRate;
 }

 public void setBioPassRate(float bioPassRate) {
  this.bioPassRate = bioPassRate;
 }

 public int getBioPassValue() {
  return bioPassValue;
 }

 public void setBioPassValue(int bioPassValue) {
  this.bioPassValue = bioPassValue;
 }

 public int getBioPassStuNum() {
  return bioPassStuNum;
 }

 public void setBioPassStuNum(int bioPassStuNum) {
  this.bioPassStuNum = bioPassStuNum;
 }

 public int getBioTotalValue() {
  return bioTotalValue;
 }

 public void setBioTotalValue(int bioTotalValue) {
  this.bioTotalValue = bioTotalValue;
 }

 public int getBioRankValue() {
  return bioRankValue;
 }

 public void setBioRankValue(int bioRankValue) {
  this.bioRankValue = bioRankValue;
 }

 public float getPhyTotalScore() {
  return phyTotalScore;
 }

 public void setPhyTotalScore(float phyTotalScore) {
  this.phyTotalScore = phyTotalScore;
 }

 public float getPhyAveageScore() {
  return phyAveageScore;
 }

 public void setPhyAveageScore(float phyAveageScore) {
  this.phyAveageScore = phyAveageScore;
 }

 public int getPhyAveageValue() {
  return phyAveageValue;
 }

 public void setPhyAveageValue(int phyAveageValue) {
  this.phyAveageValue = phyAveageValue;
 }

 public float getPhyGoodRate() {
  return phyGoodRate;
 }

 public void setPhyGoodRate(float phyGoodRate) {
  this.phyGoodRate = phyGoodRate;
 }

 public int getPhyGoodValue() {
  return phyGoodValue;
 }

 public void setPhyGoodValue(int phyGoodValue) {
  this.phyGoodValue = phyGoodValue;
 }

 public int getPhyGoodStuNum() {
  return phyGoodStuNum;
 }

 public void setPhyGoodStuNum(int phyGoodStuNum) {
  this.phyGoodStuNum = phyGoodStuNum;
 }

 public float getPhyPassRate() {
  return phyPassRate;
 }

 public void setPhyPassRate(float phyPassRate) {
  this.phyPassRate = phyPassRate;
 }

 public int getPhyPassValue() {
  return phyPassValue;
 }

 public void setPhyPassValue(int phyPassValue) {
  this.phyPassValue = phyPassValue;
 }

 public int getPhyPassStuNum() {
  return phyPassStuNum;
 }

 public void setPhyPassStuNum(int phyPassStuNum) {
  this.phyPassStuNum = phyPassStuNum;
 }

 public int getPhyTotalValue() {
  return phyTotalValue;
 }

 public void setPhyTotalValue(int phyTotalValue) {
  this.phyTotalValue = phyTotalValue;
 }

 public int getPhyRankValue() {
  return phyRankValue;
 }

 public void setPhyRankValue(int phyRankValue) {
  this.phyRankValue = phyRankValue;
 }

 public float getChemistryTotalScore() {
  return chemistryTotalScore;
 }

 public void setChemistryTotalScore(float chemistryTotalScore) {
  this.chemistryTotalScore = chemistryTotalScore;
 }

 public float getChemistryAveageScore() {
  return chemistryAveageScore;
 }

 public void setChemistryAveageScore(float chemistryAveageScore) {
  this.chemistryAveageScore = chemistryAveageScore;
 }

 public int getChemistryAveageValue() {
  return chemistryAveageValue;
 }

 public void setChemistryAveageValue(int chemistryAveageValue) {
  this.chemistryAveageValue = chemistryAveageValue;
 }

 public float getChemistryGoodRate() {
  return chemistryGoodRate;
 }

 public void setChemistryGoodRate(float chemistryGoodRate) {
  this.chemistryGoodRate = chemistryGoodRate;
 }

 public int getChemistryGoodValue() {
  return chemistryGoodValue;
 }

 public void setChemistryGoodValue(int chemistryGoodValue) {
  this.chemistryGoodValue = chemistryGoodValue;
 }

 public int getChemistryGoodStuNum() {
  return chemistryGoodStuNum;
 }

 public void setChemistryGoodStuNum(int chemistryGoodStuNum) {
  this.chemistryGoodStuNum = chemistryGoodStuNum;
 }

 public float getChemistryPassRate() {
  return chemistryPassRate;
 }

 public void setChemistryPassRate(float chemistryPassRate) {
  this.chemistryPassRate = chemistryPassRate;
 }

 public int getChemistryPassValue() {
  return chemistryPassValue;
 }

 public void setChemistryPassValue(int chemistryPassValue) {
  this.chemistryPassValue = chemistryPassValue;
 }

 public int getChemistryPassStuNum() {
  return chemistryPassStuNum;
 }

 public void setChemistryPassStuNum(int chemistryPassStuNum) {
  this.chemistryPassStuNum = chemistryPassStuNum;
 }

 public int getChemistryTotalValue() {
  return chemistryTotalValue;
 }

 public void setChemistryTotalValue(int chemistryTotalValue) {
  this.chemistryTotalValue = chemistryTotalValue;
 }

 public int getChemistryRankValue() {
  return chemistryRankValue;
 }

 public void setChemistryRankValue(int chemistryRankValue) {
  this.chemistryRankValue = chemistryRankValue;
 }

 public int getClassTotalValue() {
  return classTotalValue;
 }

 public void setClassTotalValue(int classTotalValue) {
  this.classTotalValue = classTotalValue;
 }

 public int getClassTotalRank() {
  return classTotalRank;
 }

 public void setClassTotalRank(int classTotalRank) {
  this.classTotalRank = classTotalRank;
 }
}
