// ClassListAdapter.kt
package kh.edu.rupp.fe.dse.attendencechecker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.attendencechecker.R
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel

class ClassListAdapter(private var classList: List<ClassUiModel>) : RecyclerView.Adapter<ClassListAdapter.ClassViewHolder>() {

    inner class ClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sessionNameTextView: TextView = itemView.findViewById(R.id.subject)
        val sessionDateTextView: TextView = itemView.findViewById(R.id.date)
        val sessionStartTimeTextView: TextView = itemView.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_checked_in, parent, false)
        return ClassViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        val classItem = classList[position]
        holder.sessionNameTextView.text = classItem.session_name
        holder.sessionDateTextView.text = classItem.session_date
        holder.sessionStartTimeTextView.text = classItem.session_start_time
    }

    override fun getItemCount() = classList.size

    fun updateClassList(newClassList: List<ClassUiModel>) {
        classList = newClassList
        notifyDataSetChanged()
    }
}
