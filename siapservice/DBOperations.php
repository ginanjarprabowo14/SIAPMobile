<?php

class DBOperations{

    public $host = "localhost";
    public $user = "root";
    public $db = "db_loginRegister";
    public $pass = "";
    public $conn;

public function __construct() {

   $this -> conn = new PDO("mysql:host=".$this -> host.";dbname=".$this -> db, $this -> user, $this -> pass);

}

public function insertData($name,$email,$password){

    $coba = new Common();
    $encrypted_password = $coba->encrypt($password);
 
    $sql = 'INSERT INTO users SET name =:name,
     email =:email,password =:password,created_at = NOW(),roles = 1';
 
    $query = $this ->conn ->prepare($sql);
    $query->execute(array(':name' => $name, ':email' => $email,
      ':password' => $encrypted_password));
 
     if ($query) {
 
         return true;
 
     } else {
 
         return false;
 
     }
  }

  public function buatTiketDB($no_tiket, $nama_pengadu, 
  $alamat_pengadu, $no_telepon_pengadu, $topik_aduan, $isi_aduan){

 
    // $sql = 'INSERT INTO tiket SET name =:name,
    //  email =:email,password =:password,created_at = NOW(),roles = 1';
 
    $sql = 'INSERT INTO tiket SET id ="", no_tiket =:no_tiket, nama_pengadu =:nama_pengadu, alamat_pengadu =:alamat_pengadu, no_telepon_pengadu =:no_telepon_pengadu,
    topik_aduan =:topik_aduan, isi_aduan =:isi_aduan, status_aduan="1", pool_terima="", skpd_balik="", skpd_terima="", departemen="", status_jawab=""';

    $query = $this ->conn ->prepare($sql);
    $query->execute(array(':no_tiket' => $no_tiket, ':nama_pengadu' => $nama_pengadu, ':alamat_pengadu' => $alamat_pengadu, ':no_telepon_pengadu' => $no_telepon_pengadu, ':topik_aduan' => $topik_aduan, ':isi_aduan' => $isi_aduan));
 
     if ($query) {
 
         return true;
 
     } else {
 
         return false;
 
     }
  }
 
  public function checkLogin($email, $password) {
 
     $sql = 'SELECT * FROM users WHERE email = :email';
     $query = $this -> conn -> prepare($sql);
     $query -> execute(array(':email' => $email));
     $data = $query -> fetchObject();
     $db_encrypted_password = $data -> password;
     $coba = new Common();

     
     if ($this -> verifyHash($password,$db_encrypted_password) ) {
 
         $user["name"] = $data -> name;
         $user["email"] = $data -> email;
         $user["roles"] = $data -> roles;
         $user["username"] = $data -> username;
         return $user;
 
     } else {
 
         return false;
     }
  }
 
  public function changePassword($email, $password){
 
     $coba = new Common();
     $hash = $coba->encrypt($password);
     $encrypted_password = $hash;
 
     $sql = 'UPDATE users SET password = :password WHERE email = :email';
     $query = $this -> conn -> prepare($sql);
     $query -> execute(array(':email' => $email, ':password' => $encrypted_password));
 
     if ($query) {
 
         return true;
 
     } else {
 
         return false;
 
     }
  }
 
  public function checkUserExist($email){
 
     $sql = 'SELECT COUNT(*) from users WHERE email =:email';
     $query = $this -> conn -> prepare($sql);
     $query -> execute(array('email' => $email));
 
     if($query){
 
         $row_count = $query -> fetchColumn();
 
         if ($row_count == 0){
 
             return false;
 
         } else {
 
             return true;
 
         }
     } else {
 
         return false;
     }
  }

  public function checkNIKDB($nik){
 
    $sql = 'SELECT COUNT(*) from nik WHERE nik =:nik';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array('nik' => $nik));

    if($query){

        $row_count = $query -> fetchColumn();

        if ($row_count == 0){

            return false;

        } else {

            return true;

        }
    } else {

        return false;
    }
 }

 public function checkTiketDB($no_tiket){
 
    $sql = 'SELECT COUNT(*) from tiket WHERE no_tiket =:no_tiket';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array('no_tiket' => $no_tiket));

    if($query){

        $row_count = $query -> fetchColumn();

        if ($row_count == 0){

            return false;

        } else {

            return true;

        }
    } else {

        return false;
    }
 }

 public function checkJawabanTikets($no_tiket){

    $sqls = 'SELECT * from tiket WHERE no_tiket =:no_tiket';
    $querys = $this -> conn -> prepare($sqls);
    $querys -> execute(array('no_tiket' => $no_tiket));
    $datas = $querys -> fetchObject();
    $jawaban = $datas -> status_aduan;
 
    // $sql = 'SELECT * from jawaban_tiket WHERE no_tiket =:no_tiket';
    // $query = $this -> conn -> prepare($sql);
    // $query -> execute(array('no_tiket' => $no_tiket));
    // $data = $query -> fetchObject();
    // $jawaban = "Belum Terdapat Jawaban";

    // $sqls = 'SELECT COUNT(*) from jawaban_tiket WHERE no_tiket =:no_tiket';
    // $querys = $this -> conn -> prepare($sqls);
    // $querys -> execute(array('no_tiket' => $no_tiket));

    // $row_count = $querys -> fetchColumn();
    // if($row_count != 0){

    //     $jawaban = $datas -> isi;

    // }
    return $jawaban;
    
 }

 public function checkJawabanTiket($no_tiket){

    // $sqls = 'SELECT * from tiket WHERE no_tiket =:no_tiket';
    // $querys = $this -> conn -> prepare($sqls);
    // $querys -> execute(array('no_tiket' => $no_tiket));
    // $datas = $querys -> fetchObject();
    // $jawaban["status_aduan"] = $datas -> status_aduan;
 
    $sql = 'SELECT * from jawaban_tiket WHERE no_tiket =:no_tiket';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array('no_tiket' => $no_tiket));
    $data = $query -> fetchObject();
    $jawaban = "Belum Terdapat Jawaban";

    $sqls = 'SELECT COUNT(*) from jawaban_tiket WHERE no_tiket =:no_tiket';
    $querys = $this -> conn -> prepare($sqls);
    $querys -> execute(array('no_tiket' => $no_tiket));

    $row_count = $querys -> fetchColumn();
    if($row_count != 0){

        $jawaban = $data -> isi;

    }
    return $jawaban;
    
 }

  public function deleteTiketDB($no_tiket) {
 
    $sql = 'DELETE FROM tiket WHERE no_tiket = :no_tiket';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array(':no_tiket' => $no_tiket));
    
    if ($query) {
        
        return $no_tiket;

    } else {

        return false;
    }
 }

 public function terimaAdminPoolDB($no_tiket, $username) {
 
    $sql = 'UPDATE tiket SET status_aduan="2" , pool_terima = :username WHERE no_tiket = :no_tiket';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array(':no_tiket' => $no_tiket, ':username' => $username));
    
    if ($query) {
        
        return $no_tiket;

    } else {

        return false;
    }
 }

 public function kembaliSKPDDB($no_tiket, $username) {
 
    $sql = 'UPDATE tiket SET status_aduan="2", skpd_balik = :username WHERE no_tiket = :no_tiket';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array(':no_tiket' => $no_tiket, ':username' => $username));
    
    if ($query) {
        
        return $no_tiket;

    } else {

        return false;
    }
 }

 public function flagTiketJawab($no_tiket) {
 
    // 'INSERT INTO jawaban_tiket SET no_tiket =:no_tiket,
    //  created_date = NOW(), created_by =:created_by,isi =:isi';


    $sql = 'UPDATE tiket SET status_jawab ="s" WHERE no_tiket = :no_tiket';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array(':no_tiket' => $no_tiket));
    
    if ($query) {
        
        return $no_tiket;

    } else {

        return false;
    }
 }

 public function jawabTiketDB($no_tiket, $username, $isi) {
 
    // 'INSERT INTO jawaban_tiket SET no_tiket =:no_tiket,
    //  created_date = NOW(), created_by =:created_by,isi =:isi';
    $sql = 'INSERT INTO jawaban_tiket SET no_tiket =:no_tiket,
    created_date = NOW(), created_by =:created_by,isi =:isi';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array(':no_tiket' => $no_tiket, ':created_by' => $username, ':isi' => $isi));
    
    if ($query) {
        
        return $no_tiket;

    } else {

        return false;
    }
 }

 public function belumTerimaSKPDDB($no_tiket, $departemen) {
 
    $sql = 'UPDATE tiket SET status_aduan="3", departemen = :departemen WHERE no_tiket = :no_tiket';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array(':no_tiket' => $no_tiket, ':departemen' => $departemen));
    
    if ($query) {
        
        return $no_tiket;

    } else {

        return false;
    }
 }

 public function terimaSKPDDB($no_tiket, $username) {
 
    $sql = 'UPDATE tiket SET status_aduan="4", skpd_terima = :username WHERE no_tiket = :no_tiket';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array(':no_tiket' => $no_tiket, ':username' => $username));
    
    if ($query) {
        
        return $no_tiket;

    } else {

        return false;
    }
 }

 public function selesaiAduanDB($no_tiket) {
 
    $sql = 'UPDATE tiket SET status_aduan="5" WHERE no_tiket = :no_tiket';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array(':no_tiket' => $no_tiket));
    
    if ($query) {
        
        return $no_tiket;

    } else {

        return false;
    }
 }

 public function kategoriAduanDB($no_tiket, $topik_aduan) {
 
    $sql = 'UPDATE tiket SET topik_aduan = :topik_aduan WHERE no_tiket = :no_tiket';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array(':no_tiket' => $no_tiket, ':topik_aduan' => $topik_aduan));
    
    if ($query) {
        
        return $no_tiket;

    } else {

        return false;
    }
 }

 public function getDataTiketPool(){

    // initialize object
        $tiket = new tiket();
        
        $sql = 'SELECT * FROM tiket WHERE status_aduan = "1" OR status_aduan = "2"';
            $query = $this -> conn -> prepare($sql);
            $query -> execute();

        // query products
        $stmt = $query;
        $num = $stmt->rowCount();
        
        // check if more than 0 record found
        if($num>0){
        
            // products array
            $tiket_arr=array();
            $tiket_arr["tiket"]=array();
        
            // retrieve our table contents
            // fetch() is faster than fetchAll()
            // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
            while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
                // extract row
                // this will make $row['name'] to
                // just $name only
                extract($row);
        
                $tiket_item=array(
                    "id" => $id,
                    "no_tiket" => $no_tiket,
                    "nama_pengadu" => $nama_pengadu,
                    "alamat_pengadu" => $alamat_pengadu,
                    "no_telepon_pengadu" => $no_telepon_pengadu,
                    "topik_aduan" => $topik_aduan,
                    "isi_aduan" => $isi_aduan,
                    "status_aduan" => $status_aduan,
                    "pool_terima" => $pool_terima,
                    "skpd_balik" => $skpd_balik,
                    "skpd_terima" => $skpd_terima,
                    "status_jawab" => $status_jawab
                );
        
                array_push($tiket_arr["tiket"], $tiket_item);
            }

            return $tiket_arr;

        }
    }

    public function getDataTiketSKPD(){

        // initialize object
            $tiket = new tiket();
            
            $sql = 'SELECT * FROM tiket WHERE status_aduan = "3" OR status_aduan = "4"';
                $query = $this -> conn -> prepare($sql);
                $query -> execute();
    
            // query products
            $stmt = $query;
            $num = $stmt->rowCount();
            
            // check if more than 0 record found
            if($num>0){
            
                // products array
                $tiket_arr=array();
                $tiket_arr["tiket"]=array();
            
                // retrieve our table contents
                // fetch() is faster than fetchAll()
                // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
                while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
                    // extract row
                    // this will make $row['name'] to
                    // just $name only
                    extract($row);
            
                    $tiket_item=array(
                        "id" => $id,
                        "no_tiket" => $no_tiket,
                        "nama_pengadu" => $nama_pengadu,
                        "alamat_pengadu" => $alamat_pengadu,
                        "no_telepon_pengadu" => $no_telepon_pengadu,
                        "topik_aduan" => $topik_aduan,
                        "isi_aduan" => $isi_aduan,
                        "status_aduan" => $status_aduan,
                        "pool_terima" => $pool_terima,
                        "skpd_balik" => $skpd_balik,
                        "skpd_terima" => $skpd_terima,
                        "status_jawab" => $status_jawab
                    );
            
                    array_push($tiket_arr["tiket"], $tiket_item);
                }
    
                return $tiket_arr;
    
            }
        }

  public function getDataTiketPoolBelumDiterima(){

    // initialize object
        $tiket = new tiket();
        
        $sql = 'SELECT * FROM tiket WHERE status_aduan = "1"';
            $query = $this -> conn -> prepare($sql);
            $query -> execute();

        // query products
        $stmt = $query;
        $num = $stmt->rowCount();
        
        // check if more than 0 record found
        if($num>0){
        
            // products array
            $tiket_arr=array();
            $tiket_arr["tiket"]=array();
        
            // retrieve our table contents
            // fetch() is faster than fetchAll()
            // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
            while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
                // extract row
                // this will make $row['name'] to
                // just $name only
                extract($row);
        
                $tiket_item=array(
                    "id" => $id,
                    "no_tiket" => $no_tiket,
                    "nama_pengadu" => $nama_pengadu,
                    "alamat_pengadu" => $alamat_pengadu,
                    "no_telepon_pengadu" => $no_telepon_pengadu,
                    "topik_aduan" => $topik_aduan,
                    "isi_aduan" => $isi_aduan,
                    "status_aduan" => $status_aduan,
                    "pool_terima" => $pool_terima,
                    "skpd_balik" => $skpd_balik,
                    "skpd_terima" => $skpd_terima,
                    "status_jawab" => $status_jawab
                );
        
                array_push($tiket_arr["tiket"], $tiket_item);
            }

            return $tiket_arr;

        }
    }

    public function getDataTiketPoolDiterima(){

        // initialize object
            $tiket = new tiket();
            
            $sql = 'SELECT * FROM tiket WHERE status_aduan = "2"';
                $query = $this -> conn -> prepare($sql);
                $query -> execute();
    
            // query products
            $stmt = $query;
            $num = $stmt->rowCount();
            
            // check if more than 0 record found
            if($num>0){
            
                // products array
                $tiket_arr=array();
                $tiket_arr["tiket"]=array();
            
                // retrieve our table contents
                // fetch() is faster than fetchAll()
                // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
                while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
                    // extract row
                    // this will make $row['name'] to
                    // just $name only
                    extract($row);
            
                    $tiket_item=array(
                        "id" => $id,
                        "no_tiket" => $no_tiket,
                        "nama_pengadu" => $nama_pengadu,
                        "alamat_pengadu" => $alamat_pengadu,
                        "no_telepon_pengadu" => $no_telepon_pengadu,
                        "topik_aduan" => $topik_aduan,
                        "isi_aduan" => $isi_aduan,
                        "status_aduan" => $status_aduan,
                        "pool_terima" => $pool_terima,
                        "skpd_balik" => $skpd_balik,
                        "skpd_terima" => $skpd_terima,
                        "status_jawab" => $status_jawab
                    );
            
                    array_push($tiket_arr["tiket"], $tiket_item);
                }
    
                return $tiket_arr;
    
            }
        }

        public function getDataTiketSKPDBelumDiterima(){

            // initialize object
                $tiket = new tiket();
                
                $sql = 'SELECT * FROM tiket WHERE status_aduan = "3"';
                    $query = $this -> conn -> prepare($sql);
                    $query -> execute();
        
                // query products
                $stmt = $query;
                $num = $stmt->rowCount();
                
                // check if more than 0 record found
                if($num>0){
                
                    // products array
                    $tiket_arr=array();
                    $tiket_arr["tiket"]=array();
                
                    // retrieve our table contents
                    // fetch() is faster than fetchAll()
                    // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
                    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
                        // extract row
                        // this will make $row['name'] to
                        // just $name only
                        extract($row);
                
                        $tiket_item=array(
                            "id" => $id,
                            "no_tiket" => $no_tiket,
                            "nama_pengadu" => $nama_pengadu,
                            "alamat_pengadu" => $alamat_pengadu,
                            "no_telepon_pengadu" => $no_telepon_pengadu,
                            "topik_aduan" => $topik_aduan,
                            "isi_aduan" => $isi_aduan,
                            "status_aduan" => $status_aduan,
                            "pool_terima" => $pool_terima,
                            "skpd_balik" => $skpd_balik,
                            "skpd_terima" => $skpd_terima,
                            "status_jawab" => $status_jawab
                        );
                
                        array_push($tiket_arr["tiket"], $tiket_item);
                    }
        
                    return $tiket_arr;
        
                }
            }

            public function getDataTiketSKPDDiterima(){

                // initialize object
                    $tiket = new tiket();
                    
                    $sql = 'SELECT * FROM tiket WHERE status_aduan = "4"';
                        $query = $this -> conn -> prepare($sql);
                        $query -> execute();
            
                    // query products
                    $stmt = $query;
                    $num = $stmt->rowCount();
                    
                    // check if more than 0 record found
                    if($num>0){
                    
                        // products array
                        $tiket_arr=array();
                        $tiket_arr["tiket"]=array();
                    
                        // retrieve our table contents
                        // fetch() is faster than fetchAll()
                        // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
                        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
                            // extract row
                            // this will make $row['name'] to
                            // just $name only
                            extract($row);
                    
                            $tiket_item=array(
                                "id" => $id,
                                "no_tiket" => $no_tiket,
                                "nama_pengadu" => $nama_pengadu,
                                "alamat_pengadu" => $alamat_pengadu,
                                "no_telepon_pengadu" => $no_telepon_pengadu,
                                "topik_aduan" => $topik_aduan,
                                "isi_aduan" => $isi_aduan,
                                "status_aduan" => $status_aduan,
                                "pool_terima" => $pool_terima,
                                "skpd_balik" => $skpd_balik,
                                "skpd_terima" => $skpd_terima,
                                "status_jawab" => $status_jawab
                            );
                    
                            array_push($tiket_arr["tiket"], $tiket_item);
                        }
            
                        return $tiket_arr;
            
                    }
                }
 
  public function getHash($password) {
 
      $salt = sha1(rand());
      $salt = substr($salt, 0, 10);
      $encrypted = password_hash($password.$salt, PASSWORD_DEFAULT);
      $hash = array("salt" => $salt, "encrypted" => $encrypted);
 
      return $hash;
 
 }
 
 public function verifyHash($password, $hash) {
 
     return password_verify ($password, $hash);
 }

}

////////////////////////////////////////////////////////////

require_once("PasswordHash.php");

class Common 
{

    public function encrypt($password) 
    {
        $phpass = new PasswordHash(12, false);
        $hash = $phpass->HashPassword($password);
    	return $hash;
    }

}

class tiket{
 
    // object properties
    public $id;
    public $no_tiket;
    public $nama_pengadu;
    public $alamat_pengadu;
    public $no_telepon_pengadu;
    public $topik_aduan;
    public $isi_aduan;
    public $status_aduan;
    public $pool_terima;
    public $skpd_balik;
    public $skpd_terima;
 
}