package com.hb.nf.data.store.system

import com.hb.nf.data.api.response.DataResponse
import com.hb.nf.data.entity.News
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by buihai on 9/9/17.
 */

interface SystemStore {


    interface LocalStorage {
    }

    interface RequestService {

        @GET("fy6ny7syutxl1yd/newsfeed.json")
        fun getNewsfeed() : Observable<DataResponse<News>>

        @GET("v83n38kvsm6qw62/detail.json")
        fun getDetail() : Observable<News>

//        @GET("users")
//        fun getUsers(
//            @Query("page") page: Int,
//            @Query("pageSize") pageSize: Int = 30,
//            @Query("site") site: String = "stackoverflow"
//        ): Observable<UsersResponse>
//
//        @GET("users/{USER_ID}/reputation-history")
//        fun getReputationByUser(
//            @Path("USER_ID") userId: Int,
//            @Query("page") page: Int,
//            @Query("pageSize") pageSize: Int = 30,
//            @Query("site") site: String = "stackoverflow"
//        ): Observable<ReputationResponse>
    }
}
