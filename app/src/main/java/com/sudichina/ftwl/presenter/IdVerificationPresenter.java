package com.sudichina.ftwl.presenter;

import android.os.Handler;

import com.sudichina.ftwl.bean.CarOwnerBean;
import com.sudichina.ftwl.biz.IIdVerificationBiz;
import com.sudichina.ftwl.biz.IdVerificationBiz;
import com.sudichina.ftwl.biz.OnGetCarOwnerInfoListener;
import com.sudichina.ftwl.biz.OnIdVerifyListener;
import com.sudichina.ftwl.utils.SPUtils;
import com.sudichina.ftwl.view.IIdVerificationView;

import java.io.File;
import java.util.List;
import java.util.concurrent.Semaphore;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by mccccccmike on 2016/8/26.
 * 车主身份让认证
 */
public class IdVerificationPresenter {
    private IIdVerificationView idVerificationView;
    private IIdVerificationBiz idVerificationBiz;
    private Handler mHandler;

    public IdVerificationPresenter(IIdVerificationView idVerificationView) {
        this.idVerificationView = idVerificationView;
        idVerificationBiz = new IdVerificationBiz();
        mHandler = new Handler();
    }

    private File res;
    private Semaphore semaphore = new Semaphore(0);

    public void verify() {
        idVerificationView.showDialogVerifying("正在上传数据");
        idVerificationView.setVerifyBtnDisabled();

        new Thread() {
            @Override
            public void run() {
                recCompressFile(new File(idVerificationView.getDrivingLicencePath()));

                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (res.exists()) {
                    System.out.println("文件压缩成功");
                } else {
                    System.out.println("文件压缩失败");
                }
                System.out.println(res.getAbsolutePath());

                idVerificationBiz.verify(idVerificationView.getActivity(), idVerificationView.getRealName(), idVerificationView.getDrivingLicence(), idVerificationView.getDrivingLicencePath(), new OnIdVerifyListener() {
                    @Override
                    public void IdVerifySuccessful(final String msg) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                idVerificationView.showToast(msg);
                                idVerificationView.dismissDialogVerifying();
                                idVerificationView.setVerifyBtnEnabled();
                            }
                        });
                    }

                    @Override
                    public void IdVerifyFailed(final String msg) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                idVerificationView.showToast(msg);
                                idVerificationView.dismissDialogVerifying();
                                idVerificationView.setVerifyBtnEnabled();
                            }
                        });
                    }

                    @Override
                    public void requestFailed() {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                idVerificationView.dismissDialogVerifying();
                                idVerificationView.setVerifyBtnEnabled();
                            }
                        });
                    }

                    @Override
                    public void requestSuccess() {

                    }
                });
            }
        }.start();


    }

    private void recCompressFile(File file) {
        Luban.get(idVerificationView.getContext())
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
                        res = newPath;
                        semaphore.release();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用
                        System.out.println("压缩失败");
                    }
                }).launch();    //启动压缩
    }

    public void getCarOwnerInfo() {
        idVerificationView.showDialogVerifying("加载信息中。。。");
        long id = (long) SPUtils.get(idVerificationView.getActivity(), "id", 0L);
        idVerificationBiz.getCarOwnerInfo(id, new OnGetCarOwnerInfoListener() {
            @Override
            public void getCarOwnerInfoSuccess(final CarOwnerBean carOwnerBean) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        idVerificationView.setCarOwnerBean(carOwnerBean);
                        idVerificationView.dismissDialogVerifying();
                    }
                });
            }

            @Override
            public void getCarOwnerInfoFailed(CarOwnerBean carOwnerBean) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        idVerificationView.showToast("获取信息失败");
                    }
                });
            }

            @Override
            public void requestSuccess() {

            }

            @Override
            public void requestFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        idVerificationView.showToast("请求失败");
                    }
                });
            }
        });
    }
}
