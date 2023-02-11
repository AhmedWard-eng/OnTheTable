package com.mad.iti.onthetable.ui.favorite.view;

import com.mad.iti.onthetable.model.Meal;

public interface OnClickFavoriteMeal {

    void onClickItem(String id);

    void onClickDeleteItem(Meal meal);
}
