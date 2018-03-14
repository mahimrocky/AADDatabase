package com.aad.databaseforaad.with_room;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aad.databaseforaad.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahim on 3/11/2018.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.CustomVH> {

    private Context mContext;
    private List<Employee> employeeModels;

    private OnItemClick onItemClick;

    public interface OnItemClick {
        void onGetItem(int id);
    }

    public EmployeeAdapter(Context mContext, List<Employee> employeeModels, OnItemClick onItemClick) {
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
        final Employee model = employeeModels.get(position);
        holder.name.setText(model.getEmployeeName());
        holder.designation.setText(model.getEmployeeDesignation());

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

        @BindView(R.id.text_view_name)
        TextView name;

        @BindView(R.id.text_view_designation)
        TextView designation;


        public CustomVH(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
