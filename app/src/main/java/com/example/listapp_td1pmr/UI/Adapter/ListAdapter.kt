package com.example.listapp_td1pmr.UI.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp_td1pmr.R


class ListAdapter (private val actionListener: ActionListener,
                   private val idListe:MutableList<Int>,
                   private val myList: MutableList<String>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater =LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.liste_names,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return myList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myList[position], idListe[position],actionListener)
    }


    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val titreElem = itemView.findViewById<TextView>(R.id.item_titre_elem)

        fun bind(myItem: String, id: Int, actionListener: ActionListener) {
            titreElem.text= myItem
            itemView.setOnClickListener{
                actionListener.onListClicked(id,myItem)
            }
        }
    }





    interface ActionListener {
        fun onListClicked(id: Int, label:String)
    }




}