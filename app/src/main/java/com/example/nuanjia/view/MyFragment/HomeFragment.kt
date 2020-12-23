package com.example.nuanjia.view.MyFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.nuanjia.R
import com.example.nuanjia.model.bean.DataBean
import com.example.nuanjia.view.RecommendAdapter
import com.example.nuanjia.view.home.CalendarActivity
import com.example.nuanjia.view.home.MusicActivity
import com.example.nuanjia.view.home.SearchActivity
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val recommendList = ArrayList<Recommend>()

    class Recommend(val information:String, val image:Int)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //轮播图
        val banner: Banner<DataBean, BannerImageAdapter<DataBean>> = view.findViewById(R.id.banner)

        banner.setAdapter(object : BannerImageAdapter<DataBean>(DataBean.testData3) {
            override fun onBindView(
                holder: BannerImageHolder,
                data: DataBean,
                position: Int,
                size: Int
            ) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                    .load(data.imageUrl)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                    .into(holder.imageView)
            }
        }).addBannerLifecycleObserver(this).indicator = CircleIndicator(this.activity)


        //搜索
        search.setOnClickListener {
            val intent = Intent(activity,SearchActivity::class.java)
            startActivity(intent)
        }

        //日历
        button1.setOnClickListener {
            val intent = Intent(activity, CalendarActivity::class.java)
            startActivity(intent)
        }

        //音乐
        button2.setOnClickListener {
            val intent = Intent(activity,MusicActivity::class.java)
            startActivity(intent)
        }



        initRecommend()
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerview.layoutManager = layoutManager
        val adapter = RecommendAdapter(recommendList)
        recyclerview.adapter = adapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun initRecommend(){
        repeat(2){
            recommendList.add(Recommend("适合送给对所有有手机的人的礼物-手机工具卡",R.drawable.ic_like))
            recommendList.add(Recommend("适合送给所有爱美之人的礼物-防水喷雾 ",R.drawable.ic_collect))
            recommendList.add(Recommend("适合送给有强迫症人的礼物-《战胜强迫症》",R.drawable.ic_like))
            recommendList.add(Recommend("送学生同学文具",R.drawable.ic_collect))
            recommendList.add(Recommend("适合送给坐办公室的人的礼物-蜂窝坐垫",R.drawable.ic_collect))
            recommendList.add(Recommend("情人送围巾",R.drawable.ic_like))
        }
    }
}







