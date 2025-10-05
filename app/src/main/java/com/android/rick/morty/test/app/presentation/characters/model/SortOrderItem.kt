package com.android.rick.morty.test.app.presentation.characters.model

import com.android.rick.morty.test.app.domain.model.SortOrder

data class SortOrderItem(
    val sortOrder: SortOrder,
    val isEnabled: Boolean
)