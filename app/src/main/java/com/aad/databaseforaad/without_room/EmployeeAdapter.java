package com.aad.databaseforaad.without_room;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aad.databaseforaad.R;

import java.util.List;

/**
 * Created by Mahim on 3/11/2018.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.CustomVH> {

    private Context mContext;
    private List<EmployeeModel> employeeModels;

    private OnItemClick onItemClick;

    public interface OnItemClick {
        void onGetItem(int id);
    }

    public EmployeeAdapter(Context mContext, List<EmployeeModel> employeeModels, OnItemClick onItemClick) {
        this.mContext = mContext;
        this.employeeModels = employeeModels;
        this.onItemClick = onItemClick;
    }

    @Override
    public CustomVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new CustomVH(view);
    }

    @Override
    public void onBindViewHolder(CustomVH holder, int position) {
        final EmployeeModel model = employeeModels.get(position);
        holder.name.setText(model.getName());
        holder.designation.setText(model.getDesignation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onGetItem(model.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeModels.size();
    }

    public class CustomVH extends RecyclerView.ViewHolder {

        TextView name, designation;

        public CustomVH(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.text_view_name);
            designation = (TextView) itemView.findViewById(R.id.text_view_designation);
        }
    }
}
