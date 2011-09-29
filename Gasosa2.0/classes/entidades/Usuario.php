<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Usuario
 *
 * @author ricardo
 */
class Usuario {
    public $cdusuario;
    public $nmusuario;
    
    public function getCdUsuario(){
        return $this->cdusuario;
    }
    
    public function setCdUsuario($id){
        $this->cdusuario = $id;
    }
    
    public function getNmUsuario(){
        return $this->nmusuario;
    }
    
    public function setNmUsuario($nome){
        $this->nmusuario = $nome;
    }
}

?>
