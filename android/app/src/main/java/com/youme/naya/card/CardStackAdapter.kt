package com.youme.naya.card

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.loopeer.cardstack.CardStackView
import com.loopeer.cardstack.StackAdapter
import com.youme.naya.R
import com.youme.naya.database.entity.Card

class CardStackAdapter(
    context: Context,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) : StackAdapter<Card>(context) {

    private var activityLauncher = launcher

    override fun bindView(card: Card, position: Int, holder: CardStackView.ViewHolder) {
        val h = holder as ColorItemViewHolder
        h.onBind(card, activityLauncher)
    }

    override fun onCreateView(parent: ViewGroup, viewType: Int): CardStackView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.list_card_item, parent, false)
        return ColorItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.list_card_item
    }

    internal class ColorItemViewHolder(view: View) : CardStackView.ViewHolder(view) {
        var mLayout: View
        var mContainerContent: View
        var mTextName: TextView
        var mTextEngName: TextView
        var mTextCompany: TextView
        var mTextTeamAndRole: TextView
        var mTextAddress: TextView
        var mTextMobile: TextView
        var mTextEmail: TextView
        var mTextSummaryMain: TextView
        var mTextSummarySub: TextView
        var mBtnDetails: Button

        init {
            mLayout = view.findViewById(R.id.frame_list_card_item)
            mContainerContent = view.findViewById(R.id.container_list_content)
            mTextName = view.findViewById(R.id.bcard_name)
            mTextEngName = view.findViewById(R.id.bcard_english_name)
            mTextCompany = view.findViewById(R.id.bcard_company)
            mTextTeamAndRole = view.findViewById(R.id.bcard_team_and_role)
            mTextAddress = view.findViewById(R.id.bcard_address)
            mTextMobile = view.findViewById(R.id.bcard_mobile)
            mTextEmail = view.findViewById(R.id.bcard_email)
            mTextSummaryMain = view.findViewById(R.id.bcard_summary_main)
            mTextSummarySub = view.findViewById(R.id.bcard_summary_sub)
            mBtnDetails = view.findViewById(R.id.btn_details)
        }

        override fun onItemExpand(b: Boolean) {
            mContainerContent.visibility = if (b) View.VISIBLE else View.GONE
        }

        fun onBind(card: Card, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
            mLayout.background
            mTextName.text = card.name
            mTextEngName.text = card.engName
            mTextCompany.text = card.company
            val teamAndRole = card.team + " | " + card.role
            mTextTeamAndRole.text = teamAndRole
            mTextAddress.text = card.address
            mTextMobile.text = card.mobile
            mTextEmail.text = card.email
            val summaryMain =
                card.name + " | " + card.company + " | " + card.team + " | " + card.role
            val summarySub = card.memoContent.ifEmpty { "메모를 등록하지 않았어요" }
            mTextSummaryMain.text = summaryMain
            mTextSummarySub.text = summarySub
            mBtnDetails.setOnClickListener {
                val intent = Intent(context, CardDetailsActivity::class.java)
                intent.putExtra("cardId", card.NayaCardId)
                launcher.launch(intent)
            }
        }
    }

}