package com.ymr.rmachor.datos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Monumento(var id: Long ,
                     var name:String="",
                     var photoUrl:String="",
                     var descripcion:String=""): Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Monumento

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
