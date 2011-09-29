<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of ConnectionFactory
 *
 * @author ricardo
 */
class ConnectionFactory {

    private $connection = null;
    private $dbType = "mysql";
    private $host = "127.0.0.1";
    private $user = "gasosa";
    private $password = "gasosa";
    private $db = "gasosa";


    public function Connect() {
        try {
            // realiza a conexão
//            $this->con = new PDO($this->dbType . ":host=" . $this->host . ";dbname=" . $this->db, $this->user, $this->senha,
//                            array(PDO::ATTR_PERSISTENT => $this->persistent
            $this->connection = new PDO($this->dbType . ":host=" . $this->host . ";dbname=" . $this->db, $this->user, $this->password, false);
            return $this->connection;
        } catch (PDOException $ex) {
            echo "Não foi possível conectar ao banco. Erro: " . $exc->getMessage();
        }
    }
    
    public function Disconnect(){
        if($this->connection != null){
            $this->connection = null;
        }
    }
    
}

?>
