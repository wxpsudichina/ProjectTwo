package com.sudichina.ftwl.presenter;

import android.os.Handler;

import com.sudichina.ftwl.bean.CarBean;
import com.sudichina.ftwl.biz.IMyCarsBiz;
import com.sudichina.ftwl.biz.MyCarsBiz;
import com.sudichina.ftwl.biz.OnDeleteVehicleListener;
import com.sudichina.ftwl.biz.OnGetCarListListener;
import com.sudichina.ftwl.utils.SPUtils;
import com.sudichina.ftwl.view.IMyCarsView;

import java.util.List;

/**
 * Created by mccccccmike on 2016/9/2.
 */
public class MyCarsPresenter {
    private IMyCarsView myCarsView;
    private IMyCarsBiz myCarsBiz;
        private Handler mHandler;

        public MyCarsPresenter(IMyCarsView myCarsView) {
            this.myCarsView = myCarsView;
            myCarsBiz = new MyCarsBiz();
        mHandler = new Handler();
    }

    public void getCarList() {
        myCarsView.showProgressDialog();
        myCarsBiz.getCarList((Long) SPUtils.get(myCarsView.getContext(), "id", 0L), new OnGetCarListListener() {
            @Override
            public void getCarListSuccess(final List<CarBean> carBeanList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCarsView.setCarBeanList(carBeanList);
                        myCarsView.dismissProgressDialog();
                    }
                });
            }

            @Override
            public void getCarListFailure(String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void requestFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void requestSuccess() {

            }
        });
    }

    public void deleteVehicle(long id) {
        myCarsView.showProgressDialog();
        System.out.println("the deleted vehicle's id is " + id);
        myCarsBiz.deleteVehicle(id, new OnDeleteVehicleListener() {
            @Override
            public void deleteVehicleSuccess(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCarsView.showToast(msg);
                        myCarsView.dismissProgressDialog();
                        myCarsView.refreshData();
                    }
                });
            }

            @Override
            public void deleteVehicleFailed(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCarsView.showToast(msg);
                    }
                });
            }

            @Override
            public void requestSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void requestFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

    }
}
