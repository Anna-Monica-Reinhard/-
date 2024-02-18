package com.mkvsk.warehousewizard.ui.view.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mkvsk.warehousewizard.core.Product;
import com.mkvsk.warehousewizard.databinding.RvProductItemBinding;
import com.mkvsk.warehousewizard.ui.util.Utils;
import com.mkvsk.warehousewizard.ui.view.listeners.OnProductClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemViewHolder> {
    private final List<Product> data = new ArrayList<>();
    private OnProductClickListener listener;
    private RvProductItemBinding binding;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RvProductItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ItemViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    public void setClickListener(OnProductClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Product> data) {
        if (data != null) {
            this.data.clear();
            notifyDataSetChanged();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemViewHolder(RvProductItemBinding binding) {
            super(binding.getRoot());
        }

        public void bind(Product productItem) {
            binding.tvTitle.setText(productItem.getTitle());
            binding.tvCode.setText(productItem.getCode());
            Glide.with(Utils.getAppContext()).load(productItem.getImage()).apply(Utils.getOptions()).into(binding.ivImage);
            binding.cvProduct.setOnClickListener(v -> {
                listener.onProductClick(productItem, getBindingAdapterPosition());
            });
        }
    }
}


