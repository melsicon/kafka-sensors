package de.melsicon.kafka.sensors.app;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.flogger.FluentLogger;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ResourceList.ResourceFilter;
import java.io.File;

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
      var builder = ImmutableMultimap.<File, String>builder();
      for (var duplicate : resourceList.findDuplicatePaths()) {
        try (var duplicates = duplicate.getValue()) {
          for (var resource : duplicates) {
            builder.put(resource.getClasspathElementFile(), duplicate.getKey());
          }
        }
      }
      var map = builder.build();

      for (var file : map.keySet()) {
        logger.atWarning().log("Duplicate resource in %s:", file);
        for (String resource : map.get(file)) {
          logger.atWarning().log(" -> %s", resource);
        }
      }
    }
    logger.atInfo().log("Check finished");
  }
}
