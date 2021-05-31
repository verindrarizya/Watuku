package com.capstone.watuku

import com.capstone.watuku.detail.DataDummy
import com.capstone.watuku.detail.DetailViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class DetailViewModelTest {

    private lateinit var detailViewModel: DetailViewModel
    private val dummywatuData = DataDummy.listData

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel()
    }

    @Test
    fun `test detail of watu data, return should not be null`() {
        detailViewModel.setDataDummy(dummywatuData[3])
        val watu = detailViewModel.getDataDummy()
        Assert.assertNotNull(watu)
        Assert.assertEquals(dummywatuData[3].title, watu.title)
        Assert.assertEquals(dummywatuData[3].definition, watu.definition)
        Assert.assertEquals(dummywatuData[3].usage, watu.usage)
        Assert.assertEquals(dummywatuData[3].Img, watu.Img)
    }

}