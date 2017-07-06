start "Encrypt" execute1.bat
start "Dencrypt" execute2.bat
start "client" client8MEM.bat
java -cp bin;lib\asm-all-5.0.4.jar -javaagent:"lib\java-agent.jar" -Dformatting=chat/Program;cipher/Program;cipher/Cipher encryption.chat.Program  M4500