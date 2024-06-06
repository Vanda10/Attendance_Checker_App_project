package kh.edu.rupp.fe.dse.attendencechecker.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.attendencechecker.R
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassList
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel
import kh.edu.rupp.fe.dse.attendencechecker.adapter.TitleViewHolder
import kh.edu.rupp.fe.dse.attendencechecker.adapter.BaseViewHolder

abstract class BaseViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
    abstract fun bindData(classUiModel: ClassUiModel)
}

class TitleViewHolder(containerView: View) : BaseViewHolder(containerView) {
    private val titleView: TextView by lazy { containerView.findViewById(R.id.subject) }

    override fun bindData(classUiModel: ClassUiModel) {
        titleView.text = classUiModel.group_code
    }
}

