package com.example.empiricomecampus.adapters

import android.content.Context
import android.graphics.Color
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.empiricomecampus.R
import com.example.empiricomecampus.databinding.RvItemScheduleBinding
import com.example.empiricomecampus.models.Schedule
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ScheduleAdapter(
    options: FirebaseRecyclerOptions<Schedule>, val context: Context,
    val onClickListener: OnClickListener
) : FirebaseRecyclerAdapter<Schedule, ScheduleAdapter.ScheduleHolder>(options) {

    private val _progressBarShow = MutableLiveData<Boolean?>()
    val progressBar: LiveData<Boolean?>
        get() = _progressBarShow

    class ScheduleHolder(private var binding: RvItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Schedule,context: Context) {
            binding.tVDay.text = schedule.day
            binding.tVSemester.text = schedule.semester
            binding.tVSubject.text = schedule.subject
            binding.tVTip.text = schedule.type
            binding.tVAttendance.text = schedule.attendance
            binding.tVTime.text = context.getString(R.string.schedule_time, schedule.startTime, schedule.endTime)
            binding.tVAttendance.setTextColor(
                when (schedule.attendance) {
                    "F2F" -> Color.parseColor("#0000FF")
                    "WebEx" -> Color.parseColor("#00FF00")
                    else -> Color.parseColor("#FF0000")
                }
            )
            binding.tVTip.setTextColor(
                when (schedule.type) {
                    "P" -> Color.parseColor("#34e5eb")
                    "LV", "AV" -> Color.parseColor("#729438")
                    "ZUI", "ZPI", "T-I", "T-II" -> Color.parseColor("#87038a")
                    else -> Color.parseColor("#34e5eb")
                }
            )

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleHolder {
        return ScheduleHolder(RvItemScheduleBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: ScheduleHolder, position: Int, model: Schedule) {
        val item = getItem(position)
        holder.bind(item, context)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }

    }

    override fun onDataChanged() {
        super.onDataChanged()
        _progressBarShow.value = true
    }

    class OnClickListener(val clickListener: (schedule: Schedule) -> Unit) {
        fun onClick(schedule: Schedule) = clickListener(schedule)
    }


}
