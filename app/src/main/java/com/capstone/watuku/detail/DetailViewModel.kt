package com.capstone.watuku.detail


import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {
    private var _detailWatu = WatuRecources()

    fun setDataDummy(watu: WatuRecources) {
        _detailWatu = watu
    }

    fun getDataDummy() : WatuRecources = _detailWatu

}