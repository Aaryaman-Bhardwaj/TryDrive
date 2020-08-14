package com.aaryaman.googledrive

import com.aaryaman.googledrive.model.fileStruct
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


//interface Jinterface {
//
//    @POST("/upload/drive/v3/files?uploadType=media")
//    fun uploadFile(@Header("Authorization") authorization: String,
//                   @Header("Accept") accept: String = "application/json",
//                   @Header("Content-Type") type: String)   : Call<fileStruct>
//}

interface Jinterface {
    @Multipart
    @POST("upload/drive/v3/files?uploadType=media")
    fun uploadFile(
        @Header("Authorization") authorization: String,
        @Part file: MultipartBody.Part
    ) : Call<RequestBody>
}
//interface UploadReceiptService {
//    @Multipart
//    @POST("/api/receipt/upload")
//    fun uploadReceipt(
//        @Header("Cookie") sessionIdAndRz: String?,
//        @Part file: MultipartBody.Part?,
//        @Part("items") items: RequestBody?,
//        @Part("isAny") isAny: RequestBody?
//    ): Call<List<UploadResult?>?>?
//}

















//    POST https://www.googleapis.com/drive/v3/files?key=[YOUR_API_KEY] HTTP/1.1
//
//    Authorization: Bearer [YOUR_ACCESS_TOKEN]
//    Accept: application/json
//    Content-Type: application/json
//
//    {}