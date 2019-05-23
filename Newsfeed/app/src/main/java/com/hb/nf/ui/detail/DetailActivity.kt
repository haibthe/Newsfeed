package com.hb.nf.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.hb.lib.mvp.impl.lce.sr.HBMvpLceSRActivity
import com.hb.lib.utils.ui.ThemeUtils
import com.hb.nf.R
import com.hb.nf.app.App
import com.hb.nf.data.entity.Section
import com.hb.uiwidget.recyclerview.BaseAdapter
import com.hb.uiwidget.recyclerview.BaseViewHolder
import timber.log.Timber

class DetailActivity : HBMvpLceSRActivity<List<Section>, DetailPresenter>(), DetailContract.View {

    override fun getResLayoutId(): Int {
        return R.layout.activity_lce_sr_search
    }

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLceViewHolder.contentView.isEnabled = false

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)

            actionBar.title = "Detail"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setData(data: List<Section>) {
        val adapter = getAdapter<DetailAdapter>()
        adapter.data = data
    }

    override fun setupRecylcerView(addItemDecoration: Boolean) {
        super.setupRecylcerView(false)

        val rv = getRecyclerView()
        val padding = ThemeUtils.dpToPx(this, 16)
        val padding2 = padding / 2
        rv.setPadding(
            padding,
            padding2,
            padding,
            padding2
        )
    }

    override fun createAdapter(context: Context, recyclerView: RecyclerView): RecyclerView.Adapter<*> {
        val adapter = DetailAdapter(context, recyclerView)
        return adapter
    }

    class TitleViewHolder(itemView: View) : BaseViewHolder<Section>(itemView) {
        @BindView(R.id.tv_title)
        lateinit var title: TextView

        @BindView(R.id.tv_date)
        lateinit var dateView: TextView

        @BindView(R.id.tv_description)
        lateinit var description: TextView

        override fun bindData(data: Section) {
            title.text = data.context["title"].toString()
            dateView.text = data.context["date"].toString()
            description.text = data.context["description"].toString()

        }
    }

    class TextViewHolder(itemView: View) : BaseViewHolder<Section>(itemView) {
        @BindView(R.id.tv_text)
        lateinit var textView: TextView

        override fun bindData(data: Section) {
            textView.text = data.context["text"].toString()
        }
    }

    class VideoViewHolder(itemView: View) : BaseViewHolder<Section>(itemView) {
        @BindView(R.id.iv_preview)
        lateinit var preview: ImageView
        @BindView(R.id.tv_caption)
        lateinit var caption: TextView

        override fun bindData(data: Section) {
            try {
                val previewData = data.context.getValue("preview_image") as Map<String, String>
                Timber.d("$previewData")

//                val type = object: TypeToken<HashMap<String, String>>() {}.type
//                val jsData = Gson().fromJson<HashMap<String, String>>(previewData.toString(), type)
                App.imageHelper.loadBanner(preview, previewData["href"].toString())
            } catch (e: Exception) {
                Timber.e(e)
            }

            caption.text = data.context["caption"].toString()
        }
    }

    class ImageViewHolder(itemView: View) : BaseViewHolder<Section>(itemView) {
        @BindView(R.id.iv_preview)
        lateinit var preview: ImageView
        @BindView(R.id.tv_caption)
        lateinit var caption: TextView

        override fun bindData(data: Section) {

            App.imageHelper.loadBanner(preview, data.context["href"])

            caption.text = data.context["caption"].toString()
        }
    }

    class DetailAdapter(
        context: Context,
        recyclerView: RecyclerView
    ) : BaseAdapter<List<Section>, BaseViewHolder<Section>>(context, recyclerView) {

        companion object {
            const val VT_TITLE = 0
            const val VT_TEXT = 1
            const val VT_VIDEO = 2
            const val VT_IMAGE = 3
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Section> {
            return when (viewType) {
                VT_TITLE -> {
                    val itemView = mInflater.inflate(R.layout.itemview_title, parent, false)
                    TitleViewHolder(itemView)
                }
                VT_TEXT -> {
                    val itemView = mInflater.inflate(R.layout.itemview_text, parent, false)
                    TextViewHolder(itemView)

                }
                VT_IMAGE -> {
                    val itemView = mInflater.inflate(R.layout.itemview_image, parent, false)
                    ImageViewHolder(itemView)
                }
                VT_VIDEO -> {
                    val itemView = mInflater.inflate(R.layout.itemview_video, parent, false)
                    VideoViewHolder(itemView)
                }
                else -> {
                    val itemView = mInflater.inflate(R.layout.itemview_text, parent, false)
                    TitleViewHolder(itemView)
                }
            }
        }

        override fun onBindViewHolder(holder: BaseViewHolder<Section>, position: Int) {
//            super.onBindViewHolder(holder, position)

            val section = getItem<Section>(position)!!
            holder.bindData(section)
        }

        override fun getItemViewType(position: Int): Int {
            if (position == 0) {
                return VT_TITLE
            }
            val section = getItem<Section>(position)!!
            return section.type

        }


        override fun getItemCount(): Int {
            if (mData == null)
                return 0
            return mData!!.size
        }
    }

}