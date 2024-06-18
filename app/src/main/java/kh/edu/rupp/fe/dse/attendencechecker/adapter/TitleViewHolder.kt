package kh.edu.rupp.fe.dse.attendencechecker.adapter

import android.view.View
import android.widget.TextView
import kh.edu.rupp.fe.dse.attendencechecker.R
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel

class TitleViewHolder(containerView: View) : BaseViewHolder(containerView) {
    private val sessionNameView: TextView = containerView.findViewById(R.id.subject)
    private val sessionDateView: TextView = containerView.findViewById(R.id.date)
    private val sessionStartTimeView: TextView = containerView.findViewById(R.id.time)

    override fun bindData(classUiModel: ClassUiModel) {
        sessionNameView.text = classUiModel.session_name
        sessionDateView.text = classUiModel.session_date
        sessionStartTimeView.text = classUiModel.session_start_time
    }
}
