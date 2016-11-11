package com.sudichina.ftwl.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.sudichina.ftwl.R;
import com.sudichina.ftwl.bean.CarOwnerBean;
import com.sudichina.ftwl.presenter.IdVerificationPresenter;
import com.sudichina.ftwl.utils.SPUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.iwf.photopicker.PhotoPicker;

/**
 * 车主认证页面
 */
public class IdVerificationActivity extends BaseActivity implements IIdVerificationView, View.OnClickListener {
    Dialog dialog;

    private EditText et_real_name;//用户名
    private EditText et_driving_licence;
    private Button btn_verify;
    private ImageView iv_driver;
    private ImageView iv_back;

    private IdVerificationPresenter idVerificationPresenter = new IdVerificationPresenter(this);
    private String driveLicensePath;

    private String accountDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_verification);

        accountDegree = (String) SPUtils.get(this, "accountDegree", "");

        initData();
        initView();
        initEvent();
    }

    @Override
    protected void initData() {
        //获取车主信息
        if (accountDegree.equals("1") || accountDegree.equals("3")) {
            idVerificationPresenter.getCarOwnerInfo();
        }
    }

    @Override
    protected void initView() {
        et_real_name = (EditText) findViewById(R.id.et_real_name);
        et_driving_licence = (EditText) findViewById(R.id.et_driving_licence);
        btn_verify = (Button) findViewById(R.id.btn_verify);
        iv_driver = (ImageView) findViewById(R.id.iv_driver);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        if (accountDegree.equals("1") || accountDegree.equals("3")) {
            btn_verify.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initEvent() {
        btn_verify.setOnClickListener(this);
        iv_driver.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_verify:
                idVerificationPresenter.verify();
                break;
            case R.id.iv_driver:
                showDialog_();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public String getRealName() {
        return et_real_name.getText().toString().trim();
    }

    @Override
    public String getDrivingLicence() {
        return et_driving_licence.getText().toString().trim();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

    @Override
    public void setVerifyBtnEnabled() {
        btn_verify.setEnabled(true);
    }

    @Override
    public void setVerifyBtnDisabled() {
        btn_verify.setEnabled(false);
    }

    //选择，打开相机，或者拍照
    private void showDialog_() {
        View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog,
                null);
        dialog = new Dialog(this, R.style.main_menu_animstyle);
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void on_click(View v) {
        switch (v.getId()) {
            case R.id.btn_gallery:
                System.out.println("lalala");
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);
                break;
            case R.id.btn_photograph:
                dispatchTakePictureIntent();
                break;
            case R.id.cancel:
                dialog.cancel();//取消弹出框的展示
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                for (int i = 0; i < photos.size(); i++) {
                    System.out.println("图片" + i + photos.get(i));
//                    clickedImageView.setImageURI(Uri.parse(photos.get(i)));
                    Picasso.with(this).load(new File(photos.get(i))).into(iv_driver);

                    driveLicensePath = photos.get(i);
                    System.out.println("driveLicenPath = " + driveLicensePath);
                }
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            clickedImageView.setImageBitmap(imageBitmap);
            System.out.println(data);
            setPic();

            if (new File(mCurrentPhotoPath).exists()) {
                System.out.println("拍的照片存在");
            } else {
                System.out.println("拍的照片不存在");
            }
            driveLicensePath = mCurrentPhotoPath;
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
//                ...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                galleryAddPic();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = iv_driver.getWidth();
        int targetH = iv_driver.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        iv_driver.setImageBitmap(bitmap);
    }

    private String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public String getDrivingLicencePath() {
        return driveLicensePath;
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void showDialogVerifying(String msg) {
        dialog = new Dialog(this, R.style.diyDialog);
        dialog.setTitle(msg);
        dialog.show();
    }

    @Override
    public void dismissDialogVerifying() {
        dialog.dismiss();
    }

    @Override
    public void setCarOwnerBean(CarOwnerBean carOwnerBean) {
        if (carOwnerBean != null) {
            et_real_name.setText(carOwnerBean.getRealName());
            et_real_name.setEnabled(false);
            String a = carOwnerBean.getDrivingLicense();
            et_driving_licence.setText(a.substring(0, 3) + "********" + a.substring(8, a.length()));
            et_driving_licence.setEnabled(false);
            Picasso.with(this).load(carOwnerBean.getDrivingLicensePath()).into(iv_driver);
        }
    }
}
