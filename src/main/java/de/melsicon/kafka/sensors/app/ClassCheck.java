package de.melsicon.kafka.sensors.app;

import com.google.common.flogger.FluentLogger;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ResourceList.ResourceFilter;

public final class ClassCheck {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private static final ResourceFilter MY_FILTER =
      resource -> {
        var path = resource.getPath();
        if (path.equals("module-info.class")) {
          return false;
        }
        if (!path.endsWith(".class") || path.length() < 7) {
          return false;
        }
        // Check filename is not simply ".class"
        var c = path.charAt(path.length() - 7);
        return c != '/' && c != '.';
      };

  private ClassCheck() {}

  public static void main(String... args) {
    var classGraph = new ClassGraph();
    try (var scan = classGraph.scan();
        var resourceList = scan.getAllResources().filter(MY_FILTER)) {
      for (var duplicate : resourceList.findDuplicatePaths()) {
        logger.atWarning().log("Duplicate resource: %s", duplicate.getKey());
        try (var duplicates = duplicate.getValue()) {
          for (var resource : duplicates) {
            logger.atWarning().log(" -> %s", resource.getClasspathElementFile());
          }
        }
      }
    }
    logger.atInfo().log("Check finished");
  }
}
