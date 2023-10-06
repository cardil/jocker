package pl.wavesoftware.tools.jocker.jni;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ConatinerIT {

  @Rule
  public final TemporaryFolder tmp = new TemporaryFolder();

  @Test
  public void execute() throws IOException {
    File err = tmp.newFile();
    int fdNum;
    try (FileOutputStream fos = new FileOutputStream(err)) {
      FileDescriptor errFd = fos.getFD();
      fdNum = FileDescriptorUtil.getFd(errFd);
      Conatiner conatiner = new Conatiner.Builder()
          .command("echo", "Hello")
          .err(errFd)
          .build();
      Assert.assertThrows(ContainerExecutionFailed.class, conatiner::execute);
    }

    byte[] bytes = Files.readAllBytes(err.toPath());
    String content = new String(bytes, StandardCharsets.UTF_8);
    String expected = "Hello from C!\n" +
                      "in: 0\n" +
                      "out: 1\n" +
                      String.format("err: %d\n", fdNum);
    Assert.assertEquals(expected, content);
  }
}