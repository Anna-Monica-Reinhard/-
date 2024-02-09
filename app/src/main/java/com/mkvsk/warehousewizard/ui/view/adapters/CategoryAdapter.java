package com.mkvsk.warehousewizard.ui.view.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.mkvsk.warehousewizard.R;
import com.mkvsk.warehousewizard.core.Category;
import com.mkvsk.warehousewizard.databinding.RvCategoryItemBinding;
import com.mkvsk.warehousewizard.ui.view.listeners.OnCategoryClickListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemViewHolder> {
    private List<String> data = new ArrayList<String>();
    private OnCategoryClickListener listener;
    private int selectedTag = 0;
    private RvCategoryItemBinding binding;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RvCategoryItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ItemViewHolder holder, int position) {
        String categoryItem = (String) data.toArray()[position];
        holder.bind(categoryItem);
    }

    public void setClickListener(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<String> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelected(int position) {
        selectedTag = position;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemViewHolder(RvCategoryItemBinding binding) {
            super(binding.getRoot());
        }

        public void bind(String categoryItem) {

            if (selectedTag == getBindingAdapterPosition()) {
                selectItem(binding.cvItem, binding.tvTitle);
            } else {
                unselectItem(binding.cvItem, binding.tvTitle);
            }

            binding.tvTitle.setText(categoryItem);
            binding.cvItem.setOnClickListener(v -> listener.onCategoryClick(
                    selectedTag,
                    getBindingAdapterPosition(),
                    categoryItem
            ));
        }

        private void selectItem(MaterialCardView cardView, TextView textView) {
            cardView.setBackgroundResource(R.drawable.selected_tag_item);
            cardView.setClickable(false);
            textView.setSelected(true);
        }

        private void unselectItem(MaterialCardView cardView, TextView textView) {
            cardView.setBackgroundResource(R.drawable.unselected_tag_item);
            cardView.setClickable(true);
            textView.setSelected(false);
        }
    }
}


