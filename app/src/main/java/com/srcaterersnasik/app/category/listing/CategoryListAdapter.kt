package com.srcaterersnasik.app.category.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.srcaterersnasik.R
import com.srcaterersnasik.model.Category

class CategoryListAdapter : RecyclerView.Adapter<CategoryListAdapter.CategoryListAdapterHolder>() {
    private val categoryList: ArrayList<Category> = ArrayList()
    private var _listener: ((category:Category)->Unit)? = null
    val listener get() = _listener

    fun setOnItemClickListener(listener: (category:Category)->Unit){
        _listener = listener
    }

    class CategoryListAdapterHolder(view: View) : RecyclerView.ViewHolder(view) {
        var categoryName = view.findViewById<TextView>(R.id.category_tv)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryListAdapterHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return CategoryListAdapterHolder(view)
    }

    override fun onBindViewHolder(
        holder: CategoryListAdapterHolder,
        position: Int,
    ) {
        val category = categoryList[position]
        holder.categoryName.text = category.name
        holder.itemView.setOnClickListener {
            listener?.let { listener1 -> listener1(category) }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun addCategories(categories: ArrayList<Category>) {
        categoryList.addAll(categories)
    }

    fun addCategory(category: Category) {
        categoryList.add(category)
    }

    fun clearAllItems() {
        categoryList.clear()
    }
}