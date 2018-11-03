<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Pessoa</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>

<body>
	<!-- Barra superior com os menus de navegação -->
	<c:import url="Menu.jsp" />
	<!-- Container Principal -->
	<div id="main" class="container">
		<h3 class="page-header">Identifica Pessoa</h3>

		<form method="POST" action="pegaFoto64" enctype="multipart/form-data" >
			<div class="row">
				<div class="form-group col-md-8">
					<label for="nome">Nome</label>
					<form:errors path="nome" cssStyle="color:red" />
					<input type="text" class="form-control" name="nome" id="nome"
						maxlength="60" placeholder="nome">
				</div>
			</div>
			<div id="capturaFoto">
				<div>
					<video id="video" width="640" height="480" autoplay></video>
					<input type="button" id="snap" title="Capturar Foto" value="Captura Imagem"> 
					<canvas id="canvas" width="640" height="480"></canvas>
					<script type="text/javascript">			
				// Grab elements, create settings, etc.
				var video = document.getElementById('video');
				// Get access to the camera!
				if(navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
				    // Not adding `{ audio: true }` since we only want video now
				    navigator.mediaDevices.getUserMedia({ video: true }).then(function(stream) {
				        video.src = window.URL.createObjectURL(stream);
				        video.play();
				    });
				}
				// Elements for taking the snapshot
				var canvas = document.getElementById('canvas');
				var context = canvas.getContext('2d');
				var video = document.getElementById('video');
				// Trigger photo take
				document.getElementById("snap").addEventListener("click", function() {
					context.drawImage(video, 0, 0, 640, 480);
					var input = document.createElement('input');
					input.type = "text";
					input.name = "file";
					input.value = canvas.toDataURL();
					document.getElementById("foto").appendChild(input);
				});
				</script>
				</div>
				<div id="foto">
					
				</div>
			</div>
			<br /> <br /> <input type="submit" value="Identificar Pessoa"    title="Clique aqui para Verificar a pessoa da foto">
			
		</form>

	</div>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>

</html>