package com.pcs.apptoko.response.itemTransaksi

data class ItemTransaksi(
    val id: String,
    val transaksi_id: String,
    val produk_id: String,
    val qty: String,
    val harga_saat_transaksi: String,
    val sub_total: String
)