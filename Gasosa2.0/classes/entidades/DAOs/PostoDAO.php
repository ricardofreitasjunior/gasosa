<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of PostoDAO
 *
 * @author ricardo
 */
class PostoDAO extends ConnectionFactory {

    public $connection = null;

    public function PostoDAO() {
        $this->connection = ConnectionFactory::Connect();
    }

    public function Cadastrar($posto) {
        try {
            $stmt = $this->connection->prepare("INSERT INTO postos (cdPosto, nmPosto, vlGas, vlEta) VALUES (?, ?, ?, ?)");

            $stmt->bindValue(1,$posto->getCdPosto());
            $stmt->bindValue(2,$posto->getNmPosto());
            $stmt->bindValue(3,$posto->getVlGas());
            $stmt->bindValue(4, $posto->getVlEta());
            
            $stmt->execute();
            $this->connection = null;

            } catch (PDOException $exc) {
            echo "Erro de Cadastro: " . $exc->getMessage();
        }
    }

    public function Alterar($posto) {
        try {
            $stmt = $this->connection->prepare("UPDATE postos SET nmPosto=?, vlGas=?, vlEta=? WHERE cdPosto=?");
            
            $stmt->bindValue(1,$posto->getNmPosto());
            $stmt->bindValue(2,$posto->getVlGas());
            $stmt->bindValues(3,$posto->getVlEta());
            $stmt->bindValue(4, $posto->getCdPosto());
            
            $stmt->execute();
            $this->connection = null;
        } catch (PDOException $exc) {
            echo "Erro de Alteração: " . $exc->getMessage();
        }
    }

    public function Remover($posto) {
        try {
            $stmt = $this->connection->prepare("DELETE FROM postos WHERE cdPosto=?");
            
            $stmt->bindValue(1,$posto->getCdPosto());
            
            $stmt->execute();
            
            $this->connection = null;
        } catch (PDOException $exc) {
            echo "Erro de Remoção: " . $exc->getMessage();
        }
    }

    public function Listar() {
        try {
            $stmt = $this->connection->prepare("SELECT * FROM postos");
            
            $stmt->execute();
            
            $this->connection = null;
            
            return $stmt;
        } catch (PDOException $exc) {
            echo "Erro de Listagem: " . $exc->getMessage();
        }
    }

    public function Localizar($posto) {
        try {
            $stmt = $this->connection->prepare("SELECT * FROM postos WHERE cdPsoto=? or nmPosto=%?% or vlGas=? or vlEta=?");
            
            $stmt->bindValues(1,$posto->getCdPosto());
            $stmt->bindValue(2,$posto->getNmPosto());
            $stmt->bindValue(3,$posto->getVlGas());
            $stmt->bindValue(4,$posto->getVlEta());
            
            $stmt->execute();
            
            $this->connection = null;
            
            return $stmt;
        } catch (PDOException $exc) {
            echo "Erro de Localização: " . $exc->getMessage();
        }
    }

}

?>
