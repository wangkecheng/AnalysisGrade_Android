package warron.phpprojectandroid.VC.FragHome.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * onCreated = "sql"：当第一次创建表需要插入数据时候在此写sql语句
 */
@Table(name = "userInfoModel", onCreated = "CREATE UNIQUE INDEX index_name ON userInfoModel(id)")
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
  public String hasConfiguration;//是否配置过 初次创建是没有的 只要存入数据库都有值

    @Column(name = "allProjectSumScore")
  public String allProjectSumScore;

    @Column(name = "sumScoreChn")
  public String sumScoreChn;

    @Column(name = "sumScoreMath")
  public String sumScoreMath;

    @Column(name = "sumScoreEng")
  public String sumScoreEng;

    @Column(name = "sumScorePlitical")
  public String sumScorePlitical;

    @Column(name = "sumScoreHistory")
  public String sumScoreHistory;

    @Column(name = "sumScoreGrography")
  public String sumScoreGrography;

    @Column(name = "sumScorePhysical")
  public String sumScorePhysical;

    @Column(name = "sumScoreBiology")
  public String sumScoreBiology;

    @Column(name = "sumScoreChimistry")
  public String sumScoreChimistry;

    @Column(name = "totalScoreLbl")
  public String totalScoreLbl;
    @Column(name = "goodRate")
  public String goodRate;//优生率
    @Column(name = "passRate")
  public String passRate;//及格率
    @Column(name = "difficultyRate")
  public String difficultyRate;//学困率

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
  }

  public String getHasConfiguration() {
    return hasConfiguration;
  }

  public void setHasConfiguration(String hasConfiguration) {
    this.hasConfiguration = hasConfiguration;
  }

  public String getAllProjectSumScore() {
    return allProjectSumScore;
  }

  public void setAllProjectSumScore(String allProjectSumScore) {
    this.allProjectSumScore = allProjectSumScore;
  }

  public String getSumScoreChn() {
    return sumScoreChn;
  }

  public void setSumScoreChn(String sumScoreChn) {
    this.sumScoreChn = sumScoreChn;
  }

  public String getSumScoreMath() {
    return sumScoreMath;
  }

  public void setSumScoreMath(String sumScoreMath) {
    this.sumScoreMath = sumScoreMath;
  }

  public String getSumScoreEng() {
    return sumScoreEng;
  }

  public void setSumScoreEng(String sumScoreEng) {
    this.sumScoreEng = sumScoreEng;
  }

  public String getSumScorePlitical() {
    return sumScorePlitical;
  }

  public void setSumScorePlitical(String sumScorePlitical) {
    this.sumScorePlitical = sumScorePlitical;
  }

  public String getSumScoreHistory() {
    return sumScoreHistory;
  }

  public void setSumScoreHistory(String sumScoreHistory) {
    this.sumScoreHistory = sumScoreHistory;
  }

  public String getSumScoreGrography() {
    return sumScoreGrography;
  }

  public void setSumScoreGrography(String sumScoreGrography) {
    this.sumScoreGrography = sumScoreGrography;
  }

  public String getSumScorePhysical() {
    return sumScorePhysical;
  }

  public void setSumScorePhysical(String sumScorePhysical) {
    this.sumScorePhysical = sumScorePhysical;
  }

  public String getSumScoreBiology() {
    return sumScoreBiology;
  }

  public void setSumScoreBiology(String sumScoreBiology) {
    this.sumScoreBiology = sumScoreBiology;
  }

  public String getSumScoreChimistry() {
    return sumScoreChimistry;
  }

  public void setSumScoreChimistry(String sumScoreChimistry) {
    this.sumScoreChimistry = sumScoreChimistry;
  }

  public String getTotalScoreLbl() {
    return totalScoreLbl;
  }

  public void setTotalScoreLbl(String totalScoreLbl) {
    this.totalScoreLbl = totalScoreLbl;
  }

  public String getGoodRate() {
    return goodRate;
  }

  public void setGoodRate(String goodRate) {
    this.goodRate = goodRate;
  }

  public String getPassRate() {
    return passRate;
  }

  public void setPassRate(String passRate) {
    this.passRate = passRate;
  }

  public String getDifficultyRate() {
    return difficultyRate;
  }

  public void setDifficultyRate(String difficultyRate) {
    this.difficultyRate = difficultyRate;
  }
}
