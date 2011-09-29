<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Sincronizacao
 *
 * @author ricardo
 */
class Sincronizacao {
    public $cdusuario;
    public $cdposto;
    public $dtsinc;
    
    public function getCdUsuario(){
        return $this->cdusuario;
    }
    
    public function setCdUsuario($idUsuario){
        $this->cdusuario = $idUsuario;
    }
    
    public function getCdPosto(){
        return $this->cdposto;
    }
    
    public function setCdPosto($idPosto){
        $this->cdposto = $idPosto;
    }
    
    public function getDtSinc(){
        return $this->dtsinc;
    }
    
    public function setDtSinc($data){
        $this->dtsinc = $data;
    }
}

?>
