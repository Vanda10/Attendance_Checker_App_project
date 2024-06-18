package kh.edu.rupp.fe.dse.attendencechecker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.attendencechecker.R
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel

class ClassListAdapter(private var sessionList: List<ClassUiModel>) : RecyclerView.Adapter<ClassListAdapter.SessionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_checked_in, parent, false)
        return SessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(sessionList[position])
    }

    override fun getItemCount(): Int = sessionList.size

    fun updateSessionList(newClassList: List<ClassUiModel>) {
        sessionList = newClassList
        notifyDataSetChanged()
    }

    class SessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sessionNameView: TextView = itemView.findViewById(R.id.subject)
        private val sessionDateView: TextView = itemView.findViewById(R.id.date)
        private val sessionStartTimeView: TextView = itemView.findViewById(R.id.time)


        fun bind(classUiModel: ClassUiModel) {
            sessionNameView.text = classUiModel.session_name
            sessionDateView.text = classUiModel.session_date
            sessionStartTimeView.text = classUiModel.session_start_time
        }
    }
}