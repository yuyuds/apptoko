<?php

use LDAP\Result;

defined('BASEPATH') or exit('No direct script access allowed');

class M_transaksi extends CI_Model
{

    function __construct()
    {
        parent::__construct();
        $this->load->database();
    }


    //--- transaksi ---//

    //get transaksi
    public function get_transaksi()
    {
        // $data = $this->db->get('transaksi');
        $this->db->select('transaksi.id, admin.nama, transaksi.total, transaksi.tanggal');
        $this->db->from('transaksi');
        $this->db->join('admin', 'admin.id = transaksi.admin_id');
        $query = $this->db->get();
        return $query->result_array();
    }

    //get transaksi bulan ini
    public function get_transaksiBulanIni()
    {
        $this->db->select('transaksi.id, transaksi.total, transaksi.tanggal, admin.nama_admin');
        $this->db->from('transaksi');
        $this->db->join('admin', 'admin.id = transaksi.admin_id');
        $this->db->where('month(tanggal)', date('m'));
        $query = $this->db->get();
        return $query->result_array();
    }

    //tambahan
    public function get_totaltransaksiBulanIni()
    {
        $this->db->select('sum(transaksi.total) as total');
        $this->db->from('transaksi');
        $this->db->join('admin', 'admin.id = transaksi.admin_id');
        $this->db->where('month(tanggal)', date('m'));
        $query = $this->db->get();
        return $query->row_object();
    }

    //insert transaksi
    public function insert_transaksi($data)
    {
        $this->db->insert('transaksi', $data);
        $insert_id = $this->db->insert_id();
        $result = $this->db->get_where('transaksi', array('id' => $insert_id));
        return $result->row_array();
    }

    //update transaksi
    public function update_transaksi($data, $id)
    {
        $this->db->where('id', $id);
        $this->db->update('transaksi', $data);

        $result = $this->db->get_where('transaksi', array('id' => $id));
        return $result->row_array();
    }

    //delete transaksi
    public function delete_transaksi($id)
    {
        $result = $this->db->get_where('transaksi', array('id' => $id));

        $this->db->where('id', $id);
        $this->db->delete('transaksi');

        return $result->row_array();
    }

    //get transaksi by id
    public function cekTransaksiExist($id)
    {
        $data = array(
            "id" => $id
        );

        $this->db->where($data);
        $result = $this->db->get('transaksi');

        if (empty($result->row_array())) {
            return false;
        }

        return true;
    }
}
