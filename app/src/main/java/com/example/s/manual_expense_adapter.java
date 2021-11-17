package com.example.s;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

    public class manual_expense_adapter extends RecyclerView.Adapter<manual_expense_adapter.MyHolder> {
        Context context;
        ArrayList<String> msz;

        public manual_expense_adapter(Context context, ArrayList<String> msz) {
            this.context = context;
            this.msz = msz;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(context).inflate(R.layout.manual_expense_card_view, viewGroup, false);
            MyHolder holder = new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
            myHolder.msz.setText(msz.get(i));
        }

        @Override
        public int getItemCount() {
            return msz.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView msz;
            public MyHolder(@NonNull View v) {
                super(v);
                msz=v.findViewById(R.id.msz);
                 }

            }
        }

