<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Resultado</title>
</head>
<body>
	<h2>Nome da pessoa: ${pessoa.nome}</h2>
	<h3>CPF da pessoa: ${pessoa.cpf}</h3>
	<h3>Código Azure: ${pessoa.codAzure}</h3>
	<img alt="Foto" src="${pessoa.foto}">
</body>
</html>
