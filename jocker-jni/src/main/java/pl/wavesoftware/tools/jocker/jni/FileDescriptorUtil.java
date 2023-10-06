package pl.wavesoftware.tools.jocker.jni;

import java.io.FileDescriptor;
import java.lang.reflect.Field;

final class FileDescriptorUtil {
  static int getFd(FileDescriptor descriptor) {
    try {
      Field field = descriptor.getClass().getDeclaredField("fd");
      field.setAccessible(true);
      return (int) field.get(descriptor);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      throw new IllegalStateException(e);
    }
  }
}
