package com.pcs.apptoko.response.produk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Produk(
    val id: String,
    val admin_id: String,
    val nama_admin: String,
    val nama: String,
    val harga: String,
    val stok: String,
    val stokjual: String,
    val status: Int
):Parcelable