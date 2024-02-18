package com.mkvsk.warehousewizard.ui.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.mkvsk.warehousewizard.R;
import com.mkvsk.warehousewizard.core.Category;
import com.mkvsk.warehousewizard.core.Product;
import com.mkvsk.warehousewizard.core.User;
import com.mkvsk.warehousewizard.ui.view.listeners.OnAddNewItemClickListener;
import com.mkvsk.warehousewizard.ui.view.listeners.OnProductCardClickListener;
import com.mkvsk.warehousewizard.ui.view.listeners.OnShowUserInfo;

import java.util.List;
import java.util.Objects;

public final class CustomAlertDialogBuilder {
    public static boolean isEditMode = false;

    @SuppressLint("UseCompatLoadingForDrawables")
    public static AlertDialog productCardFullInfo(final Context context, Product product, OnProductCardClickListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_full_product_info, null, false);
        dialog.setView(dialogView);

        final TextView tvEditor = dialogView.findViewById(R.id.tvProductEditorFullInfo);
        final TextView tvCategory = dialogView.findViewById(R.id.tvProductCategoryFullInfo);
        final TextView tvExpMonth = dialogView.findViewById(R.id.tvProductExpMonth);
        final EditText tvName = dialogView.findViewById(R.id.tvProductNameFullInfo);
        final EditText tvCode = dialogView.findViewById(R.id.tvProductCode);
        final EditText tvDescription = dialogView.findViewById(R.id.tvDescription);
        final TextView tvAvailability = dialogView.findViewById(R.id.tvAvailabilityFullInfo);
        final EditText tvPrice = dialogView.findViewById(R.id.tvPriceFullInfo);
        final EditText tvQty = dialogView.findViewById(R.id.tvQty);
        final ImageView ivImage = dialogView.findViewById(R.id.ivImage);
        final ImageButton btnPlus = dialogView.findViewById(R.id.btnPlus);
        final ImageButton btnMinus = dialogView.findViewById(R.id.btnMinus);
        final ImageButton btnClose = dialogView.findViewById(R.id.btnCloseFullInfo);
        final ImageButton btnEdit = dialogView.findViewById(R.id.btnEditProductCard);
        final ImageButton btnDelete = dialogView.findViewById(R.id.btnDeleteProductCard);

        tvCategory.setText(product.getCategory());
        tvExpMonth.setText("Expiration (month)" + product.getExpiration());
        tvEditor.setText(Objects.requireNonNullElse("Editor: " + product.getLastEditor(), "Unknown"));
        tvName.setText(product.getTitle());
        tvCode.setText(product.getCode());
        tvDescription.setText(product.getDescription());
        tvPrice.setText(String.valueOf(product.getPrice()));
        tvQty.setText(String.valueOf(product.getQty()));
        tvAvailability.setText(product.getQty() > 0 ? R.string.available : R.string.unavailable);
        Glide.with(context)
                .load(Uri.parse(product.getImage()))
                .apply(Utils.getOptions())
                .dontAnimate()
                .into(ivImage);
        setEditMode(isEditMode = false, context, tvName, tvDescription, tvPrice, tvCode, tvQty);

        dialog.setCancelable(false);
        AlertDialog alertDialog = dialog.create();

        btnPlus.setOnClickListener(v -> {
            long qty = Long.parseLong(tvQty.getText().toString());
            qty++;
            if (qty == 0) {
                tvAvailability.setText(R.string.available);
            } else {
                tvAvailability.setText(R.string.unavailable);
            }
            tvQty.setText(String.valueOf(qty));
        });

        btnMinus.setOnClickListener(v -> {
            long qty = Long.parseLong(tvQty.getText().toString());
            qty--;
            if (qty == 0) {
                tvAvailability.setText(R.string.available);
            } else {
                tvAvailability.setText(R.string.unavailable);
            }
            tvQty.setText(String.valueOf(qty));
        });

        btnEdit.setOnClickListener(v -> {
            if (!isEditMode) {
                Glide.with(context)
                        .load(R.drawable.ic_accept)
                        .into(btnEdit);

                btnClose.setVisibility(View.GONE);
                btnDelete.setVisibility(View.VISIBLE);
//                listener.onEdit(product);
            } else {
                Glide.with(context)
                        .load(R.drawable.ic_edit)
                        .into(btnEdit);

                btnClose.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.GONE);

                product.setTitle(tvName.getText().toString());
                product.setQty(Long.parseLong(tvQty.getText().toString()));
                product.setPrice(Double.parseDouble(tvPrice.getText().toString()));
                product.setDescription(tvDescription.getText().toString());
                product.setCode(tvCode.getText().toString());

                listener.onEdit(product);
            }
            isEditMode = !isEditMode;
            setEditMode(isEditMode, context, tvName, tvDescription, tvPrice, tvCode, tvQty);
        });

        btnDelete.setOnClickListener(view -> {
//                    TODO undo toast
            setEditMode(isEditMode = false, context, tvName, tvDescription, tvPrice, tvCode, tvQty);
            listener.onDelete(product);
            alertDialog.dismiss();
        });

        btnClose.setOnClickListener(v -> {
            listener.onCloseCard();
            alertDialog.dismiss();
        });
        Objects.requireNonNull(alertDialog.getWindow())
                .setBackgroundDrawable(context.getDrawable(R.drawable.alert_dialog_bgr));
        return alertDialog;
    }

    @SuppressLint("ResourceAsColor")
    public static void setEditMode(boolean isEditMode, Context context, View... view) {
        for (View viewItem : view) {
            if (viewItem instanceof EditText || viewItem instanceof TextInputEditText) {
                if (isEditMode) {
                    viewItem.setFocusableInTouchMode(true);
                    viewItem.setClickable(true);
                    ((EditText) viewItem).setCursorVisible(true);
                    viewItem.setBackgroundResource(R.drawable.bgr_edit_product_full_info);
                } else {
                    viewItem.setFocusableInTouchMode(false);
                    viewItem.setClickable(false);
                    ((EditText) viewItem).setCursorVisible(false);
                    viewItem.setBackgroundColor(Color.TRANSPARENT);
//                    setBackgroundColor(R.color.background_secondary_color)
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static AlertDialog cardAddNewProduct(final Context context, String username, Product newProduct, List<String> listCategories, OnAddNewItemClickListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_create_product, null, false);
        dialog.setView(dialogView);

        final MaterialAutoCompleteTextView ddCategory = dialogView.findViewById(R.id.dd_list_product_category);
        final TextInputEditText tvName = dialogView.findViewById(R.id.et_add_product_name);
        final TextInputEditText tvCode = dialogView.findViewById(R.id.et_add_product_code);
        final TextInputEditText tvExpirationMonth = dialogView.findViewById(R.id.et_add_product_exp);
        final TextInputEditText tvPrice = dialogView.findViewById(R.id.et_add_product_price);
        final TextInputEditText tvQty = dialogView.findViewById(R.id.et_add_product_qty);
        final TextInputEditText tvImageLink = dialogView.findViewById(R.id.et_add_product_image);
        final TextInputEditText tvDescription = dialogView.findViewById(R.id.et_add_product_description);
        final TextView tvAvailability = dialogView.findViewById(R.id.tv_add_product_availability);
        final AppCompatButton btnSave = dialogView.findViewById(R.id.btn_save_product);
        final ImageButton btnClose = dialogView.findViewById(R.id.btnCloseNewProduct);
        final ShapeableImageView ivPreview = dialogView.findViewById(R.id.iv_add_product_image_preview);
        dialog.setCancelable(false);
        AlertDialog alertDialog = dialog.create();

        Uri imageUri = Uri.parse("https://img.freepik.com/free-photo/bright-petals-gift-love-in-a-bouquet-generated-by-ai_188544-13370.jpg");
        Glide.with(context)
                .load(Drawable.createFromPath(imageUri.getPath()))
                .apply(Utils.getOptions())
                .dontAnimate()
                .into(ivPreview);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.list_item, listCategories);
        ddCategory.setAdapter(adapter);
        ddCategory.setDropDownBackgroundDrawable(
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.drop_down_list_bgr, null)
        );
        ddCategory.setOnItemClickListener((adapterView, view, position, l)
                -> newProduct.setCategory(adapterView.getItemAtPosition(position).toString()));
        tvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                btnSave.setEnabled(tvName.getText() != null && tvCode.getText() != null
                        && ddCategory.getText() != null && tvExpirationMonth.getText() != null);
            }
        });

        tvQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //todo numberformatexception
                tvAvailability.setText(Long.parseLong(tvQty.getText().toString()) > 0
                        ? R.string.available_in_shop : R.string.unavailable_in_shop);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        tvImageLink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 10) {
                    Glide.with(context).load(Drawable.createFromPath(tvImageLink.getText().toString()))
                            .apply(Utils.getOptions()).into(ivPreview);
                }
            }
        });

        btnSave.setOnClickListener(v -> {
            newProduct.setTitle(Objects.requireNonNull(tvName.getText()).toString());
            newProduct.setCategory(ddCategory.getText().toString());
            newProduct.setExpiration(Integer.getInteger(tvExpirationMonth.getText().toString()));
            newProduct.setCode(Objects.requireNonNull(tvCode.getText()).toString());
            newProduct.setPrice(tvPrice.getText().toString().isBlank() ? 0.0D : Double.parseDouble(tvPrice.getText().toString()));
            newProduct.setQty(tvQty.getText().toString().isBlank() ? 0L : Long.parseLong(tvQty.getText().toString()));
            newProduct.setImage(Objects.requireNonNullElse(tvImageLink.getText().toString(), Constants.DEFAULT_PRODUCT_IMAGE));
            newProduct.setDescription(tvDescription.getText().toString());
            newProduct.setLastEditor(username);
            listener.onSaveNewData();
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
                btnSave.setEnabled(s.length() > 2);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnSave.setOnClickListener(v -> {
            newCategory.setTitle(Objects.requireNonNull(tvName.getText()).toString());
            listener.onSaveNewData();
            alertDialog.dismiss();
        });
        btnClose.setOnClickListener(v -> alertDialog.dismiss());
        Objects.requireNonNull(alertDialog.getWindow())
                .setBackgroundDrawable(context.getDrawable(R.drawable.alert_dialog_bgr));
        return alertDialog;
    }

    public static AlertDialog cardUserInfo(final Context context, User user, OnShowUserInfo listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_user_info, null, false);
        dialog.setView(dialogView);

        final TextInputEditText tvName = dialogView.findViewById(R.id.dialogUserInfoName);
        final TextInputEditText tvEmail = dialogView.findViewById(R.id.dialogUserInfoEmail);
        final TextInputEditText tvPhoneNumber = dialogView.findViewById(R.id.dialogUserInfoPhoneNumber);
        final TextInputEditText tvPassword = dialogView.findViewById(R.id.dialogUserInfoPassword);
        final ImageButton btnEdit = dialogView.findViewById(R.id.btnEditUserInfo);
        final ImageButton btnClose = dialogView.findViewById(R.id.btnCloseUserInfo);
        dialog.setCancelable(false);
        AlertDialog alertDialog = dialog.create();

        setEditMode(isEditMode = false, context, tvName, tvEmail, tvPhoneNumber, tvPassword);

        tvName.setText(user.fullName);
        tvEmail.setText(user.email);
        tvPhoneNumber.setText(user.phoneNumber);
        tvPassword.setText(user.password);

        btnEdit.setOnClickListener(v -> {
            if (!isEditMode) {
                Glide.with(context)
                        .load(R.drawable.ic_accept)
                        .into(btnEdit);
                btnClose.setVisibility(View.GONE);

            } else {
                Glide.with(context)
                        .load(R.drawable.ic_edit)
                        .into(btnEdit);

                user.setFullName(tvName.getText().toString().isBlank() ? user.fullName : tvName.getText().toString());
                user.setPassword(tvPassword.getText().toString().isBlank() ? user.password : tvPassword.getText().toString());
                user.setEmail(tvEmail.getText().toString().isBlank() ? user.email : tvEmail.getText().toString());
                user.setPhoneNumber(tvPhoneNumber.getText().toString().isBlank() ? user.phoneNumber : tvPhoneNumber.getText().toString());
                listener.updateUser(user);

                btnClose.setVisibility(View.VISIBLE);
            }
            isEditMode = !isEditMode;
            setEditMode(isEditMode, context, tvName, tvEmail, tvPhoneNumber, tvPassword);
        });

        btnClose.setOnClickListener(v -> {
            setEditMode(isEditMode = false, context, tvName, tvEmail, tvPhoneNumber, tvPassword);
            alertDialog.dismiss();
        });

        Objects.requireNonNull(alertDialog.getWindow())
                .setBackgroundDrawable(context.getDrawable(R.drawable.alert_dialog_bgr));
        return alertDialog;
    }
}