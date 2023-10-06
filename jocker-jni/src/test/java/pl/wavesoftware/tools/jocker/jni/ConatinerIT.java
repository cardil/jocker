package pl.wavesoftware.tools.jocker.jni;

import io.vavr.collection.List;
import org.junit.Test;

public class ConatinerIT {

  @Test
  public void execute() {
    Conatiner conatiner = new Conatiner(
        List.of("echo", "Hello"),
        List.of(),
        "/tmp",
        List.of(),
        List.of()
    );
    conatiner.execute();
  }
}