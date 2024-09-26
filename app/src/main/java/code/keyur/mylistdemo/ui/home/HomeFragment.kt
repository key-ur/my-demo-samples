package code.keyur.mylistdemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import code.keyur.mylistdemo.databinding.FragmentHomeBinding
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.text.Spanned

import code.keyur.mylistdemo.utils.MyLeadingMarginSpan22

import android.text.SpannableString

import android.text.Html

import android.graphics.Rect


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var finalHeight: Int = 0
    private var finalWidth: Int = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
            val vto: ViewTreeObserver = _binding!!.imageView1.viewTreeObserver
            vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    _binding!!.imageView1.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    finalHeight = _binding!!.imageView1.measuredHeight
                    finalWidth = _binding!!.imageView1.measuredWidth
                    makeSpan()
                }
            })
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * This method builds the text layout
     */
    private fun makeSpan() {
        /**
         * Get the text
         */
        /**
         * Get the text
         */
        val plainText = "rlhkifu  ukyg iuoh iguy iug  kuyg h iujh uyguygesources.getString(R.string.text_samplekjl iug utgb iuh y6g iuh uih utyfu ih ub y6f uyjb iug yfg ujh uiyg ytf uyjg )"

        val htmlText = Html.fromHtml(plainText)
        val mSpannableString = SpannableString(htmlText)

        val allTextStart = 0
        val allTextEnd = htmlText.length - 1

        /**
         * Calculate the lines number = image height.
         * You can improve it... it is just an example
         */
        /**
         * Calculate the lines number = image height.
         * You can improve it... it is just an example
         */
        val lines: Int
        val bounds = Rect()
        _binding?.textView1?.getPaint()?.getTextBounds(plainText.substring(0, 10), 0, 1, bounds)


        //float textLineHeight = mTextView.getPaint().getTextSize();

        //float textLineHeight = mTextView.getPaint().getTextSize();
        val fontSpacing: Float? = _binding?.textView1?.getPaint()?.getFontSpacing()
        lines = ((finalHeight / fontSpacing!!).toInt())

        /**
         * Build the layout with LeadingMarginSpan2
         */
        /**
         * Build the layout with LeadingMarginSpan2
         */
        val span = MyLeadingMarginSpan22(lines, finalWidth - 80)
        mSpannableString.setSpan(span, allTextStart, allTextEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)


        _binding?.textView1?.setText(mSpannableString)
    }


}