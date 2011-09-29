//##################################################
//                  POSTO
//##################################################
//function doPost(formName, actionName)
//{
//	var hiddenControl = document.getElementById('action');
//	var theForm = document.getElementById(formName);
//	
//	hiddenControl.value = actionName;
//	theForm.submit();
//}
function PostoDAO(){
//    confirmação
//    if (confirm('Tem certeza que quer enviar este formulário?')){ 
//      document.seuformulario.submit() 
//   }
    $.post("../classes/actionPosto.php", {
        funcao: 'Cadastrar',
        cdPosto: frmPosto.cdPosto.value,
        nmPosto: frmPosto.nmPosto.value,
        vlGas: frmPosto.vlGas.value,
        vlEta: frmPosto.vlEta.value
    },
    function(output){
        $('#retorno').html(output).show();
    });
//    alert("Chegou aqui.");
}

//function CadastrarPosto(){
//    $.post('actionPosto.php', {
//        funcao: frmPosto.botao.value,
//        cdPosto: frmPosto.cdPosto.value,
//        nmPosto: frmPosto.nmPosto.value,
//        vlGas: frmPosto.vlGas.value,
//        vlEta: frmPosto.vlEta.value
//    },
//    function(output){
//        $('#retorno').html(output).show();
//    });
//}
//
//function AlterarPosto(){
//    $.post('actionPosto.php',{
//        funcao: frmPosto.botao.value,
//        cdPosto: frmPosto.cdPosto.value,
//        nmPosto: frmPosto.nmPosto.value,
//        vlGas: frmPosto.vlGas.value,
//        vlEta: frmPosto.vleta.value       
//    },
//    function(output){
//        $('#retorno').html(output).show();
//    });
//}
//
//function RemoverPosto(){
//    $.post('actionPosto.php', {
//        funcao: frmPosto.botao.value,
//        cdPosto: frmPosto.cdPosto.value,
//        nmPosto: frmPosto.nmPosto.value,
//        vlGas: frmPosto.vlGas.value,
//        vlEta: frmPosto.vlEta.value
//    },
//    function(output){
//        $('#retorno').html(output).show();
//    });
//}
//
//function ListarPosto(){
//    $.post('actionPosto.php', {
//        funcao: frmPosto.botao.value,
//        cdPosto: frmPosto.cdPosto.value,
//        nmPosto: frmPosto.nmPosto.value,
//        vlGas: frmPosto.vlGas.value,
//        vlEta: frmPosto.vlEta.value
//    }, 
//    function(output){
//        $('#retorno').html(output).show();
//    });
//}
//
//function LocalizarPosto(){
//    $.post('actionPosto.php', {
//        funcao: frmPosto.botao.value,
//        cdPosto: frmPosto.cdPosto.value,
//        nmPosto: frmPosto.nmPosto.value,
//        vlGas: frmPosto.vlGas.value,
//        vlEta: frmPosto.vlEta.value
//    }, 
//    function(output){
//        $('#retorno').html(output).show();
//    });
//}