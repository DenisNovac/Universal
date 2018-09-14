cd src
javac Main.java -d ../bin
cd ../bin
javah Main
cd ../src
gcc -I /usr/lib/jvm/java-1.8.0-openjdk-amd64/include/linux \
-I /usr/lib/jvm/java-1.8.0-openjdk-amd64/include \
-I ../bin -fPIC -shared stringlib.c -o stringlib.so
mv stringlib.so ../bin

cd ../bin
java Main
