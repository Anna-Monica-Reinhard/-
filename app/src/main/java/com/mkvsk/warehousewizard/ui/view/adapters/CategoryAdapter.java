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
    private int selectedTag = -1;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(RvCategoryItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ItemViewHolder holder, int position) {
        String categoryItem = data.get(position);
        holder.bind(categoryItem);
    }

    public void setClickListener(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<String> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public void setSelected(int position) {
        selectedTag = position;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final RvCategoryItemBinding binding;

        ItemViewHolder(RvCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String categoryItem) {
            if (selectedTag == getBindingAdapterPosition()) {
                selectItem();
            } else {
                unselectItem();
            }

            binding.tvTitle.setText(categoryItem);
            binding.cvItem.setOnClickListener(v -> listener.onCategoryClick(
                    selectedTag,
                    getBindingAdapterPosition(),
                    categoryItem
            ));
        }

        private void selectItem() {
            binding.cvItem.setBackgroundResource(R.drawable.selected_tag_item);
            binding.cvItem.setClickable(false);
            binding.tvTitle.setSelected(true);
        }

        private void unselectItem() {
            binding.cvItem.setBackgroundResource(R.drawable.unselected_tag_item);
            binding.cvItem.setClickable(true);
            binding.tvTitle.setSelected(false);
        }
    }
}


