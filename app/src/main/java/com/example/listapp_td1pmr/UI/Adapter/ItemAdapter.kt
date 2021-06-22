package com.example.listapp_td1pmr.UI.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp_td1pmr.R

class ItemAdapter(private val actionListener: ActionListener2,
                  private val idItem:MutableList<String>,
                  private val itemChecks:MutableList<Int>,
                  private val itemLabels: MutableList<String>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater =LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.liste_items,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemLabels.count()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(idItem[position], itemLabels[position],itemChecks[position],actionListener)
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cbElem = itemView.findViewById<CheckBox>(R.id.checkBox)

        fun bind(idItem:String, label:String, checked:Int, actionListener: ActionListener2) {
            cbElem.text= label
            cbElem.isChecked = checked == 1
            cbElem.setOnClickListener{
                actionListener.onItemClicked(idItem,1-checked)
            }
        }
    }

    interface ActionListener2 {
        fun onItemClicked(idItem:String,checked:Int)
    }


}