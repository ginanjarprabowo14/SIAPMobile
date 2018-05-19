<?php

require_once 'DBOperations.php';

class Functions{

private $db;

public function __construct() {

      $this -> db = new DBOperations();

}

public function registerUser($name, $email, $password) {

   $db = $this -> db;

   if (!empty($name) && !empty($email) && !empty($password)) {

      if ($db -> checkUserExist($email)) {

         $response["result"] = "failure";
         $response["message"] = "User Already Registered !";
         return json_encode($response);

      } else {

         $result = $db -> insertData($name, $email, $password);

         if ($result) {

            $response["result"] = "success";
            $response["message"] = "User Registered Successfully !";
            return json_encode($response);

         } else {

            $response["result"] = "failure";
            $response["message"] = "Registration Failure";
            return json_encode($response);

         }
      }
   } else {

      return $this -> getMsgParamNotEmpty();

   }
}

public function loginUser($email, $password) {

  $db = $this -> db;

  if (!empty($email) && !empty($password)) {

    if ($db -> checkUserExist($email)) {

       $result =  $db -> checkLogin($email, $password);

       if(!$result) {

        $response["result"] = "failure";
        $response["message"] = "Invalid Login Credentials";
        return json_encode($response);

       } else {

        $response["result"] = "success";
        $response["message"] = "Login Sucessful";
        $response["user"] = $result;
        return json_encode($response);

       }
    } else {

      $response["result"] = "failure";
      $response["message"] = "Invalid Login Credentials";
      return json_encode($response);

    }
  } else {

      return $this -> getMsgParamNotEmpty();
    }
}

public function buatTiket($no_tiket, $nama_pengadu, 
$alamat_pengadu, $no_telepon_pengadu, $topik_aduan, $isi_aduan) {

  $db = $this -> db;

  if (!empty($no_tiket) && !empty($nama_pengadu) && !empty($isi_aduan) && !empty($topik_aduan) && !empty($no_telepon_pengadu) && !empty($alamat_pengadu)) {

        $result = $db -> buatTiketDB($no_tiket, $nama_pengadu, 
        $alamat_pengadu, $no_telepon_pengadu, $topik_aduan, $isi_aduan);

        if ($result) {

           $response["result"] = "success";
           $response["message"] = "Create Ticket Successfully !";
           return json_encode($response);

        } else {

           $response["result"] = "failure";
           $response["message"] = "Create Ticket Failure";
           return json_encode($response);

        }
     
  } else {

     return $this -> getMsgParamNotEmpty();

  }
}

public function checkNIK($nik) {

  $db = $this -> db;

  if (!empty($nik)) {

     if ($db -> checkNIKDB($nik)) {

        $response["result"] = "success";
        $response["message"] = "NIK Terdaftar";
        return json_encode($response);

     } else {

           $response["result"] = "failure";
           $response["message"] = "NIK Tidak Terdaftar";
           return json_encode($response);

        }
     }
   else {

     return $this -> getMsgParamNotEmpty();

  }
}

public function checkTiket($no_tiket) {

  $db = $this -> db;

  if (!empty($no_tiket)) {

     if ($db -> checkTiketDB($no_tiket)) {

      $result =  $db -> checkJawabanTiket($no_tiket);
      $results =  $db -> checkJawabanTikets($no_tiket);

        $response["result"] = "success";
        $response["message"] = "Tiket Ditemukan";
        $response["jawaban"] = $result;
        $response["status_aduan"] = $results;
        return json_encode($response);

     } else {

           $response["result"] = "failure";
           $response["message"] = "Tiket Tidak Ditemukan";
           return json_encode($response);

        }
     }
   else {

     return $this -> getMsgParamNotEmpty();

  }
}

public function deleteTiket($no_tiket) {

  $db = $this -> db;

  if (!empty($no_tiket)) {

       $result =  $db -> deleteTiketDB($no_tiket);

       if(!$result) {

        $response["result"] = "failure";
        $response["message"] = "Delete Unsuccessful";
        return json_encode($response);

       } else {

        $response["result"] = "success";
        $response["message"] = "Delete Successfully";
        return json_encode($response);

       }
  
  } else {

      return $this -> getMsgParamNotEmpty();
    }
}

public function terimaAdminPool($no_tiket, $username) {

  $db = $this -> db;

  if (!empty($no_tiket)) {

       $result =  $db -> terimaAdminPoolDB($no_tiket, $username);

       if(!$result) {

        $response["result"] = "failure";
        $response["message"] = "Terima Admin Unsuccessful";
        return json_encode($response);

       } else {

        $response["result"] = "success";
        $response["message"] = "Terima Admin Successfully";
        return json_encode($response);

       }
  
  } else {

      return $this -> getMsgParamNotEmpty();
    }
}

public function jawabTiket($no_tiket, $username, $isi) {

  $db = $this -> db;

  if (!empty($no_tiket)) {

      $result1 = $db -> flagTiketJawab($no_tiket);
       $result =  $db -> jawabTiketDB($no_tiket, $username, $isi);

       if(!$result || !$result1) {

        $response["result"] = "failure";
        $response["message"] = "Jawab Tiket Unsuccessful";
        return json_encode($response);

       } else {

        $response["result"] = "success";
        $response["message"] = "Jawab Tiket Successfully";
        return json_encode($response);

       }
  
  } else {

      return $this -> getMsgParamNotEmpty();
    }
}

public function kembaliSKPD($no_tiket, $username) {

  $db = $this -> db;

  if (!empty($no_tiket)) {

       $result =  $db -> kembaliSKPDDB($no_tiket, $username);

       if(!$result) {

        $response["result"] = "failure";
        $response["message"] = "Kembali SKPD Unsuccessful";
        return json_encode($response);

       } else {

        $response["result"] = "success";
        $response["message"] = "Kembali SKPD Successfully";
        return json_encode($response);

       }
  
  } else {

      return $this -> getMsgParamNotEmpty();
    }
}

public function belumterimaSKPD($no_tiket, $departemen) {

  $db = $this -> db;

  if (!empty($no_tiket)) {

       $result =  $db -> belumTerimaSKPDDB($no_tiket, $departemen);

       if(!$result) {

        $response["result"] = "failure";
        $response["message"] = "Belum Terima SKPD Unsuccessful";
        return json_encode($response);

       } else {

        $response["result"] = "success";
        $response["message"] = "Belum Terima SKPD Successfully";
        return json_encode($response);

       }
  
  } else {

      return $this -> getMsgParamNotEmpty();
    }
}

public function terimaSKPD($no_tiket, $username) {

  $db = $this -> db;

  if (!empty($no_tiket)) {

       $result =  $db -> terimaSKPDDB($no_tiket, $username);

       if(!$result) {

        $response["result"] = "failure";
        $response["message"] = "Terima SKPD Unsuccessful";
        return json_encode($response);

       } else {

        $response["result"] = "success";
        $response["message"] = "Terima SKPD Successfully";
        return json_encode($response);

       }
  
  } else {

      return $this -> getMsgParamNotEmpty();
    }
}

public function selesaiAduan($no_tiket) {

  $db = $this -> db;

  if (!empty($no_tiket)) {

       $result =  $db -> selesaiAduanDB($no_tiket);

       if(!$result) {

        $response["result"] = "failure";
        $response["message"] = "Selesai Aduan Unsuccessful";
        return json_encode($response);

       } else {

        $response["result"] = "success";
        $response["message"] = "Selesai Aduan Successfully";
        return json_encode($response);

       }
  
  } else {

      return $this -> getMsgParamNotEmpty();
    }
}

public function kategoriAduan($no_tiket, $topik_aduan) {

  $db = $this -> db;

  if (!empty($no_tiket)) {

       $result =  $db -> kategoriAduanDB($no_tiket, $topik_aduan);

       if(!$result) {

        $response["result"] = "failure";
        $response["message"] = "Kategori Aduan Unsuccessful";
        return json_encode($response);

       } else {

        $response["result"] = "success";
        $response["message"] = "Kategori Aduan Successfully";
        return json_encode($response);

       }
  
  } else {

      return $this -> getMsgParamNotEmpty();
    }
}

public function getTiketPool() {

  $db = $this -> db;
  $result = $db -> getDataTiketPool();

    return json_encode($result);
    
}

public function getTiketSKPD() {

  $db = $this -> db;
  $result = $db -> getDataTiketSKPD();

    return json_encode($result);
    
}

public function getTiketPoolBelumDiterima() {

  $db = $this -> db;
  $result = $db -> getDataTiketPoolBelumDiterima();

    return json_encode($result);
    
}

public function getTiketPoolDiterima() {

  $db = $this -> db;
  $result = $db -> getDataTiketPoolDiterima();

    return json_encode($result);
    
}

public function getTiketSKPDBelumDiterima() {

  $db = $this -> db;
  $result = $db -> getDataTiketSKPDBelumDiterima();

    return json_encode($result);
    
}

public function getTiketSKPDDiterima() {

  $db = $this -> db;
  $result = $db -> getDataTiketSKPDDiterima();

    return json_encode($result);
    
}

public function changePassword($email, $old_password, $new_password) {

  $db = $this -> db;

  if (!empty($email) && !empty($old_password) && !empty($new_password)) {

    if(!$db -> checkLogin($email, $old_password)){

      $response["result"] = "failure";
      $response["message"] = 'Invalid Old Password';
      return json_encode($response);

    } else {

    $result = $db -> changePassword($email, $new_password);

      if($result) {

        $response["result"] = "success";
        $response["message"] = "Password Changed Successfully";
        return json_encode($response);

      } else {

        $response["result"] = "failure";
        $response["message"] = 'Error Updating Password';
        return json_encode($response);

      }
    }
  } else {

      return $this -> getMsgParamNotEmpty();
  }
}

public function isEmailValid($email){

  return filter_var($email, FILTER_VALIDATE_EMAIL);
}

public function getMsgParamNotEmpty(){

  $response["result"] = "failure";
  $response["message"] = "Parameters should not be empty !";
  return json_encode($response);

}

public function getMsgInvalidParam(){

  $response["result"] = "failure";
  $response["message"] = "Invalid Parameters";
  return json_encode($response);

}

public function getMsgInvalidEmail(){

  $response["result"] = "failure";
  $response["message"] = "Invalid Email";
  return json_encode($response);

}
}