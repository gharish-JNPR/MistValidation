package com.mist.mistvalidation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mist.mistvalidation.R

class OrgAdapter : RecyclerView.Adapter<OrgAdapter.ViewHolder>() {

    private var orgList: List<String> = listOf()
    override fun getItemCount(): Int {
        return orgList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.org_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setOrdNames(orgs : List<String>){
        this.orgList = orgs
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var txtOrgName: TextView = itemView.findViewById(R.id.txt_org_name)

        fun bind(position: Int) {
            txtOrgName.text = orgList[position]
        }
    }
}