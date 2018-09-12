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
}
