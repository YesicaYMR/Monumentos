package com.ymr.rmachor.controlador

import android.os.Parcelable
import com.ymr.rmachor.datos.Monumento
import com.ymr.rmachor.datos.Usuario
import kotlinx.parcelize.Parcelize

@Parcelize
class Empresa (
    var usuarios:ArrayList<Usuario>,
    var monumentos:ArrayList<Monumento>): Parcelable {

    /**
     * Añade usuarios
     */
    fun anadirUsuarios(){
        usuarios.add(Usuario("a","123","Yesica Macho"))
        usuarios.add(Usuario("b","123","Pepe Martinez"))
    }

    /**
     * busca el usuario que intenta acceder
     */
    fun buscar(login:String, contra:String):Int{
        var pos=usuarios.indexOf(Usuario(login,contra))
        return pos
    }

    /**
     * Crea los monumentos
     */
    fun anadirMonumentos() {
        monumentos.add(
            Monumento(
                1,
                "Catedral",
                "https://www.guiasturisticosburgos.com/media/galerias/catedral-de-burgos-id-730.jpg",
                "Templo católico dedicado a la Virgen María. Estilo gótico, aunque posee, en su interior, varios elementos decorativos renacentistas y barrocos",


            )
        )
        monumentos.add(
            Monumento(
                2,
                "El cid Campeador",
                "https://images.mnstatic.com/50/d8/50d84df2e49ab3c0d2879d9778488ef9.jpg",
                "Rodrigo Díaz de Vivar, también conocido como el Cid Campeador, fue un líder militar castellano que llegó a dominar al frente de su propia mesnada",


                )
        )
        monumentos.add(
            Monumento(
                3,
                "Castillo de Burgos",
                "https://www.terranostrum.es/images/content/full/Castillo-burgos-1.jpg",
                "Fortaleza que se encuentra en el cerro del Castillo. La primera torre fue levantada por Diego Porcelos  en tiempos de la reconquista, en el año 884",


                )
        )
    }
    fun existeMonumento(id:Long):Int{
        val pos = monumentos.indexOf(Monumento(id))
        return pos
    }
    fun anadir(monumento: Monumento):Int{
        monumentos.add(monumento)
        val pos = monumentos.indexOf(monumento)
        return pos
    }

}