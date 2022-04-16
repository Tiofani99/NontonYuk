package com.tiooooo.core.utils

enum class Routes {
    DETAIL_MOVIE,
    DETAIL_TV_SERIES,
    // for next feature
    SEARCH_MOVIE,
    SEARCH_TV_SERIES,
}

enum class DataState {
    LOADING,
    SUCCESS,
    ERROR
}

enum class InfoEnum {
    SUCCESS,
    DANGER
}


class IntentExtra {
    companion object {
        const val ROUTE = "ROUTE"
        const val ARGS = "ARGS"
    }
}