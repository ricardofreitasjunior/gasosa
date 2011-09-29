<?php

/**
 * Description of actionPosto
 *
 * @author ricardo
 */
require_once('requires.php');

$posto = new Posto();

$posto->setCdPosto($_POST['cdPosto']);
$posto->setNmPosto($_POST['nmPosto']);
$posto->setVlGas($_POST['vlGas']);
$posto->setVlEta($_POST['vlEta']);

$funcao = $_REQUEST['funcao'];

if (function_exists($funcao)) {
    call_user_func($funcao);
}

function Cadastrar() {
    global $posto;

    $DAO = new PostoDAO();
    $DAO->Cadastrar($posto);

    foreach ($DAO->Localizar($posto) as $row){
        echo 'Posto ' . $row['nmPosto'] . ' cadastrado com sucesso.';
    }
}

function Alterar() {
    global $posto;
    
    $DAO = new PostoDAO();
    $DAO->Alterar($posto);
    
    foreach ($DAO->Localizar($posto) as $row){
        echo 'Posto ' . $row['nmPosto'] . ' alterado com sucesso.';
    }
}

function Remover() {
    global $posto;
    
    $DAO = new PostoDAO();
    $DAO->Remover($posto);
    
    echo 'Posto' . $posto->getNmPosto() . 'removido com sucesso.';
}

function Listar() {
    $DAO = new PostoDAO();
    
    echo 'Listagem de Postos';
    foreach ($DAO->Listar() as $row){
        echo 'Codigo: ' . $row['cdPosto'];
        echo 'Nome: ' . $row['nmPosto'];
        echo 'Valor Gas: ' . $row['vlGas'];
        echo 'Valor Eta: ' . $row['vlEta'];
        echo '#';
    }
}

function Localizar() {
    global $posto;
    
    $DAO = new PostoDAO();
    
    echo 'Postos encontrados';
    foreach ($DAO->Localizar($posto) as $row){
        echo 'Código: ' . $row['cdPosto'];
        echo 'Nome: ' . $row['nmPosto'];
        echo 'Valor Gas: ' . $row['vlGas'];
        echo 'Valor Eta: ' . $row['vlEta'];
        echo '#';
    }
}

?>
