package com.mermakov.testgithubclient.rest.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by max_ermakov on 8/21/16.
 */
public class AccessTokenDTO {

        public String access_token;
        public String token_type;
        public String scope;
        public String error;
        public String error_description;
        public String error_uri;

}
