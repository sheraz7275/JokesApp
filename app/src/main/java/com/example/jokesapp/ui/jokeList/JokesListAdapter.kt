package com.example.jokesapp.ui.jokeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jokesapp.dataModels.Joke
import com.example.jokesapp.databinding.ItemJokesBinding
import com.example.jokesapp.utils.SetItemClickListener

class JokesListAdapter(
    private val list: List<Joke>, private val itemClickListener: SetItemClickListener
) :
    RecyclerView.Adapter<JokesListAdapter.JokeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemJokesBinding.inflate(layoutInflater, parent, false)

        return JokeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class JokeViewHolder(itemView: ItemJokesBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val mItemView = itemView

        fun bindData(data: Joke) {
            mItemView.mDataModel = data
            mItemView.root.setOnClickListener {
                itemClickListener?.onItemClick(data)
            }
            mItemView.detail.setOnClickListener {
                itemClickListener?.onItemClick(data)
            }
        }
    }

}