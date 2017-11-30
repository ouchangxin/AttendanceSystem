package com.xinshen.attendancesystem.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 图片处理工具
 */
@SuppressWarnings("ALL")
public class ImageUtil {
    /**
     * 旋转图片
     * @param bitmap 待旋转的图片
     * @param degrees 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotate(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            try {
                Matrix m = new Matrix();
                m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
                Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != b2) {
                    bitmap.recycle();
                    bitmap = b2;
                }
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 根据手机方向旋转图片
     * @param bitmap 待旋转的图片
     * @param orientation 手机方向
     */
    public static Bitmap rotateBitmap(Bitmap bitmap,int orientation){
        Bitmap bmp = null;
        switch (orientation){
            case 90:
                bmp = rotate(bitmap,-90);
                break;
            case 180:
                bmp = rotate(bitmap,-180);
                break;
            case 270:
                bmp = rotate(bitmap,-270);
                break;
        }
        return bmp;
    }
    /**
     * 检查图片是否为RGB_565格式，如果不是则将其转换
     * @param bitmap
     * @return 转化成RGB_565的图片
     */
    public static Bitmap checkBit(Bitmap bitmap) {
        Bitmap bit = bitmap;
        if (bitmap.getConfig() != Bitmap.Config.RGB_565) {
            bit = bitmap.copy(Bitmap.Config.RGB_565, true);
        }

        if (bitmap.getWidth() % 2 != 0) {
            bit = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() - 1, bitmap.getHeight(), true);
        }
        return bit;
    }
}
