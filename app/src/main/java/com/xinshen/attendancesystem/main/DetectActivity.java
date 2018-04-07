package com.xinshen.attendancesystem.main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.xinshen.attendancesystem.Global;
import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.main.employee.EmployeeBean;
import com.xinshen.attendancesystem.util.Base64Util;
import com.xinshen.attendancesystem.util.DateUtil;
import com.xinshen.attendancesystem.util.EmployeeUtils;
import com.xinshen.attendancesystem.util.ImageUtil;
import com.xinshen.attendancesystem.util.ScreenUtil;
import com.xinshen.attendancesystem.util.GlideCircleTransform;
import com.xinshen.attendancesystem.view.FrameView;
import com.xinshen.sdk.Iface.FindFaceCallBack;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.FindFaceRespond;
import com.xinshen.sdk.face.FaceSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xinshen.attendancesystem.Global.Const.CAMERA_ID;

/**
 * Created by thinkpad on 2017/10/29.
 */

public class DetectActivity extends Activity implements SurfaceHolder.Callback, Camera
        .PreviewCallback {

    @BindView(R.id.sv_detect)
    SurfaceView mSurfaceView;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.frameView)
    FrameView mFrameView;
    @BindView(R.id.portrait)
    ImageView ivPortrait;
    @BindView(R.id.detect_name)
    TextView tvName;
    @BindView(R.id.detect_depart)
    TextView tvDepart;
    @BindView(R.id.detect_job)
    TextView tvJob;
    @BindView(R.id.date)
    TextView tvDate;
    @BindView(R.id.time)
    TextView tvTime;
//    @BindView(R.id.name)
//    TextView name;
//    @BindView(R.id.department)
//    TextView department;
//    @BindView(R.id.job)
//    TextView job;
//    @BindView(R.id.date)
//    TextView date;
//    @BindView(R.id.time)
//    TextView time;
//    @BindView(R.id.portrait)
//    ImageView portrait;
//    @BindView(R.id.detect_name)
//    TextView detectName;
//    @BindView(R.id.detect_depart)
//    TextView detectDepart;
//    @BindView(R.id.detect_job)
//    TextView detectJob;
    @BindView(R.id.lyShow)
    ConstraintLayout lyShow;


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
    private final int FACE_MAX_NUM = 1;
    //Message
    private final int MESSAGE_SHOW_BITMAP = 0;  //展示识别的图片
    private final int MESSAGE_SHOW_FRAME = 1;   //展示脸宽
    private final int MESSAGE_SEARCH_FACE = 2; //匹配人脸
    private boolean stopSearch;
    private int currentReq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtil.setFullScreen(this);
        ScreenUtil.keepScreenLight(this);
        setContentView(R.layout.activity_detect);
        ButterKnife.bind(this);
        initSurfaceView();
        initField();
    }

    private void initField() {
        mFixedThreadPool = Executors.newFixedThreadPool(4);
        rs = RenderScript.create(this);
        rects = new ArrayList<>();
        mSettingWidth = 480;
        yuvToRgbIntrinsic = ScriptIntrinsicYuvToRGB.create(rs, Element.U8_4(rs));
    }

    private void initSurfaceView() {
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        openCamera();
    }

    private void openCamera() {
        if (mCamera == null) {
            try {
                mCamera = Camera.open(CAMERA_ID);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        //设置预览方向
        int rotation = ScreenUtil.getDisplayRotation(DetectActivity.this);
        mRotate = ScreenUtil.getDisplayOrientation(rotation, CAMERA_ID);
        mCamera.setDisplayOrientation(mRotate);
        if (mSurfaceHolder != null) {
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startPreview() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() != null && mCamera != null) {
            mCamera.stopPreview();
            Camera.Parameters parameters = mCamera.getParameters();
            List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
            float targetRatio = (float) width / height;
            Camera.Size previewSize = ScreenUtil.getOptimalPreviewSize(this, sizes, targetRatio);
            mPreviewWidth = previewSize.width;
            mPreviewHeight = previewSize.height;
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            mCamera.setParameters(parameters);
            startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPreviewFrame(final byte[] data, Camera camera) {
        mFixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                //TODO: 2017-11-10 yuv data[]转bitmap
                if (yuvType == null) {
                    yuvType = new Type.Builder(rs, Element.U8(rs)).setX(data.length);
                    in = Allocation.createTyped(rs, yuvType.create(), Allocation.USAGE_SCRIPT);
                    rgbaType = new Type.Builder(rs, Element.RGBA_8888(rs)).
                            setX(mPreviewWidth).setY(mPreviewHeight);
                    out = Allocation.createTyped(rs, rgbaType.create(), Allocation.USAGE_SCRIPT);
                }
                in.copyFrom(data);
                yuvToRgbIntrinsic.setInput(in);
                yuvToRgbIntrinsic.forEach(out);
                Bitmap bmp = Bitmap.createBitmap(mPreviewWidth, mPreviewHeight, Bitmap.Config
                        .ARGB_8888);
                out.copyTo(bmp);
                //TODO：1017-11-15 开始检测人脸
                float scale = (float) mPreviewHeight / (float) mPreviewWidth;
                int w = mSettingWidth;
                int h = (int) (mSettingWidth * scale);
                Bitmap detBitmap = Bitmap.createScaledBitmap(bmp, w, h, false);
                if (!bmp.isRecycled()) {
                    bmp.recycle();
                }
                detBitmap = ImageUtil.checkBit(detBitmap);
                detBitmap = ImageUtil.rotateBitmap(detBitmap, mRotate);//480 270-->270 480
                int scaleWidth;
                int scaleHeight;
                if (mRotate % 90 == 0) {
                    scaleWidth = mPreviewWidth / detBitmap.getHeight();
                    scaleHeight = mPreviewHeight / detBitmap.getWidth();
                } else {
                    scaleWidth = mPreviewWidth / detBitmap.getWidth();
                    scaleHeight = mPreviewHeight / detBitmap.getHeight();
                }
                FaceDetector detector = new FaceDetector(detBitmap.getWidth(), detBitmap
                        .getHeight(), FACE_MAX_NUM);
                FaceDetector.Face[] faces = new FaceDetector.Face[FACE_MAX_NUM];
                int faceNum = detector.findFaces(detBitmap, faces);
                rectList = new ArrayList<>();
                if (faceNum > 0) {
                    float eyesDis;
                    PointF mid = new PointF();
                    for (int i = 0; i < faceNum; i++) {
                        if (faces[i] != null) {
                            faces[i].getMidPoint(mid);
                            eyesDis = faces[i].eyesDistance();
                            if (CAMERA_ID == 1)
                                mid.x = -mid.x + h; //前置摄像头镜像
                            Rect rect = new Rect((int) ((mid.x - eyesDis * 1.25) * scaleWidth),
                                    (int) ((mid.y - eyesDis * 1.25) * scaleHeight),
                                    (int) ((mid.x + eyesDis * 1.25) * scaleWidth),
                                    (int) ((mid.y + eyesDis * 1.90) * scaleHeight));
//                             Logger.e("left " + rect.left + " top " + rect.top + " right " + rect
//                                    .right + " bottom " + rect.bottom);
                            rectList.add(rect);
                        }
                    }
                    if (!stopSearch) {
                        Message msg = Message.obtain(mHandler, MESSAGE_SEARCH_FACE);
                        msg.obj = Base64Util.bitmapToBase64(detBitmap);
                        mHandler.sendMessage(msg);
                    }
                }
                mHandler.sendEmptyMessage(MESSAGE_SHOW_FRAME);
                if (!detBitmap.isRecycled()) {
                    detBitmap.recycle();
                }
//                Logger.e("spend time =  "+(System.currentTimeMillis()-startTime));
            }
        });
    }

    private List<Rect> rectList;
    private Bitmap temp;

    private final int MESSAGE_SHOW_RESULT = 11;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SHOW_BITMAP:
                    img.setImageBitmap(temp);
                    break;
                case MESSAGE_SHOW_FRAME:
                    mFrameView.setRectList(rectList);
                    break;
                case MESSAGE_SHOW_RESULT:
                    showResult((FindFaceRespond.ResultsBean) msg.obj);
                    break;
                case MESSAGE_SEARCH_FACE:
                    stopSearch = true;
                    FaceSearch.newInstance().findFace((String) msg.obj, Global.Variable
                            .COMPANY_NAME, new FindFaceCallBack() {
                        @Override
                        public void onRespond(FindFaceRespond respond) {
                            FindFaceRespond.ThresholdsBean thresholds = respond.getThresholds();
                            double matchThreshold = thresholds.get_$1e3();
                            List<FindFaceRespond.ResultsBean> results = respond.getResults();
                            if (results != null) {
                                double maxConfidence = 0;
                                int t = 0;
                                for (int i = 0; i < results.size(); ++i) {
                                    if (results.get(i).getConfidence() > maxConfidence) {
                                        maxConfidence = results.get(i).getConfidence();
                                        t = i;
                                    }
                                }
                                FindFaceRespond.ResultsBean result = results.get(t);
                                if (maxConfidence > matchThreshold) {
                                    Message msg = Message.obtain();
                                    msg.what = MESSAGE_SHOW_RESULT;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                } else {
                                    Logger.e("未匹配到人员 " + result.getUser_id());
                                }
                            }
                        }

                        @Override
                        public void onError(ErrorRespond error) {
                            stopSearch = false;
                            Logger.e("人脸检测错误：" + error.getError_message());
                        }
                    });
                    break;
            }
            return true;
        }
    });

    private void showResult(FindFaceRespond.ResultsBean resultsBean){
        List<EmployeeBean> employees = EmployeeUtils.getEmployees(this);
        for (EmployeeBean employee : employees){
            if (employee.getFace_token().equals(resultsBean.getFace_token())){
                long time = System.currentTimeMillis();
                tvDate.setText(DateUtil.getDate(time));
                tvTime.setText(DateUtil.getTime(time));
                Glide.with(this).load(employee.getAvator_url()).
                        transform(new GlideCircleTransform(this)).into(ivPortrait);
                tvName.setText(employee.getName());
                tvDepart.setText(employee.getDepart());
                tvJob.setText(employee.getPosition());
                lyShow.setVisibility(View.VISIBLE);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopSearch = false;
                        lyShow.setVisibility(View.GONE);
                    }
                },4000);

                break;
            }
        }

    }



    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }
}
