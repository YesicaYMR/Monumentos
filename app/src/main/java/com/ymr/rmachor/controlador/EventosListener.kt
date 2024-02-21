package com.ymr.rmachor.controlador

import com.ymr.rmachor.datos.Monumento

interface EventosListener {
    fun editar(monumento: Monumento)

}