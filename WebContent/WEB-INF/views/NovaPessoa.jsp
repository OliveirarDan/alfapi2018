<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Cadastro de Pessoas</title>

 	<link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>
<body>
    <!-- Barra superior com os menus de navegação -->
	<c:import url="Menu.jsp"/>
    <!-- Container Principal -->
    <div id="main" class="container">
        <h3 class="page-header">Novo cadastro</h3>
        <!-- Formulario para inclusao de clientes -->
        <form action="inserirPessoa" method="post">
            <!-- area de campos do form -->
         <div class="row">
                <div class="form-group col-md-8">
                    <label for="nome">Nome</label>
                     <form:errors path="pessoa.nome" cssStyle="color:red"/>
                    <input type="text" class="form-control" name="nome" id="nome" maxlength="60" placeholder="nome">
                </div>
            </div>
            
         <div class="row">
                <div class="form-group col-md-8">
                    <label for="sobrenome">Sobrenome</label>
                     <form:errors path="pessoa.sobrenome" cssStyle="color:red"/>
                    <input type="text" class="form-control" name="sobrenome" id="sobrenome" maxlength="60" placeholder="sobrenome">
                </div>
            </div>        
        <div class="row">
                <div class="form-group col-md-8">
                    <label for="cpf">CPF</label>
                     <form:errors path="pessoa.cpf" cssStyle="color:red"/>
                    <input type="text" class="form-control" name="cpf" id="cpf" maxlength="60" placeholder="cpf">
                </div>
            </div>   
        <div class="row">
                <div class="form-group col-md-8">
                    <label for="registro">Registro</label>
                     <form:errors path="pessoa.registrosec" cssStyle="color:red"/>
                    <input type="text" class="form-control" name="registrosec" id="registro" maxlength="60" placeholder="registro">
                </div>
            </div>   
                
       <div class="row">
                <div class="form-group col-md-8">
                    <label for="email">Email</label>
                    <form:errors path="pessoa.email" cssStyle="color:red"/>
                    <input type="text" class="form-control" name="email" id="email" maxlength="60" placeholder="email">
                </div>
            </div>                                
    <div class="row">
                <div class="form-group col-md-8">
                    <label for="datanascimento">Data de nascimento</label>
                    <form:errors path="pessoa.dataNascimento" cssStyle="color:red"/>
                    <input type="text" class="form-control" name="dataNascimento" id="datanascimento" maxlength="60" placeholder="datanascimento">
                </div>
            </div>          
    <div class="row">
                <div class="form-group col-md-8">
                    <label for="genero">Genero</label>
                    <form:errors path="pessoa.genero" cssStyle="color:red"/>
                    <input type="text" class="form-control" name="genero" id="genero" maxlength="60" placeholder="genero">
                </div>
            </div>              
                                
        <div class="row">
                <div class="form-group col-md-8">
                    <label for="telresidencial">Telefone Residencial</label>
                    <form:errors path="pessoa.telResidencial" cssStyle="color:red"/>
                    <input type="text" class="form-control" name="telResidencial" id="telresidencial" maxlength="60" placeholder="telresidencial">
                </div>
            </div>
         <div class="row">
                <div class="form-group col-md-8">
                    <label for="telsecundario">Telefone Secundario</label>
                    <form:errors path="pessoa.telSecundario" cssStyle="color:red"/>
                    <input type="text" class="form-control" name="telSecundario" id="telsecundario" maxlength="60" placeholder="telsecundario">
                </div>
            </div>           
        
         <!--  
            <div class="row">
            	    <div class="form-group col-md-8">
                    <label for="posterpath">Insira as fotos</label>
                    <input type="text" class="form-control" name="posterPath" id="posterpath" maxlength="200" placeholder="caminho da imagem das fotos">
                </div>
        
                 <div class="form-group col-md-8">
                    <label for="CPF">RG </label>
                    <input type="text" class="form-control" name="RG" id="RG" min="0.0" max="100.0" placeholder="RG">
                </div>
            </div>
            <hr />
           -->   
            
         <!-- parte para inclusao de endereço de cliente -->
            
            <!-- area de campos do form -->
            <div class="row">            
              <div class="form-group col-md-8">
                    <label for="cep">CEP</label>
                    <input type="text" class="form-control" name="endereco.cep" id="CEP" required maxlength="100" placeholder="digite o cep">
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-8">
                    <label for="tipologradouro">TipoLogradouro</label>
                    <input type="text" class="form-control" name="endereco.tipoDeLogradouro" id="tipologradouro" maxlength="60" placeholder="tipo de logradouro">
                </div>
                <div class="form-group col-md-8">
                    <label for="endereco">Endereco</label>
                    <input type="text" class="form-control" name="endereco.endereco" id="Endereco" maxlength="60" placeholder="Endereco">
                </div>

                <div class="form-group col-md-4">
                    <label for="numero">Numero</label>
                    <input type="text" class="form-control" name="endereco.numero" id="numero" placeholder="numero da residencia">
                </div>
                
                   <div class="form-group col-md-4">
                    <label for="bairro">Bairro</label>
                    <input type="text" class="form-control" name="endereco.bairro" id="bairro" placeholder="Bairro">
                </div>
                
                <div class="form-group col-md-4">
                    <label for="cidade">Cidade</label>
                    <input type="text" class="form-control" name="endereco.cidade" id="cidade" placeholder="Cidade">
                </div>
                
                <div class="form-group col-md-4">
                    <label for="estado">Estado</label>
                    <input type="text" class="form-control" name="endereco.estado" id="estado" placeholder="Estado">
                </div>
                
                <div class="form-group col-md-4">
                    <label for="Pais">Pais</label>
                    <input type="text" class="form-control" name="endereco.pais" id="pais" placeholder="Pais">
                </div>
                
            </div>
        
            <hr />
            <div id="actions" class="row">
                <div class="col-md-12">
                    <button type="submit" class="btn btn-primary" name="acao" value="criar">Salvar</button>
                    <a href="index" class="btn btn-default">Cancelar</a>
                </div>
            </div>
        </form>
            
            
    </div>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>