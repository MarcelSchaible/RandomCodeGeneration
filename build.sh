WIP=`pwd`
OUT=${WIP}/out
ANTLR=/usr/local/lib/antlr-4.7.2-complete.jar

pushd .
cd src
java -jar ${ANTLR} -package org.antlr.parser.antlr4 -o org/antlr/parser/antlr4 -visitor -no-listener LexBasic.g4
java -jar ${ANTLR} -package org.antlr.parser.antlr4 -o org/antlr/parser/antlr4 -visitor -no-listener ANTLRv4Lexer.g4
java -jar ${ANTLR} -package org.antlr.parser.antlr4 -o org/antlr/parser/antlr4 -visitor -no-listener ANTLRv4Parser.g4

pushd .
cd org/antlr/parser/antlr4
javac -cp ${ANTLR} -d ${OUT} *.java
popd

pwd
javac -classpath "${ANTLR}:${OUT}" -d ${OUT} *.java



popd





