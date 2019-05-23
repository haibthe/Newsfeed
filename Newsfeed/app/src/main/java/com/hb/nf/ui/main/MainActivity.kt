package com.hb.nf.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.google.android.material.tabs.TabLayout
import com.hb.lib.mvp.impl.lce.sr.HBMvpLceSRActivity
import com.hb.nf.R
import com.hb.nf.app.App
import com.hb.nf.common.AppConstants
import com.hb.nf.data.entity.News
import com.hb.nf.data.entity.dw.DataWrapper
import com.hb.nf.navigation.Navigator
import com.hb.uiwidget.recyclerview.BaseAdapter
import com.hb.uiwidget.recyclerview.BaseViewHolder
import com.hb.uiwidget.recyclerview.EndlessRecyclerViewListener
import com.hb.uiwidget.recyclerview.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : HBMvpLceSRActivity<List<DataWrapper<News>>, MainPresenter>(), MainContract.View {

    override fun getResLayoutId(): Int {
        return R.layout.activity_main
    }

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.tablayout)
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        mLceViewHolder.contentView.isEnabled = false

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                loadBy(tab.text.toString())
            }
        })
    }


    override fun loadBy(type: String) {
        mPresenter.filter(type)
    }

    override fun updateTablayout(data: Array<String>) {
        tablayout.removeAllTabs()
        val tab = tablayout.newTab()
        tab.text = "Tất cả"
        tablayout.addTab(tab, 0)
        data.forEach {
            val tab = tabLayout.newTab()
            tab.text = it
            tabLayout.addTab(tab)
        }

    }

    override fun setupRecylcerView(addItemDecoration: Boolean) {
        super.setupRecylcerView(false)
        val rv = getRecyclerView()
        rv.addOnScrollListener(object : EndlessRecyclerViewListener() {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
//                if (mLoadType == LOAD_ALL) {
//                    mPresenter.loadNextPage()
//                }
            }
        })
    }

    override fun createAdapter(context: Context, recyclerView: RecyclerView): RecyclerView.Adapter<*> {
        val adapter = MyAdapter(context, recyclerView)
        adapter.setOnItemClickListener(OnItemClickListener { anchor, obj, position ->
            if (obj is DataWrapper<*>) {
                mPresenter.setNewsSelected(obj.getData() as News)
            }
            Navigator.startDetail(this)
        })

        return adapter
    }

    override fun setData(data: List<DataWrapper<News>>) {
        val adapter = getAdapter<MyAdapter>()
        adapter.data = data
    }

    class MyViewHolder(itemView: View) : BaseViewHolder<DataWrapper<*>>(itemView) {

        @BindView(R.id.tv_display_name)
        lateinit var title: TextView
        @BindView(R.id.tv_reputation)
        lateinit var reputation: TextView
        @BindView(R.id.tv_location)
        lateinit var location: TextView
        @BindView(R.id.tv_date)
        lateinit var dateView: TextView

        @BindView(R.id.image_view_avatar)
        lateinit var avatar: ImageView

        override fun bindData(data: DataWrapper<*>) {
            title.text = data.getTitle()
            reputation.text = data.getSubtitle()
            location.text = data.getDescription()
            App.imageHelper.loadAvatar(avatar, data.getIcon())

            val news = data.getData() as News
            val date = AppConstants.parseDate.parse(news.publishedDate)
            dateView.text = AppConstants.formatDate.format(date)

        }
    }


    class MyAdapter(
        context: Context, rv: RecyclerView
    ) : BaseAdapter<List<DataWrapper<News>>, BaseViewHolder<*>>(context, rv) {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
            val itemView = mInflater.inflate(R.layout.itemview_news, parent, false)
            return MyViewHolder(itemView)

        }

        override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
            if (holder is MyViewHolder) {
                super.onBindViewHolder(holder, position)
                val data = getItem<DataWrapper<News>>(position)!!
                holder.bindData(data)
            }
        }

        override fun getItemCount(): Int {
            if (mData == null)
                return 0
            val size = mData!!.size
            return size
        }


    }
}
