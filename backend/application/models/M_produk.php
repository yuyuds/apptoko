<?php

use LDAP\Result;

defined('BASEPATH') or exit('No direct script access allowed');

class M_produk extends CI_Model
{

    function __construct()
    {
        parent::__construct();
        $this->load->database();
    }


    //--- PRODUK ---//

    //get produk
    public function get_produkbeli()
    {
        $this->db->select('produk.id, produk.admin_id, admin.nama_admin, produk.nama, produk.harga, produk.stok, produk.stokjual, produk.status ');
        $this->db->from('produk');
        $this->db->join('admin', 'admin.id = produk.admin_id');
        // $this->db->where('stok >=', 0);

        $query = $this->db->get();
        return $query->result_array();
    }

    public function get_produkjual()
    {
        $this->db->select('produk.id, produk.admin_id, admin.nama_admin , produk.nama, produk.harga, produk.stok, produk.stokjual, produk.status');
        $this->db->from('produk');
        $this->db->join('admin', 'admin.id = produk.admin_id');
        $this->db->where('status', 1); //tambahan
        $this->db->where('stokjual >', 0);

        $query = $this->db->get();
        return $query->result_array();
    }

    //insert produk
    public function insert_produktambah($data)
    {
        $insert = $this->db->insert('produk', $data);
    }

    //update produk
    public function update_produkbeli($data, $id)
    {
        //stok dan stok jual baru
        $result_produkbeli = $this->db->get_where('produk', array('id' => $id));
        $result_produkbeli = $result_produkbeli->row_array();
        $stok_lama = $result_produkbeli["stok"];
        $stok_baru = $stok_lama - $data["stokjual"];
        $stokjual_lama = $result_produkbeli["stokjual"];
        $stokjual_baru = $stokjual_lama + $data["stokjual"];
        $data_produk_update = array(
            "stok" => $stok_baru,
            "stokjual" => $stokjual_baru,
            "status" => 1
        );

        $this->db->where('id', $id);
        $this->db->update('produk', $data_produk_update);

        $result = $this->db->get_where('produk', array('id' => $id));
        return $result->row_array();
    }

    public function update_produkjual($data, $id)
    {
        $this->db->where('id', $id);
        $this->db->update('produk', $data);

        $result = $this->db->get_where('produk', array('id' => $id));
        return $result->row_array();
    }

    public function update_produkjualdelete($data, $id)
    {
        $this->db->where('id', $id);
        $this->db->update('produk', $data);

        $result = $this->db->get_where('produk', array('id' => $id));
        return $result->row_array();
    }

    //delete produk
    public function delete_produk($id)
    {
        $result = $this->db->get_where('produk', array('id' => $id));

        $this->db->where('id', $id);
        $this->db->delete('produk');

        return $result->row_array();
    }

    //get produk by id
    public function cekProdukExist($id)
    {
        $data = array(
            "id" => $id
        );

        $this->db->where($data);
        $result = $this->db->get('produk');

        if (empty($result->row_array())) {
            return false;
        }

        return true;
    }
}
