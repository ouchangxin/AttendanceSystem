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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.util.ImageUtil;
import com.xinshen.attendancesystem.util.ScreenUtil;
import com.xinshen.attendancesystem.view.FrameView;

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

public class DetectActivity extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    @BindView(R.id.surface_view)
    SurfaceView mSurfaceView;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.frameView)
    FrameView mFrameView;
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
    private final int MESSAGE_SHOW_BITMAP = 0;
    private final int MESSAGE_SHOW_FRAME = 1;
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
        int rotation = ScreenUtil.getDisplayRotation(DetectActivity.this);
//        Logger.e("rotation = "+rotation); //0
        mRotate = ScreenUtil.getDisplayOrientation(rotation,CAMERA_ID);
//        Logger.e("rotate = "+ mRotate); //90
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
                int scaleWidth;
                int scaleHeight;
                if (mRotate%90 == 0){
                     scaleWidth = mPreviewWidth/detBitmap.getHeight();
                     scaleHeight = mPreviewHeight/detBitmap.getWidth();
                }else{
                     scaleWidth = mPreviewWidth/detBitmap.getWidth();
                     scaleHeight = mPreviewHeight/detBitmap.getHeight();
                }
                FaceDetector detector = new FaceDetector(detBitmap.getWidth(),detBitmap.getHeight(), FACE_MAX_NUM);
                FaceDetector.Face[] faces = new FaceDetector.Face[FACE_MAX_NUM];
                int faceNum = detector.findFaces(detBitmap, faces);
                rectList = new ArrayList<Rect>();
                if (faceNum>0){
                    float eyesDis;
                    PointF mid = new PointF();
                    for (int i=0;i<faceNum;i++){
                        if (faces[i]!=null){
                            faces[i].getMidPoint(mid);
                            eyesDis =  faces[i].eyesDistance();
                            if (CAMERA_ID == 1)
                                mid.x = -mid.x+h; //前置摄像头镜像
                            Rect rect = new Rect((int)((mid.x - eyesDis*1.25)*scaleWidth),
                                                (int)((mid.y - eyesDis*1.25)*scaleHeight),
                                                (int)((mid.x + eyesDis*1.25)*scaleWidth),
                                                (int)((mid.y + eyesDis*1.90)*scaleHeight));
                            rectList.add(rect);
                        }
                    }
                }
                mHandler.sendEmptyMessage(MESSAGE_SHOW_FRAME);
                if (!detBitmap.isRecycled()){
                    detBitmap.recycle();
                }
//                Logger.e("spend time =  "+(System.currentTimeMillis()-startTime));
            }
        });
    }
    private List<Rect> rectList;
    private Bitmap temp;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_SHOW_BITMAP:
                    img.setImageBitmap(temp);
                    break;
                case MESSAGE_SHOW_FRAME:
                    mFrameView.setRectList(rectList);
                    break;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera!=null){
            mCamera.stopPreview();
        }
    }
}
