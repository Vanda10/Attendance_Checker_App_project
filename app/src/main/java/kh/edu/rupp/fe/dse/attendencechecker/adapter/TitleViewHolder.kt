package kh.edu.rupp.fe.dse.attendencechecker.adapter

import android.view.View
import android.widget.TextView
import kh.edu.rupp.fe.dse.attendencechecker.R
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel
import kh.edu.rupp.fe.dse.attendencechecker.model.SessionInfo
import kh.edu.rupp.fe.dse.attendencechecker.model.UserResponse

class TitleViewHolder(containerView: View) : BaseViewHolder(containerView) {
    private val sessionNameView: TextView = containerView.findViewById(R.id.subject)
    private val sessionDateView: TextView = containerView.findViewById(R.id.date)
    private val sessionStartTimeView: TextView = containerView.findViewById(R.id.time)

    private val UserNameView: TextView = containerView.findViewById(R.id.profile_name)
    private val majorView: TextView = containerView.findViewById(R.id.student_department1)
    private val DepartmentView: TextView = containerView.findViewById(R.id.student_department)
    private val DobView: TextView = containerView.findViewById(R.id.profile_dob)
    private val ClassCodeView: TextView = containerView.findViewById(R.id.class_code)

    override fun bindData(classUiModel: ClassUiModel, userProfile: UserResponse, sessionInfo: SessionInfo) {
        sessionNameView.text = classUiModel.session_name
        sessionDateView.text = classUiModel.session_date
        sessionStartTimeView.text = classUiModel.session_start_time

        UserNameView.text = userProfile.user_name
        majorView.text = userProfile.department
        DepartmentView.text = userProfile.department
        DobView.text = userProfile.dob
        ClassCodeView.text = userProfile.class_code


    }
}
