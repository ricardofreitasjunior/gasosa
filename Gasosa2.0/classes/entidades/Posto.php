<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Posto
 *
 * @author ricardo
 */
class Posto {
    public $cdposto;
    public $nmposto;
    public $vlgas;
    public $vleta;

    //construtor
    public function Posto() {
        
    }

    public function getCdPosto() {
        return $this->cdposto;
    }

    public function setCdPosto($id) {
        $this->cdposto = $id;
    }
    
    public function getNmPosto(){
        return $this->nmPosto;
    }
    
    public function setNmPosto($nome){
        $this->nmposto = $nome;
    }
    
    public function getVlGas(){
        return $this->vlgas;
    }
    
    public function setVlGas($gas){
        $this->vlgas = $gas;
    }
    
    public function getVlEta(){
        return $this->vleta;
    }
    
    public function setVlEta($eta){
        $this->vleta = $eta;
    }
}

?>
