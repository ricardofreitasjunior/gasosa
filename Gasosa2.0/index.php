<html> 
    <head> 
        <meta charset="utf-8"> 
        <title>jQuery UI Dialog - Modal form</title> 
        <link rel="stylesheet" href="css/jquery.ui.all.css"> 
<!--        <link rel="stylesheet" href="css/demos.css"> -->
        <link rel="stylesheet" href="css/admarea.css"> 

        <script src="js/jquery-1.4.4.js"></script> 
        <script src="js/jquery-ui-1.8.9.custom.js"></script> 
<!--        <script src="js/jquery.bgiframe-2.1.2.js"></script> 
        <script src="js/jquery.ui.core.js"></script> 
        <script src="js/jquery.ui.widget.js"></script> 
        <script src="js/jquery.ui.mouse.js"></script> 
        <script src="js/jquery.ui.button.js"></script> 
        <script src="js/jquery.ui.draggable.js"></script> 
        <script src="js/jquery.ui.position.js"></script> 
        <script src="js/jquery.ui.resizable.js"></script> 
        <script src="js/jquery.ui.dialog.js"></script> 
        <script src="js/jquery.effects.core.js"></script> 
        <script src="js/jquery.ui.tabs.js"></script> -->
<!--        <script src="js/admarea.js"></script> -->
        <script src="js/action.js"></script> 

    </head> 
    <body> 

<!--        <div class="demo"> -->

<!--            <div id="tabs"> 
                <ul> 
                    <li><a href="#tabs-1">Aula 1 - Gasosa</a></li> 
                    <li><a href="#tabs-2">Aula 2 - Checkbox</a></li> 
                    <li><a href="#tabs-3">Aula 3 - Redirecionamento</a></li> 
                    <li><a href="#tabs-4">Aula 4 - JDBC</a></li> 
                    <li><a href="#tabs-5">Aula 5 - JSP</a></li> 
                                        
                </ul> 

                <div id="tabs-1"> 
                    <div id="users-contain" class="ui-widget"> 
                        <h1>Cálculo de Viabilidade</h1> 
                        <table id="users" class="ui-widget ui-widget-content"> 
                            <thead> 
                                <tr class="ui-widget-header "> 
                                    <th>Gasolina R$</th> 
                                    <th>Gasolina Km</th> 
                                    <th>Etanol R$</th> 
                                    <th>Etanol Km</th> 
                                    <th>Melhor opção</th> 
                                </tr> 
                            </thead> 
                            <tbody> 
                                <tr> 
                                    <td>R$ 2,54</td> 
                                    <td>9 Km</td> 
                                    <td>R$ 2,19</td> 
                                    <td>10 km</td> 
                                    <td>Gasolina</td> 
                                </tr> 
                            </tbody> 
                        </table> 
                    </div> 

                    <button id="gasosa-calc">Calcular</button> -->

                    <!--Formulario-->
<!--                    <div id="gasosa-form" title="Novo Usuário"> -->
                        <form name="frmPosto">
                            <fieldset> 
                                <input type="hidden" id="action" name="action" />
                                <label for="cdPosto">Código</label> 
                                <input type="hidden" name="cdPosto" id="cdPosto" class="text ui-widget-content ui-corner-all" /> 
                                <label for="nmPosto">Nome</label> 
                                <input type="text" name="nmPosto" id="nmPosto" class="text ui-widget-content ui-corner-all" /> 
                                <label for="vlGas">Valor Gasolina</label> 
                                <input type="text" name="vlGas" id="vlGas" class="text ui-widget-content ui-corner-all" /> 
                                <label for="vlEta">Valor Etanol</label> 
                                <input type="text" name="vlEta" id="vlEta" value="" class="text ui-widget-content ui-corner-all" /> 
                            </fieldset> 
                            <input name="botao" type="submit" id="Cadastrar" value="Cadatrar" onclick="PostoDAO()"/>
                        </form> 
<!--                    </div> 
                </div> 

                <div id="tabs-2"> 
                    
                </div> 
                
                <div id="tabs-3"> 
                    
                </div> 
            </div> 

        </div> End demo  -->

    </body> 
</html> 