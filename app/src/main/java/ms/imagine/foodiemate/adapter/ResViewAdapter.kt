package ms.imagine.foodiemate.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.data.Egg


/**
 * Created by eugen on 3/30/2018.
 */
class ResViewAdapter(private val myDataset: ArrayList<Egg>, private val context: Context) :
        RecyclerView.Adapter<ResViewAdapter.ViewHolder>() {

    private lateinit var onClick: OnItemClicked

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.

    fun setOnClick(onClick: OnItemClicked) {
        this.onClick = onClick
    }
    interface OnItemClicked {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var imgView: ImageView
        var timestamp: TextView
        var status: TextView
        var entity: CardView

        init {
            entity = view.findViewById<View>(R.id.card_view) as CardView
            imgView = view.findViewById<View>(R.id.imgview_thumbnail) as ImageView
            title = view.findViewById<View>(R.id.egg_tag) as TextView
            timestamp = view.findViewById<View>(R.id.egg_timestamp) as TextView
            status = view.findViewById<View>(R.id.egg_info) as TextView
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.card, parent, false) as View
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        var egg = myDataset[position]
        holder.title.text = egg.eggtag
        holder.timestamp.text = egg.displayTime()
        holder.status.text = egg.displayStatus()
        holder.imgView.setImageDrawable(egg.displayStatusThumbnail(context))
        holder.entity.setOnClickListener { onClick.onItemClick(position) }
        // holder.itemView.setTag(99, position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size


}