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
        TextView mTextName, mTextEngName, mTextCompany, mTextTeam, mTextRole, mTextAddress, mTextMobile, mTextEmail;

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
        }

    }

}