package com.example.mybudget
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mybudget.ListItem // Adjust the package path accordingly


class CustomAdapter(private val context: Context, private val itemList: List<ListItem>) : BaseAdapter() {

    // Returns the number of items in the list
    override fun getCount(): Int {
        return itemList.size
    }

    // Returns the item at the given position
    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    // Returns the item ID at the given position
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Creates the view for each item in the ListView
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        // Check if the view is being reused, if not, inflate a new one
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            viewHolder = ViewHolder(view) // Create ViewHolder
            view.tag = viewHolder // Store ViewHolder as a tag
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder // Retrieve ViewHolder from tag
        }

        // Get the current item data
        val currentItem = getItem(position) as ListItem

        // Bind the data to the view components
        viewHolder.nameTextView.text = currentItem.name
        viewHolder.iconImageView.setImageResource(currentItem.icon)

        return view
    }

    // ViewHolder pattern to optimize performance by avoiding repeated findViewById calls
    private class ViewHolder(view: View) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val iconImageView: ImageView = view.findViewById(R.id.iconImageView)
    }
}