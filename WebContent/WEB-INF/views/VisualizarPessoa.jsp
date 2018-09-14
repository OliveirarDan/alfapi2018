<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <!DOCTYPE html>
        <html lang="pt-br">

        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Visualizar Pessoa</title>

            <link href="css/bootstrap.min.css" rel="stylesheet">
            <link href="css/style.css" rel="stylesheet">
        </head>

        <body>
                <!-- Modal -->
                <div class="modal fade" id="delete-modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Fechar"><span aria-hidden="true">&times;</span>
                                </button>
                                <h4 class="modal-title" id="modalLabel">Excluir Pessoa</h4>
                            </div>
                            <div class="modal-body">
                                Deseja realmente excluir esta Pessoa?
                            </div>
                            <div class="modal-footer">
                                <form action="excluirPessoa" method="post">
                                    <input type="hidden" name="id" value="${pessoa.id}" />
                                    <button type="submit" class="btn btn-primary" name="acao" value="Excluir">Sim</button>
                                    <button type="button" class="btn btn-default" data-dismiss="modal">N&atilde;o</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.modal -->
                <!-- Barra superior com os menus de navegação -->
				<c:import url="Menu.jsp"/>
                <!-- Container Principal -->
	<div id="main" class="container">
		<h3 class="page-header">${pessoa.id} - ${pessoa.nome}</h3>
		<div class="row">
			<div class="col-md-4">
				<img alt="?" src="${pessoa.posterPath}" class="img-responsive">
			</div>
			<div class="col-md-8">
				<div class="row">
					<div class="col-md-12">
						<p><strong>Tel Celular:&nbsp;</strong>${pessoa.telcelular}</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<p><strong>Email:&nbsp;</strong>${pessoa.email}</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<p><strong>CEP:&nbsp;</strong>${pessoa.CEP}</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<p><strong>Fotos:&nbsp;</strong>${pessoa.posterPath}</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<p><strong>CPF:&nbsp;</strong>${pessoa.cpf}</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<p><strong>RG:&nbsp;</strong>${pessoa.rg}</p>
					</div>
				</div>								
			</div>
		</div>
		<hr />
		<div id="actions" class="row">
			<div class="col-md-12">
				<a href="manterPessoas.do?acao=editar&id=${pessoa.id}" class="btn btn-primary">Editar</a> 
				<a href="#" class="btn btn-danger" data-toggle="modal" data-target="#delete-modal">Excluir</a> 
				<a href="ListarPessoas.jsp" class="btn btn-default">Voltar</a>
			</div>
		</div>
	</div>
	<script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>