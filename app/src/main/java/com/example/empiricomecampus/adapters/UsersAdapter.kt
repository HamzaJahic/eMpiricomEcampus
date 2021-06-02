package com.example.empiricomecampus.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.empiricomecampus.databinding.RvItemProfileBinding
import com.example.empiricomecampus.models.Student
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class UsersAdapter(
    options: FirebaseRecyclerOptions<Student>,
    val context: Context,
    val onClickListener: OnClickListener
) : FirebaseRecyclerAdapter<Student, UsersAdapter.UserHolder>(options) {

    private val _progressBarShow = MutableLiveData<Boolean?>()
    val progressBar: LiveData<Boolean?>
        get() = _progressBarShow

    class UserHolder(private var binding: RvItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student, context: Context, onClickListener: OnClickListener) {
            binding.userName.text = "${student.name} ${student.lastName}"
            Glide.with(context).load(student.img).into(binding.imgUser)

            binding.cardView.setOnClickListener {
                onClickListener.onClick(student)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(
            RvItemProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: UserHolder, position: Int, model: Student) {
        val item = getItem(position)
        holder.bind(item, context, onClickListener)

    }

    override fun onDataChanged() {
        super.onDataChanged()
        _progressBarShow.value = true
    }

    class OnClickListener(val clickListener: (student: Student) -> Unit) {
        fun onClick(student: Student) = clickListener(student)
    }

}
