package com.youme.naya.card

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.compose.ui.platform.LocalView
import androidx.navigation.findNavController
import com.loopeer.cardstack.CardStackView
import com.loopeer.cardstack.StackAdapter
import com.youme.naya.R
import com.youme.naya.database.entity.Card
import com.youme.naya.share.ShareActivity

class CardStackAdapter(context: Context) : StackAdapter<Card>(context) {

    override fun bindView(card: Card, position: Int, holder: CardStackView.ViewHolder) {
        val h = holder as ColorItemViewHolder
        h.onBind(card)
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

        fun onBind(card: Card) {
            mLayout.background
            mTextName.text = card.name
            mTextEngName.text = card.engName
            mTextCompany.text = card.company
            val TeamAndRole = card.team + " | " + card.role
            mTextTeamAndRole.text = TeamAndRole
            mTextAddress.text = card.address
            mTextMobile.text = card.mobile
            mTextEmail.text = card.email
            val summaryMain =
                card.name + " | " + card.company + " | " + card.team + " | " + card.role
            val summarySub = if (card.memo_content.isEmpty()) "메모를 등록하지 않았어요" else card.memo_content
            mTextSummaryMain.text = summaryMain
            mTextSummarySub.text = summarySub
            mBtnDetails.setOnClickListener {
                val intent = Intent(context, CardDetailsActivity::class.java)
                intent.putExtra("cardId", card.NayaCardId)
                context.startActivity(intent)
            }
        }
    }

}