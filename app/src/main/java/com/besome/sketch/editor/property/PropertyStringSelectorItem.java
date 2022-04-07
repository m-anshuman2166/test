package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.Kw;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PropertyStringSelectorItem extends RelativeLayout implements View.OnClickListener {

    public String key = "";
    public String value = "";
    public TextView tvName;
    public TextView tvValue;
    public ImageView imgLeftIcon;
    public int icon;
    public View propertyItem;
    public View propertyMenuItem;
    public ViewGroup radioGroupContent;
    public Kw valueChangeListener;

    public PropertyStringSelectorItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        int identifier = getResources().getIdentifier(key, "string", getContext().getPackageName());
        if (identifier > 0) {
            tvName.setText(xB.b().a(getResources(), identifier));
            switch (key) {
                case "property_ad_size":
                    icon = Resources.drawable.widget_admob;
                    break;

                case "property_indeterminate":
                    icon = Resources.drawable.event_on_accuracy_changed_48dp;
                    break;

                case "property_scale_type":
                    icon = Resources.drawable.enlarge_48;
                    break;
            }
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(Resources.id.img_icon)).setImageResource(icon);
                ((TextView) findViewById(Resources.id.tv_title)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            imgLeftIcon.setImageResource(icon);
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        tvValue.setText(value);
    }

    @Override
    public void onClick(View view) {
        if (!mB.a()) {
            a();
        }
    }

    public void setOnPropertyValueChangeListener(Kw onPropertyValueChangeListener) {
        valueChangeListener = onPropertyValueChangeListener;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            propertyItem.setVisibility(GONE);
            propertyMenuItem.setVisibility(VISIBLE);
        } else {
            propertyItem.setVisibility(VISIBLE);
            propertyMenuItem.setVisibility(GONE);
        }
    }

    private void initialize(Context context, boolean z) {
        wB.a(context, this, Resources.layout.property_selector_item);
        tvName = findViewById(Resources.id.tv_name);
        tvValue = findViewById(Resources.id.tv_value);
        imgLeftIcon = findViewById(Resources.id.img_left_icon);
        propertyItem = findViewById(Resources.id.property_item);
        propertyMenuItem = findViewById(Resources.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    private void a() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);
        View view = wB.a(getContext(), Resources.layout.property_popup_selector_single);
        radioGroupContent = view.findViewById(Resources.id.rg_content);

        String[] items;
        switch (key) {
            case "property_ad_size":
                items = sq.k;
                break;

            case "property_indeterminate":
                items = sq.l;
                break;

            case "property_scale_type":
                items = sq.j;
                break;

            default:
                items = null;
        }

        for (String item : items) {
            radioGroupContent.addView(a(item));
        }

        for (int counter = 0; counter < radioGroupContent.getChildCount(); counter++) {
            RadioButton childAt = (RadioButton) radioGroupContent.getChildAt(counter);
            if (childAt.getTag().toString().equals(value)) {
                childAt.setChecked(true);
                break;
            }
        }

        dialog.a(view);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_select), v -> {
            int childCount = radioGroupContent.getChildCount();
            int counter = 0;
            while (true) {
                if (counter >= childCount) {
                    break;
                }
                RadioButton radioButton = (RadioButton) radioGroupContent.getChildAt(counter);
                if (radioButton.isChecked()) {
                    setValue(radioButton.getTag().toString());
                    break;
                }
                counter++;
            }
            if (valueChangeListener != null) {
                valueChangeListener.a(key, value);
            }
            dialog.dismiss();
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private RadioButton a(String str) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setTextSize(2, 12.0f);
        radioButton.setText(str);
        radioButton.setTag(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) wB.a(getContext(), 4.0f);
        layoutParams.bottomMargin = (int) wB.a(getContext(), 4.0f);
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }
}