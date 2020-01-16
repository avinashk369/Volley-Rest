package com.techcamino.pay.alipay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techcamino.pay.alipay.R;
import com.techcamino.pay.alipay.details.MenuDetails;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MemberView> {

    private List<MenuDetails> menuDetailsList;
    private Context context;
    private CustomDialogListener mViewClickListener;

    public interface CustomDialogListener {
        void onItemClicked(MenuDetails menuDetails);
    }

    @NonNull
    @Override
    public MemberView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item, viewGroup, false);

        return new MemberView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberView unclearedView, final int i) {

        unclearedView.name.setText(menuDetailsList.get(i).getMenuName());
        unclearedView.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onItemClicked(menuDetailsList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuDetailsList.size();
    }

    public class MemberView extends RecyclerView.ViewHolder {

        public TextView name;
        public RelativeLayout action;

        public MemberView(View view) {
            super(view);

            name = view.findViewById(R.id.menuName);
        }
    }

    public MenuItemAdapter(List<MenuDetails> chipDetailsList, Context context) {
        this.menuDetailsList = chipDetailsList;
        this.context = context;
        this.mViewClickListener = (CustomDialogListener) context;
    }

}
