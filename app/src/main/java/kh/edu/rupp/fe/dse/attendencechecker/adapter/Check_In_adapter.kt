package kh.edu.rupp.fe.dse.attendencechecker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.attendencechecker.R
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassList
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel
import kh.edu.rupp.fe.dse.attendencechecker.adapter.BaseViewHolder
import kh.edu.rupp.fe.dse.attendencechecker.adapter.TitleViewHolder

class ClassListAdapter(private val classList: List<ClassUiModel>) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_checked_in, parent, false)
        return TitleViewHolder(view) // Assuming you only have one type of ViewHolder for now
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindData(classList[position])
    }

    override fun getItemCount(): Int {
        return classList.size
    }
}
