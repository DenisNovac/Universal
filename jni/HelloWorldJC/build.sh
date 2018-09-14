cd src
javac Main.java -d ../bin
cd ../bin
javah Main
cd ../src


gcc -I /usr/lib/jvm/java-1.8.0-openjdk-amd64/include/ -I \
/usr/lib/jvm/java-1.8.0-openjdk-amd64/include/linux -I ../bin -fPIC \
 -shared hello.c -o hello.o
gcc -I /usr/lib/jvm/java-1.8.0-openjdk-amd64/include/ -I \
/usr/lib/jvm/java-1.8.0-openjdk-amd64/include/linux -I ../bin -fPIC \
 -shared numbers.c -o numbers.o

mv hello.o ../bin
mv numbers.o ../bin
cd ../bin


java Main
