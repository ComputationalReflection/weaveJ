start "Encrypt" execute1.bat
start "Dencrypt" execute2.bat
start "server" serverSTEADY.bat
java -cp bin;lib\asm-all-5.0.4.jar -javaagent:"lib\java-agent.jar" -Dformatting=chat/Program;cipher/Program;cipher/Cipher encryption.chat.Program X127.0.0.1 4500