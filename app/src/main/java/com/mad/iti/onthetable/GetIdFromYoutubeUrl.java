package com.mad.iti.onthetable;

public class GetIdFromYoutubeUrl {
    public static String getId(String link) {
        if (link != null && link.split("\\?v=").length > 0)
            return link.split("\\?v=")[1];
        else return "";
    }
}
