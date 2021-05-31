package com.capstone.watuku.detail


import androidx.lifecycle.ViewModel
import com.capstone.watuku.model.WatuResource

class DetailViewModel : ViewModel() {
    private var _detailWatu = WatuResource()

    fun setDataDummy(watu: WatuResource) {
        _detailWatu = watu
    }

    fun getDataDummy() : WatuResource = _detailWatu

}