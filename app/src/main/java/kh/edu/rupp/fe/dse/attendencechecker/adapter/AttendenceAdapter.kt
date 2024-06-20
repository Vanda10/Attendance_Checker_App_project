// AttendanceAdapter.kt
package kh.edu.rupp.fe.dse.attendencechecker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.attendencechecker.R
import kh.edu.rupp.fe.dse.attendencechecker.model.SessionInfo
class AttendanceAdapter(private var sessionList: List<SessionInfo>) :
    RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_checked_in, parent, false)
        return AttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        val session = sessionList[position]
        holder.bind(session)
    }

    override fun getItemCount(): Int = sessionList.size

    fun setData(sessionList: List<SessionInfo>) {
        this.sessionList = sessionList
        notifyDataSetChanged()
    }

    class AttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sessionName: TextView = itemView.findViewById(R.id.subject)
        private val sessionDate: TextView = itemView.findViewById(R.id.date)
        private val sessionStartTime: TextView = itemView.findViewById(R.id.time)

        fun bind(sessionInfo: SessionInfo) {
            sessionName.text = sessionInfo.session_name
            sessionDate.text = sessionInfo.session_date
            sessionStartTime.text = sessionInfo.session_start_time
        }
    }
}
