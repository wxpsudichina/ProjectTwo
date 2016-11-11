package com.sudichina.ftwl.presenter;

import android.os.Handler;

import com.sudichina.ftwl.bean.UsefulCarBean;
import com.sudichina.ftwl.biz.HomeBiz;
import com.sudichina.ftwl.biz.IHomeBiz;
import com.sudichina.ftwl.biz.OnGetUsefulCarListListener;
import com.sudichina.ftwl.biz.OnReleaseRouteListener;
import com.sudichina.ftwl.view.IHomeView;

import java.util.List;

/**
 * Created by mccccccmike on 2016/8/30.
 */
public class HomePresenter {
    private IHomeView homeView;
    private IHomeBiz homeBiz;
    private Handler mHandler;

    public HomePresenter(IHomeView homeView) {
        this.homeView = homeView;
        homeBiz = new HomeBiz();
        mHandler = new Handler();
    }

    public void getUsefulCarList() {
        homeView.showProgressDialog();
        homeBiz.getUsefulCarList(homeView.getAccountId(), new OnGetUsefulCarListListener() {
            @Override
            public void getCarListSuccess(List<UsefulCarBean> usefulCarBeanList) {
                homeView.setUsefulCarBeanList(usefulCarBeanList);
            }

            @Override
            public void getCarListFailure(String msg) {
                System.out.println(msg);
            }

            @Override
            public void requestSuccess() {

            }

            @Override
            public void requestFailed() {

            }
        });
    }

    public void releaseRoute() {
        homeBiz.releaseRoute(
                homeView.getAccountId(),
                homeView.getCarId(),
                homeView.getToZoneCode(),
                homeView.getFromZoneCode(),
                homeView.getLaLoPosition(),
                homeView.getFromAddress(),
                homeView.getReleaseTime(),
                homeView.getArriveTime(),
                homeView.getType(),
                homeView.getPriceAll(),
                homeView.getPriceKg(),
                homeView.getPriceDun(),
                homeView.getPriceSquare(),
                homeView.getPrickDoor(),
                homeView.getPrickStation(),
                homeView.getAddService(),
                new OnReleaseRouteListener() {
                    @Override
                    public void onReleaseRouteSuccess() {
                        if (!homeView.isLl_release_complete_is_show()) {
                            homeView.setLl_release_complete_is_show(true);
                            homeView.showContent(-homeView.getLl_release_complete().getPaddingBottom(), homeView.getLl_release_complete(), 300);
                        }

//                        ValueAnimator animator = ValueAnimator.ofInt(-height, 0);
//                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                            @Override
//                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                                vg.setPadding(vg.getPaddingLeft(), vg.getPaddingTop(), vg.getPaddingRight(), (Integer) valueAnimator.getAnimatedValue());
//                            }
//                        });
//                        animator.setDuration(duration);
//                        animator.start();

                        //显示发布路线完成
                        homeView.hideContent(homeView.getLl_bottom_height(), homeView.getLl_container(), 600);
                        homeView.setFlag(false);


                    }

                    @Override
                    public void onReleaseRouteFailed() {

                    }

                    @Override
                    public void requestSuccess() {

                    }

                    @Override
                    public void requestFailed() {

                    }
                }
        );
    }
}
