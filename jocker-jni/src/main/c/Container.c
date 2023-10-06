#include <stdio.h>
#include "pl_wavesoftware_tools_jocker_jni_Conatiner.h"

JNIEXPORT jint JNICALL Java_pl_wavesoftware_tools_jocker_jni_Conatiner_exec(
  JNIEnv *jni, jobject obj,
  jobjectArray command,
  jobjectArray environment,
  jstring dir,
  jobjectArray binds,
  jobjectArray roBinds,
  jboolean proc,
  jboolean dev,
  jboolean tmpfs,
  jint in,
  jint out,
  jint err
) {

  printf("Hello from C!\n");
  printf("in: %d\n", in);
  printf("out: %d\n", out);
  printf("err: %d\n", err);

	return out * err;
}
