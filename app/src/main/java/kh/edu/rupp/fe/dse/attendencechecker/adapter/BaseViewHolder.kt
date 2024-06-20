package kh.edu.rupp.fe.dse.attendencechecker.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel
import kh.edu.rupp.fe.dse.attendencechecker.model.SessionInfo
import kh.edu.rupp.fe.dse.attendencechecker.model.UserResponse

abstract class BaseViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
    abstract fun bindData(classUiModel: ClassUiModel, userProfile: UserResponse, sessionInfo: SessionInfo)
}
