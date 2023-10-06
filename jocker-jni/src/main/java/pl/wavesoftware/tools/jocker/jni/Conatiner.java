package pl.wavesoftware.tools.jocker.jni;

import io.vavr.collection.List;

import java.io.FileDescriptor;

import static pl.wavesoftware.tools.jocker.jni.FileDescriptorUtil.getFd;

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

  private Conatiner(
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

  public void execute() throws ContainerExecutionFailed {
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
      throw new ContainerExecutionFailed(retcode);
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

  public static final class Builder {
    public List<String> command = List.empty();
    public List<String> environment = List.empty();
    public String dir = "/";
    public List<String> binds = List.empty();
    public List<String> roBinds = List.empty();
    public boolean proc = true;
    public boolean dev = true;
    public boolean tmpfs = true;
    public FileDescriptor in = FileDescriptor.in;
    public FileDescriptor out = FileDescriptor.out;
    public FileDescriptor err = FileDescriptor.err;

    public Builder command(String... command) {
      this.command = List.of(command);
      return this;
    }

    public Builder environment(List<String> environment) {
      this.environment = environment;
      return this;
    }

    public Builder dir(String dir) {
      this.dir = dir;
      return this;
    }

    public Builder binds(List<String> binds) {
      this.binds = binds;
      return this;
    }

    public Builder roBinds(List<String> roBinds) {
      this.roBinds = roBinds;
      return this;
    }

    public Builder proc(boolean proc) {
      this.proc = proc;
      return this;
    }

    public Builder dev(boolean dev) {
      this.dev = dev;
      return this;
    }

    public Builder tmpfs(boolean tmpfs) {
      this.tmpfs = tmpfs;
      return this;
    }

    public Builder in(FileDescriptor in) {
      this.in = in;
      return this;
    }

    public Builder out(FileDescriptor out) {
      this.out = out;
      return this;
    }

    public Builder err(FileDescriptor err) {
      this.err = err;
      return this;
    }

    public Conatiner build() {
      return new Conatiner(
          command,
          environment,
          dir,
          binds,
          roBinds,
          proc,
          dev,
          tmpfs,
          in,
          out,
          err
      );
    }
  }
}
