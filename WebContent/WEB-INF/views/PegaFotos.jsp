<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="pt-br">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Identificando Pessoas</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

</head>
<body>
	<!-- Barra superior com os menus de navegação -->
	<c:import url="Menu.jsp" />
	<!-- Container Principal -->
	<div id="main" class="container">
		<h3 class="page-header">Identifica Pessoa</h3>

		<!--

		<div>
				<video id="video" width="640" height="480" autoplay></video>
				<button id="snap">Snap Photo</button>
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
				});
				
				
				</script>
			</div>
-->



		<form method="POST" action="pegaFoto" enctype="multipart/form-data">

			<!-- Teste com captura da camera 	 Método 1

			<video id="player" controls autoplay></video>
			<input type="button" id="capture">Capture</button>
			<canvas id="snapshot" width=320 height=240  name="file"></canvas>
			<script>
				var player = document.getElementById('player');
				var snapshotCanvas = document.getElementById('snapshot');
				var captureButton = document.getElementById('capture');
				var videoTracks;

				var handleSuccess = function(stream) {
					// Attach the video stream to the video element and autoplay.
					player.srcObject = stream;
					videoTracks = stream.getVideoTracks();
				};

				captureButton.addEventListener('click', function() {
					var context = snapshot.getContext('2d');
					
					context.drawImage(player, 0, 0, snapshotCanvas.width,
							snapshotCanvas.height);
					canvas.src = URL.createObjectURL(file);
				
			
					// Stop all video streams.
					videoTracks.forEach(function(track) {
						track.stop()
					});
				});

				navigator.mediaDevices.getUserMedia({
					video : true
				}).then(handleSuccess);
			</script>

	-->
			<div class="row">
				<div class="form-group col-md-8">
					<label for="nome">Nome</label>
					<form:errors path="nome" cssStyle="color:red" />
					<input type="text" class="form-control" name="nome" id="nome"
						maxlength="60" placeholder="nome">
				</div>
			</div>


				<form:errors path="file" cssStyle="color:red" />
				<input type="file" class="form-control" name="file" id="file">
			
			<!-- TEste com captura da camera 	Método 2
			<input type="file" accept="image/*" capture="camera" id="camera"
				name="file">
				<form:errors path="file" cssStyle="color:red"/>
				 <img id="frame">
				
			<script>
				var camera = document.getElementById('camera');
				var frame = document.getElementById('frame');

				camera.addEventListener('change', function(e) {
					var file = e.target.files[0];
					// Do something with the image file.
					frame.src = URL.createObjectURL(file);
				});
			</script>
			
			 -->


			<br /> <br /> <input type="submit" value="Upload"> Clique
			aqui para Verificar a pessoa da foto
		</form>



		<!-- Formulario para inclusao de clientes
		<form:form  action="pegaFoto" method="post" enctype="multipart/form-data">
			
			<!-- area de campos do form 
			<div class="row">
				<div class="form-group col-md-8">
                    <label for="nome">Nome</label>
                     <form:errors path="pessoa.nome" cssStyle="color:red"/>
                    <input type="text" class="form-control" name="nome" id="nome" maxlength="60" placeholder="nome">
                </div>
			</div>
			
			 <div class="row">
				<div class="form-group col-md-8">
					<label for="foto">foto</label>
					<input type="file" name="foto" id="pessoa.foto"/>
				</div>
			</div> 


			<hr />
			<div id="actions" class="row">
				<div class="col-md-12">
					<button type="submit" class="btn btn-primary" name="acao"
						value="criar">Salvar</button>
					<a href="index" class="btn btn-default">Cancelar</a>
				</div>
			</div>
		</form:form>
 -->

	</div>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>