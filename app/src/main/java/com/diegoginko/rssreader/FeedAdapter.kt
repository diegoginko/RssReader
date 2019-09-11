package com.diegoginko.rssreader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ViewHolder(v : View){

    val tvNombre : TextView = v.findViewById(R.id.tvNombre)
    val tvArtista : TextView = v.findViewById(R.id.tvArtista)

}

class FeedAdapter(context: Context, private val resource: Int, private val objeto: List<FeedEntry>) :
    ArrayAdapter<FeedEntry>(context, resource, objeto) {

    private val TAG = "FeedAdapter"
    private val layoutInflator = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View
        val viewHolder : ViewHolder
        if(convertView == null){ //Creo la vista una sola vez, si ya viene, la reutilizo
            view = layoutInflator.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val objetoActual : FeedEntry = objeto[position]

        viewHolder.tvNombre.text = objetoActual.name
        viewHolder.tvArtista.text = objetoActual.artist

        return view //retorno la vista que acabo de crear
    }

    override fun getCount(): Int {  //Si no haces override de este metodo, no vas a ver objetos en el listado, MUY IMPORTANTE
        return objeto.size
    }
}