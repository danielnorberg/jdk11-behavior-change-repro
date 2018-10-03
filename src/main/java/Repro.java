import java.io.IOException;

public class Repro {

  /**
   * Fails to compile on JDK 11 but compiles on : "unreported exception java.lang.Exception; must be caught or declared to be thrown"
   */
  void repro() throws IOException {
    when(f(any()));
  }

  interface G<T, E extends Exception> { }

  <T, E extends Exception> T f(G<T, E> g) throws IOException, E {
    return null;
  }

  static <T> T any() {
    return null;
  }

  static <T> OngoingStubbing<T> when(T methodCall) {
    return null;
  }

  interface OngoingStubbing<T> { }
}
