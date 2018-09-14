#include "stdio.h"
#include "jni.h"
#include "Main.h"

JNIEXPORT jstring JNICALL Java_Main_strMethod (JNIEnv *env, jobject javaobject, jstring s) {
    const char* p = (*env)->GetStringUTFChars( env, s , NULL ) ;
    printf("%s from C\n",p);
    jstring r = (*env)->NewStringUTF(env,p);

    // modify incoming string
    char somestuff[3]={'x','y','z'} ;
    s = (*env)->NewStringUTF(env,somestuff);

    return(r);
}

JNIEXPORT jcharArray JNICALL Java_Main_chrMethod
  (JNIEnv* env, jobject javaobject, jcharArray c) {
    jchar* ch = (*env) -> GetCharArrayElements(env,c,0);
    for (int i=0; i<3; i++) {
      printf("%c",ch[i]);
    }
    printf("\n");
    jhar r = (*env)->NewCharArray(env,ch);
    return(r);
  }
