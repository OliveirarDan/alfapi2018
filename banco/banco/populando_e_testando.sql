-- Populando e testando banco --

use  alfapidb;

-- testando insert 

INSERT INTO pessoa (nome, cpf, rg, email, telCelular, codAzure) values ('Jose da silva', 12345676, '123123123', 'jose.silva@mail.com', '11 11111 1111', '01'); 

select * FROM PESSOA;


UPDATE pessoa SET nome='Jose dos Santos', cpf=123123123, rg='123543123', email='jose.santos@mail.com', cep='099099900', telCelular='11090900900', codAzure=22 WHERE idPessoa=1;