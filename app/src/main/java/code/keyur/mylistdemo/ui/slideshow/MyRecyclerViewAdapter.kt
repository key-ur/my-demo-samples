package code.keyur.mylistdemo.ui.slideshow

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import code.keyur.mylistdemo.R


/**
 *  + What happens in this class?
=>


 */
class MyRecyclerViewAdapter : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    private val TAG = MyRecyclerViewAdapter::class.java.simpleName
    private val data = mutableListOf<Record>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgThumb = itemView.findViewById<ImageView>(R.id.imgThumb)
        val txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        val txtDesc = itemView.findViewById<TextView>(R.id.txtDesc)
        val toggleButtonLike = itemView.findViewById<ToggleButton>(R.id.toggleButtonLike)

        init {

        }

        fun bindView(position: Int) {

            if (data[position].hospitalname?.isNotEmpty() == true) {
                txtTitle.text = data[position].hospitalname
            } else {
                txtTitle.text = "--"
            }

            if (data[position].hospitaladdress?.isNotEmpty() == true) {
                txtDesc.text = data[position].hospitaladdress
            } else {
                txtDesc.text = "!!"
            }

            toggleButtonLike.setOnCheckedChangeListener { _, isChecked ->
                data[position].isLiked = isChecked
                Log.d(TAG, "bindView: ${data[position].hospitalname} - ${data[position].isLiked}")
                loadIsLikedButton(position)
            }

            loadIsLikedButton(position)

        }

        private fun loadIsLikedButton(position: Int) {
            if (data[position].isLiked) {
                toggleButtonLike.background = ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_baseline_thumb_up_alt_24,
                    null
                )
                toggleButtonLike.backgroundTintList = ColorStateList(
                    arrayOf(
                        intArrayOf(
                            android.R.attr.state_selected
                        ), intArrayOf()
                    ), intArrayOf(Color.GREEN, Color.BLUE)
                )

            } else {
                toggleButtonLike.background = ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_baseline_thumb_up_alt_24,
                    null
                )
                toggleButtonLike.backgroundTintList = ColorStateList(
                    arrayOf(
                        intArrayOf(
                            android.R.attr.state_selected
                        ), intArrayOf()
                    ), intArrayOf(Color.RED, Color.YELLOW)
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addData(it: List<Record>) {
        data.clear()
        if (it != null) {
            data.addAll(it)
            Log.d(TAG, "addData: $data")
        }
        notifyDataSetChanged()
    }
}