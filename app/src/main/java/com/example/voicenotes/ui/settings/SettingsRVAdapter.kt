package com.example.voicenotes.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicenotes.data.Setting
import com.example.voicenotes.databinding.SettingsItemBinding
import com.example.voicenotes.utils.OnItemClickListener

class SettingsRVAdapter(private val settings: List<Setting>, private val clickListener: OnItemClickListener<Setting>):
    RecyclerView.Adapter<SettingsRVAdapter.SettingsViewHolder>() {

    inner class SettingsViewHolder(private val binding: SettingsItemBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(setting: Setting, action: OnItemClickListener<Setting>) {
                binding.apply {
                    settingsItemLogoSiv.setImageResource(setting.logo)
                    settingsItemNameTv.text = setting.name

                    itemView.setOnClickListener{
                        action.onItemClick(setting, adapterPosition)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val binding = SettingsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SettingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val currentSetting = settings[position]
        holder.bind(currentSetting, clickListener)
    }

    override fun getItemCount(): Int {
        return settings.size
    }
}