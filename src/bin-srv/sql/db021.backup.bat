@echo off
SET BACKUP_DIR=C:\PeritControl_v1\PCSrv\backup\
SET DB_NAME=peritcontrol
SET d=%date:~0,2%
SET m=%date:~3,2%
SET a=%date:~6,4%
SET FECHA=%d%%m%%a%
SET DIR=%BACKUP_DIR%%FECHA%_peritcontrol

cd C:\Program Files\MySQL\MySQL Server 8.0\bin
mysqldump -u root -p peritcontrol > %DIR%.sql
echo -----------------------------------------
echo Copia de seguridad finalizada con exito!
echo -----------------------------------------
echo Presione la tecla ENTER para finalizar. 
echo -----------------------------------------
PAUSE