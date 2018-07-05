#!/bin/bash

rm *.class
javac *.java
x-terminal-emulator -e java YodafyClienteTCP
x-terminal-emulator -e java YodafyClienteTCP
java YodafyServidorIterativo

