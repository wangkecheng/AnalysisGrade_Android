package warron.phpprojectandroid.VC.FragHome.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * onCreated = "sql"：当第一次创建表需要插入数据时候在此写sql语句
 */
@Table(name = "userInfoModel")
public class UserInfoModel {
    public UserInfoModel(){

    }
    //并不是所有的实体对象都快可以通过这种方式去存储，
    //一定要保证对象的类型中有int类型的id或者_id的属性，
    //这就对应数据库表中的主键字段。如果类型中没有id字段，
    //可以通过@Id注解去指定一个int类型的字段作为主键。
    //如果表中的又字段不想被存储在数据库中，
    //也可以通过@Transient去实现忽略。
    // 如果直接存储一个对象的列表，这样也是被允许的，达到批量存储的目的。

    /**
     * name = "keyId"：数据库表中的一个字段
     * isId = true：是否是主键
     * autoGen = true：是否自动增长  似乎不能用
     * property = "NOT NULL"：添加约束 似乎不能用
     */

    @Column(name = "id",isId = true)
  public long id; //这个作为数据库中数据的唯一标识 这个标识的值 等于  keyId的int值

    @Column(name = "keyId")
  public String keyId;

    @Column(name = "hasConfiguration")
  public int hasConfiguration;//是否配置过 初次创建是没有的 只要存入数据库都有值

    @Column(name = "allProjectSumScore")
  public int allProjectSumScore;

    @Column(name = "sumScoreChn")
  public int sumScoreChn;

    @Column(name = "sumScoreMath")
  public int sumScoreMath;

    @Column(name = "sumScoreEng")
  public int sumScoreEng;

    @Column(name = "sumScorePlitical")
  public int sumScorePlitical;

    @Column(name = "sumScoreHistory")
  public int sumScoreHistory;

    @Column(name = "sumScoreGrography")
  public int sumScoreGrography;

    @Column(name = "sumScorePhysical")
  public int sumScorePhysical;

    @Column(name = "sumScoreBiology")
  public int sumScoreBiology;

    @Column(name = "sumScoreChimistry")
  public int sumScoreChimistry;

    @Column(name = "totalScoreLbl")
  public int totalScoreLbl;
    @Column(name = "goodRate")
  public float goodRate;//优生率
    @Column(name = "passRate")
  public float passRate;//及格率
    @Column(name = "difficultyRate")
  public float difficultyRate;//学困率

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
    this.id = Long.parseLong(keyId);//这里做一个强制转换
  }
  public int getHasConfiguration() {
    return hasConfiguration;
  }

  public void setHasConfiguration(int hasConfiguration) {
    this.hasConfiguration = hasConfiguration;
  }

  public int getAllProjectSumScore() {
    return allProjectSumScore;
  }

  public void setAllProjectSumScore(int allProjectSumScore) {
    this.allProjectSumScore = allProjectSumScore;
  }

  public int getSumScoreChn() {
    return sumScoreChn;
  }

  public void setSumScoreChn(int sumScoreChn) {
    this.sumScoreChn = sumScoreChn;
  }

  public int getSumScoreMath() {
    return sumScoreMath;
  }

  public void setSumScoreMath(int sumScoreMath) {
    this.sumScoreMath = sumScoreMath;
  }

  public int getSumScoreEng() {
    return sumScoreEng;
  }

  public void setSumScoreEng(int sumScoreEng) {
    this.sumScoreEng = sumScoreEng;
  }

  public int getSumScorePlitical() {
    return sumScorePlitical;
  }

  public void setSumScorePlitical(int sumScorePlitical) {
    this.sumScorePlitical = sumScorePlitical;
  }

  public int getSumScoreHistory() {
    return sumScoreHistory;
  }

  public void setSumScoreHistory(int sumScoreHistory) {
    this.sumScoreHistory = sumScoreHistory;
  }

  public int getSumScoreGrography() {
    return sumScoreGrography;
  }

  public void setSumScoreGrography(int sumScoreGrography) {
    this.sumScoreGrography = sumScoreGrography;
  }

  public int getSumScorePhysical() {
    return sumScorePhysical;
  }

  public void setSumScorePhysical(int sumScorePhysical) {
    this.sumScorePhysical = sumScorePhysical;
  }

  public int getSumScoreBiology() {
    return sumScoreBiology;
  }

  public void setSumScoreBiology(int sumScoreBiology) {
    this.sumScoreBiology = sumScoreBiology;
  }

  public int getSumScoreChimistry() {
    return sumScoreChimistry;
  }

  public void setSumScoreChimistry(int sumScoreChimistry) {
    this.sumScoreChimistry = sumScoreChimistry;
  }

  public int getTotalScoreLbl() {
    return totalScoreLbl;
  }

  public void setTotalScoreLbl(int totalScoreLbl) {
    this.totalScoreLbl = totalScoreLbl;
  }

  public float getGoodRate() {
    return goodRate;
  }

  public void setGoodRate(float goodRate) {
    this.goodRate = goodRate;
  }

  public float getPassRate() {
    return passRate;
  }

  public void setPassRate(float passRate) {
    this.passRate = passRate;
  }

  public float getDifficultyRate() {
    return difficultyRate;
  }

  public void setDifficultyRate(float difficultyRate) {
    this.difficultyRate = difficultyRate;
  }
}
