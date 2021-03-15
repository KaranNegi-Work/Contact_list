package com.example.contactlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class rAdapter extends RecyclerView.Adapter<rAdapter.viewHolder> {
List<ContactModel> contactModelList;
OnClickItem onClickItem;
public rAdapter(List<ContactModel> contactModelList, OnClickItem onClickItem){
    this.contactModelList=contactModelList;
    this.onClickItem=onClickItem;
}

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.contact_list_temp,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
     holder.name.setText(contactModelList.get(position).getName());
     holder.phoneNo.setText(contactModelList.get(position).getPhoneNo());
     holder.edit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             onClickItem.onCLickEdit(position);
         }
     });
    }

    @Override
    public int getItemCount() {
        return contactModelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView phoneNo;
    TextView edit;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            phoneNo=itemView.findViewById(R.id.phoneNo);
            edit=itemView.findViewById(R.id.edit);
        }
    }
}
