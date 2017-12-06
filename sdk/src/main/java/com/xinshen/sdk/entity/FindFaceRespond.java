package com.xinshen.sdk.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by thinkpad on 2017/11/30.
 */

public class FindFaceRespond {

    /**
     * request_id : 1470481443,0d749845-7153-4f5e-a996-ffc5a1ac0a79
     * time_used : 1126
     * thresholds : {"1e-3":65.3,"1e-5":76.5,"1e-4":71.8}
     * results : [{"confidence":96.46,"user_id":"234723hgfd","face_token":"4dc8ba0650405fa7a4a5b0b5cb937f0b"}]
     */

    private String request_id;
    private int time_used;
    private ThresholdsBean thresholds;
    private List<ResultsBean> results;
    private List<FacesBean> faces;
    private String image_id;
    private String error_message;

    public List<FacesBean> getFaces() {
        return faces;
    }

    public void setFaces(List<FacesBean> faces) {
        this.faces = faces;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public ThresholdsBean getThresholds() {
        return thresholds;
    }

    public void setThresholds(ThresholdsBean thresholds) {
        this.thresholds = thresholds;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ThresholdsBean {
        /**
         * 1e-3 : 65.3
         * 1e-5 : 76.5
         * 1e-4 : 71.8
         */

        @SerializedName("1e-3")
        private double _$1e3;
        @SerializedName("1e-5")
        private double _$1e5;
        @SerializedName("1e-4")
        private double _$1e4;

        public double get_$1e3() {
            return _$1e3;
        }

        public void set_$1e3(double _$1e3) {
            this._$1e3 = _$1e3;
        }

        public double get_$1e5() {
            return _$1e5;
        }

        public void set_$1e5(double _$1e5) {
            this._$1e5 = _$1e5;
        }

        public double get_$1e4() {
            return _$1e4;
        }

        public void set_$1e4(double _$1e4) {
            this._$1e4 = _$1e4;
        }
    }

    public static class ResultsBean {
        /**
         * confidence : 96.46
         * user_id : 234723hgfd
         * face_token : 4dc8ba0650405fa7a4a5b0b5cb937f0b
         */

        private double confidence;
        private String user_id;
        private String face_token;

        public double getConfidence() {
            return confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }
    }
    public static class FacesBean {
        /**
         * landmark : {"mouth_upper_lip_left_contour2":{"y":185,"x":146},"contour_chin":{"y":231,"x":137},"right_eye_pupil":{"y":146,"x":205},"mouth_upper_lip_bottom":{"y":195,"x":159}}
         * attributes : {"gender":{"value":"Female"},"age":{"value":21},"glass":{"value":"None"},"headpose":{"yaw_angle":-26.625063,"pitch_angle":12.921974,"roll_angle":22.814377},"smile":{"threshold":30.1,"value":2.566890001296997}}
         * face_rectangle : {"width":140,"top":89,"left":104,"height":141}
         * face_token : ed319e807e039ae669a4d1af0922a0c8
         */

        private DetectFaceRespond.FacesBean.LandmarkBean landmark;
        private DetectFaceRespond.FacesBean.AttributesBean attributes;
        private DetectFaceRespond.FacesBean.FaceRectangleBean face_rectangle;
        private String face_token;

        public DetectFaceRespond.FacesBean.LandmarkBean getLandmark() {
            return landmark;
        }

        public void setLandmark(DetectFaceRespond.FacesBean.LandmarkBean landmark) {
            this.landmark = landmark;
        }

        public DetectFaceRespond.FacesBean.AttributesBean getAttributes() {
            return attributes;
        }

        public void setAttributes(DetectFaceRespond.FacesBean.AttributesBean attributes) {
            this.attributes = attributes;
        }

        public DetectFaceRespond.FacesBean.FaceRectangleBean getFace_rectangle() {
            return face_rectangle;
        }

        public void setFace_rectangle(DetectFaceRespond.FacesBean.FaceRectangleBean face_rectangle) {
            this.face_rectangle = face_rectangle;
        }

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }

        public static class LandmarkBean {
            /**
             * mouth_upper_lip_left_contour2 : {"y":185,"x":146}
             * contour_chin : {"y":231,"x":137}
             * right_eye_pupil : {"y":146,"x":205}
             * mouth_upper_lip_bottom : {"y":195,"x":159}
             */

            private DetectFaceRespond.FacesBean.LandmarkBean.MouthUpperLipLeftContour2Bean mouth_upper_lip_left_contour2;
            private DetectFaceRespond.FacesBean.LandmarkBean.ContourChinBean contour_chin;
            private DetectFaceRespond.FacesBean.LandmarkBean.RightEyePupilBean right_eye_pupil;
            private DetectFaceRespond.FacesBean.LandmarkBean.MouthUpperLipBottomBean mouth_upper_lip_bottom;

            public DetectFaceRespond.FacesBean.LandmarkBean.MouthUpperLipLeftContour2Bean getMouth_upper_lip_left_contour2() {
                return mouth_upper_lip_left_contour2;
            }

            public void setMouth_upper_lip_left_contour2(DetectFaceRespond.FacesBean.LandmarkBean.MouthUpperLipLeftContour2Bean mouth_upper_lip_left_contour2) {
                this.mouth_upper_lip_left_contour2 = mouth_upper_lip_left_contour2;
            }

            public DetectFaceRespond.FacesBean.LandmarkBean.ContourChinBean getContour_chin() {
                return contour_chin;
            }

            public void setContour_chin(DetectFaceRespond.FacesBean.LandmarkBean.ContourChinBean contour_chin) {
                this.contour_chin = contour_chin;
            }

            public DetectFaceRespond.FacesBean.LandmarkBean.RightEyePupilBean getRight_eye_pupil() {
                return right_eye_pupil;
            }

            public void setRight_eye_pupil(DetectFaceRespond.FacesBean.LandmarkBean.RightEyePupilBean right_eye_pupil) {
                this.right_eye_pupil = right_eye_pupil;
            }

            public DetectFaceRespond.FacesBean.LandmarkBean.MouthUpperLipBottomBean getMouth_upper_lip_bottom() {
                return mouth_upper_lip_bottom;
            }

            public void setMouth_upper_lip_bottom(DetectFaceRespond.FacesBean.LandmarkBean.MouthUpperLipBottomBean mouth_upper_lip_bottom) {
                this.mouth_upper_lip_bottom = mouth_upper_lip_bottom;
            }

            public static class MouthUpperLipLeftContour2Bean {
                /**
                 * y : 185
                 * x : 146
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class ContourChinBean {
                /**
                 * y : 231
                 * x : 137
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class RightEyePupilBean {
                /**
                 * y : 146
                 * x : 205
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class MouthUpperLipBottomBean {
                /**
                 * y : 195
                 * x : 159
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }
        }

        public static class AttributesBean {
            /**
             * gender : {"value":"Female"}
             * age : {"value":21}
             */

            private DetectFaceRespond.FacesBean.AttributesBean.GenderBean gender;
            private DetectFaceRespond.FacesBean.AttributesBean.AgeBean age;


            public DetectFaceRespond.FacesBean.AttributesBean.GenderBean getGender() {
                return gender;
            }

            public void setGender(DetectFaceRespond.FacesBean.AttributesBean.GenderBean gender) {
                this.gender = gender;
            }

            public DetectFaceRespond.FacesBean.AttributesBean.AgeBean getAge() {
                return age;
            }

            public void setAge(DetectFaceRespond.FacesBean.AttributesBean.AgeBean age) {
                this.age = age;
            }

            public static class GenderBean {
                /**
                 * value : Female
                 */

                private String value;

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class AgeBean {
                /**
                 * value : 21
                 */

                private int value;

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }
        }

        public static class FaceRectangleBean {
            /**
             * width : 140
             * top : 89
             * left : 104
             * height : 141
             */

            private int width;
            private int top;
            private int left;
            private int height;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}
