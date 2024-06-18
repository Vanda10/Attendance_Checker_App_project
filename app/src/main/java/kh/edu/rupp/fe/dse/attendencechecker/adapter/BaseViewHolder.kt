package kh.edu.rupp.fe.dse.attendencechecker.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel

abstract class BaseViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
    abstract fun bindData(classUiModel: ClassUiModel)
}
