package com.mkvsk.warehousewizard.ui.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.mkvsk.warehousewizard.R;
import com.mkvsk.warehousewizard.core.Category;
import com.mkvsk.warehousewizard.core.Product;
import com.mkvsk.warehousewizard.ui.view.listeners.OnAddNewItemClickListener;
import com.mkvsk.warehousewizard.ui.view.listeners.OnProductCardClickListener;

import java.util.Objects;

public final class CustomAlertDialogBuilder {
    @SuppressLint("UseCompatLoadingForDrawables")
    public static AlertDialog productCardFullInfo(final Context context, Product product, OnProductCardClickListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_full_product_info, null, false);
        dialog.setView(dialogView);

        final TextView tvName = dialogView.findViewById(R.id.tv_product_name);
        final TextView tvCode = dialogView.findViewById(R.id.tvProductCode);
        final TextView tvDescription = dialogView.findViewById(R.id.tvDescription);
        final TextView tvAvailability = dialogView.findViewById(R.id.tvAvailability);
        final TextView tvQty = dialogView.findViewById(R.id.tvQty);
        final ImageButton btnPlus = dialogView.findViewById(R.id.btnPlus);
        final ImageButton btnMinus = dialogView.findViewById(R.id.btnMinus);
        final ImageButton btnClose = dialogView.findViewById(R.id.btnCloseFullInfo);
        final ImageView ivImage = dialogView.findViewById(R.id.ivImage);

        tvName.setText(product.getTitle());
        tvCode.setText(product.getCode());
        tvDescription.setText(product.getDescription());
//        tvAvailability.setText(product.isAvailable() ? "Available" : "Out of stock");
        tvQty.setText(String.valueOf(product.getQty()));
        Glide.with(context).load(product.getImage()).apply(Utils.getOptions()).into(ivImage);

//        TODO listener
        dialog.setCancelable(false);
        AlertDialog alertDialog = dialog.create();
        btnPlus.setOnClickListener(v -> {
            long qty = Long.parseLong((String) tvQty.getText());
            qty++;
            if (qty == 0) {
                tvAvailability.setText("Out of stock");
            } else {
                tvAvailability.setText("Available");
            }
            tvQty.setText(String.valueOf(qty));
        });

        btnMinus.setOnClickListener(v -> {
            long qty = Long.parseLong((String) tvQty.getText());
            qty--;
            if (qty == 0) {
                tvAvailability.setText("Out of stock");
            } else {
                tvAvailability.setText("Available");
            }
            tvQty.setText(String.valueOf(qty));
        });

        btnClose.setOnClickListener(v -> {
//            update data

            alertDialog.dismiss();
        });
        Objects.requireNonNull(alertDialog.getWindow())
                .setBackgroundDrawable(context.getDrawable(R.drawable.alert_dialog_bgr));
        return alertDialog;
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public static AlertDialog cardAddNewProduct(final Context context, Product newProduct, OnAddNewItemClickListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_create_product, null, false);
        dialog.setView(dialogView);

        final TextInputEditText tvName = dialogView.findViewById(R.id.et_add_product_name);
//        final TextInputEditText tvCategory = dialogView.findViewById(R.id.et_add_product_category);
//        final TextInputEditText tvCode = dialogView.findViewById(R.id.et_add_product_code);
//        final TextInputEditText tvQty = dialogView.findViewById(R.id.et_add_product_qty);
//        final TextInputEditText tvImageLink = dialogView.findViewById(R.id.et_add_product_image);
//        final TextInputEditText tvDescription = dialogView.findViewById(R.id.et_add_product_description);
//        final TextInputEditText tvAvailability = dialogView.findViewById(R.id.tv_add_product_availability);
        final AppCompatButton btnSave = dialogView.findViewById(R.id.btn_save_product);
        final ImageButton btnClose = dialogView.findViewById(R.id.btnCloseNewProduct);

        dialog.setCancelable(false);
        AlertDialog alertDialog = dialog.create();

        tvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    btnSave.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

//                btnSave.setEnabled(tvName.getText() != null || tvCode.getText() != null || tvCategory.getText() != null);
            }
        });

//        newProduct.setTitle(tvName.getText().toString());
//        newProduct.setCategory(tvCategory.getText().toString());
//        newProduct.setCode(tvCode.getText().toString());
//        newProduct.setQty(Long.getLong(String.valueOf(tvQty.getText())));
//        newProduct.setImage(tvImageLink.getText().toString());
//        newProduct.setDescription(tvDescription.getText().toString());
//        newProduct.setAvailable();

        btnSave.setOnClickListener(v -> {
//            listener.onSaveNewData();
            alertDialog.dismiss();
        });

        btnClose.setOnClickListener(v -> alertDialog.dismiss());
        Objects.requireNonNull(alertDialog.getWindow())
                .setBackgroundDrawable(context.getDrawable(R.drawable.alert_dialog_bgr));
        return alertDialog;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static AlertDialog cardAddNewCategory(final Context context, Category newCategory, OnAddNewItemClickListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_create_category, null, false);
        dialog.setView(dialogView);

        final TextInputEditText tvName = dialogView.findViewById(R.id.et_add_category_title);
        final AppCompatButton btnSave = dialogView.findViewById(R.id.btn_save_category);
        final ImageButton btnClose = dialogView.findViewById(R.id.btnCloseNewCategory);

        dialog.setCancelable(false);
        AlertDialog alertDialog = dialog.create();

        tvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnSave.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnSave.setOnClickListener(v -> {
            listener.onSaveNewData();
            alertDialog.dismiss();
            Toast.makeText(context, "Category added", Toast.LENGTH_SHORT).show();
        });
        btnClose.setOnClickListener(v -> alertDialog.dismiss());
        Objects.requireNonNull(alertDialog.getWindow())
                .setBackgroundDrawable(context.getDrawable(R.drawable.alert_dialog_bgr));
        return alertDialog;
    }


//    public static AlertDialog dialogWithTwoButtons(final Context context, String title, String message,
//                                                   DialogButtonClickListener listener) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        View dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_view_custom_buttons, null);
//        dialog.setView(dialogView);
//
//        final TextView tvTitle = dialogView.findViewById(R.id.tvDialogTitle);
//        final TextView tvMessage = dialogView.findViewById(R.id.tvDialogMessage);
//        final Button positiveBtn = dialogView.findViewById(R.id.btnDialogPositive);
//        final Button negativeBtn = dialogView.findViewById(R.id.btnDialogNegative);
//
//        setTitle(context, title, tvTitle);
//        tvMessage.setText(message);
//
//        dialog.setCancelable(false);
//        AlertDialog alertDialog = dialog.create();
//        positiveBtn.setOnClickListener(v -> listener.onPositiveButtonClick());
//        negativeBtn.setOnClickListener(v -> {
//            listener.onNegativeButtonClick();
//            alertDialog.dismiss();
//        });
//
//        return alertDialog;
//    }
//
//
//    @SuppressLint({"ResourceType", "NonConstantResourceId", "UseCompatLoadingForDrawables"})
//    public static AlertDialog dialogWithTwoButtons(final Context context, String title, String message,
//                                                   String positiveButtonText, String negativeButtonText,
//                                                   ImageView icon, boolean isNeedToRecolorBtn,
//                                                   DialogButtonClickListener listener) {
//
//        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        View dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_view_custom_buttons, null);
//        dialog.setView(dialogView);
//
//        final TextView tvTitle = dialogView.findViewById(R.id.tvDialogTitle);
//        final TextView tvMessage = dialogView.findViewById(R.id.tvDialogMessage);
//        final Button positiveBtn = dialogView.findViewById(R.id.btnDialogPositive);
//        final Button negativeBtn = dialogView.findViewById(R.id.btnDialogNegative);
//        final ImageView ivIcon = dialogView.findViewById(R.id.ivDialogIcon);
//
//        setTitle(context, title, tvTitle);
//        tvMessage.setText(message);
//        setIcon(context, icon, ivIcon);
//        setButtons(context, positiveButtonText, negativeButtonText, positiveBtn, negativeBtn, isNeedToRecolorBtn);
//
//        dialog.setCancelable(false);
//        AlertDialog alertDialog = dialog.create();
//        positiveBtn.setOnClickListener(v -> listener.onPositiveButtonClick());
//        negativeBtn.setOnClickListener(v -> {
//            listener.onNegativeButtonClick();
//            alertDialog.dismiss();
//        });
//
//        return alertDialog;
//    }
//
//
//    public static AlertDialog dialogWithOneButton(final Context context, String title, String message,
//                                                  DialogNeutralButtonClickListener listener) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        View dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_view_custom_buttons, null);
//        dialog.setView(dialogView);
//        final TextView tvTitle = dialogView.findViewById(R.id.tvDialogTitle);
//        final TextView tvMessage = dialogView.findViewById(R.id.tvDialogMessage);
//        final Button uselessBtn = dialogView.findViewById(R.id.btnDialogPositive);
//        final Button neutralBtn = dialogView.findViewById(R.id.btnDialogNegative);
//
//        setTitle(context, title, tvTitle);
//        tvMessage.setText(message);
//
//        uselessBtn.setVisibility(View.INVISIBLE);
//        dialog.setCancelable(false);
//        AlertDialog alertDialog = dialog.create();
//        neutralBtn.setOnClickListener(v -> {
//            listener.onNeutralButtonClick();
//            alertDialog.dismiss();
//        });
//
//        return alertDialog;
//    }
//
//    @SuppressLint({"ResourceType", "NonConstantResourceId"})
//    public static AlertDialog dialogWithOneButton(final Context context, String title, String message,
//                                                  String neutralButtonText,
//                                                  ImageView icon, DialogNeutralButtonClickListener listener) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        View dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_view_custom_buttons, null);
//        dialog.setView(dialogView);
//
//        final TextView tvTitle = dialogView.findViewById(R.id.tvDialogTitle);
//        final TextView tvMessage = dialogView.findViewById(R.id.tvDialogMessage);
//        final Button uselessBtn = dialogView.findViewById(R.id.btnDialogPositive);
//        final Button neutralBtn = dialogView.findViewById(R.id.btnDialogNegative);
//        final ImageView ivIcon = dialogView.findViewById(R.id.ivDialogIcon);
//
//        setIcon(context, icon, ivIcon);
//        setTitle(context, title, tvTitle);
//        tvMessage.setText(message);
//
//        if (!neutralButtonText.isEmpty()) {
//            neutralBtn.setText(neutralButtonText);
//        } else {
//            neutralBtn.setText(context.getString(R.string.terminal_btn_ok));
//        }
//
//        uselessBtn.setVisibility(View.INVISIBLE);
//        dialog.setCancelable(false);
//        AlertDialog alertDialog = dialog.create();
//
//        neutralBtn.setOnClickListener(v -> {
//            listener.onNeutralButtonClick();
//            alertDialog.dismiss();
//        });
//
//        return alertDialog;
//    }
//
//    private static void setButtons(Context context,
//                                   String positiveButtonText, String negativeButtonText,
//                                   Button positiveBtn, Button negativeBtn,
//                                   boolean isNeedToRecolorBtn) {
//        if (!positiveButtonText.isEmpty()) {
//            positiveBtn.setText(positiveButtonText);
//        } else {
//            positiveBtn.setText(context.getString(R.string.terminal_btn_ok));
//        }
//
//        if (!negativeButtonText.isEmpty()) {
//            negativeBtn.setText(negativeButtonText);
//        } else {
//            negativeBtn.setText(context.getString(R.string.terminal_btn_cancel));
//        }
//
//        if (isNeedToRecolorBtn) {
//            positiveBtn.setBackgroundResource(R.drawable.bg_red_round_12);
//            positiveBtn.setTextColor(context.getColorStateList(R.color.color_white));
//        } else {
//            positiveBtn.setBackgroundResource(R.drawable.bg_transparent_round_12);
//            positiveBtn.setTextColor(context.getColorStateList(R.color.dialog_button_color));
//        }
//    }
//
//    @SuppressLint("UseCompatLoadingForDrawables")
//    private static void setIcon(Context context, ImageView icon, ImageView ivIcon) {
//        if (icon != null) {
//            ivIcon.setImageDrawable(icon.getDrawable());
//        } else {
//            ivIcon.setImageDrawable(context.getDrawable(R.drawable.ic_message_information));
//        }
//    }
//
//    private static void setTitle(Context context, String title, TextView tvTitle) {
//        if (!title.isEmpty()) {
//            tvTitle.setText(title);
//        } else {
//            tvTitle.setText(context.getString(R.string.attention));
//        }
//    }
}