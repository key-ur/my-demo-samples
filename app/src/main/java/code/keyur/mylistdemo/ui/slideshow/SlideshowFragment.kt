package code.keyur.mylistdemo.ui.slideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import code.keyur.mylistdemo.R
import code.keyur.mylistdemo.databinding.FragmentSlideshowBinding
import code.keyur.mylistdemo.utils.PaginationScrollListener

class SlideshowFragment : Fragment(), MyRecyclerViewAdapter.OnLikeButtonTappedListener {

    private val TAG = SlideshowFragment::class.java.simpleName
    private lateinit var slideshowViewModel: SlideshowViewModel
    private var _binding: FragmentSlideshowBinding? = null
    private lateinit var recyclerViewAdapter: MyRecyclerViewAdapter
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var searchString: String? = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textSlideshow
//        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setListeners()
        setObservers()
        loadData()

    }

    private fun initViews(view: View) {
        _binding?.recyclerView?.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        val animation =
//            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_fall_down)
//        _binding?.recyclerView?.layoutAnimation = animation
        recyclerViewAdapter = MyRecyclerViewAdapter()
        _binding?.recyclerView?.adapter = recyclerViewAdapter
    }

    private fun setListeners() {
        (_binding?.recyclerView?.adapter as MyRecyclerViewAdapter).setOnLikeButtonTapListener(this)
        _binding?.recyclerView?.addOnScrollListener(object :
            PaginationScrollListener(_binding?.recyclerView?.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                Log.d(TAG, "isLastPage: $isLastPage")
                return isLastPage
            }

            override fun isLoading(): Boolean {
                Log.d(TAG, "isLoading: $isLoading")
                return isLoading
            }

            override fun loadMoreItems() {
                if (searchString.isNullOrEmpty()) {
                    isLoading = true
                    Log.d(TAG, "loadMoreItems: fetch more items..")
                    _binding!!?.recyclerView?.adapter?.let { slideshowViewModel.fetchMoreData(it.itemCount) }
                }
            }

        })

        // -- search-filter records -- //
        _binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit: query: $query submitted..")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchString = newText
                Log.d(TAG, "onQueryTextChange: searched-text: $newText")
//                if (!newText.isNullOrBlank() && newText.length > 1) {
                (_binding?.recyclerView?.adapter as MyRecyclerViewAdapter).filter.filter(newText)
//                }
                return true
            }

        })
    }

    private fun loadData() {
//        slideshowViewModel.loadDummyData()
//        slideshowViewModel.fetchDataFromServer()
        slideshowViewModel.loadData()
    }


    private fun setObservers() {
        slideshowViewModel._sampleData.observe(requireActivity(), {
            Log.d(TAG, "setObservers: _sampleData: ${it.size}")
            if (!it.isNullOrEmpty()) {
//                (_binding?.recyclerView?.adapter as MyRecyclerViewAdapter).addData(it)
            }
        })

        slideshowViewModel._records.observe(requireActivity(), {
            Log.d(TAG, "setObservers: _records: ${it.size}")
            if (it.isNotEmpty()) {
                (_binding?.recyclerView?.adapter as MyRecyclerViewAdapter).addData(it)
            }
        })

        slideshowViewModel.miscResults.observe(requireActivity(), {
            Log.d(TAG, "setObservers: miscResults: $it")
            if (it > 0) {
                isLoading = false
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLikeButtonTapped(record: Record, position: Int, isLiked: Boolean) {
        record.isLiked = isLiked
//        Log.d(TAG, "onLikeButtonTapped: record to update: $record -- isLiked: $isLiked")
        Log.d(TAG, "onLikeButtonTapped: after assignment..")
        slideshowViewModel.updateRecord(record)
    }


}