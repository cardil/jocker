package pl.wavesoftware.tools.jocker.jni;

import java.io.IOException;

public class ContainerExecutionFailed extends IOException {
  public final int retcode;

  public ContainerExecutionFailed(int retcode) {
    super("Container returned non zero exit code: " + retcode);
    this.retcode = retcode;
  }
}
