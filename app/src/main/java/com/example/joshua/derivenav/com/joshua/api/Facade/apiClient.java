/*
 * Copyright (c) 2015-2016 Filippo Engidashet. All Rights Reserved.
 * <p>
 *  Save to the extent permitted by law, you may not use, copy, modify,
 *  distribute or create derivative works of this material or any part
 *  of it without the prior written consent of Filippo Engidashet.
 *  <p>
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 */

package com.example.joshua.derivenav.com.joshua.api.Facade;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @date 1/22/2016
 */
public class apiClient implements Serializable {

//    to add objects --> private String[] topics
//    remember to use split, to separate each record inside object

    private static final long serialVersionUID = 111696345129311948L;
    public byte[] imageByteArray;

    private Bitmap picture;
    private boolean isFromDatabase;

    @Expose
    private Integer id;
    @Expose
    private String title;
    @Expose
    private String thumbnailUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public boolean isFromDatabase() {
        return isFromDatabase;
    }

    public void setFromDatabase(boolean fromDatabase) {
        isFromDatabase = fromDatabase;
    }
}
