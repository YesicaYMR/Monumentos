package com.ymr.rmachor.datos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuario(var login:String, var contra:String, var nombre:String=""): Parcelable {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Usuario

        if (login != other.login) return false
        if (contra != other.contra) return false

        return true
    }

    override fun hashCode(): Int {
        var result = login.hashCode()
        result = 31 * result + contra.hashCode()
        return result
    }
}