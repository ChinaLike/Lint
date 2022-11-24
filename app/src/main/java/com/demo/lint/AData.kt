package com.demo.lint

import java.io.Serializable

/**
 *
 * @author like
 * @date 2022/11/23 23:27
 */
data class AData(
    val data: BData,
    val cData: CData
) : Serializable {

    data class CData(
        val name: String
    )
}
