package com.pcs.apptoko

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.pcs.apptoko.api.BaseRetrofit
import com.pcs.apptoko.response.produk.Produk
import com.pcs.apptoko.response.produk.ProdukResponsePost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeliFragment : Fragment() {

    private val api by lazy { BaseRetrofit().endpoint }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_beli, container, false)

        val btnBeliProduk = view.findViewById<Button>(R.id.btnBeliProduk)

        val txtFormNama = view.findViewById<TextView>(R.id.txtFormNama)
        val txtFormHarga = view.findViewById<TextView>(R.id.txtFormHarga)
        val txtFormStok = view.findViewById<TextView>(R.id.txtFormStok)
        val txtFormStokJual = view.findViewById<TextView>(R.id.txtFormStokJual)
        val txtFormStatus = view.findViewById<TextView>(R.id.txtFormStatus)

        val status = arguments?.getString("status")
        val produk = arguments?.getParcelable<Produk>("produk")
        val visible = arguments?.getString("stok")

        Log.d("produkForm",produk.toString())

        if(status=="edit"){
            txtFormNama.setText(produk?.nama.toString())
            txtFormHarga.setText(produk?.harga.toString())
            txtFormStok.setText(produk?.stok.toString())
//            txtFormStokJual.setText(produk?.stokjual.toString())
            txtFormStatus.setText(produk?.status.toString())
        }

//        if(visible=="stok") {
//            produk?.stok?.toInt()!! <= 0
//        }

        btnBeliProduk.setOnClickListener{
            val txtFormNama = view.findViewById<TextInputEditText>(R.id.txtFormNama)
            val txtFormHarga = view.findViewById<TextInputEditText>(R.id.txtFormHarga)
            val txtFormStok = view.findViewById<TextInputEditText>(R.id.txtFormStok)
            val txtFormStokJual = view.findViewById<TextInputEditText>(R.id.txtFormStokJual)

            val token = LoginActivity.sessionManager.getString("TOKEN")
            val adminId = LoginActivity.sessionManager.getString("ADMIN_ID")

            if(status=="edit"){
                if(txtFormStokJual.text.toString().toInt() > txtFormStok.text.toString().toInt()) {
                    Toast.makeText(activity?.applicationContext, "Pembelian melebihi stok!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.pembelianFragment)
                }
                else{
                    api.putProdukBeli(token.toString(),produk?.id.toString().toInt(),adminId.toString().toInt(),txtFormNama.text.toString(),txtFormHarga.text.toString().toInt(),
                        txtFormStok.text.toString().toInt(), txtFormStokJual.text.toString().toInt(), 1).enqueue(object :
                        Callback<ProdukResponsePost> {
                        override fun onResponse(
                            call: Call<ProdukResponsePost>,
                            response: Response<ProdukResponsePost>
                        ) {

                            Log.d("ResponData",response.body()!!.data.toString())
                            Toast.makeText(activity?.applicationContext,"Data "+ response.body()!!.data.produk.nama.toString() +" dibeli!",Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.produkFragment)
                        }

                        override fun onFailure(call: Call<ProdukResponsePost>, t: Throwable) {
                            Log.e("Error",t.toString())
                        }

                    })
                }
            }
//            else{
//                api.postProdukBeli(token.toString(),adminId.toString().toInt(),txtFormNama.text.toString(),txtFormHarga.text.toString().toInt(),
//                    txtFormStok.text.toString().toInt(), txtFormStokJual.text.toString().toInt(), 1 ).enqueue(object :
//                    Callback<ProdukResponsePost> {
//                    override fun onResponse(
//                        call: Call<ProdukResponsePost>,
//                        response: Response<ProdukResponsePost>
//                    ) {
////                        val id_produk = response.body()!!.data.produk.id
////                        Log.e("id_produk",id_produk.toString())
//
//                        Log.d("Data",response.toString())
//                        Toast.makeText(activity?.applicationContext,"Data di input",Toast.LENGTH_LONG).show()
//
//                        findNavController().navigate(R.id.produkFragment)
//                    }
//
//                    override fun onFailure(call: Call<ProdukResponsePost>, t: Throwable) {
//                        Log.e("Error",t.toString())
//                    }
//
//                })
//            }
        }

        return view
    }


}