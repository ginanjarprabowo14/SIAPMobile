<?php

require_once 'Functions.php';

$fun = new Functions();

if ($_SERVER['REQUEST_METHOD'] == 'POST')
{
  $data = json_decode(file_get_contents("php://input"));

  if(isset($data -> operation)){

   $operation = $data -> operation;

   if(!empty($operation)){

      if($operation == 'register'){

         if(isset($data -> user ) && !empty($data -> user) && isset($data -> user -> name)
            && isset($data -> user -> email) && isset($data -> user -> password)){

            $user = $data -> user;
            $name = $user -> name;
            $email = $user -> email;
            $password = $user -> password;

          if ($fun -> isEmailValid($email)) {

            echo $fun -> registerUser($name, $email, $password);

          } else {

            echo $fun -> getMsgInvalidEmail();
          }

         } else {

            echo $fun -> getMsgInvalidParam();

         }

      }else if ($operation == 'login') {

        if(isset($data -> user ) && !empty($data -> user) && isset($data -> user -> email) && isset($data -> user -> password)){

          $user = $data -> user;
          $email = $user -> email;
          $password = $user -> password;

          echo $fun -> loginUser($email, $password);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      } else if ($operation == 'chgPass') {

        if(isset($data -> user ) && !empty($data -> user) && isset($data -> user -> email) && isset($data -> user -> old_password)
          && isset($data -> user -> new_password)){

          $user = $data -> user;
          $email = $user -> email;
          $old_password = $user -> old_password;
          $new_password = $user -> new_password;

          echo $fun -> changePassword($email, $old_password, $new_password);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }
      else if ($operation == 'deleteTiket') {

        if(isset($data -> tiket ) && !empty($data -> tiket) && isset($data -> tiket -> no_tiket)){

          $tiket = $data -> tiket;
          $no_tiket = $tiket -> no_tiket;

          echo $fun -> deleteTiket($no_tiket);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }
      else if ($operation == 'terimaAdminPool') {

        if(isset($data -> tiket ) && !empty($data -> tiket) && isset($data -> tiket -> no_tiket) && isset($data -> tiket -> username)){

          $tiket = $data -> tiket;
          $no_tiket = $tiket -> no_tiket;
          $username = $tiket -> username;

          echo $fun -> terimaAdminPool($no_tiket, $username);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }
      else if ($operation == 'kembaliSKPD') {

        if(isset($data -> tiket ) && !empty($data -> tiket) && isset($data -> tiket -> no_tiket) && isset($data -> tiket -> username)){

          $tiket = $data -> tiket;
          $no_tiket = $tiket -> no_tiket;
          $username = $tiket -> username;

          echo $fun -> kembaliSKPD($no_tiket, $username);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }
      else if ($operation == 'belumTerimaSKPD') {

        if(isset($data -> tiket ) && !empty($data -> tiket) && isset($data -> tiket -> no_tiket) && isset($data -> tiket -> departemen)){

          $tiket = $data -> tiket;
          $no_tiket = $tiket -> no_tiket;
          $departemen = $tiket -> departemen;

          echo $fun -> belumTerimaSKPD($no_tiket, $departemen);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }
      else if ($operation == 'terimaSKPD') {

        if(isset($data -> tiket ) && !empty($data -> tiket) && isset($data -> tiket -> no_tiket) && isset($data -> tiket -> username)){

          $tiket = $data -> tiket;
          $no_tiket = $tiket -> no_tiket;
          $username = $tiket -> username;

          echo $fun -> terimaSKPD($no_tiket, $username);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }
      else if ($operation == 'selesaiAduan') {

        if(isset($data -> tiket ) && !empty($data -> tiket) && isset($data -> tiket -> no_tiket)){

          $tiket = $data -> tiket;
          $no_tiket = $tiket -> no_tiket;

          echo $fun -> selesaiAduan($no_tiket);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }
      else if ($operation == 'kategoriAduan') {

        if(isset($data -> tiket ) && !empty($data -> tiket) && isset($data -> tiket -> no_tiket)){

          $tiket = $data -> tiket;
          $no_tiket = $tiket -> no_tiket;
          $topik_aduan = $tiket -> topik_aduan;

          echo $fun -> kategoriAduan($no_tiket, $topik_aduan);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }
      else if ($operation == 'jawabTiket') {

        if(isset($data -> tiket ) && !empty($data -> tiket) && isset($data -> tiket -> no_tiket) && isset($data -> tiket -> isi)){

          $tiket = $data -> tiket;
          $no_tiket = $tiket -> no_tiket;
          $username = $tiket -> username;
          $isi = $tiket -> isi;

          echo $fun -> jawabTiket($no_tiket, $username, $isi);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }
      else if ($operation == 'checkNIK') {

        if(isset($data -> nik ) && !empty($data -> nik)){

          $nik = $data -> nik;
          $nik = $nik -> nik;

          echo $fun -> checkNIK($nik);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }
      else if ($operation == 'checkTiket') {

        if(isset($data -> tiket ) && !empty($data -> tiket)){

          $tiket = $data -> tiket;
          $no_tiket = $tiket -> no_tiket;

          echo $fun -> checkTiket($no_tiket);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }
      
      else if ($operation == 'buatTiket') {

        if(isset($data -> tiket ) && !empty($data -> tiket)){

          $tiket = $data -> tiket;
          $no_tiket = $tiket -> no_tiket;
          $nama_pengadu = $tiket -> nama_pengadu;
          $alamat_pengadu = $tiket -> alamat_pengadu;
          $no_telepon_pengadu = $tiket -> no_telepon_pengadu;
          $topik_aduan = $tiket -> topik_aduan;
          $isi_aduan = $tiket -> isi_aduan;

          echo $fun -> buatTiket($no_tiket, $nama_pengadu, 
          $alamat_pengadu, $no_telepon_pengadu, $topik_aduan, $isi_aduan);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }
   }else{

      echo $fun -> getMsgParamNotEmpty();

   }
  } else {

      echo $fun -> getMsgInvalidParam();

  }
} else if ($_SERVER['REQUEST_METHOD'] == 'GET'){

  $operations = explode('/', $_SERVER['REQUEST_URI']);

  if($operations[3] == 'getTiketPoolBelumDiterima'){

      echo $fun -> getTiketPoolBelumDiterima();

  } else if($operations[3] == 'getTiketPoolDiterima'){

      echo $fun -> getTiketPoolDiterima();

  } else if($operations[3] == 'getTiketSKPDBelumDiterima'){

    echo $fun -> getTiketSKPDBelumDiterima();

  } else if($operations[3] == 'getTiketSKPDDiterima'){

    echo $fun -> getTiketSKPDDiterima();

  } else if($operations[3] == 'getTiketSKPD'){

    echo $fun -> getTiketSKPD();

  } else if($operations[3] == 'getTiketPool'){

    echo $fun -> getTiketPool();

  }
  else {
    echo $fun -> getMsgParamNotEmpty();
  }


}