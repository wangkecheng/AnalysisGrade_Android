package warron.phpprojectandroid.VC.FragHome;

public interface GradeFactoryInterface {
    //回调接口，相当于ios中的block
    void onProgress(int progress, boolean isFinsish);
    void alertInfo(String infoStr,boolean isSuccess);
}
