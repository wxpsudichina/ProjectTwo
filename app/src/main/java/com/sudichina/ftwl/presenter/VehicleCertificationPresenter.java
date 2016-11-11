package com.sudichina.ftwl.presenter;

import android.os.Handler;

import com.sudichina.ftwl.bean.CarBean;
import com.sudichina.ftwl.bean.DicGroupBean;
import com.sudichina.ftwl.bean.DicValueBean;
import com.sudichina.ftwl.bean.MyCarBean;
import com.sudichina.ftwl.bean.MyCarInfoBean;
import com.sudichina.ftwl.biz.IVehicleCertificationBiz;
import com.sudichina.ftwl.biz.OnGetVerifiedVehicleInfoListener;
import com.sudichina.ftwl.biz.OnModifyVehicleInfoListener;
import com.sudichina.ftwl.biz.OnRequestCarInfoListener;
import com.sudichina.ftwl.biz.OnRequestListener;
import com.sudichina.ftwl.biz.VehicleCertificationBiz;
import com.sudichina.ftwl.utils.SPUtils;
import com.sudichina.ftwl.view.IVehicleCertificationView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by mccccccmike on 2016/8/30.
 */
public class VehicleCertificationPresenter {
    private IVehicleCertificationView vehicleCertificationView;
    private IVehicleCertificationBiz vehicleCertificationBiz;
    private Handler mHandler;

    public VehicleCertificationPresenter(IVehicleCertificationView vehicleCertificationView) {
        this.vehicleCertificationView = vehicleCertificationView;
        vehicleCertificationBiz = new VehicleCertificationBiz();
        mHandler = new Handler();
    }

    //获取所有车辆信息
    public void getCarInfo() {
        vehicleCertificationView.showProgressDialog("正在加载车辆数据");
        vehicleCertificationBiz.getCarInfo(new OnRequestCarInfoListener() {
            @Override
            public void requestCarInfoSuccess(final List<DicGroupBean> dicGroupBeanList, final List<DicValueBean> dicValueBeanList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        vehicleCertificationView.setCarInfoData(dicGroupBeanList, dicValueBeanList);
                        vehicleCertificationView.dismissProgressDialog();
                        getModifiedVehicleInfo(vehicleCertificationView.getCarBean());
                    }
                });
            }

            @Override
            public void requestCarInfoFailure() {

            }

            @Override
            public void requestFailed() {

            }

            @Override
            public void requestSuccess() {

            }
        });
    }

    private LinkedList<File> files;
    private ArrayList<File> res = new ArrayList<>();
    private Semaphore semaphore = new Semaphore(0);

    public void verify() {
        vehicleCertificationView.showProgressDialog("正在上传数据。。。");
        System.out.println("验证车辆信息");

        new Thread() {
            @Override
            public void run() {
                verifyVehicle();

//                System.out.println("我執行了嗎？");


            }
        }.start();

    }

    private void verifyVehicle() {
        System.out.println("认证的逻辑");

        if (vehicleCertificationView.getCarFirstLetter().equals("")) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    vehicleCertificationView.showToast("请选择车牌号首字母");
                }
            });
            return;
        }

        if (vehicleCertificationView.getDriveLicensePath() == null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    vehicleCertificationView.showToast("请上传行驶证");
                }
            });
            return;
        }

        if (vehicleCertificationView.getDriveLicensePath2() == null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    vehicleCertificationView.showToast("请上传驾驶证");
                }
            });
            return;
        }


        files = new LinkedList<>();
        files.add(new File(vehicleCertificationView.getDriveLicensePath()));
        files.add(new File(vehicleCertificationView.getDriveLicensePath2()));

        System.out.println("aaa" + vehicleCertificationView.getDriveLicensePath());
        System.out.println("bbb" + vehicleCertificationView.getDriveLicensePath2());
        if (files.size() > 0) {
            res.clear();
            recCompressFile(files.removeFirst());
        }
//
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("我執行了嗎？");
        System.out.println(res.get(0).getAbsolutePath());
        System.out.println(res.get(1).getAbsolutePath());


        if (res.get(0).exists()) {
            System.out.println("文件存在");
        } else {
            System.out.println("文件不存在");

        }
//
        vehicleCertificationBiz.verify(
                (long) SPUtils.get(vehicleCertificationView.getContext(), "id", 0L),
                vehicleCertificationView.getCarFirstLetter(),
                vehicleCertificationView.getCardNumber(),
                vehicleCertificationView.getCarDicId(),
                vehicleCertificationView.getEngineNum(),
                res.get(0).getAbsolutePath(),
                res.get(1).getAbsolutePath(),
                new OnRequestListener() {
                    @Override
                    public void requestSuccess() {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                vehicleCertificationView.dismissProgressDialog();
                            }
                        });
                    }

                    @Override
                    public void requestFailed() {

                    }
                }
        );
    }

    private void recCompressFile(File file) {
        Luban.get(vehicleCertificationView.getContext())
                .load(file)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        System.out.println("开始压缩");
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        System.out.println("压缩成功");
                        System.out.println("图片 --> " + file.getAbsolutePath());
                        File newPath = new File(file.getAbsolutePath() + ".jpg");
                        boolean a = file.renameTo(newPath);
//                        System.out.println("命名成功了吗" + a);
                        System.out.println("absolutePath = " + newPath.getAbsolutePath());
                        res.add(newPath);
                        if (files.size() > 0) {
                            System.out.println(files.size());
                            recCompressFile(files.removeFirst());
                            System.out.println(files.size());
                        }

                        if (res.size() == 2) {
                            semaphore.release();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用
                        System.out.println("压缩失败");
                    }
                }).launch();    //启动压缩
    }

    public void getModifiedVehicleInfo(CarBean carBean) {
        if (carBean == null) {
            return;
        }
        vehicleCertificationView.showProgressDialog("正在获取车辆信息");
//        CarBean carBean = vehicleCertificationView.getCarBean();


        vehicleCertificationBiz.getVerifiedVehicleInfo(carBean.getId(), new OnGetVerifiedVehicleInfoListener() {
            @Override
            public void getVerifiedVehicleInfoSuccess(final MyCarBean myCarBean) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        vehicleCertificationView.setMyCarBean(myCarBean);
//                        System.out.println("dicValueBeanList = " + dicValueBeanList.size());
                        vehicleCertificationView.dismissProgressDialog();
                    }
                });
            }

            @Override
            public void getVerifiedVehicleInfoFailed() {

            }

            @Override
            public void requestFailed() {

            }

            @Override
            public void requestSuccess() {

            }
        });
    }

    public void modifyVehicleInfo() {
        vehicleCertificationView.showProgressDialog("修改车辆信息中。。。");

        //压缩图片
        new Thread() {
            @Override
            public void run() {

                File f1 = new File(vehicleCertificationView.getDriveLicensePath());
                File f2 = new File(vehicleCertificationView.getDriveLicensePath2());

                if (f1.exists() && f2.exists()) {

                    files = new LinkedList<>();
                    files.add(f1);
                    files.add(f2);

                    System.out.println("aaa" + vehicleCertificationView.getDriveLicensePath());
                    System.out.println("bbb" + vehicleCertificationView.getDriveLicensePath2());
                    if (files.size() > 0) {
                        res.clear();
                        recCompressFile(files.removeFirst());
                    }
//
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("我執行了嗎？");
                    System.out.println(res.get(0).getAbsolutePath());
                    System.out.println(res.get(1).getAbsolutePath());

                    if (res.get(0).exists()) {
                        System.out.println("文件存在");
                    } else {
                        System.out.println("文件不存在");

                    }
//
                    vehicleCertificationBiz.verify(
                            (long) SPUtils.get(vehicleCertificationView.getContext(), "id", 0L),
                            vehicleCertificationView.getCarFirstLetter(),
                            vehicleCertificationView.getCardNumber(),
                            vehicleCertificationView.getCarDicId(),
                            vehicleCertificationView.getEngineNum(),
                            res.get(0).getAbsolutePath(),
                            res.get(1).getAbsolutePath(),
                            new OnRequestListener() {
                                @Override
                                public void requestSuccess() {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            vehicleCertificationView.dismissProgressDialog();
                                        }
                                    });
                                }

                                @Override
                                public void requestFailed() {

                                }
                            }
                    );
                }

                if (!f1.exists() && !f2.exists()) {
                    vehicleCertificationBiz.verify(
                            (long) SPUtils.get(vehicleCertificationView.getContext(), "id", 0L),
                            vehicleCertificationView.getCarFirstLetter(),
                            vehicleCertificationView.getCardNumber(),
                            vehicleCertificationView.getCarDicId(),
                            vehicleCertificationView.getEngineNum(),
                            vehicleCertificationView.getDriveLicensePath(),
                            vehicleCertificationView.getDriveLicensePath2(),
                            new OnRequestListener() {
                                @Override
                                public void requestSuccess() {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            vehicleCertificationView.dismissProgressDialog();
                                        }
                                    });
                                }

                                @Override
                                public void requestFailed() {

                                }
                            }
                    );
                }

                if (f1.exists() && !f2.exists()) {
                    //压缩f1
                    Luban.get(vehicleCertificationView.getContext())
                            .load(f1)                     //传人要压缩的图片
                            .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                            .setCompressListener(new OnCompressListener() { //设置回调
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                    System.out.println("开始压缩");
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                    System.out.println("压缩成功");
                                    System.out.println("图片 --> " + file.getAbsolutePath());
                                    File newPath = new File(file.getAbsolutePath() + ".jpg");
                                    if (file.renameTo(newPath)) {
                                        vehicleCertificationBiz.verify(
                                                (long) SPUtils.get(vehicleCertificationView.getContext(), "id", 0L),
                                                vehicleCertificationView.getCarFirstLetter(),
                                                vehicleCertificationView.getCardNumber(),
                                                vehicleCertificationView.getCarDicId(),
                                                vehicleCertificationView.getEngineNum(),
                                                newPath.getAbsolutePath(),
                                                vehicleCertificationView.getDriveLicensePath2(),
                                                new OnRequestListener() {
                                                    @Override
                                                    public void requestSuccess() {
                                                        mHandler.post(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                vehicleCertificationView.dismissProgressDialog();
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void requestFailed() {

                                                    }
                                                }
                                        );
                                    }
                                    System.out.println("absolutePath = " + newPath.getAbsolutePath());

                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过去出现问题时调用
                                    System.out.println("压缩失败");
                                }
                            }).launch();    //启动压缩

                }

                if (f2.exists() && !f1.exists()) {
                    //压缩f1
                    Luban.get(vehicleCertificationView.getContext())
                            .load(f2)                     //传人要压缩的图片
                            .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                            .setCompressListener(new OnCompressListener() { //设置回调
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                    System.out.println("开始压缩");
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                    System.out.println("压缩成功");
                                    System.out.println("图片 --> " + file.getAbsolutePath());
                                    File newPath = new File(file.getAbsolutePath() + ".jpg");
                                    if (file.renameTo(newPath)) {
                                        vehicleCertificationBiz.verify(
                                                (long) SPUtils.get(vehicleCertificationView.getContext(), "id", 0L),
                                                vehicleCertificationView.getCarFirstLetter(),
                                                vehicleCertificationView.getCardNumber(),
                                                vehicleCertificationView.getCarDicId(),
                                                vehicleCertificationView.getEngineNum(),
                                                vehicleCertificationView.getDriveLicensePath(),
                                                newPath.getAbsolutePath(),
                                                new OnRequestListener() {
                                                    @Override
                                                    public void requestSuccess() {
                                                        mHandler.post(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                vehicleCertificationView.dismissProgressDialog();
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void requestFailed() {

                                                    }
                                                }
                                        );
                                    }
                                    System.out.println("absolutePath = " + newPath.getAbsolutePath());

                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过去出现问题时调用
                                    System.out.println("压缩失败");
                                }
                            }).launch();    //启动压缩

                }


            }
        }.start();

    }

}
