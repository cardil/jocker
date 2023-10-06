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
  jint inFd,
  jint outFd,
  jint errFd
) {

  FILE* err = fdopen(errFd, "a");

  fprintf(err, "Hello from C!\n");
  fprintf(err, "in: %d\n", inFd);
  fprintf(err, "out: %d\n", outFd);
  fprintf(err, "err: %d\n", errFd);

  fflush(err);

	return (outFd+1) * (errFd+1);
}
