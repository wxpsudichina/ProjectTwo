package com.sudichina.ftwl.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sudichina.ftwl.R;
import com.sudichina.ftwl.bean.CarBean;
import com.sudichina.ftwl.bean.DicGroupBean;
import com.sudichina.ftwl.bean.DicValueBean;
import com.sudichina.ftwl.bean.MyCarBean;
import com.sudichina.ftwl.presenter.VehicleCertificationPresenter;
import com.sudichina.ftwl.utils.SPUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

public class VehicleCertificationActivity extends BaseActivity implements IVehicleCertificationView, View.OnClickListener {
    private static final String DEFAULT_VALUE_INPUT = "";

    Dialog dialog;//选择图片弹出框

    private ImageView iv_driver_act, iv_add;//驾驶证，行驶证,其他照片
    private EditText et_car_engine, edit_card_number, et_car_long, et_car_volume, et_car_model, et_car_square;//发动机号,车牌号
    private Button btn_save;//保存
    private TextView tv_car_first_letter;//车牌号首字母
    private String[] carnumber_one;//车牌号首字母
    private String[] car_model;//车型
    private ArrayAdapter<String> adapter;//定义适配器，加载弹出框中的数据
    private ProgressDialog mProgressDialog;//进度条，展示加载动画

    private VehicleCertificationPresenter vehicleCertificationPresenter = new VehicleCertificationPresenter(this);

    private List<String> carLongList = new ArrayList<>();//车长
    private List<Integer> carLongIdList = new ArrayList<>();//车长所对应的id

    List<DicGroupBean> dicGroupBeanList;
    List<DicValueBean> dicValueBeanList;

    //认证需要提交到服务器的数据
    private long id;//修改需要提交的数据
    private long accountId;
    private String carFirstLetter;
    private String cardNumber;
    private String carDicId;
    private String driveLicense;
    private String driveLicensePath;
    private String driveLicensePath2;

    private CarBean carBean;
    private MyCarBean myCarBean;

    private ImageView clickedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_certification);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            carBean = (CarBean) bundle.getSerializable("carBean");
        }

        initData();//加载数据
        initView();//获取控件
        initEvent();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_card_number://车牌号
                String edit_tv_number = edit_card_number.getText().toString();
                break;
            case R.id.et_car_engine://发动机型号
                String edit_tv_act = et_car_engine.getText().toString();
                break;
            case R.id.tv_car_first_letter://车牌号首字母
                showDialogCarFirstLetter();
                break;
            case R.id.edit_car_long://车长
                showDialogCarLong();//车长
                break;
            case R.id.edit_car_model://车型
                showDialogCarModel();
                break;
            case R.id.iv_driver_act://行驶证
                clickedImageView = iv_driver_act;
                showDialogGallery();
                break;
            case R.id.iv_add:
                clickedImageView = iv_add;
                showDialogGallery();
                break;
            case R.id.btn_save:
                if (carBean == null) {
                    vehicleCertificationPresenter.verify();
                } else {
                    vehicleCertificationPresenter.modifyVehicleInfo();
                }
                break;
        }
    }

    //选择车长
    private void showDialogCarLong() {
        final Dialog dialog = new Dialog(this, R.style.diyDialog);
        View view = getLayoutInflater().inflate(R.layout.pop_carnumber_one, null);

        ListView listView = (ListView) view.findViewById(R.id.pop_carnumberone_gv);
        TextView tv = (TextView) view.findViewById(R.id.tv_title_bar);
        tv.setText("请选择车长");
        adapter = new ArrayAdapter<String>(VehicleCertificationActivity.this, R.layout.pop_grid_item, R.id.pop_grid_item_tv, carLongList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_car_long.setText(carLongList.get(position));

                //重置面积载重
                et_car_square.setText(DEFAULT_VALUE_INPUT);
                et_car_volume.setText(DEFAULT_VALUE_INPUT);

                int dicGroupId = carLongIdList.get(position);
                carDicId = String.valueOf(dicGroupId);
                System.out.println("dicGroupId ======== " + dicGroupId);

//                List<DicValueBean> dicValueBeanList = DataSupport.where("dicGroupId = ?", String.valueOf(dicGroupId)).find(DicValueBean.class);
                List<DicValueBean> w = new ArrayList<DicValueBean>();
                for (int i = 0; i < dicValueBeanList.size(); i++) {
                    DicValueBean dicValueBean = dicValueBeanList.get(i);
                    if (dicValueBean.getDicGroupId() == dicGroupId) {
                        w.add(dicValueBean);
                    }
                }

                System.out.println(w.size());
                for (int i = 0; i < w.size(); i++) {
                    DicValueBean dicValueBean = w.get(i);
                    String paraCode = dicValueBean.getParaCode();
                    if (paraCode.equals("car_load_dun")) {
                        //体积
                        System.out.println("car_load_dun = " + dicValueBean.getParaName());
                        et_car_volume.setText(dicValueBean.getParaName());
                    } else if (paraCode.equals("car_load_square")) {//面积
                        System.out.println("car_load_square = " + dicValueBean.getParaName());
                        et_car_square.setText(dicValueBean.getParaName());
                    }
                }
                dialog.dismiss();
            }
        });
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        dialog.setContentView(view);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setWindowAnimations(R.style.mystyle);
        lp.width = outMetrics.widthPixels / 2;
        lp.height = outMetrics.heightPixels;
        lp.gravity = Gravity.RIGHT;
        dialog.show();

    }

    //弹出车型
    private void showDialogCarModel() {
        final Dialog dialog = new Dialog(this, R.style.diyDialog);
        View view = getLayoutInflater().inflate(R.layout.pop_carnumber_one, null);
        final ListView listView = (ListView) view.findViewById(R.id.pop_carnumberone_gv);
        TextView tv = (TextView) view.findViewById(R.id.tv_title_bar);
        tv.setText("请选择车型");
        adapter = new ArrayAdapter<String>(VehicleCertificationActivity.this, R.layout.pop_grid_item, R.id.pop_grid_item_tv, car_model);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_car_model.setText(car_model[position]);
                //重置
                et_car_long.setText(DEFAULT_VALUE_INPUT);
                et_car_square.setText(DEFAULT_VALUE_INPUT);
                et_car_volume.setText(DEFAULT_VALUE_INPUT);

                switch (position) {
                    case 0:
                        //"van_vehicle";
                        getDicCode("van_vehicle");
                        break;
                    case 1:
                        //"flat_vehicle"
                        getDicCode("flat_vehicle");
                        break;
                    case 2://hl_vehicle
                        getDicCode("hl_vehicle");

                        break;
                    case 3://gaolan_vehicle
                        getDicCode("gaolan_vehicle");
                        break;

                    case 4://midlan_vehicle
                        getDicCode("midlan_vehicle");
                        break;

                    case 5://lowlan_vehicle
                        getDicCode("lowlan_vehicle");
                        break;
                }
                showDialogCarLong();
                dialog.dismiss();
            }
        });
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        dialog.setContentView(view);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setWindowAnimations(R.style.mystyle);
        lp.width = outMetrics.widthPixels / 2;
        lp.height = outMetrics.heightPixels;
        lp.gravity = Gravity.RIGHT;
        dialog.show();
    }

    private void getDicCode(String type) {
        List<DicGroupBean> b = new ArrayList<>();
        for (int i = 0; i < dicGroupBeanList.size(); i++) {
            DicGroupBean dicGroupBean = dicGroupBeanList.get(i);
            if (dicGroupBean.getGroupCode().equals(type)) {
                b.add(dicGroupBean);
            }
        }

        carLongList.clear();
        carLongIdList.clear();
        for (int i = 0; i < b.size(); i++) {
            String dicCode = b.get(i).getDicCode() + "===" + b.get(i).getDicCode();
            carLongList.add(b.get(i).getDicCode());
            carLongIdList.add(b.get(i).getId());
        }
    }

    //弹出车牌号首字母
    private void showDialogCarFirstLetter() {
        final Dialog dialog = new Dialog(this, R.style.diyDialog);
        View view = getLayoutInflater().inflate(R.layout.pop_carnumber_one, null);

        ListView listView = (ListView) view.findViewById(R.id.pop_carnumberone_gv);
        TextView tv = (TextView) view.findViewById(R.id.tv_title_bar);
        tv.setText("请选择省份");
        adapter = new ArrayAdapter<String>(VehicleCertificationActivity.this, R.layout.pop_grid_item, R.id.pop_grid_item_tv, carnumber_one);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_car_first_letter.setText(carnumber_one[position]);
                //重置
                et_car_model.setText(DEFAULT_VALUE_INPUT);
                et_car_long.setText(DEFAULT_VALUE_INPUT);
                et_car_square.setText(DEFAULT_VALUE_INPUT);
                et_car_volume.setText(DEFAULT_VALUE_INPUT);

                showDialogCarModel();//接着弹出车型
                dialog.dismiss();
            }
        });
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        dialog.setContentView(view);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setWindowAnimations(R.style.mystyle);
        lp.width = outMetrics.widthPixels / 2;
        lp.height = outMetrics.heightPixels;
        lp.gravity = Gravity.RIGHT;
        dialog.show();
    }

    public void on_click(View v) {
        switch (v.getId()) {
            case R.id.btn_gallery:
                System.out.println("lalala");
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);
                break;
            case R.id.btn_photograph:
                dispatchTakePictureIntent();
                break;
            case R.id.cancel:
                dialog.cancel();//取消弹出框的展示
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                for (int i = 0; i < photos.size(); i++) {
                    System.out.println("图片" + i + photos.get(i));
//                    clickedImageView.setImageURI(Uri.parse(photos.get(i)));
                    Picasso.with(this).load(new File(photos.get(i))).into(clickedImageView);

                    if (clickedImageView == iv_driver_act) {
                        driveLicensePath = photos.get(i);
                        System.out.println("driveLicenPath = " + driveLicensePath);
                    } else if (clickedImageView == iv_add) {
                        driveLicensePath2 = photos.get(i);
                        System.out.println("driveLicenPath2 = " + driveLicensePath2);
                    }
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

            if (clickedImageView == iv_driver_act) {
                driveLicensePath = mCurrentPhotoPath;
                System.out.println("driveLicenPath = " + driveLicensePath);
            } else if (clickedImageView == iv_add) {
                driveLicensePath2 = mCurrentPhotoPath;
                System.out.println("driveLicenPath2 = " + driveLicensePath2);
            }
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
        int targetW = clickedImageView.getWidth();
        int targetH = clickedImageView.getHeight();

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
        clickedImageView.setImageBitmap(bitmap);
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
    protected void initData() {
        vehicleCertificationPresenter.getCarInfo();

        carnumber_one = new String[]{"京", "沪", "津", "渝", "冀", "晋", "蒙", "辽", "吉", "黑", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "桂", "琼", "川", "贵", "云", "藏", "陕", "甘", "青", "宁", "新"};//展示车牌号首字母
        car_model = new String[]{"厢式车", "平板车", "高低板车", "高栏车", "中栏车", "低栏车"};
    }

    //获取控件id
    @Override
    protected void initView() {
        tv_car_first_letter = (TextView) findViewById(R.id.tv_car_first_letter);//车牌号首字母
        et_car_long = (EditText) findViewById(R.id.edit_car_long);//车长
        et_car_volume = (EditText) findViewById(R.id.edit_car_volume); //载重
        et_car_model = (EditText) findViewById(R.id.edit_car_model);//车型
        et_car_engine = (EditText) findViewById(R.id.et_car_engine);//发动机型号
        edit_card_number = (EditText) findViewById(R.id.edit_card_number);//车牌号
        et_car_square = (EditText) findViewById(R.id.edit_car_area);//面积
        iv_driver_act = (ImageView) findViewById(R.id.iv_driver_act);//行驶证照片
        iv_add = (ImageView) findViewById(R.id.iv_add);//其他照片
        btn_save = (Button) findViewById(R.id.btn_save);//保存
    }

    @Override
    protected void initEvent() {
        tv_car_first_letter.setOnClickListener(this);
        edit_card_number.setOnClickListener(this);
        et_car_engine.setOnClickListener(this);
        et_car_volume.setOnClickListener(this);
        et_car_long.setOnClickListener(this);
        et_car_model.setOnClickListener(this);
        iv_driver_act.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    //展示选择图片的弹出框
    private void showDialogGallery() {
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

    @Override
    public void showProgressDialog(String msg) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(msg);
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public String getCarFirstLetter() {
        return tv_car_first_letter.getText().toString().trim();
    }

    @Override
    public String getCardNumber() {
        return edit_card_number.getText().toString().trim();
    }

    @Override
    public String getCarDicId() {
        return carDicId;
    }

    @Override
    public String getEngineNum() {
        return et_car_engine.getText().toString().trim();
    }

    @Override
    public String getDriveLicensePath() {
        return driveLicensePath;
    }

    @Override
    public String getDriveLicensePath2() {
        return driveLicensePath2;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setCarInfoData(List<DicGroupBean> dicGroupBeanList, List<DicValueBean> dicValueBeanList) {
        this.dicGroupBeanList = dicGroupBeanList;
        this.dicValueBeanList = dicValueBeanList;
        System.out.println("size == =" + dicGroupBeanList.size());
        System.out.println("size === " + dicValueBeanList.size());
    }

    @Override
    public CarBean getCarBean() {
        return carBean;
    }

    @Override
    public void setMyCarBean(MyCarBean myCarBean) {
        System.out.println(myCarBean);

        CarBean carBean = myCarBean.getCar();
        DicGroupBean dicGroup = myCarBean.getDicGroup();
        List<DicValueBean> dicValueBeanList = myCarBean.getDicValue();

        driveLicensePath = carBean.getDriveLicensePath();
        System.out.println("driveLicensePath = " + driveLicensePath);
        driveLicensePath2 = carBean.getDriveLicensePath2();
        System.out.println("driveLicensePath2" + driveLicensePath2);

        System.out.println("driveLicensePath = " + this.carBean.getDriveLicensePath());
        System.out.println("driveLicensePath2" + this.carBean.getDriveLicensePath2());
//
        tv_car_first_letter.setText(carBean.getCarFirstLetter());
        edit_card_number.setText(carBean.getCardNumber());
        et_car_engine.setText(carBean.getEngineNum());
        carDicId = String.valueOf(carBean.getCarDicId());
        System.out.println("carDicId = " + carDicId);
        System.out.println("another carDicId = " + this.carBean.getCarDicId());
//
//        this.myCarInfoBean = myCarInfoBean;
//
        for (int i = 0; i < dicValueBeanList.size(); i++) {
            DicValueBean dicValueBean = dicValueBeanList.get(i);
            switch (dicValueBean.getParaCode()) {
                case "car_long":
                    et_car_long.setText(dicValueBean.getParaName());
                    break;
                case "car_load_dun":
                    et_car_volume.setText(dicValueBean.getParaName());
                    break;
                case "car_load_square":
                    et_car_square.setText(dicValueBean.getParaName());
                    break;
            }
        }
//
        String model = dicGroup.getGroupCode();
        switch (model) {
            case "van_vehicle":
                et_car_model.setText("厢式车");
                break;
            case "flat_vehicle":
                et_car_model.setText("平板车");
                break;
            case "hl_vehicle":
                et_car_model.setText("高板车");
                break;
            case "gaolan_vehicle":
                et_car_model.setText("高栏车");
                break;
            case "midlan_vehicle":
                et_car_model.setText("中栏车");
                break;
            case "lowlan_vehicle":
                et_car_model.setText("低栏车");
                break;
        }

        //设置图片
        Picasso.with(this).load(carBean.getDriveLicensePath()).into(iv_driver_act);
        Picasso.with(this).load(carBean.getDriveLicensePath2()).into(iv_add);

    }

    @Override
    public long getId() {
        return carBean.getId();
    }

    @Override
    public long getAccountId() {
        return (long) SPUtils.get(this, "id", 0L);
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
    }
}