#include "stdio.h"
#include "jni.h"
#include "Main.h"

JNIEXPORT jint JNICALL Java_Main_sum (JNIEnv *env, jobject javaobj, jint a, jint b) {
  printf("It's numbers library\n");
  int sum=a+b;
  return sum;
}
