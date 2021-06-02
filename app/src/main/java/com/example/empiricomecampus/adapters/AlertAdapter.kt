package com.example.empiricomecampus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.empiricomecampus.databinding.RvItemAlertsBinding
import com.example.empiricomecampus.models.Alert
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class AlertAdapter(
    options: FirebaseRecyclerOptions<Alert>,
    private val onClickListener: OnClickListener
) : FirebaseRecyclerAdapter<Alert, AlertAdapter.AlertHolder>(options) {


    private val _progressBarShow = MutableLiveData<Boolean?>()
    val progressBar: LiveData<Boolean?>
        get() = _progressBarShow

    class AlertHolder(private var binding: RvItemAlertsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(alert: Alert, onClickListener: OnClickListener) {
            binding.tVType.text = alert.type
            binding.tvDetails.text = alert.text

            binding.cardView.setOnClickListener {
                onClickListener.onClick(alert)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertHolder {
        return AlertHolder(
            RvItemAlertsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: AlertHolder, position: Int, model: Alert) {
        val item = getItem(position)
        holder.bind(item, onClickListener)

    }

    override fun onDataChanged() {
        super.onDataChanged()
        _progressBarShow.value = true
    }


    class OnClickListener(val clickListener: (alert: Alert) -> Unit) {
        fun onClick(alert: Alert) = clickListener(alert)
    }


}
