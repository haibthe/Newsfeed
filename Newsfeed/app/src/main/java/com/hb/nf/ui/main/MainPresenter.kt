package com.hb.nf.ui.main

import com.hb.lib.mvp.impl.lce.HBMvpLcePresenter
import com.hb.nf.data.DataManager
import com.hb.nf.data.entity.News
import com.hb.nf.data.entity.dw.DataWrapper
import com.hb.nf.data.repository.SystemRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter
@Inject constructor(
    private var repository: SystemRepository
) : HBMvpLcePresenter<MainActivity>(), MainContract.Presenter {

    private val mData = ArrayList<DataWrapper<News>>()
    private val mDataTemp = ArrayList<DataWrapper<News>>()

    private val mDataFilter = ArrayList<DataWrapper<News>>()

    private val mTabsContent = HashSet<String>()


    private var mPage = 1
    private var isLoadNext = true


    override fun loadData(pullToRefresh: Boolean) {
        if (pullToRefresh) {
            mDataTemp.clear()
            mDataTemp.addAll(mData)
            mTabsContent.clear()
            mData.clear()
            if (isViewAttached()) {
                getView().setData(mDataTemp)
            }
            mPage = 1
        }

        if (isViewAttached()) {
            if (mPage == 1) {
                getView().showLoading(pullToRefresh)
            }
        }

        val dm = dataManager<DataManager>()

        disposable.clear()
        disposable.addAll(
            repository.getNewsfeed(mPage)
                .doOnNext {
                    if (it.isEmpty()) isLoadNext = false
                }
                .flatMap {
                    Observable.fromIterable(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mTabsContent.add(it.getData().contentType)
                    mData.add(it)
                }, { error ->
                    if (isViewAttached()) {
                        getView().showError(error, pullToRefresh)
                    }
                }, {
                    if (isViewAttached()) {
                        getView().apply {
                            var arr = Array(mTabsContent.size) {""}
                            arr = mTabsContent.toArray(arr)
                            updateTablayout(arr)
                            setData(mData)
                            showContent()
                        }
                    }
                })
        )
    }

    override fun destroy() {
        mDataFilter.clear()
        mData.clear()
        mDataTemp.clear()
        super.destroy()
    }

    override fun loadNextPage() {
        if (!isLoadNext) return
        mPage++
        loadData(pullToRefresh = false)
    }


    override fun setNewsSelected(news: News) {
        dataManager<DataManager>().setNews(news)
    }

    override fun filter(name: String) {
        mDataFilter.clear()

        if (isViewAttached()) {
            getView().showLoading(false)
        }

        val filterFunc = Observable.create<List<DataWrapper<News>>> {
            try {
                if (name == "Tất cả") {
                    it.onNext(mData)
                } else {
                    val nameLowerCase = name.toLowerCase().trim().hashCode()
                    val hc = nameLowerCase.hashCode()
                    val data = mData.filter { ndw ->
                        val news = ndw.getData()
                        val hashCode = news.contentType.toLowerCase().hashCode()
                        hc == hashCode
                    }
                    it.onNext(data)

                }
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe({
                mDataFilter.addAll(it)
                if (isViewAttached()) {
                    getView().apply {
                        setData(mDataFilter)
                        showContent()
                    }
                }
            }, {
                if (isViewAttached()) {
                    getView().showError(it, false)
                }
            })

        disposable.clear()
        disposable.add(filterFunc)

    }
}

