package code.keyur.mylistdemo.ui.slideshow

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.BackgroundColorSpan
import androidx.core.content.res.ResourcesCompat
import code.keyur.mylistdemo.R
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import java.util.*


/**
 *  + What happens in this class?
=>


 */
class MyRecyclerViewAdapter : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>(),
    Filterable {

    private val TAG = MyRecyclerViewAdapter::class.java.simpleName
    private val data = mutableListOf<Record>()
    private val originalData = mutableListOf<Record>()
    private var searchString = ""
    private var lastPosition = -1
    private lateinit var onIsLikeButtonTapListener: OnLikeButtonTappedListener

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        OnLikeButtonTappedListener {
        //        val imgThumb = itemView.findViewById<ImageView>(R.id.thumbnail_view)
        val txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        val txtDesc = itemView.findViewById<TextView>(R.id.txtAddress)

        //        val toggleButtonLike = itemView.findViewById<ToggleButton>(R.id.toggleButtonLike)
        val imageLike = itemView.findViewById<ImageView>(R.id.imageLike)

        init {

        }

        fun bindView(position: Int) {

            if (data[position].hospitalname?.isNotEmpty() == true) {
                txtTitle.text = data[position].hospitalname
//                val display: Display = getWindowManager().getDefaultDisplay()
//                tryFlowText(data[position].hospitalname, imgThumb, txtTitle, itemView.context.)
            } else {
                txtTitle.text = "--"
            }

            if (data[position].hospitaladdress?.isNotEmpty() == true) {
                txtDesc.text = data[position].hospitaladdress
            } else {
                txtDesc.text = "!!"
            }
//
//            toggleButtonLike.setOnCheckedChangeListener { _, isChecked ->
//                data[position].isLiked = isChecked
//                Log.d(TAG, "bindView: ${data[position].hospitalname} - ${data[position].isLiked}")
//                loadIsLikedButton(position)
//            }

            imageLike.setOnClickListener {
                data[position].isLiked = !data[position].isLiked
                Log.d(TAG, "bindView: ${data[position].hospitalname} - ${data[position].isLiked}")
                onIsLikeButtonTapListener.onLikeButtonTapped(
                    data[position],
                    position,
                    data[position].isLiked
                )
                loadIsLikedButton(position)
            }

            loadIsLikedButton(position)

        }


        override fun onLikeButtonTapped(record: Record, position: Int, isLiked: Boolean) {
            onIsLikeButtonTapListener.onLikeButtonTapped(record, position, isLiked)
        }

        private fun loadIsLikedButton(position: Int) {
            Log.d(
                TAG,
                "loadIsLikedButton: ${data[position].hospitalname} : ${data[position].isLiked}"
            )
            if (data[position].isLiked) {
                imageLike.background = ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_baseline_thumb_up_alt_24,
                    null
                )
//                imageLike.backgroundTintList = ColorStateList(
//                    arrayOf(
//                        intArrayOf(
//                            android.R.attr.state_selected
//                        ), intArrayOf()
//                    ), intArrayOf(Color.GREEN, Color.BLUE)
//                )

            } else {
                imageLike.background = ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_baseline_thumb_up_grey_24,
                    null
                )
//                imageLike.backgroundTintList = ColorStateList(
//                    arrayOf(
//                        intArrayOf(
//                            android.R.attr.state_selected
//                        ), intArrayOf()
//                    ), intArrayOf(Color.RED, Color.YELLOW)
//                )
            }
        }

    }

    interface OnLikeButtonTappedListener {
        fun onLikeButtonTapped(record: Record, position: Int, isLiked: Boolean)
    }

    fun setOnLikeButtonTapListener(onLikeButtonTappedListener: OnLikeButtonTappedListener) {
        this.onIsLikeButtonTapListener = onLikeButtonTappedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(position)
        setAnimation(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addData(it: List<Record>) {
//        data.clear()
        if (it != null) {
            if (data.containsAll(it)) {
                Log.d(TAG, "addData: data already contains new-data: $it")
            } else {
                data.addAll(it)
            }
            Log.d(TAG, "addData: ${data.size}")
        }
        notifyDataSetChanged()
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
//        super.onViewDetachedFromWindow(holder)
        clearAnimation(holder.itemView)
    }

    /**
     * Here is the key method to apply the animation
     */
    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.item_animation_fall_down)
            animation.duration = 350
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    fun clearAnimation(mRootLayout: View) {
        mRootLayout.clearAnimation()
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)

//        tryFlowText(
//            data[holder.adapterPosition].hospitaladdress,
//            holder.imgThumb,
//            holder.txtTitle,
//            holder.itemView.display
//        )
        if (searchString.isNotBlank()) {
            highlightSearchString(holder.txtTitle)
            highlightSearchString(holder.txtDesc)
        }
    }

    override fun getFilter(): Filter {
        if (originalData.isNullOrEmpty()) {
            originalData.addAll(data)
        }
        Log.d(TAG, "getFilter: original-data: ${originalData.size}")
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchWords = constraint.toString()
                searchString = searchWords
                val filterResults = FilterResults()
                return if (searchWords.length > 1) {
                    val filteredList =
                        originalData.filter { it.toString().lowercase().contains(searchWords) }
                    Log.d(
                        TAG,
                        "performFiltering: filtered-list: for \"$searchWords\"${filteredList.size}"
                    )
                    filterResults.values = filteredList
                    filterResults
                } else {
                    Log.d(TAG, "performFiltering: ")
                    filterResults
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                // if search-string is blank or empty and results is null or empty, load original data
                var templist = mutableListOf<Record>()
                if (constraint.isNullOrBlank()) {
                    data.clear()
                    data.addAll(originalData)
                } else if (results != null && results.values != null) {
                    templist = results.values as MutableList<Record>
                    data.clear()
                    data.addAll(templist)
                }
                notifyDataSetChanged()
                Log.d(
                    TAG,
                    "publishResults: search-text: $constraint -- result: -- ${templist.size}"
                )
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return super.convertResultToString(resultValue)
            }

        }
    }

    private fun highlightSearchString(textView: TextView) {
        if (searchString.isNullOrBlank()) {
            return
        }
        if (searchString.length <= 1) {
            return
        }
        var mask = searchString.lowercase(Locale.getDefault())
        val highlightenabled = true
        var isHighlighted = false
        if (highlightenabled) {
            if (!TextUtils.isEmpty(textView.text)
                && !TextUtils.isEmpty(mask)
            ) {
                val textLC = textView.text.toString().lowercase()
                mask = mask.lowercase()
                if (textLC.contains(mask)) {
                    var ofe = textLC.indexOf(mask, 0)
                    val wordToSpan: Spannable = SpannableString(textView.text)
                    var ofs = 0
                    while (ofs < textLC.length && ofe != -1) {
                        ofe = textLC.indexOf(mask, ofs)
                        isHighlighted = if (ofe == -1) {
                            break
                        } else {
                            // set color here
                            wordToSpan.setSpan(
                                BackgroundColorSpan(-0x100), ofe, ofe + mask.length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            textView.setText(wordToSpan, TextView.BufferType.SPANNABLE)
                            true
                        }
                        ofs = ofe + 1
                    }
                }
            }
        }
        if (!isHighlighted) {
//                txtTitle.text = text
        }

    }

}