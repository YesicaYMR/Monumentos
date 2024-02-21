package com.ymr.rmachor.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ymr.rmachor.R
import com.ymr.rmachor.controlador.EventosListener
import com.ymr.rmachor.databinding.ItemMonumentBinding
import com.ymr.rmachor.datos.Monumento

class AdaptadorMonumento (
    private var monumentos: ArrayList<Monumento>,
    private var listener: EventosListener
) :
//caracteristicas de cada elemento
RecyclerView.Adapter<AdaptadorMonumento.ViewHolder>() {
    private lateinit var contexto: Context
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMonumentBinding.bind(view)


        fun setListener(monumento: Monumento) {
            //cuando pulses la tarjeta
            with(binding.root) {
                setOnClickListener { listener.editar(monumento) }
            }

        }
    }
    //al ser un recycler hay que tener estos 3 metodos obligatoriamente

    //cuando inflas un elemento donde se encuentra el diseño
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        contexto = parent.context
        val view =
            LayoutInflater.from(contexto).inflate(R.layout.item_monument, parent, false)
        return ViewHolder(view)
    }
    //dime con que elemento estas dentro y muestra la informacion
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val monumento = monumentos.get(position)
        with(holder) {
            binding.tvName.text = monumento.name

            setListener(monumento)
            Glide.with(contexto)
                .load(monumento.photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgPhoto)
        }
    }
    //dice cuantos numeros de elementos tiene que haber
    override fun getItemCount(): Int = monumentos.size
}