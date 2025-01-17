package com.aaryaman.googledrive.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class OAuthToken {
    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null

    @SerializedName("expires_in")
    @Expose
    var expiresIn: Long? = null

    @SerializedName("token_type")
    @Expose
    var tokenType: String? = null

    @SerializedName("refresh_token")
    @Expose
    var refreshToken: String? = null

}