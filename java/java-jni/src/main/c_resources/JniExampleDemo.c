#include<jni.h>
#include <stdio.h>
#include "com_study_JniExampleDemo.h"

int countProperty = 0;

JNIEXPORT void JNICALL Java_com_study_JniExampleDemo_set(JNIEnv *env, jobject thisObj, jint thisInt) {
   printf("this is native set method!\n");
   countProperty = thisInt;
   return;
}


JNIEXPORT jint JNICALL Java_com_study_JniExampleDemo_get(JNIEnv *env, jobject thisObj) {
   printf("this is native get method!\n");
   return countProperty;
}



