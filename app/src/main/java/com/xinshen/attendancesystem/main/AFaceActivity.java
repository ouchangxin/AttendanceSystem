package com.xinshen.attendancesystem.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.orhanobut.logger.Logger;
import com.xinshen.attendancesystem.Global;
import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.util.Base64Util;
import com.xinshen.attendancesystem.util.ImageUtil;
import com.xinshen.attendancesystem.util.ScreenUtil;
import com.xinshen.attendancesystem.util.ToastUtil;
import com.xinshen.sdk.Iface.AddFaceCallBack;
import com.xinshen.sdk.Iface.DetectFaceCallBack;
import com.xinshen.sdk.Iface.SetUserIdCallBack;
import com.xinshen.sdk.entity.AddFaceRespond;
import com.xinshen.sdk.entity.DetectFaceRespond;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.SetUserIdRespond;
import com.xinshen.sdk.face.FaceDetector;
import com.xinshen.sdk.face.FaceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xinshen.attendancesystem.Global.Const.CAMERA_ID;

/**
 * Created by thinkpad on 2017/12/4.
 */

public class AFaceActivity extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback{

    @BindView(R.id.sv_add)
    SurfaceView mSurfaceView;
    //相机
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private ExecutorService mFixedThreadPool;
    private int mPreviewWidth, mPreviewHeight;
    private int mSettingWidth;
    private int mRotate;
    //data转bitmap相关
    private RenderScript rs;
    private ScriptIntrinsicYuvToRGB yuvToRgbIntrinsic;
    private Type.Builder yuvType, rgbaType;
    private Allocation in, out;
    //人脸检测
    private List<Rect> rects;
    private boolean detectFinish = true;
    private String face_token;
    //info
    private String user_id;
    public static final String EXTRA_FACE_TOKEN = "extra_face_token";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtil.setFullScreen(this);
        ScreenUtil.keepScreenLight(this);
        setContentView(R.layout.activity_aface);
        ButterKnife.bind(this);
        initSurfaceView();
        initField();
    }

    private void initField() {
        user_id = getIntent().getStringExtra(APersonActivity.EXTRA_NAME);
        mFixedThreadPool = Executors.newFixedThreadPool(1);
        rs = RenderScript.create(this);
        rects = new ArrayList<Rect>();
        mSettingWidth = 480;
        yuvToRgbIntrinsic = ScriptIntrinsicYuvToRGB.create(rs, Element.U8_4(rs));
    }

    private void initSurfaceView() {
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initCamera();
        //5秒后开始检测人脸
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                detectFinish = false;
            }
        },5000);
    }

    private void initCamera() {
        if (mCamera == null){
            try{
                mCamera = Camera.open(CAMERA_ID);
            }catch (RuntimeException e){
                e.printStackTrace();
            }
        }
        //设置预览方向
        int rotation = ScreenUtil.getDisplayRotation(AFaceActivity.this);
        mRotate = ScreenUtil.getDisplayOrientation(rotation,CAMERA_ID);
        mCamera.setDisplayOrientation(mRotate);
        if (mSurfaceHolder!=null){
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startPreview() {
        if (mCamera!=null){
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface()!=null && mCamera!=null){
            mCamera.stopPreview();
            Camera.Parameters parameters =mCamera.getParameters();
            List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
            float targetRatio = (float) width / height;
            Camera.Size previewSize = ScreenUtil.getOptimalPreviewSize(this, sizes,targetRatio);
            mPreviewWidth = previewSize.width;
            mPreviewHeight = previewSize.height;
            parameters.setPreviewSize(previewSize.width,previewSize.height);
            mCamera.setParameters(parameters);
            startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera!=null){
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPreviewFrame(final byte[] data, Camera camera) {
        if (!detectFinish){
            detectFinish = true;
            mFixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    long startTime = System.currentTimeMillis();
                    //TODO: 2017-11-10 yuv data[]转bitmap
                    if (yuvType == null){
                        yuvType = new Type.Builder(rs,Element.U8(rs)).setX(data.length);
                        in = Allocation.createTyped(rs,yuvType.create(),Allocation.USAGE_SCRIPT);
                        rgbaType = new Type.Builder(rs,Element.RGBA_8888(rs)).
                                setX(mPreviewWidth).setY(mPreviewHeight);
                        out = Allocation.createTyped(rs, rgbaType.create(),Allocation.USAGE_SCRIPT);
                    }
                    in.copyFrom(data);
                    yuvToRgbIntrinsic.setInput(in);
                    yuvToRgbIntrinsic.forEach(out);
                    Bitmap bmp = Bitmap.createBitmap(mPreviewWidth, mPreviewHeight, Bitmap.Config.ARGB_8888);
                    out.copyTo(bmp);
                    //TODO：1017-11-15 开始检测人脸
                    float scale = (float) mPreviewHeight / (float) mPreviewWidth;
                    int w = mSettingWidth;
                    int h = (int) (mSettingWidth * scale);
                    Bitmap detBitmap = Bitmap.createScaledBitmap(bmp,w,h,false);
                    if (!bmp.isRecycled()){
                        bmp.recycle();
                    }
                    detBitmap = ImageUtil.checkBit(detBitmap);
                    detBitmap = ImageUtil.rotateBitmap(detBitmap, mRotate);//480 270-->270 480
                    detectFace(detBitmap);
                    if (!detBitmap.isRecycled()){
                        detBitmap.recycle();
                    }
//                Logger.e("spend time =  "+(System.currentTimeMillis()-startTime));
                }
            });
        }
    }

    private void detectFace(final Bitmap bmp) {
        String img = Base64Util.bitmapToBase64(bmp);
        FaceDetector.newInstance().detectFace(img, new DetectFaceCallBack() {
            @Override
            public void onRespond(DetectFaceRespond respond) {
                List<DetectFaceRespond.FacesBean> faces = respond.getFaces();
                if (faces!=null && faces.size()>0){
                    face_token = faces.get(0).getFace_token();
                    detectFinish = true;
                    addFace();
                }else{
                    detectFinish = false;
                }
            }

            @Override
            public void onError(ErrorRespond error) {
                String msg = error.getError_message();
                if (!"CONCURRENCY_LIMIT_EXCEEDED".equals(msg)){
                    ToastUtil.showOnUI(AFaceActivity.this,"人脸检测失败："+error.getError_message());
                    Logger.e("人脸检测失败："+error.getError_message());
                }
                detectFinish = false;
            }
        });
    }

    private void addFace() {
        FaceManager.newInstance().addFace(Global.Variable.COMPANY_NAME, face_token,
                new AddFaceCallBack() {
            @Override
            public void onRespond(AddFaceRespond respond) {
                if (respond.getFace_added() == 1){
                    setUserId();
                    Logger.e("count："+respond.getFace_count());
                }else{
                    detectFinish = false;
                    face_token = null;
                    Logger.e("人脸添加失败："+respond.getFailure_detail().get(0).getReason());
                }
            }

            @Override
            public void onError(ErrorRespond error) {
                String msg = error.getError_message();
                if (!"CONCURRENCY_LIMIT_EXCEEDED".equals(msg)){
                    ToastUtil.showOnUI(AFaceActivity.this,"人脸添加失败："+error.getError_message());
                    detectFinish = false;
                }else{
                    addFace();
                }
            }
        });
    }

    private void setUserId() {
        FaceManager.newInstance().setUserID(face_token, user_id, new SetUserIdCallBack() {
            @Override
            public void onRespond(SetUserIdRespond respond) {
                ToastUtil.showOnUI(AFaceActivity.this,"人脸添加成功");
                detectFinish = true;
                finishSelf();
            }
            @Override
            public void onError(ErrorRespond error) {
                String msg = error.getError_message();
                if (!"CONCURRENCY_LIMIT_EXCEEDED".equals(msg)){
                    ToastUtil.showOnUI(AFaceActivity.this,"人脸添加失败："+error.getError_message());
                    detectFinish = false;
                }else{
                    setUserId();
                }
            }
        });
    }

    private void finishSelf() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_FACE_TOKEN,face_token);
        setResult(RESULT_OK,intent);
        finish();
    }

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera!=null){
            mCamera.stopPreview();
            detectFinish = true;
        }
    }
}
