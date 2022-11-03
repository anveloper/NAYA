package com.youme.naya.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.youme.naya.R;
import com.youme.naya.database.entity.Card;

public class CardStackAdapter extends StackAdapter<Card> {

    public CardStackAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindView(Card card, int position, CardStackView.ViewHolder holder) {
        ColorItemViewHolder h = (ColorItemViewHolder) holder;
        h.onBind(card, position);
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
        return new ColorItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_card_item;
    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout, mContainerContent;
        TextView mTextName, mTextEngName, mTextCompany, mTextTeam, mTextRole, mTextAddress, mTextMobile, mTextEmail, mTextSummaryMain, mTextSummarySub;

        public ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextName = view.findViewById(R.id.bcard_name);
            mTextEngName = view.findViewById(R.id.bcard_english_name);
            mTextCompany = view.findViewById(R.id.bcard_company);
            mTextTeam = view.findViewById(R.id.bcard_team);
            mTextRole = view.findViewById(R.id.bcard_role);
            mTextAddress = view.findViewById(R.id.bcard_address);
            mTextMobile = view.findViewById(R.id.bcard_mobile);
            mTextEmail = view.findViewById(R.id.bcard_email);
            mTextSummaryMain = view.findViewById(R.id.bcard_summary_main);
            mTextSummarySub = view.findViewById(R.id.bcard_summary_sub);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        public void onBind(Card card, int position) {
            mLayout.getBackground();
            mTextName.setText(card.getName());
            mTextEngName.setText(card.getEngName());
            mTextCompany.setText(card.getCompany());
            mTextTeam.setText(card.getTeam());
            mTextRole.setText(card.getRole());
            mTextAddress.setText(card.getAddress());
            mTextMobile.setText(card.getMobile());
            mTextEmail.setText(card.getEmail());

            String summaryMain = card.getName() + " / " + card.getCompany() + " / " + card.getTeam() + " / " + card.getRole();
            String summarySub = card.getMemo_content().isEmpty() ? "메모를 등록하지 않았어요" : card.getMemo_content();
            mTextSummaryMain.setText(summaryMain);
            mTextSummarySub.setText(summarySub);
        }

    }

}