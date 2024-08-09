create database dbcadastroCanditado;
use dbcadastroCanditado;
create table alunos(
	RA int primary key auto_increment,
    nome varchar (30) not null,
    foto longblob not null
);
describe alunos;



select * from alunos;
select * from alunos where ra =1;
select * from alunos where nome like 't%' order by nome limit 1;