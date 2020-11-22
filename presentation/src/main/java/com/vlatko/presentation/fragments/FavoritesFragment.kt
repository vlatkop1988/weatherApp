package com.vlatko.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlatko.presentation.R
import com.vlatko.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.rv_item_favorite_city.view.*

class FavoritesFragment : BaseFragment() {

    private lateinit var adapter: Adapter
    private lateinit var viewModel: FavoritesFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorite, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoritesFragmentViewModel::class.java)
        adapter = Adapter()
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        tvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_favoritesFragment_to_searchFragment)
        }

        adapter.apply {
            setItems(viewModel.getFavoriteCities())
            onItemRemove = { name, position ->
                viewModel.removeFavoriteCity(name)
                adapter.removeAtPosition(position)
            }
            onItemClick = {
                val bundle = bundleOf("fromFavorites" to true, "cityName" to it)
                findNavController().navigate(R.id.action_favoritesFragment_to_weatherOverviewFragment3, bundle)
            }
        }
    }
}

class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<String>()
    fun setItems(items: List<String>?) {
        this.items.clear()
        this.items.addAll(items ?: ArrayList())
        notifyDataSetChanged()
    }

    fun removeAtPosition(p: Int) {
        items.removeAt(p)
        notifyItemRemoved(p)
    }

    var onItemClick: ((name: String) -> Unit)? = null
    var onItemRemove: ((name: String, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.rv_item_favorite_city, parent, false)
        )

    override fun getItemCount() = items.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        with(holder.itemView) {
            tvCityName.text = items[holder.adapterPosition]
            ibDelete.setOnClickListener {
                onItemRemove?.invoke(items[holder.adapterPosition], holder.adapterPosition)
            }
            setOnClickListener {
                onItemClick?.invoke(items[holder.adapterPosition])
            }
        }
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
