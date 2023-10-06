package pl.wavesoftware.tools.jocker.jni;

import io.vavr.collection.List;

import java.io.FileDescriptor;
import java.lang.reflect.Field;

public class Conatiner {

  static {
    NarSystem.loadLibrary();
  }

  private final List<String> command;
  private final List<String> environment;
  private final String dir;
  private final List<String> binds;
  private final List<String> roBinds;
  private final boolean proc;
  private final boolean dev;
  private final boolean tmpfs;
  private final FileDescriptor in;
  private final FileDescriptor out;
  private final FileDescriptor err;

  public Conatiner(
      List<String> command,
      List<String> environment,
      String dir,
      List<String> binds,
      List<String> roBinds
  ) {
    this(
        command,
        environment,
        dir,
        binds,
        roBinds,
        true,
        true,
        true,
        FileDescriptor.in,
        FileDescriptor.out,
        FileDescriptor.err
    );
  }

  public Conatiner(
      List<String> command,
      List<String> environment,
      String dir,
      List<String> binds,
      List<String> roBinds,
      boolean proc,
      boolean dev,
      boolean tmpfs,
      FileDescriptor in,
      FileDescriptor out,
      FileDescriptor err
  ) {
    this.command = command;
    this.environment = environment;
    this.dir = dir;
    this.binds = binds;
    this.roBinds = roBinds;
    this.proc = proc;
    this.dev = dev;
    this.tmpfs = tmpfs;
    this.in = in;
    this.out = out;
    this.err = err;
  }

  public void execute() {
    int retcode = exec(
        command.toJavaArray(String[]::new),
        environment.toJavaArray(String[]::new),
        dir,
        binds.toJavaArray(String[]::new),
        roBinds.toJavaArray(String[]::new),
        proc,
        dev,
        tmpfs,
        getFd(in),
        getFd(out),
        getFd(err)
    );
    if (retcode != 0) {
      throw new IllegalStateException("Command returned non zero exit code: " + retcode);
    }
  }

  private int getFd(FileDescriptor descriptor) {
    try {
      Field field = descriptor.getClass().getDeclaredField("fd");
      field.setAccessible(true);
      return (int) field.get(descriptor);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      throw new IllegalStateException(e);
    }
  }

  private native int exec(
    String[] command,
    String[] environment,
    String dir,
    String[] binds,
    String[] roBinds,
    boolean proc,
    boolean dev,
    boolean tmpfs,
    int in,
    int out,
    int err
  );
}
