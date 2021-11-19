package code.keyur.mylistdemo.ui.slideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import code.keyur.mylistdemo.R
import code.keyur.mylistdemo.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private val TAG = SlideshowFragment::class.java.simpleName
    private lateinit var slideshowViewModel: SlideshowViewModel
    private var _binding: FragmentSlideshowBinding? = null
    private lateinit var recyclerViewAdapter: MyRecyclerViewAdapter

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
        loadData()
        setObservers()

    }

    private fun loadData() {
        recyclerViewAdapter = MyRecyclerViewAdapter()
        _binding?.recyclerView?.adapter = recyclerViewAdapter
        slideshowViewModel.loadDummyData()
        slideshowViewModel.fetchDataFromServer()
    }

    private fun initViews(view: View) {
        _binding?.recyclerView?.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        val animation =
//            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_fall_down)
//        _binding?.recyclerView?.layoutAnimation = animation
    }

    private fun setObservers() {
        slideshowViewModel._sampleData.observe(requireActivity(), {
            Log.d(TAG, "setObservers: _sampleData: $it")
            if (!it.isNullOrEmpty()) {
//                (_binding?.recyclerView?.adapter as MyRecyclerViewAdapter).addData(it)
            }
        })

        slideshowViewModel._records.observe(requireActivity(), {
            Log.d(TAG, "setObservers: _records: $it")
            if (it.isNotEmpty()) {
                (_binding?.recyclerView?.adapter as MyRecyclerViewAdapter).addData(it)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}