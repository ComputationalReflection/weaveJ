cd dir1
start "Encrypt" execute1.bat
start "Dencrypt" execute2.bat
cd ..
cd dir2
start "client" execute4Memory.bat
cd ..
cd dir1
execute3Memory.bat