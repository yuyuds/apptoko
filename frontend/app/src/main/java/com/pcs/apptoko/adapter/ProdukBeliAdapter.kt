package com.pcs.apptoko.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pcs.apptoko.LoginActivity
import com.pcs.apptoko.R
import com.pcs.apptoko.api.BaseRetrofit
import com.pcs.apptoko.response.produk.Produk
import com.pcs.apptoko.response.produk.ProdukResponsePost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukBeliAdapter(val listProduk: List<Produk>):RecyclerView.Adapter<ProdukBeliAdapter.ViewHolder>() {

    private val api by lazy { BaseRetrofit().endpoint }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_produkbeli,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produk = listProduk[position]
        holder.txtNamaProduk.text = produk.nama
        holder.txtHarga.text = produk.harga

//        val token = LoginActivity.sessionManager.getString("TOKEN")
//        val adminId = LoginActivity.sessionManager.getString("ADMIN_ID")

        holder.btnBeli.setOnClickListener{
            //Toast.makeText(holder.itemView.context,produk.nama,Toast.LENGTH_LONG).show()
            val bundle = Bundle()
            bundle.putParcelable("produk",produk)
            bundle.putString("status","edit")
//            bundle.putString("stok","visible")

            holder.itemView.findNavController().navigate(R.id.beliFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return listProduk.size
    }

    class ViewHolder(itemViem : View) : RecyclerView.ViewHolder(itemViem){
        val txtNamaProduk = itemViem.findViewById(R.id.txtNamaProduk) as TextView
        val txtHarga = itemViem.findViewById(R.id.txtHarga) as TextView
        val btnBeli = itemViem.findViewById(R.id.btnBeli) as ImageButton
    }

}