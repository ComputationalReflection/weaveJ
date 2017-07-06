rem start "Encrypter"  java encryption.cipher.Program -e 5000 127.0.0.1 5500
rem start "Decrypter"  java encryption.cipher.Program -d 5500 127.0.0.1 6000
start "Server"     java encryption.chat.Program 4500 
start "Client"     java encryption.chat.Program 127.0.0.1 4500