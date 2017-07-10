java -cp bin example.gui.MainWindow &
java -javaagent:"java-agent.jar" -cp bin:./asm-all-5.0.4.jar:./java-agent.jar -Dformatting=component example.component.Main 