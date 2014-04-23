package com.adnan;

@SuppressWarnings("deprecation")
public class Image {
        double latitude;
        double longitude;
        byte[] physicalImage;
        Long streamId;
        String tags;

        public Image(double a, double b, byte[] c, Long st, String tags) {
                latitude = a;
                longitude = b;
                physicalImage = c;
                streamId = st;
                this.tags = tags;
        }

        public Image() {
        }
}
