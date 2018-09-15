package warron.phpprojectandroid.VC.FragHome;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import jxl.write.WriteException;
import warron.phpprojectandroid.Base.BaseFragment;
import warron.phpprojectandroid.R;

public class FragHome extends BaseFragment implements View.OnClickListener{
    View view;
    EditText et_url;
    Button btOpen;
    Button btUrl;
    Button btnHomeSetting;
    Button btnHomeExportStatistics;
    Button btnHomeExportAll;
    String fileUrl;
    File file ;
    String fileName;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, null);
        et_url = view.findViewById(R.id.et_url);
        btOpen = view.findViewById(R.id.bt_open);
        btOpen.setOnClickListener(this);

        btUrl = view.findViewById(R.id.bt_url);
        btUrl.setOnClickListener(this);

        btnHomeSetting = view.findViewById(R.id.btn_home_setting);
        btnHomeSetting.setOnClickListener(this);

        btnHomeExportStatistics = view.findViewById(R.id.btn_home_exportStatistics);
        btnHomeExportStatistics.setOnClickListener(this);

        btnHomeExportAll = view.findViewById(R.id.btn_home_ExportAll);
        btnHomeExportAll.setOnClickListener(this);

        String sdStatus = Environment.getExternalStorageState();
        if (sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            //创建本地文件夹 写入外置内存卡
            File directory = new File(Environment.getExternalStorageDirectory(), getResources().getString(R.string.app_name));
            if (!directory.exists())
                directory.mkdirs();//这里用这个好一些
        }
        GradeFactory.getInstance(getContext()).initArrModel(fileUrl);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_url:{
                this.chooseFilePath(); /*选择文件路径*/
            }break;
            case R.id.bt_open:{
                this.openFile();   /*打开文件*/
            }break;

            case R.id.btn_home_setting: {
                try {
                    GradeFactory.getInstance(getContext()).generateAllStuRank();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }break;

            case R.id.btn_home_exportStatistics: {
                try {
                    GradeFactory.getInstance(getContext()).genearteClassRankTable();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }break;

            case R.id.btn_home_ExportAll: {
                try {
                    GradeFactory.getInstance(getContext()).genearteAllStuInClassRankTables();
                } catch (WriteException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }break;

        }
    }
    private void parseExcel(){

    }

    public  void chooseFilePath(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }
    public  void  openFile(){
        this.parseExcel();
//        if (fileUrl!= null) {
//            try {
//                File file = new File(fileUrl);
//                Intent intent2 = new Intent("android.intent.action.VIEW");
//                intent2.addCategory("android.intent.category.DEFAULT");
//                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Uri uri = Uri.fromFile(file);
//                //application/msword
//                //application/vnd.ms-excel
//                if (fileUrl.contains(".docx")){
//                    intent2.setDataAndType(uri, "application/msword");
//                }else if (fileUrl.contains(".xlsx")){
//                    intent2.setDataAndType(uri, "application/vnd.ms-excel");
//                }else {
//                    intent2.setDataAndType(uri, "text/plain");
//                }
//                startActivity(intent2);
//            } catch (Exception e) {
//                //没有安装第三方的软件会提示
//                Toast toast = Toast.makeText(getActivity(), "没有找到打开该文件的应用程序", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//
//            // Intent intent2 = new Intent();
//            // intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //  intent2.setAction(Intent.ACTION_VIEW);
//            //  intent2.setDataAndType((Uri) etUrl.getText(), "text/plain");
//            //   startActivity(intent2);
//        } else {
//            Toast.makeText(getActivity(), "请选择或输入文件路径", Toast.LENGTH_SHORT).show();
//            return;
//        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { /*接收到刚才选择的文件路径*/
        if (resultCode == Activity.RESULT_OK) {

            if (data == null) {
                return;
            }
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            if(!uri.getPath().equals(fileUrl)){//判断是否第二次选择文件
                file=null;
            }
            //获取到选中文件的路径
            fileUrl = uri.getPath();
            //判断是否是外部打开
            if(fileUrl.contains("external")){
                isExternal(uri);
            }
            //获取的是否是真实路径
            if(file==null){
                isWhetherTruePath(uri);
            }
            //如果前面都获取不到文件，则自己拼接路径
            if(file==null){
                splicingPath(uri);
            }

            et_url.setText(fileUrl.toString());
        }
    }

    private void isExternal(Uri uri){ //拿到文件外部路径，通过外部路径遍历出真实路径
        Log.i("hxl", "获取文件的路径filePath========="+fileUrl);
        Log.i("hxl", "===调用外部遍历出路径方法===");
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor actualimagecursor = getActivity().managedQuery(uri,proj,null,null,null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        file = new File(img_path);
//        Log.i("hxl", "file========="+file);
        fileUrl=file.getAbsolutePath();
        if(!fileUrl.endsWith(".xls")){
            Toast.makeText(getActivity(), "您选中的文件不是Word文档", Toast.LENGTH_LONG).show();
            fileUrl=null;
            return;
        }

    }
    /**
     * 判断打开文件的是那种类型
     * @param uri
     */
    private void isWhetherTruePath(Uri uri){
        try {
            Log.i("hxl", "获取文件的路径filePath========="+fileUrl);
            if (fileUrl != null) {
                if (fileUrl.endsWith(".xls")) {
                    if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                        fileUrl = getPath(getActivity(), uri);
                        Log.i("hxl", "===调用第三方应用打开===");
                        fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                        file = new File(fileUrl);
                    }
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                        Log.i("hxl", "===调用4.4以后系统方法===");
                        fileUrl = getRealPathFromURI(uri);
                        fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                        file = new File(fileUrl);
                    } else {//4.4以下系统调用方法
                        fileUrl = getRealPathFromURI(uri);
                        Log.i("hxl", "===调用4.4以下系统方法===");
                        fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                        file = new File(fileUrl);
                    }
                } else {
                    Toast.makeText(getActivity(), "您选中的文件格式不是Word文档", Toast.LENGTH_LONG).show();
                }
//                Log.i("hxl", "file========="+file);
            }else{

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 如果前面两种都获取不到文件
     * 则使用此种方法拼接路径
     * 此方法在Andorid7.0系统中可用
     */
    private void splicingPath(Uri uri){
        Log.i("hxl", "获取文件的路径filePath========="+fileUrl);
        if(fileUrl.endsWith(".xls")){
            Log.i("hxl", "===调用拼接路径方法===");
            String string =uri.toString();
            String a[]=new String[2];
            //判断文件是否在sd卡中
            if (string.indexOf(String.valueOf(Environment.getExternalStorageDirectory()))!=-1){
                //对Uri进行切割
                a = string.split(String.valueOf(Environment.getExternalStorageDirectory()));
                //获取到file
                file = new File(Environment.getExternalStorageDirectory(),a[1]);
            }else if(string.indexOf(String.valueOf(Environment.getDataDirectory()))!=-1) { //判断文件是否在手机内存中
                //对Uri进行切割
                a = string.split(String.valueOf(Environment.getDataDirectory()));
                //获取到file
                file = new File(Environment.getDataDirectory(), a[1]);
            }
        }else{
            Toast.makeText(getActivity(), "您选中的文件不是Word文档", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }

            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    public String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    //获取文件的真实路径
    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


}
