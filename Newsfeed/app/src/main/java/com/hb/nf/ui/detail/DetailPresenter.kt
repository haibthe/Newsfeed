package com.hb.nf.ui.detail

import com.hb.lib.mvp.impl.lce.HBMvpLcePresenter
import com.hb.nf.common.AppConstants
import com.hb.nf.data.entity.Section
import com.hb.nf.data.repository.SystemRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailPresenter
@Inject constructor(
    private var repository: SystemRepository
) : HBMvpLcePresenter<DetailActivity>(), DetailContract.Presenter {

    override fun loadData(pullToRefresh: Boolean) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh)
        }

        val funcDetails = repository.getDetail()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe({
                val content = HashMap<String, String>()
                val date = AppConstants.parseDate.parse(it.publishedDate)
                content["title"] = it.title
                content["date"] = AppConstants.formatDate.format(date)
                content["description"] = it.description

                val section = Section(0, content)
                it.sections.add(0, section)

                if (isViewAttached()) {
                    getView().setData(it.sections)
                    getView().showContent()
                }
            }, {
                if (isViewAttached()) {
                    getView().showError(it, pullToRefresh)
                }
            })

        disposable.add(funcDetails)
    }
}