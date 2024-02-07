package com.mkvsk.warehousewizard.ui.view.listeners;

import com.mkvsk.warehousewizard.core.Product;

public interface OnProductCardClickListener {
    void onEdit(Product product);
    void onDelete(Product product);
    void onCloseCard();
}
